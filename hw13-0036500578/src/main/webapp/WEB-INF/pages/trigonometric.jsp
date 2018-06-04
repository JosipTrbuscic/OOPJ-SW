<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body bgcolor="#${not empty sessionScope.pickedBgCol ? sessionScope.pickedBgCol : 'FFFFFF'}">
		<table>
			<thead>
				<tr><th>Angle</th><th>Sin</th><th>Cos</th></tr>
			</thead>
			<tbody>
				<c:forEach var="value" items="${values}">
					<tr><td>${value.number}</td><td>${value.sin}</td><td>${value.cos}</td></tr>
				</c:forEach>
			</tbody>
		</table>
	</body>

</html>