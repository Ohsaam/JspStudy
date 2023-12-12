package com.example.demo.pojo2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.demo.pojo1.FrontMVC;

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
	
		String path = null;
		
		if(true)
		{
			path="1";
		}
		// 전체조회일 떄 -  SELECT - n건 - List<Map|VO> - list.jsp - 상세보기와 응답페이지이름이 달라서 메소드를 분리한다.
		/**
		 *  배포위치가 WEB-INF 일 떄,
		 *  
		 *  배포위치가 Wepapp일 떄, -> /WEB-INF/(WorkName)/메소드이름 or 업무배열의 1번의 이름.jsp 구조여야 한다.
		 *  2가지 경우가 있다는 건 변수를 생각해야된다.
		 */
		
		// 상세조회일 떄, SELECT - 1건 - Map or VO괜찮아. - read.jsp 
		else if(true)
		{
			path="2";
		}
		
		
		
		// 등록일 떄, post방식 - insert (1)
		else if(true)
		{
			path="3";
		}
		
		
		// 수정일 떄 , Get방식 update (1-수정성공 or0-수정x)
		else if(true)
		{
			path="4";
		}
		
		// 삭제일 떄, : delete방식 
		else
		{
			path="5";
		}
		
		
		
		
		
		
		
		return path;
		
		
		
	//	return "redirect:/notice/noticeList.jsp"; // webapp
	//	return "forward:/notice/noticeList.jsp"  & webapp
   //   return "/notice/noticeList"; ==> WEB-INF/jsp/notice아래 
		
		
		
		// 요청이 유지되는 것으로 판단해서 서블릿이 쥐고 있는것을 jsp 사용가능
	}
	
	

}
