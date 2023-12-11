<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<%@include file = "/common/quill_common.jsp"%>
</head>
<body>
<div id="editor">
  <p>Hello World!</p>
  <p>Some initial <strong>bold</strong> text</p>
  <p><br></p>
</div>

<!-- Include the Quill library -->
<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

<!-- Initialize Quill editor -->
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
const quill = new Quill('#editor', {
	  modules: { toolbar: toolbarOptions },				    
	  theme: 'snow',
	  placeholder: '글 작성하기',
});

// quill에디터에서 이미지를 클릭 했을 떄, 실제로 화면애서는 input 타입 file를 생성해주자.
// 이미지를 선택하면 선택하자마자(동기로) 백앤드에 요청을 Post방식으로 보낸다.
function selectLocalImage() {
    const fileInput = document.createElement('input');
    fileInput.setAttribute('type', 'file');
    console.log("input.type " + fileInput.type);

    fileInput.click();

    fileInput.addEventListener("change", function () {  // change 이벤트로 input 값이 바뀌면 실행
        const formData = new FormData();
        const file = fileInput.files[0];
        formData.append('uploadFile', file);

        $.ajax({
            type: 'post',
            enctype: 'multipart/form-data',
            url: '/board/register/imageUpload',
            data: formData,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (data) {
                const range = quill.getSelection(); // 사용자가 선택한 에디터 범위
                // uploadPath에 역슬래시(\) 때문에 경로가 제대로 인식되지 않는 것을 슬래시(/)로 변환
                data.uploadPath = data.uploadPath.replace(/\\/g, '/');

                quill.insertEmbed(range.index, 'image', "/board/display?fileName=" + data.uploadPath +"/"+ data.uuid +"_"+ data.fileName);

            },
            error: function (err) {
                console.log(err);
            }
        });

    });
}



</script>


</body>
</html>