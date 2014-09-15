package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


public class BaseController implements Controller
{
	protected final String TAG_layoutName  = "__layoutName__";
	protected final String TAG_contentView = "__content__";

	protected Log logger;
	private String layoutFilename;
	private String contentFilename;
	private HttpServletRequest handleRequest;
	private HttpServletResponse handleResponse;


	public BaseController()
	{
		 setLayoutFilename("layouts/"+getLayoutName()+"/layout");
		 setContentFilename("test");
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		setRequest(request);
		setResponse(response);

		return processRequest();
	}


	protected ModelAndView processRequest()
	{
		ModelAndView layout  = processRequestLayout(new ModelAndView(getLayoutFilename()));
//		ModelAndView content = processRequestContent(new ModelAndView(getContentFilename()));

//		layout.addObject(TAG_contentView, content.getView().toString());

//		getLogger().info(content);
//		getLogger().info(content.getView());

		return layout;
	}


	protected ModelAndView processRequestLayout(ModelAndView layout)
	{
		layout.addObject(TAG_layoutName, getLayoutName());

		return layout;
	}


	protected ModelAndView processRequestContent(ModelAndView content)
	{
		return processRequestLayout(content);
	}


	protected void setRequest(HttpServletRequest request)
	{
		this.handleRequest = request;
	}


	protected HttpServletRequest getRequest()
	{
		return handleRequest;
	}


	protected void setResponse(HttpServletResponse response)
	{
		this.handleResponse = response;
	}

	
	protected HttpServletResponse getResponse()
	{
		return handleResponse;
	}

	
	protected void setLayoutFilename(String layoutFilename)
	{
		this.layoutFilename = layoutFilename;
	}


	protected String getLayoutFilename()
	{
		return layoutFilename;
	}


	protected void setContentFilename(String contentFilename)
	{
		this.contentFilename = contentFilename;
	}


	protected String getContentFilename()
	{
		return contentFilename;
	}


	/**
	 * Return actual-selected layout, @see: WEB-ING/jsp/layouts/<getLayout/layout.jsp
	 * @return String
	 */
	protected String getLayoutName()
	{
		return "dais";
	}


	protected Log getLogger()
	{
		if (logger == null || !(logger instanceof Log)) {
			logger = LogFactory.getLog(getClass());
		}
		return logger;
	}

	
}
