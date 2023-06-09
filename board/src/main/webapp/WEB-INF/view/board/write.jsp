<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>게시글 등록</h1>
<form method="post" action="insert.do" enctype="multipart/form-data">
<table border = "1" >
<tr>
	<td>제목</td>
	<td><input type="text" name="title"></td>
</tr>
<tr>
	<td>내용</td>
	<td><textarea cols = "25" rows="5" name="content"></textarea></td>
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