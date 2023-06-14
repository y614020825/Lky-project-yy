package com.samton.common.exception;

import com.samton.common.constant.ExpCodeConstant;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author yangpeng
 *         定义的一个基础异常类，该类可把对应的错误信息转成json直接返回给前端
 *         如需扩展此类，可直接在项目中继承
 */
public abstract class BaseException extends Exception {

    private static final long serialVersionUID = 1L;

    // 异常代码
    protected String errorCode;

    public BaseException() {
        this(ExpCodeConstant.DEFAULT_CODE);
    }

    public BaseException(String errorCode) {
        this(errorCode, ExpCodeConstant.explainCodeToMsg(errorCode));
    }

    public BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public JSONObject processException(HttpServletRequest request) {
        JSONObject jo = new JSONObject();
        jo.put("rs", -1);
        String msg = this.getMessage();
        if (StringUtils.isNotEmpty(errorCode)) {
            msg += "(" + errorCode + ")";
        }
        jo.put("errorCode", errorCode);
        
        jo.put("errorMsg", msg);
        return jo;
    }
}
