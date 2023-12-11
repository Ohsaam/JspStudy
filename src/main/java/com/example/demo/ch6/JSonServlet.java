package com.example.demo.ch6;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
//확장자는 중요하지 않다. 
//이 문서가 무엇인지는 마임타입으로 결정됨
@WebServlet("/mime/json.do")
public class JSonServlet extends HttpServlet {
	Logger logger = Logger.getLogger(JSonServlet.class);
	//URL패턴이 필요한데? -> 1:exactly,2:/basic/*,3:*.do,4: / -root directory
	//-> 테스트 URL패턴 적어보기
	//-> http://localhost:8000/mime/json.do 엔터 - >get방식만 가능함 . post는 이렇게 요청 할 수 없다(나는 설명할 수 있다)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//res.setContentType("application/json");//JSON포맷 - 마임타임 -> 데이터셋 역할 ->  ReactJS와 같은 UI솔루션이나 라이브러리 비벼진다
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> rmap = new HashMap<>();//SELECT deptno, dname, loc FROM dept
		rmap.put("deptno", 10);
		rmap.put("dname", "총무부");
		rmap.put("loc", "서울");
		list.add(rmap);
		rmap = new HashMap<>();//SELECT deptno, dname, loc FROM dept
		rmap.put("deptno", 20);
		rmap.put("dname", "개발부");
		rmap.put("loc", "인천");
		list.add(rmap);
		rmap = new HashMap<>();//SELECT deptno, dname, loc FROM dept
		rmap.put("deptno", 30);
		rmap.put("dname", "운영부");
		rmap.put("loc", "서울");
		list.add(rmap);
		//Front -End와 Back-End - 만나는 부분에 처리이다(mime type역할이 있다)
		//서블릿이라고 쓰고 JSON이라고 읽는다
		//확장자 이름에 속지 말고 마임타입을 확인 할 것
		//리액트의 어떤 코드(언어가-자바스크립트- 전송 - (get,post)-fetch, axios, ajax)가 이 URL패턴을 가져가야 하나?
		Gson g = new Gson();
		String temp = g.toJson(list);
		//무엇을 봤나요?
		//out.print(list);//JSON형식에 맞지 않아 - 키와값사이에 대입연산자 있어서.... ReactJS
		out.print(temp);
	}

}
