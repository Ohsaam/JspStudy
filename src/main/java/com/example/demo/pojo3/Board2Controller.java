package com.example.demo.pojo3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.util.HashMapBinder;

public class Board2Controller implements Controller3 {
	Logger logger = Logger.getLogger(Board2Controller.class);
	Map<String, Object> pMap = new HashMap<>();
	Board2Logic bLogic = new Board2Logic();
	String path = null;
	@Override
	public Object execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object jsonBoardList(HttpServletRequest req, HttpServletResponse res) {
		logger.info("jsonBoardList");
		List<Map<String ,Object>> nList = null;
		HashMapBinder hmb = new HashMapBinder(req);
		hmb.bind(pMap);
		nList = bLogic.boardList(pMap);
		Gson g = new Gson();
		String temp = g.toJson(nList);
		return temp; 
	}

	@Override
	public Object boardList(HttpServletRequest req, HttpServletResponse res) {
		logger.info("boardList");
		logger.info(req+", "+res);
		List<Map<String ,Object>> bList = null;//nList.size()가 n개 
		// NoticeLogic의 메소드 호출 - 객체주입 - 내가(책임) 아님 스프링(제어역전)
		HashMapBinder hmb = new HashMapBinder(req);
		hmb.bind(pMap);  
		bList = bLogic.boardList(pMap);      
		//원본에다가 담아 두자
		req.setAttribute("bList", bList);
		//pageMove[0]=forward, pageMove[1]=/board/boardList.jsp
		path = "forward:board/boardList"; 		
		return path;
	}

	@Override
	public Object boardDetail(HttpServletRequest req, HttpServletResponse res) {
		logger.info("boardDetail");
		List<Map<String ,Object>> bList = null;//nList.size()=1
		// NoticeLogic의 메소드 호출 - 객체주입 - 내가(책임) 아님 스프링(제어역전)
		//select * from notice where n_no=5;
		HashMapBinder hmb = new HashMapBinder(req);
		hmb.bind(pMap);
		bList = bLogic.boardDetail(pMap);
		logger.info(bList);
		ModelAndView mav = new ModelAndView(req);//WEB-INF/jsp/[[[board2/boardDetail]]].jsp
		mav.addObject("bList", bList);
		mav.setViewName("board2/boardDetail");
		return mav;
	}

	@Override
	public Object boardInsert(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object boardUpdate(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object boardDelete(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object imageDownload(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object imageUpload(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object imageGet(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

}
