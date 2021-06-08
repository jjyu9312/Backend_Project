<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="app" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
${sessionScope.userInfo.usr_name}(${sessionScope.userInfo.usr_id})
님이 로그인 하였습니다.<br/>
<a href="${app}/user/logout">로그아웃</a><br/>

<table border="1">
	<caption>${boardDTO.boa_name}</caption>
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>작성일</th>
		<th>조회수</th>
	</tr>
	<c:forEach items="${list}" var="dto">
	<tr>
		<td>${dto.art_no}</td>
		<td><a href="${dto.art_no}/">${dto.art_title}</a></td>
		<td>${dto.userDTO.usr_name}(${dto.userDTO.usr_id})</td>
		<td><fmt:formatDate value="${dto.art_regdate}" pattern="yyyy-MM-dd"/></td>
		<td>${dto.art_readcnt}</td>
	</tr>
	</c:forEach>
</table><br/>
<a href="insert">게시물 입력</a>
</body>
</html>