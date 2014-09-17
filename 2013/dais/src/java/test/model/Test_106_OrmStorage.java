package test.model;


import javax.sql.DataSource;
import model.service.repository.crud.storage.StorageFactoryType;
import resources.ResourceManager;
import test.BaseTest;

public class Test_106_OrmStorage extends BaseTest
{
	protected DataSource dataSource;


	@Override
	public void runAllTests()
	{
		dataSource = (DataSource) ResourceManager.getSqlDataSource();
		StorageFactoryType storageType = ResourceManager.getSqlDataSourceType();

		// ---[ connection ]-------------------------------------------------
		// test: right DataSource
		Assert((Boolean)(dataSource instanceof DataSource), true, "Invalid DataSource returned from ResourceManager.getSqlDataSource()");

		switch(storageType) {
			case MYSQL:  runAllMySqlTests();  break;
			case ORACLE: runAllOracleTests(); break;

			case UNDEFINED:
			default:
				throw new RuntimeException("StorageFactoryType.UNDEFINED or unimplemented StorageFactoryType!");
		}
	}


	protected void runAllMySqlTests()
	{
		// ---[ low-level SQL-QUERY: DROP TABLE IF EXISTS ]------------------
		// test: query = DROP TABLE test-<randomSuffix> IF EXISTS;
		


		// ---[ low-level SQL-QUERY: CREATE TABLE IF NOT EXISTS ]------------
		// test: query = CREATE TABLE test-<randomSuffix> (id INT, name VARCHAR(64), born DATE, salary DECIMAL(7,2)) ENGINE = MEMORY;



		// ---[ low-level SQL-QUERY: INSERT INTO ... ]-----------------------
		// test: query = INSERT INTO test-<randomSuffix> (id, name, born, salary) VALUES (1, "John Doe", "1234-11-11", 12345.67);
		// test: query = INSERT INTO test-<randomSuffix> (id, name, born, salary) VALUES (2, "John Downhill", "2014-02-03", 43210.98);
		// test: query = INSERT INTO test-<randomSuffix> (id, name, born, salary) VALUES (3, "The GULA Master", "2013-10-07", 1234567.89);



		// ---[ low-level SQL-QUERY: SELECT * FROM ... ]---------------------
		// test: query = SELECT * FROM test-<randomSuffix> ORDER BY name ASC;
		// test: query = SELECT * FROM test-<randomSuffix> WHERE id = 2;

		

		// ---[ low-level SQL-QUERY: UPDATE ... ]----------------------------
		// test: query = UPDATE test-<randomSuffix> SET salary = salary * 2 WHERE id = 2;
		// test: query = SELECT * FROM test-<randomSuffix> WHERE id = 2;



		// ---[ low-level SQL-QUERY: DELETE FROM ... ]-----------------------
		// test: query = DELETE FROM test-<randomSuffix>;
		// test: query = DROP TABLE test-<randomSuffix>;

	}


	protected void runAllOracleTests()
	{
		// TODO: implement same tests as in: runAllMySqlTests();
	}


}

