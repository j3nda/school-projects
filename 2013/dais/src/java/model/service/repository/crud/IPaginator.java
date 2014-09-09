package model.service.repository.crud;


public interface IPaginator
{
	public void setPage(int page);
	public void setPageLimit(int limit);
	public int  getPage();
	public int  getPageLimit();

	public int  getOffsetStart();
	public int  getOffsetEnd();
	public int  getMaxRecords();
	public int  getMaxPage();

	public void setOrderConditions(OrderConditions orderConditions);
	public OrderConditions getOrderConditions();
}

