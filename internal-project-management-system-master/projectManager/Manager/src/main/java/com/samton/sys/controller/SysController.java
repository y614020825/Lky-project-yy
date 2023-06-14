package com.samton.sys.controller;

import com.samton.common.cache.RedisPool;
import com.samton.common.keygen.KeyGenerator;
import com.samton.framework.base.BaseController;
import com.samton.framework.bean.CurrentCachUser;
import com.samton.framework.util.CurrentUtil;
import com.samton.sys.bean.TIbenSysMenu;
import com.samton.sys.service.IMenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/sys")
public class SysController extends BaseController{

	@Resource
	private IMenuService menuService;
	
	/**
	 * 获取当前用户信息
	 * @return
	 */
	@RequestMapping("/getCurrentUserInfo")
	@ResponseBody
	public Map<String,Object> getCurrentUserInfo() {
		Map<String,Object> result = new HashMap<>();
		CurrentCachUser cachUser = CurrentUtil.getCurrentCachUser();
		Set<String> requestPaths = null;
		List<TIbenSysMenu> menuList = null;
		if(cachUser != null){
			result.put("rs", 1);
			menuList = CurrentUtil.getCurrentMenus();
			//缓存中未找到菜单信息直接查询数据库
			if(menuList == null){
				menuList = menuService.selectAllMenuByRoleId(cachUser.getRoleId());
				menuList = menuService.makeTree(menuList, false, 2);
				CurrentUtil.setCurrenCachMenu(menuList);
			}
		}else{
			result.put("rs", -1);
		}
		Object obj = RedisPool.getRedisPool().getObject(KeyGenerator.generateMenusRequestPathKey(CurrentUtil.getCurrentCachUser().getUserId()));
		if(obj != null){
			requestPaths = (Set<String>)obj;
		}
		//将用户的权限路径放入cach中
		if(menuList != null && menuList.size() > 0 && (requestPaths == null || requestPaths.size() == 0)){
			requestPaths = new HashSet<>();
			//查询当前用户菜单信息
			List<TIbenSysMenu> allMenuList = menuService.selectAllMenuByRoleId(cachUser.getRoleId());
			//将用户的权限路径放入cach中
			if(allMenuList != null && allMenuList.size() > 0){
				for(TIbenSysMenu sysMenu : allMenuList){
					if(StringUtils.isNotEmpty(sysMenu.getRequestPath())){
						requestPaths.add(sysMenu.getRequestPath());
					}
				}
			}
			RedisPool.getRedisPool().setObject(KeyGenerator.generateMenusRequestPathKey(CurrentUtil.getCurrentCachUser().getUserId()), requestPaths);
		}
		result.put("menuList",CurrentUtil.getCurrentMenus());
		result.put("requestPaths", requestPaths);
		return result;
	}

}

