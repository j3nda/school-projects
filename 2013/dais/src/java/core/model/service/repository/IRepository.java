package core.model.service.repository;

import core.model.entity.IEntity;


public interface IRepository
{
	public void initialize();

	public void setEntity(IEntity entity);
	public IEntity getEntity();

	public boolean Load();
	public boolean Save();
	public boolean Destroy();


	// ???RepositoryCollection???
//	public boolean Search(); --> ??find??
//	public boolean List();
//	public boolean Exists();


}
