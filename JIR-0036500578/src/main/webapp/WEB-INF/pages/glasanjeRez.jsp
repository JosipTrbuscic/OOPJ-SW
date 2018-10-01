<%@page import="java.util.List, hr.fer.zemris.java.servlets.GlasanjeServlet" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<style type="text/css">
table.reztd {
	text-align: center;
}
</style>
</head>
<body bgcolor="#${not empty sessionScope.pickedBgCol ? sessionScope.pickedBgCol : 'FFFFFF'}">

	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="band" items="${bands}">
			<tr><td>${band.name}</td><td>${band.votes}</td></tr>
		</c:forEach>
		</tbody>
	</table>
	 <h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls"> ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova :</p>
	<ul>
		<c:forEach var="b" items="${best}">
			<li><a href="${b.link }"target=_blank>${b.name }</a></li>
		</c:forEach>
	</ul><br>
	<a href="glasanje">Return to voting page</a><br>
	<a href="index.jsp">Return to home page</a>
</body>
</html>