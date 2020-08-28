package com.sist.join;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@WebServlet("/EmpServlet")
public class EmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//doGet => thread
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out=response.getWriter();
		EmpDAO dao=new EmpDAO();
		ArrayList<EmpVO> list=dao.empDeptJoinData();
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>�������</h1>");
		out.println("<table border=1 width=700>");
		//Ÿ��Ʋ�� ����
		out.println("<tr>");
		out.println("<th>���</th>");
		out.println("<th>�̸�</th>");
		out.println("<th>����</th>");
		out.println("<th>�Ի���</th>");
		out.println("<th>�޿�</th>");
		out.println("<th>�μ���</th>");
		out.println("<th>�ٹ���</th>");
		out.println("</tr>");
		
		for(EmpVO vo:list)
		{
			out.println("<tr>");
			//������ td�� �ؾ������� �� ��� th�� �������
			out.println("<th>"+vo.getEmpno()+"</th>");
			out.println("<th>"+vo.getEname()+"</th>");
			out.println("<th>"+vo.getJob()+"</th>");
			out.println("<th>"+vo.getHiredate().toString()+"</th>");
			out.println("<th>"+vo.getSal()+"</th>");
			out.println("<th>"+vo.getDvo().getDname()+"</th>");
			out.println("<th>"+vo.getDvo().getLoc()+"</th>");
			out.println("</tr>");
		}
		
	
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
		
		
		
		
	}

}