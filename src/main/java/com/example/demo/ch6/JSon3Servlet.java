package com.example.demo.ch6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

//확장자는 중요하지 않다. 
//이 문서가 무엇인지는 마임타입으로 결정됨
@WebServlet("/mime/json3.do")
public class JSon3Servlet extends HttpServlet {
	Logger logger = Logger.getLogger(JSon2Servlet.class);
	//jsonTest.jsp페이지를 요청할 것 외에는 한일이 아무것도 없다???
	//서블릿은 경유 했지만 실제로 무엇을 했나? - 한게 없다
	//유지문제가 포함되지 않았다. - 학습목표 - forward호출해서 - RequestDispatcher
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		logger.info("doGet");
		
		//유지하려면 일단 서블릿에서 그 데이터셋을 쥐고 있어야 넘길 수 있다.
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
		//여기가 포인트 - 여기에 쓸 낱말 카드를 나는 알고 있다. - scope - 유효범위 - request
		req.setAttribute("list", list);
		RequestDispatcher view = req.getRequestDispatcher("./json2Test.jsp");
		view.forward(req, res);//req와 res 원본이 넘겨져야 한다 - 왜냐면 그래야 서블릿에서 쥐고 있는 데이터셋을 화면에서 출력할 수 있으니까...
	}

}
