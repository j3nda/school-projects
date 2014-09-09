package model.service.repository.crud;


import java.util.LinkedHashMap;
import java.util.Map.Entry;


abstract public class PrimaryKey implements IPrimaryKey, IPrimaryKeySwitch
{
	protected LinkedHashMap<String, Object> pkey;           // column info
	protected LinkedHashMap<String, Object> whereCondition; // pkey values
	private boolean initialized = false;


	public PrimaryKey()
	{
	}


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
	public void add(String pkName)
	{
		add(pkName, null, null);
	}


	@Override
	public void add(String pkName, Object pkType)
	{
		add(pkName, pkType, null);
	}


	@Override
	public void add(String pkName, Object pkType, Object pkWhere)
	{
		initialize();
		pkey.put(pkName, pkType);
		whereCondition.put(pkName, pkWhere);
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
	public Object getType()
	{
		if (pkey instanceof IPrimaryKey) {
			return this.pkey;
		}
		throw new RuntimeException("Set PrimaryKey(s) first!");
	}


	@Override
	public Object getType(String name)
	{
		if (pkey instanceof IPrimaryKey) {
			return this.pkey.get(name);
		}
		throw new RuntimeException("Set PrimaryKey(s) first!");
	}


	@Override
	public Object getWhere()
	{
		if (whereCondition instanceof IPrimaryKey) {
			return this.whereCondition;
		}
		throw new RuntimeException("Set PrimaryKey(s) first!");
	}


	@Override
	public Object getWhere(String name)
	{
		if (whereCondition instanceof IPrimaryKey) {
			return this.whereCondition.get(name);
		}
		throw new RuntimeException("Set PrimaryKey(s) first!");
	}


	@Override
	public void setType(LinkedHashMap<String, Object> pkeyType)
	{
		this.pkey = pkey;
	}


	@Override
	public void setWhere(LinkedHashMap<String, Object> whereCondition)
	{
		this.whereCondition = whereCondition;
	}


	@Override
	public void setWhereValues(StorageDataRow rowData)
	{
		whereCondition.clear();
		for(Entry<String, Object> e : pkey.entrySet()) {
			if (!rowData.is(e.getKey())) {
				throw new RuntimeException("Unable update primary-key, key="+e.getKey()+" via setWhereValues()! rowData hasn't primary-key's column value!");
			}
			whereCondition.put(e.getKey(), rowData.get(e.getKey()));
		}
	}


	@Override
	public boolean isSet()
	{
		int pkTypeCount  = 0;
		int pkWhereCount = 0;

		for(Entry<String, Object> e : pkey.entrySet()) {
			if (isSetType(e.getKey())) {
				pkTypeCount++;
			}

			if (isSetWhere(e.getKey())) {
				pkWhereCount++;
			}
		}

		return (pkTypeCount > 0 && pkTypeCount == pkWhereCount ? true : false);
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


	private boolean isSetWhere(String pkName)
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
	public String toWhere()
	{
		return toWhere(whereCondition);
	}


	protected String toWhere(LinkedHashMap<String, Object> whereCondition)
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
	public boolean checkIntegrity(StorageDataRow data)
	{
		if (isSet() && data instanceof StorageDataRow) {
			int pkDataCount = 0;
			for(Entry<String, Object> e : pkey.entrySet()) {
				Object pkValue = whereCondition.get(e.getKey());

				if (data.is(e.getKey())) {
					Object pkData = data.get(e.getKey());

					if (pkValue.equals(pkData)) {
						pkDataCount++;
					}
				}
			}

			return (pkey.size() == pkDataCount ? true : false);
		}
		return false;
	}


	protected void initialize()
	{
		if (!isInitializated()) {
			if (!(pkey instanceof LinkedHashMap)) {
				pkey = new LinkedHashMap<>();
			}
			if (!(whereCondition instanceof LinkedHashMap)) {
				whereCondition = new LinkedHashMap<>();
			}
			initialized = true;
		}
	}

	protected boolean isInitializated()
	{
		return initialized;
	}


}
