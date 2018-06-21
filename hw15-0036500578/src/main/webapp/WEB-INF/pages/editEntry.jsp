<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  <h1>Edit Entry</h1>
	<form action="/blog/servleti/editEntry" method="POST">
		Title:<br>
		<input type="text" name="title" value="${entry.title}"><br>
		<c:if test="${form.hasError('title')}">
		 <div><c:out value="${form.getError('title')}"/></div>
		</c:if>
		
		Text:<br>
		<input type="text" name="text" value="${entry.text}"><br>
		<c:if test="${form.hasError('text')}">
		 <div><c:out value="${form.getError('text')}"/></div>
		</c:if>
		
		<input type="hidden" name="eid" value="${entry.id}">
		<input type="submit" value="Edit Entry">
		<br><br>
	</form>
  </body>
</html>
