package com.example.demo.pojo1;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.util.HashMapBinder;
/*
 * NoticeController는 서블릿을 상속받지 않았다 - 왜냐면 결합도를 낮추고 싶어서. -그게 프레임워크 중요 사상 하나이니까
 * 서블릿이 아닌데 req,와 res는 어디서 주입받죠?  - doGet(req, res)
 * 
 */
public class NoticeController implements Action {
	Logger logger = Logger.getLogger(NoticeController.class);
	NoticeLogic nLogic = new NoticeLogic();//이른
	//->http://localhost:8000/notice/noticeInsert.gd?n_title=%EC%9D%B4%EB%B2%A4%ED%8A%B8&n_content=%EB%82%B4%EC%9A%A9&n_writer=%EA%B4%80%EB%A6%AC%EC%9E%90
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String n_title = req.getParameter("n_title");
		String n_content = req.getParameter("n_content");
		String n_writer = req.getParameter("n_writer");
		logger.info(n_title+", "+n_content+", "+n_writer);		
		//메소드 마다의 url패턴 처리될 수 없는 구조이니까 - if문을 사용할 수  밖에 없었다. - 별로다 - 직관적이지 않다, solid규칙에도 부합하지 않아서
		String upmu[] = (String[])req.getAttribute("upmu");//notice, noticeList or noticeInsert or noticeUpdate or noticeDelete -  분기
		ActionForward af = new ActionForward();
		StringBuilder path = new StringBuilder();  
		boolean isRedirect = false;
		path.append("/notice/");
		//insert here
		////////////////////<< 사용자로 부터 입력받은 값 처리 >>///////////////////
		// ==>  스프링에서는 이런 공통된 관심사에 대해서도  지원하고 있다
		// 스프링에서는 HashMapBinder와 같은 클래스를 따로 구현하지 않아도 된다. - 지원되니깐
		Map<String, Object> pMap = new HashMap<>();
		HashMapBinder hmb = new HashMapBinder(req);
		////////////////////<< 사용자로 부터 입력받은 값 처리 >>///////////////////
		if("noticeList".equals(upmu[1])) {//select
			logger.info("noticeList");
			List<Map<String ,Object>> nList = null;//nList.size()가 n개 
			// NoticeLogic의 메소드 호출 - 객체주입 - 내가(책임) 아님 스프링(제어역전)
			hmb.bind(pMap);  
			nList = nLogic.noticeList(pMap);
			//원본에다가 담아 두자
			req.setAttribute("nList", nList);
			path.append("noticeList.jsp");
			isRedirect = false;//false이면 forward처리됨
		}
		else if("procNoticeList".equals(upmu[1])) {//select
			logger.info("procNoticeList");
			List<Map<String ,Object>> nList = null;//nList.size()가 n개 
			// NoticeLogic의 메소드 호출 - 객체주입 - 내가(책임) 아님 스프링(제어역전)
			hmb.bind(pMap);  
			nList = nLogic.procNoticeList(pMap);
			//원본에다가 담아 두자
			req.setAttribute("nList", nList);
			path.append("noticeList.jsp");
			isRedirect = false;//false이면 forward처리됨
		}		
		//myBatis는 동적쿼리를 지원해서 전체조회와 한건조회를 같은 메소드를 사용해도 
		//되지만 굳이 나누는 이유는 유지보수를 위해서 나눈다
		//같은 메소드를 호출하지만 그 응답페이지 이름이 달라서... 그렇다
		else if("noticeDetail".equals(upmu[1])) {//select
			logger.info("noticeDetail");
			List<Map<String ,Object>> nList = null;//nList.size()=1
			// NoticeLogic의 메소드 호출 - 객체주입 - 내가(책임) 아님 스프링(제어역전)
			//select * from notice where n_no=5;
			hmb.bind(pMap);
			nList = nLogic.noticeList(pMap);
			//원본에다가 담아 두자
			req.setAttribute("nList", nList);
			path.append("noticeDetail.jsp");
			isRedirect = false;//false이면 forward처리됨
		}
		//화면 출력을 ReactJS와 같이 다른 언어 다른 라이브러리를 사용하여 처리해야 할땐
		//Back-End에서 해야될 일은 JSON포맷으로 응답이 나가도록 처리해주면 된다.
		//POJO 1-3버전에서는 이 부분도 공통코드로 담아 본다
		else if("jsonNoticeList".equals(upmu[1])) {//select
			logger.info("jsonNoticeList");
			List<Map<String ,Object>> nList = null;
			hmb.bind(pMap);
			nList = nLogic.noticeList(pMap);
			//원본에다가 담아 두자
			req.setAttribute("nList", nList);
			//실제 플젝에서는 이렇게 하지 않는다(서블릿단에서 직접 내보낸다) 1-2버전에서는 개선해 본다
			path.append("jsonNoticeList.jsp");//jsp를 통해서 나가는 구조이다. 
			isRedirect = false;//false이면 forward처리됨
		}		
		//jsp - 입력 - action(insert) - 1 - 성공 - action(select) - jsp
		else if("noticeInsert".equals(upmu[1])) {//insert
			logger.info("noticeInsert");
			int result = 0;
			hmb.bind(pMap);
			result = nLogic.noticeInsert(pMap);
			if(result == 1) {
				path.append("noticeList.gd");
				isRedirect = true;//sendRedirect
			}else {
				path.append("noticeError.jsp");
				isRedirect = true;
			}
		}else if("noticeUpdate".equals(upmu[1])) {//update
			logger.info("noticeUpdate");
			int result = 0;
			hmb.bind(pMap);
			result = nLogic.noticeUpdate(pMap);
			if(result == 1) {
				path.append("noticeList.gd");
				isRedirect = true;
			}else {
				path.append("noticeError.jsp");
				isRedirect = true;
			}
			
		}else if("noticeDelete".equals(upmu[1])) {//delete
			logger.info("noticeDelete");
			int result = 0;
			hmb.bind(pMap);
			result = nLogic.noticeDelete(pMap);
			if(result == 1) {
				path.append("noticeList.gd");
				isRedirect = true;
			}else {
				path.append("noticeError.jsp");
				isRedirect = true;
			}	
		}else if("imageUpload".equals(upmu[1])) {//delete

		}// end of imageUpload
		else if("imageGet".equals(upmu[1])) {//delete		

		}// end of imageGet
		af.setPath(path.toString());			
		af.setRedirect(isRedirect);//true-> ActionForward - isRedirect - false->true
		return af;
	}
}
