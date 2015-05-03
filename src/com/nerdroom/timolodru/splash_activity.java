package com.nerdroom.timolodru;

import com.nerdroom.timolodru.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;



public class splash_activity extends Activity  {
splash_activity s;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		s=this;
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.splash);  
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);	 
		 new Handler().postDelayed(new Runnable(){
	            @Override
	            public void run() {
	                /* Create an Intent that will start the Menu-Activity. */
	            	
	                Intent mainIntent = new Intent(s,MainActivity.class);
	                s.startActivity(mainIntent);
	                s.finish();
	            }
	        }, 3000);
}
}