package com.miot.management;

import java.util.ArrayList;

import com.miot.utils.SaveInfo;
import com.miot.webservice.CheckUserInfo;
import com.miot.webservice.QueryInfo;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Personinfoquery extends Activity {
	private  ArrayList<String> brrayList ;
	private TextView userid,username,userstate,userkj,useryj,userphone;
	private Button btn1,btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personinfoquery);
		userid = (TextView)findViewById(R.id.Id);
		username = (TextView)findViewById(R.id.Name);
		userstate = (TextView)findViewById(R.id.State);
		userkj = (TextView)findViewById(R.id.Yj);
		useryj = (TextView)findViewById(R.id.Kj);
		userphone = (TextView)findViewById(R.id.Phone);
		btn1 = (Button)findViewById(R.id.InfoAlterBtn);
		btn2 = (Button)findViewById(R.id.PwdAlterBtn);
		brrayList = new ArrayList<String>();
		final String ID = SaveInfo.UserId;
		final String methodName = "PersonInfoQuery";
		final String method1 = "Userid";
		 
		/*Intent intent = this.getIntent();
		String ID = intent.getStringExtra("userID");*/
		new Thread(){
			public void run() {
				try {
					brrayList = QueryInfo.Query_Info(methodName,method1,ID);
					userid.setText(brrayList.get(0));
					username.setText(brrayList.get(1));
					userstate.setText(brrayList.get(2));
					userkj.setText(brrayList.get(3));
					useryj.setText(brrayList.get(4));
					userphone.setText(brrayList.get(5));
					SaveInfo.PassWord = brrayList.get(6);
					runOnUiThread(new Runnable() {
						public void run() {
														
						}
					});
					
				} catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						public void run() {
						Toast.makeText(Personinfoquery.this, "网络错误", 0).show();
						}
					});
				}
				
			}
			
		}.start();
		
		final String password = SaveInfo.PassWord;
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				infoalter();
			}

			private void infoalter() {
				final Dialog dialog = new Dialog(Personinfoquery.this);
				dialog.setContentView(R.layout.activity_alterphone);
				dialog.setTitle("请输入您的新手机号码");
				Window dialogWindow = dialog.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				dialogWindow.setGravity(Gravity.CENTER);  
		        dialogWindow.setAttributes(lp); 
		        dialog.show();
		        final EditText edittxt = (EditText)dialog.findViewById(R.id.alterphone);
		        Button confirm = (Button)dialog.findViewById(R.id.button1);
		        Button cancel = (Button)dialog.findViewById(R.id.button2);
		        confirm.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						final String phonenum = edittxt.getText().toString();
						if (phonenum.length()!=11) {
							Toast.makeText(Personinfoquery.this, "您输入的号码为空或者不正确，请重新再输!", Toast.LENGTH_SHORT).show();
							edittxt.setText("");
						}
						else {
							new Thread(){
								public void run() {
									String methodname = "Alterinfo";
									String methodname1 = "userid";
									String methodname2 = "phone";
									String result = null ;
									try {
										result = CheckUserInfo.CheckInfo(methodname,methodname1,methodname2,ID, phonenum);
									} catch (Exception e) {
										e.printStackTrace();
									}
									if (result.equals("YES")) {
										runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(Personinfoquery.this, "信息修改成功!", Toast.LENGTH_SHORT).show();
										userphone.setText(phonenum);
										dialog.dismiss();
											}
										});										
									}
									else {
										runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(Personinfoquery.this, "信息修改不成功!", Toast.LENGTH_SHORT).show();
										userphone.setText(phonenum);
										dialog.dismiss();
											}
										});				
									}
									}
																	
							}.start();
					    }
					}
				});
		        
		        cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pwdalter();
			}

			private void pwdalter() {
				final Dialog dialog = new Dialog(Personinfoquery.this);
				dialog.setContentView(R.layout.activity_alterpwd);
				dialog.setTitle("密码修改");
				Window dialogWindow = dialog.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				dialogWindow.setGravity(Gravity.CENTER);  
		        dialogWindow.setAttributes(lp); 
		        dialog.show();
		        final EditText editpwd = (EditText)dialog.findViewById(R.id.pwd);
		        final EditText editpwd1 = (EditText)dialog.findViewById(R.id.newpwd1);
		        final EditText editpwd2 = (EditText)dialog.findViewById(R.id.newpwd2);
		        Button confirm1 = (Button)dialog.findViewById(R.id.pwdbtn1);
		        Button cancel1 = (Button)dialog.findViewById(R.id.pwdbtn2);
		        
		        confirm1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String oldpassword = editpwd.getText().toString();
						if (!oldpassword.equals(password)) {
							Toast.makeText(Personinfoquery.this, "旧密码错误，请重新再输!", Toast.LENGTH_SHORT).show();
							editpwd.setText("");
							editpwd1.setText("");
							editpwd2.setText("");
						}
						else {
							String edit1 = editpwd1.getText().toString();
							String edit2 = editpwd2.getText().toString();
							if (oldpassword.equals(edit1)||edit1.equals(null)||edit2.equals(null)||!edit1.equals(edit2)) {
								Toast.makeText(Personinfoquery.this, "新密码为空或密码不一致，请重新再输!", Toast.LENGTH_SHORT).show();
								editpwd1.setText("");
								editpwd2.setText("");
							}
							else {
								String methodname = "AlterPwd";
								String methodname1 = "userid";
								String methodname2 = "newpwd";
								String result = null;
								try {
									result = CheckUserInfo.CheckInfo(methodname,methodname1,methodname2,ID, edit1);
								} catch (Exception e) {
									e.printStackTrace();
								}
								if (result.equals("YES")) {
									Toast.makeText(Personinfoquery.this, "密码修改成功!", Toast.LENGTH_SHORT).show();
								}
								else {
									Toast.makeText(Personinfoquery.this, "密码修改不成功!", Toast.LENGTH_SHORT).show();
								}
							}
						}
					}
				});
		        
		        cancel1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personinfoquery, menu);
		return true;
	}

}
