package model.service.repository.mapper;


import javax.sql.DataSource;
import model.entity.PredmetEntity;
import model.service.repository.crud.StorageDataRow;
import model.service.repository.crud.StorageInfo;
import model.service.repository.crud.storage.SqlOraclePrimaryKey;
import model.service.repository.crud.storage.SqlOracleStorage;
import resources.ResourceManager;


public class PredmetMapper extends BaseMapper
{


	@Override
	public void initialize()
	{
		SqlOraclePrimaryKey pk = new SqlOraclePrimaryKey();
		pk.add("id");

		setCRUD(
			new SqlOracleStorage(
				(DataSource)ResourceManager.getSqlDataSource(),
				new StorageInfo("predmet", pk)
			)
		);

		super.initialize();
	}


	@Override
	protected StorageDataRow mapEntityToCRUD(String name)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	@Override
	protected StorageDataRow mapEntityToCRUD_primaryKey(String name)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	@Override
	protected PredmetEntity mapCrudToEntity(String name)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}





}
