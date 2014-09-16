package test.orm;

import javax.sql.DataSource;
import model.service.repository.crud.IPrimaryKey;
import model.service.repository.crud.ICRUD;
import model.service.repository.crud.ICRUDSEL;
import model.service.repository.crud.IPrimaryKeySwitch;
import model.service.repository.crud.StorageInfo;
import model.service.repository.crud.storage.SqlMySqlPrimaryKey;
import model.service.repository.crud.storage.SqlMySqlStorage;
import org.springframework.jdbc.core.JdbcTemplate;
import resources.ResourceManager;
import test.BaseTest;


public class Test_107_OrmCRUD extends BaseTest
{


	@Override
	public void runAllTests()
	{
		runAllMySqlTests();

		super.runAllTests();
	}


	protected void runAllMySqlTests()
	{
		SqlMySqlStorage crud;
		SqlMySqlPrimaryKey crudPK;

		DataSource ds = (DataSource)ResourceManager.getDataSource("SqlMysql");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds, true);


		// ---[ new CRUD() ]-------------------------------------------------
		// test: IPrimaryKey, IPrimaryKeySwitch
		crudPK = new SqlMySqlPrimaryKey();
		Assert((Boolean)(crudPK instanceof IPrimaryKey),       true, "SqlMySqlPrimaryKey isn't instanceof IPrimaryKey!");
		Assert((Boolean)(crudPK instanceof IPrimaryKeySwitch), true, "SqlMySqlPrimaryKey isn't instanceof IPrimaryKeySwitch!");

		// test: ICRUD
		crud = mysql_createNewCRUD((DataSource)ds, new StorageInfo("testMySqlTable!", crudPK));
		Assert((Boolean)(crud instanceof ICRUD), true, "SqlMySqlStorage isn't instanceof ICRUD");

		// test: ICRUDSEL
		Assert((Boolean)(crud instanceof ICRUDSEL), true, "SqlMySqlStorage isn't instanceof ICRUDSEL! Unable to call CRUDSEL.Query(...);");


		// ---[ CRUDSEL.Query ]----------------------------------------------
		// test: CREATE TABLE ...
		// -- http://docs.spring.io/spring/docs/2.5.x/reference/jdbc.html#jdbc-NamedParameterJdbcTemplate
		mysql_testCRUDSEL_Query(crud, crudPK
/*
		CREATE TABLE IF NOT EXISTS testMySqlTable << getStorageInfo.getName
		(
			id INT,
			name VARCHAR(64) ,
			surname VARCHAR(64),
			born DATE,
			salary DECIMAL(7,2),
		) ENGINE = MEMORY;
*/
		);

		// test: SELECT * FROM ...
		mysql_testCRUDSEL_Query(crud, crudPK
/*
		DESCRIBE testMySqlTable << getStorageInfo.getName
		-
		mysql> DESCRIBE City;
		+------------+----------+------+-----+---------+----------------+
		| Field      | Type     | Null | Key | Default | Extra          |
		+------------+----------+------+-----+---------+----------------+
		| Id         | int(11)  | NO   | PRI | NULL    | auto_increment |
		| Name       | char(35) | NO   |     |         |                |
		| Country    | char(3)  | NO   | UNI |         |                |
		| District   | char(20) | YES  | MUL |         |                |
		| Population | int(11)  | NO   |     | 0       |                |
		+------------+----------+------+-----+---------+----------------+
*/
		);


		// ---[ CRUD.Create ]------------------------------------------------
		// test: INSERT INTO ...

//		crud.setDataSource(ds);
//		crud.Query("CREATE TABLE ");
//		crud.setStorageInfo(null);

//		crud.setDataSource((DataSource)ds);
//		crud.setStorageInfo(
//				new StorageInfo
//		);
//
//		SqlOraclePrimaryKey pk = new SqlOraclePrimaryKey();
//		pk.add("id");
//
//		setCRUD(
//			new SqlOracleStorage(
//				(DataSource)ResourceManager.getSqlDataSource(),
//				new StorageInfo("predmet", pk)
//			)
//		);


		// ---[ CRUD.Read ]--------------------------------------------------
		// test: SELECT * FROM ...


		// ---[ CRUD.Update ]------------------------------------------------
		// test: UPDATE ...


//			getDataRow
//			getName
//			getPrimaryKey
//			getState
//			setDataSource
//			setStorageInfo


		// ---[ CRUD.Delete ]------------------------------------------------
		// test: DELETE ...


	}


	private void mysql_testCRUDSEL_Query(SqlMySqlStorage crud, SqlMySqlPrimaryKey pk)
	{
			// TODO
	}


	private SqlMySqlStorage mysql_createNewCRUD(DataSource ds, StorageInfo sinfo)
	{
		return new SqlMySqlStorage(ds, sinfo);
	}


//	private CRUD crud_createNewCRUD() // TODO: low-level CRUD testing
//	{
//		return new CRUD()
//		{
//			@Override
//			protected StorageDataRow processCreate(StorageDataRow beforeCreateData)
//			{
//				// TODO: INSERT INTO xxx (aaa, bbb) VALUES (111, 222); and retreive <PrimaryKey>
//				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//			}
//
//			@Override
//			protected StorageDataRow processRead(PrimaryKey pkey, StorageDataRow beforeReadData)
//			{
//				// TODO: SELECT * FROM xxx WHERE <PrimaryKey = ???>;
//				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//			}
//
//			@Override
//			protected StorageDataRow processUpdate(PrimaryKey pkey, StorageDataRow beforeUpdateData)
//			{
//				// TODO: UPDATE xxx SET aaa = 111, bbb = 222 WHERE <PrimaryKey = ???>;
//				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//			}
//
//			@Override
//			protected StorageDataRow processDelete(PrimaryKey pkey, StorageDataRow beforeDeleteData)
//			{
//				// TODO: DELETE FROM xxx WHERE <PrimaryKey = ???>;
//				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//			}
//
//			@Override
//			protected StorageInfo gatheringStorageInfo()
//			{
//				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//			}
//		};
//	}


}

