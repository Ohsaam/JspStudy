package com.example.demo.pojo2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.util.MyBatisCommonFactory;

/**
 * @ 1-1 에는 없는 XXXDao를 등판시켰다.
 */

public class BoardDao {
	Logger logger = Logger.getLogger(BoardDao.class);
	SqlSessionFactory sqlSessionFactory = null;
	
	public BoardDao() {
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
	}
	// 생성자를 이용해서 전역변수를 초기화하자.
	public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
		logger.info("BoardDao : boardList 메소드 안에 진입했습니다.");
		List<Map<String,Object>> bList = new ArrayList<>();
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {  
			bList = sqlSession.selectList("boardList", pMap);
			logger.info(bList.toString());
		} catch (Exception e) {
			logger.info(e.toString());
		}
		
		return bList;
	}

	public int boardInsert(Map<String, Object> pMap) {
		logger.info("BoardDao : boardInsert 메소드 안에 진입했습니다.");
		int result = 0;
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			result = sqlSession.insert("boardInsert", pMap);			
			sqlSession.commit();//빼먹으면 물리적인테이블 반영안됨
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return result;
	}

	public int boardUpdate(Map<String, Object> pMap) {
		logger.info("BoardDao : boardUpdate 메소드 안에 진입했습니다.");
		int result = 0;
		
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			result = sqlSession.update("boardUpdate", pMap);			
			
			/**
			 * 이 부분에서 읽어오질 못하고 있ㅇ므 
			 */
			sqlSession.commit();//빼먹으면 물리적인테이블 반영안됨
		} catch (Exception e) {
			logger.info(e.toString());
		}		
		return result;
	}

	public int boardDelete(Map<String, Object> pMap) {
		logger.info("BoardDao : boardDelete 메소드 안에 진입했습니다.");
		int result = 0;
		sqlSessionFactory = MyBatisCommonFactory.getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			int b_no = Integer.parseInt(pMap.get("b_no").toString());			
			result = sqlSession.delete("boardDelete", b_no);			
			logger.info(result);
			sqlSession.commit();//빼먹으면 물리적인테이블 반영안됨
		} catch (Exception e) {
			logger.info(e.toString());
		}		
		return result;
	}
}
