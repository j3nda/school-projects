package model.service.repository.crud;


public interface ISearchConditions
{

	/**
	 * set link between: storage-key-name x search-value-in-storage
	 * @param name
	 * @param value
	 */
	public void set(String name, Object value);


	/**
	 * get search-value object for specified storage-key-name
	 * @param name
	 * @return
	 */
	public Object get(String name);


	/**
	 * return true, if search-storage-key-name already exists, otherwise return false
	 * @param name
	 * @return
	 */
	public boolean is(String name);


	/**
	 * return true if one or more searchConditions are set, otherwise returns false
	 * @return
	 */
	public boolean isSet();


}

