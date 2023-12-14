package com.util;

import org.apache.log4j.Logger;

/**
 * StringBuilder를 사용한 이유는 String 사용 시 + 하게 되면 원본은 수정되지않고 계속 객체가 생성되니깐 
 * StringBuilder 를 사용함.
 */

public class BSPageBar {
	Logger logger = Logger.getLogger(BSPageBar.class);
	//전체레코드 갯수
	private int totalRecord;//list.size():47row
	//페이지당 레코드 수
	private int numPerPage;// 10개씩이다
	//블럭당 디폴트 페이지 수 - 여기서는 일단 2개로 정함.
	private int pagePerBlock=4;
	//총페이지 수
	private int totalPage;
	//총블럭 수
	private int totalBlock;
	//현재 내가 바라보는 페이지 수
	private int nowPage;
	//현재 내가 바라보는 블럭 수
	private int nowBlock;
	//적용할 페이지 이름
	private String pagePath;
	
	private String pagination;
	//페이지 네비게이션 초기화
	/*
	 * 화면에서 받아와야 하는 정보에는 어떤 것들이 있을까?
	 * 페이지에 뿌려질 로우의 수 numPerPage
	 * 전체 레코드 수 totalRecord
	 * 현재 내가 바라보는 페이지 번호 nowPage
	 * 내가 처리해야할 페이지 이름 pagePath
	 * 
	 * 공식을 세우는데 필요한 인자는 누구?
	 * 
	 * 세워진 공식들은 어디에서 적용하면 되는 거지?
	 * 
	 * 화면에 내보내 져야 하는 언어는 html 아님 자바 중에서 ?????
	 * html
	 * 내보내지는 정보는 어디에 담으면 될까?
	 * 
	 */
	public BSPageBar(int numPerPage, int totalRecord, int nowPage, String pagePath) {
		this.numPerPage = numPerPage;
		this.totalRecord = totalRecord;
		this.nowPage = nowPage;
		this.pagePath = pagePath;
		/**
		 * pagePath 공지사항 페이징처리인가 아님 게시판에 페이징처리인가?
		 * 1. /notice/noticeList.gd 전체 레코드의 숫자에 따라서 내가 위치한 값이 달라지니깐 매 번 계산이 되어야한다.
		 * 2. 만약에 이렇게 온다면 에러다.
		 * 
		 *  /notice/noticeList.gd?&
		 *  -> /notice/noticeList.gd? 
		 *  -> &nowPage 이걸 쓰면 터진다. 
		 *  -> 왜? /notice/noticeList.gd? &nowPage 이렇게 나오니깐 
		 *  ->?
		 * 
		 */
		this.totalPage = 
				(int)Math.ceil((double)this.totalRecord/this.numPerPage);// 47.0/10=> 4.7 4.1->5page 4.2->5page
		//  전체페이지 === 전체레코드(총 저장되어있는 행) 개수 / 페이지당 (저장되어있는 행 개수)레코드 수 
		//  각각 객체를 나눠서 페이지를 구한것. (전체 객체/ 몇개씩 슬라이싱?) 
		this.totalBlock= 
				(int)Math.ceil((double)this.totalPage/this.pagePerBlock);//5.0/2=> 2.5-> 3장
		//   전체페이지/블럭당페이지 디폴트 수 ===>totalblock이 나옴
		
		
		//현재 내가바라보는 페이지 : (int)((double)4-1/2)
		this.nowBlock = (int)((double)this.nowPage/this.pagePerBlock);
		// 현재내가 바라보고 있는 페이지 수(몇번에 속해있는지) /  블럭당 디폴트 페이지 수
		// 내가 어디에 소속되어있는 페이지"블럭"를 얘기한다.
		
		
	}
	//setter메소드 선언
	public void setPageBar() {
		StringBuilder pageLink = new StringBuilder();
		//전체 레코드 수가 0보다 클때 처리하기
		if(totalRecord>0) {
			//nowBlock이 0보다 클때 처리
			//이전 페이지로 이동 해야 하므로 페이지 번호에 a태그를 붙여야 하고
			//pagePath뒤에 이동할 페이지 번호를 붙여서 호출 해야함.
			
			
			//"이전" 버튼을 생성하는 로직
			if(nowBlock > 0 ) {                        // 첫 번째 블록이 아닌 경우에만 이전 블록으로 이동하는 버튼을 생성
				pageLink.append("<li class='page-item'>");
				pageLink.append("<a class='page-link' href='"+pagePath+"&nowPage="+((nowBlock-1)*pagePerBlock+(pagePerBlock-1))+"'>");
				// ((nowBlock-1)*pagePerBlock+(pagePerBlock-1))은 이전 블록의 마지막 페이지를 의미한다.
				// nowBlock = 2
				// totalBlock = 3
				// pagePerBlock = 4
				
				/**
				 *  <a class='page-link' href='...?nowPage=7'>
				 */
				
				
				// href = board/boardList&nowPage=
				pageLink.append("<span aria-hidden='true'>&laquo;</span>");
				pageLink.append("</a>");
				pageLink.append("</li>");
			}
			for(int i=0;i<pagePerBlock;i++) {
				/**
				 * 현재 내가보고있는 블록 * 페이지당블록 + i == nowPage
				 * nowBlock * pagePerBlock + i == nowPage
				 *  --> 페이지당블록이라는게 무슨말이지? 몇개로쪼갤거냐, 몇개식 쪼갤거냐 
				 *  --> pagePerBlock은 페이지 바에서 하나의 블록 또는 그룹에 표시되는 페이지 수를 결정하는 변수이다.
				 *  (1*4+0) == nowPage(내 페이지)
				 * nowPage는 현재 사용자가 보고 있는 페이지 블록에서 해당하는 페이지 번호를  나타낸다.와 같으면
				 * nowBlock는 현재 사용자가 위치한 페이지 블록
				 * i는 현재 루프에서 처리 중인 페이지가 속한 위치(0,1,2,3) 이라고 하면 여기에 속하는 페이지넘버를 의미한다. 1번게시글2번게시글
				 * 
				   현재사용자가 위치한 페이지 블록 * 페이지 바에서 하나의블록 페이지의 수 * 페이지처리하고있는 페이지가 속한 위치 == nowPage(현재 보고 있는 페이지의 번호)
				  페이지 네비게이션에서 현재 사용자가 보고 있는 페이지에 대해 특별한 표시를 하는 데 사용 
				 */
				if(nowBlock*pagePerBlock+i==nowPage) {
					pageLink.append("<a class='page-link'>"+(nowBlock*pagePerBlock+i+1)+"</a>");
				}
				
				/**
				 *   nowBlock = 2, pagePerBlock = 4, i = 1, nowPage = 9라고 가정한다.
					 nowBlock * pagePerBlock + i = 2 * 4 + 1 = 9
					 nowPage = 9
					위 두 값이 같으므로 아래의 링크가 생성된다.
					
					<a class='page-link'>10</a> 
					nowBlock*pagePerBlock+i+1 = 2*4+1+1 ==> 10
					이 조건에 걸리는 경우면, 이 pageLink는 a태그 처리가 되어있지않다.
				 */

				//그렇지 않을 때를 나누어 처리해야 함.
				else { 
// 페이지 네비게이션에서 현재 사용자가 보고 있는 페이지에 대해 특별한 표시를 하는 데  두 개가 같지 않은 경우
// 현재사용자가 위치한 페이지 블록 * 페이지 바에서 하나의블록 페이지의 수 * 페이지처리하고있는 페이지가 속한 위치 !!== nowPage(현재 보고 있는 페이지의 번호) 같지 않을경우
// 사용자가 보고 있는 페이지 블록에서 현재 루프에서 처리 중인 페이지가 현재 보고 있는 페이지의 번호와 같지 않을 때 실행
// 보려고 한 페이지가 다음에 있을경우 a태그를 눌러 이동해야되기 떄문에 설정한다.
					
					pageLink.append("<a class='page-link' href='"+pagePath+"&nowPage="+((nowBlock*pagePerBlock)+i)+"'>"+((nowBlock*pagePerBlock)+i+1)+"</a>");
					// 그렇지 않을 경우에는 페이지를 클릭하면 경로를 이동해야되기 때문에 경로를 추가한다.
					/**
					 * 예시
					 * 가정:
								nowBlock = 2
								pagePerBlock = 4
								i = 1
								nowPage = 9
								pagePath = "/example"
					 *
					 *[계산]
					 * (nowBlock * pagePerBlock) + i = (2 * 4) + 1 = 9
						(nowBlock * pagePerBlock) + i + 1 = 9 + 1 = 10
					 * 
					 * <a class='page-link' href='/example&nowPage=9'>10</a>
					 *  사용자에게 페이지 10으로 이동할 수 있는 링크를 제공
					 *  만약 사용자가 이 링크를 클릭하면 "/example&nowPage=9" 경로로 이동하게된다.
					 */
					

				}
				//모든 경우에 pagePerBlock만큼 반복되지 않으므로 break처리해야 함.
				//주의할 것.
				
				
				/**
				 * 현재 생성되는 페이지링크의 수는 pagePerBlock에 의해 결정된다.
				 * 마지막 블록에서 남은 페이지 수가 "pagePerBlock" 보다 작을 수 있다.
				 * 이 때 추가적인 생성을 하지 않아도 되기 때문에, break문을 사용한다.
				 * ex
				 * pagePerBlock : 4 이고  총 페이지 수 (totalPage)가 15일 경우
				 * 마지막 블록에서는 12/// ===> 13,14,15만 생성되어야한다. 
				 */
				if((nowBlock*pagePerBlock)+i+1==totalPage) break;
				// 현재 블록에서 루프변수 처리중인 페이지에 해당하는 전체 페이지 번호를 나타낸다.

				/* nowBlock = 2,pagePerBlock = 4,totalPage = 15
				 * i = 0 일 때: (2 * 4) + 0 + 1 = 9 
				 * i = 1 일 때: (2 * 4) + 1 + 1 = 10 
				 * i = 2 일 때: (2* 4) + 2 + 1 = 11 
				 * i = 3 일 때: (2 * 4) + 3 + 1 = 12
				 */
				
				
			}
			//현재 블록에서 다음 블록이 존재할 경우 이미지 추가하고 페이지 이동할 수 있도록
			//a태그 활용하여 링크 처리하기
			//다음버튼을 생성하는 로직이다.
			if(totalBlock > nowBlock+1) {
				pageLink.append("<li class='page-item'>");
				pageLink.append("<a class='page-link' aria-label='Next' href='"+pagePath+"?nowPage="+((nowBlock+1)*pagePerBlock)+"'>");
				pageLink.append("<span aria-hidden=\"true\">&raquo;</span>");
				pageLink.append("</a>");	
				pageLink.append("</li>");
			}
			/**
			 * ((nowBlock+1)*pagePerBlock)은 다음 블록의 시작 페이지이다.
			 * 
			 * nowBlock = 2
			 * totalBlock = 3
			 * pagePerBlock = 4
			 *  <a class='page-link' aria-label='Next' href='...?nowPage=12'>
			 *  이 링크를 클릭하면 현재 페이지 블록에서의 다음 블록으로 이동한다.
			 *  
			 * 
			 */
		}
		logger.info("pageLink.toString():"+pageLink.toString());
		pagination = pageLink.toString();
	}
	//getter메소드 선언
	public String getPageBar() {
		this.setPageBar();
		return pagination;
	}
}