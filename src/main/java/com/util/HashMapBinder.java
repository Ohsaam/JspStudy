package com.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.vo.BoardVO;

import java.io.File;
import java.util.Enumeration;

public class HashMapBinder {
	Logger logger = Logger.getLogger(HashMapBinder.class);
	HttpServletRequest req = null;
	
	MultipartRequest multi  = null;
	String realFolder = "C:\\Program Files\\workspace_jsp\\nae2Gym\\src\\main\\webapp\\pds";
	String encType = "utf-8";
	int maxSize = 5*1024*1024;
	
	
	//우리가 getAttribute & setAttribute를 해서 반복되는 코드를 HashMapBinder로 공통코드로 만들었음.
	
	//왜 req인가? -> 생각해볼 필요가 있다.
	public HashMapBinder(HttpServletRequest req) {
		this.req = req;
	}
	// public void multiBinder(Map<String,Object> pMap) 파라미터에있는 주소번지는 어디서 결정되나요?
	// boardController 에서 주입 받는다.(이 공통코드를 클래스를 주입받는다.
	public void multiBinder(Map<String,Object> pMap)
	{
		pMap.clear();//기존의 들어있는 정보는 비운다.
		try {
			multi = new MultipartRequest(req, realFolder, maxSize,  encType, new DefaultFileRenamePolicy());
		}
		catch(Exception e) {
			logger.info(e.toString());
		}
		
		
		//이미지 처리말고 Post에서 첨부파일에 있는 포스트 방식일 때 사용하는 메소드
		//첨부파일이 아닌 다른 정보에 대해서 받아준다.
		Enumeration<String> em = multi.getParameterNames();
		while(em.hasMoreElements()) {
			//키값 꺼내기
			String key = em.nextElement();//n_title, n_content, n_writer
			logger.info("em여기타니?");
			pMap.put(key, multi.getParameter(key));
			
		}////////////// end of while
//		
//		
//		// 첨부파일에 대한 처리를 말함.
		Enumeration<String> files = multi.getFileNames();
		String fullPah = null;//파일 정보에 대한 전체경로
		String filename = null;//파일이름
		//첨부파일이 있다면?
		if(files !=null) {
			//파일 이름을 클래스로 정의하는 객체 - 파일객체가 생성되었다고 해서 그 안에 내용까지 포함하진 않음
			//파일 크기를 계산해주는 메소드 지원함
			File file = null;
			while(files.hasMoreElements()) {
				String fname = files.nextElement();
				filename = multi.getFilesystemName(fname);
				pMap.put("b_file", filename);//avartar.png
				//File객체 생성하기
				file = new File(realFolder+"\\"+filename);
			}
			//첨부파일의 크기를 담기
			double size = 0;
			if(file !=null) {
				size = file.length();
				size = size/(1024);
				pMap.put("bs_size", size);
			}
		}
		logger.info("pMap :" + pMap.toString());
	}
	
	
	
	//메소드 설계시 리턴타입이 아닌 파라미터 자리를 통해서 값을 전달하는 방법 소개
	//사용자가 입력한 값을 담아 맵이 외부 클래스에서 인스턴스화 되어 넘어오니까
	//초기화 처리 후 사용함
	/*****************************************************************
	 * 
	 * @param pMap -  필요한 클래스 주입 - 선언자리이지 생성자리 아님
	 *****************************************************************/
	public void bind(Map<String,Object> pMap) {
		pMap.clear();
		//<input type="text" name="n_title">
		//<input type="text" name="n_content">
		//<input type="text" name="n_writer">
		Enumeration<String> em = req.getParameterNames();
		
		
		while(em.hasMoreElements()) {
			//키값 꺼내기
			String key = em.nextElement();//n_title, n_content, n_writer
			pMap.put(key, req.getParameter(key));
		}////////////// end of while
		
	
		//insert문 처리 DTO / VO 객체를 만든다음에 넣고 pMap 반환
//		String b_title = req.getParameter("b_title");
//		String b_writer = req.getParameter("b_writer");
//		String b_content = req.getParameter("b_content");
//		String b_file = req.getParameter("b_file");
//		pMap.put("b_title", b_title);
//		pMap.put("b_writer", b_writer);
//		pMap.put("b_content", b_content);
//		pMap.put("b_file", b_file);
		
		
		logger.info(pMap.toString());
	}///////////////// end of bind
}/////////////////// end of HashMapBinder