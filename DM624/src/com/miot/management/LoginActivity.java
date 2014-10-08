package com.miot.management;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.miot.utils.SaveInfo;
import com.miot.webservice.CheckUserInfo;

public class LoginActivity extends Activity {
	String result = "";
	// 声明登录、取消按钮
	private Button cancelBtn, loginBtn;
	// 声明用户名、密码输入框
	private EditText userEditText, pwdEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("资产管理系统-用户登录");
		// 设置当前Activity界面布局
		setContentView(R.layout.activity_login);
		cancelBtn = (Button) findViewById(R.id.CancelBtn);
		// 通过findViewById方法实例化组件
		loginBtn = (Button) findViewById(R.id.LoginBtn);
		// 通过findViewById方法实例化组件
		userEditText = (EditText) findViewById(R.id.userEditText);
		// 通过findViewById方法实例化组件
		pwdEditText = (EditText) findViewById(R.id.pswEditText);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 异步执行调用WebService的任务
				final String methodname = "CheckUserInfo";
				final String methodname1 = "UserId";
				final String methodname2 = "U_PassWord";
				final String UserId = userEditText.getText().toString().trim();
				// 获得密码
				SaveInfo.UserId = UserId;
				final String U_PassWord = pwdEditText.getText().toString().trim();
				if(validate()){
					new Thread(){
						public void run() {
							final String result ;
							try {
								result = CheckUserInfo.CheckInfo(methodname, methodname1, methodname2, UserId, U_PassWord);
								if (!result.equals("No")) {
									runOnUiThread(new Runnable() {
										public void run() {
											SaveInfo.UserName = result;
											Intent intent = new Intent();
											intent.setClass(LoginActivity.this, Menu_Activity.class);
											intent.putExtra("userID", userEditText.getText()
													.toString());
											startActivity(intent);	
										}
									});
												 				
								}else {
									runOnUiThread(new Runnable() {
										public void run() {
											showDialog("用户名或密码错误，请重新输入");
										}
									});
							}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								runOnUiThread(new Runnable() {
									public void run() {
										showDialog("网络错误！");
									}
								});
							}
							
					}
				/*String path = "http://222.200.105.249:11125/Service1.asmx?op=CheckUserInfo"+URLEncoder.encode(UserId)+URLEncoder.encode(U_PassWord);
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(methodName,path, new MyHandler(){

					@Override
					public void onSuccess(String content) {
						if(!content.equals("No")){
						SaveInfo.UserName = content;
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, Menu_Activity.class);
						intent.putExtra("userID", userEditText.getText()
								.toString());
						startActivity(intent);
						}
						super.onSuccess(content);
					}

					@Override
					public void onFailure(String content) {
						showDialog("用户名称或者密码错误，请重新输入！");
						super.onFailure(content);
					}*/
					}.start();		
			}
			}
		});
	}							

	/*class WSAsyncTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... params) {
			try {
				String serviceUrl = "http://222.200.105.249:11125/Service1.asmx?op=CheckUserInfo";
				String methodName = "CheckUserInfo";
				String nameSpace = "http://tempuri.org/";
				String UserId = userEditText.getText().toString();
				// 获得密码
				String U_PassWord = pwdEditText.getText().toString();
				SoapObject request = new SoapObject(nameSpace, methodName);
				request.addProperty("UserId", UserId);
				request.addProperty("U_PassWord", U_PassWord);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.bodyOut = request;
				// 设置是否调用的是dotNet开发的WebService
				envelope.dotNet = true;
				// 等价于envelope.bodyOut = rpc;
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(serviceUrl);
				try {
					// 调用WebService
					ht.call(serviceUrl, envelope);
				} catch (Exception e) {
					e.printStackTrace();
				}
				SoapObject object = (SoapObject) envelope.bodyIn;
				// 获取返回的结果
				result = object.getProperty(0).toString();
				return result;
			} catch (Exception e) {
				result = "调用WebService错误.";
			}
			// 必须使用post方法更新UI组件
			loginBtn.post(new Runnable() {
				@Override
				public void run() {
				}

			});
			return null;

		}
	}*/

	// 登录方法
	private String login() {
		// 获得用户名称
		String UserId = userEditText.getText().toString();
		// 获得密码
		String U_PassWord = pwdEditText.getText().toString();
		// 获得登录结果
		String methodname = "CheckUserInfo";
		String methodname1 = "UserId";
		String methodname2 = "U_PassWord";
		String result = null;
		try {

			result = CheckUserInfo.CheckInfo(methodname, methodname1,
					methodname2, UserId, U_PassWord);
		} catch (Exception e) {
			e.printStackTrace();
			result = "erro";
		}
		if (result.equals("No")) {

			return "No";
		} else if (result.equals("erro")) {
			return "erro";
		} else {
			SaveInfo.UserId = UserId;
			result = result.replace(" ", "");
			SaveInfo.UserName = result;
			return "YES";
		}
	}

	// 验证方法
	private boolean validate() {
		String username = userEditText.getText().toString();
		if (username.equals("")) {
			showDialog("用户名称是必填项！");
			return false;
		}
		String pwd = pwdEditText.getText().toString();
		if (pwd.equals("")) {
			showDialog("用户密码是必填项！");
			return false;
		}
		return true;
	}

	private void showDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
