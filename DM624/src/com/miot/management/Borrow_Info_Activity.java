package com.miot.management;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import com.miot.utils.SaveInfo;
import com.miot.webservice.QueryInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Borrow_Info_Activity extends Activity{
	private ListView listView;
	private SimpleAdapter adapter;
	private String Name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("资产管理系统--借还记录");
		setContentView(R.layout.activity_borrow_info);
		 listView = (ListView) findViewById(R.id.Borrow_Info_listview);
		 listView.setOnItemClickListener(itemListener); 
		 //setListView();
                 new Thread(){
			public void run() {
				try {
					final List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String UserId = SaveInfo.UserId.toString();
		 String methodName = "BorHisQuery";
		 String Name1 = "userid";
		 
		 list = getAllInfo(methodName,Name1,UserId);		
					runOnUiThread(new Runnable() {
						public void run() {
								adapter = new SimpleAdapter(
				Borrow_Info_Activity.this, 
				list, 
				R.layout.activity_borrow_info_textview, 
				new String[] { "Borrow_D_Id", "Borrow_D_Name", "Borrow_B_Time","Borrow_R_Time","State" }, 
				new int[] { R.id.Borrow_D_Id, R.id.Borrow_D_Name, R.id.Borrow_B_Time,R.id.Borrow_R_Time, R.id.Borrow_State});

		listView.setAdapter(adapter);						
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
		 
	}
	 // 条目上单击处理方法.  
    OnItemClickListener itemListener = new OnItemClickListener() {  
        @Override  
        public void onItemClick(AdapterView<?> parent, View view, int position,  
                long id) {  
            // 这里的view是我们在list.xml中定义的LinearLayout对象.  
            // 所以可以通过findViewById方法可以找到list.xml中定义的它的子对象,如下:  
            TextView stuId = (TextView) view.findViewById(R.id.Borrow_D_Id); 
            Name = stuId.getText().toString();
          //通过intent跳转到下一个页面.
			Intent intent = new Intent();
			intent.setClass(Borrow_Info_Activity.this, Device_info.class);
			//通过Bundle来获取数据,通过key-Value的方式放入数据
			Bundle bl = new Bundle();
			bl.putString("D_Id", Name);
			//将Bundle放入Intent传入下一个Activity
			intent.putExtras(bl);
			//跳到下一个Activity,并且等待其返回结果
			startActivityForResult(intent, 0);   
        }
 
    }; 

	/*设置listView*/

	private void setListView() {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String UserId = SaveInfo.UserId.toString();
		 String methodName = "BorHisQuery";
		 String Name1 = "userid";
		 
		 list = getAllInfo(methodName,Name1,UserId);
		adapter = new SimpleAdapter(
				Borrow_Info_Activity.this, 
				list, 
				R.layout.activity_borrow_info_textview, 
				new String[] { "Borrow_D_Id", "Borrow_D_Name", "Borrow_B_Time","Borrow_R_Time","State" }, 
				new int[] { R.id.Borrow_D_Id, R.id.Borrow_D_Name, R.id.Borrow_B_Time,R.id.Borrow_R_Time, R.id.Borrow_State});

		listView.setAdapter(adapter);
	}
	private  ArrayList<String> crrayList = new ArrayList<String>();
	/**
	 * 获取所有查询设备的信息
	 * 
	 * @return
	 */
	public  List<HashMap<String, String>> getAllInfo(String MethodName,String Name1,String Name) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		crrayList.clear();		
		try {
			crrayList = QueryInfo.Query_Info(MethodName,Name1,Name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HashMap<String, String> hashMap0 = new HashMap<String, String>();
		hashMap0.put("Borrow_D_Id", "资产ID");
		hashMap0.put("Borrow_D_Name", "资产名称");
		hashMap0.put("Borrow_B_Time", "借出时间");
		hashMap0.put("Borrow_R_Time", "归还时间");			
		hashMap0.put("State", "状态");
		list.add(hashMap0); 
		
		for (int j = 0; j < crrayList.size(); j += 5) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("Borrow_D_Id", crrayList.get(j));
			hashMap.put("Borrow_D_Name", crrayList.get(j + 1));
			hashMap.put("Borrow_B_Time", crrayList.get(j + 2).substring(0,9));
			hashMap.put("Borrow_R_Time", crrayList.get(j + 3).substring(0,9));			
			hashMap.put("State", crrayList.get(j + 4));
			list.add(hashMap);
		}

		return list;
	}


	
	
}
