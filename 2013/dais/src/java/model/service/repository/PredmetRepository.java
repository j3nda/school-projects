package model.service.repository;


import core.model.service.repository.BaseRepository;
import model.service.repository.mapper.PredmetMapper;


public class PredmetRepository extends BaseRepository
{


	@Override
	public void initialize()
	{
		setMapper(new PredmetMapper());

		super.initialize();
	}


}

