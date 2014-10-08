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
		setTitle("�ʲ�����ϵͳ--�軹��¼");
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
						Toast.makeText(Personinfoquery.this, "�������", 0).show();
						}
					});
				}
				
			}
			
		}.start();
		 
	}
	 // ��Ŀ�ϵ���������.  
    OnItemClickListener itemListener = new OnItemClickListener() {  
        @Override  
        public void onItemClick(AdapterView<?> parent, View view, int position,  
                long id) {  
            // �����view��������list.xml�ж����LinearLayout����.  
            // ���Կ���ͨ��findViewById���������ҵ�list.xml�ж���������Ӷ���,����:  
            TextView stuId = (TextView) view.findViewById(R.id.Borrow_D_Id); 
            Name = stuId.getText().toString();
          //ͨ��intent��ת����һ��ҳ��.
			Intent intent = new Intent();
			intent.setClass(Borrow_Info_Activity.this, Device_info.class);
			//ͨ��Bundle����ȡ����,ͨ��key-Value�ķ�ʽ��������
			Bundle bl = new Bundle();
			bl.putString("D_Id", Name);
			//��Bundle����Intent������һ��Activity
			intent.putExtras(bl);
			//������һ��Activity,���ҵȴ��䷵�ؽ��
			startActivityForResult(intent, 0);   
        }
 
    }; 

	/*����listView*/

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
	 * ��ȡ���в�ѯ�豸����Ϣ
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
		hashMap0.put("Borrow_D_Id", "�ʲ�ID");
		hashMap0.put("Borrow_D_Name", "�ʲ�����");
		hashMap0.put("Borrow_B_Time", "���ʱ��");
		hashMap0.put("Borrow_R_Time", "�黹ʱ��");			
		hashMap0.put("State", "״̬");
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
