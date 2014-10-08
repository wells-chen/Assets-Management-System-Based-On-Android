package com.miot.management;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import com.miot.webservice.QueryInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Book_Inquiry_Activity extends Activity{
	private ListView listView;
	private SimpleAdapter adapter;
	private EditText editText;
	private Button button;
	private String Name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("�ʲ�����ϵͳ-ͼ���ѯ");
		setContentView(R.layout.activity_book_query);
		 editText =(EditText) findViewById(R.id.querybook_edittext);
		 button = (Button) findViewById(R.id.QueryBook_Btn);
		 listView = (ListView) findViewById(R.id.Book_Query_listview);
		 listView.setOnItemClickListener(itemListener); 
		
		 button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setListView();
				}
			});

		 
	}
//	/*��ӵ��Ч����listview*/
//	private void setListView() {
//		
//		 String Name = editText.getText().toString();
//		 String methodName = "DeviceQuery";
//		 mData = getAllInfo(methodName,Name);
//		 MyAdapter adapter = new MyAdapter(this);
//		 listView.setAdapter(adapter);
//	}
	
	 // ��Ŀ�ϵ���������.  
    OnItemClickListener itemListener = new OnItemClickListener() {  
        @Override  
        public void onItemClick(AdapterView<?> parent, View view, int position,  
                long id) {  
            // �����view��������list.xml�ж����LinearLayout����.  
            // ���Կ���ͨ��findViewById���������ҵ�list.xml�ж���������Ӷ���,����:  
            TextView stuId = (TextView) view.findViewById(R.id.B_Id); 
            Name = stuId.getText().toString();
          //ͨ��intent��ת����һ��ҳ��.
			Intent intent = new Intent();
			intent.setClass(Book_Inquiry_Activity.this, Book_Info_Activity.class);
			//ͨ��Bundle����ȡ����,ͨ��key-Value�ķ�ʽ��������
			Bundle bl = new Bundle();
			bl.putString("B_Id", Name);
			//��Bundle����Intent������һ��Activity
			intent.putExtras(bl);
			//������һ��Activity,���ҵȴ��䷵�ؽ��
			startActivityForResult(intent, 0);
//            toastShow(stuId.getText().toString());   
        }
 
    };  
    // ��װToast,һ������ü�,��һ���������ʾʱ��ֻҪ�Ĵ�һ���ط�����.  
//    public void toastShow(String text) {  
//        Toast.makeText(Device_Inquiry_Activity.this, text, 1000).show();  
//    }  


	/*����listView*/

	private void setListView() {

//		listView.setVisibility(View.VISIBLE);

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String Name = editText.getText().toString();
		 String methodName = "BookQuery";
		 String Name1 = "Name";
		 
		 list = getAllInfo(methodName,Name1,Name);
		adapter = new SimpleAdapter(
				Book_Inquiry_Activity.this, 
				list, 
				R.layout.activity_book_query_info, 
				new String[] { "B_Id", "B_Name", "L_Id","Remark" }, 
				new int[] { R.id.B_Id, R.id.B_Name, R.id.Book_L_Id, R.id.Book_Remark});

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


		for (int j = 0; j < crrayList.size(); j += 4) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("B_Id", crrayList.get(j));
			hashMap.put("B_Name", crrayList.get(j + 1));
			hashMap.put("L_Id", crrayList.get(j + 2));
			hashMap.put("Remark", crrayList.get(j + 3));
			list.add(hashMap);
		}

		return list;
	}
	

	
}
	 
