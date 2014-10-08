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
		setTitle("资产管理系统-图书查询");
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
//	/*添加点击效果的listview*/
//	private void setListView() {
//		
//		 String Name = editText.getText().toString();
//		 String methodName = "DeviceQuery";
//		 mData = getAllInfo(methodName,Name);
//		 MyAdapter adapter = new MyAdapter(this);
//		 listView.setAdapter(adapter);
//	}
	
	 // 条目上单击处理方法.  
    OnItemClickListener itemListener = new OnItemClickListener() {  
        @Override  
        public void onItemClick(AdapterView<?> parent, View view, int position,  
                long id) {  
            // 这里的view是我们在list.xml中定义的LinearLayout对象.  
            // 所以可以通过findViewById方法可以找到list.xml中定义的它的子对象,如下:  
            TextView stuId = (TextView) view.findViewById(R.id.B_Id); 
            Name = stuId.getText().toString();
          //通过intent跳转到下一个页面.
			Intent intent = new Intent();
			intent.setClass(Book_Inquiry_Activity.this, Book_Info_Activity.class);
			//通过Bundle来获取数据,通过key-Value的方式放入数据
			Bundle bl = new Bundle();
			bl.putString("B_Id", Name);
			//将Bundle放入Intent传入下一个Activity
			intent.putExtras(bl);
			//跳到下一个Activity,并且等待其返回结果
			startActivityForResult(intent, 0);
//            toastShow(stuId.getText().toString());   
        }
 
    };  
    // 封装Toast,一方面调用简单,另一方面调整显示时间只要改此一个地方即可.  
//    public void toastShow(String text) {  
//        Toast.makeText(Device_Inquiry_Activity.this, text, 1000).show();  
//    }  


	/*设置listView*/

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
	 
