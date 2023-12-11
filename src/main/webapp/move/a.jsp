<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>a.jsp페이지</h2>
	<p>내용1</p>
	<p>내용2</p>
<%
	//스크립틀릿
	response.sendRedirect("b.jsp");
%>	
<!--==============  이 후   ================-->
	<p>여기 코드는 실행될까?</p>	
</body>
</html>