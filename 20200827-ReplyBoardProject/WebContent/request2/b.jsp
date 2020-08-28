<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String id=request.getParameter("id"); 
		//response.sendRedirect("c.jsp"); //request에 값 유지가 필요없는 경우
	%>
	<center>
		<jsp:forward page="c.jsp"></jsp:forward> <%--이렇게 하면 request값을 유지한다 --%>
		
	</center>
</body>
</html>