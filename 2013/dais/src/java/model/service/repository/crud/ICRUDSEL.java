package model.service.repository.crud;


import java.sql.ResultSet;


// CRUD... inspiration in: http://en.wikipedia.org/wiki/Create,_read,_update_and_delete
// =============================================
// CRUDSEL operation SQL     HTTP        note
// ---------------------------------------------
// C------ Create    insert  put / post  RESTful -- RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
//  R----- Read      select  get         RESTful -- RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
//   U---- Update    update  put / patch RESTful -- RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
//    D--- Delete    delete  delete      RESTful -- RESTful -- http://en.wikipedia.org/wiki/Representational_state_transfer
//     S-- Search    -       -           search data with defined criteria and return Collection of StorageDataRow
//      E- Exists    -       -           true/false if record already exists in storage
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//       L List      -       -           list large data with defined criteria and pagination, returns ResultSet


public interface ICRUDSEL extends ICRUDSE
{
	public ResultSet List(ListConditions listConditions);
	public ResultSet Query(String query);
}

