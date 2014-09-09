package model.service.repository.crud;


public interface IOrderConditions
{
	public void set(String name, boolean descending); // TODO: better behavior for: a-z, z-a, firstDirectories, ... instead of boolean use Object!
//	public Object get(String name);

	public boolean asc(String name);
	public boolean desc(String name);


	/**
	 * return true, if order-key-name already exists, otherwise return false
	 * @param name
	 * @return
	 */
	public boolean is(String name);


}

