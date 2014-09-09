package jdbc;

import model.dao.crud.pkey.IPrimaryKeySwitch;
import model.dao.crud.pkey.PrimaryKeySwitch;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


public abstract class PrimaryKey implements IPrimaryKey, IPrimaryKeySwitch
{
	protected LinkedHashMap<String, Object> pkey;           // column info
	protected LinkedHashMap<String, Object> whereCondition; // pkey values

	
	public PrimaryKey(LinkedHashMap<String, Object> pkey)
	{
		this.pkey = pkey;
	}


	public PrimaryKey(LinkedHashMap<String, Object> pkey, LinkedHashMap<String, Object> whereCondition)
	{
		this.pkey = pkey;
		this.whereCondition = whereCondition;
	}


	@Override
	public IPrimaryKey get()
	{
		if (pkey instanceof IPrimaryKey) {
			return this;
		}
		throw new RuntimeException("Set PrimaryKey(s) first!");
	}


	@Override
	public Object getType(String pkName)
	{
		if (pkey instanceof IPrimaryKey) {
			return this.pkey.get(pkName);
		}
		throw new RuntimeException("Set PrimaryKey(s) first!");
	}


	@Override
	public Object getValue(String pkName)
	{
		if (whereCondition instanceof IPrimaryKey) {
			return this.whereCondition.get(pkName);
		}
		throw new RuntimeException("Set PrimaryKey(s) first!");
	}


	@Override
	public void set(LinkedHashMap<String, Object> pkey)
	{
		this.pkey = pkey;
	}


	@Override
	public void set(LinkedHashMap<String, Object> pkey, LinkedHashMap<String, Object> whereCondition)
	{
		set(pkey);
		setWhere(whereCondition);
	}


	@Override
	public boolean isSet()
	{
		int pkTypeCount  = 0;
		int pkValueCount = 0;

		for(Entry<String, Object> e : pkey.entrySet()) {
			if (isSetType(e.getKey())) {
				pkTypeCount++;
			}

			if (isSetValue(e.getKey())) {
				pkValueCount++;
			}
		}

		return (pkTypeCount > 0 && pkTypeCount == pkValueCount ? true : false);
	}


	private boolean isSetType(String pkName)
	{
		if (pkey instanceof LinkedHashMap && pkey.containsKey(pkName)) {
			Object pkType = pkey.get(pkName);
			if (pkType instanceof Object) {
				return true;
			}
		}
		return false;
	}


	private boolean isSetValue(String pkName)
	{
		if (whereCondition instanceof LinkedHashMap && whereCondition.containsKey(pkName)) {
			Object pkValue = whereCondition.get(pkName);
			if (pkValue instanceof Object) {
				return true;
			}
		}
		return false;
	}


	@Override
	public void setWhere(LinkedHashMap<String, Object> whereCondition)
	{
		this.whereCondition = whereCondition;
	}


	@Override
	public void setWhere(RepositoryDataRow rowData)
	{
		whereCondition.clear();
		for(Entry<String, Object> e : pkey.entrySet()) {
			if (!rowData.is(e.getKey())) {
				throw new RuntimeException("Unable update primary-key via setWhere()! rowData hasn't primary-key's column value!");
			}
			whereCondition.put(e.getKey(), rowData.get(e.getKey()));
		}
	}


	@Override
	public String toWhere()
	{
		return toWhere(whereCondition);
	}


	@Override
	public String toWhere(LinkedHashMap<String, Object> whereCondition)
	{
		String where  = "";
		Object pkType = null;
		for(Entry<String, Object> e : whereCondition.entrySet()) {
			pkType = this.pkey.get(e.getKey());
			where += toWhereSwitch(e.getKey(), pkType, e.getValue());
		}
//			System.out.printf("%s, %s\n", e.getKey(), e.getValue());
		return where;
	}


	@Override
	public String toWhereSwitch(String pkName, Object pkType, Object whereConditionObject)
	{
		try {
			switch(whereConditionObject.getClass().getName().toLowerCase()) {
// TODO: int,integer, char, varchar, string, in aj...
// int, integer
// char, varchar, string
// in

				default:
					if (whereConditionObject instanceof IPrimaryKeySwitch) {
						PrimaryKeySwitch pkSwitch = (PrimaryKeySwitch)whereConditionObject;
						return pkSwitch.toWhereSwitch(pkName, pkType, whereConditionObject);
					}
					throw new RuntimeException("whereConditionObject isn't implementing IPrimaryKeySwitch!");
			}

		} catch (Exception e) {
			throw new RuntimeException("Undefined whereConditionObject's type in switch() case!", e);
		}
	}


	@Override
	public boolean checkIntegrity(RepositoryDataRow rowData)
	{
		if (isSet() && rowData instanceof RepositoryDataRow) {
			int pkDataCount = 0;
			for(Entry<String, Object> e : pkey.entrySet()) {
				Object pkValue = whereCondition.get(e.getKey());

				if (rowData.is(e.getKey())) {
					Object pkData = rowData.get(e.getKey());

					if (pkValue.equals(pkData)) {
						pkDataCount++;
					}
				}
			}

			return (pkey.size() == pkDataCount ? true : false);
		}
		return false;
	}
	

}
