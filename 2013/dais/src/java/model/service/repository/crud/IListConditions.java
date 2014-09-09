package model.service.repository.crud;


public interface IListConditions extends ISearchConditions
{
	public void setPaginator(Paginator paginator);
	public Paginator getPaginator();
}

