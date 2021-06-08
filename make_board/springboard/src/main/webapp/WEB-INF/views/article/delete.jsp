<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var ="dto" value ="${articleDTO}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" type="image/png" href="/favicon.png">
<title>글 수정</title>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<style type="text/css">
body {
		font-family: 'Nanum Pen Script', cursive;
		font-size:25px;
     }
alert_msg {
		color:Red;
}
</style>
</head>
<body>
<form  method="POST">

<table>
<caption>게시물 삭제</caption>
<tr>
	<th>글번호</th>
	<td>
		<input type="hidden" name="no" 
		value="${dto.no}" /></td>
</tr>

<tr>
	<th>비밀번호</th>
	<td><input type="password" name="passwd" required="required" autofocus="autofocus"/><br/>
		<div class="alert_msg">처음 글작성시 입력한 비번을 재입력하세요</div>
	</td>
</tr>

<tr>
	<th></th>
	<td>
		<button type="submit">완료</button>
		<button type="reset">초기화</button>
	</td>
</tr>
</table>

</form>
</body>
</html>

