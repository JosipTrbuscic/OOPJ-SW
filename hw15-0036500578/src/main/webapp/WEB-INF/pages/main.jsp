<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
  <c:choose>
    <c:when test="${not empty sessionScope.login}">
       Loged in as <c:out value="${login.firstName}"/> <c:out value="${login.lastName}"/> <a href="logout">Logout</a>
        <br />
    </c:when>    
    <c:otherwise>
        Not loged in 
        <br />
    </c:otherwise>
</c:choose>
  	<br><br>
	<form action="main" method="POST">
		Nick:<br>
		<input type="text" name="nick"><br>
		<c:if test="${form.hasError('nick')}">
		 <div><c:out value="${form.getError('nick')}"/></div>
		</c:if>
		
		Password:<br>
		<input type="password" name="password"><br>
		<c:if test="${form.hasError('password')}">
		 <div><c:out value="${form.getError('password')}"/></div>
		</c:if>
		
		<br>
		<input type="submit" value="Login">
		<c:if test="${form.hasError('exists')}">
		 <div><c:out value="${form.getError('exists')}"/></div>
		</c:if>
	</form>
	
	<br><br>
	<a href="register">Registration</a>
	<br><br><br>
	Users:
	<ul>
      <c:forEach var="u" items="${users}">
        <li><a href="author/${u.nick}">${u.firstName} ${u.lastName}</a></li>
      </c:forEach>
    </ul>
    <br><br>

  </body>
</html>
