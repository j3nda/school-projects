package core.model.service.repository.crud;


public class PrimaryKeySwitch implements IPrimaryKeySwitch
{

	@Override
	public String toWhereSwitch(String pkName, Object pkType, Object pkValue)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}


}
