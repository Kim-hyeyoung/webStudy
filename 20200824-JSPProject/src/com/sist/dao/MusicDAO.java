package com.sist.dao;
/*
 * 제어문(출력),변수(데이터 모으기),예외처리(오라클 연결)
 * 
 * 오라클 연결 => ArrayList => add(), get(), size()
 * 출력 => for-each
 */
import java.sql.*;
import java.util.*;
public class MusicDAO {
	//연결
			private Connection conn;
			//사용자 입력값을 sql문장으로 바꿔서 전송
			private PreparedStatement ps;
			//오라클 주소 첨부
			private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
			//드라이버 등록
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
			//연결, 닫기 반복 => 기능 반복=> 메소드
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
			//기능
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
			
			//영화 => 출력
			public ArrayList<MovieVO> movieAllData(int page)
			{
				ArrayList<MovieVO> list=new ArrayList<MovieVO>();
				try
				{
					getConnection();
					//페이징
					int rowSize=10;//10개를 한 페이지로
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
			
			//총페이지
			public int movieTotalPage()
			{
				int total=0;
				try
				{
					getConnection();
					String sql="SELECT CEIL(COUNT(*)/10.0) FROM daum_movie";//10.1처럼 나오면 2페이지로
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

			
			
			
			
			
			
			
			
			
			
			

