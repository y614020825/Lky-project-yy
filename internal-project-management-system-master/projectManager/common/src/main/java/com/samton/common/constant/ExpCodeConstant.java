package com.samton.common.constant;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangpeng
 *         系统异常时返回给前端的错误码和错误信息
 *         如果需要添加，可在对应项目中继承该类
 */
public class ExpCodeConstant {

    protected static Map<String, String> msgMap = new HashMap<>();
    protected static Map<String, String> enMsgMap = new HashMap<>();

    //系统级别错误“0”开头
    public static final String DEFAULT_CODE = "000";

    public static final String URL_ACCESS_DENIED = "001";

    public static final String ILLEGAL_PARAMETER = "002";

    public static final String CHARACTER_TOO_LONG = "003";

    public static final String NUMBER_TOO_LARGE = "004";

    public static final String INVALID_FORMAT_ERROR = "005";

    public static final String REQUEST_RESUBMIT_ERROR = "006";

    //业务级别按模块划分，第一位数值位1\2\3\4\5\6\7\8\9依次递增

    static {
        msgMap.put(DEFAULT_CODE, "系统错误");
        msgMap.put(URL_ACCESS_DENIED, "URL拒绝访问");
        msgMap.put(ILLEGAL_PARAMETER, "非法参数");
        msgMap.put(CHARACTER_TOO_LONG, "输入内容过长");
        msgMap.put(NUMBER_TOO_LARGE, "数值不能大于999999999");
        msgMap.put(INVALID_FORMAT_ERROR, "请输入正确的类型");
        msgMap.put(REQUEST_RESUBMIT_ERROR, "正在执行中，请稍后");
    }

    public static String explainCodeToMsg(String errorCode) {
        String errorMsg = msgMap.get(errorCode);
        if (StringUtils.isEmpty(errorMsg)) {
            errorMsg = msgMap.get(DEFAULT_CODE);
        }

        return errorMsg;
    }

    public static String explainCodeToMsg(String errorCode, String... formate) {
        String errorMsg = msgMap.get(errorCode);
        if (StringUtils.isEmpty(errorMsg)) {
            errorMsg = msgMap.get(DEFAULT_CODE);
        }
        return String.format(errorMsg, formate);
    }


}