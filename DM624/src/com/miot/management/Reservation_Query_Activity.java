package com.miot.management;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import com.miot.utils.SaveInfo;
import com.miot.webservice.QueryInfo;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class Reservation_Query_Activity extends Activity{
	private ListView listView;
	private SimpleAdapter adapter;
//	private String Name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("资产管理系统--预约查询");
		setContentView(R.layout.activity_borrow_info);
		 listView = (ListView) findViewById(R.id.Borrow_Info_listview);
		// setListView();
                new Thread(){
			public void run() {
				try {
				final List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String UserId = SaveInfo.UserId.toString();
		 String methodName = "ReservationQuery";
		 String Name1 = "userid";
		 list = getAllInfo(methodName,Name1,UserId);		
					runOnUiThread(new Runnable() {
						public void run() {
							adapter = new SimpleAdapter(
				Reservation_Query_Activity.this, 
				list, 
				R.layout.activity_reservationquery, 
				new String[] { "Id", "D_Name","State" }, 
				new int[] { R.id.Reservation_Id, R.id.Reservation_D_Name, R.id.Reservation_State});
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

	/*设置listView*/

	private void setListView() {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String UserId = SaveInfo.UserId.toString();
		 String methodName = "ReservationQuery";
		 String Name1 = "userid";
		 list = getAllInfo(methodName,Name1,UserId);
		adapter = new SimpleAdapter(
				Reservation_Query_Activity.this, 
				list, 
				R.layout.activity_reservationquery, 
				new String[] { "Id", "D_Name","State" }, 
				new int[] { R.id.Reservation_Id, R.id.Reservation_D_Name, R.id.Reservation_State});

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
		hashMap0.put("Id", "预约编号");
		hashMap0.put("D_Name", "设备名称");			
		hashMap0.put("State", "状态");
		list.add(hashMap0);
		
		for (int j = 0; j < crrayList.size(); j += 3 ) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("Id", crrayList.get(j));
			hashMap.put("D_Name", crrayList.get(j + 1));			
			hashMap.put("State", crrayList.get(j + 2));
			list.add(hashMap);
		}

		return list;
	}
	


	
	
}
