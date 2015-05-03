package com.nerdroom.timolodru;

import java.io.IOException;
import java.util.ArrayList;

import com.nerdroom.timolodru.R;

import eu.erikw.PullToRefreshListView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

class louder extends AsyncTask<Void, Void, Void> {
protected static int search=5;
int page;
String qr;
boolean loud=false, b=false; 
ProgressBar bar;
final static int news=0;
final static int article=1;
final static int jornal=2;
final static int video=3;
final static int anons=4;
final static int bunner=5;
ArrayList<news> news_list;
ArrayList<video> video_list;
ArrayList<bunner> bunner_list;
PullToRefreshListView list;
TextView textAlert;
	public void break_it()
	{
		b=true;
	}
	public louder(int st) 
	{
		page=st;
	}
	public louder(int st,String q) 
	{
		page=st;
		qr=q;
	}
	
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      list = (PullToRefreshListView) MainActivity.mn.findViewById(R.id.list);
      bar=(ProgressBar) MainActivity.mn.findViewById(R.id.spinbar);
      textAlert=(TextView) MainActivity.mn.findViewById(R.id.textAlert);
      textAlert.setVisibility(View.GONE);
      bar.setVisibility(View.VISIBLE);
      list.setVisibility(View.GONE);
      Log.d("louder", "pre execute");
      
    }

    @Override
    protected Void doInBackground(Void... params) {
    	Log.d("louder", "bag ground");
    	int i=0;
    	do
		 {	
    	try {
    		i++;
    		 if(page==news)
        	 {
        		 Log.d("louder", "get news");	
        		 news_list=parser.get_news(); 
        			
        	 }
        	 if(page==article)
        	 {
        		 Log.d("louder", "get article");	
        		 news_list=parser.get_article(); 
        			
        	 }
        	 if(page==anons)
        	 {
        		 Log.d("louder", "get video");	
        		 news_list=parser.get_anons(); 
        			
        	 }
        	 if(page==video)
        	 {
        		 Log.d("louder", "get video");	
        		 video_list=parser.get_video(1); 
        			
        	 }
        	 if(page==search)
        	 {
        		 Log.d("louder", "get video");	
        		 news_list=parser.get_search(qr); 
        			
        	 }
        	 if(page==bunner)
        	 {
        		 Log.d("louder", "get buner");	
        		 loud=true;
        		 bunner_list=parser.get_bunner(); 
        			
        	 }
        	 loud=true;
        	 if(page==video)
        		 {
        		 if(video_list==null) loud=false;
        		 else if(video_list.size()==0){loud=false;} 
        		 }
        	 if(page==news || page==article)
        	 	{
        		 if(news_list==null) loud=false;
        		 else if(news_list.size()==0){loud=false;} 
        	 	}
        	 if(page==bunner )
     	 	{
     		 if(bunner_list==null) loud=false;
     		 else if(bunner_list.size()==0){loud=false;} 
     	 	}
       
          } catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
          
       	
    	 } 
        	 while((b==false) && (loud==false) && (i<30));
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      bar.setVisibility(View.GONE);
      list.setVisibility(View.VISIBLE);
      if(page==news || page==article)   { 
    	 
    	  mylistadapter adapter = new mylistadapter(MainActivity.mn, news_list);	
	   			list.setAdapter(adapter);

	   			if(news_list!=null)		
	   				list.setSelection(news_list.size());
	   			else 
	   				list.setSelection(0);
	   		
	   		int index = list.getFirstVisiblePosition();
	   		View v = list.getChildAt(0);
	   		int top = (v == null) ? 0 : v.getTop();
	   		list.setSelectionFromTop(index, top);
	   	 if(news_list!=null) if(news_list.size()==0) 
	      {
	    	  textAlert.setVisibility(View.VISIBLE);
	      }
	     if(news_list==null)  
	     {
	   	  textAlert.setVisibility(View.VISIBLE);
	     } 
	     news_list=null;   
	    }	
      
      
      if(page==video)   { 
     	 
    	 video_adapter adapter = new video_adapter(MainActivity.mn, video_list);	
	   			list.setAdapter(adapter);

	   			if(video_list!=null)		
	   				list.setSelection(video_list.size());
	   			else 
	   				list.setSelection(0);
	   		
	   		int index = list.getFirstVisiblePosition();
	   		View v = list.getChildAt(0);
	   		int top = (v == null) ? 0 : v.getTop();
	   		list.setSelectionFromTop(index, top);
	   	 if(video_list!=null) if(video_list.size()==0) 
	      {
	    	  textAlert.setVisibility(View.VISIBLE);
	      }
	     if(video_list==null)  
	     {
	   	  textAlert.setVisibility(View.VISIBLE);
	     } 
	     video_list=null;   
	    }	
      
      
      if(page==bunner) 
      {
    	  
    	  Bitmap t=BitmapFactory.decodeResource(MainActivity.mn.getResources(), R.drawable.twitter);
    	  Bitmap v=BitmapFactory.decodeResource(MainActivity.mn.getResources(), R.drawable.vkontakte);
 if(bunner_list==null)bunner_list=new ArrayList<bunner>();
	 bunner_list.add(new bunner("https://twitter.com/GorodMolodyh",t));
     bunner_list.add(new bunner("http://vk.com/ti_molod",v));
    	  bunner_adapter bunner_adapter = new bunner_adapter(MainActivity.mn, bunner_list);
    	  ListView blist = (ListView) MainActivity.mn.findViewById(R.id.bunners);
    	  blist.setAdapter(bunner_adapter);
      }
      
	   		} 
 
  }

