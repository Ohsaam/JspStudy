package com.example.demo.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class ModelAndView3 {
	Logger logger = Logger.getLogger(ModelAndView3.class);
	HttpServletRequest req = null;
	HttpServletResponse res = null;
	String viewName = null;
	List<Map<String,Object>> reqList = new ArrayList<>();
	public ModelAndView3() {
	}
	public ModelAndView3(HttpServletRequest req,HttpServletResponse res) {
		this.req = req;
		this.res = res;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
		//this.viewName+=req.getContextPath()+"/WEB-INF/view/"+viewName+".jsp";
	}
	public void addObject(String name,Object obj) {
		Map<String,Object> pMap = new HashMap<>();
		pMap.put(name,obj);
		reqList.add(pMap);
		req.setAttribute(name, obj);
	}
	public String getViewName() {
		return viewName;
	}
}
