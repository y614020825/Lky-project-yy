package com.samton.common.log;

import org.apache.commons.lang.StringUtils;

public class LogFlag {
    private static final ThreadLocal<String> logFlags = new ThreadLocal<String>();
    
    public static void setLogFlag(String logFlagStr){
        logFlags.set(logFlagStr);
    }
    
    public static String getLogFlag(){
        String flag = logFlags.get();
        if(StringUtils.isEmpty(flag)){
            flag = "1";
        }
        return flag;
    }
}
