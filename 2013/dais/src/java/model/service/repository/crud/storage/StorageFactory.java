package model.service.repository.crud.storage;


import core.model.service.repository.crud.CRUDSEL;
import core.model.service.repository.crud.PrimaryKey;
import core.model.service.repository.crud.storage.StorageInfo;
import javax.sql.DataSource;


public class StorageFactory
{
	protected DataSource dataSource;
	protected StorageFactoryType storageType = StorageFactoryType.UNDEFINED;
	protected StorageInfo storageInfo;


	public StorageFactory(DataSource dataSource, StorageFactoryType storageType)
	{
		this.dataSource  = dataSource;
		this.storageType = storageType;
	}


	public StorageFactory(DataSource dataSource, StorageFactoryType storageType, StorageInfo storageInfo)
	{
		this.dataSource  = dataSource;
		this.storageType = storageType;
		this.storageInfo = storageInfo;
	}


	public PrimaryKey getPrimaryKeyObjectInstance()
	{
		switch(storageType) {
			case MYSQL:  return new SqlMySqlPrimaryKey();
			case ORACLE: return new SqlOraclePrimaryKey();
			default:
				throw new RuntimeException("StorageFactoryType must be set!");
		}
	}


	public CRUDSEL getStorageObjectInstance()
	{
		switch(storageType) {
			case MYSQL:  return (SqlMySqlStorage) new SqlMySqlStorage (dataSource, storageInfo);
			case ORACLE: return (SqlOracleStorage)new SqlOracleStorage(dataSource, storageInfo);
			default:
				throw new RuntimeException("StorageFactoryType must be set!");
		}
	}


}
