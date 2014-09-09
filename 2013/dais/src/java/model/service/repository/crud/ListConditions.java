package model.service.repository.crud;


public class ListConditions extends SearchConditions implements IListConditions
{
	private Paginator paginator;


	@Override
	public void setPaginator(Paginator paginator)
	{
		this.paginator = paginator;
	}


	@Override
	public Paginator getPaginator()
	{
		if (paginator instanceof Paginator) {
			return paginator;
		}
		throw new RuntimeException("Must setPaginator() first!");
	}


}

