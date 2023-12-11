<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.google.gson.Gson" %>    
<%
	//스크립틀릿 - 자바코드 섞어쓰기 - 정적페이지
	//서블릿에서 코드를 입양할땐 변수이름이 아니라...  내장객체를 쓴다
	//response.setContentType("text/html");필요없다. 왜냐면 page directive안에 있잖아
	//jsonTest_jsp.java -> jsonTest_jsp.class(컴파일)
	//PrintWriter out = res.getWriter();
	//out.print("<br>");
	List<Map<String,Object>> list = new ArrayList<>();
	Map<String,Object> rmap = new HashMap<>();//SELECT deptno, dname, loc FROM dept
	rmap.put("deptno", 10);
	rmap.put("dname", "총무부");
	rmap.put("loc", "서울");
	list.add(rmap);
	rmap = new HashMap<>();//SELECT deptno, dname, loc FROM dept
	rmap.put("deptno", 20);
	rmap.put("dname", "개발부");
	rmap.put("loc", "인천");
	list.add(rmap);
	rmap = new HashMap<>();//SELECT deptno, dname, loc FROM dept
	rmap.put("deptno", 30);
	rmap.put("dname", "운영부");
	rmap.put("loc", "서울");
	list.add(rmap);
	//Front -End와 Back-End - 만나는 부분에 처리이다(mime type역할이 있다)
	//서블릿이라고 쓰고 JSON이라고 읽는다
	//확장자 이름에 속지 말고 마임타입을 확인 할 것
	//리액트의 어떤 코드(언어가-자바스크립트- 전송 - (get,post)-fetch, axios, ajax)가 이 URL패턴을 가져가야 하나?
	Gson g = new Gson();
	String temp = g.toJson(list);
	//무엇을 봤나요?
	//out.print(list);//JSON형식에 맞지 않아 - 키와값사이에 대입연산자 있어서.... ReactJS
	out.print(temp);
%>   
