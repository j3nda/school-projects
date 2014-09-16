package model.service.repository.mapper;


import core.model.service.repository.crud.CRUDSEL;
import core.model.service.repository.crud.PrimaryKey;
import core.model.service.repository.crud.storage.StorageDataRow;
import core.model.service.repository.crud.storage.StorageInfo;
import javax.sql.DataSource;
import model.entity.PredmetEntity;
import model.service.repository.crud.storage.StorageFactory;
import resources.ResourceManager;


public class PredmetMapper extends BaseMapper
{


	@Override
	public void initialize()
	{
		StorageFactory storage = new StorageFactory((DataSource)ResourceManager.getSqlDataSource(), ResourceManager.getSqlDataSourceType());

		PrimaryKey pkey = storage.getPrimaryKeyObjectInstance();
		pkey.add("id");

		CRUDSEL crud = storage.getStorageObjectInstance();
		crud.setStorageInfo(new StorageInfo("predmet", pkey));

		setCRUD(crud);

// TODO: remove thid death-code...
//		setCRUD(
//			new SqlOracleStorage(
//				(DataSource)ResourceManager.getSqlDataSource(),
//				new StorageInfo("predmet", pk)
//			)
//		);

		super.initialize();
	}


	@Override
	protected StorageDataRow mapEntityToCRUD(String name)
	{
/*
		Entity --> CRUD --> SQL-COLUMNS
		id					id
		nazev				nazev
*/
		PredmetEntity entity   = (PredmetEntity)getEntity();
		StorageDataRow dataRow = new StorageDataRow();

		dataRow.set("id",    (Integer)entity.getId());
		dataRow.set("nazev", (String)entity.getNazev());

		return dataRow;
	}


	@Override
	protected StorageDataRow mapEntityToCRUD_primaryKey(String name)
	{
		PredmetEntity entity   = (PredmetEntity)getEntity();
		StorageDataRow dataRow = new StorageDataRow();

		dataRow.set("id", (Integer)entity.getId()); // TODO: auto_increment due DB-SERVER (MySQL, Oracle, ...)

		return dataRow;
	}


	@Override
	protected PredmetEntity mapCrudToEntity(String name)
	{
		PredmetEntity entity   = new PredmetEntity();
		StorageDataRow dataRow = crud.getDataRow();

		entity.setId((Integer)dataRow.get("id"));
		entity.setNazev((String)dataRow.get("nazev"));

		return entity;
	}



}
