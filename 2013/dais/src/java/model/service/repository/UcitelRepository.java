package model.service.repository;


import core.model.service.repository.BaseRepository;
import model.service.repository.mapper.UcitelMapper;


public class UcitelRepository extends BaseRepository
{


	@Override
	public void initialize()
	{
		setMapper(new UcitelMapper());

		super.initialize();
	}


}

