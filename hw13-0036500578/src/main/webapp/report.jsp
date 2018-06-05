<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<html>
	<body bgcolor="#${not empty sessionScope.pickedBgCol ? sessionScope.pickedBgCol : 'FFFFFF'}">
		<h2>OS Usage</h2>
		<p>Here are the results of OS usage in survey that we completed.</p>
		<img src="reportImage"><br>
		<a href="index.jsp">Return to home page</a>
	</body>
</html>