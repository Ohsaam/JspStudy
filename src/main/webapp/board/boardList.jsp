<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.util.BSPageBar" %>    
<%
	List<Map<String,Object>> bList = (List)request.getAttribute("bList");
	int size = 0;//총레코드 수
	if(bList !=null){
		size = bList.size();//4출력
	}
	//out.print(bList);
	
	//1(26,25,24,23,22) 2(21,20,19,18,17) 3(16,15,14,13,12) > 4(11) 5 6
	
	//한 페이지에 몇개씩 뿌릴거야?
	int numPerPage = 3;
	int nowPage = 0;
	if(request.getParameter("nowPage")!=null){
		nowPage = Integer.parseInt(request.getParameter("nowPage"));
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판(webapp)</title>
<!-- 
WAS란?
TOMCAT(웹서버{apache사용함-정적페이지}+웹컨테이너:jsp-api.jar:서블릿변환, servlet-api.jar:컴파일)
1)액션태그 - a_jsp.java, b_jsp.java
: 파일이 두 개로 생성됨. 처리 결과가 a.jsp에 반영됨.  제어권은 a.jsp에게 있다
:주소가 바뀌지 않는다, 요청이 계속 유지되는 것으로 판단함
2)include directive방식 - a_jsp.java : jsp파일은 두갠데 서블릿은 한개다
역할
: 인증에 관련된 코드는 서비스 개시 거의 직전에 추가함
: 보통 보안과 관련된 업체는 별도로 선정하고 요구사항을 수렴하는 시스템이어야 함
 -->
    <%@include file="/common/bootstrap_common.jsp" %>
	<link rel="stylesheet" href="/css/board.css">
	<script type="text/javascript">
		const searchEnter = (event) =>{
			console.log('searchEnter')
			console.log(window.event.keyCode);//13
			if(window.event.keyCode == 13){
				boardSearch();//재사용성 - 				
			}
		}// end of searchEnter
		
		const boardSearch = () => {
			console.log('boardSearch');
			const gubun = document.querySelector("#gubun").value;
			const keyword = document.querySelector("#keyword").value;
			console.log(`${gubun} , ${keyword}`);
			location.href="/board/boardList.gd2?gubun="+gubun+"&keyword="+keyword;
		}// end of boardSearch	
		
		const boardList = () => {
			location.href="/board/boardList.gd2";
		}
		
		const boardInsert = () => {
			//<input type=text name=b_title/>
			//<input type=text name=b_content/>
			//<input type=text name=b_writer/>
			//Enumeration<String> em = req.getParameterNames();
			document.querySelector("#f_board").submit();//form태그에 묶인 컴포넌트값들이 전송됨
		}
		
		const fileDown = (b_file) => {//b_file=avatar24.png
			location.href="downLoad.jsp?b_file="+b_file;
		}
	</script>
</head>
<body>
<!-- header start -->
	<%@include file="/include/gym_header.jsp"%>
	<!-- header end    -->
	<!-- body start    -->
	<div class="container">
		<div class="page-header">
			<h2>게시판 <small>게시글목록</small></h2>
			<hr />
		</div>
		<!-- 검색기 시작 -->
		<div class="row">
			<div class="col-3">
		    	<select id="gubun" class="form-select" aria-label="분류선택">
		      		<option value="none">분류선택</option>
		      		<option value="b_title">제목</option>
		      		<option value="b_writer">작성자</option>
		      		<option value="b_content">내용</option>
		    	</select>			
		  	</div>
			<div class="col-6">
		 		<input type="text" id="keyword" class="form-control" placeholder="검색어를 입력하세요" 
		           aria-label="검색어를 입력하세요" aria-describedby="btn_search" onkeyup="searchEnter()"/>
			</div>
			<div class="col-3">
		 		<button id="btn_search" class="btn btn-danger" onclick="boardSearch()">검색</button>
		 	</div>
		</div>		
		<!-- 검색기 끝 -->
		
		<!-- 회원목록 시작 -->
		<div class='board-list'>
			<table class="table table-hover">
		    	<thead>
		      		<tr>
		        		<th width="10%">#</th>
		        		<th width="40%">제목</th>
		        		<th width="20%">첨부파일</th>
		        		<th width="15%">작성자</th>
		        		<th width="15%">조회수</th>
		      		</tr>
		    	</thead>
		    	<tbody>	      	
