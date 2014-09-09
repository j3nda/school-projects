package model.service.repository.mapper;


import model.entity.IEntity;
import model.service.repository.crud.ICRUD;


public interface IBaseMapper extends IMapper
{
	public void initialize();
	public boolean isInitialized();
	public void setEntity(IEntity entity);
	public void setCRUD(ICRUD crud);
	public IEntity getEntity();
}

