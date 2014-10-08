package com.miot.management;


import java.util.ArrayList;

import com.miot.utils.SaveInfo;
import com.miot.webservice.CheckUserInfo;
import com.miot.webservice.QueryInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("ResourceAsColor")
public class Book_Info_Activity extends Activity{
	private Button button;
	private  ArrayList<String> crrayList = new ArrayList<String>();
	private String Name;
	private EditText editText;
	private TextView textView_D_Id,D_Name_TV,BuyTime_TV,Price_TV,L_Name_TV,T_Name_TV,borrow_UserTV,D_State_TV,Remark_TV;
	Bundle bl;
	Intent intent;
	Button btn;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("资产管理系统-图书信息");
		//与device共用信息显示界面
		setContentView(R.layout.activity_device_info_show);
		//获取到上一个页面传过来的Intent
		 intent=this.getIntent();
		//获取Intent中的Bundle数据
		 bl=intent.getExtras();
		 Name = bl.getString("B_Id");
		 editText = (EditText) findViewById(R.id.Device_Reservation_EditText);
		 button = (Button) findViewById(R.id.Device_Reservation);
		 textView_D_Id = (TextView) findViewById(R.id.Detail_D_Id);
		 D_Name_TV = (TextView) findViewById(R.id.Detail_D_Name);
		 BuyTime_TV = (TextView) findViewById(R.id.Detail_BuyTime);
		 Price_TV = (TextView) findViewById(R.id.Detail_Price);
		 L_Name_TV = (TextView) findViewById(R.id.Detail_L_Name);
		 T_Name_TV = (TextView) findViewById(R.id.Detail_T_Name);
		 borrow_UserTV = (TextView) findViewById(R.id.Detail_UserName);
		 D_State_TV = (TextView) findViewById(R.id.Detail_D_State);
		 Remark_TV = (TextView) findViewById(R.id.Detail_Remark);
		 setListView();
		 if (!D_State_TV.getText().toString().equals("可借")) {
			 button.setBackgroundColor(R.color.dimgray);
			}
		 button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {				
					if (D_State_TV.getText().toString().equals("可借")) {
						showInfo();
					}
					else {
						toastShow("图书在借状态，不可预约");
					}

				}
			});

		 
	}
	/*添加点击效果的listview*/
	private void setListView() {
		 String MethodName = "BookInfo";
		 String Name1 = "Name";
		 crrayList.clear();
		 try {
			crrayList = QueryInfo.Query_Info(MethodName,Name1,Name);
			textView_D_Id.setText(crrayList.get(0));
			D_Name_TV.setText(crrayList.get(1));
			BuyTime_TV.setText(crrayList.get(2).substring(0,9));
			Price_TV.setText(crrayList.get(3));
			L_Name_TV.setText(crrayList.get(4));
			T_Name_TV.setText(crrayList.get(5));
			borrow_UserTV.setText(crrayList.get(7));
			D_State_TV.setText(crrayList.get(8));
			Remark_TV.setText(crrayList.get(6));
			
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
    /**
     * 点击预约按键弹出对话框
     */
    public void showInfo(){
    	AlertDialog.Builder builder = new Builder(this);
    	builder.setTitle("提示信息");
        builder.setMessage("您预约的图书为"+crrayList.get(1));
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	String UserId = SaveInfo.UserId;
            	try {
            		//将UserId与D_Id绑定一起传输，便于重用service中两个变量的代码
            		String remarkString = editText.getText().toString();
            		String bindleString = UserId +"@" +Name;
					String state = CheckUserInfo.CheckInfo("Reservation","UserId","D_Id",bindleString, remarkString);
					if (state!=null) {
						state = "恭喜你，预约成功：预约单号为"+state;
					}
					else {
						state = "预约失败,请重新预约";
					}
					
					 toastShow(state);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        	@Override
        	public void onClick(DialogInterface dialog, int which) {
        		dialog.dismiss();
        	}
        });
    	builder.create().show();    
    }
    
 // 封装Toast,一方面调用简单,另一方面调整显示时间只要改此一个地方即可.  
  public void toastShow(String text) {  
      Toast.makeText(Book_Info_Activity.this, text, 20000).show();  
  }  
}

    
//	/**
//	 * 获取所有查询设备的信息
//	 * 
//	 * @return
//	 */
//	public  List<HashMap<String, String>> GetBookInfo(String MethodName,String Name1,String Name) {
//		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//
//		crrayList.clear();
//
//		
//
//		try {
//			crrayList = QueryInfo.Query_Info(MethodName,Name1,Name);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		for (int j = 0; j < crrayList.size(); j += 9) {
//			HashMap<String, String> hashMap = new HashMap<String, String>();
//			hashMap.put("Detail_B_Id", crrayList.get(j));
//			hashMap.put("Detail_B_Name", crrayList.get(j + 1));
//			hashMap.put("Detail_BuyTime", crrayList.get(j + 2));
//			hashMap.put("Detail_Price", crrayList.get(j + 3));
//			hashMap.put("Detail_L_Name", crrayList.get(j + 4));
//			hashMap.put("Detail_T_Name", crrayList.get(j + 5));
//			hashMap.put("Detail_UserName", crrayList.get(j + 7));
//			hashMap.put("Detail_B_State", crrayList.get(j + 8));
//			hashMap.put("Detail_Remark", crrayList.get(j + 6));
//
//			list.add(hashMap);
//		}
//
//		return list;
//	}
//	
//
//}
