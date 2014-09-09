package jdbc;

import java.sql.ResultSet;
import jdbc.CRUD;
import model.dao.crud.pkey.PrimaryKey;
import jdbc.RepositoryDataRow;


public abstract class ExtendedCRUD extends CRUD
{
	private Object model;


	public ExtendedCRUD()
	{
		
	}


	@Override
	abstract protected RepositoryDataRow processCreate(RepositoryDataRow beforeCreateData);

	@Override
	abstract protected RepositoryDataRow processRead(PrimaryKey pkey, RepositoryDataRow beforeReadData);

	@Override
	abstract protected RepositoryDataRow processUpdate(PrimaryKey pkey, RepositoryDataRow beforeUpdateData);


	@Override
	abstract protected RepositoryDataRow processDelete(PrimaryKey pkey, RepositoryDataRow beforeDeleteData);


	public ResultSet Search()
	{
		return null;
	}


	public RepositoryDataRow Exists()
	{
		return null;
	}


}
