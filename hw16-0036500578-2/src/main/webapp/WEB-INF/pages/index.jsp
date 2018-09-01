<%@page import="java.util.List, hr.fer.zemris.java.servlets.GlasanjeServlet" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<style type="text/css">
table.reztd {
	text-align: center;
}
</style>
</head>
<body>

	<h1>Choose poll</h1>
	<ol>
		<c:forEach var="poll" items="${polls}">
			 <li><a href ="glasanje?pollID=${poll.id}">${poll.title}</a></li><br>
		</c:forEach>
	</ol>
</body>
</html>