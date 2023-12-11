package com.example.demo.pojo1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//나는 추상메소드만 가질 수  있다. - 나는 클래스의 명세서 역할을 담당할 뿐이다.??
//구현할 수 없다.-왜냐면 결정되지 않았으니까? - 뭐가요? - 디바이스
public interface Action {
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException;
}
