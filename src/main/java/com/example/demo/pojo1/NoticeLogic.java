package com.example.demo.pojo1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
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
	
}
