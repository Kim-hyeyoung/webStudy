package com.sist.manager;
//기업정보 manager
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.ReviewDAO;
/*
 * 1.//id=100~1000
				Document doc=Jsoup.connect("https://www.jobplanet.co.kr/companies?industry_id=100").get();
		이 부분이 산업군(서비스직,영업직..)인데 id=100~1000(100씩증가)까지 for문으로..어떻게?
	2.기업 클릭하면(ex.스타벅스)제일 첫 페이지가 소개 페이지가 아니고 리뷰 페이지.. 소개 페이지를 처음으로 하려면?=>데이터 긁을 용도
	3.private int cno; //기업번호 처리 방법
 * 
 */
public class com_manager {
	public void comAllData()
	{
		ReviewDAO dao=new ReviewDAO();
		try
		{
			int k=1;
			//for(int i=1;i<=5;i++)
			{
				//id=100~1000
				Document doc=Jsoup.connect("https://www.jobplanet.co.kr/companies?industry_id=100").get();
				Elements link=doc.select("dt.us_titb_l3 a");
				
				for(int j=0;j<link.size();j++)
				{
					try
					{
						//String link2=link.get(j).attr("href"); 찐
						//link2="https://www.jobplanet.co.kr"+link2; 찐
						//Document doc2=Jsoup.connect(link2).get(); 찐
						
						//소개 페이지에서 긁히는지 확인용..위에서 링크 해결되면(2번문제)없애기
						String link2="https://www.jobplanet.co.kr/companies/70749/landing/%EC%8A%A4%ED%83%80%EB%B2%85%EC%8A%A4%EC%BB%A4%ED%94%BC%EC%BD%94%EB%A6%AC%EC%95%84";
						//link2="https://www.jobplanet.co.kr"+link2;
						Document doc2=Jsoup.connect(link2).get();
						//소개 페이지에서 긁히는지 확인용..위에서 링크 해결되면(2번문제)없애기
						
						/*
						 * 	private int cno; //기업번호

						 */
						Element cname=doc2.selectFirst("h1.name");//기업명
						Element clogo=doc2.selectFirst("a.thumb_wrap img");//기업로고
						Element ctype=doc2.select("strong.info_item_subject").get(1);//기업형태
						Element indutype=doc2.select("strong.info_item_subject").get(0);//산업군
						Element cloc=doc2.select("ul.basic_info_more dd").get(2);//기업주소
						Element cscore=doc2.select("div.about_company span").get(0);//총평점
						Element cdate=doc2.select("strong.info_item_subject").get(3);//설립일
						Element worknum=doc2.select("strong.info_item_subject").get(2);//사원수
						Element bname=doc2.select("ul.basic_info_more dd").get(0);//대표이름
						Element sale=doc2.select("ul.basic_info_more dd").get(1);//매출
						Element cover=doc2.select("ul.basic_info_more dd").get(5);//기업소개
						Element site=doc2.select("ul.basic_info_more dd").get(3);//웹사이트
						Element history=doc2.select("ul.basic_info_more dd").get(4);//연혁
								
						System.out.println("기업번호:"+k++);
						System.out.println("기업로고:"+clogo.attr("src"));
						System.out.println("기업형태:"+ctype.text());
						System.out.println("산업군:"+indutype.text());
						System.out.println("기업주소:"+cloc.text());
						System.out.println("총평점:"+cscore.text());
						System.out.println("설립일:"+cdate.text());
						System.out.println("사원수:"+worknum.text());
						System.out.println("대표이름:"+bname.text());
						System.out.println("매출:"+sale.text());
						System.out.println("기업소개:"+cover.text());
						System.out.println("웹사이트:"+site.text());
						System.out.println("연혁:"+history.text());
						System.out.println("================================");
						
						
						
					}catch(Exception ex) {}
				}
				
			}
		}catch(Exception ex) {}
	}
	public static void main(String[] args) {
		com_manager m=new com_manager();
		m.comAllData();
	}

}
