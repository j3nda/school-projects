package model.service.repository.crud.storage;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import model.service.repository.crud.CRUDSEL;
import model.service.repository.crud.ListConditions;
import model.service.repository.crud.PrimaryKey;
import model.service.repository.crud.SearchConditions;
import model.service.repository.crud.StorageDataRow;
import model.service.repository.crud.StorageInfo;


public class SqlOracleStorage extends CRUDSEL
{
	public SqlOracleStorage(DataSource dataSource, StorageInfo storageInfo)
	{
		setDataSource(dataSource);
		setStorageInfo(storageInfo);
	}


	@Override
	protected StorageDataRow processCreate(StorageDataRow beforeCreateData)
	{

		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		// TODO: getStorageInfo().getColumns() iterate -> create SQL -> query -> execute
	}


	@Override
	protected StorageDataRow processRead(PrimaryKey pkey, StorageDataRow beforeReadData)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	@Override
	protected StorageDataRow processUpdate(PrimaryKey pkey, StorageDataRow beforeUpdateData)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	@Override
	protected StorageDataRow processDelete(PrimaryKey pkey, StorageDataRow beforeDeleteData)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	@Override
	protected LinkedList<StorageDataRow> processSearch(SearchConditions searchConditions)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	@Override
	protected ResultSet processList(ListConditions listConditions)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	@Override
	public LinkedList<StorageDataRow> Search()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	@Override
	public ResultSet Query(String query)
	{
            try {
                PreparedStatement ps = getDataSource().getConnection().prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                return rs;
            } catch (SQLException ex) {
                Logger.getLogger(SqlOracleStorage.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
	}

	public int ExecuteUpdate(String query)
	{
            try {
                PreparedStatement ps = getDataSource().getConnection().prepareStatement(query);
                int rowCount = ps.executeUpdate();
                return rowCount;
           } catch (SQLException ex) {
                Logger.getLogger(SqlOracleStorage.class.getName()).log(Level.SEVERE, null, ex);
                return 0;
            }
	}


	@Override
	protected StorageInfo gatheringStorageInfo()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


}

