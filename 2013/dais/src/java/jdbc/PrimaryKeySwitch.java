package jdbc;

import model.dao.crud.pkey.IPrimaryKeySwitch;


public class PrimaryKeySwitch implements IPrimaryKeySwitch
{

	@Override
	public String toWhereSwitch(String pkName, Object pkType, Object pkValue)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}


}
