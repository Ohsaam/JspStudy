package com.example.demo.pojo2;

public class BoardVOTest {

	public static void main(String[] args) {
		BoardVO bvo = new BoardVO();
		bvo.setB_title("제목333");
		System.out.println(bvo.getB_title());
		BoardVO bvo2 = BoardVO.builder().b_no(4).b_title("gdgd").b_writer("jihwan").build();
		System.out.println(bvo2);
		

	}

}
