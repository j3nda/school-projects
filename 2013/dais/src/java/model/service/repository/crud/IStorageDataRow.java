package model.service.repository.crud;


public interface IStorageDataRow
{

	/**
	 * set link between: storage-key-name x storage-value
	 * @param name
	 * @param value
	 */
	public void set(String name, Object value);


	/**
	 * get storage-value object for specified storage-key-name
	 * @param name
	 * @return
	 */
	public Object get(String name);


	/**
	 * return true, if storage-key-name already exists, otherwise return false
	 * @param name
	 * @return
	 */
	public boolean is(String name);


	/**
	 * return true, if storage-key structure is empty, otherwise return false
	 * @return 
	 */
	public boolean isEmpty();

}

