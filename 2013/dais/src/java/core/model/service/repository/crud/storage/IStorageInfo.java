package core.model.service.repository.crud.storage;


import core.model.service.repository.crud.PrimaryKey;
import java.util.LinkedHashMap;


public interface IStorageInfo
{
	public void setName(String name);
	public String getName();
	public void setPrimaryKey(PrimaryKey pkey);
	public PrimaryKey getPrimaryKey();
	public void setColumns(LinkedHashMap<String, Object> columns);
	public void addColumn(String name, Object type);

	public LinkedHashMap<String, Object> getColumns();
}

