<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>
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
	<form action="/blog/servleti/addComment" method="POST">
		Add Comment:<br>
		<input type="text" name="text" value="Comment Text"required><br>
		<input type="text" name="email" value="Email" required><br>
		<input type="hidden" name="eid" value="${entry.id}">
		<input type="submit" value="Add comment">
	</form>
  </body>
</html>
