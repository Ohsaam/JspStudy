package com.example.demo.pojo3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * select -> 유지 -> forward -> 유효범위:request
 * 
 * 1. select (List<Map>) -> jsp -> 유지 -> request -> 서블릿과 의존관계가있다.
 * 		1-1) List : Row
 * 		1-2) Map : Colum 
 * 
 * 2. 서블릿으로부터 완전한 독립이 안 된다. -> 불만 -> Spring 2.0
 * 
 * 3. request, response가 없이는 나는 웹 서비스를 구현할 수 없어요
 * 
 * 
 * 4. 전체조회를 하고 싶음 n건을 보여줘야함 
 * 	 	1. ModelAndView에 List<Map>이 필요한 이유
 * 		2. HttpServletRequest --> 나는 왜 주입을 받나요? (req,res)를 사용하기 위해서 
 * 				- req.setAttribute("list", new ArrayList<>());
 * 				- SELECT일떈, Object 타입이 필요하다 유지되어야한다.
 * 				- 자바와 jsp 사이에서 유지에 대해서 문제를 해결해야한다.
 * 				- ModelAndView는 어디서 사용하는가? -> 컨트롤러 에서 사용된다. --> 응답페이지에 대한 책임(관심사)는  MVC패턴 통해서 컨트롤 계층에 있다.
 * 				- 어디서 주입받죠? ActionServlet에서 주입받는다.(여기가 원천이다.) 얘가 진또배기
 * 
 * 				- xxxController는 서블릿이 아니다. Controller 인터페이스의 추상메소드로 구현 된 것 뿐이다. --> 실질적인 주인은 ActionServelt
 * 
 * 				- xxx Controller에서 if문으로 분기하고 있는데, 이 부분을 해결하기 위해서 HandlerMapping을 구현했다.
 * 
 */

//-> select -> List:배열-로우<Map:여러컬럼담기> -> jsp -> 유지 -> request -> 서블릿과 의존관계있다
//-> 서블릿으로 부터 완전한 독립이 안되었다 - >불만 - > spring 5.0
//- > request와 response가 없이는 나는 웹서비스를 구현할 수 없어요
//-> 나는 어떤 클래스에서 사용되나요? -> 응답페이지에 대한 책임(관심사-jsp)은 누가 있지? -> XXXController
//-> 응답 페이지 처리에 대한 관심사는 MVC패턴 중에서 컨트롤계층에 있다
//java(서블릿) -> jsp사이에서 유지문제 해결
//ModelAndView는 생성자를 통해서 request 받아온다
//어디서? 서블릿에서 받아옴 - Board2Controller
public class ModelAndView {
	//req.setAttribute("list", new ArrayList<>());
	HttpServletRequest req = null;//나는 어디서 주입을 받게 되나요?
	List<Map<String,Object>> list = new ArrayList<>();
	//컨트롤 클래스에서 결정된 화면의 이름을  담을 변수 선언
	String viewName = null;
	public ModelAndView(HttpServletRequest req) {
		this.req = req;
	}
	//컨트롤 클래스에서  화면이름이 결정되면 그 값을 읽고 쓸 수 있는 메소드 설계
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getViewName() {
		return viewName;
	}
	public void addObject(String name, Object obj) {
//		Map<String, Object> pMap = new HashMap<>();
//		pMap.put(name, obj);
//		req.setAttribute(name, obj);
//		list.add(pMap);
		Map<String,Object> pMap = new HashMap<>();
		pMap.put(name, obj);
		req.setAttribute(name, obj);
		list.add(pMap);
	}
	
}

/**
 * 모델andview는 생성자를 통해서 요청객체를 받아온다.
 * 어디서? 서블릿에서 받아온다.
 * 모델andview는 select시 에만 사용한다.
*/



