<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  <c:choose>
    <c:when test="${not empty sessionScope.login}">
       Loged in as <c:out value="${login.firstName}"/> <c:out value="${login.lastName}"/>
        <br />
    </c:when>    
    <c:otherwise>
        Not loged in 
        <br />
    </c:otherwise>
</c:choose>
<%-- ${not empty sessionScope.login ? "logged in as" <c:out value="${login.firstName}"/> ${login.lastname} : "not logged in"} --%>
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
	</c:if>
  </body>
</html>
