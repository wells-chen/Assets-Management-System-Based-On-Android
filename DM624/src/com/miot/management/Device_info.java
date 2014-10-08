package com.miot.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("ResourceAsColor")
public class Device_info extends Activity{
	private Button button;
	private EditText editText;
	private  ArrayList<String> crrayList = new ArrayList<String>();
	private String Name;
	private TextView textView_D_Id,D_Name_TV,BuyTime_TV,Price_TV,L_Name_TV,T_Name_TV,borrow_UserTV,D_State_TV,Remark_TV;
	Bundle bl;
	Intent intent;
	Button btn;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("�ʲ�����ϵͳ--�豸��Ϣ");
		setContentView(R.layout.activity_device_info_show);
		//��ȡ����һ��ҳ�洫������Intent
		 intent=this.getIntent();
		//��ȡIntent�е�Bundle����
		 bl=intent.getExtras();
		 Name = bl.getString("D_Id");
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
		 new Thread(){
				public void run() {	
					try {	
						 String MethodName = "DeviceInfo";
						 String Name1 = "Name";
						  crrayList.clear();						
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
						if (!crrayList.isEmpty()) {
							runOnUiThread(new Runnable() {
								public void run() {
									if (!D_State_TV.getText().toString().equals("�ɽ�")) {
										 button.setBackgroundColor(R.color.dimgray);
										}
							
								}
							});
							
						}
						
					} catch (Exception e) {																	
	  			        e.printStackTrace();
						runOnUiThread(new Runnable() {
							public void run() {
							Toast.makeText(Device_info.this, "�������", 0).show();
							}
						});
					}
				}
									
			}.start();	
			 button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {				
						if (D_State_TV.getText().toString().equals("�ɽ�")) {
							/*new Thread(){
								public void run() {
									try {
										
									} catch (Exception e) {
										// TODO: handle exception
									}
									
								}
								
							}.start();*/
							showInfo();
						}
						else {
							toastShow("ͼ���ڽ�״̬������ԤԼ");
						}
					}
		
				});
		 

		 
	}
	/*��ӵ��Ч����listview*/
	private void setListView() {
		 String MethodName = "DeviceInfo";
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	  
    /**
     * ���ԤԼ���������Ի���
     */
    public void showInfo(){
    	AlertDialog.Builder builder = new Builder(this);
    	builder.setTitle("��ʾ��Ϣ");
        builder.setMessage("��ԤԼ���豸Ϊ"+crrayList.get(1));
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	final String UserId = SaveInfo.UserId;
            		new Thread(){
						public void run() {
							try {
								String remarkString = editText.getText().toString();
			            		String bindleString = UserId +"@" +Name;
								final String state = CheckUserInfo.CheckInfo("Reservation","UserId","D_Id",bindleString, remarkString);
								if (state!=null) {
									runOnUiThread(new Runnable() {
										public void run() {
										String	content = "��ϲ�㣬ԤԼ�ɹ���ԤԼ����Ϊ"+state;
										Toast.makeText(Device_info.this, content, 0).show();
										}
									});
									
								}
								else {
									runOnUiThread(new Runnable() {
										public void run() {
											String content = "ԤԼʧ��,������ԤԼ";
										    Toast.makeText(Device_info.this, content, 0).show();
										}
									});
									
								}
							} catch (Exception e) {
								runOnUiThread(new Runnable() {
									public void run() {
									Toast.makeText(Device_info.this, "�������", 0).show();
									}
								});
							}
							
						}
						
					}.start();           							
            }
        });
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
        	@Override
        	public void onClick(DialogInterface dialog, int which) {
        		dialog.dismiss();
        	}
        });
    	builder.create().show();    
    }
    
 // ��װToast,һ������ü�,��һ���������ʾʱ��ֻҪ�Ĵ�һ���ط�����.  
  public void toastShow(String text) {  
      Toast.makeText(Device_info.this, text, 20000).show();  
  }  