<%
	//스크립틀릿 - 지변이다, 메소드 선언불가, 생성자 선언불가, 실행문
	//n건을 조회하는 경우이지만 resultType에는 map이나 vo패턴을 주는게 맞다
	//주의사항 - 자동으로 키값을 생성함 - 디폴트가 대문자이다
	//myBatis연동시 resultType=map{한행}으로 줌 -> selectList("noticeList", pMap)
	//for(int i=0;i<size;i++){
	for(int i=nowPage*numPerPage;i<(nowPage*numPerPage)+numPerPage;i++){
		if(size == i) break;	
		Map<String,Object> rmap = bList.get(i);
%>					
					<tr>
						<td><%=rmap.get("B_NO") %></td>
						<td><%=rmap.get("B_TITLE") %></td>
						<td>
						<!-- 자바스크립트에서는 값 앞뒤에 싱글 또는 더블퀘테이션을 붙이지 않으면 변수 취급받음
						XXXX is undefined 에러 보게된다
						 -->
						<a href="javascript:fileDown('<%= rmap.get("B_FILE") %>')"><%=rmap.get("B_FILE") %></a>
						</td>
						<td><%=rmap.get("B_WRITER") %></td>
						<td><%=rmap.get("B_HIT") %></td>
					</tr>					
<%
	}
%>		    	
		    	</tbody>
			</table> 	
<hr />  
<!-- [[ Bootstrap 페이징 처리  구간  ]] -->
	<div style="display:flex;justify-content:center;">
	<ul class="pagination">
<%
	String pagePath = "boardList.gd2";
	BSPageBar bspb = new BSPageBar(numPerPage,  size, nowPage, pagePath);
	out.print(bspb.getPageBar());
%>	
	</ul>
	</div>
<!-- [[ Bootstrap 페이징 처리  구간  ]] -->		  
		  	<div class='board-footer'>
		    	<button class="btn btn-warning" onclick="boardList()">
		      		전체조회
		    	</button>&nbsp; 
			    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#boardForm">
			    글쓰기
			    </button>
		    </div>
		</div>		
		<!-- 회원목록   끝  -->		
		
	</div>
	<!-- body end       -->
	<!-- footer start -->
	<%@include file="/include/gym_footer.jsp"%>
	
	<!-- ========================== [[ 게시판 Modal ]] ========================== -->
	<div class="modal" id="boardForm">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">게시판</h4>
	        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	      </div>
	      <!-- Modal body -->
	      <div class="modal-body">
	      	<!-- <form id="f_board" method="get" action="./boardInsert.pj2"> -->
	      	<form id="f_board" method="post" enctype="multipart/form-data" action="./boardInsert.gd2">
	      	  <input type="hidden" name="method" value="boardInsert">
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="b_title" name="b_title" placeholder="Enter 제목" />
	            <label for="b_title">제목</label>
	          </div>	      	
	          <div class="form-floating mb-3 mt-3">
	            <input type="text"  class="form-control" id="b_writer" name="b_writer" placeholder="Enter 작성자" />
	            <label for="b_writer">작성자</label>
	          </div>	      	
	          <div class="form-floating mb-3 mt-3">
	            <textarea rows="5" class="form-control h-25" aria-label="With textarea" id="b_content" name="b_content"></textarea>
			  </div>	
		      <div class="input-group mb-3">
				  <input type="file" class="form-control" id="b_file" name="b_file">
				  <label class="input-group-text" for="b_file">Upload</label>
			  </div>      	
	      	</form>
	      </div>	
	      <div class="modal-footer">
	        <input type="button" class="btn btn-warning" data-bs-dismiss="modal" onclick="boardInsert()"  value="저장">
	        <input type="button" class="btn btn-danger" data-bs-dismiss="modal" value="닫기">
	      </div>
	
	    </div>
	  </div>
	</div>
    <!-- ========================== [[ 게시판 Modal ]] ========================== -->	
	
</body>
</html>