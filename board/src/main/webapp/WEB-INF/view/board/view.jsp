<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function edit() {
	location.href='edit.do?boardno=${data.boardno}';
}
</script>
</head>
<body>
<h1>게시글 상세</h1>
<table border = "1" >
<tr>
	<td>제목</td>
	<td>${data.title }</td>
</tr>
<tr>
	<td>작성일</td>
	<td>${data.regdate }</td>
</tr>
<tr>
	<td>내용</td>
	<td>${data.content }</td>
</tr>
<c:forEach var="file" items="${fileList }" varStatus="status">
<tr>
	<td>첨부파일${status.index +1 }</td>
	<td>${file.filename_org }</td>
</tr>
</c:forEach>
<tr>
	<td colspan="2">
		<input type="button" value="수정" onclick="edit();">
		<input type="button" value="삭제">
	</td>
</tr>

</table>
</body>
</html>