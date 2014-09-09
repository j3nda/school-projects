package resources;


import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


public class ResourceManager
{

	/**
	 * return default sql dataSource
	 * @return
	 */
	static public DataSource getSqlDataSource()
	{
//		return (DataSource)getDataSource("SqlOracle@smi051");
		return (DataSource)getDataSource("SqlMysql@asnetxe.tergos");
//		return (DataSource)getDataSource("SqlMysql@asnetxe.j3nda");
	}


	static public Object getDataSource(String name)
	{
		switch(name) {
			case "SqlMysql@asnetxe.j3nda":
				throw new RuntimeException("Unimplemented: org.springframework.jdbc.datasource.DriverManagerDataSource()!");
//				DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//				dataSource.setDriverClassName(DataSourceDemo.DRIVER);
//				dataSource.setUrl(DataSourceDemo.JDBC_URL);
//				dataSource.setUsername(DataSourceDemo.USERNAME);
//				dataSource.setPassword(DataSourceDemo.PASSWORD);
//				return dataSource;
//
////				return (DataSource) new SingleConnectionDataSource("jdbc:mysql://localhost:3306/test", "root", "t3rg0s", true);

			case "SqlMysql@asnetxe.tergos":
				return (DataSource) new SingleConnectionDataSource("jdbc:mysql://localhost:3306/test", "root", "t3rg0s", true);

			case "SqlOracle@smi051":
				throw new RuntimeException("Unimplemented: org.springframework.jdbc.datasource.DriverManagerDataSource()!");
//				break;
//				if ()
//				return (DataSource)
//		org.springframework.jdbc.datasource.DriverManagerDataSource().
//		DriverManagerDataSource()
		}
		throw new RuntimeException("Undefined name!");
	}


}

