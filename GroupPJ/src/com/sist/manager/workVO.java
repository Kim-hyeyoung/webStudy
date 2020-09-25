package com.sist.manager;
//재직자후기vo
public class workVO {
	private int rno; //리뷰번호
	private String rtype; //직무
	private String rloc; //근무지역
	private String rdate; //작성일
	private String rscore; //개인평점
	private String rcontent; //리뷰내용
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public String getRtype() {
		return rtype;
	}
	public void setRtype(String rtype) {
		this.rtype = rtype;
	}
	public String getRloc() {
		return rloc;
	}
	public void setRloc(String rloc) {
		this.rloc = rloc;
	}
	public String getRdate() {
		return rdate;
	}
	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	public String getRscore() {
		return rscore;
	}
	public void setRscore(String rscore) {
		this.rscore = rscore;
	}
	public String getRcontent() {
		return rcontent;
	}
	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}
	
}
