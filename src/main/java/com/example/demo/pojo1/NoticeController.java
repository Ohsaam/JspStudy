package com.example.demo.pojo1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
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
		else if("jsonNoticeList2".equals(upmu[1])) {//select
			logger.info("jsonNoticeList");
			List<Map<String ,Object>> nList = null;
			hmb.bind(pMap);
			nList = nLogic.noticeList(pMap);
			Gson g = new Gson();
			String temp = g.toJson(nList);
			res.setCharacterEncoding("utf-8");
			res.setContentType("application/json");
			PrintWriter out = res.getWriter();
			//out.print(nList);//List-> [], Map -> {deptno=10, dname=영업부}
			out.print(temp);//[{"deptno":10, "dname":"영업무"}]
			//응답결과가 페이지가 아닌 경우가 존재한다 
			//예를들면) json이거나 quill사용시 이미지 이름 일때도 포함된다.
			//path의 값을 null처리하거나 문자열이 나가는 경우를 고려해야 한다
			int end = path.toString().length();// -> notice/
			logger.info("end : " + end);
			path.delete(0, end);
			logger.info("path.delete 결과 : " + path);
			path.append(temp);//url이 전달되는게 아니라 json형식 즉 문자열이 전달됨
			// localhost:3000/~~~[{~ }] 방식 이다.
			logger.info("path.append : " + path);
			
		}		
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
		}
		
		
		else if("imageUpload".equals(upmu[1])) {//delete
			MultipartRequest multi = null;
			//realFolder는 업로드 시 이 pds에 사진이 올라간다.
			String realFolder = "C:\\Program Files\\workspace_jsp\\nae2Gym\\src\\main\\webapp\\pds";
			String encType = "utf-8";
			int maxSize = 5*1024*1024;
			try {
				multi = new MultipartRequest(req,realFolder,maxSize,encType, new DefaultFileRenamePolicy());
			//  DefaultFileRenamePolicy 중복된 이름이 있을 떄 처리해준다.	
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			Map<String,Object> rmap = nLogic.imageUpload(multi, realFolder);
			String temp = null;
			if(rmap != null)
			{
				temp = rmap.get("bs_file").toString();
			}
			int end = path.toString().length();// -> notice/
			path.delete(0, end);
			path.append(temp);//url이 전달되는게 아니라 json형식 즉 문자열이 전달됨
			
		}// end of imageUpload
		
		/**
		 * 이 코드는 비동기적으로 처리해야된다.
		 * post이면서 enctype 바이너리인 경우 전송이 안 된다.
		 * 그래서 cos.jar를 이용해야된다.
		 */
		
		
		else if("imageGet".equals(upmu[1])) {//delete		

			String b_file = req.getParameter("imageName");
			// 이 부분은 Jsp에서 받아오는 것이다.
			String filePath = "C:\\Program Files\\workspace_jsp\\nae2Gym\\src\\main\\webapp\\pds";
			File file = new File(filePath,b_file.trim());
			String mimeType = req.getServletContext().getMimeType(file.toString()); 
			//파일이름을 가지고 
			
			// 만약에 마임타입이 없다면?
			if(mimeType == null)
			{
				res.setContentType("application/octet-stream"); 
			}
			//b_file (파일 이름)
			
			String downName = null;
			// 파일을 직접 내려받아서 사용
			FileInputStream fis = null; 
			ServletOutputStream sos = null; // WAS 에서 제공하는 서블릿클래스 무조건 예외처리를 해주어야한다.
			
			try {
				
				if(req.getHeader("user-agen").indexOf("MSIE")== -1)
				{
					downName = new String(b_file.getBytes("UTF-8"), "8859-1"); //국제표준규격-다국어지원
				}else {
					
					downName = new String(b_file.getBytes("EUC-KR"), "8859-1"); // 한국 표준 규격
				}
				res.setHeader("Content-Disposition","attachment;filename="+downName);
				fis = new FileInputStream(file);
				sos = res.getOutputStream(); // 얻어와서 quill 에디터에 뿌려줘야한다.
				byte b[] = new byte[1024*10];
				int data = 0;
				while((data = (fis.read(b,0,b.length)))!=-1) // fileInputStream를 통해서 매개변수로 b를 주고 길이만큼 읽어들이면서 서블릿에다가 출력을 해야 되는데,
					//sos.write() 라고 하면 클라이언트쪽에 출력이 나간다.
				{
					sos.write(b,0,data);
				}
				sos.flush(); // FileInputStream를 사용해서 File객체를 읽음 ( 메모리에 쌓인 정보를 비우는 메소드 호출이다.)
				isRedirect = true; // null처리를 해준다. path에 대한 정보는 필요없다. -> delete로 날리자.
				
				int end = path.toString().length();// -> notice/
				path.delete(0, end);
				path = null;
			}

			catch(Exception e) {
				logger.info(e.toString());
			}
			finally {
				
				try {
					if(sos!=null)
					{
						sos.close();
					}
					if(fis != null)
					{
						fis.close(); 
					}
					
				}catch(Exception e)
				{
					
				}
			}
		}// end of imageGet
		
		
		
		
		// 이부분도 손을 봐야된다. -> why? 위에서 null처리를 했기 때문에 만약에 가만히 두면 NullPointException이 발생한다.
		/**
		 * 1. 파일 업로드 시
		 * - 파일 업로드 시와 마찬가지로 파일 정보를 얻어올떄도 출력페이지를 내보낼 필요가 없는 경우  
		 */
		if(path != null) // null이 아니란 얘기는 응답페이지가 존재하는 경우에만 처리할것.
		{
			af.setPath(path.toString());
		}
		else {
			af.setPath(null);
		}
		af.setRedirect(isRedirect); // sendRedirect랑 forward를 나누는 경우가 아니라, 경로에 대한 Focus이다.		
		return af;
	}
}
