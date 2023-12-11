<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.google.gson.Gson" %>    
<%
	//서블릿에서 동적으로 처리된 결과를 받아서 (유지해 주었기 때문에, forward메소드로 포워드가 되었기때문에)
	List<Map<String,Object>> list = (List)request.getAttribute("list");
	Gson g = new Gson();
	String temp = g.toJson(list);
	out.print(temp);
%>   