package com.nerdroom.timolodru;



import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.nerdroom.timolodru.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class video_activity extends YouTubeBaseActivity implements 
	YouTubePlayer.OnInitializedListener{
	
	public static final String API_KEY = "AI39si7Gfo7lYRsB0xqn8NO3B6OL09hCtoRFAM1zluJXTLe-aukUrrWVRsV2-iEkSvUVAqBdmu3q1uDwIBbzb2b-GzvNvbMSfA";
	public static  String VIDEO_ID = "";
	
	private YouTubePlayer youTubePlayer;
	private YouTubePlayerFragment youTubePlayerFragment;
	//private TextView textVideoLog;
	private Button btnViewFullScreen;
	
	private static final int RQS_ErrorDialog = 1;
	
	private MyPlayerStateChangeListener myPlayerStateChangeListener;
	private MyPlaybackEventListener myPlaybackEventListener;
	
	String log = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        Bundle extras = getIntent().getExtras();
        VIDEO_ID = extras.getString("video");
       
        if(VIDEO_ID.equals(""))Log.d("vadio href", "null");
        VIDEO_ID=VIDEO_ID.replaceAll("//www.youtube.com/embed/", "");      
        Log.d("vadio href", VIDEO_ID);
        youTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager()
        	    .findFragmentById(R.id.youtubeplayerfragment);
        youTubePlayerFragment.initialize(API_KEY, this);

      //  textVideoLog = (TextView)findViewById(R.id.videolog);
        
        myPlayerStateChangeListener = new MyPlayerStateChangeListener();
        myPlaybackEventListener = new MyPlaybackEventListener();
    //    youTubePlayer.setFullscreen(true);
      
    }

	@Override
	public void onInitializationFailure(Provider provider,
			YouTubeInitializationResult result) {
	//	youTubePlayer.setFullscreen(true);
		if (result.isUserRecoverableError()) {
			result.getErrorDialog(this, RQS_ErrorDialog).show();	
		} else {
			
			 
		}
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player,
			boolean wasRestored) {
		
		youTubePlayer = player;
		
		
		
		youTubePlayer.setPlayerStateChangeListener(myPlayerStateChangeListener);
		youTubePlayer.setPlaybackEventListener(myPlaybackEventListener);
		
		if (!wasRestored) {
		      player.cueVideo(VIDEO_ID);
		    }

	}
	
	private final class MyPlayerStateChangeListener implements PlayerStateChangeListener {
		
		private void updateLog(String prompt){
			log += 	"MyPlayerStateChangeListener" + "\n" + 
					prompt + "\n\n=====";
		//	textVideoLog.setText(log);
		};

		@Override
		public void onAdStarted() {
			updateLog("onAdStarted()");
		}

		@Override
		public void onError(
				com.google.android.youtube.player.YouTubePlayer.ErrorReason arg0) {
			updateLog("onError(): " + arg0.toString());
		}

		@Override
		public void onLoaded(String arg0) {
			updateLog("onLoaded(): " + arg0);
		}

		@Override
		public void onLoading() {
			updateLog("onLoading()");
		}

		@Override
		public void onVideoEnded() {
			updateLog("onVideoEnded()");
		}

		@Override
		public void onVideoStarted() {
			updateLog("onVideoStarted()");
		}
		
	}
	
	private final class MyPlaybackEventListener implements PlaybackEventListener {
		
		private void updateLog(String prompt){
			log += 	"MyPlaybackEventListener" + "\n-" + 
					prompt + "\n\n=====";
		//	textVideoLog.setText(log);
		};

		@Override
		public void onBuffering(boolean arg0) {
			updateLog("onBuffering(): " + String.valueOf(arg0));
		}

		@Override
		public void onPaused() {
			updateLog("onPaused()");
		}

		@Override
		public void onPlaying() {
			updateLog("onPlaying()");
		}

		@Override
		public void onSeekTo(int arg0) {
			updateLog("onSeekTo(): " + String.valueOf(arg0));
		}

		@Override
		public void onStopped() {
			updateLog("onStopped()");
		}
		
	}

}
