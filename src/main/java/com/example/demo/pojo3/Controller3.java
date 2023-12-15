package com.example.demo.pojo3;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller3 {
	
	public Object execute(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException;
	public Object boardList(HttpServletRequest req, HttpServletResponse res);
	public Object jsonBoardList(HttpServletRequest req, HttpServletResponse res);
	public Object boardDetail(HttpServletRequest req, HttpServletResponse res);
	public Object boardInsert(HttpServletRequest req, HttpServletResponse res);
	public Object boardUpdate(HttpServletRequest req, HttpServletResponse res);
	public Object boardDelete(HttpServletRequest req, HttpServletResponse res); 
	public Object imageDownload(HttpServletRequest req, HttpServletResponse res);
	public Object imageUpload(HttpServletRequest req, HttpServletResponse res);
	public Object imageGet(HttpServletRequest req, HttpServletResponse res);
	
	
	
	

}
