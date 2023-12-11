package com.example.demo.mvc;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
@WebServlet("/auth/logout1.do")
public class LogOutController1 extends HttpServlet {
	Logger logger = Logger.getLogger(LogOutController1.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doGet");
		req.setAttribute("mem_name", null);
		HttpSession session = req.getSession();
		session.invalidate();
		resp.sendRedirect("./index.jsp");
	}// end of doGet

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}	

}
