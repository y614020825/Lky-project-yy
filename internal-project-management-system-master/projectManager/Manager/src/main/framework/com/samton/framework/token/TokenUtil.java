package com.samton.framework.token;



/**
 * @author yangpeng
 * token管理工具类
 */
public class TokenUtil {
	
	private static final ThreadLocal<String> tokenHolder = new ThreadLocal<String>();
	
	public static void setTokenHolder(String token){
		tokenHolder.set(token);
	}
	
	public static String getCurrentToken(){
		return tokenHolder.get();
	}
	
	public static void removeCurrenToken(){
		tokenHolder.remove();
	}
	
}
