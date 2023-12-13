package com.example.demo.pojo2;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.demo.pojo1.FrontMVC;
import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.HashMapBinder;

/**
 * PageMove[] - 올라올 때,
 * 
 * 
 * Upmu[] - 내려갈 떄, 
 * - 개선점 -> Spring -> XXXhandlerMapping -> BoardController에서 부터 메소드를 쪼갤 수는 없나?
 * - 현재는 if문으로 되어 있어서 가독성이 떨어진다. -> 1-3버전에서는 이 장면을 메소드 단위로 변경하고 싶다.(req,res)
 * 
 * 1. ActionServlet - > BoardController 
 * 
 * 이렇게 Spring - > 
 */

public class BoardController implements Controller{

	Logger logger = Logger.getLogger(BoardController.class);
	BoardLogic bLogic = new BoardLogic();
	
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		String upmu[] = (String[])req.getAttribute("upmu");//notice, noticeList or noticeInsert or noticeUpdate or noticeDelete -  분기
		String path = null;
		Map<String, Object> pMap = new HashMap<>();
		HashMapBinder hmb = new HashMapBinder(req);
		
		if("boardList".equals(upmu[1])) {//select
			logger.info("BoardController클래스 안 : if ==>boardList");
			List<Map<String ,Object>> bList = null;//nList.size()가 n개 
			hmb.bind(pMap);  
			bList = bLogic.boardList(pMap);
			req.setAttribute("bList", bList);
			//페이지를 결정한다. 라고 했을 떄, 
			path = "forward:/board/boardList.jsp"; // 결정은 여기서 하더라고 리턴을 해줘야된다. 맨 아래
		}
		
		else if("boardDetail".equals(upmu[1]))
		{
				logger.info("BoardController클래스 안 : if ==>boardDetail");
				List<Map<String ,Object>> bList = null;//nList.size()=1
				// NoticeLogic의 메소드 호출 - 객체주입 - 내가(책임) 아님 스프링(제어역전)
				//select * from notice where n_no=5;
				hmb.bind(pMap);
				bList = bLogic.boardList(pMap);
				//원본에다가 담아 두자
				req.setAttribute("bList", bList);
				path = "forward:/board/boardDetail.jsp";
				
			}
		
		else if("boardInsert".equals(upmu[1])) {//insert
			logger.info("BoardController클래스 안 : if ==>boardInsert");
			int result = 0;
			hmb.bind(pMap);
			result = bLogic.boardInsert(pMap);
			if(result == 1) {
				path = "redirect:/board/boardList.gd2";
			}else {
				path = "redirect:/board/boardError.jsp";
			}
			
			}
		
			else if("boardUpdate".equals(upmu[1])) {//update
				logger.info("BoardController클래스 안 : if ==>boardUpdate");
				int result = 0;
				hmb.bind(pMap);
				result = bLogic.boardUpdate(pMap);
				if(result == 1) {
					path = "redirect:/board/boardList.gd2";
				}else {
					path = "redirect:/board/boardError.jsp";
				}
			}
				
				
				else if("boardDelete".equals(upmu[1])) {//delete
					logger.info("BoardController클래스 안 : if ==>boardDelete");
					int result = 0;
					hmb.bind(pMap);
					result = bLogic.boardDelete(pMap);
					if(result == 1) {
						path = "redirect:/board/boardList.gd2";
					}else {
						path = "redirect:/board/boardError.jsp";
				}}			

		// 요청이 유지되는 것으로 판단해서 서블릿이 쥐고 있는것을 jsp 사용가능
		
		return path;
	}
}
	
	

