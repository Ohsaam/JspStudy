package com.example.demo.ch5;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		//서블릿 초기화 -  서블릿이 생성 또는 리로딩때 단 한번한 수행됨
		//서블릿이 초기화 될 때 자동 호출되는 메소드 - 개발자가 신경쓰지 않아도 된다
		System.out.println("init호출");
	}
	//브라우저 새로고침을 누르면 service메소드만 호출되고 처리된다.
	//호출될 때마다 반복적으로 수행됨 -  수정
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException{
		//service메소드 호출
		System.out.println("service호출");
	}
	@Override
	public void destroy() {
		//서블릿이 제거될 때 단 한번만 수행됨
	   //서블릿이 메모리에서 내려올 때
	   //어플리케이션 종료
	   //주석이라도 수정 후 저장하면 다시 리로딩
		System.out.println("destroy호출");
	}  

}
