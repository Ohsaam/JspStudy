package com.example.demo.pojo2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.demo.pojo1.ActionForward;
import com.example.demo.pojo1.NoticeController;

public class ActionServlet extends HttpServlet{

	Logger logger =  Logger.getLogger(ActionServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	// 헤더로 받는다.
		doService(req,res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	// body, 서버인터셉트 안당한다. 서버전달한다. 제한이 없음 = 바이너리 파일
    // post 방식 : enctype - multipart/form-data = 바이너리 전달 (문자  + 숫자) => 이미지 포함으로 보낼 수 있음
		doService(req,res);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doService(req,res);
	
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doService(req,res);
	}
	
	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}
	
	

}
