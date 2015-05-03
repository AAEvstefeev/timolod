package com.nerdroom.timolodru;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import android.util.Log;

public  class parser {
	public static ArrayList<news> get_news() throws IOException
	{
		Log.d("parser", "get news");
		return get_paper("http://www.timolod.ru/news/");
	}
	public static ArrayList<news> get_article() throws IOException
	{
		Log.d("parser", "get article");
		return get_paper("http://www.timolod.ru/news/nreport/");
	}	
	public static ArrayList<news> get_anons() throws IOException
	{
		Log.d("parser", "get anons");
		return get_paper("http://www.timolod.ru/billboard/?PAGEN_1=1");
	}
	public static ArrayList<news> get_jornal() throws IOException
	{
		return get_paper("http://www.timolod.ru/youngcity/journal/electronic_journals/journal_of_d.php");
	}
	public static ArrayList<news> get_paper(String adr) throws IOException
	{
		Document doc;   
		doc = Jsoup.connect(adr).get();
	                           
	        
	       ArrayList<news> news = new ArrayList<news>();
	   //  Elements links = doc.getElementsByTag("div class=\"title\"");
	       Elements title = doc.getElementsByClass("title");   
	       Elements text = doc.getElementsByClass("text");
	       Elements date = doc.getElementsByClass("activity");
	       int i =0;
	      
	       while(i<title.size()) {
	    	   Log.d("заголовок", title.get(i).text());   
	           Log.d("ссыль",  title.get(i).attr("http://www.timolod.ru"+"href"));
	           Elements video = title.get(i).getElementsByTag("iframe");
	           String link="http://www.timolod.ru"+title.get(i).attr("href");
	           String t=title.get(i).text();
	           String d=null;
		          if(date.size()>i) 
		        	  d=date.get(i).text();
	           String txt=null;
	          if(text.size()>i) 
	        	  txt=text.get(i).text();
	     if(text.size()>i) news.add(new news(t,txt,link,d));
	           i++;
	       }

	    return news;   
	  
	}
	public static ArrayList<news> get_search(String adr) throws IOException
	{
		Document doc;   
		doc = Jsoup.connect("http://www.timolod.ru/search/?q="+adr).get();
	                           
	        
	       ArrayList<news> news = new ArrayList<news>();
	   //  Elements links = doc.getElementsByTag("div class=\"title\"");
	       Elements title = doc.getElementsByClass("search-item");   
	       Elements text = doc.getElementsByClass("search-item-meta");
	       int i =0;
	      
	       while(i<title.size()) {
	    	   Log.d("заголовок", title.get(i).html());   
	           Log.d("ссыль",  "http://www.timolod.ru"+title.get(i).attr("href"));
	          
	           String link="http://www.timolod.ru"+ title.get(i).getElementsByTag("a").attr("href");;
	           String t= title.get(i).getElementsByTag("a").text();
	           String txt=null;
	          if(text.size()>i) 
	        	  txt=text.get(i).text();
	          
	       if(text.size()>i) news.add(new news(t,txt,link));
	           i++;
	       }

	    return news;   
	  
	}
	public static ArrayList<video> get_video(int n) throws IOException
	{
		Log.d("parser", "get video");
		ArrayList<video> video = new ArrayList<video>(); 
		Document doc;
		int str=1;
	   
		         
		  doc = Jsoup.connect("http://www.timolod.ru/youngcity/telecast/?logout=yes&bitrix_include_areas=N&PAGEN_1="+str).get();   
	   Elements links = doc.getElementsByTag("iframe");
	     Elements title = doc.getElementsByClass("title");
	      
	      int i=0; 
	      while(i<links.size()) 
	      {
	    	String t=title.get(i).text(); 
	        String src=null;
	        Log.d("заголовок", title.get(i).text()); 
	        src=links.get(i).attr("src");   
	        video.add(new video(t,src));
	         i++;
	         // Element2 link = links2.getelement();
	         // Log.d("текст", links2.text());
	      }
	      
	     
			
	 return video;
	   
	}
	public static ArrayList<bunner> get_bunner() throws IOException {
		// TODO Auto-generated method stub
		Log.d("parser", "get video");
		ArrayList<bunner> bunner = new ArrayList<bunner>(); 
		Document doc;
		int str=1;
	   
		         
		  doc = Jsoup.connect("http://www.timolod.ru/").get();	
		  Elements banner_list = doc.getElementsByClass("banner-list");
		 
		  Elements pics = banner_list.get(1).getElementsByTag("img");
		     Elements links = banner_list.get(1).getElementsByTag("a"); 
		     int i=0; 
		     while(i<links.size()) 
		      {
		    	String href=null; 
		        String src=null;
		        Log.d("заголовок", links.get(i).text()); 
		        src="http:"+pics.get(i).attr("src");   
		        href=links.get(i).attr("href");
		        
		        bunner.add(new bunner(href,src));
		         i++;
		         // Element2 link = links2.getelement();
		         // Log.d("текст", links2.text());
		      }
		return bunner;
	}
}
