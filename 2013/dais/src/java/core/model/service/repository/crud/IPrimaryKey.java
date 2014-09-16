package core.model.service.repository.crud;

import core.model.service.repository.crud.storage.StorageDataRow;
import java.util.LinkedHashMap;


public interface IPrimaryKey
{
	public IPrimaryKey get();
	public Object      getType();
	public Object      getType(String name);
	public Object      getWhere();
	public Object      getWhere(String name);


	/**
	 * add primary key, like: id or name (pkType is set to null and can be gathered from StorageInfo)
	 * @param pkName
	 */
	public void add(String pkName);

	/**
	 * add primary key with type, like: id => INTEGER or name => VARCHAR(64)
	 * @param pkName
	 * @param pkType
	 */
	public void add(String pkName, Object pkType);


	/**
	 * add primary key with type and where condition, like: id => INTEGER == 8
	 * @param pkName
	 * @param pkType
	 * @param pkWhere
	 */
	public void add(String pkName, Object pkType, Object pkWhere);


	/**
	 * set primary key, like: id => INTEGER or name => VARCHAR(64) and so on...
	 * @param pkeyType
	 */
	public void setType(LinkedHashMap<String, Object> pkeyType);


	/**
	 * set where condition, like: id = 8 AND name = 'Jezevec', ...
	 * @param whereCondition 
	 */
	public void setWhere(LinkedHashMap<String, Object> whereCondition);


	/**
	 * set where condition values only, like: 8, 'Jezevec', ...
	 * @param rowData
	 */
	public void setWhereValues(StorageDataRow rowData);


	/**
	 * return true, if whole primary-key was set-up. otherwise return false
	 * (count(pkType) == count(pkWhere) && count(pkType) > 0 ? true : false)
	 * @return
	 */
	public boolean isSet();


	/**
	 * return string representation of whereCondition, like: id = 8 AND name = 'Jezevec'
	 * @return
	 */
	public String toWhere();



	/**
	 * check integrity of primary-key from StorageDataRow (~RowDataGateway) data columns
	 * @param data
	 * @return
	 */
	public boolean checkIntegrity(StorageDataRow data);


}
