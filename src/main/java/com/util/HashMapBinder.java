package com.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import java.util.Enumeration;

public class HashMapBinder {
	Logger logger = Logger.getLogger(HashMapBinder.class);
	HttpServletRequest req = null;
	public HashMapBinder(HttpServletRequest req) {
		this.req = req;
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
		logger.info(pMap.toString());
	}///////////////// end of bind
}/////////////////// end of HashMapBinder
