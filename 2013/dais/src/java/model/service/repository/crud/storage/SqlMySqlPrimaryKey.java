package model.service.repository.crud.storage;


import java.util.LinkedHashMap;
import core.model.service.repository.crud.PrimaryKey;


public class SqlMySqlPrimaryKey extends PrimaryKey
{
	

	public SqlMySqlPrimaryKey()
	{
	}


	public SqlMySqlPrimaryKey(LinkedHashMap<String, Object> pkey)
	{
		super(pkey);
	}


	public SqlMySqlPrimaryKey(LinkedHashMap<String, Object> pkey, LinkedHashMap<String, Object> whereCondition)
	{
		super(pkey, whereCondition);
	}


}

