package com.example.demo.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class ViewResolver {
	Logger logger = Logger.getLogger(ViewResolver.class);
	public ViewResolver() {}
	public ViewResolver(String[] pageMove) {}
	public ViewResolver(HttpServletRequest req, HttpServletResponse res, String[] pageMove) 
	throws ServletException, IOException
	{
		String path = pageMove[1];
		if("redirect".equals(pageMove[0])) {
			res.sendRedirect(path);
		}else if("forward".equals(pageMove[0])) {
			RequestDispatcher view = req.getRequestDispatcher("/"+path+".jsp");
			view.forward(req, res);
		}else {
			logger.info("else");
			path = pageMove[0]+"/"+pageMove[1];
			RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/views/"+path+".jsp");
			view.forward(req, res);
		}		
	}
}
