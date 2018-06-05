<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<html>
	<body bgcolor="#${not empty sessionScope.pickedBgCol ? sessionScope.pickedBgCol : 'FFFFFF'}">
		<h3>${msg}</h3>
		<a href="index.jsp">Return to home page</a>
	</body>
</html>