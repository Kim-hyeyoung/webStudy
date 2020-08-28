<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="table.css">
</head>
<body>
<%--submit => action에 등록된 파일로 입력한 데이터 전송
	= <input> <textarea> <select> 값만 전송됨

 --%>
	<center>
		<h1>글쓰기</h1>
		<form method=post action=insert_ok.jsp>
		<table class="table_content" width=500>
			<tr>
				<th width=15% align=right>이름</th>
				<td width=85%>
					<input type=text name=name size=15 required>
				</td>
			</tr>
			<tr>
				<th width=15% align=right>제목</th>
				<td width=85%>
					<input type=text name=subject size=45 required>
				</td>
			</tr>
			<tr>
				<th width=15% align=right>내용</th>
				<td width=85%>
					<textarea rows="7" cols="55" name="content" required></textarea>
				</td>
			</tr>
			<tr>
				<th width=15% align=right>비밀번호</th>
				<td width=85%>
					<input type=password name=pwd size=10 required> <!-- required : 입력값 필수! -->
				</td>
			</tr>
			<tr>
				<td colspan="2" align=center>
				<input type=submit value="글쓰기">
				<input type=button value="취소" onclick="javascript:history.back()"> <!-- button은 기능이 없어서 자바스크립트로 구현해야 함 -->
				</td>
			</tr>		
		</table>
		</form>
	</center>

</body>
</html>