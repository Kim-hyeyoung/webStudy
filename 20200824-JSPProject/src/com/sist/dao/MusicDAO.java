package com.sist.dao;
/*
 * ���(���),����(������ ������),����ó��(����Ŭ ����)
 * 
 * ����Ŭ ���� => ArrayList => add(), get(), size()
 * ��� => for-each
 */
import java.sql.*;
import java.util.*;
public class MusicDAO {
	//����
			private Connection conn;
			//����� �Է°��� sql�������� �ٲ㼭 ����
			private PreparedStatement ps;
			//����Ŭ �ּ� ÷��
			private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
			//����̹� ���
			public MusicDAO()
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
			//=>MusicTop200
			public ArrayList<MusicVO> musicAllData()
			{
				ArrayList<MusicVO> list=new ArrayList<MusicVO>();
				try
				{
					getConnection();
					String sql="SELECT mno,title,singer,album,poster FROM genie_music";
					ps=conn.prepareStatement(sql);
					ResultSet rs=ps.executeQuery();
					while(rs.next())
					{
						MusicVO vo=new MusicVO();
						vo.setMno(rs.getInt(1));
						vo.setTitle(rs.getString(2));
						vo.setSinger(rs.getString(3));
						vo.setAlbum(rs.getString(4));
						vo.setPoster(rs.getString(5));
						
						list.add(vo);
					}
					rs.close();
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}finally
				{
					disConnection();
				}
				return list;
			}
			
			//��ȭ => ���
			public ArrayList<MovieVO> movieAllData(int page)
			{
				ArrayList<MovieVO> list=new ArrayList<MovieVO>();
				try
				{
					getConnection();
					//����¡
					int rowSize=10;//10���� �� ��������
					//BETWEEN start AND end
					int start=(page*rowSize)-(rowSize-1);
					int end=page*rowSize;

					String sql="SELECT no,cateno,title,poster,actor,director "
								+"FROM (SELECT no,cateno,title,poster,actor,director,rownum as num "
								+"FROM (SELECT no,cateno,title,poster,actor,director "
								+"FROM daum_movie ORDER BY no)) "
								+"WHERE num BETWEEN ? AND ?";
								
					ps=conn.prepareStatement(sql);
					ps.setInt(1, start);
					ps.setInt(2, end);
					ResultSet rs=ps.executeQuery();
					while(rs.next())
					{
						MovieVO vo=new MovieVO();
						vo.setNo(rs.getInt(1));
						vo.setCateno(rs.getInt(2));
						vo.setTitle(rs.getString(3));
						vo.setPoster(rs.getString(4));
						vo.setActor(rs.getString(5));
						vo.setDirector(rs.getString(6));
						
						list.add(vo);
					}
					rs.close();
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}finally
				{
					disConnection();
				}
				return list;
			}
			
			//��������
			public int movieTotalPage()
			{
				int total=0;
				try
				{
					getConnection();
					String sql="SELECT CEIL(COUNT(*)/10.0) FROM daum_movie";//10.1ó�� ������ 2��������
					ps=conn.prepareStatement(sql);
					ResultSet rs=ps.executeQuery();
					rs.next();
					total=rs.getInt(1);
					rs.close();
				}catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}finally
				{
					disConnection();
				}
				return total;
			}
			
			
			
			
		}

			
			
			
			
			
			
			
			
			
			
			

