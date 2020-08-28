<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,com.sist.dao.*,java.text.*"%>
<%

	//list.jsp?page=1 : request.getParameter("page"); => 1페이지
	//list.jsp : request.getParameter("page"); => null
	//list.jsp?page= : request.getParameter("page"); => ""
	String strPage=request.getParameter("page");
	if(strPage==null) //1페이지는 page번호가 없기 때문에<http://localhost/20200824-BoardProject/board/list.jsp> => null값으로
		strPage="1";
	/*
		request VS response
		1) request (내장객체 => 미리 생성된 객체)
			= HttpServletRequest request
			= 주요 기능
				1. 브라우저 정보(사용자 정보)
					= 사용자 IP
					= 사용자 PORT
				2. 사용자 요청정보 (사용자가 보낸 모든 값을 받을 수 있는 기능)
					= 단일값 : getParameter("보낸변수명");
							list.jsp?page=1 => getParameter("page");
							<input type=text name=no> => getParameter("no");
					= 다중값 : checkbox, select => getParameterValues()
					= 한글처리 : 한글 => 인코딩 => 디코딩(한글이 정상적으로 들어온다)
							setCharacterEncoding("UTF-8")
		
	*/
	BoardDAO dao=new BoardDAO();
	int curpage=Integer.parseInt(strPage);
	int totalpage=dao.boardTotalPage();
	//데이터 받기
	ArrayList<BoardVO> list=dao.boardAllData(curpage);
	
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="table.css">

</head>
<body>
	<center>
		<h1>게시판</h1>
		<table class="table_content" width=700>
		<tr>
			<td>
				<a href="insert.jsp">새글</a>
			</td>
		</tr>
		</table>
		<table class="table_content" width=700>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>이름</th>
			<th>날짜</th>
			<th>조회수</th>
		</tr>
		<tbody>
		<%
			for(BoardVO vo:list)
			{
		%>
			<tr class="dataTr">
               <td width=10% class"tdcenter"><%= vo.getNo()%></td>
               <td width=45% class"tdLeft">
           		 <a href="detail.jsp?no=<%= vo.getNo() %>"><%= vo.getSubject() %></a>
				<%
					Date date=new Date();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String today=sdf.format(date);
					if(today.equals(vo.getRegdate().toString()))
					{
				%>
					<font color=red><sup>new</sup></font>
				<%
					}
				%>
				</td>
				<td><%=vo.getName() %></td>
				<td><%=vo.getRegdate() %></td>
				<td><%=vo.getHit() %></td>
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
					<a href="list.jsp?page=<%= curpage>1?curpage-1:curpage%>">이전</a> <!-- 1페이지보다 클 때만 이전 페이지로 가도록 -->
					<%=curpage  %> page / <%=totalpage %> pages
					<a href="list.jsp?page=<%=curpage<totalpage?curpage+1:curpage%>">다음</a> <!-- 마지막페이지일 때는 다음페이지x -->
				</td>
			</tr>
		</table>
	</center>
</body>
</html>