package com.example.demo.pojo2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


public class BoardLogic {
	Logger logger = Logger.getLogger(BoardLogic.class);
	BoardDao bDao = new BoardDao();
	public List<Map<String, Object>> boardList(Map<String, Object> pMap) {
		logger.info("boardList");
		//웹개발에서는 NullPointerException이 발동하면 화면자체가 안열림 - 막막함
		//어떤 힌트를 보고 문제를 예측해서(추측) 하나씩 가능성을 제거해 나가는 과정을 통해서 트러블슈팅을 완성함
		//화면은 출력이 된다
		List<Map<String, Object>> bList = new ArrayList<>();//NullPointerException방어코드로
		bList = bDao.boardList(pMap);
		return bList;//화면과 로직을 분리하자 - > POJO 설계해 본다
	}
	//아래 메소드는 트랜잭션처리 대상이다
	//업무적인 depth가 깊을 수록 이런상황이 발생된다
	//AOP{수평적인 관점 관계설정, 개입, 처리가능하게 지원}(<->  OOP-상하관계)는 프레임워크의 한 종류로 공통된 관심사에 대해서 클래스의 어떤 지점을 
	//접근하고 추가코드 없이 자동으로 어떤 일 처리를 가능하게 해주는 프로그래밍 기법이다
	public int boardInsert(Map<String, Object> pMap) {
		logger.info("boardInsert");
		int result = 0;
		result = bDao.boardInsert(pMap);
		//result2 = mDao.memberUpdate(pMap);
		return result;
	}
	public int boardUpdate(Map<String, Object> pMap) {
		logger.info("boardUpdate");
		int result = 0;
		result = bDao.boardUpdate(pMap);
		return result;
	}
	public int boardDelete(Map<String, Object> pMap) {
		logger.info("boardDelete");
		int result = 0;
		result = bDao.boardDelete(pMap);
		return result;
	}
}