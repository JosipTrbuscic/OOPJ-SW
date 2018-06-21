<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  <h1>Create new blog entry</h1>
	<form action="/blog/servleti/author/${nick}/new" method="POST">
		Title:<br>
		<input type="text" name="title"><br>
		<c:if test="${form.hasError('title')}">
		 <div><c:out value="${form.getError('title')}"/></div>
		</c:if>
		
		
		Text:<br>
		<input type="text" name="text"><br>
		<c:if test="${form.hasError('text')}">
		 <div><c:out value="${form.getError('text')}"/></div>
		</c:if>
		
		<input type="hidden" name="nick" value="${nick}">
		<input type="submit" value="Create New Entry">
		<br><br>
		<c:if test="${not empty form and not form.hasErrors()}">
			 <div><c:out value="New entry successfully created"/></div>
		</c:if>
	</form>
  </body>
</html>
