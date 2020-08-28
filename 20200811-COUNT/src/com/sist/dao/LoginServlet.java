package com.sist.dao;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		//브라우저에 HTML을 전송, 한글 포함
		//HTML 제작
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>Login</h1>");
		out.println("<form method=post action=LoginServlet>"); //post를 쓰면 post 호출, form태그가 없으면 get 디폴트 실행
		//아이디
		out.println("<table width=250>");
		out.println("<tr>");
		out.println("<td width=15% align=right>이름</td>"); //테이블 크기 250에 대한 15%
		out.println("<td width=85%>"); //테이블크기 250에 대한 85% / 위와 합쳐서 100%
		out.println("<input type=text name=ename size=17>");
		out.println("</td>");
		out.println("</tr>");
		//사번
		out.println("<tr>");
		out.println("<td width=15% align=right>사번</td>"); //테이블 크기 250에 대한 15%
		out.println("<td width=85%>"); //테이블크기 250에 대한 85% / 위와 합쳐서 100%
		out.println("<input type=password name=empno size=17>");
		out.println("</td>");
		out.println("</tr>");
		//로그인 버튼
		out.println("<tr>");//다음줄
		out.println("<td colspan=2 align=center>"); //colspan으로 해야 버튼이 중앙으로 온다 => 가로 통합 / 세로 통합은 rowspan
		out.println("<input type=submit value=로그인>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	//doPost : 데이터를 받아서 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청 처리
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out=response.getWriter();
		String ename=request.getParameter("ename");
		String empno=request.getParameter("empno"); //브라우저에서 입력한 값은 모두 String으로 들어옴 = > 필요에따라 int 변환 필수!!!!!
		MyDAO dao=new MyDAO();
		String result=dao.isLogin(ename.toUpperCase(), Integer.parseInt(empno));
		System.out.println("이름:"+ename.toUpperCase());
		System.out.println("사번:"+empno);
		if(result.equals("NONAME"))
		{
			out.println("<script>");
			out.println("alert(\"이름이 존재하지 않습니다\");");
			out.println("history.back();"); //틀렸을 경우 메인화면으로
			out.println("</script>");
		}
		else if(result.equals("NOSABUN"))
		{
			out.println("<script>");
			out.println("alert(\"사번이 틀립니다\");");
			out.println("history.back();");
			out.println("</script>");
		}
		else
		{
			//out.println("<script>");
			//out.println("alert(\""+result+"님 메인으로 이동합니다\");");
			//out.println("</script>");
			response.sendRedirect("MusicServlet"); //로그인 성공시 다음 화면으로 이동
		}
		
		
	}

}
