package com.samton.framework.token;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import com.samton.common.cache.RedisPool;
import com.samton.framework.bean.CurrentCachUser;
import com.samton.framework.util.CurrentUtil;
import com.samton.util.DataSourceContextHolder;

/**
 * @author yangpeng
 * 对每个请求进行token处理
 */
@Component
public class TokenAop {
	
	public Object dealToken(ProceedingJoinPoint pjp) throws Throwable{
		Object result = null;
		result = pjp.proceed();
		String methodName = pjp.getSignature().getName();
		if(methodName.contains("checkCodePic")){
			return result;
		}
		
		if(methodName.contains("bindRedirect")){
			return result;
		}
		
		if(result == null){
			return null;
		}
		//对返回结果添加token值
		@SuppressWarnings("unchecked")
		Map<String,Object> resultTemp = (Map<String, Object>) result;
//		if(resultTemp.get("msg") != null) {
//			resultTemp.put("msg", TranslateUtils.getTranslateResult(DataSourceContextHolder.getLanType(), resultTemp.get("msg").toString()));
//			
////			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
////			if(request != null) {
////				String msg = resultTemp.get("msg").toString();
////				Integer lan = null;
////		        if(request != null) {
////		        	//语种，1：汉语，2：英语，3：西班牙语
////		        	String lanType = request.getHeader("lanType");
////		        	if(lanType != null) {
////		        		try {
////		        			lan = Integer.parseInt(lanType);
////		        		} catch (Exception e) {
////		        		}
////		        	}
////		        }
////		        resultTemp.put("msg", TranslateUtils.getTranslateResult(lan, msg));
////			}
//		}
//		if(resultTemp.get("speech") != null) {
//			resultTemp.put("speech", TranslateUtils.getTranslateResult(DataSourceContextHolder.getLanType(), resultTemp.get("speech").toString()));
//		}
//		if(resultTemp.get("errorMsg") != null) {
//			resultTemp.put("errorMsg", TranslateUtils.getTranslateResult(DataSourceContextHolder.getLanType(), resultTemp.get("errorMsg").toString()));
//		}
//		if(resultTemp.get("errorMsgs") != null) {
//			resultTemp.put("errorMsgs", TranslateUtils.getTranslateResult(DataSourceContextHolder.getLanType(), resultTemp.get("errorMsgs").toString()));
//		}
//		if(resultTemp.get("jmsg") != null) {
//			resultTemp.put("jmsg", TranslateUtils.getTranslateResult(DataSourceContextHolder.getLanType(), resultTemp.get("jmsg").toString()));
//		}
		
		resultTemp.put("_token_iben", TokenUtil.getCurrentToken());
		//添加当前用户信息
		CurrentCachUser userInfo = CurrentUtil.getCurrentCachUser();
		if(userInfo != null){
			RedisPool.getRedisPool().set("userToToken" + userInfo.getLoginAccount(),TokenUtil.getCurrentToken(),300);
			resultTemp.put("currentUserInfo", userInfo);
		}
		return result;
	}
}
