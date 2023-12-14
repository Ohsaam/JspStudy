package com.example.demo.pojo2;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

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

/*
 * upmu[] -  내려갈 때 -> ActionServlet -> BoardController로 연결될 때
 * -> 개선점(1-3버전) -> spring -> XXXHandlerMapping -> BoardController 에서 부터 메소드를 쪼갤 수 는 없나? -> 현재는 if문으로 되어있어서 가독성떨어짐, 재사용성 떨어짐
 * pageMove[] -  올라올 때
 * 
 * XXXLogic 메소드를 호출할 때 BoardLogic클래스의 인스턴스화가 선행됨(DI지원)
 * 여기는 POJO이므로 제어권을 개발자인 내가 가지므로 이른 인스턴스화 부분은 생략함
 * 객체에 대한 라이프사이클 관리 책임이 개발자인 내게 있다
 * 
 * 아쉬운 점은 BoardController에서 메소드로 분할이 안되었다는 점이다.
 * 대신 if문으로 처리하였다. - 별로다
 * Reflection API 깊은 고민 - ApplicationContext, BeanFactory 스프링 컨테이너
 * IoC직접 구현해 보는 경험 - 시니어
 */
//@Controller - 스프링에서는 클래스 사이의 결합도를 낮추기 위해 상속(결합도가 높아지니까....)을 포기하였다
//@RequestMapping(/notice/*) - 2번 URL매핑
public class BoardController implements Controller {
	Logger logger = Logger.getLogger(BoardController.class);
	BoardLogic bLogic = new BoardLogic();//이른
	//@GetMapping("noticeList.gd") - 객체 주입을 받으려면 ApplicationContext로 부터 빈 관리를 받을 때만 사용이 가능함 - req, res를 주입해주기때문에
	//public String noticeList(HttpServletRequest req, HttpServletResponse res) {}
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String upmu[] = (String[])req.getAttribute("upmu");
		String path = null;

		Map<String, Object> pMap = new HashMap<>();
		HashMapBinder hmb = new HashMapBinder(req);
		//전체조회일때 - select - n건 - List<Map|VO> - list.jsp -상세보기와 응답페이지이름이 달라서 메소드를 분리한다
		//배포위치를 WEB-INF일때 -> /WEB-INF/jsp/(workname)/메소드이름 or upmu[1].jsp
		//둘 - 배포위치가 webapp 일때
		if("boardList".equals(upmu[1])) {//select - 1-3버전에서는 이 장면을 메소드 단위로 변경하고 싶다(req, res)넘겨 받을 수 있어야 한다. -문제 해결못하니까
			logger.info("boardList");
			List<Map<String ,Object>> bList = null;//nList.size()가 n개 
			// NoticeLogic의 메소드 호출 - 객체주입 - 내가(책임) 아님 스프링(제어역전)
			hmb.bind(pMap);  
			bList = bLogic.boardList(pMap);      
			//원본에다가 담아 두자
			req.setAttribute("bList", bList);
			//pageMove[0]=forward, pageMove[1]=/board/boardList.jsp
			path = "forward:board/boardList"; 
		}//end of 목록조회
		//상세조회일때 - select - 1건 - Map or VO괜찮아 - read.jsp
		else if("boardDetail".equals(upmu[1])) {
			logger.info("boardDetail");
			List<Map<String ,Object>> bList = null;//nList.size()=1
			// NoticeLogic의 메소드 호출 - 객체주입 - 내가(책임) 아님 스프링(제어역전)
			//select * from notice where n_no=5;
			hmb.bind(pMap);
			bList = bLogic.boardList(pMap);
			//원본에다가 담아 두자
			req.setAttribute("bList", bList);
			path="forward:/board/boardDetail.jsp";
		}
		
