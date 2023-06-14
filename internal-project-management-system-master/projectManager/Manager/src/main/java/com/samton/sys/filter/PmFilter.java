package com.samton.sys.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.samton.common.cache.RedisPool;
import com.samton.common.constant.ExpCodeConstant;
import com.samton.common.keygen.KeyGenerator;
import com.samton.common.log.LogFlag;
import com.samton.framework.bean.CurrentCachUser;
import com.samton.framework.exception.FilterException;
import com.samton.framework.token.TokenUtil;
import com.samton.framework.util.CurrentUtil;
import com.samton.util.DataSourceContextHolder;
import com.samton.base.constant.PmExpCodeConstant;

public class PmFilter implements Filter {

	// 存放不过滤的地址
	private Set<String> passPath;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	    //本次访问日志标识
	    LogFlag.setLogFlag(String.valueOf(System.currentTimeMillis()));
		passPath = new HashSet<String>();
		// 静态资源不过滤
		passPath.add("/resources");
		passPath.add("/manage/goLogin");
		passPath.add("/sys/test");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		String pagenumStr = request.getParameter("pagenum");
		if(StringUtils.isNotEmpty(pagenumStr)){
			try {
				Integer pagenum = Integer.parseInt(pagenumStr);
				if(pagenum < 0){
					response.getWriter().print("{\"rs\":-9}");
					return;
				}
			} catch (Exception e) {
				response.getWriter().print("{\"rs\":-9}");
				return;
			}
		}
		TokenUtil.removeCurrenToken();
		// 将每个请求的token放入threadLocal变量中
		String token = req.getParameter("_token_iben");
		String user = req.getParameter("user");
		String apikey = request.getParameter("apiKey");
		if(StringUtils.isNotEmpty(apikey)){
			token = RedisPool.getRedisPool().Hget(KeyGenerator.getApiKeyToken()).get(apikey);
			if(StringUtils.isNotEmpty(token)){
				TokenUtil.setTokenHolder(token);
			}
		}else if (StringUtils.isNotEmpty(token)) {
			token = token.replaceAll("\"", "");
			TokenUtil.setTokenHolder(token);
		}else if(StringUtils.isNotEmpty(user)){
			token = RedisPool.getRedisPool().get("userToToken" + user);
			if(StringUtils.isNotEmpty(token)){
				token = user.replaceAll("\"", "");
				TokenUtil.setTokenHolder(token);
			}
		}
		String servletPath = req.getServletPath();
		//语种，1：汉语，2：英语，3：西班牙语
		String lanType = req.getHeader("lanType");
		Integer lan = 1;
		if(lanType != null) {
			try {
				lan = Integer.parseInt(lanType);
				if(lan != null && lan != 1) {
					System.out.println(servletPath + "------请求语种："+lan);
				}
			} catch (Exception e) {
			}
		}
		DataSourceContextHolder.setLanType(lan);
		if (servletPath.endsWith(".jsp") || servletPath.endsWith(".js")
				|| servletPath.endsWith(".css")) {
			chain.doFilter(request, response);
			return;
		}
		int index = servletPath.indexOf(".");
		if (index != -1) {
			servletPath = servletPath.substring(0, index);
		}
		
		for (String path : passPath) {
			if (servletPath.contains(path)) {
				//服务器之间通信token校验
//			    if(path.equals("/studyQuestion/addStudyQuestion")){
//			        String tokenStr = request.getParameter("_checkToken");
//			        String _time = request.getParameter("_time");
//			        try {
//                        if(!TokenCheck.checkToken(tokenStr, Long.parseLong(_time))){
//                            LogUtil.errorSystemLog("token校验失败");
//                            return;
//                        }
//                    } catch (Exception e) {
//                        LogUtil.errorSystemLog("token校验失败",e);
//                        return;
//                    }
//			    }
				chain.doFilter(request, response);
				return;
			}
		}
		CurrentCachUser cachUser = CurrentUtil.getCurrentCachUser();

