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
<body>

	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Ime</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="option" items="${pollOptions}">
			<tr><td>${option.optionTitle}</td><td>${option.votesCount}</td></tr>
		</c:forEach>
		</tbody>
	</table>
	 <h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}" width="400" height="400" />
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}"> ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Najbolji:</p>
	<ul>
		<c:forEach var="b" items="${best}">
			<li><a href="${b.optionLink }"target=_blank>${b.optionTitle}</a></li>
		</c:forEach>
	</ul><br>
	<a href="glasanje?pollID=${pollID}">Return to this poll's voting page</a><br>
	<a href="index.html">Return to poll list</a>
</body>
</html>