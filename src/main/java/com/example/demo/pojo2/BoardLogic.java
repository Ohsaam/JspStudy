package com.example.demo.pojo2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.example.demo.pojo1.NoticeLogic;

public class BoardLogic {
	Logger logger = Logger.getLogger(BoardLogic.class);
	BoardDao bDao = new BoardDao();

	public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
		logger.info("BoardLogic : boardList 메소드 안에 진입했습니다.");
		List<Map<String,Object>> bList = new ArrayList<>();
		bList = bDao.boardList(pMap); 
		// 이전에는 sqlsession으로 바로접근했는데, 지금은 Dao를 만들어그렇지않음.
		return bList;
	}

	public int boardInsert(Map<String, Object> pMap) {
		logger.info("BoardLogic : boardInsert 메소드 안에 진입했습니다.");
		int result = 0;
		result = bDao.boardInsert(pMap);
//		result2 = mDao.memberUpdate(pMap) -- 이 부분에서 다른 Dao로 접근한다.트랜잭션 처리 대상이다.
		return result;
	}

	public int boardUpdate(Map<String, Object> pMap) {
		logger.info("BoardLogic : boardUpdate 메소드 안에 진입했습니다.");
		int result = 0;
		result = bDao.boardUpdate(pMap);
		return result;
	}

	public int boardDelete(Map<String, Object> pMap) {
		logger.info("BoardLogic : boardDelete 메소드 안에 진입했습니다.");
		int result = 0;
		result = bDao.boardDelete(pMap);
		return result;
	}


}
