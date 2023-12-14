package com.example.demo.pojo1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
/*
 * http://localhost:8000/member/memberInsert.gd - insert:int - 입력
 * http://localhost:8000/lecture/lectureInsert.gd
 * http://localhost:8000/notice/noticeInsert.gd
 * http://localhost:8000/member/memberUpdate.gd - update:int -수정
 * http://localhost:8000/lecture/lectureUpdate.gd
 * http://localhost:8000/notice/noticeUpdate.gd
 * http://localhost:8000/member/memberDelete.gd - delete:int - 삭제
 * http://localhost:8000/lecture/lectureDelete.gd
 * http://localhost:8000/notice/noticeDelete.gd
 * http://localhost:8000/member/memberList.gd - select:List<Map> - dispatcher(forward) - false
 * http://localhost:8000/lecture/lectureList.gd - select - forward - false
 * http://localhost:8000/notice/noticeLIst.gd - select - forward - false
 * 
 * 왜 서블릿을 상속받는가? - 스프링도 서블릿을 상속받았다
 * 웹서비스 제공 - 네트워크 - 프로토콜 - 통신규약 - 준수하는 http
 * stateless - 장단점 - 단점보완:세션과쿠키 - 장점:동시접속자
 * http프로토콜을 지원하는 api -> request, response
 * req - getParameter:String, getAttribute(세션):Object - 유효범위(scope)
 * A a = new A(); request, session application
 * 페이지 전환(화면전환) -  유지되어야 하는 정보가 있다. - 너의 아이디, 이름  -유지문제 
 * Sonata myCar = new Sonata();ActionTag-xml문법
 * <jsp:useBean id="myCar" class="com.di.Sonata" scope=page|request|session|application/>
 * 액션태그로 객체생성이 가능하다
 * 저 태그는 jsp에 작성이 된다. - 치명적이다
 * jsp -> jsp가 받는다 -> 모델1 -> 재사용성, 이식성 문제제기 -> 별로다 -> 이렇게 하지 않는다 -> 요청을 jsp가 받고 있다???
 * jsp -> servlet - > 모델2 : 요청을 서블릿이 받는다
 * res - setContentType("text/html;utf-8");
 * 응답처리 -  html - DataSet을 가지고 있나? - 없음 - javascript - > application/json -> JSON.stringyfy, JSON.parse -> html끼워넣는다 - > DOM API
 * UI솔루션, ReactJS 라이브러리 - 다른 언어(이종간)
 * 
 */
@WebServlet("*.gd")
public class FrontMVC extends HttpServlet {
	Logger logger = Logger.getLogger(FrontMVC.class);
	private static final long serialVersionUID = 1L;

	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.info("doService");
		String  uri = req.getRequestURI(); // => /notice/noticeInsert.gd?n_title=a&n_content=b
		logger.info(uri);
		String context = req.getContextPath();// /
		logger.info(context);
		String command = uri.substring(context.length()+1);//-> notice/noticeInsert.gd
		logger.info(command);
		//뒤에 의미없는 확장자 gd를 잘라내기
		int end = command.lastIndexOf(".");//점이 있는 위치정보를 가져온다
		logger.info(""+end);
		command =  command.substring(0,end);//-> notice/noticeInsert까지만 가져온다. .gd는 빼고서....
		logger.info(command);//-> notice/noticeList or notice/noticeInsert or notice/noticeUpdate or notice/noticeDelete
		String upmu[] = null;
		upmu = command.split("/");
		for(String name:upmu) {
			logger.info(name);
		}
		//////////////////////////////////[[ upmu[] ]]///////////////////////////////////
		ActionForward af = null;
		NoticeController nc = new NoticeController();//결합도가 강하다-별로다-제어역전아니다 - 스프링관계된  포인트
		//MemberController mc = new MemberController();
		//LectureController lc = new LectureController();
		////////////////////////[[  어떤 컨트롤러를 태울것인가? ]]/////////////////////////
		//이 지점은 내려가는 방향이다
		if("notice".equals(upmu[0])) {
			req.setAttribute("upmu",upmu);
			af = nc.execute(req, res);//NoticeController클래스로 건너감 - upmu[1]-메소드이름이니까...
		}

		else if("member".equals(upmu[0])) {
			
		}
		else if("lecture".equals(upmu[0])) {
			
		}
		if(af !=null) {
			if(af.isRedirect()) {//true라는 건 sendRedirect인 경우임
				if(af.getPath() == null) {
					return;//해당메소드 탈출
				}else {
					res.sendRedirect(af.getPath());// -> notice/noticeList.jsp					
				}
			}
			else{//forward인 경우임 - url안바뀜, 화면은 바뀜, 유지됨. a페이지에서 쥐고 있는 정보를 b페이지에서도 사용가능함
				if(af.getPath().contains("/")) {
					RequestDispatcher view = req.getRequestDispatcher(af.getPath());
					view.forward(req, res);					
				}
				else if(af.getPath() == null) {//파일 업로드 처리시 ActionForward를 통해서 값을 리턴 받을때 문제가 발생됨. 이부분에 대한 해결  프로세스 추가하였다.
					logger.info("path가 null일때");
				}
				else {
					logger.info("슬래쉬가 미포함인 경우 ===> " + af.getPath());
					res.setCharacterEncoding("utf-8");
					res.setContentType("text/plain;utf-8");
					PrintWriter out = res.getWriter();
					out.print(af.getPath());
					logger.info(af.getPath());
					return;					
				}
			}
		}/////////// end of if - 응답페이지 호출하기 - 포워드		
		
		//////////////////////////////////////////////////////////////////
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doService(req,res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doService(req,res);
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doPut- 수정할때");
		String n_no = req.getParameter("n_no");
		String n_title = req.getParameter("n_title");
		String n_content = req.getParameter("n_content");
		String n_writer = req.getParameter("n_writer");
		logger.info(n_no+", "+n_title+", "+n_content+", "+n_writer);
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doDelete- 삭제할때");
		String n_no = req.getParameter("n_no");
		logger.info(n_no);		
	}

}

