package com.example.demo.pojo2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.example.demo.pojo1.NoticeLogic;

public class BoardLogic {
	Logger logger = Logger.getLogger(BoardLogic.class);

	public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
		logger.info("BoardLogic : boardList 메소드 안에 진입했습니다.");
		List<Map<String,Object>> bList = new ArrayList<>();
		return null;
	}

	public int boardInsert(Map<String, Object> pMap) {
		logger.info("BoardLogic : boardInsert 메소드 안에 진입했습니다.");
		int result = 0;
		return result;
	}

	public int boardUpdate(Map<String, Object> pMap) {
		logger.info("BoardLogic : boardUpdate 메소드 안에 진입했습니다.");
		int result = 0;
		return result;
	}

	public int boardDelete(Map<String, Object> pMap) {
		logger.info("BoardLogic : boardDelete 메소드 안에 진입했습니다.");
		int result = 0;
		
		return result;
	}


}
