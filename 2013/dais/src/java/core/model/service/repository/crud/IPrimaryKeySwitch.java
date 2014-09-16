package core.model.service.repository.crud;


public interface IPrimaryKeySwitch
{
	/**
	 * return string as 1x SQL-WHERE condition, like: (`id`=123) or (`name`='Jezevcik') and so on...
	 * @param pkName
	 * @param pkType
	 * @param pkValue
	 * @return
	 */
	public String toWhereSwitch(String pkName, Object pkType, Object pkValue);
}
