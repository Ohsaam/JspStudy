package com.example.demo.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.util.HashMapBinder;

public class Auth1Controller implements Controller1{
	Logger logger = Logger.getLogger(Auth1Controller.class);
	Auth1Logic authLogic = new Auth1Logic();
	@Override
	public Object login(HttpServletRequest req, HttpServletResponse res) {
		logger.info("login호출");
		Map<String,Object> pMap = new HashMap<>();
		HashMapBinder hmb = new HashMapBinder(req);
		hmb.bind(pMap);
		logger.info(pMap.toString());
		List<Map<String,Object>> aList = new ArrayList<>();
		//aList = authLogic.login(pMap);
		logger.info(aList.toString());
		String mem_name=null;
		if(aList.size()==1) {
			mem_name = aList.get(0).get("MEM_NAME").toString();
		}
		HttpSession session = req.getSession();
		session.setAttribute("smem_name", "default");
		return "redirect:./index1.jsp";
	}
	@Override
	public Object logout(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}
}
