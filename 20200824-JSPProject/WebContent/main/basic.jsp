<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%--
   	JSP(Java Server Page) : �������� ����Ǵ� �ڹ�����
	JSP�� �������
		1. ������
			= page : JPS���Ͽ� ���� ���� 
			= taglib : �±׷� �ڹ��� ����
					   ==> wpdjans <c:forEach> <c:if>
			= include : Ư�� JSP �ȿ� JSP�� ÷�� 
			�ڹ� + HTML (HTML ���, �ڹ� ÷��)
			<% �ڹٿ��� %>
			<html>
				<body>
					<h1>�Խ���</h1>
					<ul>
						<% 
							for(BoardVO vo:list)
							{
						%>
						<li>��ȣ-����-�̸�-�ۼ���-��ȸ��</li>
						<%
							}
						%>
					</ul>
				</body>
			</html>
			
			<html>
				<body>
					<h1>�Խ���</h1>
					<ul>
						<c:forEacj items="list">
						<li>��ȣ-����-�̸�-�ۼ���-��ȸ��</li>
						</c:forEach>
					</ul>
				</body>
			</html> 
			
		2. �ڹ� �ڵ� �κ�
			= ��ũ��Ʈ�� <% %> : �Ϲ� �ڹ� �ڵ�
			= ǥ���� <%= %> : out.println() : ȭ�鿡 ���� ���
			= ����� <%! %> : ��������, �޼ҵ带 ���� ���(���󵵰� ����)
		3. ���� ��ü (8�� ����) : �̸� ��ü�� �����س��� �ʿ�� ��� �����ϰ� ����� ��
			= request : ������� ��û��
			= reponse : �������� ó���� �� ���� ����
			= session
			= pageContext
			= page
			= config
			= exception
			= application
		4. ǥ���� (EL,JSTL)
		5. MVC
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
	String name="ȫ�浿";
	out.println(name);
%>
</body>
</html>