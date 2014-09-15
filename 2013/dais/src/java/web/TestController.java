package web;

import java.util.Date;
import org.springframework.web.servlet.ModelAndView;


/**
 * Provide automatic simple tests, which you can start thru this webAppController by clickink on some button...
 */
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


	@Override
	protected ModelAndView processRequestContent(ModelAndView content)
	{
		content.addObject("now", (new Date()).toString());

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
		// TODO
	}


}
