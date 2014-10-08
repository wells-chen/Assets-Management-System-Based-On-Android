package com.miot.webservice;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;

import android.os.Message;
import android.util.Xml;

public class AsyncHttpClient {
	public void get(final String meathodname,final String path,final MyHandler myhandler){
		new Thread(){
			public void run(){
				HttpClient client = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(path);
				try{
					HttpResponse response  = client.execute(httpget);
					InputStream is = response.getEntity().getContent();
					String content= parseSOAP(is,meathodname);
					Message msg = new Message();
					msg.obj = 
					msg.what = 1;
					myhandler.sendMessage(msg);
					
				}catch(Exception e){
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 2;
					msg.obj="—È÷§ ß∞‹";
					myhandler.sendMessage(msg);
					
				}
				
			}
		}.start();
	}
	
	
	
	private static String parseSOAP(InputStream xml,String methodname)throws Exception{
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");
		int event = pullParser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_TAG:
				String ifString = methodname + "Result";
				if(ifString.equals(pullParser.getName())){
					return pullParser.nextText();
				}
				break;
			}
			event = pullParser.next();
		}
		return "NO";
	}
	

}
