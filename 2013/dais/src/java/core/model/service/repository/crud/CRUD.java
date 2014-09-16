package core.model.service.repository.crud;


import core.model.service.repository.crud.storage.StorageInfo;
import core.model.service.repository.crud.storage.StorageDataRow;
import javax.sql.DataSource;


abstract public class CRUD implements ICRUD
{
	private DataSource     dataSource;
	private StorageInfo    storageInfo;
	private boolean        storageInfoInitialized = false;
	private StorageDataRow storageRowData;
	private StateCRUD      crudState = StateCRUD.NONE;


	@Override
	public void setDataSource(DataSource ds)
	{
		this.dataSource = ds;
	}


	protected DataSource getDataSource()
	{
		return dataSource;
	}


	@Override
	public void setStorageInfo(StorageInfo storageInfo)
	{
		this.storageInfo = storageInfo;
	}


	protected StorageInfo getStorageInfo()
	{
//		initializeTable();
		return storageInfo;
	}


	@Override
	public String getName()
	{
		return getStorageInfo().getName();
	}


	@Override
	public PrimaryKey getPrimaryKey()
	{
		return getStorageInfo().getPrimaryKey();
	}


	protected StorageDataRow beforeCreate()
	{
		// NOTE: here you can put some default values...
		// NOTE: TableInfo defaultData = new TableInfo(); ... return defaultData;
		setDataRow(null, StateCRUD.CREATE_BEFORE);
		return null;
	}


	protected StorageDataRow beforeRead()
	{
		return beforeRead(getPrimaryKey());
	}


	protected StorageDataRow beforeRead(PrimaryKey pkey)
	{
		setDataRow(null, StateCRUD.READ_BEFORE);
		return null;
	}


	protected StorageDataRow beforeUpdate(PrimaryKey pkey, StorageDataRow initialData)
	{
		setDataRow(null, StateCRUD.UPDATE_BEFORE);
		return initialData;
	}


	protected StorageDataRow beforeDelete(PrimaryKey pkey)
	{
		// NOTE: here you can put some requirement data integrity check...
		setDataRow(null, StateCRUD.DELETE_BEFORE);
		return null;
	}


	abstract protected StorageDataRow processCreate(StorageDataRow beforeCreateData);
	abstract protected StorageDataRow processRead(PrimaryKey pkey, StorageDataRow beforeReadData);
	abstract protected StorageDataRow processUpdate(PrimaryKey pkey, StorageDataRow beforeUpdateData);
	abstract protected StorageDataRow processDelete(PrimaryKey pkey, StorageDataRow beforeDeleteData);


	protected StorageDataRow afterCreate(PrimaryKey pkey, StorageDataRow processCreateData)
	{
		return setDataRow(processCreateData, StateCRUD.CREATE_AFTER);
	}


	protected StorageDataRow afterRead(PrimaryKey pkey, StorageDataRow processReadData)
	{
		return setDataRow(processReadData, StateCRUD.READ_AFTER);
	}


	protected StorageDataRow afterUpdate(PrimaryKey pkey, StorageDataRow processUpdateData)
	{
		return setDataRow(processUpdateData, StateCRUD.UPDATE_AFTER);
	}


	protected StorageDataRow afterDelete(PrimaryKey pkey, StorageDataRow processDeleteData)
	{
		return setDataRow(processDeleteData, StateCRUD.DELETE_AFTER);
	}


	protected StorageDataRow beforeCreate_mergeInitialData(StorageDataRow beforeCreateData, StorageDataRow initialData)
	{
		setState(StateCRUD.CREATE_MERGE_INITIAL_DATA);

		if (!(initialData instanceof StorageDataRow)) {
			throw new RuntimeException("Invalid initialData! it isn't instance of StorageDataRow!");
		}

		if (beforeCreateData instanceof StorageDataRow) {
			// TODO: foreach(sloupce) -> pokud mam beforeCreateData && nemam initialData -> mrdnu to tam!
// initialData.is -> set | get
//			for(Entry<String, Object> e : beforeCreateData.entrySet()) {
//
//
//		}
//		if (isSet() && rowData instanceof StorageDataRow) {
//			int pkDataCount = 0;
//			for(Entry<String, Object> e : pkey.entrySet()) {
//				Object pkValue = whereCondition.get(e.getKey());
//
//				if (rowData.is(e.getKey())) {
//					Object pkData = rowData.get(e.getKey());
//
//					if (pkValue.equals(pkData)) {
//						pkDataCount++;
//					}
//				}
//			}
		}

		return initialData;
	}


