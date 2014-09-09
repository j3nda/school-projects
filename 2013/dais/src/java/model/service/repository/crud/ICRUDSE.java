package model.service.repository.crud;


import java.util.LinkedList;


// CRUD... inspiration in: http://en.wikipedia.org/wiki/Create,_read,_update_and_delete
// ============================================
// CRUDSE operation SQL     HTTP        note
// --------------------------------------------
// C----- Create    insert  put / post  RESTful -- RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
//  R---- Read      select  get         RESTful -- RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
//   U--- Update    update  put / patch RESTful -- RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
//    D-- Delete    delete  delete      RESTful -- RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//     S- Search    -       -           search data with defined criteria and return Collection of StorageDataRow
//      E Exists    -       -           true/false if record already exists in storage


public interface ICRUDSE extends ICRUD
{
	public LinkedList<StorageDataRow> Search();
	public boolean Exists();
	public boolean Exists(PrimaryKey pkey);
}
