package model.service.repository.crud;


import javax.sql.DataSource;


// CRUD... inspiration in: http://en.wikipedia.org/wiki/Create,_read,_update_and_delete
// ==========================================
// CRUD operation SQL     HTTP        note
// ------------------------------------------
// C--- Create    insert  put / post  RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
//  R-- Read      select  get         RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
//   U- Update    update  put / patch RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
//    D Delete    delete  delete      RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer


public interface ICRUD
{
	public void setDataSource(DataSource ds);
	public void setStorageInfo(StorageInfo tableInfo);

	public String getName();
	public PrimaryKey getPrimaryKey();
	public StorageDataRow getDataRow();

	public StorageDataRow Create(StorageDataRow data);
	public StorageDataRow Read();
	public StorageDataRow Read(PrimaryKey pkey);
	public StorageDataRow Update(StorageDataRow data);
	public StorageDataRow Update(PrimaryKey pkey, StorageDataRow data);
	public StorageDataRow Delete();
	public StorageDataRow Delete(PrimaryKey pkey);
}
