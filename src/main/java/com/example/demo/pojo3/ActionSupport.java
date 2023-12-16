package com.example.demo.pojo3;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
@WebServlet("*.gd3")
public class ActionSupport extends HttpServlet {
	Logger logger = Logger.getLogger(ActionSupport.class);
	private void doService(HttpServletRequest req, HttpServletResponse res) 
	throws ServletException, IOException
	{
		String  uri = req.getRequestURI(); // =>/board/boardInsert.gd2?n_title=a&n_content=b
		String context = req.getContextPath();// /
		String command = uri.substring(context.length()+1);//-> board/boardInsert.gd
		//뒤에 의미없는 확장자 gd를 잘라내기
		int end = command.lastIndexOf(".");//점이 있는 위치정보를 가져온다
		command =  command.substring(0,end);//-> notice/noticeInsert까지만 가져온다. .gd는 빼고서....
		logger.info(command);//-> notice/noticeList or notice/noticeInsert or notice/noticeUpdate or notice/noticeDelete
		String upmu[] = null;
		String result = null;//-> redirect:/board/boardList.jsp,  forward:/board/boardList.jsp
		upmu = command.split("/");
		for(String name:upmu) {
			//단 반드시 추상 메소드를 먼저 설계할것. - 이 약속이 지켜져야 컴파일 에러가 없다.
			logger.info(name);//upmu[0]=board2 upmu[1]= boardList.jsp, boardList.gd3, boardInsert.gd3, boardUpdate.gd3, boardDelete.gd3		
		}		
		//여기서 getController() 호출 할거야!!!
		Object obj = null;
		try {
			// 이 요청을 어떤 컨트롤러 클래스가 담당하나요?
			obj = HandlerMapping.getController(upmu, req, res);//upmu[0]:board2
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//ViewResolver와 관련된 코드 시작됨
		//getController호출된 다음에 반드시
		//리턴타입을 받아서 타입체크를 하고 String일때  ModelAndView일때를 나누어 처리해야 하니까
		//NullPointerException발동할  가능성이 있는 섹션이다
		//아래 블록이 ViewResolver안에 들어가게됨
		if(obj != null) {
			logger.info(obj);
			String pageMove[] = null;
			ModelAndView mav = null;//
			//path라면 :이 너 안에 포함되어 있어?  응 - redirect or forward
			//:이 없는 경우는  WEB-INF/jsp/XXXXXX.jsp
			if(obj instanceof String) {//너 String타입이니? 네: json 이거나 path
				if(((String) obj).contains(":")) {
					pageMove = obj.toString().split(":");
				}
				else if(((String) obj).contains("/")) {
					pageMove = obj.toString().split("/");					
				}
				//콜론도 없고 슬래쉬도 없는 경우라면 -  단순한 문자열을 반환하는 경우 - String path = "avatart.png", "JSON 형식 문자열"
				// http://localhost:8000/board2/jsonBoardList.gd3
				else {
					logger.info(obj.toString());//JSON
					pageMove = new String[1];
					pageMove[0] = obj.toString();//파일이름같은 경우 저장됨 - >화면에서 후처리하는 용도로 사용할 수 있다.
				}
			}
			else if(obj instanceof ModelAndView) {
				mav = (ModelAndView)obj;
				pageMove = new String[2];
				pageMove[0] = "";
				pageMove[1] = mav.getViewName();
				logger.info("pageMove ==> "+pageMove[0] +", "+pageMove[1]);
			}
			else if(obj instanceof byte[]) {//응답이 png일때 - Quill Editor
				res.setContentType("image/png;utf-8");
				PrintWriter out = res.getWriter();
				out.print(obj);
				return;
			}
			
			//
			if(pageMove !=null && pageMove.length == 2) {
				logger.info("pageMove 원소의 갯수가 2개 일 때");
				String path = pageMove[1];
				if("redirect".equals(pageMove[0])) {
					res.sendRedirect(path);
				}else if("forward".equals(pageMove[0])) {
					RequestDispatcher view = req.getRequestDispatcher(path);
					view.forward(req, res);
				}else {
					logger.info("여길타야되는거아닌가?");
					// /WEB-INF/jsp/board/boardList.jsp -> spring ViewResolver
					RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/"+path+".jsp");
					view.forward(req, res);										
				}
			}///////////////////end of if
			else if(pageMove !=null && pageMove.length == 1) {//quill editor 이미지 선택시 파일이릌반환
				res.setContentType("text/plain;charset=utf-8");
				PrintWriter out = res.getWriter();
				out.print(obj);
				return;
			}
			//JSON포맷으로 반환되는 값을 출력하기 - @ResponseBody, @RestController역할 재현
			else {
				logger.info("여길타니?");
				res.setContentType("text/plain;charset=utf-8");
				PrintWriter out = res.getWriter();
				out.print(obj);
				return;				
			}
			
		}
		
		
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(req,res);
	}
	//쿼리스트링, ?, 링크, header, 제한적임
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(req,res);
	}
	//body, 서버인터셉트 안당함,무조건서버전달, 제한이없음 - 바이너리타입(첨부파일) 
	//post방식 - enctype="multipart/form-data" - 바이너리 전달 - 문자+숫자 - 이미지 포함
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(req,res);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doService(req,res);
	}	
}
