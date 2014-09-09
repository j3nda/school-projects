package jdbc;

import java.util.LinkedHashMap;


public interface IPrimaryKey
{
	public IPrimaryKey get();
	public Object      getType(String pkName);
	public Object      getValue(String pkName);


	/**
	 * set primary key, like: id => INTEGER or name => VARCHAR(64) and so on...
	 * @param pkey
	 */
	public void set(LinkedHashMap<String, Object> pkey);


	/**
	 * set primary key and whereCondition, like: id => INTEGER or name => VARCHAR(64) and so on...
	 * @param pkey
	 * @param whereCondition 
	 */
	public void set(LinkedHashMap<String, Object> pkey, LinkedHashMap<String, Object> whereCondition);
	
	
	/**
	 * return true, if whole primary-key was set-up. otherwise return false
	 * @return 
	 */
	public boolean isSet();


	/**
	 * set where condition, like: id = 8 AND name = 'Jezevec'
	 * @param whereCondition 
	 */
	public void setWhere(LinkedHashMap<String, Object> whereCondition);
	public void setWhere(RepositoryDataRow rowData);


	public String toWhere();
	public String toWhere(LinkedHashMap<String, Object> whereCondition);


	/**
	 * check integrity of primary-ket from TableRow data columns
	 * @param data
	 * @return
	 */
	public boolean checkIntegrity(RepositoryDataRow data);


}
