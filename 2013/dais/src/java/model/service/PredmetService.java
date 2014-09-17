package model.service;


import model.entity.PredmetEntity;
import model.service.repository.PredmetRepository;


public class PredmetService // TODO: extends BaseService implements IService + TODO: findByPrimaryKey(...shit...)
{


	public PredmetEntity findById(Integer id)
	{
		PredmetEntity entity = new PredmetEntity();
		entity.setId(id);

		PredmetRepository repo = new PredmetRepository();
		repo.initialize();
		repo.setEntity(entity);

		if (repo.Load()) {
			return (PredmetEntity)repo.getEntity();

		} else {
			return null;
		}
	}


}

