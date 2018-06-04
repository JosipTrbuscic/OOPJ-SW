<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<html>
	<body bgcolor="#${not empty sessionScope.pickedBgCol ? sessionScope.pickedBgCol : 'FFFFFF'}">
		<a href="setcolor?pickedBgCol=00FFFF">CYAN</a>
		<a href="setcolor?pickedBgCol=FFFFFF">WHITE</a>
		<a href="setcolor?pickedBgCol=FF0000">RED</a>
		<a href="setcolor?pickedBgCol=00FF00">GREEN</a>
	</body>
</html>