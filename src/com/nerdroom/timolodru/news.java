package com.nerdroom.timolodru;


public class news extends item {
	String text;
	String href;
public news(String t, String txt, String link) {
		// TODO Auto-generated constructor stub
text=txt;
href=link;
title=t;
}
public news(String t, String txt, String link, String d) {
	// TODO Auto-generated constructor stub
text=txt;
href=link;
title=t;
date=d;
}
}
