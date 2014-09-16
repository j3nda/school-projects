package OBSOLETE_jdbc;

import model.dao.crud.pkey.PrimaryKey;
import java.util.LinkedHashMap;


public class StorageInfo
{
	private String tableName;
	private PrimaryKey pkey;
	protected LinkedHashMap<String, Object> columns;


	public StorageInfo(String tableName)
	{
		this.tableName = tableName;
	}


	public StorageInfo(String tableName, PrimaryKey pkey)
	{
		this.tableName = tableName;
		this.pkey      = pkey;
	}


	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}


	public String getTableName()
	{
		return tableName;
	}

	
	public void setPrimaryKey(PrimaryKey pkey)
	{
		this.pkey = pkey;
	}


	public PrimaryKey getPrimaryKey()
	{
		return pkey;
	}


	public void setColumns(LinkedHashMap<String, Object> columns)
	{
		this.columns = columns;
	}


	public void addColumn(String name, Object type)
	{
		if (!(columns instanceof LinkedHashMap)) {
			columns = new LinkedHashMap<>();
		}
		columns.put(name, type);
	}

}
