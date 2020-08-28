package com.sist.dao;
import java.sql.*;
import java.util.*;
public class MyDAO {
	//���� ��ü
	private Connection conn;
	//SQL ���� ��ü
	private PreparedStatement ps;
	//URL(����Ŭ �ּ�)
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//����̹� ���
	public MyDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	//����
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	//����
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	//JDBC (���üҽ�) => DBCP => ORM (MyBatis,Hibernate,JPA)
	//���
	public String isLogin(String ename, int empno)
	{
		String result="";
		try
		{
			getConnection();
			//SQL���� ����
			String sql="SELECT COUNT(*) FROM emp WHERE ename=?";
			ps=conn.prepareStatement(sql);
			//?�� ���� ä���
			ps.setString(1, ename);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			rs.close();
			if(count==0) //����̸� ����x
			{
				result="NONAME";
			}
			else //����̸� ���� o
			{
				sql="SELECT empno FROM emp WHERE ename=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, ename);
				rs=ps.executeQuery();
				rs.next(); //�����Ͱ� �ִ� ��ġ�� Ŀ���� �д�
				int db_empno=rs.getInt(1);
				rs.close();
				
				if(empno==db_empno) //�α���o
				{
					result=ename;
				}
				else //��� Ʋ��
				{
					result="NOSABUN";
				}
			}
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			disConnection();
		}
		return result;
	}

}
