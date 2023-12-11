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
@WebServlet("/mvc/login.do")
public class LoginController1 extends HttpServlet {
	Logger logger = Logger.getLogger(LoginController1.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doGet");
		String mem_id = req.getParameter("mem_id");
		logger.info(mem_id);
		String mem_pw = req.getParameter("mem_pw");
		logger.info(mem_pw);
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
		//코드를 분리했기 때문에 분리된 두 코드간에 데이터를 전달하기 위해
		//모델객체가 필요하다 -여기서 모델객체는 request저장소나 session저장소를 이용한다
		req.setAttribute("mem_name", mem_name);
		HttpSession session = req.getSession();
		session.setAttribute("smem_name", mem_name);
		//출력(View)의 분리 - 변하는 것과 변하지 않는 것의 분리
		resp.sendRedirect("./index.jsp");
		//RequestDispatcher view = req.getRequestDispatcher("./index.jsp");
		//view.forward(req, resp);
	}// end of doGet

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}	

}
