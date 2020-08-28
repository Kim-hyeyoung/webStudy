<%@ page info="상세보기(2020.08.28)" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%
	/*
		JSP
		사용자가 요청한 내용을 브라우저에 출력
		1) 사용자가 보내준 데이터 받기
		2) 데이터베이스 DAO 연결
		3) 출력
	*/
	String no=request.getParameter("no");
	String strPage=request.getParameter("page");
	
	//DAO 연결 => 데이터 읽기 => DAO 상세보기 만들기
	ReplyBoardDAO dao=new ReplyBoardDAO();
	ReplyBoardVO vo=dao.boardDetail(Integer.parseInt(no),1);
	
	/*
		page는 내장객체여서 strPage로 써야 함
		이와 같은 것들
		=request
		=response
		=application
		=session
		=out
		=exception
		=pageContext
		=page => 자바에서의 this
		=config
		=cookie(내장객체는 아님)
	*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/table.css">
</head>
<body>
	<center>
		<h1>내용보기</h1>
		<table class="table_content" width=700>
			<tr>
				<th width=20%>번호</th>
				<td width=20% align="center"><%=vo.getNo() %></td>
				<th width=20%>작성일</th>
				<td width=20% align="center"><%--<%=vo.getRegdate().toString() --%></td>
			</tr>
			<tr>
				<th width=20%>이름</th>
				<td width=20% align="center"><%=vo.getName() %></td>
				<th width=20%>조회수</th>
				<td width=20% align="center"><%=vo.getHit() %></td>
			</tr>
			<tr>
				<th width=20%>제목</th>
				<td colspan="3" align="left"><%=vo.getSubject() %></td>
			<tr>
				<td colspan="4" height="200" valign="top"><pre><%=vo.getContent() %></pre></td>
			</tr>
			<tr>
			<%--
				화면 이동(데이터 전송)
				HTML : <a> : get방식 
						<form> : get방식, post방식 선택 가능
				JavaScript : location.href="" : get방식
								ajax : get방식, post방식 선택 가능
				Java : sendRedirect() : 서버에서 화면 이동시 사용 , get방식
						forward() : get방식
						
				request : 화면 이동시 전에 받아놓은 데이터는 손실된다
						A	 	=>		 B 		=> 		C
							 request		request 		=> 서로 다른 request(초기화된다!!! => request가 매개변수이기 때문) => 계속 유지하고 싶을 때 session 사용
			 --%>
				<td colspan="4" align="right">
					<a href="reply.jsp?no=<%=vo.getNo()%>&page=<%=strPage%>">답변</a>
					<a href="update.jsp?no=<%=vo.getNo()%>&page=<%=strPage%>">수정</a>&nbsp;
					<a href="#">삭제</a>&nbsp;   <!-- &nbsp; : 공백주기 -->
					<a href="list.jsp?page=<%=strPage%>">목록</a>
				</td>
			</tr>
			</tr>
		</table>
	</center>
</body>
</html>