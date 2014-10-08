package com.miot.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Infor_Inquiry_Activity extends Activity{
	private ImageButton infoquery1,infoquery2,infoquery3;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_inquiry);
		infoquery1 = (ImageButton)findViewById(R.id.orderinfoquery);
		infoquery2 = (ImageButton)findViewById(R.id.borinfoquery);
		infoquery3 = (ImageButton)findViewById(R.id.personinfoquery);
		infoquery1.setImageResource(R.drawable.orderquery);
		infoquery2.setImageResource(R.drawable.borquery);
		infoquery3.setImageResource(R.drawable.personquery);
		
		infoquery3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Infor_Inquiry_Activity.this,Personinfoquery.class);
				startActivity(intent);
				
			}
		});
		infoquery2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Infor_Inquiry_Activity.this,Borrow_Info_Activity.class);
				startActivity(intent);
			}
		});
		infoquery1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Infor_Inquiry_Activity.this,Reservation_Query_Activity.class);
				startActivity(intent);
				
			}
		});
		
	}

}
