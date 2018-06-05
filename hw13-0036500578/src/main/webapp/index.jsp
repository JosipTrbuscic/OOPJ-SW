<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<html>
<body bgcolor="#${not empty sessionScope.pickedBgCol ? sessionScope.pickedBgCol : 'FFFFFF'}"><br>
	<a href="colors.jsp">Background color chooser</a>
	<form action="trigonometric" method="GET">
		Početni kut:<br>
		<input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut:<br>
		<input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset"><br>
		<a href="stories/funny.jsp">Funny story</a><br>
		<a href="report.jsp">OS usage report</a><br>
		<a href="powers?a=1&b=100&n=3">Powers</a><br>
		<a href="appinfo.jsp">App Info</a><br>
		<a href="glasanje">Glasanje</a>
	</form>
</body>

</html>