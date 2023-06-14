package com.samton.framework.base;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samton.common.constant.ExpCodeConstant;
import com.samton.common.exception.BaseException;
import com.samton.common.http.NetworkUtil;
import com.samton.framework.exception.FilterException;
import com.samton.framework.exception.UndefinedException;
import com.samton.framework.log.LogUtil;
import com.samton.base.constant.PmExpCodeConstant;

@Controller
public class BaseController {
	
	//拦截所有抛出的异常
	@ExceptionHandler
	@ResponseBody
	public JSONObject exp(HttpServletRequest request, Exception exp) {
		String userInfo = "IP：" + NetworkUtil.getIpAddress(request) + "\nSession：" + request.getSession().getId() + "\n";
		//TODO	如果用户登录了，需记录用户登录信息
		String showCode = "编号：" + System.currentTimeMillis();
		BaseException baseException = null;
		if(exp instanceof BaseException){
			//将错误信息记入日志
			baseException=(BaseException)exp;
		}else if(exp instanceof UndeclaredThrowableException || exp instanceof FilterException){
			return new FilterException(PmExpCodeConstant.PM_ACCESS_DENIED, PmExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.PM_ACCESS_DENIED)).processException(request);
		}else if(exp instanceof BindException || exp instanceof TypeMismatchException || exp instanceof NumberFormatException){
			LogUtil.errorLog(userInfo + showCode, exp);
			return new FilterException(PmExpCodeConstant.PARAM_IS_UNFORMAT, PmExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.PARAM_IS_UNFORMAT)).processException(request);
		}
		else {
			//将错误信息记入日志
			LogUtil.errorLog(userInfo + showCode, exp);
			String errorCode = ExpCodeConstant.DEFAULT_CODE;
			Exception causeExp = (Exception)exp.getCause();
			if(causeExp == null){
				causeExp=exp;
			}
			if(causeExp instanceof PSQLException){
				PSQLException pgExp = (PSQLException)causeExp;
				if(pgExp.getSQLState().equals("22001")){
					errorCode = ExpCodeConstant.CHARACTER_TOO_LONG;
					showCode = null;
				}else if(pgExp.getSQLState().equals("22003")){
					errorCode = ExpCodeConstant.NUMBER_TOO_LARGE;
					showCode = null;
				}
			}else if (causeExp instanceof InvalidFormatException) {
				errorCode = ExpCodeConstant.INVALID_FORMAT_ERROR;
				showCode = null;
			}
			baseException = new UndefinedException(exp, errorCode,showCode);
		}
		
		return baseException.processException(request);
	}
	
	protected HttpServletRequest request;  
    protected HttpServletResponse response;  
      
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
    }  
	
	protected HttpServletRequest getRequest(){
		return this.request;
	}
	
	protected HttpServletResponse getResponse(){
		return this.response;
	}
	
	public Map<String,Object> returnErrorMsg(String errorCode,String errorMsg){
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> data = new HashMap<>();
		result.put("rs", -1);
		data.put("errorCode", errorCode);
		result.put("data", data);
		return result;
	}
	
}
