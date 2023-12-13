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
			path.delete(0, end);
			path.append(temp);//url이 전달되는게 아니라 json형식 즉 문자열이 전달됨\
			logger.info(path.toString()==null); // false이다.
			logger.info("Controller에 들어왔습니다.");

			
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
		}else if("imageUpload".equals(upmu[1])) {//delete
			//quill editor에서 이미지를 선택하면 해당 요청을 호출함 - 비동기처리
			//post이면서 enctype 바이너리인 경우 전송이 안됨
			MultipartRequest multi  = null;
			String realFolder = "C:\\Program Files\\workspace_jsp\\nae2Gym\\src\\main\\webapp\\pds";
			String encType = "utf-8";
			int maxSize = 5*1024*1024;
			try {
				multi = new MultipartRequest(req, realFolder, maxSize,  encType, new DefaultFileRenamePolicy());
			} catch (Exception e) {
				logger.info(e.toString());
			}
			Map<String, Object> rmap = nLogic.imageUpload(multi, realFolder);
			String temp = null;
			if(rmap !=null) {
				temp = rmap.get("bs_file").toString();
			}
			int end = path.toString().length();// -> notice/
			logger.info(end);
			path.delete(0, end);
			path.append(temp);
			logger.info(temp);
		}// end of imageUpload
		//http://localhost:8000/notice/imageGet.gd?imageName=avatar.png
		else if("imageGet".equals(upmu[1])) {//첨부파일을 처리할 때 다운로드 처리하는 화면에서 사용할 코드 소개함
			String b_file = req.getParameter("imageName");// avartar.png
			logger.info("111 => " +b_file);
			String filePath = "C:\\Program Files\\workspace_jsp\\nae2Gym\\src\\main\\webapp\\pds" ;
			File file = new File(filePath, b_file.trim());
			logger.info("222 => " + file );
			String mimeType = req.getServletContext().getMimeType(file.toString());
			// 서블릿 컨텍스트를 통해 MIME 타입을 가져오는 코드이다.
			if(mimeType == null) {
				res.setContentType("application/octet-stream");
			}
			String downName = null;
			FileInputStream fis = null;
			ServletOutputStream sos = null;
			try {
				if(req.getHeader("user-agent").indexOf("MSIE")==-1) {
					downName = new String(b_file.getBytes("UTF-8"), "8859_1");//국제 표준규격- 다국어지원
				}else {
					downName = new String(b_file.getBytes("EUC-KR"), "8859_1");	//한국 표준 규격				
				}
				res.setHeader("Content-Disposition", "attachment;filename="+downName);
				logger.info("333 + downName" + downName);
				fis = new FileInputStream(file);
				logger.info(fis);
				sos = res.getOutputStream();
				byte b[] = new byte[1024*10];
				int data = 0;
				logger.info("444");
				while((data=(fis.read(b,0, b.length)))!=-1) {
					sos.write(b,0,data);
				}
				sos.flush();//FileInputStream을 사용해서 file객체를 읽음- 메모리에 쌓인 정보를 비우는 메소드 호출 
				isRedirect = true;//null처리를 해둠 
				
				// true면 sendRedirect인데, path를 밑에서 자르기 때문에 sendRedirect하는게 의미가 있을까?
				
				logger.info(path);
				int end = path.toString().length();// -> notice/
				path.delete(0, end);
				path = null; // path는 null값이 찍힌다.
			} catch (Exception e) {
				logger.info(e.toString());
			} finally {
				try {
					if(sos !=null) sos.close();
					if(fis !=null) fis.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}// end of imageGet
		//파일 업로드 시와 마찬가지로 파일 정보를 얻어올때도 출력페이지를 내보낼 필요가 없는 경우임
		if(path !=null) {//응답페이지가 존재하는 경우만 처리할것
			af.setPath(path.toString());//이 대로 두면 NullPointerException대상임			
		}else {
			af.setPath(null);//이 대로 두면 NullPointerException대상임			
		}
		af.setRedirect(isRedirect);//true-> ActionForward - isRedirect - false->true
		return af;
	}
}
