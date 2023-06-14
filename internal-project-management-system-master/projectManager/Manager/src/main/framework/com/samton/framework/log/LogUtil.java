package com.samton.framework.log;

import java.util.List;

import org.apache.log4j.Logger;

import com.samton.common.log.LogFlag;

public class LogUtil {
	
	private static boolean isDebug = false;
	
	private static Logger systemLog = null;
	private static Logger accessLog = null;
	private static Logger errorLog = null;
	    
	
	static{
           if (systemLog == null) {
        	   systemLog = Logger.getLogger("System");
           }
           if(accessLog == null){
        	   accessLog = Logger.getLogger("Access");
           }
           if(errorLog == null){
        	   errorLog = Logger.getLogger("Error");
           }
	}
	
	
	private static void debugLog(Logger logger,String msg){
		logger.debug("【" + LogFlag.getLogFlag() + "】" + msg);
		if(isDebug){
			System.out.println(msg);
		}
	}
	
	private static void infoLog(Logger logger,String msg){
		logger.info("【" + LogFlag.getLogFlag() + "】" + msg);
		if(isDebug){
			System.out.println(msg);
		}
	}
	
	private static void warnLog(Logger logger,String msg){
		logger.warn("【" + LogFlag.getLogFlag() + "】" + msg);
		if(isDebug){
			System.out.println(msg);
		}
	}
	
	private static void errorLog(Logger logger,String msg){
		logger.error("【" + LogFlag.getLogFlag() + "】" + msg);
		if(isDebug){
			System.out.println(msg);
		}
	}
	
	private static void errorLog(Logger logger,String msg,Exception e){
		logger.error("【" + LogFlag.getLogFlag() + "】" + msg,e);
		if(isDebug){
			System.out.println(msg);
		}
	}


    private static final String VALUES_SEP_LEFT = "[";
    private static final String VALUES_SEP_RIGHT = "]";
	
	public static void infoAccessLog(List<String> params){
		StringBuffer sb = new StringBuffer();
        for (String param : params) {
            sb.append(VALUES_SEP_LEFT + (param == null ? "" : param) + VALUES_SEP_RIGHT);
        }
		infoLog(accessLog, sb.toString());
	}
	
	public static void debugSystemLog(String msg){
		debugLog(systemLog, msg);
	}
	
	public static void infoSystemLog(String msg){
		infoLog(systemLog, msg);
	}
	
	public static void warnSystemLog(String msg){
		warnLog(systemLog, msg);
	}
	
	public static void errorLog(String msg){
		errorLog(errorLog, msg);
	}
	
	public static void errorLog(String msg,Exception e){
		errorLog(errorLog, msg,e);
	}
}