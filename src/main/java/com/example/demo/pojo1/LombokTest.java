package com.example.demo.pojo1;

import com.vo.EmpVO;
import com.vo.NoticeVO;

public class LombokTest {
	void methodA() {
		NoticeVO nvo = 
				NoticeVO.builder().n_no(3)
				.n_title("제목").n_content("내용").n_writer("작성자")
				.build();
		System.out.println(nvo.getN_writer());
		NoticeVO nvo2 = 
				NoticeVO.builder().n_no(3)
				.build();
		System.out.println(nvo2.getN_writer());
		NoticeVO nvo3 = 
				NoticeVO.builder()
				.n_title("제목").n_content("내용").n_no(3).n_writer("작성자")
				.build();
		System.out.println(nvo3.getN_content());
	}
	public static void main(String[] args) {
		LombokTest lt = new LombokTest();
		lt.methodA();
		NoticeVO nvo = new NoticeVO();
		//getter/setter를 정의하지 않아도 즉시 사용이 가능함
		nvo.setN_title("휴관공지");//n_title전역변수에 휴관공지가 담김
		nvo.setN_title("");
		System.out.println(nvo.getN_title());//28번에서 담은 휴관공지 문자열 출력됨
		EmpVO evo = new EmpVO();
		evo = new EmpVO(7566);
		//evo = new EmpVO(7566,"SCOTT");
		//evo = new EmpVO(7566,"SCOTT", "2000-10-25");
	}

}
