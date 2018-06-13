<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<html>
<body bgcolor="#${not empty sessionScope.pickedBgCol ? sessionScope.pickedBgCol : 'FFFFFF'}"><br>
	<a href="colors.jsp">Background color chooser</a><br>
	<a href="trigonometric?a=0&b=90">Trigonometry(Default values)</a><br>
	<form action="trigonometric" method="GET">
		Početni kut:<br>
		<input type="number" name="a" min="0" max="360" step="1" value="0"><br>
		Završni kut:<br>
		<input type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset" value="Reset"><br><br>
	</form>
	
	<a href="stories/funny.jsp">Funny story</a><br>
	<a href="report.jsp">OS usage report</a><br>
	<a href="powers?a=1&b=100&n=3">Powers(Default values)</a><br>
	<a href="appinfo.jsp">App Info</a><br>
	<a href="glasanje">Glasanje</a>
</body>

</html>