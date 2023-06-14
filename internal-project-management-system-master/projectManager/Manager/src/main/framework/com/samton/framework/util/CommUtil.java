package com.samton.framework.util;

import java.net.HttpURLConnection;
import java.net.URL;
public class CommUtil {
	
	public static boolean stringEmptyOrNull(String str){
		if(str == null || "".equals(str)){
			return true;
		} else {
			return false;
		}
	}
	
     public static boolean isUrlValid(String strLink) {
    	 URL url;
    	 try {
	     url = new URL(strLink);
	     HttpURLConnection connt = (HttpURLConnection)url.openConnection();
	     //connt.setRequestMethod("HEAD");
	     String strMessage = connt.getResponseMessage();
	     if(strMessage==null){
	    	 return true;
	     }
	     if (strMessage.compareTo("Not Found") == 0) {
	        return false;
	     }
	     connt.disconnect();
	     } catch (Exception e) {
  	       return false;
	     }
	     return true;
	 }
     
}
