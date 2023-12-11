<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@include file="/common/quill_common.jsp" %>
</head>
<body>
	<!-- Create the editor container -->
	<div id="editor22">
	  <p>Hello World!</p>
	  <p>Some initial <strong>bold</strong> text</p>
	  <p><br></p>
	</div>
	<!-- Initialize Quill editor 
	브라우저가 DOM Tree그린다
	CSS 포함하는 DOM Tree 그린다
	출력됨
	-->
	<script>
	const toolbarOptions = [
		  ['bold', 'italic', 'underline'],        // toggled buttons
		  ['blockquote', 'code-block'],
		  [{ 'list': 'ordered'}, { 'list': 'bullet' }],
		  [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
		  [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
		  [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
		  [{ 'font': [] }],
		  [{ 'align': [] }],
		  ['link', 'image']
		];				
	  const quill = new Quill('#editor22', {
		  modules: { toolbar: toolbarOptions },				    
		  theme: 'snow',
		  placeholder: '글 작성하기',
	  });
	  const  selectLocalImage = (event) =>  {
		  console.log('selectLocalImage');
		  //e.preventDefault();이벤트 버블링 방어 코드 작성 - submit이슈, <button type=submit|button>
		  //quill에디터에서 이미지를 클릭했을 때 실제 화면에서는 <input type=file>생성해 주자
		  //이미지를 선택하면 선택하자마자 백엔드에 요청을 post방식으로 넘긴다 - 8000번 서버의 pds폴더에 선택한 이미지를 업로드 처리함
		  //파일을 (바이너리코드) 전송할땐 무조건 post방식으로 해야 함
		    const fileInput = document.createElement('input');//<input> - DOM API의 createElement를 사용하여 태그 생성하는 코드
		    fileInput.setAttribute('type', 'file');//<input type=file>
		    console.log("input.type " + fileInput.type);
		    //이미지 파일만 선택가능하도록 제한
		    fileInput.setAttribute("accept", "image/*");//* -> image/png , image/gif, image/jpg
		    fileInput.setAttribute("name", "image");//req.getParameter("image"); -> <input name="image"/>
		    fileInput.click();

		    fileInput.addEventListener("change", function () {  // change 이벤트로 input 값이 바뀌면 실행
		        const formData = new FormData();
		        const file = fileInput.files[0];
		        formData.append('image', file);

		        $.ajax({//<form method=post enctype=multipart/form-data/> 이럴경우 req.getParameter사용이 불가함 -> cos.jar
		            type: 'post',
		            enctype: 'multipart/form-data',
		            url: '/notice/imageUpload.gd',
		            data: formData,
		            processData: false,
		            contentType: false,
		            success: function (response) {
						console.log('avatar.png'+response);

		            },
		            error: function (err) {
		                console.log(err);
		            }
		        });//////////////////// end of ajax

		    });////////////////////// end of onchange 이벤트 핸들러
		}////////////////////////// end of selectLocalImage	  
		//html 가져오기
		const html = quill.root.innerHTML;
		console.log(html);
		quill.on('text-change', (delta, oldDelta, source) => {
			  console.log('글자가 입력될때 마다 호출');
			  console.log(quill.root.innerHTML);
			  //console.log(source);//user
			  //console.log(delta);
			  //console.log(oldDelta);
		}); ////////////// end of onchage - 텍스트 내용이 변경되었을 때 발동
		quill.getModule('toolbar').addHandler('image',  () => {
			console.log('image가 변경되었을때');
			selectLocalImage();
		})
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	</script>
</body>
</html>