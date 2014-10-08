package com.miot.management;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class Device_Menu_Activity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("资产管理系统-设备菜单");
        setContentView(R.layout.activity_device_menu);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // 继承BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	// 上下文
        private Context mContext;
        // 构造方法
        public ImageAdapter(Context c) {
            mContext = c;
        }
        // 组件个数
        public int getCount() {
            return mThumbIds.length;
        }
        // 当前组件
        public Object getItem(int position) {
            return null;
        }
        // 当前组件id
        public long getItemId(int position) {
            return 0;
        }
        // 获得当前视图
        public View getView(int position, View convertView, ViewGroup parent) {
        	// 声明图片视图
            ImageView imageView;
            if (convertView == null) {
            	// 实例化图片视图
                imageView = new ImageView(mContext);
                // 设置图片视图属性
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            // 设置图片视图图片资源
            imageView.setImageResource(mThumbIds[position]);
            // 为当前视图添加监听器
            switch (position) {
			case 0:
				// 添加点餐监听器
				imageView.setOnClickListener(device_inquiryLinstener);
				break;
			case 1:
				// 并台监听器
				imageView.setOnClickListener(device_borrowLinstener);
				break;
			case 2:
				// 添加转台监听器
				imageView.setOnClickListener(infor_inquiryLinstener);
				break;
//			case 3:
//				// 添加更新监听器
//				imageView.setOnClickListener(updateLinstener);
//				break;
			case 3:
				// 添加注销监听器
				imageView.setOnClickListener(exitLinstener);
				break;
			default:
				break;
			}
            
            return imageView;
        }
        // 图片资源数组
        private Integer[] mThumbIds = {
                R.drawable.device_inquiry, R.drawable.device_borrow,
                R.drawable.info_inquiry,R.drawable.cancellation
        };
    }
    
//    // 更新监听器
//    OnClickListener updateLinstener = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			Intent intent = new Intent();
//			// 启动结算Activity
//			intent.setClass(Device_Menu_Activity.this, UpdateActivity.class);
//			startActivity(intent);
//		}
//	};
    
    // 查询监听器
    OnClickListener device_inquiryLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动查询Activity
			intent.setClass(Device_Menu_Activity.this, Device_Inquiry_Activity.class);
			startActivity(intent);
		}
	};
    
    // 借还监听器
    OnClickListener device_borrowLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动借还Activity
			intent.setClass(Device_Menu_Activity.this, Device_Borrow_Activity.class);
			startActivity(intent);
		}
	};
    
    // 信息查询监听器
    OnClickListener infor_inquiryLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// 启动订餐Activity
			intent.setClass(Device_Menu_Activity.this, Infor_Inquiry_Activity.class);
			startActivity(intent);
		}
	};
	// 注销监听器
    OnClickListener exitLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			logout();
		}
	};
	
	

	// 退出系统
	private void logout(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("真的要退出系统吗？")
		       .setCancelable(false)
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	  Intent intent = new Intent();
		        	  intent.setClass(Device_Menu_Activity.this, LoginActivity.class);
		        	  startActivity(intent);
		           }
		       })
		       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
}
