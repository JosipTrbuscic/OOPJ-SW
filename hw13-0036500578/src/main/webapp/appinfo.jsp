<%@ page import="java.util.concurrent.TimeUnit" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>


<html>
	<body bgcolor="#${not empty sessionScope.pickedBgCol ? sessionScope.pickedBgCol : 'FFFFFF'}">
		<p>Elapsed time since application start: ${ value}</p>
		<%
			long interval = System.currentTimeMillis() - (long) getServletContext().getAttribute("startTime");
		
			long mili = TimeUnit.MILLISECONDS.toMillis(interval) % 1000;
			long sec = TimeUnit.MILLISECONDS.toSeconds(interval) % 60;
			long min = TimeUnit.MILLISECONDS.toMinutes(interval) % 60;
			long hr = TimeUnit.MILLISECONDS.toHours(interval) % 24;
			long days = TimeUnit.MILLISECONDS.toHours(interval) % 365;
			out.print(days + " days "+hr + " hours " + min + " min " + sec + " sec " + mili + " milis");
		%>
		<br>
		<a href="index.jsp">Return to home page</a>
	</body>
</html>