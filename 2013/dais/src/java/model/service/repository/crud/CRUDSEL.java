package model.service.repository.crud;


import java.sql.ResultSet;


abstract public class CRUDSEL extends CRUDSE implements ICRUDSEL
{

	abstract protected ResultSet processList(ListConditions listConditions);


	@Override
	public ResultSet List(ListConditions listConditions)
	{
		if (listConditions instanceof ListConditions) {
			return processList(listConditions);
		}
		throw new RuntimeException("Invalid list-conditions! it isn't ListConditions instance! try: ListConditions.set() first!");
	}


	@Override
	abstract public ResultSet Query(String query);


}

