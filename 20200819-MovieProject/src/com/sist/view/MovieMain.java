package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;//ArrayList => 배열(가변형 배열)
import com.sist.dao.*;//MovieDAO
import com.sist.manager.*;//MovieVO

@WebServlet("/MovieMain")
public class MovieMain extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * HttpServletRequest request : 사용자의 요청값 받기, 브라우저 정보(사용자의 IP)
		 * HttpServletResponse response : 응답정보(서버=>클라이언트로 전송)
		 */
		//브라우저 준비(HTML,XML)
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out=response.getWriter();
		//out.println("<?xml version=\"1.0\" encoding=\"EUC-KR\"?>");
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h1 class=text-center>영화목록</h1>");
		out.println("<div class=row>");
		out.println("<a href=MovieMain?no=1 class=\"btn btn-sm btn-primary\">현재상영영화</a>");
		out.println("<a href=MovieMain?no=2 class=\"btn btn-sm btn-danger\">개봉예정영화</a>");
		out.println("<a href=MovieMain?no=3 class=\"btn btn-sm btn-info\">주간박스오피스</a>");
		out.println("<a href=MovieMain?no=4 class=\"btn btn-sm btn-warning\">월간박스오피스</a>");
		out.println("<a href=MovieMain?no=5 class=\"btn btn-sm btn-success\">연간박스오피스</a>");
		out.println("<a href=NewsMain class=\"btn btn-sm btn-success\">뉴스</a>");
		out.println("</div>");
		out.println("<div class=row>");
		/*
		 * <div class="col-md-4">
	      <div class="thumbnail">
	        <a href="/w3images/lights.jpg" target="_blank">
	          <img src="/w3images/lights.jpg" alt="Lights" style="width:100%">
	          <div class="caption">
	            <p>Lorem ipsum donec id elit non mi porta gravida at eget metus.</p>
	          </div>
	        </a>
	      </div>
	    </div>
		 */
		//전송받은 값 받기
		String no=request.getParameter("no");
		if(no==null) //1페이지 페이지 번호 없을 때	
			no="1";
		MovieDAO dao=new MovieDAO();
		ArrayList<MovieVO> list=dao.MovieListData(Integer.parseInt(no));
		for(MovieVO vo:list)
		{
			out.println("<div class=\"col-md-3\">");
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=MovieDetail?no="+vo.getNo()+">");//?뒤에 변수값 / ? 앞에서 변수 번호를 받아 처리
			out.println("<img src="+vo.getPoster()+" alt=\"Lights\" style=\"width:100%\">");
			out.println("<div class=\"caption\">");
			String str=vo.getTitle();
			if(str.length()>18)
			{
				str=str.substring(0,18)+"...";
			}
			out.println("<p>"+str+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
			
		}
		
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
		
		
	}

}
