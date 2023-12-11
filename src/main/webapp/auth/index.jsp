<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
	int global = 0;//전변
	//왜 없냐면 jsp파일을 클래스 이름으로 변경하는 명명규칙이 플랫폼 마다 다르기때문에 이름을 알 수 없다
	void methodA(){//이 메소드 호출이 불가합니다. 왜냐면 인스턴스화를 할 수 없기때문에
		
	}
%>    
<%
	//디클러레이션은 사용하지 않는다 - 전변, 메소드선언, 생성자선언
	//스크립틀릿 - 자바코드 - 예외)전변, 메소드선언, 생성자 선언, 인스턴스화 불가함
	String mem_name = (String)request.getAttribute("mem_name");
	String smem_name = (String)session.getAttribute("smem_name");
	out.print(mem_name);//sendRedirect로 요청하면 무조건 null출력됨. forward로 요청하면 출력됨
	out.print("<br>");
	out.print("세션 : "+smem_name);
	//테스트 시나리오 구분할것
	// -> 서블릿을 경유하지 않고 이 페이지가 요청된다면 sendRedirect이든 forward이든 무조건 null이 출력됨 
	// -> auth/index.jsp에서 아이디와 비번을 입력 후 로그인 버튼 누르면 auth/login.do를 요청하니까
	// 서블릿을 경유한 뒤 Login1Controller에서 sendRedirect로 index.jsp가 요청이 된다
%>
<html>
<head>
<meta charset="UTF-8">
<title>index page - 로그인</title>
</head>
<body>
<!-- 
요청을 나타내는 문자열이 서블릿 매핑 이름 뒤에 붙어서.... -GET 방식 - 노출된다
내 비번이 다 보인다 - 보안취약
URL - req.getRequestURI() : 도메인을 제외한 나머지 값만, /auth/login.do
// req.getRequestURL() - full name모두 다. http://localhost:8000/auth/login.do
//요청 URL을 통해서 사용자의 요청사항을 구분할 수 있다.
auth/login.do
upmu[0] = auth - 컨트롤 클래스 이름으로 사용,
upmu[1] = login - 컨트롤클래스의 메소드이름사용
 -->
<!-- http://localhost:8000/auth/index.jsp -->
	<form id="f_login" method="get" action="./login.do">
<!-- 
id는 주로 정적페이지 그러니까 js에서 주로 사용하고 name은  서블릿에서 사용함
서블릿에서는 화면에서 입력한 값을 들을 때 name속성으로만 읽을 수 있다.
getParameter의 인자로 String name이 와야 하니까
 -->	
		<input type="text" id="mem_id" name="mem_id"/><br>
		<input type="text"  id="mem_pw" name="mem_pw"/><br>
		<input type="button" value="로그인" onclick="login()"/>
	</form>
	<script>
		login = () => {
			console.log('login');
			document.querySelector("#f_login").submit();
		}
	</script>
</body>
</html>