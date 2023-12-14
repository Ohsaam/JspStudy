package com.example.demo.pojo2;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


@SuppressWarnings("serial")
//@Slf4j
@WebServlet("*.gd2")
public class ActionServlet extends HttpServlet {
	Logger logger = Logger.getLogger(ActionServlet.class);

	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
		String result = null;//-> redirect:/board/boardList.jsp,  forward:/board/boardList.jsp
		upmu = command.split("/");
		for(String name:upmu) {
			logger.info(name);
		}		
		
		Controller controller = new BoardController();
		if("board".equals(upmu[0])) {
			logger.info("workname - board - execute호출");
			req.setAttribute("upmu",upmu);
			result = controller.execute(req, res);
		}
		//BoardController 경유한 다음에 리턴값을 문자열을 받았다
		//이 문자열 잘라서 pageMove에 담아준다
		//먼저 널 체크하기
		//문자열 배열을 선언할것
		//콜론이 포함되어 있니?
		//콜론이 없는 경우도 처리할것
		//1)redirect-webapp - "redirect:/board/boardList.jsp"
		//2)forward-webapp  - "forward:/board/boardList.jsp"
		//3)/WEB-INF/jsp/ -  "/board/boardList.jsp"
		if(result !=null) {
			String pageMove[] = null;
			if(result.contains(":")) {
				logger.info(":이 포함되어 있어요");
				//-> redirect:
				pageMove = result.split(":");//[0]=redirect or forward [1]=board/boardList
				logger.info(pageMove);
			}//end of 콜론이 있는 경우
			//콜론이 없는 경우
			else{
				pageMove = result.split("/");//[0]=board [1]=boardList
				logger.info(pageMove);
			}//end of 콜론이 없는 경우
			logger.info(pageMove[0] + ", " + pageMove[1]);
			if(pageMove !=null) {
				//치환을 한번더 함
				String path = pageMove[1];// board/boardList
				if("redirect".equals(pageMove[0])) {
					res.sendRedirect(path);//board/boardList.jsp
					//forward 처리시와 동일한 컨벤션을 적용하기 위해서 접두어와 접미어를 붙이는 과정에서 오류가 발동함.
					//현재 구조상 입력|수정|삭제 모두 처리 성공시 목록페이지로 응답이 나가도록 설계가 되어 있다는 것을 간과함
					//res.sendRedirect("/"+path+".jsp");//board/boardList.gd2.jsp
				}//end of sendRedirect
				else if("forward".equals(pageMove[0])) {
					RequestDispatcher view = req.getRequestDispatcher("/"+path+".jsp");
					view.forward(req, res);
				}//end of forward
				else {//콜론이 없는 경우에 실행되는 코드임 - WEB-INF
					path = pageMove[0] +"/"+pageMove[1];//board/boardList
					// /WEB-INF/jsp/board/boardList.jsp -> spring ViewResolver
					RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/"+path+".jsp");
					view.forward(req, res);					
				}//배포 위치가 WEB-INF/아래 인경우
			}//pageMove배열이 null이 아닌ㄱㅇ우
		}// end of if
	}// end of doService
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
