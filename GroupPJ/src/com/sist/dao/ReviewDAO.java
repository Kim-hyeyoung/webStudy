package com.sist.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

import com.sist.manager.*;

public class ReviewDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	public ReviewDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	public void getConnection()
	{
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	public ArrayList<ReviewVO> disConnection()
	{
		try
		{
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	//====================================================================================
		//기능
		//합격자소서
		public ArrayList<ReviewVO> reviewAllData()
		{
			ArrayList<ReviewVO> list=new ArrayList<ReviewVO>();
			try
			{
				getConnection();
				String sql="SELECT sno,ssubject,sname,sindutype,scontent,num "
						+"FROM (SELECT sno,ssubject,sname,sindutype,scontent,rownum as num "
						+"FROM (SELECT sno,ssubject,sname,sindutype,scontent "
						+"FROM "
				

				String sql="SELECT mno,title,poster,singer,album,RANK() OVER(ORDER BY mno ASC),num "
				+"FROM (SELECT mno,title,poster,singer,album,rownum as num "
				+"FROM (SELECT mno,title,poster,singer,album "
				+"FROM melon WHERE cateno=? ORDER BY mno)) "
				+"WHERE num BETWEEN ? AND ?";

				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					ReviewVO vo=new ReviewVO();
					vo.setMno(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setPoster(rs.getString(3));
					vo.setSinger(rs.getString(4));
					vo.setAlbum(rs.getString(5));
					vo.setRank(rs.getInt(6));
					
					list.add(vo);
				}
				rs.close();
			}catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}finally
			{
				disConnection();
			}
			return list;
		}
		
		//기업정보 
		public ArrayList<comVO> comAllData()
		{
			ArrayList<comVO> list=new ArrayList<comVO>();
			try
			{
				getConnection();
				String sql="SELECT sno,ssubject,sname,sindutype,scontent,num "
						+"FROM (SELECT sno,ssubject,sname,sindutype,scontent,rownum as num "
						+"FROM (SELECT sno,ssubject,sname,sindutype,scontent "
						+"FROM "
				

				String sql="SELECT mno,title,poster,singer,album,RANK() OVER(ORDER BY mno ASC),num "
				+"FROM (SELECT mno,title,poster,singer,album,rownum as num "
				+"FROM (SELECT mno,title,poster,singer,album "
				+"FROM melon WHERE cateno=? ORDER BY mno)) "
				+"WHERE num BETWEEN ? AND ?";

				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					ReviewVO vo=new ReviewVO();
					vo.setMno(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setPoster(rs.getString(3));
					vo.setSinger(rs.getString(4));
					vo.setAlbum(rs.getString(5));
					vo.setRank(rs.getInt(6));
					
					list.add(vo);
				}
				rs.close();
			}catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}finally
			{
				disConnection();
			}
			return list;
		}
		
		

	}
}
