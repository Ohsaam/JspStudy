package com.example.demo.pojo2;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.example.demo.pojo1.FrontMVC;
import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

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
		String upmu[] = (String[])req.getAttribute("upmu");//notice, noticeList or noticeInsert or noticeUpdate or noticeDelete -  분기
		String path = null;
		
		if("boardList".equals(upmu[1])) {//select
			logger.info("BoardList");
			List<Map<String ,Object>> bList = null;//nList.size()가 n개 
			hmb.bind(pMap);  
			bList = bLogic.boardList(pMap);
			//원본에다가 담아 두자
			req.setAttribute("bList", bList);
			path.append("boardList.jsp");
			isRedirect = false;//false이면 forward처리됨
		}
		
		else if("boardDetail".equals(upmu[1]))
		{
				logger.info("boardDetail");
				List<Map<String ,Object>> bList = null;//nList.size()=1
				// NoticeLogic의 메소드 호출 - 객체주입 - 내가(책임) 아님 스프링(제어역전)
				//select * from notice where n_no=5;
				hmb.bind(pMap);
				bList = bLogic.boardList(pMap);
				//원본에다가 담아 두자
				req.setAttribute("bList", bList);
				path.append("boardDetail.jsp");
				isRedirect = false;//false이면 forward처리됨
			}
		
		else if("boardInsert".equals(upmu[1])) {//insert
			logger.info("boardInsert");
			int result = 0;
			hmb.bind(pMap);
			result = bLogic.boardInsert(pMap);
			if(result == 1) {
				path.append("boardList.gd");
				isRedirect = true;//sendRedirect
			}else {
				path.append("boardError.jsp");
				isRedirect = true;
			}
		
			else if("boardUpdate".equals(upmu[1])) {//update
				logger.info("boardUpdate");
				int result = 0;
				hmb.bind(pMap);
				result = bLogic.boardUpdate(pMap);
				if(result == 1) {
					path.append("boardList.gd");
					isRedirect = true;
				}else {
					path.append("boardError.jsp");
					isRedirect = true;
				}
				
				
				else if("boardDelete".equals(upmu[1])) {//delete
					logger.info("boardDelete");
					int result = 0;
					hmb.bind(pMap);
					result = bLogic.boardDelete(pMap);
					if(result == 1) {
						path.append("boardList.gd");
						isRedirect = true;
					}else {
						path.append("boardError.jsp");
						isRedirect = true;
					}			

		
					
					
		
		return path;
		
		
		
	//	return "redirect:/notice/noticeList.jsp"; // webapp
	//	return "forward:/notice/noticeList.jsp"  & webapp
   //   return "/notice/noticeList"; ==> WEB-INF/jsp/notice아래 
		
		
		
		// 요청이 유지되는 것으로 판단해서 서블릿이 쥐고 있는것을 jsp 사용가능
	}
	
	

				else if("imageUpload".equals(upmu[1])) {//delete
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
				}
	
	// end of imageUpload
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
	}
	

}
