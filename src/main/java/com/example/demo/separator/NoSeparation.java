package com.example.demo.separator;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

//표준으로 사용해야 하는 이유는 다른 외부 라이브러리와 의존관계에 있을때 에러없이 처리됨
@WebServlet("/auth/login.do")
public class NoSeparation extends HttpServlet {
	//객체로 부터 Class객체 얻기 - getClass()
	Logger logger = Logger.getLogger(NoSeparation.class);
	private static final long serialVersionUID = 1L;
	//테스트 URL
	// => http://localhost:8000/auth/login.do?mem_id=kiwi&mem_pw=123
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.info("doGet");
		//아래 코드는 메소드 오버라이딩 시에 쉐도우메소드를 호출하는 코드이다
		//통상 생략하고 사용함 - 상위 클래스의 메소드에서 전처리가 필요한 부분이 있다면???
		//안드로이드 개발시에는 사용하게 되는 경우가 많음 - 한정된 디바이스(자원)에서  효율적인 처리를 이끌기 위해서
		//super.doGet(req, resp);
		//1. 입력
		String mem_id = req.getParameter("mem_id");//where mem_id=?
		String mem_pw = req.getParameter("mem_pw");//and mem_pw=?
		//2. 처리(인증) - 오라클 서버에 회원가입되었던 그 행의 값을 비교하는 것
		//오라클 서버에서 응답으로 받아온 이름을 담기
		String mem_name = null;
		String db_id = "kiwi";
		String db_pw = "123";
		String db_name = "키위";
		if(mem_id.equals(db_id)) {
			if(mem_pw.equals(db_pw)) {
				mem_name = db_name;
			}else {
				mem_name="비밀번호가 맞지 않습니다.";
			}
		}else {
			mem_name="아이디가 존재하지 않습니다.";
		}
		//3. 결과 출력 - response -   sendRedirect, or forward, 유지문제- 저장소를 무엇을 사용할거니? pageContext, request, session, application
		res.setContentType("text/html;charset=utf-8");//마임타입 결정안됨 . 페이지출력문제발생할수 있음.  한글도 깨짐
		PrintWriter out = res.getWriter();
		boolean isID = mem_name.contains("아이디가");//정규식사용가능
		boolean isPW = mem_name.contains("비밀번호가");
		if(isID || isPW) {
			out.print(mem_name);						
		}else {
			out.print(mem_name+"님 환영합니다.");			
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

}















