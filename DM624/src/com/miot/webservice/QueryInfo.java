package com.miot.webservice;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.miot.utils.StreamTool;

public class QueryInfo {
	/**
	 * 验证用户ＩＤ和密码
	 * @param UserId 用户ID
	 * @param U_PassWord 用户密码
	 * @return
	 * @throws Exception
	 */

	public static ArrayList<String> Query_Info(String MethodName,String Name1,String Name) throws Exception{
		ArrayList<String> Values = new ArrayList<String>();
		String soap = readSoap();
		soap = soap.replaceAll("\\$Name", Name);
		soap = soap.replaceAll("DeviceQuery", MethodName);
		soap = soap.replaceAll("Name", Name1);
		byte[] entity = soap.getBytes();
		String path="http://222.200.105.249:11125/Service1.asmx";
		HttpURLConnection conn= (HttpURLConnection) new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		conn.getOutputStream().write(entity);
		if(conn.getResponseCode() == 200){
			 Values = parseSOAP(conn.getInputStream(),MethodName);
			 return Values;
		}
		return Values;
	}
	
		public static ArrayList<String> parseSOAP(InputStream in,String MethodName) throws IOException {
		StringBuffer out = new StringBuffer();
		String s1 = "";
		byte[] b = new byte[4096];
		ArrayList<String> Values = new ArrayList<String>();
		Values.clear();
		
		for (int n; (n = in.read(b)) != -1;) {
			s1 = new String(b, 0, n);
			out.append(s1);
		}
		
		System.out.println(out);
		String[] s13 = s1.split("><");
		String ifString = MethodName + "Result";
		String TS = "";
		String vs = "";
		String gs = "";
		
		Boolean getValueBoolean = false;
		for (int i = 0; i < s13.length; i++) {
			gs = s13[i];
			TS = gs.replace(" ", "");
			System.out.println(TS);
			int j, k, l;
			j = TS.indexOf(ifString);
			k = TS.lastIndexOf(ifString);
		
		if (j >= 0) {
			System.out.println(j);
			if (getValueBoolean == false) {
				getValueBoolean = true;
			} else {
	
			}
	
			if ((j >= 0) && (k > j)) {
				System.out.println("FFF" + TS.lastIndexOf("/" + ifString));
				//System.out.println(TS);
				l = ifString.length() + 1;
				vs = TS.substring(j + l, k - 2);
				//System.out.println("fff"+vs);
				Values.add(vs);
				System.out.println("退出" + vs);
				getValueBoolean = false;
				return Values;
			}
	
		}
		if (TS.lastIndexOf("/" + ifString) >= 0) {
			getValueBoolean = false;
			return Values;
		}
		if ((getValueBoolean) && (TS.lastIndexOf("/" + ifString) < 0) && (j < 0)) {
			k = TS.length();
			//System.out.println(TS);
			vs = TS.substring(7, k - 8);
			//System.out.println("f"+vs);
			Values.add(vs);
		}
	
	}
	
	return Values;
	}
	
//	 public List<HashMap<String,String>> readXML(InputStream s2) throws Exception{      
//		 List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//   
//	      XmlPullParser pullParser = Xml.newPullParser();     
//	    //获取Pull解析器 Xml.newPullParser()添加一个pullParser解析器带命名空间的支持     
//	     pullParser.setInput(s2, "UTF-8");
//	     HashMap<String, String> tempHash = new HashMap<String, String>();
//		tempHash.put("D_Info", "D_Info");
//		list.add(tempHash); 
//	  //设置Pull解析器进行解析的XML内容       
//	   int event = pullParser.getEventType();    //获取第一个事件       
//	   while(event!=XmlPullParser.END_DOCUMENT)  //如果还不是结束文档事件,迭代每一个元素          {     
//	switch (event)            
//	    {                                  
//	  case XmlPullParser.START_TAG: //开始元素事件              
//	         if("D_Id".equals(pullParser.getName())){
//	        	HashMap<String, String> hashMap = new HashMap<String, String>();
//	        	String D_Id = pullParser.nextText();  
//				hashMap.put("D_Info",D_Id );
//				list.add(hashMap);//pullParser.getName()得到当前指针所指向的节点的名称   
//				break;
//		   }
//	         if("D_Name".equals(pullParser.getName())){
//	        	 HashMap<String, String> hashMap = new HashMap<String, String>();
//		        	String D_Name = pullParser.nextText();  
//					hashMap.put("D_Info",D_Name );
//					list.add(hashMap);//pullParser.getName()得到当前指针所指向的节点的名称 
//					break;
//			   }
//	         if("L_L".equals(pullParser.getName())){
//	        	 HashMap<String, String> hashMap = new HashMap<String, String>();
//		        	String L_L = pullParser.nextText();  
//					hashMap.put("D_Info",L_L );
//					list.add(hashMap);//pullParser.getName()得到当前指针所指向的节点的名称 
//					break;
//			   }
//	         if("Remark".equals(pullParser.getName())){
//	        	 HashMap<String, String> hashMap = new HashMap<String, String>();
//		        	String Remark = pullParser.nextText();  
//					hashMap.put("D_Info",Remark );
//					list.add(hashMap);//pullParser.getName()得到当前指针所指向的节点的名称 
//					break;
//			   }
//	         break;
//
//	    }
//	   return list;
//	 }
//  
//		                    
	

	private static String readSoap() throws Exception{
		InputStream inStream = CheckUserInfo.class.getClassLoader().getResourceAsStream("querysoap12.xml");
		byte[] data = StreamTool.read(inStream);
		return new String(data);
	}
}
