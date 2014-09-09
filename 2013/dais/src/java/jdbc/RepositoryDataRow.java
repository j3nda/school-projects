package jdbc;

import java.util.LinkedHashMap;


public class RepositoryDataRow
{
	private LinkedHashMap<String, Object> data;


	public RepositoryDataRow()
	{
		data = new LinkedHashMap<>();
	}


	public void set(String name, Object value)
	{
		data.put(name, value);
	}


	public Object get(String name)
	{
		return data.get(name);
	}


	public boolean is(String name)
	{
		return data.containsKey(name);
	}

}
