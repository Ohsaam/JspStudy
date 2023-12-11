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
			//왜 NoticeController클래스에 upmu[]을 넣어주나요? - 메소드
			//메소드 이름을 가지고 NoticeController에서 if문(조건식이 필요함-upmu[1])을 적어야 한다.
			//4가지 - noticeList, noticeInsert, noticeUpdate, noticeDelete
			//왜냐면 NoticeController에서 NoticeLogic클래스를 인스턴스화 해야 하니까
			//왜요? NoticeLogic에 정의된 메소드를 여기서 호출해야 하니까...
			//설계 관점 아쉬움 - 우리는 XXXController에서 부터 메소드를 가질 수 없었나?
			//이유- 나는 아직은 메소드마다 req, res에 대한 객체 주입을 처리할 수 없는 구조이니까
			req.setAttribute("upmu",upmu);
			//컨트롤러가 결정된 이름으로 메소드가 호출되고 있다. - 별로다 - 메소드마다 url패턴을 적용할 수 없어서 if문으로 처리하였다
			//스프링에서는 메소드마다 매핑을 지원하는 어노테이션(@RequestMapping, @GetMpping, @PostMapping)이 지원되고 있다
			//그런데 나는 이런 설계가 안되어서 getRequestURI()  -> upmu[0]=notice, upmu[1]=noticeList
			// req.setAttribute("upmu", upmu);
			af = nc.execute(req, res);//NoticeController클래스로 건너감 - upmu[1]-메소드이름이니까...
		}

		else if("member".equals(upmu[0])) {
			
		}
		else if("lecture".equals(upmu[0])) {
			
		}
		//////////////////////////////////{{ *.gd 어떤 컨트롤클래스를 주입받아야 하는가?  }}//////////////////////////////////////
		
		//////////////////////[[ 컨트롤을 타고 난 뒤에 내가 할일은? ]]///////////////////////
		//해당 업무에 대응하는 컨트롤러에서 결정된 페이지 이름을 받아온다
		//위에서 결정된 true 혹은 false값에 따라 sendRedirect와 forward를 통해
		//응답페이지를 호출해준다. - 이것이 FrontMVC의 역할이다.
		//이 지점은 java와 오라클 서버를 경유한 뒤 시점이다.
		if(af !=null) {
			if(af.isRedirect()) { // ture이면 sendRedirect인 경우
				if(af.getPath() == null) {
					// 첨부파일을 업로드 하는 것은 페이지 이동과 전혀무관하다.
					// 첨부파일이 처리된 경우 에는 path에 null를 반환하게 한다. 
					return;
				}else {
					res.sendRedirect(af.getPath());				// notice/noticeList.jsp
					// 파일 업로드가 아닌경우, 응답으로 나갈 페이지url이 담기는 변수가 path이다.
					// 이런 부분을 스프링에선 ViewResolver라는 클래스가 지원하는 부분이다.
				
				}
			}
			else{ // false인 경우
				//슬래쉬가 포함된 경우
				if(af.getPath().contains("/")) {
					RequestDispatcher view = req.getRequestDispatcher(af.getPath());
					view.forward(req, res);					
				}
				else if(af.getPath() == null) { // 파일 업로드 처리 시 -> ActionForward를 통해서 값을 리턴 받을 떄, 
					// 문제가 발생되니깐 , 이 부분에 대한 해결 프로세스 추가
 					logger.info("path가 null일때");
				}
				//슬래쉬가 미포함인 경우
				/**
				 * 슬래시가 포함되었다는 건 응답으로 나가는 마임타입이 Html이다.
				 *  - path.append(notice.noticeList.jsp).였음. --> 슬래시갸 있다는 건 응답페이지로 나가는게 HTML이다.
				 *  
				 *  아닌 경우는? 
				 * Json 포맷이라면 당연히 없음
				 * DataSet인데 무슨 슬래시가 필요한가?
				 * - Json
				 * - 문자열 형식(React.Js) 같이 이종간의 언어가 뷰 계층을 담당할 떄 필요하다.
				 * - Null 일 떄, 이렇게 이미지에 대한 업로드 처리 시 + 첨부파일 처리시엔, 리턴으로 나갈 값이 필요없다.
				 * - 업로드가 됏다고 해서 사후처리하는 코드는 없다 그 얘기는 null이라는거임
				 * 
				 * 슬래스가 null인 경우와 하나인경우
				 * 
				 */
				else {
					logger.info("슬래쉬가 미포함인 경우 ===> " + af.getPath());
					res.setCharacterEncoding("utf-8");
					res.setContentType("text/plain;utf-8");
					PrintWriter out = res.getWriter();
					out.print(af.getPath());
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
