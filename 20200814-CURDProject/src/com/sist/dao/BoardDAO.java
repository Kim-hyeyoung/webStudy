package com.sist.dao;
import java.util.*;
import java.sql.*;
public class BoardDAO {
	//연결
	private Connection conn;
	//사용자 입력값을 sql문장으로 바꿔서 전송
	private PreparedStatement ps;
	//오라클 주소 첨부
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//드라이버 등록
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
	
	 //1. 목록(게시판) => SELECT
	//BoardVO의 데이터들을 하나로 모아 Arraylist에 저장
	public ArrayList<BoardVO> boardListData()
	{
		ArrayList<BoardVO> list=new ArrayList<BoardVO>();
		try
		{
			//연결
			getConnection();
			//sql 전송
			String sql="SELECT no,subject,name,regdate,hit FROM freeboard ORDER BY no DESC";//최신 등록 게시글을 가장 위로
			//ORDER BY의 단점 : 속도가 늦다 => INDEX
			ps=conn.prepareStatement(sql);
			//sql 실행후 결과값 받기 / 오라클 실행후 나온 결과값을 rs에 저장
			ResultSet rs=ps.executeQuery();
			//결과값을 Arraylist에 첨부
			while(rs.next())//출력한 첫번째줄부터 마지막줄까지 읽어와라(한줄씩!)
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
	 //2. 내용보기 => SELECT(WHERE) / URL 주소에서?뒤에 값을 보냄(매개변수)
	public BoardVO boardDetail(int no)
	{
		BoardVO vo=new BoardVO();
		try
		{
			getConnection();
			//SQL 문장 전송 => 조회수 증가를 위해 update
			String sql="UPDATE freeboard SET hit=hit+1 WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);//?에 값 채우기
			//실행
			ps.executeUpdate();
			
			//내용 볼 데이터 가지고 오기
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
	 //3. 글쓰기 => INSERT
	public void boardInsert(BoardVO vo)
	{
		try
		{
			getConnection();
			//연결
			//sql 전송 => 실행
			String sql="INSERT INTO freeboard(no,name,subject,content,pwd) VALUES((SELECT NVL(MAX(no)+1,1) FROM freeboard),?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName()); //첫번째?
			ps.setString(2, vo.getSubject()); //두번째?
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			
			ps.executeUpdate(); //자동 커밋!
			
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			disConnection();
		}
	}
	 //4. 글수정 => UPDATE
	 //5. 글삭제 => DELETE
	 //6. 찾기 => SELECT (LIKE)
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
