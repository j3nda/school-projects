package core.model.service.repository.mapper;


import core.model.entity.IEntity;
import core.model.service.repository.crud.CRUD;
import core.model.service.repository.crud.CRUDSE;
import core.model.service.repository.crud.CRUDSEL;
import core.model.service.repository.crud.ICRUD;
import core.model.service.repository.crud.ICRUDSE;
import core.model.service.repository.crud.StateCRUD;
import core.model.service.repository.crud.storage.StorageDataRow;


abstract public class BaseMapper implements IBaseMapper
{
	private   boolean initialized = false;
	protected IEntity entity;
	protected ICRUD   crud;


	@Override
	public void initialize()
	{
		if (!isInitialized()) {
			if (!(crud instanceof ICRUD)) {
				throw new RuntimeException("Must setBaseCRUD() first!");
			}

			if (!(entity instanceof IEntity)) {
				throw new RuntimeException("Must setBaseEntity() first!");
			}

			initialized = true;
		}
	}


	@Override
	public boolean isInitialized()
	{
		return initialized;
	}




	/**
	 * return true,  if data exists and load is successfull
	 * return false, if data doesn't exists and entity.reset()
	 * @param crud
	 * @param entity
	 * @return
	 */
	protected boolean processLoad(ICRUD crud, IEntity entity)
	{
		crud.getPrimaryKey().setWhereValues(mapEntityToCRUD_primaryKey());

		if (crud instanceof ICRUDSE) {
			CRUDSE crud_se = (CRUDSE)crud;
			if (crud_se.Exists()) {
				if (crud_se.getState() == StateCRUD.READ) {
					mapCrudToEntity(entity.getClass().getName());
					return true;
				}
				throw new RuntimeException("processLoad() failed! data exists, but invalid invalid state detected!");

			} else {
				entity.reset();
				return false;
			}

		} else {
			CRUD crud_gs = (CRUD)crud;
			crud_gs.Read();
			if (crud_gs.getState() == StateCRUD.READ) {
				mapCrudToEntity(entity.getClass().getName());
				return true;

			} else if (crud_gs.getState() == StateCRUD.NONE) {
				entity.reset();
				return false;
			}
		}
		throw new RuntimeException("processLoad() failed!");
	}


	/**
	 * return true, if data saved successfully, otherwise returns false (or throw exception)
	 * @param crud
	 * @param entity
	 * @return
	 */
	protected boolean processSave(ICRUD crud, IEntity entity)
	{
		CRUD crud_gs = (CRUD)crud;
		if (
			crud_gs.getState() == StateCRUD.CREATED ||
			(
			 crud_gs.getState().ordinal() >= StateCRUD.CREATE_AFTER.ordinal() &&
			 crud_gs.getState().ordinal() <  StateCRUD.DELETED.ordinal()
			)
		   ) {

			crud_gs.Update(mapEntityToCRUD(entity.getClass().getName()));
			mapCrudToEntity(entity.getClass().getName());
			return true;

		} else if (crud_gs.getState() == StateCRUD.DELETED) {
			crud_gs.Create(mapEntityToCRUD(entity.getClass().getName()));
			mapCrudToEntity(entity.getClass().getName());
			return true;

		} else if (crud_gs.getState() == StateCRUD.NONE) {

			// already exists? Y/N/error

			if (crud instanceof ICRUDSE) {
				CRUDSE crud_se = (CRUDSE)crud;
				if (crud_se.Exists()) {
					crud_se.Update(mapEntityToCRUD(entity.getClass().getName()));
					mapCrudToEntity(entity.getClass().getName());
					return true;

				} else {
					crud_se.Create(mapEntityToCRUD(entity.getClass().getName()));
					mapCrudToEntity(entity.getClass().getName());
					return true;
				}

			} else {
				crud_gs.Read();
				if (crud_gs.getState() == StateCRUD.READ) {
					crud_gs.Update(mapEntityToCRUD(entity.getClass().getName()));
					mapCrudToEntity(entity.getClass().getName());
					return true;

				} else if (crud_gs.getState() == StateCRUD.NONE) {
					crud_gs.Create(mapEntityToCRUD(entity.getClass().getName()));
					mapCrudToEntity(entity.getClass().getName());
					return true;
				}
			}
		}
		throw new RuntimeException("processSave() failed!");
	}


	/**
	 * return true, if data destroyed successfuly, otherwise return false
	 * @param crud
	 * @param entity
	 * @return
	 */
	protected boolean processDestroy(ICRUD crud, IEntity entity)
	{
		CRUD crud_gs = (CRUD)crud;
		if (
			crud_gs.getState() == StateCRUD.CREATED ||
			(
			 crud_gs.getState().ordinal() >= StateCRUD.CREATE_AFTER.ordinal() &&
			 crud_gs.getState().ordinal() <  StateCRUD.DELETED.ordinal()
			)
		   ) {

			crud_gs.Delete();
			mapCrudToEntity(entity.getClass().getName());
			return true;

		} else if (crud_gs.getState() == StateCRUD.DELETED) {
			return true;

		} else if (crud_gs.getState() == StateCRUD.NONE) {

			// already exists? Y/N/error

			if (crud instanceof ICRUDSE) {
				CRUDSE crud_se = (CRUDSE)crud;
				if (crud_se.Exists()) {
					crud_gs.Delete();
					mapCrudToEntity(entity.getClass().getName());
					return true;

				} else {
					return false;
				}

			} else {
				crud_gs.Read();
				if (crud_gs.getState() == StateCRUD.READ) {
					crud_gs.Delete();
					mapCrudToEntity(entity.getClass().getName());
					return true;

				} else if (crud_gs.getState() == StateCRUD.NONE) {
					return false;
				}
			}
		}
		throw new RuntimeException("processDestroy() failed!");
	}


	@Override
	public boolean Load()
	{
		initialize();
		return processLoad(crud, getEntity());
	}


	@Override
	public boolean Save()
	{
		initialize();
		return processSave(crud, getEntity());
	}



	@Override
	public boolean Destroy()
	{
		initialize();
		return processDestroy(crud, getEntity());
	}


	@Override
	public void setEntity(IEntity entity)
	{
		this.entity = entity;
	}


	@Override
	public void setCRUD(ICRUD crud)
	{
		this.crud = (CRUDSEL)crud;
	}


	@Override
	public IEntity getEntity()
	{
		return entity;
	}


	abstract protected StorageDataRow mapEntityToCRUD(String name);
	abstract protected StorageDataRow mapEntityToCRUD_primaryKey(String name);
	abstract protected IEntity mapCrudToEntity(String name);



	protected StorageDataRow mapEntityToCRUD()
	{
		return mapEntityToCRUD(getEntity().getClass().toString());
	}


	protected StorageDataRow mapEntityToCRUD_primaryKey()
	{
		return mapEntityToCRUD(getEntity().getClass().toString());
	}


	protected IEntity mapCrudToEntity()
	{
		return mapCrudToEntity(getEntity().getClass().toString());
	}


}

