package com.miot.management;

import  com.miot.management.FragmentPage1;
import  com.miot.management.FragmentPage2;
import  com.miot.management.FragmentPage3;
import  com.miot.management.MyTabListener;
import  com.miot.management.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class Device_Borrow_Activity extends Activity {
	
	//定义ActionBar
		private ActionBar actionBar;
		
		//定义Handler句柄 
		private final Handler handler = new Handler();

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setTitle("资产管理系统--设备借还");
			setContentView(R.layout.activity_device_borrow);
			
			initView();
			
			initData();
		}

		/**
		 * 初始化组件
		 */
		private void initView(){
			//得到ActionBar
			actionBar = getActionBar();
		}
		
		/**
		 * 初始化数据
		 */
		private void initData(){
			//设置ActionBar标题不显示
			actionBar.setDisplayShowTitleEnabled(false);
			
			//设置ActionBar的背景
			actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_gradient_bg));
			
			//设置ActionBar左边默认的图标是否可用
			actionBar.setDisplayUseLogoEnabled(true);
			
			//设置导航模式为Tab选项标签导航模式
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			
			//设置ActinBar添加Tab选项标签
			actionBar.addTab(actionBar.newTab().setText("TAB1").setTabListener(new MyTabListener<FragmentPage1>(this,FragmentPage1.class)));
			actionBar.addTab(actionBar.newTab().setText("TAB2").setTabListener(new MyTabListener<FragmentPage2>(this,FragmentPage2.class)));
			actionBar.addTab(actionBar.newTab().setText("TAB3").setTabListener(new MyTabListener<FragmentPage3>(this,FragmentPage3.class)));
					
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.scanner, menu);	
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(final MenuItem item) {
			switch (item.getItemId()) {
			case R.id.menu_refresh:
				//选中刷新按钮后刷新一秒钟
				item.setActionView(R.layout.actionbar_progress);
				handler.postDelayed(new Runnable() {
					public void run() {
						item.setActionView(null);
					}
				}, 1000);
				return true;
			}
			return super.onOptionsItemSelected(item);
		}
		
	}

