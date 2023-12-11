package com.example.demo.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller1 {
	// 세션 로그인 추상메소드 선언
	public Object login(HttpServletRequest req, HttpServletResponse res);
	public Object logout(HttpServletRequest req, HttpServletResponse res);
}
