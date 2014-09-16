package resources;


import javax.sql.DataSource;
import model.service.repository.crud.storage.StorageFactoryType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


public class ResourceManager
{
	protected static StorageFactoryType sqlStorageType = null;


	/**
	 * return default sql dataSource
	 * @return
	 */
	static public DataSource getSqlDataSource()
	{
//		return (DataSource)getDataSource("SqlOracle@smi051");
//		return (DataSource)getDataSource("SqlMysql@asnetxe.tergos");
		return (DataSource)getDataSource("SqlMysql@asnetxe.j3nda");
	}


	/**
	 * return type of sql dataSource
	 * @return
	 */
	static public StorageFactoryType getSqlDataSourceType()
	{
		if (sqlStorageType == null || !(sqlStorageType instanceof StorageFactoryType)) {
			throw new RuntimeException("DataSourceType is not set! You must call getSqlDataSource() or getDataSource(String name) first!");
		}
		return sqlStorageType;
	}


//	static public DataSource getMemcacheDataSource()


	static public Object getDataSource(String name)
	{
		switch(name.toLowerCase()) {
			case "sqlmysql":
			case "sqlmysql@asnetxe.j3nda":
				// NOTE: http://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/jdbc.html
				// NOTE: 12.9 Initializing a DataSource
				sqlStorageType = StorageFactoryType.MYSQL;
				DriverManagerDataSource dataSource = new DriverManagerDataSource("j3nda@asnetxe.com.db=skola-dais");
				return (DataSource) dataSource;
//
//				dataSource.setDriverClassName(DataSourceDemo.DRIVER);
//				dataSource.setUrl(DataSourceDemo.JDBC_URL);
//				dataSource.setUsername(DataSourceDemo.USERNAME);
//				dataSource.setPassword(DataSourceDemo.PASSWORD);
//				return dataSource;
//
////				return (DataSource) new SingleConnectionDataSource("jdbc:mysql://localhost:3306/test", "root", "t3rg0s", true);//
//				dataSource.setDriverClassName(DataSourceDemo.DRIVER);
//				dataSource.setUrl(DataSourceDemo.JDBC_URL);
//				dataSource.setUsername(DataSourceDemo.USERNAME);
//				dataSource.setPassword(DataSourceDemo.PASSWORD);
//				return dataSource;
//
////				return (DataSource) new SingleConnectionDataSource("jdbc:mysql://localhost:3306/test", "root", "t3rg0s", true);

			case "sqlmysql@asnetxe.tergos":
				sqlStorageType = StorageFactoryType.MYSQL;
				return (DataSource) new SingleConnectionDataSource("jdbc:mysql://localhost:3306/test", "root", "t3rg0s", true);

			case "sqloracle":
			case "sqloracle@smi051":
				sqlStorageType = StorageFactoryType.ORACLE;
				throw new RuntimeException("Unimplemented: org.springframework.jdbc.datasource.DriverManagerDataSource()!");
//				break;
//				if ()
//				return (DataSource)
//		org.springframework.jdbc.datasource.DriverManagerDataSource().
//		DriverManagerDataSource()//				break;
//				if ()
//				return (DataSource)
//		org.springframework.jdbc.datasource.DriverManagerDataSource().
//		DriverManagerDataSource()
		}
		throw new RuntimeException("Undefined name!");
	}


}

