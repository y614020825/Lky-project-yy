package com.samton.framework.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import com.samton.common.http.NetworkUtil;
import com.samton.framework.context.WebContext;

/**
 * @author yangpeng
 * 通过AOP的方式记录access日志
 * 日志记录方式:
 * 未登录用户(ip,访问url,访问参数,访问时间,访问结果(成功/失败))
 * 登录用户(ip,访问url,访问参数,访问时间,访问结果(成功/失败),企业id,用户id,用户名)
 */
@Component
public class AccessLog {
	
	private String getDefaultValue(Object obj){
		if(obj==null) return "";
		return obj.toString();
	}
	
	public Object recordLog(ProceedingJoinPoint pjp) throws Throwable {
		Object result=null;
		 //标记是否成功 1:成功  0:失败
		 String flag="1";
		 try {
			 result = pjp.proceed();
		} catch (Throwable e) {
			flag="0";
			throw e;
		} finally {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			HttpServletRequest req = WebContext.getInstance().getRequest();  
			String servletPath=req.getServletPath();
			int index=servletPath.indexOf(".");
			if(index!=-1){			
				servletPath=servletPath.substring(0,index);
			}
			List<String> params=new ArrayList<String>();
			params.add(getDefaultValue(NetworkUtil.getIpAddress(req)));
			params.add(getDefaultValue(servletPath));
			String paramValue="";
			if(req.getParameterMap()!=null){
				paramValue=JSONObject.fromObject(req.getParameterMap()).toString();
			}
			params.add(paramValue);
			params.add(sdf.format(new Date()));
			params.add(flag);
			//TODO	如果用户登录，需记录用户信息
			LogUtil.infoAccessLog(params);
		}
		 
		return result; 
	}
}
