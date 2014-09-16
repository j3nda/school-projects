package OBSOLETE_jdbc;

import model.dao.crud.pkey.PrimaryKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map.Entry;
import javax.sql.DataSource;
import modelx.PredmetModel;


public abstract class CRUD implements ICRUD
{
	private DataSource        dataSource;
	private StorageInfo    tableInfo;
	private boolean           tableInfoInitialized = false;
	private RepositoryDataRow dataRow;
	private CrudStateEnum     crudState = CrudStateEnum.NONE;


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
	public void setTableInfo(StorageInfo tableInfo)
	{
		this.tableInfo = tableInfo;
	}


	protected StorageInfo getTableInfo()
	{
//		initializeTable();
		return tableInfo;
	}


	@Override
	public String getTableName()
	{
		return getTableInfo().getTableName();
	}


	@Override
	public PrimaryKey getPrimaryKey()
	{
		return getTableInfo().getPrimaryKey();
	}


	protected RepositoryDataRow beforeCreate()
	{
		// NOTE: here you can put some default values...
		// NOTE: TableInfo defaultData = new TableInfo(); ... return defaultData;
		setDataRow(null, CrudStateEnum.CREATE_BEFORE);
		return null;
	}


	protected RepositoryDataRow beforeRead()
	{
		return beforeRead(getPrimaryKey());
	}


	protected RepositoryDataRow beforeRead(PrimaryKey pkey)
	{
		setDataRow(null, CrudStateEnum.READ_BEFORE);
		return null;
	}


	protected RepositoryDataRow beforeUpdate(PrimaryKey pkey, RepositoryDataRow initialData)
	{
		setDataRow(null, CrudStateEnum.UPDATE_BEFORE);
		return initialData;
	}


	protected RepositoryDataRow beforeDelete(PrimaryKey pkey)
	{
		// NOTE: here you can put some requirement data integrity check...
		setDataRow(null, CrudStateEnum.DELETE_BEFORE);
		return null;
	}


	abstract protected RepositoryDataRow processCreate(RepositoryDataRow beforeCreateData);
	abstract protected RepositoryDataRow processRead(PrimaryKey pkey, RepositoryDataRow beforeReadData);
	abstract protected RepositoryDataRow processUpdate(PrimaryKey pkey, RepositoryDataRow beforeUpdateData);
	abstract protected RepositoryDataRow processDelete(PrimaryKey pkey, RepositoryDataRow beforeDeleteData);


	protected RepositoryDataRow afterCreate(PrimaryKey pkey, RepositoryDataRow processCreateData)
	{
		return setDataRow(processCreateData, CrudStateEnum.CREATE_AFTER);
	}


	protected RepositoryDataRow afterRead(PrimaryKey pkey, RepositoryDataRow processReadData)
	{
		return setDataRow(processReadData, CrudStateEnum.READ_AFTER);
	}


	protected RepositoryDataRow afterUpdate(PrimaryKey pkey, RepositoryDataRow processUpdateData)
	{
		return setDataRow(processUpdateData, CrudStateEnum.UPDATE_AFTER);
	}


	protected RepositoryDataRow afterDelete(PrimaryKey pkey, RepositoryDataRow processDeleteData)
	{
		return setDataRow(processDeleteData, CrudStateEnum.DELETE_AFTER);
	}


	protected RepositoryDataRow beforeCreate_mergeInitialData(RepositoryDataRow beforeCreateData, RepositoryDataRow initialData)
	{
		setState(CrudStateEnum.CREATE_MERGE_INITIAL_DATA);

		if (!(initialData instanceof RepositoryDataRow)) {
			throw new RuntimeException("Invalid initialData! it isn't instance of TableRow!");
		}

		if (beforeCreateData instanceof RepositoryDataRow) {
			// TODO: foreach(sloupce) -> pokud mam beforeCreateData && nemam initialData -> mrdnu to tam!
// initialData.is -> set | get
//			for(Entry<String, Object> e : beforeCreateData.entrySet()) {
//
//
//		}
//		if (isSet() && rowData instanceof TableRow) {
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


	protected void updatePrimaryKey(RepositoryDataRow data, CrudStateEnum state)
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
			pkey.setWhere(data);

		} catch (Exception e) {
			throw new RuntimeException("Unable to updatePrimaryKey() from TableRow data!", e);
		}
	}


	protected RepositoryDataRow setDataRow(RepositoryDataRow data, CrudStateEnum state)
	{
		dataRow   = data;
		return dataRow;
	}


	protected void setState(CrudStateEnum state)
	{
		crudState = state;
	}

	
	protected CrudStateEnum getState()
	{
		return crudState;
	}


