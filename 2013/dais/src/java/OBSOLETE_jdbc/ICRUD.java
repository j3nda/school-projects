package OBSOLETE_jdbc;


import model.dao.crud.pkey.PrimaryKey;
import javax.sql.DataSource;


// CRUD... inspiration in: http://en.wikipedia.org/wiki/Create,_read,_update_and_delete

public interface ICRUD
{
	public void setDataSource(DataSource ds);
	public void setTableInfo(StorageInfo tableInfo);

	public String getTableName();
	public PrimaryKey getPrimaryKey();
	public RepositoryDataRow getDataRow();

	public RepositoryDataRow Create(RepositoryDataRow data);
	public RepositoryDataRow Read();
	public RepositoryDataRow Read(PrimaryKey pkey);
	public RepositoryDataRow Update(RepositoryDataRow data);
	public RepositoryDataRow Update(PrimaryKey pkey, RepositoryDataRow data);
	public RepositoryDataRow Delete();
	public RepositoryDataRow Delete(PrimaryKey pkey);

}
