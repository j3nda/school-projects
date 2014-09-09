package model.service.repository.crud;


import java.util.LinkedHashMap;


public class StorageInfo implements IStorageInfo
{
	private   String     name;
	private   PrimaryKey pkey;
	protected LinkedHashMap<String, Object> columns;


	public StorageInfo(String tableName)
	{
		this.name = tableName;
	}


	public StorageInfo(String tableName, PrimaryKey pkey)
	{
		this.name = tableName;
		this.pkey      = pkey;
	}


	@Override
	public void setName(String name)
	{
		this.name = this.name;
	}


	@Override
	public String getName()
	{
		return name;
	}

	
	@Override
	public void setPrimaryKey(PrimaryKey pkey)
	{
		this.pkey = pkey;
	}


	@Override
	public PrimaryKey getPrimaryKey()
	{
		return pkey;
	}


	@Override
	public void setColumns(LinkedHashMap<String, Object> columns)
	{
		this.columns = columns;
	}


	@Override
	public void addColumn(String name, Object type)
	{
		if (!(columns instanceof LinkedHashMap)) {
			columns = new LinkedHashMap<>();
		}
		columns.put(name, type);
	}


	@Override
	public LinkedHashMap<String, Object> getColumns()
	{
		if (columns instanceof LinkedHashMap) {
			return columns;
		}
		throw new RuntimeException("columns isn't initializated! Try setColumns() or addColumn() first!");
	}


}