	protected void updatePrimaryKey(StorageDataRow data, StateCRUD state)
	{
		setState(state);
		boolean integrityCheck = true;
		if (
			getState().ordinal() > crudState.NONE.ordinal() && // CREATED, CREATE_*, ...
			getState().ordinal() < crudState.READ.ordinal()
		   ) {

			integrityCheck = false;
		}

		PrimaryKey pkey = getPrimaryKey();

		// integrity check
		if (integrityCheck) {
			if (!(pkey instanceof PrimaryKey)) {
				throw new RuntimeException("Invalid primary-key! it isn't PrimaryKey instance! try: PrimaryKey.set() first!");
			}

			if (!pkey.checkIntegrity(data)) {
				throw new RuntimeException("PrimaryKey.integrityCheck() failed! Invalid rowData and primary-key!");
			}
		}

		try {
			pkey.setWhereValues(data);

		} catch (Exception e) {
			throw new RuntimeException("Unable to updatePrimaryKey() from StorageDataRow data!", e);
		}
	}


	protected StorageDataRow setDataRow(StorageDataRow data, StateCRUD state)
	{
		storageRowData = data;
		return storageRowData;
	}


	@Override
	public StorageDataRow getDataRow()
	{
		return storageRowData;
	}

	
	protected void setState(StateCRUD state)
	{
		crudState = state;
	}

	
	public StateCRUD getState()
	{
		return crudState;
	}


	@Override
	public StorageDataRow Create(StorageDataRow data)
	{
		setState(StateCRUD.NONE);

		StorageDataRow beforeData = beforeCreate();
		StorageDataRow mergedData = null;
		if (beforeData instanceof StorageDataRow) {
			mergedData = beforeCreate_mergeInitialData(beforeData, data);
			if (mergedData instanceof StorageDataRow) {
				throw new RuntimeException("Invalid data! beforeCreate_mergeInitialData() returns different data than StorageDataRow instance!");
			}
		}

		StorageDataRow processData = processCreate(mergedData);
		if (processData instanceof StorageDataRow) {
			updatePrimaryKey(processData, StateCRUD.CREATE_SET_PRIMARY_KEY);
			PrimaryKey pkey = getPrimaryKey();

			if (processData instanceof StorageDataRow) {
				if (pkey instanceof PrimaryKey && pkey.isSet()) {
					StorageDataRow afterData = afterCreate(getPrimaryKey(), processData);
					if (afterData instanceof StorageDataRow) {
						setState(StateCRUD.CREATED);
						return afterData;
					}
					throw new RuntimeException("Invalid data! afterCreate() returns different data than StorageDataRow instance!");
				}
				throw new RuntimeException("Invalid primary-key! processCreate_updatePrimaryKey() doesn't set correct PrimaryKey via: PrimaryKey.setWhere()!");
			}
			throw new RuntimeException("Invalid data! processCreate_updatePrimaryKey() returns different data than StorageDataRow instance!");
		}
		throw new RuntimeException("Invalid data! processCreate() returns different data than StorageDataRow instance!");
	}


	@Override
	public StorageDataRow Read()
	{
		return Read(getPrimaryKey());
	}