		try {
			if (cachUser == null) {
				throw new FilterException(PmExpCodeConstant.PM_SESSION_EXPIRE,ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.PM_SESSION_EXPIRE));
//				if(StringUtils.isEmpty(apikey)){
//					throw new FilterException(PmExpCodeConstant.PM_SESSION_EXPIRE,ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.PM_SESSION_EXPIRE));
//				}
//				Map<String, String> apiInfo = RedisPool.getRedisPool().Hget(KeyGenerator.getApiKeyInfo(apikey));
//				
//				if(apiInfo.keySet().size() == 0){
//					response.setContentType("application/json; charset=UTF-8");
//					String result="{\"rs\":\"-1\",\"errorMsg\":\"apiKey错误\"}";
//					response.getWriter().print(result);
//					return;
//				}
//				
//				Long enterpriseId = Long.parseLong(apiInfo.get("eId"));
//				
//				SpringUtil springUtil = new SpringUtil();
//				IPmService pmService=(IPmService) springUtil.getApplicationContext().getBean("pmService");
//				
//				//查询企业信息
//				TIbenSysEnterprise enterpriseInfo = pmService.getEnterpriseInfo(enterpriseId);
//				//判断企业服务是否有效
//				if(enterpriseInfo == null){
//					throw new FilterException(PmExpCodeConstant.ENTERPRISE_NULL,ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.ENTERPRISE_NULL));
//				}
//				if(enterpriseInfo.getServiceState() == null || enterpriseInfo.getServiceState() == 0){
//					throw new FilterException(PmExpCodeConstant.ENTERPRISE_SERVICE_NULL, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.ENTERPRISE_SERVICE_NULL));
//				}
//				Long beginDate = enterpriseInfo.getServiceBeginDate().getTime();
//				Long endDate = enterpriseInfo.getServiceEndDate().getTime();
//				Long nowDate = System.currentTimeMillis();
//				if((nowDate < beginDate || nowDate > endDate) && enterpriseInfo.getEnterpriseId() != 1){
//					throw new FilterException(PmExpCodeConstant.ENTERPRISE_SERVICE_TIME_OUT, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.ENTERPRISE_SERVICE_TIME_OUT));
//				}
//				
//				//获取企业管理员
//				TIbenPmUser pmUser = pmService.getAdministrator(enterpriseId);
//				if(pmUser == null){
//					throw new FilterException(null, "请先添加管理员");
//				}
//				
//				RedisPool.getRedisPool().setSet(KeyGenerator.allEnterpriseKey(), "0", -1);
//				RedisPool.getRedisPool().setSet(KeyGenerator.allEnterpriseKey(), "-1", -1);
//				
//				//查询当前用户菜单信息
//				List<TIbenSysMenu> allMenuList = pmService.selectAllMenuByRoleId(pmUser.getRoleId(), enterpriseId);
//				List<TIbenSysMenu> allMenuList2 = new ArrayList<TIbenSysMenu>(allMenuList);
//				
//				CurrentCachUser userInfo = new CurrentCachUser();
//				userInfo.setUserId(pmUser.getUserId());
//				userInfo.setUserName(pmUser.getUserName());
//				userInfo.setLoginName(pmUser.getLoginName());
//				userInfo.setEnterpriseId(pmUser.getEnterpriseId());
//				userInfo.setEnterpriseName(enterpriseInfo.getEnterpriseName());
//				userInfo.setEnterpriseNameStr(enterpriseInfo.getEnterpriseName() + " (960" + enterpriseInfo.getEnterpriseId() + ")");
//				userInfo.setRoleName(pmUser.getRoleName());
//				userInfo.setRoleId(pmUser.getRoleId());
//				userInfo.setEocUserId(pmUser.getEocUserId());
//				userInfo.setEnterpriseVersionNum(enterpriseInfo.getVersionNum());
//				if(pmUser.getHeadimage()==null || "".equals(pmUser.getHeadimage())){
//					userInfo.setHeadImage(ManagerConfig.getInstance().getDeaultHeadImage());
//				}else{
//					userInfo.setHeadImage(ManagerConfig.getInstance().getUserHeadImagePrefix()+pmUser.getHeadimage());
//				}
//				if(StringUtils.isEmpty(pmUser.getLeftLogo())) {
//					userInfo.setLeftImg(ManagerConfig.getInstance().getLeftLogo());
//				}else {
//					userInfo.setLeftImg(ManagerConfig.getInstance().getLeftImagePrefix() + pmUser.getLeftLogo());
//				}
//				if(StringUtils.isEmpty(pmUser.getQaLogo())) {
//					userInfo.setQaImg(ManagerConfig.getInstance().getQaLogo());
//				}else {
//					userInfo.setQaImg(ManagerConfig.getInstance().getQaImagePrefix() + pmUser.getQaLogo());
//				}
//				userInfo.setFlag(pmUser.getShowQaLogo());
//				String subscribeMsg = RedisPool.getRedisPool().get(KeyGenerator.getSubscribeMgKey(pmUser.getEnterpriseId()));
//				userInfo.setSubscribeMsg(subscribeMsg);
//				if(pmUser.getEnterpriseId() == 1){
//					userInfo.setIsOperate(1);
//				}else{
//					userInfo.setIsOperate(0);
//				}
//			/*	//机器人大会期间临时代码-------------------------------------------------start
//				if(userInfo.getLoginName().endsWith("@ibenrobot.com")){
//					userInfo.setEnterpriseId(127L);
//				}
//				//机器人大会期间临时代码-------------------------------------------------end
//		*/		
//
//				int level = 3;
//				IVersionService versionService=(IVersionService) springUtil.getApplicationContext().getBean("versionService");
//				TIbenSysVersion version = versionService.getVersion(userInfo.getEnterpriseId());
//				if(version != null) {
//					userInfo.setMenuVersion(version.getMenuVersion());
//					if(version.getMenuVersion() != null && version.getMenuVersion() == 2) {
//						level = 2;
//					}
//				}
//				
//				CurrentUtil.setCurrentCachUser(userInfo);
//				//当前用户菜单信息
//				List<TIbenSysMenuVo> menuList = pmService.toMenu(pmService.makeRoleTree(allMenuList2, false, level));
//				CurrentUtil.setCurrenCachMenu(menuList);
//				
//				//查询当前用户菜单信息
////				List<TIbenSysMenu> allMenuList = pmService.selectAllMenuByRoleId(null, pmUser.getRoleId(), pmUser.getEnterpriseId());
//				//将用户的权限路径放入cach中
//				if(allMenuList != null && allMenuList.size() > 0){
//					Set<String> requestPaths = new HashSet<>();
//					for(TIbenSysMenu sysMenu : allMenuList){
//						if(StringUtils.isNotEmpty(sysMenu.getRequestPath())){
//							requestPaths.add(sysMenu.getRequestPath());
//						}
//					}
//					RedisPool.getRedisPool().setObject(GenerateKey.generateMenusRequestPathKey(CurrentUtil.getCurrentCachUser().getUserId(), CurrentUtil.getCurrentCachUser().getEnterpriseId()), requestPaths, 3600);
//				}
//				
//				RedisPool.getRedisPool().Hset(KeyGenerator.getApiKeyToken(), apikey, TokenUtil.getCurrentToken());
//				chain.doFilter(request, response);
//				return;
			} else {
			    //token保持
			    CurrentUtil.keepCashUser(cachUser);
			    chain.doFilter(request, response);
			}
		} catch (FilterException e) {
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(e.processException(req));
			return;
		}
	}
	
	@Override
	public void destroy() {
	}
	
	public static Integer getLanType(HttpServletRequest req) {
		Integer lan = null;
		if(req != null) {
			//语种，1：汉语，2：英语，3：西班牙语
			String lanType = req.getHeader("lanType");
			if(lanType != null) {
				try {
					lan = Integer.parseInt(lanType);
				} catch (Exception e) {
				}
			}
		}else {
			lan = DataSourceContextHolder.getLanType();
		}
		return lan;
	}
}
