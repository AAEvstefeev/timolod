package com.nerdroom.timolodru;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.nerdroom.timolodru.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Messenger;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class mylistadapter extends BaseAdapter {
  Context ctx;
  LayoutInflater lInflater;
  ArrayList<news> objects;

  
  public mylistadapter(Context context, ArrayList<news> arts) {
    ctx = context;
    objects = arts;
    
    lInflater = (LayoutInflater) ctx
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
  }

  // кол-во элементов

  public int getCount() {
	  if(objects!=null)
    return objects.size();
	  else
		  return 0;
		  
  }

  // элемент по позиции

  public Object getItem(int position) {
    return objects.get(position);
  }

  // id по позиции

  public long getItemId(int position) {
    return position;
  }

  // пункт списка
  private Drawable resize(Drawable image) {
	    Bitmap d = ((BitmapDrawable)image).getBitmap();
	    Bitmap bitmapOrig = Bitmap.createScaledBitmap(d, 50, 50, false);
	    return new BitmapDrawable(bitmapOrig);
	}
  @SuppressWarnings({ "unchecked", "deprecation" })
  public View getView(int position, View convertView, ViewGroup parent) {
    // используем созданные, но не используемые view
    View view = convertView;
    if (view == null) {
   view = lInflater.inflate(R.layout.item, parent, false);
    }

    final news p = getProduct(position);

    // заполнЯем View в пункте списка данными из товаров: наименование, цена
    // и картинка
    setViewTag(view, position );
  
  
  
 
  //  ((TextView) view.findViewById(R.id.date_message)).setTypeface(Typeface.createFromAsset(ctx.getAssets(),"NewCenSchCyrBol.ttf"));
    Button title=(Button) view.findViewById(R.id.title);
    title.setText(p.title);
    
// ((TextView) view.findViewById(R.id.who_say)).setTypeface(Typeface.createFromAsset(ctx.getAssets(),"NewCenSchCyrRom.ttf"));  
    ((TextView) view.findViewById(R.id.text)).setText(p.text);
  
 //   ((TextView) view.findViewById(R.id.what_say)).setTypeface(Typeface.createFromAsset(ctx.getAssets(),"NewCenSchCyrRom.ttf"));    
   ((TextView) view.findViewById(R.id.date)).setText(p.date);
   title.setOnClickListener(new OnClickListener() {
	   news p1=p;
		  @Override
		  public void onClick(View arg0) {
			  Intent intent = new Intent(MainActivity.mn, page_activity.class);
		       	 intent.putExtra("page", p.href);
		       	MainActivity.mn.startActivity(intent);
		    	  
			  }
	 
			});
	

	
//	 bitmap = codec(bitmap, Bitmap.CompressFormat.JPEG, 8);
	
 //p=null;
    return view;
  }

  private Bitmap codec(Bitmap bitmap, CompressFormat jpeg, int i) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(jpeg, i, os);
 
		byte[] array = os.toByteArray();
		return BitmapFactory.decodeByteArray(array, 0, array.length);
}

// товар по позиции
  news getProduct(int position) {
    return ((news) getItem(position));
  }


  private void setViewTag(View view, Object tag) {
	  
      view.setTag(tag);

      if (view instanceof ViewGroup) {

          for (int i=0; i < ((ViewGroup) view).getChildCount(); i++) {

              setViewTag(((ViewGroup) view).getChildAt(i), tag);

          }

      }

  }
  
  OnClickListener ImageClick = new OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			news p = getProduct((Integer)v.getTag());

			ViewParent t = v.getParent();
				//BitmapDrawable image = new BitmapDrawable(BitmapFactory.decodeByteArray(p.bmpplus, 0, p.bmpplus.length));	
				
			
		   		  
		   	
		

			//	TextView textView = (TextView)getListView().getChildAt((Integer)v.getTag());	
		//TextView tx = (TextView) t.findViewById(R.id.kolvo);	
		//tx.setText("fdfd");
		} 
		private Bitmap codec(Bitmap src, Bitmap.CompressFormat format,int quality) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			src.compress(format, quality, os);
	 
			byte[] array = os.toByteArray();
			return BitmapFactory.decodeByteArray(array, 0, array.length);
		}
	  };
}

