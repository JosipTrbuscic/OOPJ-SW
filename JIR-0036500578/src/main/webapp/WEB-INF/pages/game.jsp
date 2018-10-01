<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		<p> Pokušaj: ${sessionScope.i}</p>
		<img alt="Progress" src="/home/josip/eclipse-workspace/hw-workspace/JIR-0036500578/src/main/webapp/WEB-INF/progress.png" width="500" height="50" />
		<form action="start" method="POST">
			<input type="text" name="number"><br>
			<input type="submit" value="Guess">
			<br><br>
		</form><br><br>
		<c:if test="${debug}">
		 <div><c:out value="${form.getError('title')}"/></div>
		</c:if>
	</body>

</html>

