<%@ include file="/WEB-INF/jsp/layouts/_includeBeforeLayout.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
<%-- c:redirect url="/hello.htm"/ --%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>dais@smi051 - web app</title>
    </head>

    <body>
        <p>Hello! This is the default welcome page for a Spring Web MVC project.</p>
        <p><i>To display a different welcome page for this project, modify</i>
            <tt>index.jsp</tt> <i>, or create your own welcome page then change
                the redirection in</i> <tt>redirect.jsp</tt> <i>to point to the new
                welcome page and also update the welcome-file setting in</i>
            <tt>web.xml</tt>.</p>
        
        
        <p><table>
            <tr><th>__layoutName__</th><td>${__layoutName__}</td></tr>
            <tr><th>__contentName__</th><td>${__contentName__}</td></tr>
        </table></p>

    </body>
</html>
<%@ include file="/WEB-INF/jsp/layouts/_includeAfterLayout.jsp" %>
