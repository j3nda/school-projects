package web;


import java.io.IOException;
import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.service.repository.crud.storage.SqlOracleStorage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import resources.ResourceManager;


public class HelloController implements Controller
{
    protected final Log logger = LogFactory.getLog(getClass());

//	// http://docs.spring.io/spring/docs/2.0.8/reference/jdbc.html
//	private class xhonzadb extends SimpleJdbcTemplate
//	{
////		public
////		returnthis.jdbcTemplate.execute("create table mytable (id integer, name varchar(100))");
//	}
//	protected xhonzadb db = new xhonzadb();


    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// 1. Basic Application and Environment Setup
//        logger.info("Returning hello view");
//        return new ModelAndView("hello");

		// 2.2. Improve the controller
//        String now = (new Date()).toString();
//        logger.info("Returning hello view with " + now);
//        return new ModelAndView("hello", "now", now);

// -- https://sites.google.com/site/springmvcnetbeans/step-by-step/#step1.5
// TODO: 2.3. Decouple the view from the controller
// TODO: Chapter 3. Developing the Business Logic
// TODO: Chapter 4. Developing the Web Interface
// TODO: 4.5. Adding a form
// TODO: 4.6. Adding a form controller

// Chapter 5. Implementing Database Persistence
// -- HSQL: java -classpath ../build/web/WEB-INF/lib/hsqldb.jar org.hsqldb.Server -database test

//		SimpleJdbcDaoSupport


//		PredmetModel()
//			setEntity(new PredmetEntity())
//			getEntity();
//			Load()
//			Save()


		ModelAndView maw = new ModelAndView("hello");
		
			String now = (new Date()).toString();
			logger.info("Returning hello view with " + now);
		maw.addObject("now", now);


//		test_SqlOracleStorage_Query();

        return maw;
    }




	protected void test_SqlOracleStorage_Query()
	{
		logger.info("test_SqlOracleStorage_Query: START");

		SqlOracleStorage soq = new SqlOracleStorage(ResourceManager.getSqlDataSource(), null);

		String q = "SELECT * FROM "+soq.getName();
		logger.info("\t"+q);
		ResultSet rs = soq.Query(q);

		logger.info(rs);

		logger.info("test_SqlOracleStorage_Query: END");
	}


	// TODO: CRU


//	protected void test_SqlOracleStorage_Query()
//	{
//		logger.info("test_SqlOracleStorage_Query: START");
//
//		PrimaryKey       pk  = new SqlOraclePrimaryKey();
//			pk.add("id", Integer.class, 11);
//
//		StorageInfo      si  = new StorageInfo("predmet", pk);
//		SqlOracleStorage soq = new SqlOracleStorage(ResourceManager.getSqlDataSource(), null);
//
//		String q = "SELECT * FROM "+soq.getName();
//		logger.info("\t"+q);
//		soq.Query(q);
//
//		logger.info("test_SqlOracleStorage_Query: END");
//	}


/*

	TridaService trida = new TridaService("1.A", "2014/2015");
	- tridni
	- studenti 1:N <<<< znamky
	- predmety 1:N <<<< znamky
	- znamky 1:N

	trida.getStudents().find("Josef") return DataSource

	trida.save();



*/



}
