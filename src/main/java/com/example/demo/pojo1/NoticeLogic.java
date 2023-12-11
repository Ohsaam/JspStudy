package com.example.demo.pojo1;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.oreilly.servlet.MultipartRequest;
import com.util.MyBatisCommonFactory;
//PURE - 다른 디바이스에 넣어도 잘 동작하면 좋겠어
//어떠한 인터페이스나 추상클래스도 상속받지 않았다 - 자랑
public class NoticeLogic {
	Logger logger = Logger.getLogger(NoticeLogic.class);
	SqlSessionFactory sqlSessionFactory = null;
	public List<Map<String,Object>> noticeList(Map<String, Object> pMap){
		logger.info("noticeList");
		logger.info(pMap.toString());
		List<Map<String,Object>> nList = new ArrayList<>();
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {  
			nList = sqlSession.selectList("noticeList", pMap);
			logger.info(nList.toString());
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return nList;
	}
	public List<Map<String,Object>> procNoticeList(Map<String, Object> pMap){
		logger.info("noticeList");
		logger.info(pMap.toString());
		List<Map<String,Object>> nList = new ArrayList<>();
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {  
			sqlSession.selectList("proc_noticeList", pMap);
			logger.info(pMap.toString());
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return nList;
	}	
	public int noticeInsert(Map<String, Object> pMap){
		logger.info("noticeInsert");
		int result = 0;
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			result = sqlSession.insert("noticeInsert", pMap);			
			sqlSession.commit();//빼먹으면 물리적인테이블 반영안됨
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return result;
	}
	public int noticeUpdate(Map<String, Object> pMap){
		logger.info("noticeUpdate");
		int result = 0;
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			result = sqlSession.update("noticeUpdate", pMap);			
			sqlSession.commit();//빼먹으면 물리적인테이블 반영안됨
		} catch (Exception e) {
			logger.info(e.toString());
		}		
		return result;
	}//////////// end of noticeUpdate
	public int noticeDelete(Map<String, Object> pMap){
		logger.info("noticeDelete");
		int result = 0;
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			result = sqlSession.delete("noticeDelete", pMap);			
			sqlSession.commit();//빼먹으면 물리적인테이블 반영안됨
		} catch (Exception e) {
			logger.info(e.toString());
		}		
		return result;
	}/////////////end of noticeDelete
	public Map<String, Object> imageUpload(MultipartRequest multi, String realFolder) {
		logger.info("imageUpload");
		Map<String,Object> pMap = new HashMap<>();
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
				pMap.put("bs_file", filename);//avartar.png
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
		return pMap;
		//return pMap;
	}
	
}