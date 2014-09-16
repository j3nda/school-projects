package test.model;


import javax.sql.DataSource;
import core.model.service.repository.crud.storage.StorageDataRow;
import core.model.service.repository.crud.storage.StorageInfo;
import model.service.repository.crud.storage.SqlMySqlStorage;
import resources.ResourceManager;

public class Test_110_OrmStorage
{


	public void test_MySQLConnection()
	{
		DataSource sqlMysqlDataSource = (DataSource) ResourceManager.getDataSource("SqlMysql");
		// TODO: - ujistit se, ze mam opravdu spravnou navratovou hodnotu toho co chci!

		// VYSLEDEK JE: assert( (condition-A), (navratova-hodnota-B)) << A ? B --> true/false
//		assert( (1/0), 0 ); --> FALSE -> TEST: FAILED

//		System.out.println();
	}


	public void test_OracleConnection()
	{
		// TODO: ResourceManager.getDataSource("SqlOracle"); // @see: for inspiration in: test_MySQLConnection
	}


	public void test_MySQLStorage()
	{
		// TODO: new SqlMySqlStorage
		test_MySQLStorage_new();

		// TODO: - spravne nasetovane veci na objekt
		test_MySQLStorage_rightInitializedObject();

		// TODO: - test CRUDSEL.Create
		test_MySQLStorage_CRUDSEL_Create();

		// TODO: - test CRUDSEL.Read
//		test_MySQLStorage_CRUDSEL_Read();

		// TODO: - test CRUDSEL.Update
//		test_MySQLStorage_CRUDSEL_Update();

		// TODO: - test CRUDSEL.Delete
//		test_MySQLStorage_CRUDSEL_Delete();

		// TODO: - test CRUDSEL.Search
//		test_MySQLStorage_CRUDSEL_Search();

		// TODO: - test CRUDSEL.Exists
//		test_MySQLStorage_CRUDSEL_Exists();

		// TODO: - test CRUDSEL.List
//		test_MySQLStorage_CRUDSEL_List();
	}


	protected void test_MySQLStorage_new()
	{
		SqlMySqlStorage s = new SqlMySqlStorage((DataSource) ResourceManager.getDataSource("SqlMysql"), new StorageInfo("table-predmet"));
		// TODO
	}


	protected void test_MySQLStorage_rightInitializedObject()
	{
		SqlMySqlStorage s = new SqlMySqlStorage((DataSource) ResourceManager.getDataSource("SqlMysql"), new StorageInfo("table-predmet"));
		// TODO: object(s) have right setted data like: DataSource and StorageInfo
	}


	protected void test_MySQLStorage_CRUDSEL_Create()
	{
		SqlMySqlStorage s = new SqlMySqlStorage((DataSource) ResourceManager.getDataSource("SqlMysql"), new StorageInfo("table-predmet"));
		s.Create(new StorageDataRow());
	}

}

