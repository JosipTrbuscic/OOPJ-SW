<%@ page import="java.util.Random" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%Random rand = new Random(); %>
<%!
	private String getColor(Random rand){
		int myRandomNumber = rand.nextInt(0xffffff);
		return Integer.toHexString(myRandomNumber);
	}
%>
<html>
	<body bgcolor="#${not empty sessionScope.pickedBgCol ? sessionScope.pickedBgCol : 'FFFFFF'}">
		
		<p><font color="#<%out.print(getColor(rand));%>">
			Did you ever hear the tragedy of Darth Plagueis The Wise? I thought not. It's not a story the Jedi would tell you.
			It's a Sith legend. Darth Plagueis was a Dark Lord of the Sith, so powerful and so wise he could use the Force
			to influence the midichlorians to create life… He had such a knowledge of the dark side that he could even keep
			the ones he cared about from dying. The dark side of the Force is a pathway to many abilities some consider to
			be unnatural. He became so powerful… the only thing he was afraid of was losing his power, which eventually,
			of course, he did. Unfortunately, he taught his apprentice everything he knew, then his apprentice killed him in
			his sleep. Ironic. He could save others from death, but not himself.</font>
		</p><br>
		<a href="../index.jsp">Return to home page</a>
	</body>
</html>