	@Override
	public StorageDataRow Read(PrimaryKey pkey)
	{
		if (pkey instanceof PrimaryKey) {
			if (!pkey.isSet()) {
				throw new RuntimeException("Invalid primary-key! PrimaryKey.setWhere() first!");
			}
			setState(StateCRUD.NONE);

			StorageDataRow beforeData  = beforeRead(pkey);
			StorageDataRow processData = processRead(pkey, beforeData);

			if (processData instanceof StorageDataRow) {
				if (processData.isEmpty()) {
					return setDataRow(processData, StateCRUD.NONE);
				}

			} else {
				throw new RuntimeException("Invalid data! processRead() returns different data than StorageDataRow instance!");
			}

			updatePrimaryKey(processData, StateCRUD.READ_CHECK_PRIMARY_KEY);

			StorageDataRow afterData = afterRead(pkey, processData);
			if (afterData instanceof StorageDataRow) {
				setState(StateCRUD.READ);
				return afterData;
			}
			throw new RuntimeException("Invalid data! afterRead() returns different data than StorageDataRow instance!");

		}
		throw new RuntimeException("Invalid primary-key! it isn't PrimaryKey instance! try: PrimaryKey.set() first!");
	}


	@Override
	public StorageDataRow Update(StorageDataRow data)
	{
		return Update(getPrimaryKey(), data);
	}


	@Override
	public StorageDataRow Update(PrimaryKey pkey, StorageDataRow data)
	{
		if (pkey instanceof PrimaryKey) {
			if (!pkey.isSet()) {
				throw new RuntimeException("Invalid primary-key! PrimaryKey.setWhere() first!");
			}
			setState(StateCRUD.NONE);

			StorageDataRow beforeData  = beforeUpdate(pkey, data);
			StorageDataRow processData = processUpdate(pkey, beforeData);

			updatePrimaryKey(processData, StateCRUD.UPDATE_CHECK_PRIMARY_KEY);
			if (processData instanceof StorageDataRow) {
				StorageDataRow afterData = afterUpdate(pkey, processData);
				if (afterData instanceof StorageDataRow) {
					setState(StateCRUD.UPDATED);
					return afterData;
				}
				throw new RuntimeException("Invalid data! afterUpdate() returns different data than StorageDataRow instance!");
			}
			throw new RuntimeException("Invalid data! processUpdate() returns different data than StorageDataRow instance!");
		}
		throw new RuntimeException("Invalid primary-key! it isn't PrimaryKey instance! try: PrimaryKey.set() first!");
	}


	@Override
	public StorageDataRow Delete()
	{
		return Delete(getPrimaryKey());
	}


	@Override
	public StorageDataRow Delete(PrimaryKey pkey)
	{
		if (pkey instanceof PrimaryKey) {
			if (!pkey.isSet()) {
				throw new RuntimeException("Invalid primary-key! PrimaryKey.setWhere() first!");
			}
			setState(StateCRUD.NONE);

			StorageDataRow beforeData  = beforeDelete(pkey);
			StorageDataRow processData = processDelete(pkey, beforeData);

			updatePrimaryKey(processData, StateCRUD.DELETE_CHECK_PRIMARY_KEY);
			if (processData instanceof StorageDataRow) {
				StorageDataRow afterData = afterDelete(pkey, processData);
				if (afterData instanceof StorageDataRow) {
					setState(StateCRUD.DELETED);
					return afterData;
				}
				throw new RuntimeException("Invalid data! afterDelete() returns different data than StorageDataRow instance!");
			}
			throw new RuntimeException("Invalid data! processDelete() returns different data than StorageDataRow instance!");
		}
		throw new RuntimeException("Invalid primary-key! it isn't PrimaryKey instance! try: PrimaryKey.set() first!");
	}


	protected void initialize()
	{
		initializeStorageInfo(false);
	}


	protected void initializeStorageInfo(boolean forceInitialization)
	{
		if ((getDataSource() instanceof DataSource && getStorageInfo() instanceof StorageInfo)) {
			if (forceInitialization || !storageInfoInitialized) {
				StorageInfo info = gatheringStorageInfo();

				storageInfoInitialized = false;
				if (info instanceof StorageInfo) {
					setStorageInfo(info);
					storageInfoInitialized = true;
				}
			}
		}
	}


	protected boolean isInitializated()
	{
		return storageInfoInitialized;
	}


	/**
	 * gather useful information from storage ~ like: columns, primary keys, ...
	 * @return
	 */
	abstract protected StorageInfo gatheringStorageInfo();


}
