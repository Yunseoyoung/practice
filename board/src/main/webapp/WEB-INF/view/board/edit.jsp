<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function fileDelete(fileno) {
	if(confirm('이 파일을 삭제하시겠습니까?')) {
		$.ajax({
			url:'deleteFile.do?fileno='+ fileno,
			success : function(res) {
				if (res.trim() == 'ok') {
					alert('정상적으로 삭제되었습니다.');
					$(".file"+fileno).remove();
				}
			}		
		})	
	}
}
</script>
</head>
<body>
<h1>게시글 수정</h1>
<form method="post" action="update.do" enctype="multipart/form-data">
<input type="hidden" name="boardno" value="${data.boardno }">
<table border = "1" >
<tr>
	<td>제목</td>
	<td><input type="text" name="title" value="${data.title }"></td>
	
</tr>
<tr>
	<td>내용</td>
	<td><textarea cols = "25" rows="5" name="content">${data.content }</textarea></td>
</tr>
<tr>
	<td>기존파일</td>
	<td>
		<c:forEach var="f" items="${fileList }">
			<div class="file${f.fileno }"style="cursor:pointer;" onclick="fileDelete(${f.fileno});">${f.filename_org }[x]</div>
		</c:forEach>
	</td>
</tr>
<tr>
	<td>첨부파일1</td>
	<td><input type="file" name="filename1"></td>
</tr>
<tr>
	<td>첨부파일2</td>
	<td><input type="file" name="filename2"></td>
</tr>
<tr>
	<td>첨부파일3</td>
	<td><input type="file" name="filename3"></td>
</tr>
<tr>
	<td colspan="2">
		<input type="submit" value="저장">
		</td>
</tr>

</table>
</form>
</body>
</html>