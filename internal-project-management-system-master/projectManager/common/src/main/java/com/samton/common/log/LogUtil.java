package com.samton.common.log;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class LogUtil {

    private static final String VALUES_SEP_LEFT = "[";
    private static final String VALUES_SEP_RIGHT = "]";
    private static boolean isDebug = false;
    private static Logger baseDaoLog = null;
    private static Logger systemLog = null;
    private static Logger accessLog = null;

    static {
        if (systemLog == null) {
            systemLog = Logger.getLogger("System");
        }
        if (accessLog == null) {
            accessLog = Logger.getLogger("Access");
        }
    }

    public static boolean isDebug() {
        return isDebug;
    }

    private static void errorLog(Logger logger, String msg, Throwable exp) {
        if (exp == null) {
            logger.error("【" + LogFlag.getLogFlag() + "】" + msg);
        } else {
            logger.error("=================ERROR====================");
            if (StringUtils.isEmpty(msg)) msg = exp.getMessage();
            logger.error(msg, exp);
            logger.error("==========================================");
        }
    }

    private static void debugLog(Logger logger, String msg) {
        logger.debug("【" + LogFlag.getLogFlag() + "】" + msg);
        if (isDebug) {
            System.out.println(msg);
        }
    }

    private static void infoLog(Logger logger, String msg) {
        logger.info("【" + LogFlag.getLogFlag() + "】" + msg);
        if (isDebug) {
            System.out.println(msg);
        }
    }

    private static void warnLog(Logger logger, String msg) {
    	if(msg != null && msg.contains("crf model")){
    		return;
    	}
        logger.warn("【" + LogFlag.getLogFlag() + "】" + msg);
        if (isDebug) {
            System.out.println(msg);
        }
    }

    public static void debugBaseDaoLog(String msg) {
        debugLog(baseDaoLog, msg);
    }

    public static void infoBaseDaoLog(String msg) {
        infoLog(baseDaoLog, msg);
    }

    public static void infoAccessLog(List<String> params) {
        StringBuffer sb = new StringBuffer();
        for (String param : params) {
            sb.append(VALUES_SEP_LEFT + (param == null ? "" : param) + VALUES_SEP_RIGHT);
        }
        infoLog(accessLog, sb.toString());
    }

    public static void warnBaseDaoLog(String msg) {
        warnLog(baseDaoLog, msg);
    }

    public static void errorBaseDaoLog(String msg) {
        errorBaseDaoLog(msg, null);
    }

    public static void errorBaseDaoLog(String msg, Exception t) {
        errorLog(baseDaoLog, msg, t);
    }

    public static void debugSystemLog(String msg) {
        debugLog(systemLog, msg);
    }

    public static void infoSystemLog(String msg) {
        infoLog(systemLog, msg);
    }

    public static void warnSystemLog(String msg) {
        warnLog(systemLog, msg);
    }

    public static void errorSystemLog(String msg) {
        errorSystemLog(msg, null);
    }

    public static void errorSystemLog(String msg, Throwable t) {
        errorLog(systemLog, msg, t);
    }

    public static void logStart(String msg) {
        infoSystemLog("START: " + msg);
    }

    public static void logStop(String msg) {
        infoSystemLog("STOP: " + msg);
    }
}
