<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*,java.text.*"%>
<%

	String strPage=request.getParameter("page");
	if(strPage==null)
		strPage="1";
    
	MusicDAO dao=new MusicDAO();
	int curpage=Integer.parseInt(strPage);
	int totalpage=dao.movieTotalPage();
	//데이터 받기
	ArrayList<MovieVO> list=dao.movieAllData(curpage);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>영화목록</h1>
			<table width=800>
				<tr>
				<td>
					<input type=text id=keyword size=25>
				</td>
			</tr>
		</table>
		<table border=1 width=900 id="user-table">
		<tr>
			<th>번호</th>
			<th>카테고리</th>
			<th>영화제목</th>
			<th>포스터</th>
			<th>출연</th>
			<th>감독</th>
		</tr>
		<tbody>
		<%
			for(MovieVO vo:list)
			{
		%>
			<tr>
				<td><%=vo.getNo() %></td>
				<td><%=vo.getCateno() %></td>
				<td><%=vo.getTitle() %></td>
				<td>
					<img src=<%=vo.getPoster() %> width=30 height=30>
				</td>
				<td><%=vo.getActor() %></td>
				<td><%=vo.getDirector() %></td>
			</tr>
		<%
			}
		%>
		</tbody>
		</table>
		<table class="table_content" width=700>
			<tr>
				<td align=left></td>
				<td align=right>
					<a href="movie.jsp?page=<%= curpage>1?curpage-1:curpage%>">이전</a> <!-- 1페이지보다 클 때만 이전 페이지로 가도록 -->
					<%=curpage  %> page / <%=totalpage %> pages
					<a href="movie.jsp?page=<%=curpage<totalpage?curpage+1:curpage%>">다음</a> <!-- 마지막페이지일 때는 다음페이지x -->
				</td>
			</tr>
		</table>
	</center>
</body>
</html>