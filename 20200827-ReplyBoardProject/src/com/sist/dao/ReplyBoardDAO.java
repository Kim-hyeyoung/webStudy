package com.sist.dao;
import java.util.*;
import java.sql.*;


public class ReplyBoardDAO {
	//연결
		private Connection conn;
		//사용자 입력값을 sql문장으로 바꿔서 전송
		private PreparedStatement ps;
		//오라클 주소 첨부
		private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		//드라이버 등록
		public ReplyBoardDAO()
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
		//1.목록출력 => 페이지 나누기
		public ArrayList<ReplyBoardVO> boardListData(int page)
		{
			ArrayList<ReplyBoardVO> list=new ArrayList<ReplyBoardVO>();
			try
			{
				getConnection();
				String sql="SELECT no,subject,name,regdate,hit,group_tab,num "
						   +"FROM (SELECT no,subject,name,regdate,hit,group_tab,rownum as num "
						   +"FROM (SELECT no,subject,name,regdate,hit,group_tab "
						   +"FROM replyBoard ORDER BY group_id DESC,group_step ASC)) "
						   +"WHERE num BETWEEN ? AND ?";
				/*					group_id	group_step
				 * AAAA				2			0
				 * 	=>BBBB			2			1
				 * 		=>CCCC		2			2
				 * 	=>KKKK			2			3
				 * DDDD				1			0
				 */
				ps=conn.prepareStatement(sql);
				int rowSize=10;
				int start=(rowSize*page)-(rowSize-1);
				int end=rowSize*page;
				
				//?에 값 채우기
				ps.setInt(1, start);
				ps.setInt(2, end);
				
				//실행후에 결과값 읽기
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					ReplyBoardVO vo=new ReplyBoardVO();
					vo.setNo(rs.getInt(1));
					vo.setSubject(rs.getString(2));
					vo.setName(rs.getString(3));
					vo.setRegdate(rs.getDate(4));
					vo.setHit(rs.getInt(5));
					vo.setGroup_tab(rs.getInt(6));
					
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
		
		//1-1 전체 개수=>게시글 번호 출력
		public int boardRowCount()
		{
			int count=0;
			try
			{
				getConnection();
				String sql="SELECT COUNT(*) FROM replyBoard";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				rs.next();
				count=rs.getInt(1);
				rs.close();
			}catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}finally
			{
				disConnection();
			}
			return count;
		}
		
		//2.상세보기
		//매개변수 => 사용자가 보내준 데이터
		public ReplyBoardVO boardDetail(int no,int type)
		{
			ReplyBoardVO vo=new ReplyBoardVO();
			try
			{
				getConnection();
				//조회수
				String sql="";
				if(type==1)
				{
					sql="UPDATE replyBoard SET "
							+"hit=hit+1 " 
							+"WHERE no=?";
					ps=conn.prepareStatement(sql);
					ps.setInt(1, no);
					ps.executeUpdate();
				}
			
				//증가된 조회수 읽기
				sql="UPDATE replyBoard SET "
							+"hit=hit+1 " 
							+"WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
				sql="SELECT no,name,subject,content,regdate,hit "
					+"FROM replyBoard WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				//결과값 받기
				ResultSet rs=ps.executeQuery();
				//데이터 출력 위치로 커서위치 변경
				rs.next();
				//값 가져오기
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
			}finally
			{
				disConnection();
			}
			return vo;
		}
		
		//3.새글 등록 => INSERT
		public void boardInsert(ReplyBoardVO vo)
		{
			try
			{
				getConnection();
				String sql="INSERT INTO replyBoard(no,name,subject,content,pwd,group_id) " 
							+"VALUES(rb_no_seq.nextval,?,?,?,?,"
							+"(SELECT NVL(MAX(group_id)+1,1) FROM replyBoard))";
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setString(4, vo.getPwd());
				
				ps.executeUpdate();
			}catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}finally
			{
				disConnection();
			}
		}
		
		//4.답변하기 => SQL(서브쿼리)
		public void boardReplyInsert(int root,ReplyBoardVO vo)
		{
			try
			{
				getConnection();
				//1.답변 대상의 group_id,group_step,group_tab 가져오기 (답변 출력순서 맞추기용)
				String sql="SELECT group_id,group_step,group_tab "
							+"FROM replyBoard WHERE no=?"; 
				ps=conn.prepareStatement(sql);
				ps.setInt(1, root);
				ResultSet rs=ps.executeQuery();
				rs.next();
				int gi=rs.getInt(1);//gi
				int gs=rs.getInt(2);//gs+1
				int gt=rs.getInt(3);//gt+1
				rs.close();
				//문제 : 같은 depth의 댓글이 최신순으로 출력되도록(가장위에)
				sql="UPDATE replyBoard SET group_step=group_step+1 WHERE group_id=? AND group_step>?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, gi);
				ps.setInt(2, gs);
				ps.executeUpdate();
				
				//INSERT
				sql="INSERT INTO replyBoard(no,name,subject,content,pwd,group_id,group_step,group_tab,root) " 
						+"VALUES(rb_no_seq.nextval,?,?,?,?,?,?,?,?)";

				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setString(4, vo.getPwd());
				ps.setInt(5, gi);
				ps.setInt(6, gs+1);
				ps.setInt(7, gt+1);
				ps.setInt(8, root);
				ps.executeUpdate();
				
				//root => depth+1
				sql="UPDATE replyBoard SET depth=depth+1 WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, root);
				ps.executeUpdate();
				
			}catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}finally
			{
				disConnection();
			}
		}
		
		//5.수정하기 => UPDATE
		public boolean boardUpdate(ReplyBoardVO vo)
		{
			boolean bCheck=false;
			try
			{
				getConnection();
				//본인확인
				String sql="SELECT pwd FROM replyBoard WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, vo.getNo());
				ResultSet rs=ps.executeQuery();
				rs.next();
				String db_pwd=rs.getString(1);
				rs.close();
				
				if(db_pwd.equals(vo.getPwd()))
				{
					bCheck=true;
					
					sql="UPDATE replyBoard SET name=?,subject=?,content=? WHERE no=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, vo.getName());
					ps.setString(2, vo.getSubject());
					ps.setString(3, vo.getContent());
					ps.setInt(4, vo.getNo());
					ps.executeUpdate();
				}
			}catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}finally
			{
				disConnection();
			}
			return bCheck;
		}
		
		//6.삭제하기 => SQL(서브쿼리)
		//7.찾기 => LIKE, REGEXP_LIKE
		public ArrayList<ReplyBoardVO> boardFindData(String fs,String ss)
		{
			ArrayList<ReplyBoardVO> list=new ArrayList<ReplyBoardVO>();
			try 
			{
				getConnection();
				String sql="SELECT no,subject,name,regdate,hit FROM replyBoard "
							+"WHERE "+fs+" LIKE '%"+ss+"%'";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					ReplyBoardVO vo=new ReplyBoardVO();
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
			}finally
			{
				disConnection();
			}
			return list;
		}
		
		
}
