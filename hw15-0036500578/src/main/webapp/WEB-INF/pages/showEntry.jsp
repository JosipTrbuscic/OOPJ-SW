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
  <h1>${entry.title}</h1>
  <p>${entry.text}<p>
  <br>
  <c:if test="${isOwner}">
		 <a href="edit?eid=${entry.id}">Edit entry</a>
  </c:if>
  <br><br>
  
  <ul>
      <c:forEach var="c" items="${comments}">
        <li>${c.message}</li>
      </c:forEach>
  </ul>
  <br><br>
	<form action="<%out.print(request.getContextPath().toString());%>/servleti/addComment" method="POST">
		Add Comment:<br>
		<input type="text" name="text" value="Comment Text"required><br>
		<c:if test="${empty sessionScope.login}">
		 	<input type="text" name="email" value="Email" required><br>
		</c:if>
		<input type="hidden" name="eid" value="${entry.id}">
		<input type="submit" value="Add comment">
	</form><br><br>
	<a href="<%out.print(request.getContextPath().toString());%>/index.jsp">Return to login page</a>
  </body>
</html>
