package com.samton.util;

public class DataSourceContextHolder {
	public static final String SOURCE_IBEN = "dataSource_iben";
	public static final String DATASOURCE_ZHONGJIAN = "dataSource_zhongjian";
	public static final String DATASOURCE_IBEN_ROBOT = "dataSource_iben_robot";
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
	//语种，1：汉语，2：英语，3：西班牙语
	private static final ThreadLocal<Integer> lanHolder = new ThreadLocal<Integer>();
	  
    public static void setDbType(String dbType) {  
    	contextHolder.set(dbType);  
    }  
 
    public static String getDbType() {  
    	//System.out.println("当前数据源：" + contextHolder.get());
        return ((String) contextHolder.get());  
    }  
 
    public static void clearDbType() {  
    	contextHolder.remove();  
    }

    public static void setLanType(Integer lanType) {  
    	lanHolder.set(lanType);  
    } 
	public static Integer getLanType() {
//    	System.out.println("当前语言：" + lanHolder.get());
		return lanHolder.get();
	}
}