	@Override
	public RepositoryDataRow Create(RepositoryDataRow data)
	{
		RepositoryDataRow beforeData = beforeCreate();
		RepositoryDataRow mergedData = null;
		if (beforeData instanceof RepositoryDataRow) {
			mergedData = beforeCreate_mergeInitialData(beforeData, data);
			if (mergedData instanceof RepositoryDataRow) {
				throw new RuntimeException("Invalid data! beforeCreate_mergeInitialData() returns different data than TableRow instance!");
			}
		}

		RepositoryDataRow processData = processCreate(mergedData);
		if (processData instanceof RepositoryDataRow) {
			updatePrimaryKey(processData, CrudStateEnum.CREATE_SET_PRIMARY_KEY);
			PrimaryKey pkey = getPrimaryKey();

			if (processData instanceof RepositoryDataRow) {
				if (pkey instanceof PrimaryKey && pkey.isSet()) {
					RepositoryDataRow afterData = afterCreate(getPrimaryKey(), processData);
					if (afterData instanceof RepositoryDataRow) {
						return afterData;
					}
					throw new RuntimeException("Invalid data! afterCreate() returns different data than TableRow instance!");
				}
				throw new RuntimeException("Invalid primary-key! processCreate_updatePrimaryKey() doesn't set correct PrimaryKey via: PrimaryKey.setWhere()!");
			}
			throw new RuntimeException("Invalid data! processCreate_updatePrimaryKey() returns different data than TableRow instance!");
		}
		throw new RuntimeException("Invalid data! processCreate() returns different data than TableRow instance!");
	}


	@Override
	public RepositoryDataRow Read()
	{
		return Read(getPrimaryKey());
	}


	@Override
	public RepositoryDataRow Read(PrimaryKey pkey)
	{
		if (pkey instanceof PrimaryKey) {
			if (!pkey.isSet()) {
				throw new RuntimeException("Invalid primary-key! PrimaryKey.setWhere() first!");
			}

			RepositoryDataRow beforeData  = beforeRead(pkey);
			RepositoryDataRow processData = processRead(pkey, beforeData);

			updatePrimaryKey(processData, CrudStateEnum.READ_CHECK_PRIMARY_KEY);
			if (processData instanceof RepositoryDataRow) {
				RepositoryDataRow afterData = afterRead(pkey, processData);
				if (afterData instanceof RepositoryDataRow) {
					return afterData;
				}
				throw new RuntimeException("Invalid data! afterRead() returns different data than TableRow instance!");
			}
			throw new RuntimeException("Invalid data! processRead() returns different data than TableRow instance!");
		}
		throw new RuntimeException("Invalid primary-key! it isn't PrimaryKey instance! try: PrimaryKey.set() first!");
	}


	@Override
	public RepositoryDataRow Update(RepositoryDataRow data)
	{
		return Update(getPrimaryKey(), data);
	}


	@Override
	public RepositoryDataRow Update(PrimaryKey pkey, RepositoryDataRow data)
	{
		if (pkey instanceof PrimaryKey) {
			if (!pkey.isSet()) {
				throw new RuntimeException("Invalid primary-key! PrimaryKey.setWhere() first!");
			}

			RepositoryDataRow beforeData  = beforeUpdate(pkey, data);
			RepositoryDataRow processData = processUpdate(pkey, beforeData);

			updatePrimaryKey(processData, CrudStateEnum.UPDATE_CHECK_PRIMARY_KEY);
			if (processData instanceof RepositoryDataRow) {
				RepositoryDataRow afterData = afterUpdate(pkey, processData);
				if (afterData instanceof RepositoryDataRow) {
					return afterData;
				}
				throw new RuntimeException("Invalid data! afterUpdate() returns different data than TableRow instance!");
			}
			throw new RuntimeException("Invalid data! processUpdate() returns different data than TableRow instance!");
		}
		throw new RuntimeException("Invalid primary-key! it isn't PrimaryKey instance! try: PrimaryKey.set() first!");
	}


	@Override
	public RepositoryDataRow Delete()
	{
		return Delete(getPrimaryKey());
	}


	@Override
	public RepositoryDataRow Delete(PrimaryKey pkey)
	{
		if (pkey instanceof PrimaryKey) {
			if (!pkey.isSet()) {
				throw new RuntimeException("Invalid primary-key! PrimaryKey.setWhere() first!");
			}

			RepositoryDataRow beforeData  = beforeDelete(pkey);
			RepositoryDataRow processData = processDelete(pkey, beforeData);

			updatePrimaryKey(processData, CrudStateEnum.DELETE_CHECK_PRIMARY_KEY);
			if (processData instanceof RepositoryDataRow) {
				RepositoryDataRow afterData = afterDelete(pkey, processData);
				if (afterData instanceof RepositoryDataRow) {
					return afterData;
				}
				throw new RuntimeException("Invalid data! afterDelete() returns different data than TableRow instance!");
			}
			throw new RuntimeException("Invalid data! processDelete() returns different data than TableRow instance!");
		}
		throw new RuntimeException("Invalid primary-key! it isn't PrimaryKey instance! try: PrimaryKey.set() first!");
	}


//
//	@Override
//	public DataSource Search()
//	{
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//	}
//
//	@Override
//	public ICRUD Exists()
//	{
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//	}


	protected void initializeTable()
	{
		initializeTable(false);
	}


	protected void initializeTable(boolean forceInitialization)
	{
		if ((getDataSource() instanceof DataSource && getTableInfo() instanceof StorageInfo)) {
			if (forceInitialization || !tableInfoInitialized) {
				StorageInfo tinfo = gatheringTableInfo();

				tableInfoInitialized = false;
				if (tinfo instanceof StorageInfo) {
					setTableInfo(tinfo);
					tableInfoInitialized = true;
				}
			}
		}
	}


	protected boolean isTableInitializated()
	{
		return tableInfoInitialized;
	}

	
	protected StorageInfo gatheringTableInfo()
	{
		// TODO: gathering TableInfo: PKEYs, Columnt, ...
		return null;
	}







}
