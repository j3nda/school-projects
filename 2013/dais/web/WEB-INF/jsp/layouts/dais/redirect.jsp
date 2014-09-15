<%--
	this file is here, because i haven't idea about solution this todo:
		- remove(somehow): /WEB-INF/jsp/layouts/dais/redirect.jsp ~ while accessing testController!

	NOTE: maybe this is the solution...
	-- http://stackoverflow.com/questions/14648167/how-to-configure-welcome-file-list-in-web-xml

--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% response.sendRedirect("index.html"); %>
