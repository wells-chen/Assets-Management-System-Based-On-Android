package com.miot.webservice;

import android.os.Handler;
import android.os.Message;



public class MyHandler extends Handler {
	public void onSuccess(String content){
		
	}
	public void onFailure(String content){
		
	}
	@Override
    public void handleMessage(Message msg) {
		String content = (String) msg.obj;
		switch (msg.what) {
		case 1:		
			onSuccess(content);
			break;
		case 2:
			onFailure(content);
			break;

		default:
			break;
		}
	}
}
