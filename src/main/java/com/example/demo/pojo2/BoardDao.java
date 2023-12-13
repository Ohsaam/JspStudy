package com.example.demo.pojo2;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @ 1-1 에는 없는 XXXDao를 등판시켰다.
 */

public class BoardDao {
	Logger logger = Logger.getLogger(BoardDao.class);
	
	public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
		logger.info("BoardDao : boardList 메소드 안에 진입했습니다.");
		
		return null;
	}

	public int boardInsert(Map<String, Object> pMap) {
		logger.info("BoardDao : boardInsert 메소드 안에 진입했습니다.");
		int result = 0;
		return result;
	}

	public int boardUpdate(Map<String, Object> pMap) {
		logger.info("BoardDao : boardUpdate 메소드 안에 진입했습니다.");
		int result = 0;
		return result;
	}

	public int boardDelete(Map<String, Object> pMap) {
		logger.info("BoardDao : boardDelete 메소드 안에 진입했습니다.");
		int result = 0;
		return result;
	}
}
