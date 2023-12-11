package com.example.demo.separator;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebServlet("/auth/login1.do")
public class Login1Controller extends HttpServlet {
	Logger logger = Logger.getLogger(Login1Controller.class);
	private static final long serialVersionUID = 1L;
	Login1Logic lgLogic = null;
	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.info("doService");
		logger.info(req.toString());
		logger.info(res.toString());
		lgLogic = new Login1Logic();
		//getParameter메소드의 파라미터는 String타입이고 리턴타입도 String이다
		String mem_id = req.getParameter("mem_id");//where mem_id=?
		String mem_pw = req.getParameter("mem_pw");//and mem_pw=?		
		String mem_name = lgLogic.login(mem_id, mem_pw);
		req.setAttribute("mem_name", mem_name);		
		HttpSession session = req.getSession();
		session.setAttribute("smem_name", mem_name);		
		res.sendRedirect("./index.jsp");
		//RequestDispatcher view = req.getRequestDispatcher("index.jsp");
		//view.forward(req, res);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doService(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doService(req,resp);
	}
}
