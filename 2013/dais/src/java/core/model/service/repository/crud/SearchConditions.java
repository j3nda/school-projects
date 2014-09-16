package core.model.service.repository.crud;


import java.util.LinkedHashMap;


public class SearchConditions implements ISearchConditions
{
	private LinkedHashMap<String, Object> data;


	public SearchConditions()
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
	public boolean isSet()
	{
		return !data.isEmpty();
	}


}

