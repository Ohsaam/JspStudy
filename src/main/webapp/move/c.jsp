<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	RequestDispatcher view 
	= request.getRequestDispatcher("/WEB-INF/jsp/notice/noticeList.jsp");
	view.forward(request,response);//url은 바뀌지 않는데 그런데 화면은 바뀐다	
%>