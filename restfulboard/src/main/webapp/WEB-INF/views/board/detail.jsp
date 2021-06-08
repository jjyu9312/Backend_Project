<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="app" value="${pageContext.request.contextPath}" />
<c:set var="dto" value="${articleDTO}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- <script type="text/javascript" src="../js/xhr.js"></script> -->
<script type="text/javascript" src='<c:url value="/webjars/jquery/3.5.1/jquery.js" />'></script>
<script>
function confirm_delete() {
	if(confirm('정말로 삭제하시겠습니꺄?')) {
		location.href='delete';
	}	
}
</script>
<script type="text/javascript">
function selectComment() {
	$.ajax({
		method : 'GET',
		url : "1/", 
	}).done(function( data ) {
	 	displayCommentList(data);
	});
}


function myCommentEvent() {
	$('.btnDeleteOk').click(function(){
		if (confirm('댓글을 정말로 삭제 하시겠습니까?')){
			let my_com_val = '1/' + $(this).attr('myval'); 
			$.ajax({
				method : 'DELETE',
				url : my_com_val, 
			}).done(function( data ) {
			 	displayCommentList(data);
			});
		}
	});
	
	$('.btnUpdateOk').click(function(){
		$('.btnDeleteOk').off('click');
		$('.btnUpdateOk').off('click');
		
		let com_id = 'com_' + $(this).attr('myval');
		let com_id_content = $('#' + com_id).html();
		$('#' + com_id).html('<textarea rows="3" cols="40" id="' + 
				com_id + '_content">' + com_id_content + '</textarea><br/>' +
				'<input type="button" id="goUpdate_' + com_id + '" value="수정완료" myval="'+ $(this).attr('myval') +'" />' +
				'<input type="button" id="cancleUpdate_' + com_id + '" value="수정취소" />');
		$('#goUpdate_'     + com_id).click(function(){
			if ($('#' + com_id + '_content').val() == "") {
				alert('수정한 내용이 없습니다.');
				$('#' + com_id + '_content').focus();
				return;
			}
			let my_com_val = '1/' + $(this).attr('myval'); 
			$.ajax({
				method : 'POST', //PUT방식은 data를 전송 할 수 없음
				url : my_com_val,
				data : {com_content : $('#' + com_id + '_content').val()}
			}).done(function( data ) {
			 	displayCommentList(data);
			});
		});
		$('#cancleUpdate_' + com_id).click(function(){
			$('#' + com_id).html(com_id_content);
			myCommentEvent();
			$('.btnUpdateOk').attr( 'disabled', false);
		});
	});
}

function displayCommentList(data) {
	var mytable = "<table border='1'><tr><td>";
  	$.each( data, function( key, val ) {
    	mytable += "<table><tr><td>" + val['com_no'] + "," + val['userDTO']['usr_id'];
    	mytable += "(" + val['userDTO']['usr_name']+ ")";
    	if (val['userDTO']['usr_id'] == '${sessionScope.userInfo.usr_id}') {
    		mytable += "<span class='ui-icon ui-icon-closethick btnDeleteOk' myval='" + val['com_no'] + "' style='cursor:pointer' />";
    		mytable += "<span class='ui-icon ui-icon-pencil     btnUpdateOk' myval='" + val['com_no'] + "' style='cursor:pointer' />";
    	}
    	mytable += "</td></tr>";
    	mytable += "<tr><td><span id='com_" + val['com_no']+ "'>" + val['com_content'] + "</span></td></tr></table><br/>";
		});
	mytable += "</td></tr></table>";
 
	$('#commentDisplay').html(mytable);
 	myCommentEvent();
}

$(document).ready(function(){
// 	selectComment();
	
	$('#btnCommentOk').click(function(){
		let com_content = $('#com_content').val();
		if (com_content == "") {
			alert('먼저 댓글을 입력하세요');
			$('#com_content').focus();
			return;
		}
		$.ajax({
			method : 'POST',
			url : "1/", 
			data : {com_content : $('#com_content').val()}
		}).done(function( data ) {
			displayCommentList(data);
			$('#com_content').val('');
		});
	});
	
	
});
</script>
</head>
<body>
<table>
	<caption>게시물 상세보기</caption>
<tr>
	<th>글번호</th>
	<td>${dto.art_no}</td>
</tr>
<tr>
	<th>제목</th>
	<td>${dto.art_title}</td>
</tr>
<tr>
	<th>작성자</th>
	<td>${dto.userDTO.usr_name}(${dto.userDTO.usr_id})</td>
</tr>
<tr>
	<th>작성일</th>
	<td><fmt:formatDate value="${dto.art_regdate}" 
		pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
<tr>
	<th>조회수</th>
	<td>${dto.art_readcnt}</td>
</tr>
<tr>
	<th>내용</th>
	<td>${dto.art_content}</td>
</tr>
<tr>
	<th>추천</th>
	<td><span id="good_count">${dto.art_like}</span>
		<img src="${app}/resources/img/like.png" id="good" width="20"/></td>
</tr>
<tr>
	<th>비추천</th>
	<td><span id="bad_count">${dto.art_dislike}</span>
		<img src="${app}/resources/img/dislike.png" id="bad" width="20"/></td>
</tr>
<tr>
	<th>작성자ip</th>
	<td>${dto.art_ip}</td>
</tr>
<tr>
	<td colspan="2">
		<a href="../">[리스트]</a>
		<c:if test="${dto.userDTO.usr_id == sessionScope.userInfo.usr_id}">
			<a href="update">[수정]</a>
			<a href="javascript:;" onclick="confirm_delete()">[삭제]</a>
		</c:if>
	</td>
</tr>
<tr>
	<td colspan="2">
	<form method="GET">
	<table >
		<tr>
			<td>
				<textarea id="com_content" name="com_content" style="width:400px;height:60px;"></textarea><br/>
				<input type="button" id="btnCommentOk" value="댓글등록"  />
			</td>
		</tr>
	</table>
	</form>
	<br/>
	
	<div id="commentDisplay"></div>
	
	</td>
</tr>
</table>
</body>
</html>