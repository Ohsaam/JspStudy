package com.example.demo.pojo1;

public class ActionForward {
	private  String path = null;//캡슐화- 직접적인 접근을 막는다 - 메소드로 풀어준다 - 인스턴스화
	private boolean isRedirect = false;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
}
