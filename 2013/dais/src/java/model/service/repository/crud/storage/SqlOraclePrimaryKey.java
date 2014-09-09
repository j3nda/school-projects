package model.service.repository.crud.storage;


import java.util.LinkedHashMap;
import model.service.repository.crud.PrimaryKey;


public class SqlOraclePrimaryKey extends PrimaryKey
{
	

	public SqlOraclePrimaryKey()
	{
	}


	public SqlOraclePrimaryKey(LinkedHashMap<String, Object> pkey)
	{
		super(pkey);
	}


	public SqlOraclePrimaryKey(LinkedHashMap<String, Object> pkey, LinkedHashMap<String, Object> whereCondition)
	{
		super(pkey, whereCondition);
	}


}

