package web;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import model.entity.PredmetDao;
import model.entity.PredmetEntity;
import model.service.repository.crud.PrimaryKey;
import model.service.repository.crud.StorageInfo;
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


		//test_SqlOracleStorage_Query();
                //test_SqlMySqlStorage_Query();
                test_SqlMySqlStorage_Entity();

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

        protected void test_SqlMySqlStorage_Query()
        {
          try {
            // Připojím se k MySql storage. I když třída se jmenuje ..Oracle.. a akorát se připojím k DB, ale čert to vem.
            SqlOracleStorage mys = new SqlOracleStorage(ResourceManager.getSqlDataSource(), null);
            final String q = "SELECT * FROM temp1";
            logger.info(q);
            // Zavolám nastavený select
            ResultSet rs = mys.Query(q);
            // Procházím vrácené řádky (dokud existují)
            while (rs.next())
            {   logger.info("\n=========RADEK: " + rs.getRow());
                ResultSetMetaData md = rs.getMetaData();
                // Zjistím si počet sloupců (sice je zbytečné zjišťovat pro každý řádek, ale neva)
                int cols = md.getColumnCount();
                // Projdu všechny sloupce a vypíšu nějaké informace
                for (int i=1; i<=cols; i++)
                {   logger.info("\nSLOUPEC: " + i);
                    logger.info("getTableName: " + md.getTableName(i));
                    logger.info("getColumnName: " + md.getColumnName(i));
                    logger.info("getColumnType: " + md.getColumnType(i));
                    logger.info("getColumnClassName: " + md.getColumnClassName(i));
                    logger.info("getPrecision: " + md.getPrecision(i));
                    logger.info("getScale: " + md.getScale(i));
                    logger.info("isNullable: " + md.isNullable(i));
                    switch (md.getColumnType(i))
                    {   case 3 : logger.info(rs.getBigDecimal(i)); break;
                        case 4 : logger.info(rs.getInt(i)); break;
                        case 12 : logger.info(rs.getString(i)); break;
                        case 91 : logger.info(rs.getDate(i)); break;
                        default : logger.error("Neznamy typ: " + md.getColumnType(i) + " - tabulka: " + md.getTableName(i) + ", sloupec: " + md.getColumnName(i) + ", trida" + md.getColumnClassName(i));
                    }
                }
            }
          } catch (SQLException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
          }
        }

        protected void test_SqlMySqlStorage_Entity()
        {
            //vložení předmětu: 40, testík
            PredmetEntity novyPredmet = new PredmetEntity();
            novyPredmet.setId(40);
            novyPredmet.setNazev("testík");
            boolean result = PredmetDao.insert(novyPredmet);
            logger.info("výsledek insertu: " + result);

            // načtení předmětu 40
            PredmetEntity predmet = PredmetDao.seek(40);
            logger.info(predmet.getId() + ": " + predmet.getNazev());

            // změna názvu předmětu
            predmet.setNazev("změněný název");
            result = PredmetDao.update(predmet);
            logger.info("výsledek updatu: " + result);

            // znovu načtení změněného předmětu 40
            PredmetEntity zmenenyPredmet = PredmetDao.seek(predmet.getId());
            logger.info(zmenenyPredmet.getId() + ": " + zmenenyPredmet.getNazev());
            
            // smazání předmětu 40
            result = PredmetDao.delete(40);
            logger.info("výsledek deletu: " + result);
        }
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



	protected void test_SqlMySqlStorage_Query()
	{
//		- JDBC driver
//			- pripojeni k oracle shitu
//			- jednoduchy sql dotaz, ze to funguje: INSERT + SELECT
//                - Oracle: HelloController.test_SqlOracleStorage_Query()
//                - MySql:  HelloController.test_SqlOracleStorage_Query()
//            - CRUD:
//                - create
//                - read
//                - update
//                - delete
//                - search
//                - exists
//                - list
//                - query --> viz: HelloController.test_SqlOracleStorage_Query()
//                    - iterace nad vysledkama, tj. foreach -> logger



}
