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
	// ������¼��ȡ����ť
	private Button cancelBtn, loginBtn;
	// �����û��������������
	private EditText userEditText, pwdEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ʲ�����ϵͳ-�û���¼");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.activity_login);
		cancelBtn = (Button) findViewById(R.id.CancelBtn);
		// ͨ��findViewById����ʵ�������
		loginBtn = (Button) findViewById(R.id.LoginBtn);
		// ͨ��findViewById����ʵ�������
		userEditText = (EditText) findViewById(R.id.userEditText);
		// ͨ��findViewById����ʵ�������
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
				// �첽ִ�е���WebService������
				final String methodname = "CheckUserInfo";
				final String methodname1 = "UserId";
				final String methodname2 = "U_PassWord";
				final String UserId = userEditText.getText().toString().trim();
				// �������
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
											showDialog("�û����������������������");
										}
									});
							}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								runOnUiThread(new Runnable() {
									public void run() {
										showDialog("�������");
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
						showDialog("�û����ƻ�������������������룡");
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
				// �������
				String U_PassWord = pwdEditText.getText().toString();
				SoapObject request = new SoapObject(nameSpace, methodName);
				request.addProperty("UserId", UserId);
				request.addProperty("U_PassWord", U_PassWord);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
						SoapEnvelope.VER11);
				envelope.bodyOut = request;
				// �����Ƿ���õ���dotNet������WebService
				envelope.dotNet = true;
				// �ȼ���envelope.bodyOut = rpc;
				envelope.setOutputSoapObject(request);
				HttpTransportSE ht = new HttpTransportSE(serviceUrl);
				try {
					// ����WebService
					ht.call(serviceUrl, envelope);
				} catch (Exception e) {
					e.printStackTrace();
				}
				SoapObject object = (SoapObject) envelope.bodyIn;
				// ��ȡ���صĽ��
				result = object.getProperty(0).toString();
				return result;
			} catch (Exception e) {
				result = "����WebService����.";
			}
			// ����ʹ��post��������UI���
			loginBtn.post(new Runnable() {
				@Override
				public void run() {
				}

			});
			return null;

		}
	}*/

	// ��¼����
	private String login() {
		// ����û�����
		String UserId = userEditText.getText().toString();
		// �������
		String U_PassWord = pwdEditText.getText().toString();
		// ��õ�¼���
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

	// ��֤����
	private boolean validate() {
		String username = userEditText.getText().toString();
		if (username.equals("")) {
			showDialog("�û������Ǳ����");
			return false;
		}
		String pwd = pwdEditText.getText().toString();
		if (pwd.equals("")) {
			showDialog("�û������Ǳ����");
			return false;
		}
		return true;
	}

	private void showDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
