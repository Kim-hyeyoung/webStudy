package com.sist.manager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.dao.MusicDAO;

public class MusicManager {
	public void musicAllDate()
	{
		MusicDAO dao=new MusicDAO();
		try
		{
			int k=1;
			for(int i=1;i<=5;i++)
			{
				Document doc=Jsoup.connect("https://www.genie.co.kr/genre/L0207?genreCode=L0207&pg="+i).get();
				Elements title=doc.select("td.info a.title");
				Elements singer=doc.select("td.info a.artist");
				Elements album=doc.select("td.info a.albumtitle");
				Elements poster=doc.select("a.cover img");
				
				for(int j=0;j<title.size();j++)
				{
					try
					{
						MusicVO vo=new MusicVO();
						System.out.println("번호:"+k++);
						System.out.println("cateno:1");
						System.out.println("제목:"+title.get(j).text()); 
						System.out.println("가수명:"+singer.get(j).text());
						System.out.println("앨범명:"+album.get(j).text());
						System.out.println("포스터:"+poster.get(j).attr("src"));
						System.out.println("==================================================");
						//vo에 값을 채우고 DAO에 보내기
						vo.setCateno(10);
						vo.setTitle(title.get(j).text());
						vo.setSinger(singer.get(j).text());
						vo.setAlbum(album.get(j).text());
						vo.setPoster(poster.get(j).attr("src"));
						//DAO로 전송
						dao.musicInsert(vo);
						Thread.sleep(100);
					}catch(Exception ex) {}
				}
				System.out.println("End..........");
			}
		}catch(Exception ex) {}
	}
	public static void main(String[] args) {
		MusicManager m=new MusicManager();
		m.musicAllDate();
	}

}
