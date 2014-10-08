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
import android.widget.Toast;

public class Device_Inquiry_Activity extends Activity{
	private ListView listView;
	private SimpleAdapter adapter;
	private EditText editText;
	private Button button;
	private String Name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("资产管理系统--设备查询");
		setContentView(R.layout.activity_device_inquiry);
		 editText =(EditText) findViewById(R.id.queryedittext);
		 button = (Button) findViewById(R.id.QueryBtn);
		 listView = (ListView) findViewById(R.id.Device_Query_listview);
		 listView.setOnItemClickListener(itemListener); 
		
		 button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 new Thread(){
						    final String Name = editText.getText().toString();
							final String methodName = "DeviceQuery";
							final String Name1 = "Name";
							public void run() {
								
								try {	
									final List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(getAllInfo(methodName,Name1,Name));
									if (!list.isEmpty()) {
										runOnUiThread(new Runnable() {
											public void run() {
												adapter = new SimpleAdapter(
														Device_Inquiry_Activity.this, 
														list, 
														R.layout.activity_device_query_info, 
														new String[] { "D_Id", "D_Name", "L_Id","Remark" }, 
														new int[] { R.id.D_Id, R.id.D_Name, R.id.L_Id, R.id.Remark});
												listView.setAdapter(adapter);
											}
										});
										
									}
									
								} catch (Exception e) {																	
				  			        e.printStackTrace();
									runOnUiThread(new Runnable() {
										public void run() {
										Toast.makeText(Device_Inquiry_Activity.this, "网络错误", 0).show();
										}
									});
								}
							}
						}.start();	
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
            TextView stuId = (TextView) view.findViewById(R.id.D_Id); 
            Name = stuId.getText().toString();
          //通过intent跳转到下一个页面.
			Intent intent = new Intent();
			intent.setClass(Device_Inquiry_Activity.this, Device_info.class);
			//通过Bundle来获取数据,通过key-Value的方式放入数据
			Bundle bl = new Bundle();
			bl.putString("D_Id", Name);
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
		
		
		 
		adapter = new SimpleAdapter(
				Device_Inquiry_Activity.this, 
				list, 
				R.layout.activity_device_query_info, 
				new String[] { "D_Id", "D_Name", "L_Id","Remark" }, 
				new int[] { R.id.D_Id, R.id.D_Name, R.id.L_Id, R.id.Remark});

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

//		HashMap<String, String> tempHash = new HashMap<String, String>();
//		tempHash.put("D_Id", "D_Id");
//		tempHash.put("D_Name", "D_Name");
//		tempHash.put("L_Id", "L_Id");
//		tempHash.put("Remak", "Remak");
//		list.add(tempHash);
//		
		for (int j = 0; j < crrayList.size(); j += 4) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("D_Id", crrayList.get(j));
			hashMap.put("D_Name", crrayList.get(j + 1));
			hashMap.put("L_Id", crrayList.get(j + 2));
			hashMap.put("Remark", crrayList.get(j + 3));
			list.add(hashMap);
		}

		return list;
	}
	
//	  /** 
//     * 返回按钮的重写 
//     */  
//    @Override  
//    public void onBackPressed()  
//    {  
//        if (listView.getVisibility() == View.VISIBLE) {  
//            listView.setVisibility(View.GONE);  
//            hideButton(false);  
//        }else {  
//            MainActivity.this.finish();  
//        }  
//    }  
//}  
//	
//	 // ListView 中某项被选中后的逻辑
//   protected void onListItemClick(ListView l, View v, int position, long id) {
//        
//       Log.v("ListView-click", (String)mData.get(position).get("D_Id"));
//       Name = mData.get(position).get("D_Id");
//   }
//    
//   /**
//    * listview中点击按键弹出对话框
//    */
//   public void showInfo(){
//       new AlertDialog.Builder(this)
//       .setTitle("我的listview")
//       .setMessage(Name)
//       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//           @Override
//           public void onClick(DialogInterface dialog, int which) {
//           }
//       })
//       .show();
//        
//   }
//    
//    
//    
//   public final class ViewHolder{
//       public TextView D_Id;
//       public TextView D_Name;
//       public TextView L_Id;
//       public TextView Remark;
//   }
//    
//    
//   public class MyAdapter extends BaseAdapter{
//
//       private LayoutInflater mInflater;
//        
//        
//       public MyAdapter(Context context){
//           this.mInflater = LayoutInflater.from(context);
//       }
//       @Override
//       public int getCount() {
//           // TODO Auto-generated method stub
//           return mData.size();
//       }
//
//       @Override
//       public Object getItem(int arg0) {
//           // TODO Auto-generated method stub
//           return null;
//       }
//
//       @Override
//       public long getItemId(int arg0) {
//           // TODO Auto-generated method stub
//           return 0;
//       }
//
//       @Override
//       public View getView(int position, View convertView, ViewGroup parent) {
//            
//           ViewHolder holder = null;
//           if (convertView == null) {
//                
//               holder=new ViewHolder();  
//                
//               convertView = mInflater.inflate(R.layout.activity_device_query_info, null);
//               holder.D_Id = (TextView)convertView.findViewById(R.id.D_Id);
//               holder.D_Name = (TextView)convertView.findViewById(R.id.D_Name);
//               holder.L_Id = (TextView)convertView.findViewById(R.id.L_Id);
//               holder.Remark = (TextView)convertView.findViewById(R.id.Remark);
//               convertView.setTag(holder);
//                
//           }else {
//                
//               holder = (ViewHolder)convertView.getTag();
//           }
//            
//            
//           holder.D_Id.setText((String)mData.get(position).get("D_Id"));
//           holder.D_Name.setText((String)mData.get(position).get("D_Name"));
//           holder.L_Id.setText((String)mData.get(position).get("L_Id"));
//           holder.Remark.setText((String)mData.get(position).get("Remark"));
//            
//           holder.D_Id.setOnClickListener(new View.OnClickListener() {
//                
//               @Override
//               public void onClick(View v) {
//               	
//               	 Name = mData.get(position).get("D_Id");
//               	//通过intent跳转到下一个页面.
//   				Intent intent = new Intent();
//   				intent.setClass(Device_Inquiry_Activity.this, Device_info.class);
//   				//通过Bundle来获取数据,通过key-Value的方式放入数据
//   				Bundle bl = new Bundle();
//   				bl.putString("D_Id", Name);
//   				//将Bundle放入Intent传入下一个Activity
//   				intent.putExtras(bl);
//   				//跳到下一个Activity,并且等待其返回结果
//   				startActivityForResult(intent, 0);
//   				//不能够在这个Activity调用了startActivityForResult之后调用finsh()
//   				//否则无法接收到返回
////                   Intent intent = new Intent(Device_Inquiry_Activity.this,Device_info.class);
////                   startActivity(intent);
//               	showInfo();
//               }
//           });
//            
//            
//           return convertView;
//       }
//        
//   }
//	
//	
//	
	
	
	
}
	 
