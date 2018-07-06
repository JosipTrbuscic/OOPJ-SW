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
  	<br><br>
  	
	Blog Titles:
	<ul>
      <c:forEach var="e" items="${entries}">
        <li><a href="${nick}/${e.id}">${e.title}</a></li>
      </c:forEach>
    </ul>
    <br><br>
    
    <c:if test="${isOwner}">
		 <a href="${nick}/new">Add new entry</a>
	</c:if><br><br>
	<a href="<%out.print(request.getContextPath().toString());%>/index.jsp">Return to login page</a>
  </body>
</html>
