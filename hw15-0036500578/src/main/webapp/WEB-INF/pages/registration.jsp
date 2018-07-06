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
	<form action="register" method="POST">
		First name:<br>
		<input type="text" name="firstName" value="Your first name">
		<c:if test="${form.hasError('firstName')}">
		 <div class="greska"><c:out value="${form.getError('firstName')}"/></div>
		</c:if>
		<br><br>
		
		
		Last name:<br>
		<input type="text" name="lastName" value="Your last name">
		<c:if test="${form.hasError('lastName')}">
		 <div class="greska"><c:out value="${form.getError('lastName')}"/></div>
		</c:if>
		<br><br>
		
		Email:<br>
		<input type="text" name="email" value="Your email">
		<c:if test="${form.hasError('email')}">
		 <div class="greska"><c:out value="${form.getError('email')}"/></div>
		</c:if>
		<br><br>
		
		Nick:<br>
		<input type="text" name="nick" value="Your nick">
		<c:if test="${form.hasError('nick')}">
		 <div class="greska"><c:out value="${form.getError('nick')}"/></div>
		</c:if>
		<c:if test="${form.hasError('nickTaken')}">
		 <div class="greska"><c:out value="${form.getError('nickTaken')}"/></div>
		</c:if>
		<br><br>
		
		Password:<br>
		<input type="password" name="password" value="Your password">
		<c:if test="${form.hasError('password')}">
		 <div class="greska"><c:out value="${form.getError('password')}"/></div>
		</c:if>
		<br><br>
		
		<input type="submit" value="Register">
		
		<c:if test="${not empty form and not form.hasErrors()}">
		 <div class="greska"><c:out value="user registerd successfully"/></div>
		</c:if>
		
	</form><br><br>
	<a href="<%out.print(request.getContextPath().toString());%>/index.jsp">Return to login page</a>
  </body>
</html>
