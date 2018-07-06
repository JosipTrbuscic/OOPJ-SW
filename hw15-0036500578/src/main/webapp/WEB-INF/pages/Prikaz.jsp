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
  <c:choose>
    <c:when test="${blogEntry==null}">
      Nema unosa!
    </c:when>
    <c:otherwise>
      <h1><c:out value="${blogEntry.title}"/></h1>
      <p><c:out value="${blogEntry.text}"/></p>
      <c:if test="${!blogEntry.comments.isEmpty()}">
      <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
        <li><div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      </c:forEach>
      </ul>
      </c:if>
    </c:otherwise>
  </c:choose><br><br>
  <a href="<%out.print(request.getContextPath().toString());%>/index.jsp">Return to login page</a>

  </body>
</html>
