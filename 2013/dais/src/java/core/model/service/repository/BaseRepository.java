package core.model.service.repository;


import core.model.entity.IEntity;
import core.model.service.repository.mapper.BaseMapper;
import core.model.service.repository.mapper.IBaseMapper;


public class BaseRepository implements IRepository
{
	private boolean initialized = false;
	private BaseMapper mapper = null;


	@Override
	public void initialize()
	{
		initialized = true;
	}


	protected boolean isInitialized()
	{
		return initialized;
	}


	protected void setMapper(BaseMapper mapper)
	{
		this.mapper = mapper;
	}


	protected BaseMapper getMapper()
	{
		if (mapper instanceof IBaseMapper) {
			return mapper;
		}
		throw new RuntimeException("Must setMapper() first! Optional in initialize() method.");
	}


	@Override
	public void setEntity(IEntity entity)
	{
		getMapper().setEntity(entity);
	}


	@Override
	public IEntity getEntity()
	{
		return getMapper().getEntity();
	}


	@Override
	public boolean Load()
	{
		return getMapper().Load();
	}


	@Override
	public boolean Save()
	{
		return getMapper().Save();
	}


	@Override
	public boolean Destroy()
	{
		return getMapper().Destroy();
	}


}
