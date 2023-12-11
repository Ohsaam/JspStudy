<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>a2.jsp</title>
</head>
<body>
	<h2>a2.jsp페이지</h2>
	<p>내용1</p>
	<p>내용2</p>
<%
	//스크립틀릿
	RequestDispatcher view = request.getRequestDispatcher("./b2.jsp");
	view.forward(request,response);//url은 바뀌지 않는데 그런데 화면은 바뀐다
%>	
<!--==============  이 후   ================-->
	<p>여기 코드는 실행될까?</p>	
</body>
</html>