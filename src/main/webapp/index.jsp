<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>    
<%
	//스크립틀릿
	List<Map<String, Object>>  list = (List)request.getAttribute("list");
	out.print(list);//{[]}
	//localhost:8000/index.jsp -> 값이 null출력됨 -.> HelloServlet을 경유하지 않았잖아
	//localhost:8000/hello -> 주소는 hello인데 출력페이지는 index.jsp보임??? - 값이 출력됨
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
hello 서블릿<br>
<div><h1>안녕</h1></div>
</body>
</html>