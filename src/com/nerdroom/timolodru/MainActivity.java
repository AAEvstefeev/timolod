package com.nerdroom.timolodru;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;


import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;


import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nerdroom.timolodru.R;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;






import android.os.Bundle;
import android.os.Handler;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends SherlockFragmentActivity implements TabListener,SearchView.OnQueryTextListener  {
	boolean showHomeUp=false;
	boolean useLogo=false;
	String customHtml;
	int position=-1;
	louder l;
	 private SearchView mSearchView;
	SlidingMenu menu;
	PullToRefreshListView list;
	static MainActivity mn;
	private final Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mn=this;
		  Log.d("main", "start activity");
		setContentView(R.layout.activity_main);
		pull_to_refresh();
	//	louder blouder=new louder(louder.bunner);
	//	blouder.execute();
	    final ActionBar ab = getSupportActionBar();

        // set defaults for logo & home up
        ab.setDisplayHomeAsUpEnabled(true);
    //    ab.setDisplayUseLogoEnabled(true);
      //  ab.setIcon(android.R.color.transparent);
        ab.setIcon(R.drawable.long_logo);
        // set up tabs nav
        
            ab.addTab(ab.newTab().setText("Новости").setTabListener(this));
            ab.addTab(ab.newTab().setText("Статьи").setTabListener(this));
            ab.addTab(ab.newTab().setText("Видео").setTabListener(this));
         //   ab.addTab(ab.newTab().setText("Фотоотчеты").setTabListener(this));
        
            

        // set up list nav
        
        ab.setListNavigationCallbacks(ArrayAdapter
                .createFromResource(this, R.array.sections,
                        R.layout.sherlock_spinner_dropdown_item),
                new OnNavigationListener() {
                    public boolean onNavigationItemSelected(int itemPosition,
                            long itemId) {
                        // FIXME add proper implementation
                    	setContentView(R.layout.webcontent);
                    //	mn.finish();
                        return false;
                    }
                    
                });
        // default to tab navigation
        showTabsNav();
		slide_menu();
		
	
	      
		
	}
 private void search(String q) {
Intent si = new Intent(mn,page_activity.class);
si.putExtra("page","http://www.timolod.ru/search/?q="+q);
startActivity(si);
 }
private void pull_to_refresh() {
		// TODO Auto-generated method stub
		list = (PullToRefreshListView) findViewById(R.id.list);
		list.setOnRefreshListener(new OnRefreshListener() {
				
				@Override
				public void onRefresh() {
		list.postDelayed(new Runnable() {
			
			@Override
			public void run() {
			
				if(l!=null) l.break_it();
				louder l=new louder(position);
				l.execute();
				
				list.onRefreshComplete();
			}
		}, 2000);
	}
});	
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
     
     menu_list.add(new menu_item("Вопрос в комитет","http://www.timolod.ru/feedback/",true));
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
	
@Override
public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu _menu) {
	SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());

	getSupportMenuInflater().inflate(R.menu.main_menu, _menu);
	android.view.MenuInflater inflater = getMenuInflater();
   // inflater.inflate(R.menu.main_menu, _menu);
	MenuItem searchItem = _menu.findItem(R.id.menu_search);
    mSearchView = (SearchView) searchItem.getActionView();
    setupSearchView(searchItem);
    // set up a listener for the refresh item
    final MenuItem searchView1 = (MenuItem) _menu.findItem(R.id.menu_search);
    searchView1.setOnMenuItemClickListener(new OnMenuItemClickListener() {
        // on selecting show progress spinner for 1s
        public boolean onMenuItemClick(MenuItem item) {
            // item.setActionView(R.layout.progress_action);
            handler.postDelayed(new Runnable() {
                public void run() {
                 
                	
              //  	menu.toggle();  	  	
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
	    	  menu.toggle();              
	         return true; 
	      
	      default:            
	         return super.onOptionsItemSelected(item);    
	   }
  /**  switch (item.getItemId()) {
    case android.R.id.home:
        // TODO handle clicking the app icon/logo
        return false;
    case R.id.menu_refresh:
        // switch to a progress animation
    	item.setVisible(false);
   //    item.setActionView(R.layout.indeterminate_progress_action);
        return true;
  
 /*   case R.id.menu_logo:
        useLogo = !useLogo;
        item.setChecked(useLogo);
        getSupportActionBar().setDisplayUseLogoEnabled(useLogo);
        return true;
    case R.id.menu_up:
        showHomeUp = !showHomeUp;
        item.setChecked(showHomeUp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeUp);
        return true;
    case R.id.menu_nav_tabs:
        item.setChecked(true);
        showTabsNav();
        return true;
    case R.id.menu_nav_label:
        item.setChecked(true);
        showStandardNav();
        return true;
    case R.id.menu_nav_drop_down:
        item.setChecked(true);
        showDropDownNav();
        return true;
    case R.id.menu_bak_none:
        item.setChecked(true);
        getSupportActionBar().setBackgroundDrawable(null);
        return true;
    case R.id.menu_bak_gradient:
        item.setChecked(true);
       // getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ad_action_bar_gradient_bak));
        return true;
    default:
        return super.onOptionsItemSelected(item);
    }
    */
//	return true;
}
private void showStandardNav() {
    ActionBar ab = getSupportActionBar();
    if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_STANDARD) {
        ab.setDisplayShowTitleEnabled(true);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }
}

private void showDropDownNav() {
    ActionBar ab = getSupportActionBar();
    if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_LIST) {
        ab.setDisplayShowTitleEnabled(false);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    }
}

private void showTabsNav() {
    ActionBar ab = getSupportActionBar();
    if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
        ab.setDisplayShowTitleEnabled(false);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }
}

	@Override
	public void onTabSelected(com.actionbarsherlock.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
	//	int i=((Integer)tab.getTag()).intValue();
		
		if(l!=null) l.break_it();
		position=tab.getPosition();
		if(tab.getPosition()==0)
		{
			
			l=new	louder(louder.news);
			
		};
		if(tab.getPosition()==1)
		{
			l=new	louder(louder.article);
			
		};
		if(tab.getPosition()==2)
		{
			l=new	louder(louder.video);
			
		};
		if(tab.getPosition()==3)
		{
			
		};
		l.execute();
	}
	@Override
	public void onTabUnselected(com.actionbarsherlock.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabReselected(com.actionbarsherlock.app.ActionBar.Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	public void	onDestroy()
	{
	super.onDestroy();	
	l.break_it();
	}
	@Override
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		
		return false;
	}
	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		search(arg0);
	//	menu.toggle();
		return false;
	}
	  private void setupSearchView(MenuItem searchItem) {
		  
	    /**
		  SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        if (searchManager != null) {
	            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
	 
	            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
	            for (SearchableInfo inf : searchables) {
	                if (inf.getSuggestAuthority() != null
	                        && inf.getSuggestAuthority().startsWith("applications")) {
	                    info = inf;
	                }
	            }
	            mSearchView.setSearchableInfo(info);
	        }
	      **/
	 
	        mSearchView.setOnQueryTextListener(this);
	    }
	    public boolean onClose() {
	       // mStatusView.setText("Closed!");
	        return false;
	  
	    }
	    protected void onPause() {
	         super.onPause();
	 
	         l.cancel(true);
	     }	
	    protected void onResume() {
	         super.onResume();
	 
	     if(position!=-1) 
	    	 {
	    	 l=new louder(position);
	    	 l.execute();
	    	 }
	     
	     }
}
