package model.service.repository.crud;


import java.util.LinkedHashMap;

public class OrderConditions implements IOrderConditions
{
	private LinkedHashMap<String, Boolean> data;


	public OrderConditions()
	{
		data = new LinkedHashMap<>();
	}


	@Override
	public void set(String name, boolean descending)
	{
		data.put(name, descending);
	}


//	@Override
//	public Object get(String name)
//	{
//		return data.get(name);
//	}

	
	@Override
	public boolean asc(String name)
	{
		if (is(name)) {
			return !data.get(name);
		}
		return false;
	}
	
	
	@Override
	public boolean desc(String name)
	{
		if (is(name)) {
			return data.get(name);
		}
		return false;
	}
	

	@Override
	public boolean is(String name)
	{
		return data.containsKey(name);
	}


}

