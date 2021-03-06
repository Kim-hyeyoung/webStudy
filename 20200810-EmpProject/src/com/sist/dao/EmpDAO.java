package com.sist.dao;
import java.util.*;
import java.sql.*;
public class EmpDAO {
	//오라클 연결
		private Connection conn;
		//sql문장을 오라클로 전송
		private PreparedStatement ps;
		//오라클 주소
		private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		//드라이버 설치
		public EmpDAO()
		{
			//생성자 : 멤버변수의 초기화, 네트워크 서버 연결
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}
		//연결
		public void getConnection()
		{
			try
			{
				conn=DriverManager.getConnection(URL,"hr","happy");
			}catch(Exception ex) {}
		}
		//해제
		public void disConnection()
		{
			try
			{
				if(ps!=null) ps.close();
				if(conn!=null) conn.close();
			}catch(Exception ex) {}
		}
		//sql문장 전송 => 200개 데이터 요청
		public ArrayList<EmpVO> empAllData()
		{
			ArrayList<EmpVO> list=new ArrayList<EmpVO>();
			try
			{
				//오라클 연결
				getConnection();
				//sql문장 전송
				String sql="SELECT empno,ename,job,mgr,hiredate, sal, comm, deptno FROM emp ORDER BY empno ASC";
				ps=conn.prepareStatement(sql); //executeQuery()
				//결과값 받기
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					EmpVO vo=new EmpVO();
					vo.setEmpno(rs.getInt(1));
					vo.setEname(rs.getString(2));
					vo.setJob(rs.getString(3));
					vo.setMgr(rs.getInt(4));
					vo.setHiredate(rs.getDate(5));
					vo.setSal(rs.getInt(6));
					vo.setComm(rs.getInt(7));
					vo.setDeptno(rs.getInt(8));
					
					//200개 모아서 브라우저로 전송
					list.add(vo);
				}
				rs.close();
				//Arraylist에 값 채우기
			}catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			finally
			{
				//서버종료
				disConnection();
			}
			return list;
		}
		
	}

