<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  	<c:choose>
	    <c:when test="${not empty sessionScope.login}">
	       Loged in as <c:out value="${login.firstName}"/> <c:out value="${login.lastName}"/>
	        <a href="<%out.print(request.getContextPath().toString());%>/servleti/logout">Logout</a>
	        <br />
	    </c:when>    
	    <c:otherwise>
	        Not loged in 
	        <br />
	    </c:otherwise>
	</c:choose>
  <h1>Edit Entry</h1>
	<form action="<%out.print(request.getContextPath().toString());%>/servleti/editEntry" method="POST">
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
	</form><br><br>
	<a href="<%out.print(request.getContextPath().toString());%>/index.jsp">Return to login page</a>
  </body>
</html>
