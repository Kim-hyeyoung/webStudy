<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*"%>
<%
	String strPage=request.getParameter("page");
	if(strPage==null)
		strPage="1";
	
	int curpage=Integer.parseInt(strPage);//현재페이지
	//페이지에 해당되는 데이터 읽기
	ReplyBoardDAO dao=new ReplyBoardDAO();
	//10개씩
	ArrayList<ReplyBoardVO> list=dao.boardListData(curpage);
	//출력
	int count=dao.boardRowCount();
	int totalpage=(int)(Math.ceil(count/10.0));//11/10.0 => 1.1 => 2
	count=count-((curpage*10)-10);//2페이지 게시글 번호 제대로 주기 위함
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
		<h1>묻고답하기</h1>
		<table class="table_content" width=700>
			<tr>
				<td>
					<a href="insert.jsp">등록</a>
				</td>
			</tr>
		</table>
		<table class="table_content" width=700>
			<tr height=30>
				<th width=10%>번호</th>
				<th width=45%>제목</th>
				<th width=15%>이름</th>
				<th width=20%>작성일</th>
				<th width=10%>조회수</th>
			</tr>
			<%
				for(ReplyBoardVO vo:list)
				{
			%>
				<tr>
					<td width=10% align=center><%=count-- %></td>
					<td width=45%>
					<%
						if(vo.getGroup_tab()>0)
						{
							for(int i=0;i<vo.getGroup_tab();i++)
							{
								out.println("&nbsp;&nbsp;");//공백
							}
					%>
						<img src="image/icon_reply.gif" style="border:none">
					<%
						}
					%>
					<%--내용보기 이동 --%>
					<a href="detail.jsp?no=<%=vo.getNo()%>&page=<%=curpage%>"><%=vo.getSubject() %>
					&nbsp;
					<%
						Date date=new Date();
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						String today=sdf.format(date);
						String dbday=vo.getRegdate().toString();
						
						if(today.equals(dbday))
						{
					%>
							<sup><img src="image/new.gif" style="border:none"></sup>
					<%
						}
					%>
					
					</td>
					<td width=15% align=center><%=vo.getName() %></td>
					<td width=20% align=center><%=vo.getRegdate().toString() %></td>
					<td width=10% align=center><%=vo.getHit() %></td>
				</tr>
			<%
				}
			%>
		</table>
		<table class="table_content" width=700>
			<tr>
				<td align=left>
					search:
					<%--
						WHERE fd LIKE '%ss%'
					 --%>
				<form method=post action="find.jsp">
					<select name=fs>
						<option value="name">이름</option>
						<option value="subject">제목</option>
						<option value="content">내용</option>
					</select>
					<input type=text name=ss size=10>
					<input type=submit value="찾기">
				</form>
				</td>
				<td align=right>
					<a href="list.jsp?page=<%=curpage>1?curpage-1:curpage%>">이전</a>
						<%=curpage %> page / <%=totalpage %> pages
					<a href="list.jsp?page=<%=curpage<totalpage?curpage+1:curpage%>">다음</a>
				</td>
			</tr>
		</table>
	</center>

</body>
</html>