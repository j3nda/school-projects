package web;

import java.util.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import test.model.Test_107_OrmCRUD;


/**
 * Provide automatic simple tests, which you can start thru this webAppController by clickink on some button...
 */
//@Controller // NOTE: jakmile to pridam, prestane fungovat urlMapping... divny... asi chybka nekde v dispatcher-servlet.xml; @see: WARNING:   No mapping found for HTTP request with URI [/dais/ajax.html] in DispatcherServlet with name 'dispatcher'
@RequestMapping(value="/test")
public class TestController extends BaseController
{

//	public TestController()
//	{
//		setContentFilename("test");
//	}


//	@Override
//	protected ModelAndView processRequest(ModelAndView maw)
//	{
//		maw = super.processRequest(maw);
//
//		ModelAndView content = new ModelAndView(getContentName());
//		content.addObject("now", content);
//
//		getLogger().info("Returning hello view with " + (new Date()).toString());
//
//
//		ModelAndView maw = new ModelAndView("hello");
//
//			String now = (new Date()).toString();
//			logger.info("Returning hello view with " + now);
//		maw.addObject("now", now);
//
//
//		runAllTests(); // TODO: visualise test's results
//
//
//		maw.addObject("", content);
//
//		return maw;
//	}

//    @RequestMapping(value="/create", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(value="/create", method=RequestMethod.GET)
    @ResponseBody
    public String createSmartphone()
	{
		// -- http://fruzenshtein.com/spring-mvc-ajax-jquery/
		return "wtf.ajax!";
    }


	@Override
	protected ModelAndView processRequestContent(ModelAndView content)
	{
		content.addObject("now", (new Date()).toString());

		runAllTests();

		return content;
	}


	@Override
	protected String getContentFilename()
	{
		return "test";
	}


	protected void runAllTests()
	{
		runOrmTests();
	}


	protected void runOrmTests()
	{
		Test_107_OrmCRUD test107 = new Test_107_OrmCRUD();
		test107.runAllTests();
	}


}
