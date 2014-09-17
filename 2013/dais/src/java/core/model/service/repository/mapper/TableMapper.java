package core.model.service.repository.mapper;


import core.model.entity.IEntity;
import core.model.service.repository.crud.storage.StorageDataRow;


public class TableMapper extends BaseMapper // TODO: automatic mapper 1:1 ~ Entity <=> TableMapper <=> Sql-Table
{

	@Override
	protected StorageDataRow mapEntityToCRUD(String name) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected StorageDataRow mapEntityToCRUD_asPrimaryKey(String name) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected IEntity mapCrudToEntity(String name) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
