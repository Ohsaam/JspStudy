<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map, com.google.gson.Gson" %>    
<%!
//<select id="proc_empcursor" parameterType="java.util.Map" statementType="CALLABLE">
//variable rc_emp refcursor;
//exec proc_empcursor(:rc_emp);
//print rc_emp
//{  call proc_empcursor(#{key, jdbcType=CURSOR, mode=OUT, javaType=java.sql.ResultSet, resultMap=empVO})}
//</select>
//{key=[vo,vo,vo,, ,,,,]}
	//page directive
	//디클러레이션 - 자바코드
	//전변, 메소드 선언할 수 있음
	//잊으세요
	//인스턴스화를 못함 - 플랫폼 독립적이어서 서버마다 자바클래스이름의 명명규칙이 달라서.
	//공통코드,  DB연동하는 코드, 재사용성 고려한 메소드이라면 XXX.java
%>    
<%
	//스크립틀릿 - 지변 - service()코드가 생성되니까 메소드 안에서 선언한 변수이니까
	Map<String,Object> rmap = (Map)request.getAttribute("pMap");
	Gson g = new Gson();
	String temp = g.toJson(rmap);
	//out.print(rmap);//out은 내장객체 - PrintWriter
	out.print(temp);
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원목록 - 프로시저{webapp}</title>
</head>
<body>

</body>
</html>