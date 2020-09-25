package com.sist.manager;
//합격자소서 manager
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sist.dao.ReviewDAO;
/*
 * 1. span.cont 인덱스 번호가 글마다 바뀜..
 * 2. 자소서 질문 항목이랑 내용 분리 방법
 */
public class manager {
	public void reviewAllData()
	{
		ReviewDAO dao=new ReviewDAO();
		try
		{
			int k=1;
			//for(int i=1;i<=5;i++)
			{
				Document doc=Jsoup.connect("https://people.incruit.com/resumeguide/pdslist.asp?pds1=1&pds2=11&pass=y").get();
				Elements link=doc.select("td a");
				
				for(int j=0;j<link.size();j++)
				{
					try
					{
						String link2=link.get(j).attr("href");
						link2="https://people.incruit.com/resumeguide"+link2.substring(1,link2.length());
						Document doc2=Jsoup.connect(link2).get();
						
						Element ssubject=doc2.selectFirst("h2.title_area"); //자기소개서 제목
						Element sname=doc2.select("span.cont").get(0);//기업명
						//Element sindutype=doc2.select("span.cont").get(1);//직종
						Element scontent=doc2.select("span.cont").get(2);//자기소개서 내용
								
						System.out.println("글번호:"+k++);
						System.out.println("제목:"+ssubject.text());
						System.out.println("기업명:"+sname.text());
						//System.out.println("직종:"+sindutype.text());
						System.out.println("내용:"+scontent.text());
						System.out.println("================================");
						

						
						
					}catch(Exception ex) {}
				}
				
			}
		}catch(Exception ex) {}
	}
	public static void main(String[] args) {
		manager m=new manager();
		m.reviewAllData();
	}

}
