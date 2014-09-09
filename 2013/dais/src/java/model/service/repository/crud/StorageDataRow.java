package model.service.repository.crud;

import java.util.LinkedHashMap;


public class StorageDataRow implements IStorageDataRow
{
	private LinkedHashMap<String, Object> data;


	public StorageDataRow()
	{
		data = new LinkedHashMap<>();
	}


	@Override
	public void set(String name, Object value)
	{
		data.put(name, value);
	}


	@Override
	public Object get(String name)
	{
		return data.get(name);
	}


	@Override
	public boolean is(String name)
	{
		return data.containsKey(name);
	}


	@Override
	public boolean isEmpty()
	{
		return data.isEmpty();
	}


}
