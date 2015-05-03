package com.nerdroom.timolodru;

import java.util.ArrayList;
import java.util.Collection;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nerdroom.timolodru.R;
import com.perm.kate.api.Api;





import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class page_activity extends SherlockFragmentActivity {
	 private final int REQUEST_LOGIN=1;
	static page_activity mn;
	 page_louder pl;
	 Context ctx;
	 String  page;
	 SlidingMenu menu;
	  Account account=new Account();
	    Api api;
	 private final Handler handler = new Handler();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mn=this;
		ctx=this;
		 setContentView(R.layout.webcontent);
		 Bundle extras = getIntent().getExtras();
	//	 slide_menu();
		    page = extras.getString("page"); 
		    pl=new page_louder(page);
		    pl.execute();
		    final ActionBar ab = getSupportActionBar();

	        // set defaults for logo & home up
	       ab.setDisplayHomeAsUpEnabled(true);
	    //    ab.setDisplayUseLogoEnabled(true);
	      //  ab.setIcon(android.R.color.transparent);
	       ab.setIcon(R.drawable.long_logo);
	}
	public void slide_menu()
	{
		menu = new SlidingMenu(this);
	     
	     menu.setMode(SlidingMenu.LEFT_RIGHT);
	     
	    
	     menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	     menu.setFadeDegree(0.35f);
	     menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
	     menu.setMenu(R.layout.slidemenu);
	     menu.setSecondaryMenu(R.layout.bunnermenu);
	     menu.setBehindWidth(300);
	     menu.setBackgroundColor(0xFFFFFF);
	     getActionBar().setHomeButtonEnabled(true);
	     ArrayList<menu_item> menu_list=new ArrayList<menu_item>();
	     
	     menu_list.add(new menu_item("Электроная приемная","http://www.timolod.ru/feedback/",true));
	     menu_list.add(new menu_item("Молодежные центры","http://www.timolod.ru/org_catalog/youth_centers/",false));
	     menu_list.add(new menu_item("Организации","http://www.timolod.ru/org_catalog/social_organisations/",false));
	     menu_list.add(new menu_item("Информация","http://www.timolod.ru/helpful/documents/",true));
	     //menu_list.add(new menu_item("Конференции","http://www.timolod.ru/helpful/documents/konferenc.php",false));
	    // menu_list.add(new menu_item("Документы КДМ","http://www.timolod.ru/helpful/documents/#kdm",false));
	    // menu_list.add(new menu_item("ГЦП «МОЛОДЁЖЬ НОВОСИБИРСКА»","http://www.timolod.ru/docs/molod2015-18/",false));
	     //menu_list.add(new menu_item("Профилактика экстремизма","http://www.timolod.ru/other/extrimizm.php",false));
	     menu_list.add(new menu_item("Кадры рашают все","http://www.timolod.ru/org_catalog/social_organisations/kr/",true));
	     menu_adapter adapter = new menu_adapter(MainActivity.mn, menu_list);	
	     ListView list_m = (ListView) findViewById(R.id.listmenu);
	     list_m.setAdapter(adapter);

	     
	     //  MainActivity.menu = menu;
	  //   this.setMenuItems();  	
	}
	
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu _menu) {
	

	 
		
		
		
		//-------
		getSupportMenuInflater().inflate(R.menu.page_menu, _menu);
	    
	    // set up a listener for the refresh item
	    final MenuItem searchView1 = (MenuItem) _menu.findItem(R.id.menu_refresh);
	    searchView1.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	        // on selecting show progress spinner for 1s
	        public boolean onMenuItemClick(MenuItem item) {
	            // item.setActionView(R.layout.progress_action);
	            handler.postDelayed(new Runnable() {
	                public void run() {
	                  //  refresh.setActionView(null);
	                	 if(pl!=null) pl.break_it();
	   		    	  pl=new page_louder(page);
	   				    pl.execute();	
	                	//menu.toggle();  
	                	
	                }
	            }, 0);
	            return false;
	        }
	    });
	    final MenuItem close = (MenuItem) _menu.findItem(R.id.menu_close);
	    close.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	        // on selecting show progress spinner for 1s
	        public boolean onMenuItemClick(MenuItem item) {
	            // item.setActionView(R.layout.progress_action);
	            handler.postDelayed(new Runnable() {
	                public void run() {
	                 finish();
	                	
	                }
	            }, 0);
	            return false;
	        }
	    });  
	    
	    final MenuItem vk = (MenuItem) _menu.findItem(R.id.menu_vk);
	    vk.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	        // on selecting show progress spinner for 1s
	        public boolean onMenuItemClick(MenuItem item) {
	            // item.setActionView(R.layout.progress_action);
	            handler.postDelayed(new Runnable() {
	                public void run() {
	                	 account.restore(ctx);
	                	   if(account.access_token!=null)
	                           {
	                		  
	                		   api=new Api(account.access_token, Constants.API_ID);
	                		   postToWall();
	                           }
	                	   else
	                	   		{
	                		   startLoginActivity();   
	                	   		}
	                }
	            }, 0);
	            return false;
	        }
	    });  
	    return super.onCreateOptionsMenu(_menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) 
		   {        
		      case android.R.id.home:            
		    	  Intent a = new Intent(this,MainActivity.class);
		          a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		          startActivity(a); 
		    	 
		    	  
		         return true; 
		      
		      default:            
		         return super.onOptionsItemSelected(item);    
		   }
	}
	
	
public void	onDestroy()
	{
	super.onDestroy();	
	pl.break_it();
	}
private void postToWall() {
    //Общение с сервером в отдельном потоке чтобы не блокировать UI поток
    new Thread(){
        @Override
        public void run(){
            try {
            	ArrayList<String> message=new ArrayList<String>(); 
            	message.add(page);
                api.createWallPost(account.user_id, page, message, null, false, false, false, null, null, null, null, null);
                
            	//api.repostWallPost(message+page, message, null, null, null);
                //Показать сообщение в UI потоке 
                runOnUiThread(successRunnable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }.start();
}
Runnable successRunnable=new Runnable(){
    @Override
    public void run() {
        Toast.makeText(getApplicationContext(), "Запись успешно добавлена", Toast.LENGTH_LONG).show();
    }
};
private void startLoginActivity() {
    Intent intent = new Intent();
    intent.setClass(this, LoginActivity.class);
    startActivityForResult(intent, REQUEST_LOGIN);
}
private void logOut() {
    api=null;
    account.access_token=null;
    account.user_id=0;
    account.save(page_activity.this);
    
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_LOGIN) {
        if (resultCode == RESULT_OK) {
            //авторизовались успешно 
            account.access_token=data.getStringExtra("token");
            account.user_id=data.getLongExtra("user_id", 0);
            account.save(page_activity.this);
            api=new Api(account.access_token, Constants.API_ID);
            
        }
    }
}
protected void onPause() {
    super.onPause();

    pl.cancel(true);
}	
protected void onResume() {
    super.onResume();

 
	 
	 pl=new page_louder(page);
	 pl.execute();
	 
}
}
