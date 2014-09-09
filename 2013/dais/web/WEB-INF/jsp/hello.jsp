<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hello ~~ JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
		<p>Greetings, it is now <c:out value="${now}"/></p>
    </body>
</html>