		//공통분모 - 반환값이 int이다,  commit과 rollback대상이다
		//입력|수정|삭제 인 경우 모두 1이라면 어느 페이지로 이동할까요? - 목록(select-/board/boardList.gd2-forward처리해야함)을 보여주세요
		//등록일때 - post방식 -insert:1(수정성공) or 0(수정안됨)
		//jsp - 입력 - action(insert) - 1 - 성공 - action(select) - jsp
		else if("boardInsert".equals(upmu[1])) {//insert
			logger.info("boardInsert");
			int result = 0;
			//hmb.bind(pMap);
			//post방식은 매번 동일한 url을 요청 하더라도 무조건 서버를 경유함
			//get방식은 같은 url이면 인터셉트 당함-  302
			hmb.multiBind(pMap);//첨부파일이 포함된 post방식의 처리일때만 - get방식은 무조건 첨부파일 안됨
			result = bLogic.boardInsert(pMap);
			if(result == 1) {//글등록 성공했을때
				path="redirect:/board/boardList.gd2";//jsp --(redirect)---->boardInsert.gd2 -----(redirect)------> boardList.gd2 --(forward)---> jsp
			}else {
				path="redirect:/board/boardError.jsp";
			}
		}////////////end of boardInsert
		
		//수정일때 - get,put방식 - 큰의미 없다 - Restful 상징성을 표현함 - update:1(수정성공) or 0(수정안됨)

		else if("boardUpdate".equals(upmu[1])) {//update
			logger.info("boardUpdate");
			int result = 0;
			hmb.bind(pMap);//pMap.get(b_no)=5
			logger.info(pMap);
			result = bLogic.boardUpdate(pMap);
			if(result == 1) {//글등록 성공했을때
				path="redirect:/board/boardList.gd2";
			}else {
				path="redirect:/board/boardError.jsp";
			}
		}///////////end of boardUpdate		
		
		
		//삭제일때 - delete방식 - 스프링수업일땐  - delete:1(수정성공) or 0(수정안됨)

		else if("boardDelete".equals(upmu[1])) {//delete
			logger.info("boardDelete");
			int result = 0;
			hmb.bind(pMap);
			result = bLogic.boardDelete(pMap);
			if(result == 1) {//글등록 성공했을때
			//pageMove[0] = redirect
			//pageMove[1] = /board/boardList.gd2
			//upmu 가져가나? 아님 pageMove인가요?	
				path="redirect:/board/boardList.gd2";
			}else {
				path="redirect:/board/boardError.jsp";
			}
		}/////// end of boardDelete -  조건문 블록 하나하나가 메소드로 분할될것.		
		
		
		return path;
		//return "redirect:/notice/noticeList.jsp";//webapp
		//return "forward:/notice/noticeList.jsp";//webapp - 요청이 유지되는 것으로 판단해서 서블릿이 쥐고 있는 값을 jsp에서도 사용할 수 있다.
		//return "/notice/noticeList";//WEB-INF/jsp/notice아래
	}////////////// end of execute
}//////////////// end of BoardController

//전체 문자열 -> "redirect:/workname-컨트롤클래스이름결정/메소드이름(if문조건문)
//pageMove[]
//pageMove[0] = "redirect" or "forward"
//pageMove[1] = "/notice/noticeList.jsp"  or "/board/boardList.jsp"
// 루트 태그 떼어내고 확장자를 떼어내면 notice/noticeList -> split, splice

//입력을 처리할 때
// insert(action) -> "redirect:/board/boardList.gd2" -> path -> ActionServlet -> pageMove[0]=redirect
//, pageMove[1]= board/boardList.gd2 -> split("/") -> pageMove[0]=board, pageMove[1]=boardList.gd2
//path에 담김 -> res.sendRedirect("/"+path+".jsp") -> http://localhost:8000/board/boardList.gd2.jsp















//전체 문자열 -> "redirect:/workname-컨트롤클래스이름결정/메소드이름(if문조건문)
//pageMove[]
//pageMove[0] = "redirect" or "forward"
//pageMove[1] = "/notice/noticeList.jsp"  or "/board/boardList.jsp"
// 루트 태그 떼어내고 확장자를 떼어내면 notice/noticeList -> split, splice