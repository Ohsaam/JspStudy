package com.example.demo.ch6;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
//확장자는 중요하지 않다. 
//이 문서가 무엇인지는 마임타입으로 결정됨
@WebServlet("/mime/json2.do")
public class JSon2Servlet extends HttpServlet {
	Logger logger = Logger.getLogger(JSon2Servlet.class);
	//jsonTest.jsp페이지를 요청할 것 외에는 한일이 아무것도 없다???
	//서블릿은 경유 했지만 실제로 무엇을 했나? - 한게 없다
	//유지문제가 포함되지 않았다. - 학습목표 - forward호출해서 - RequestDispatcher
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.info("doGet");
		res.sendRedirect("./jsonTest.jsp");
	}

}
