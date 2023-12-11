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
</script>


</body>
</html>