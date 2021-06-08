<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src='<c:url value="/resources/se2/js/HuskyEZCreator.js" />'></script>
<script type="text/javascript">
window.onload = function() {
	document.getElementById('btnOk').onclick = function(){
		editors[0].exec('UPDATE_CONTENTS_FIELD',[]);
		document.myform.submit();
	}	
}
</script>
<body>
<form name="myform" method="post">
<table border="1">
	<caption>게시물 수정</caption>
<tr>
	<th>제목</th>
	<td><input type="text" name="art_title" value="${articleDTO.art_title}" required="required" autofocus="autofocus"/></td>
</tr>
<tr>
	<th>내용</th>
	<td><textarea name="art_content" rows="5" cols="60" id="content" style="width:645px;height:230px;">${articleDTO.art_content}</textarea><script type="text/javascript">
		var editors=[];		
		nhn.husky.EZCreator.createInIFrame({
			oAppRef:editors,
			elPlaceHolder:"content",
			sSkinURI: '<c:url value="/resources/se2/SmartEditor2Skin.html" />',
			fCreator:'createEditor2'
		});
		</script></td>
</tr>
<tr>
	<td colspan="2" align="center">
		<input type="button" value="완료" id="btnOk" />
	</td>
</tr>
</table>
</form>
</body>
</html>