<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<html>
	<body bgcolor="#${not empty sessionScope.pickedBgCol ? sessionScope.pickedBgCol : 'FFFFFF'}">
		<h2>OS Usage</h2>
		<p>Here are the results of OS usage in survey that we completed.</p>
		<img src="genImage">
	</body>
</html>