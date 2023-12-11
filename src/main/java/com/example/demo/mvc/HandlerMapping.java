package com.example.demo.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class HandlerMapping {
	static Logger logger = Logger.getLogger(HandlerMapping.class);
	//command => /zipcode/zipcodeList.m3
	public static Object getController(String[] upmu, HttpServletRequest req, HttpServletResponse res) {
		logger.info(upmu[0]+", "+upmu[1]);
		Controller1 controller = null;
		Object obj = null;
		String path  = null;
		ModelAndView3 mav = null;
		if("auth".equals(upmu[0])) {
			logger.info("auth");
			controller = new Auth1Controller();
			if("login".equals(upmu[1])) {
				// 파리미터로 원본을 넘긴다
				obj = controller.login(req,res);
				if(obj instanceof ModelAndView3) {
					return (ModelAndView3)obj;
				}else if(obj instanceof String) {
					return (String)obj;
				}
			}//end of login			
		}
		return obj;
	}
}
