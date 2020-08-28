<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%--
	 전송된 데이터를 받아서 오라클 연결만 시켜주는 파일
	 _ok.jsp
--%>
<%
	try
	{
		//데이터를 받는 경우 (한글포함) => 한글 변환(디코딩)
		request.setCharacterEncoding("UTF-8");
	}catch(Exception ex){}
	String name=request.getParameter("name");
	String subject=request.getParameter("subject");
	String content=request.getParameter("content");
	String pwd=request.getParameter("pwd");
	String no=request.getParameter("no");
	
	//데이터를 모아서 DAO로 전송
	BoardVO vo=new BoardVO();
	vo.setNo(Integer.parseInt(no));
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	//DAO연결 => 오라클 INSERT
	BoardDAO dao=new BoardDAO();
	boolean bCheck=dao.boardUpdate(vo);
	
	if(bCheck==true)
	{
		response.sendRedirect("detail.jsp?no="+no);
	}
	else
	{
%>
	<script>
	alert("비밀번호가 틀립니다!!");
	history.back();
	</script>
<%	
	}
%>
