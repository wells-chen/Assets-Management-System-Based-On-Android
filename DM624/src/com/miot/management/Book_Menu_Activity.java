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


public class Book_Menu_Activity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("�ʲ�����ϵͳ-ͼ��˵�");
        setContentView(R.layout.activity_book_menu);
        GridView gridview = (GridView) findViewById(R.id.BookMenu_gridview);
        gridview.setAdapter(new ImageAdapter(this));
    }
    
    // �̳�BaseAdapter
    public class ImageAdapter extends BaseAdapter {
    	// ������
        private Context mContext;
        // ���췽��
        public ImageAdapter(Context c) {
            mContext = c;
        }
        // �������
        public int getCount() {
            return mThumbIds.length;
        }
        // ��ǰ���
        public Object getItem(int position) {
            return null;
        }
        // ��ǰ���id
        public long getItemId(int position) {
            return 0;
        }
        // ��õ�ǰ��ͼ
        public View getView(int position, View convertView, ViewGroup parent) {
        	// ����ͼƬ��ͼ
            ImageView imageView;
            if (convertView == null) {
            	// ʵ����ͼƬ��ͼ
                imageView = new ImageView(mContext);
                // ����ͼƬ��ͼ����
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            // ����ͼƬ��ͼͼƬ��Դ
            imageView.setImageResource(mThumbIds[position]);
            // Ϊ��ǰ��ͼ��Ӽ�����
            switch (position) {
			case 0:
				// ��ӵ�ͼ�����
				imageView.setOnClickListener(book_inquiryLinstener);
				break;
			case 1:
				// ��̨������
				imageView.setOnClickListener(book_borrowLinstener);
				break;
			case 2:
				// ���ת̨������
				imageView.setOnClickListener(book_inquiryLinstener);
				break;
//			case 3:
//				// ��Ӹ��¼�����
//				imageView.setOnClickListener(updateLinstener);
//				break;
			case 3:
				// ���ע��������
				imageView.setOnClickListener(exitLinstener);
				break;
			default:
				break;
			}
            
            return imageView;
        }
        // ͼƬ��Դ����
        private Integer[] mThumbIds = {
                R.drawable.book_inquiry, R.drawable.book_borrow,
                R.drawable.info_inquiry,R.drawable.cancellation
        };
    }
    
//    // ���¼�����
//    OnClickListener updateLinstener = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			Intent intent = new Intent();
//			// ��������Activity
//			intent.setClass(Device_Menu_Activity.this, UpdateActivity.class);
//			startActivity(intent);
//		}
//	};
    
    // ��ѯ������
    OnClickListener book_inquiryLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// ������ѯActivity
			intent.setClass(Book_Menu_Activity.this, Book_Inquiry_Activity.class);
			startActivity(intent);
		}
	};
    
    // �軹������
    OnClickListener book_borrowLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// �����軹Activity
			intent.setClass(Book_Menu_Activity.this, Book_Inquiry_Activity.class);
			startActivity(intent);
		}
	};
    
    // ��Ϣ��ѯ������
    OnClickListener infor_inquiryLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// ��������Activity
			intent.setClass(Book_Menu_Activity.this, Book_Inquiry_Activity.class);
			startActivity(intent);
		}
	};
	// ע��������
    OnClickListener exitLinstener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			logout();
		}
	};
	
	

	// �˳�ϵͳ
	private void logout(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("���Ҫ�˳�ϵͳ��")
		       .setCancelable(false)
		       .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	  Intent intent = new Intent();
		        	  intent.setClass(Book_Menu_Activity.this, LoginActivity.class);
		        	  startActivity(intent);
		           }
		       })
		       .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
}
