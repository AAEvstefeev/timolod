package com.nerdroom.timolodru;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nerdroom.timolodru.R;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

class page_louder extends AsyncTask<Void, Void, Void> {
String page;
ProgressBar bar;
WebView web;
boolean b=false,loud=false;
final static int news=0;
final static int article=1;
final static int jornal=2;
final static int video=3;
final static int anons=3;
String q=null;
ArrayList<news> news_list;
ArrayList<video> video_list;
ListView list;
TextView textAlert;
	public page_louder(String st) 
	{
		page=st;
	}
	public void break_it()
	{
		b=true;
	}
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      bar=(ProgressBar) page_activity.mn.findViewById(R.id.bar);
      web=(WebView) page_activity.mn.findViewById(R.id.webView);
      textAlert=(TextView) page_activity.mn.findViewById(R.id.textAlert1);
      textAlert.setVisibility(View.GONE);
      web.setVisibility(View.GONE);
      bar.setVisibility(View.VISIBLE);
      
      Log.d("pagelouder", "pre execute");
      
    }

    @Override
    protected Void doInBackground(Void... params) {
    	loud(page);
		 // t.setText();
		  
      return null;
    }

    private void loud(String _page) {
		// TODO Auto-generated method stub
    	Log.d("pagelouder", "bag ground");
  	  Document doc = null;
  	  int i=0;
  	  do{
  	  try {
  		  i++;
 if(!_page.contains("&PAGEN_1="))
  		  if(_page.contains("http://www.timolod.ru/search/?q="))
 {
	 _page=_page.replace("http://www.timolod.ru/search/?q=", "");
	 q=_page;
	 _page= URLEncoder.encode( _page, "UTF-8");	
	 _page="http://www.timolod.ru/search/?q="+ _page;
 }

		// 	_page=new String( _page.getBytes(  "UTF-8" ),"UTF-8"); 
  //		_page = URLEncoder.encode(_page, "UTF-8");
  		 Log.d("url",_page);
  	
				doc = Jsoup.parse(new URL(_page).openStream(), "UTF-8", _page);
		//		doc = Jsoup.connect(_page).header("Content-Type:text/html; charset=UTF-8","application/x-www-form-urlencoded;charset=UTF-8").get();
			
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  	loud=true;
  	  if(doc==null)loud=false;
  	  else if(doc.getElementById("content")==null) loud=false;
  		  
    }
			while((b==false) && (loud==false) && (i<30));
			
			Element title=null;                       
			if(doc!=null)       
			title = doc.getElementById("content");   
		  
		    if(doc!=null)if(title!=null)
		    {
		    	 
		    	 doc.getElementsByClass("breadcrumb__item").remove();
		    if(doc.getElementById("vk_comments")!=null)	 doc.getElementById("vk_comments").remove();
		    if(doc.getElementsByClass("search-button").size()!=0)	
		    {
		    	Element newDiv = doc.createElement("div"); // Create the new element
		    	newDiv.attr("class", "search-result"); // Set it's values
		    	newDiv.text("Вы искали: \""+q+"\" "+doc.getElementsByClass("search-result").get(0).text());
		    	
		    	
		    	doc.getElementsByClass("search-result").get(0).replaceWith(newDiv);
		    			
		    	doc.getElementsByClass("search-query").remove();
		    	doc.getElementsByClass("search-button").remove();
		    	
		    	
		    }
		    	 
			Element head;
		
		  
	     
		  web.getSettings().setDefaultTextEncodingName("UTF-8");
		  web.getSettings().setJavaScriptEnabled(true);
		  web.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		  web.getSettings().getPluginState();
		  web.getSettings().setPluginState(PluginState.ON);
		  web.getSettings().setJavaScriptEnabled(true);
		  web.getSettings().setBuiltInZoomControls(true);
		//  web.getSettings().setUserAgent(0);
		  web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		  web.setWebChromeClient(new WebChromeClient() {
		    });
		  String html;
		  head=doc.head();
		  
		  title.getElementsByClass("breadcrumb clearfix").remove();
		  
		  Log.d("url",title.getElementsByTag("img").attr("src"));
		  
	//	  title.getElementById("vk_comments").text("height","100");
		 
		  html="<html><head><link rel=\"stylesheet\" href=\"/css/mobile_style.css?v=8\">"
		  +"</head><body>"+title.html()+"</body></html>";
		  web.setWebViewClient(new WebViewClient() {
	    	    @Override
	    	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    	    	String u=url.substring(url.length()-4,url.length());
	    	    if(!url.contains("http://")) 
	    	   {
	    	    	
	    	    	if(u.contains(".doc") || u.contains(".docx") && u.contains(".mp3") || u.contains(".pdf") || u.contains(".xls")|| u.contains(".xlsx")  
	    	    			|| u.contains(".odt") || u.contains(".cdr") )
	    	    	 {
	    	    		 Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	    	    		 page_activity.mn.startActivity(i);
	    	    	 }
	    	    	 else
	    	    {
	    	    	page_louder l=new page_louder("http://www.timolod.ru/"+url);
	    	    	if(q!=null) l.q=q;
	    	        l.execute();
	    	    }
	    	   }
	    	   if (url.contains("http://www.timolod.ru/"))
	    	   {
	    		   if(u.contains(".doc") || u.contains(".docx") && u.contains(".mp3") || u.contains(".pdf") || u.contains(".xlsx") || u.contains(".cdr"))
	    	    	 {
	    	    		 Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	    	    		 page_activity.mn.startActivity(i);
	    	    	 }
	    	    	 else
	    	    	 {
	    		   page_louder l=new page_louder(url);
	    		   if(q!=null) l.q=q;
	    	        l.execute();
	    	        }
	    	   }
	    	   if (!url.contains("http://www.timolod.ru/") && url.contains("http://"))
	    	//   view.loadUrl(url);
	    	   {
	    		   
	    	     Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	    	     page_activity.mn.startActivity(i);
	    	   }
	    	     return true;
	    	    }
	    	});	
		 // web.loadData(html, "text/html; charset=UTF-8", "UTF-8"); 
		   web.loadDataWithBaseURL("http://www.timolod.ru/", html, "text/html", "utf-8", "");  
		        
		    }
		
	}

	@Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      bar.setVisibility(View.GONE);
      if(loud==true)
     {
      web.setVisibility(View.VISIBLE);
     }
      else
      {
    	  textAlert.setVisibility(View.VISIBLE);	  
      }
   	//  textAlert.setVisibility(View.VISIBLE);  
      
      
      
    }
	
public void downlud(Uri uri)
{
	DownloadManager.Request r = new DownloadManager.Request(uri);

	// This put the download in the same Download dir the browser uses
	r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "fileName");

	// When downloading music and videos they will be listed in the player
	// (Seems to be available since Honeycomb only)
	r.allowScanningByMediaScanner();

	// Notify user when download is completed
	// (Seems to be available since Honeycomb only)
	r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

	// Start download
//	DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//	dm.enqueue(r);	
}
	
  }

