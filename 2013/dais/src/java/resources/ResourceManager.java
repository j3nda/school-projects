package resources;


import javax.sql.DataSource;


public class ResourceManager
{

	/**
	 * return default sql dataSource
	 * @return
	 */
	static public DataSource getSqlDataSource()
	{
//		return (DataSource)getDataSource("SqlOracle@smi051");
		return (DataSource)getDataSource("SqlMysql@asnetxe.j3nda");
	}


	static public Object getDataSource(String name)
	{
		switch(name) {
			case "SqlMysql@asnetxe.j3nda":
				return (DataSource)org.springframework.jdbc.datasource.DriverManagerDataSource().DriverManagerDataSource();
//				throw new RuntimeException("Unimplemented: org.springframework.jdbc.datasource.DriverManagerDataSource()!");

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

