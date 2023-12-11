package com.example.demo.separator;

import org.apache.log4j.Logger;

//순수한 자바 클래스  - 이식성 관련
//요청에 대한 처리를 전담하는 클래스로 설계하였다
//다른 프레임워크로 이관작업이 되더라도 최소한의 코드 수정만으로 적용되기를 원함.
//실력차이를 최소화 하기 위해서 라도 프레임워크는 필수임
public class Login1Logic {
	Logger logger = Logger.getLogger(Login1Logic.class);

	public String login(String mem_id, String mem_pw) {
		String mem_name = null;
		String db_id = "kiwi";
		String db_pw = "123";
		String db_name = "키위";
		if(mem_id.equals(db_id)) {
			if(mem_pw.equals(db_pw)) {
				mem_name = db_name;
			}else {
				mem_name="비밀번호가 맞지 않습니다.";
			}
		}else {
			mem_name="아이디가 존재하지 않습니다.";
		}
		return mem_name;
	}////////// end of login
}/////////////end of Login1Logic
