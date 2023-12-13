package com.example.demo.pojo2;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
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
	BoardLogic nLogic = new BoardLogic();
	
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) {
		String upmu[] = (String[])req.getAttribute("upmu");//notice, noticeList or noticeInsert or noticeUpdate or noticeDelete -  분기
		String path = null;
		
		if("boardList".equals(upmu[1])) {//select
			logger.info("BoardList");
			List<Map<String ,Object>> bList = null;//nList.size()가 n개 
			hmb.bind(pMap);  
			bList = bLogic.boardList(pMap);
			//원본에다가 담아 두자
			req.setAttribute("bList", bList);
			path.append("boardList.jsp");
			isRedirect = false;//false이면 forward처리됨
		}
		
		else if("boardDetail".equals(upmu[1]))
		{
				logger.info("boardDetail");
				List<Map<String ,Object>> bList = null;//nList.size()=1
				// NoticeLogic의 메소드 호출 - 객체주입 - 내가(책임) 아님 스프링(제어역전)
				//select * from notice where n_no=5;
				hmb.bind(pMap);
				bList = bLogic.boardList(pMap);
				//원본에다가 담아 두자
				req.setAttribute("bList", bList);
				path.append("boardDetail.jsp");
				isRedirect = false;//false이면 forward처리됨
			}
		
		else if("boardInsert".equals(upmu[1])) {//insert
			logger.info("boardInsert");
			int result = 0;
			hmb.bind(pMap);
			result = bLogic.boardInsert(pMap);
			if(result == 1) {
				path.append("boardList.gd");
				isRedirect = true;//sendRedirect
			}else {
				path.append("boardError.jsp");
				isRedirect = true;
			}
		
			else if("boardUpdate".equals(upmu[1])) {//update
				logger.info("boardUpdate");
				int result = 0;
				hmb.bind(pMap);
				result = bLogic.boardUpdate(pMap);
				if(result == 1) {
					path.append("boardList.gd");
					isRedirect = true;
				}else {
					path.append("boardError.jsp");
					isRedirect = true;
				}
				
				
				else if("boardDelete".equals(upmu[1])) {//delete
					logger.info("boardDelete");
					int result = 0;
					hmb.bind(pMap);
					result = bLogic.boardDelete(pMap);
					if(result == 1) {
						path.append("boardList.gd");
						isRedirect = true;
					}else {
						path.append("boardError.jsp");
						isRedirect = true;
					}			

		
					
					
		
		return path;
		
		
		
	//	return "redirect:/notice/noticeList.jsp"; // webapp
	//	return "forward:/notice/noticeList.jsp"  & webapp
   //   return "/notice/noticeList"; ==> WEB-INF/jsp/notice아래 
		
		
		
		// 요청이 유지되는 것으로 판단해서 서블릿이 쥐고 있는것을 jsp 사용가능
	}
	
	


}
