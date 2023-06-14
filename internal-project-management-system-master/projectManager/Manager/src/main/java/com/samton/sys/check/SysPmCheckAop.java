package com.samton.sys.check;

import com.samton.base.constant.PmExpCodeConstant;
import com.samton.common.cache.RedisPool;
import com.samton.common.constant.ExpCodeConstant;
import com.samton.common.keygen.KeyGenerator;
import com.samton.framework.bean.CurrentCachUser;
import com.samton.framework.exception.FilterException;
import com.samton.framework.util.CurrentUtil;
import com.samton.sys.bean.TIbenSysMenu;
import com.samton.sys.service.IMenuService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aspect
@Component
public class SysPmCheckAop{
	
	@Resource
	private IMenuService menuService;
	
	@Before("@annotation(sysPmCheck)")
	public void check(SysPmCheck sysPmCheck) throws FilterException {
		String menuCode=sysPmCheck.menuCode();
		CurrentCachUser curUser = CurrentUtil.getCurrentCachUser();
		//从缓存中得到所有选项信息
		Set<String> menuCodeSet = (Set<String>)RedisPool.getRedisPool().getObject(KeyGenerator.generateMenusRequestPathKey(curUser.getUserId()));
		if(menuCodeSet == null || menuCodeSet.size() == 0){
			
			//查询当前用户菜单信息
			List<TIbenSysMenu> allMenuList = menuService.selectAllMenuByRoleId(curUser.getRoleId());
			//将用户的权限路径放入cach中
			if(allMenuList != null && allMenuList.size() > 0){
				Set<String> requestPaths = new HashSet<>();
				for(TIbenSysMenu sysMenu : allMenuList){
					if(StringUtils.isNotEmpty(sysMenu.getRequestPath())){
						requestPaths.add(sysMenu.getRequestPath());
					}
				}
				menuCodeSet = requestPaths;
				RedisPool.getRedisPool().setObject(KeyGenerator.generateMenusRequestPathKey(curUser.getUserId()), requestPaths);
			}
		}
		if((menuCodeSet!=null)&&(!menuCodeSet.contains(menuCode))){
			throw new FilterException(PmExpCodeConstant.PM_ACCESS_DENIED, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.PM_ACCESS_DENIED));
		}
    }  
	
}