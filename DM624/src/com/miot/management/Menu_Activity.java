package com.miot.management;

import com.miot.utils.SaveInfo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu_Activity extends Activity {
	private ImageButton button1,button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		String UserName = "用户" + SaveInfo.UserName + "欢迎您";
		setTitle(UserName);
		setContentView(R.layout.activity_menu);
		button1 = (ImageButton)findViewById(R.id.imagebutton1);
		button2 = (ImageButton)findViewById(R.id.imagebutton2);
		button1.setImageResource(R.drawable.book);
		button2.setImageResource(R.drawable.device);
		
		button1.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu_Activity.this,Book_Menu_Activity.class);
				startActivity(intent);
			}
		});
		button2.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Menu_Activity.this,Device_Menu_Activity.class);
				startActivity(intent);
			}
		});
	}
	}
