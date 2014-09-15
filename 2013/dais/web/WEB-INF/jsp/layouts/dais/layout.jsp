<%@ include file="/WEB-INF/jsp/layouts/_includeBeforeLayout.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>dais@smi051 - web app</title>

<!--
-- http://docs.spring.io/autorepo/docs/webflow/2.3.x/reference/html/spring-js.html
	<script type="text/javascript" src="<c:url value="/resources/dojo/dojo.js" />"> </script>
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/dijit/themes/tundra/tundra.css" />" />

-- http://www.mkyong.com/spring-mvc/spring-mvc-how-to-include-js-or-css-files-in-a-jsp-page/
	<link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" >

-- http://docs.oracle.com/javaee/1.4/tutorial/doc/JSPTags5.html
	-- and: http://stackoverflow.com/questions/11069950/include-a-view-in-spring-mvc

-- http://docs.spring.io/docs/Spring-MVC-step-by-step/part2.html


COMPOSITE VIEW
-- http://bikashshaw.wordpress.com/2013/05/13/spring-mvc-create-jspjstl-composite-view-header-body-and-footer/

-->
		
	</head>

	<body>
		<div style="background-color:gray;"><strong>@layout:</strong><br/>
			LAYOUT-UPPER:

			<div style="background-color:silver;"><strong>@content</strong> ~ as innerView:<br/>
				CONTENT-UPPER:
				<jsp:include page="/WEB-INF/jsp/${__contentFilename__}.jsp" />
				CONTENT-BOTTOM:

			</div>
			LAYOUT-BOTTOM:
		</div>

		<hr/>


		<table>
			<tr><th>__layoutName__</th><td>${__layoutName__}</td></tr>
			<tr><th>__content__</th><td>ModelAndView object</td></tr>
		</table>


	</body>
</html>
<%@ include file="/WEB-INF/jsp/layouts/_includeAfterLayout.jsp" %>
