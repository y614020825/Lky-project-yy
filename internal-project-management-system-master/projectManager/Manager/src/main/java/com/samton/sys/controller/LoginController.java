package com.samton.sys.controller;

import com.samton.base.constant.BaseConstant;
import com.samton.base.constant.PmExpCodeConstant;
import com.samton.common.cache.RedisPool;
import com.samton.common.constant.ExpCodeConstant;
import com.samton.common.keygen.KeyGenerator;
import com.samton.framework.base.BaseController;
import com.samton.framework.bean.CurrentCachUser;
import com.samton.framework.util.CurrentUtil;
import com.samton.sys.bean.TIbenSysMenu;
import com.samton.sys.bean.TIbenSysUser;
import com.samton.sys.service.IMenuService;
import com.samton.sys.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/manage")
public class LoginController extends BaseController{
	
	@Resource
	private IUserService userService;
	@Resource
	private IMenuService menuService;
	
	/**
	 * 登录
	 * @param loginAccount
	 * @param pwd
	 * @return
	 */
	@RequestMapping("/goLogin")
	@ResponseBody
	public  Map<String, Object> goLogin(String loginAccount, String pwd) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isBlank(loginAccount)){
			return BaseConstant.returnErrorMsg(PmExpCodeConstant.LOGIN_NAME_NULL, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.LOGIN_NAME_NULL));
		}
		if(StringUtils.isBlank(pwd)){
			return BaseConstant.returnErrorMsg(PmExpCodeConstant.PASSWD_NULL, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.PASSWD_NULL));
		}
		
		//判断用户是否存在
		TIbenSysUser info = userService.getByLoginAccount(loginAccount);
		if(info == null){
			return BaseConstant.returnErrorMsg(PmExpCodeConstant.NO_LOGIN_NAME, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.NO_LOGIN_NAME));
		}
		
		//校验登录用户
		TIbenSysUser user = userService.getByLoginAccountAndPasswd(loginAccount, pwd);
		if(user == null){
			return BaseConstant.returnErrorMsg(PmExpCodeConstant.LOGIN_NAME_OR_PWD_ERROR, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.LOGIN_NAME_OR_PWD_ERROR));
		}
		
		//查询当前用户菜单信息
		List<TIbenSysMenu> allMenuList = menuService.selectAllMenuByRoleId(user.getRoleId());
		List<TIbenSysMenu> allMenuList2 = new ArrayList<TIbenSysMenu>(allMenuList);
		
		
		//查询当前用户菜单信息
		List<TIbenSysMenu> menuList = menuService.makeTree(allMenuList2, false, 2);
		
		CurrentCachUser userInfo = new CurrentCachUser();
		userInfo.setUserId(user.getUserId());
		userInfo.setFullName(user.getFullName());
		userInfo.setLoginAccount(loginAccount);
		userInfo.setRoleName(user.getRoleName());
		userInfo.setRoleId(user.getRoleId());
		userInfo.setDeptId(user.getDeptId());
		userInfo.setDataAuth(user.getDataAuth());

		
//		//查询该用户的数据权限
//		TIbenRole role = pmService.getRoleByRoleId(userInfo.getRoleId());
//		userInfo.setDataAccessType(role.getDataAccessType());
//		userInfo.setEncryptPhone(user.getEncryptPhone());
		
		CurrentUtil.setCurrentCachUser(userInfo);
		CurrentUtil.setCurrenCachMenu(menuList);
		
		//将用户的权限路径放入cach中
		if(allMenuList != null && allMenuList.size() > 0){
			Set<String> requestPaths = new HashSet<>();
			for(TIbenSysMenu sysMenu : allMenuList){
				if(StringUtils.isNotEmpty(sysMenu.getRequestPath())){
					requestPaths.add(sysMenu.getRequestPath());
				}
			}
			RedisPool.getRedisPool().setObject(KeyGenerator.generateMenusRequestPathKey(CurrentUtil.getCurrentCachUser().getUserId()), requestPaths);
			result.put("requestPaths", requestPaths);
		}
		
		result.put("rs", 1);
		result.put("menuList", menuList);
		return result;
	}
	
	/**
	 * 退出登录
	 * @return
	 */
	@RequestMapping("/loginOut")
	@ResponseBody
	public Map<String,Object> loginOut(){
		Map<String, Object> result = new HashMap<String, Object>();
		CurrentUtil.removeCurrentUser();
		return result;
	}
		
}