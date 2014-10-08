package com.miot.webservice;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.miot.utils.StreamTool;
public class CheckUserInfo {
	/**
	 * 验证用户ＩＤ和密码
	 * @param UserId 用户ID
	 * @param U_PassWord 用户密码
	 * @return
	 * @throws Exception
	 */
	/*public static String CheckInfomation(String methodname,String userid,String u_password) throws Exception{
		String serviceUrl = "http://222.200.105.249:11125/Service1.asmx?op=CheckUserInfo";
		//打开一个浏览器
		HttpClient client = new DefaultHttpClient();
		//输入地址
		HttpPost httpPost = new HttpPost(serviceUrl);
		//指定提交的数据试题
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("UserId", userid));
		parameters.add(new BasicNameValuePair("U_Password", u_password));
		httpPost.setEntity(new UrlEncodedFormEntity(parameters ,"UTF-8"));
		//回车确认
		 HttpResponse response = client.execute(httpPost);
		 int code = response.getStatusLine().getStatusCode();
		 if (code == 200) {
			InputStream is =response.getEntity().getContent();
			String result = parseSOAP(is, methodname);
			return result;
		}
		 else {
			 return "No";
		}
		
		
	}*/
	public static String CheckInfo(String methodname,String methodname1,String methodname2,String userid,String u_password) throws Exception{	 
		String soap = readSoap();
		soap = soap.replaceAll("CheckUserInfo", methodname);
		soap = soap.replaceAll("UserId", methodname1);
		soap = soap.replaceAll("U_PassWord", methodname2);
		soap = soap.replaceAll("\\$userid", userid);
		soap = soap.replaceAll("\\$u_password", u_password);
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
			return parseSOAP(conn.getInputStream(),methodname);
		}
		else
		{
			return "NO";	
		}
	}
	
	
	/*
	 <?xml version="1.0" encoding="utf-8"?>
	<soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
	  <soap12:Body>
	    <getMobileCodeInfoResponse xmlns="http://WebXml.com.cn/">
	      <getMobileCodeInfoResult>string</getMobileCodeInfoResult>
	    </getMobileCodeInfoResponse>
	  </soap12:Body>
	</soap12:Envelope>
	 */
	private static String parseSOAP(InputStream xml,String methodname)throws Exception{
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8");
		int event = pullParser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_TAG:
				String ifString = methodname + "Result";
				if(ifString.equals(pullParser.getName())){
					return pullParser.nextText();
				}
				break;
			}
			event = pullParser.next();
		}
		return "NO";
	}

	private static String readSoap() throws Exception{
		InputStream inStream = CheckUserInfo.class.getClassLoader().getResourceAsStream("soap12.xml");
		byte[] data = StreamTool.read(inStream);
		return new String(data);
	}
}
