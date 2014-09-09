package model.service.repository.crud;


public class Paginator implements IPaginator
{
	private int page;
	private int pageLimit;
	private OrderConditions orderConditions;

	private int pageMax;
	private int offsetStart;
	private int offsetEnd;
	private int offsetMax;
	private boolean initialized = false;


	@Override
	public void setPage(int page)
	{
		this.page = (page <= 0 ? 1 : page);
	}


	@Override
	public void setPageLimit(int limit)
	{
		this.pageLimit = (limit <= 0 ? -1 : limit);
	}


	@Override
	public int getPage()
	{
		return page;
	}


	@Override
	public int getPageLimit()
	{
		initialize(false);
		return pageLimit;
	}


	@Override
	public int getOffsetStart()
	{
		int pageLimit = getPageLimit();
		if (pageLimit > 0) {
			return (getPage() -1) * pageLimit;
		}
		return 0;
	}


	@Override
	public int getOffsetEnd()
	{
		int pageLimit = getPageLimit();
		if (pageLimit > 0) {
			return getPage() * pageLimit;
		}
		return getMaxRecords();
	}


	@Override
	public int getMaxRecords()
	{
		throw new UnsupportedOperationException("Not supported yet. Just connect to gatheringThisInformationFromStorage!");
	}


	@Override
	public int getMaxPage()
	{
		initialize(false);
		return pageMax;
	}


	@Override
	public void setOrderConditions(OrderConditions orderConditions)
	{
		this.orderConditions = orderConditions;
	}


	@Override
	public OrderConditions getOrderConditions()
	{
		if (orderConditions instanceof OrderConditions) {
			return orderConditions;
		}
		throw new UnsupportedOperationException("Must setOrderConditions() first!");
	}


	protected void initialize(boolean forceInitialization)
	{
		if (forceInitialization || !isInitializated()) {
			if (page == 0) {
				throw new RuntimeException("Must setPage() first!");
			}
			if (pageLimit == 0) {
				throw new RuntimeException("Must setPageLimit() first!");
			}

			offsetMax = 999999; // TODO
			if (pageLimit > 0) {
				pageMax = (int)Math.ceil((double)offsetMax / (double)pageLimit);

			} else {
				pageMax = 1;
			}
			initialized = true;
		}
	}


	protected boolean isInitializated()
	{
		return initialized;
	}

	
}

