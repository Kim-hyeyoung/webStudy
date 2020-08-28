package com.sist.manager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.dao.MelonDAO;

public class MelonManager {
	public void melonAllDate()
	{
		MelonDAO dao=new MelonDAO();
		try
		{
			int k=1;
			//for(int i=1;i<=101;i+=50)
			//{
				Document doc=Jsoup.connect("https://www.melon.com/genre/song_list.htm?gnrCode=GN0800").get();
				 Elements title=doc.select("div.rank01");
	             Elements singer=doc.select("div.rank02 span.checkEllipsis");
	             Elements album=doc.select("div.rank03");
	             Elements poster=doc.select("div.wrap img");
				
				for(int j=0;j<title.size();j++)
				{
					try
					{
						MelonVO vo=new MelonVO();
						System.out.println("번호:"+k++);
						System.out.println("cateno:1");
						System.out.println("제목:"+title.get(j).text()); 
						System.out.println("가수명:"+singer.get(j).text());
						System.out.println("앨범명:"+album.get(j).text());
						System.out.println("포스터:"+poster.get(j).attr("src"));
						System.out.println("==================================================");
						//vo에 값을 채우고 DAO에 보내기
						vo.setCateno(8);
						vo.setTitle(title.get(j).text());
						vo.setSinger(singer.get(j).text());
						vo.setAlbum(album.get(j).text());
						vo.setPoster(poster.get(j).attr("src"));
						//DAO로 전송
						dao.melonInsert(vo);
						Thread.sleep(100);
					}catch(Exception ex) {}
				}
				System.out.println("End..........");
			//}
		}catch(Exception ex) {}
	}
	public static void main(String[] args) {
		MelonManager m=new MelonManager();
		m.melonAllDate();
	}

}

