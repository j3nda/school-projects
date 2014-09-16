package OBSOLETE_jdbc.crud;

import OBSOLETE_jdbc.ExtendedCRUD;
import model.dao.crud.pkey.PrimaryKey;
import OBSOLETE_jdbc.RepositoryDataRow;


public class SqlCRUD extends ExtendedCRUD
{

	@Override
	protected RepositoryDataRow processCreate(RepositoryDataRow beforeCreateData) {


					getTableName
						  getPrimaryKey
									getValuesToCreate
		INSERT INTO table () VALUES ();


		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected RepositoryDataRow processRead(PrimaryKey pkey, RepositoryDataRow beforeReadData) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected RepositoryDataRow processUpdate(PrimaryKey pkey, RepositoryDataRow beforeUpdateData) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected RepositoryDataRow processDelete(PrimaryKey pkey, RepositoryDataRow beforeDeleteData) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public RepositoryDataRow getDataRow() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
