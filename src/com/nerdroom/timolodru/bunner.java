package com.nerdroom.timolodru;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class bunner {
String link;
Bitmap pic;
public bunner(String link,String bmp)
{
	this.link=link;
	this.pic=getBitmapFromURL(bmp);
}

public bunner(String link,Bitmap bmp)
{
	this.link=link;
	this.pic=bmp;
}
public static Bitmap getBitmapFromURL(String src) {
    try {
        URL url = new URL(src);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        Bitmap myBitmap = BitmapFactory.decodeStream(input);
        return myBitmap;
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
}
