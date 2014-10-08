package com.miot.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.miot.webservice.QueryInfo;

public class DBUtils {
	private  ArrayList<String> crrayList = new ArrayList<String>();
	/**
	 * 获取所有货物的信息
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

		HashMap<String, String> tempHash = new HashMap<String, String>();
		tempHash.put("设备ID", "设备ID");
		tempHash.put("设备名称", "设备名称");
		tempHash.put("存放位置", "存放位置");
		tempHash.put("简介", "简介");
		list.add(tempHash);
		
		for (int j = 0; j < crrayList.size(); j += 3) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("设备ID", crrayList.get(j));
			hashMap.put("设备名称", crrayList.get(j + 1));
			hashMap.put("存放位置", crrayList.get(j + 2));
			hashMap.put("简介", crrayList.get(j + 3));
			list.add(hashMap);
		}

		return list;
	}
//
//	/**
//	 * 增加一条货物信息
//	 * 
//	 * @return
//	 */
//	public void insertCargoInfo(String Cname, String Cnum) {
//
//		arrayList.clear();
//		brrayList.clear();
//		
//		arrayList.add("Cname");
//		arrayList.add("Cnum");
//		brrayList.add(Cname);
//		brrayList.add(Cnum);
//		
////		Soap.GetWebServre("insertCargoInfo", arrayList, brrayList);
//	}
	
	/**
	 * 删除一条货物信息
	 * 
	 * @return
	 */
//	public void deleteCargoInfo(String Cno) {
//
//		arrayList.clear();
//		brrayList.clear();
//		
//		arrayList.add("Cno");
//		brrayList.add(Cno);
//		
////		Soap.GetWebServre("deleteCargoInfo", arrayList, brrayList);
//	}
}
