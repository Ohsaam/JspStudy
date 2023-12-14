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
		String context = req.getContextPath();// /
		String command = uri.substring(context.length()+1);//-> notice/noticeInsert.gd
		//뒤에 의미없는 확장자 gd를 잘라내기
		int end = command.lastIndexOf(".");//점이 있는 위치정보를 가져온다
		command =  command.substring(0,end);//-> notice/noticeInsert까지만 가져온다. .gd는 빼고서....
		String upmu[] = null;
		String result = null;
		upmu = command.split("/");
		
		Controller controller = new BoardController();
		if("board".equals(upmu[0]))
		{
			logger.info("ActionServlet WorkName ==> execute 호출");
			req.setAttribute("upmu",upmu);
			result = controller.execute(req, res);
		}
		// board 컨트롤러를 경유한 다음에 리턴값으로 문자열을 받았다.
		// 콜론으로 자르고(1) + 슬래시로 자른다(2)
		
		
		
		// 1. 널체크하기
		// 2. 문자열 배열을 선언할 것,
		// 3. 콜론이 포함되어 있니?
		// 4. 콜론이 포함되어 있지 않은 경우
		if(result != null)
		{
			logger.info("result 타니?");
			String [] pageMove = null;
			if(result.contains(":"))
			{
				pageMove = result.split(":"); // :를 기준으로 나눔
				// [0] = redirect [1] = /board/boardList.jsp
				// 이 경우에는 슬래시로 한 번더 짤라야된다.
				
			}
			else //WEB-INF 경로를 가진 경우 /board/boardList.jsp
			{
				// 슬래시 기준으로 나눈다.
				pageMove = result.split("/");
				//[0] = board [1] = boardList.jsp
				logger.info(pageMove);
			}
			logger.info(pageMove[0] + ", " + pageMove[1]);
			
			if(pageMove != null)
			{
				String path = pageMove[1];
				logger.info("pageMove타니?");
				
				if("redirect".equals(pageMove[0]))
				{
					res.sendRedirect("/"+path+".gd2");
				}
				else if("forward".equals(pageMove[0]))
				{
					RequestDispatcher view = req.getRequestDispatcher("/"+path+".jsp");
					view.forward(req, res);
				}
				else {
					logger.info("WEB-INF타니?");
					path = pageMove[0]+"/"+pageMove[1];
					RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/jsp/"+path+".jsp");
					view.forward(req, res);
				}///// end of 배포위치가 WEB-INF인 경우
			}

		}
	}//end of Service
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

