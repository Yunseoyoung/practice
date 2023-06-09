<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function view(boardno) {
	location.href='view.do?boardno=' +boardno;
}
</script>
</head>
<body>
<h1>게시글 목록</h1>
<table border="1">
<tr>
	<th>글번호</th>
	<th>제목</th>
	<th>작성일</th>
</tr>
<c:if test ="${empty boardList }">
<tr>
	<td colspan="3">등록된 글이 없습니다.</td>
</tr>
</c:if>
<c:forEach var="vo" items="${boardList }">
<tr onclick="view(${vo.boardno})" style="cursor:pointer"> 
	<td>${vo.boardno }</td>
	
	<td>${vo.title }</td>
	<td>${vo.regdate }</td>
</tr>
</c:forEach>
</table>
</body>
</html>