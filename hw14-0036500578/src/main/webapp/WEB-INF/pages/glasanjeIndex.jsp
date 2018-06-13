<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html >
	<body>
		 <h1>${poll.title}</h1>
		 <p>${poll.message} Kliknite na link kako biste glasali!</p>
		 <ol><c:forEach var="option" items="${pollOptions}">
					 <li><a href = "glasanje-glasaj?id=${option.id}" >${option.optionTitle}</a></li> 
			</c:forEach> 
		 </ol>
		 
		 <br><br>
		 <a href = "index.html" >Return to poll list</a>
	</body>
 </html>