//  
//	/**
//	 * ��ȡ���в�ѯ�豸����Ϣ
//	 * 
//	 * @return
//	 */
//	public  List<HashMap<String, String>> GetDeviceInfo(String MethodName,String Name1,String Name) {
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
//			hashMap.put("Detail_D_Id", crrayList.get(j));
//			hashMap.put("Detail_D_Name", crrayList.get(j + 1));
//			hashMap.put("Detail_BuyTime", crrayList.get(j + 2));
//			hashMap.put("Detail_Price", crrayList.get(j + 3));
//			hashMap.put("Detail_L_Name", crrayList.get(j + 4));
//			hashMap.put("Detail_T_Name", crrayList.get(j + 5));
//			hashMap.put("Detail_UserName", crrayList.get(j + 7));
//			hashMap.put("Detail_D_State", crrayList.get(j + 8));
//			hashMap.put("Detail_Remark", crrayList.get(j + 6));
//
//			list.add(hashMap);
//		}
//
//		return list;
//	}
//	
	
//	
//	 // ListView ��ĳ�ѡ�к���߼�
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//         
//        Log.v("ListView-click", (String)mData.get(position).get("D_Id"));
//    }
//     
//    /**
//     * listview�е�����������Ի���
//     */
//    public void showInfo(){
//        new AlertDialog.Builder(this)
//        .setTitle("ԤԼ��ʾ��Ϣ")
//        .setMessage("����...")
//        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            	
//            }
//        })
//        .show();
//         
//    }
//     
//     
//     
//    public final class ViewHolder{
//        public TextView Detail_D_Id;
//        public TextView Detail_D_Name;
//        public TextView Detail_BuyTime;
//        public TextView Detail_Price;
//        public TextView Detail_L_Id;
//        public TextView Detail_UserId;
//        public TextView Detail_Remark;
//        public Button Device_Reservation;
//    }
//     
//     
//    public class MyAdapter extends BaseAdapter{
// 
//        private LayoutInflater mInflater;
//         
//         
//        public MyAdapter(Context context){
//            this.mInflater = LayoutInflater.from(context);
//        }
//        @Override
//        public int getCount() {
//            // TODO Auto-generated method stub
//            return mData.size();
//        }
// 
//        @Override
//        public Object getItem(int arg0) {
//            // TODO Auto-generated method stub
//            return null;
//        }
// 
//        @Override
//        public long getItemId(int arg0) {
//            // TODO Auto-generated method stub
//            return 0;
//        }
// 
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//             
//            ViewHolder holder = null;
//            if (convertView == null) {
//                 
//                holder=new ViewHolder();  
//                 
//                convertView = mInflater.inflate(R.layout.activity_device_query_info, null);
//                holder.Detail_D_Id = (TextView)convertView.findViewById(R.id.Detail_D_Id);
//                holder.Detail_D_Name = (TextView)convertView.findViewById(R.id.Detail_D_Name);
//                holder.Detail_BuyTime = (TextView)convertView.findViewById(R.id.Detail_BuyTime);
//                holder.Detail_Price = (TextView)convertView.findViewById(R.id.Detail_Price);
//                holder.Detail_L_Id = (TextView)convertView.findViewById(R.id.Detail_L_Id);
//                holder.Detail_UserId = (TextView)convertView.findViewById(R.id.Detail_UserId);
//                holder.Detail_Remark = (TextView)convertView.findViewById(R.id.Detail_Remark);
//                holder.Device_Reservation = (Button)convertView.findViewById(R.id.Device_Reservation);
//                convertView.setTag(holder);
//                 
//            }else {
//                 
//                holder = (ViewHolder)convertView.getTag();
//            }
//             
//             
//            holder.Detail_D_Id.setText((String)mData.get(position).get("Detail_D_Id"));
//            holder.Detail_D_Name.setText((String)mData.get(position).get("Detail_D_Name"));
//            holder.Detail_BuyTime.setText((String)mData.get(position).get("Detail_BuyTime"));
//            holder.Detail_Price.setText((String)mData.get(position).get("Detail_Price"));
//            holder.Detail_L_Id.setText((String)mData.get(position).get("Detail_L_Id"));
//            holder.Detail_UserId.setText((String)mData.get(position).get("Detail_UserId"));
//            holder.Detail_Remark.setText((String)mData.get(position).get("Detail_Remark"));
//             
//            holder.Device_Reservation.setOnClickListener(new View.OnClickListener() {
//                 
//                @Override
//                public void onClick(View v) {
//                    showInfo();
//                }
//            });
//             
//             
//            return convertView;
//        }
//         
//    }
//    
  
	

}
