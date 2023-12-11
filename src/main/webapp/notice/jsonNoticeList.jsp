<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*, com.google.gson.Gson" %>
<%
	//서블릿(FrontMVC)을 경유[NoticeController-> NoticeLogic]하고 
	//select한 결과를 돌려(List<Map>) 받아서 request객체에 담아 두었다
	//                                                    req.setAttribute("xList", nList);
	//out.print("<br>");
	List<Map<String,Object>> nList = (List)request.getAttribute("nList");
	Gson g = new Gson();
	String temp = g.toJson(nList);
	out.print(temp);
%>   