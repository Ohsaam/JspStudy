package com.example.demo.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet("/auth/loginAll.do")
public class LoginAllServlet extends HttpServlet {
	Logger logger = Logger.getLogger(LoginAllServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doGet");
		String mem_id = req.getParameter("mem_id");
		String mem_pw = req.getParameter("mem_pw");
		String mem_name = null;
		String db_id = "kiwi";
		String db_pw = "123";
		String db_name = "키위";
		if(mem_id.equals(db_id)) {
			if(mem_pw.equals(db_pw)) {
				mem_name = db_name;
			}else {
				mem_name = "비밀번호가 맞지않습니다.";
			}
		}else {
			mem_name = "아이디가 존재하지 않습니다.";			
		}
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		boolean isID = false;
		boolean isPW = false;
		isID = mem_name.contains("아이디가");
		isPW = mem_name.contains("비밀번호가");
		if(isID || isPW) {
			out.print(mem_name);			
		}else {
			out.print(mem_name+"님 환영합니다.");			
		}
	}// end of doGet

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
