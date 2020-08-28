package com.sist.dao;
import java.util.*;
import java.sql.*;
public class BoardDAO {
	//����
	private Connection conn;
	//����� �Է°��� sql�������� �ٲ㼭 ����
	private PreparedStatement ps;
	//����Ŭ �ּ� ÷��
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//����̹� ���
	public BoardDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
	}
	//����, �ݱ� �ݺ� => ��� �ݺ�=> �޼ҵ�
	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	public void disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	//���
	
	 //1. ���(�Խ���) => SELECT
	//BoardVO�� �����͵��� �ϳ��� ��� Arraylist�� ����
	public ArrayList<BoardVO> boardListData()
	{
		ArrayList<BoardVO> list=new ArrayList<BoardVO>();
		try
		{
			//����
			getConnection();
			//sql ����
			String sql="SELECT no,subject,name,regdate,hit FROM freeboard ORDER BY no DESC";//�ֽ� ��� �Խñ��� ���� ����
			//ORDER BY�� ���� : �ӵ��� �ʴ� => INDEX
			ps=conn.prepareStatement(sql);
			//sql ������ ����� �ޱ� / ����Ŭ ������ ���� ������� rs�� ����
			ResultSet rs=ps.executeQuery();
			//������� Arraylist�� ÷��
			while(rs.next())//����� ù��°�ٺ��� �������ٱ��� �о�Ͷ�(���پ�!)
			{
				BoardVO vo=new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			disConnection();
		}
		return list;
	}
	 //2. ���뺸�� => SELECT(WHERE) / URL �ּҿ���?�ڿ� ���� ����(�Ű�����)
	public BoardVO boardDetail(int no)
	{
		BoardVO vo=new BoardVO();
		try
		{
			getConnection();
			//SQL ���� ���� => ��ȸ�� ������ ���� update
			String sql="UPDATE freeboard SET hit=hit+1 WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);//?�� �� ä���
			//����
			ps.executeUpdate();
			
			//���� �� ������ ������ ����
			sql="SELECT no,name,subject,content,regdate,hit FROM freeboard WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ResultSet rs=ps.executeQuery();
			rs.next();
			
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			
			rs.close();
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			disConnection();
		}
		return vo;
	}
	 //3. �۾��� => INSERT
	public void boardInsert(BoardVO vo)
	{
		try
		{
			getConnection();
			//����
			//sql ���� => ����
			String sql="INSERT INTO freeboard(no,name,subject,content,pwd) VALUES((SELECT NVL(MAX(no)+1,1) FROM freeboard),?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName()); //ù��°?
			ps.setString(2, vo.getSubject()); //�ι�°?
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			
			ps.executeUpdate(); //�ڵ� Ŀ��!
			
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			disConnection();
		}
	}
	 //4. �ۼ��� => UPDATE
	 //5. �ۻ��� => DELETE
	 //6. ã�� => SELECT (LIKE)
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
