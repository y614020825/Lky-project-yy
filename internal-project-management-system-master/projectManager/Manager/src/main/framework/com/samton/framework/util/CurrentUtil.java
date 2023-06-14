package com.samton.framework.util;

import com.samton.common.MD5.GeneratorUUID;
import com.samton.common.cache.RedisPool;
import com.samton.common.keygen.KeyGenerator;
import com.samton.framework.bean.BaseBean;
import com.samton.framework.bean.CurrentCachUser;
import com.samton.framework.token.TokenUtil;
import com.samton.sys.bean.TIbenSysMenu;

import java.util.Date;
import java.util.List;

//当前用户操作工具类
public class CurrentUtil {
	
	//得到当前登录用户
	public static CurrentCachUser getCurrentCachUser(){
		CurrentCachUser cachUser = null;
		String token = TokenUtil.getCurrentToken();
		cachUser = (CurrentCachUser)RedisPool.getRedisPool().getObject(KeyGenerator.generateTokenKey(token));
		return cachUser;
	}
	
	//得到当前用户菜单信息
	public static List<TIbenSysMenu> getCurrentMenus(){
		String key = getCurrentCachUser().getUserId() + "" ;
		List<TIbenSysMenu> menus = null;
		Object obj = RedisPool.getRedisPool().getObject(key);
		if(obj != null){
			menus = (List<TIbenSysMenu>)obj;
		}
		return menus;
	}
	
	//注销当前用户
	public static void removeCurrentUser(){
		String token = TokenUtil.getCurrentToken();
		RedisPool.getRedisPool().delete(KeyGenerator.generateTokenKey(token));
	}
	
	//用户登录成功后将用户放入缓存中
	public static void setCurrentCachUser(CurrentCachUser user){
//		String token = GeneratorUUID.generatorUUID();
//		TokenUtil.setTokenHolder(token);
//		RedisPool.getRedisPool().set(user.getUserId()+"", KeyGenerator.generateTokenKey(token));
//		RedisPool.getRedisPool().setObject(KeyGenerator.generateTokenKey(token), user, 3600);
		setCurrentCachUser(user, 3600);
	}
	
	//用户登录成功后将用户放入缓存中
	public static void setCurrentCachUser(CurrentCachUser user, int expire){
		String token = GeneratorUUID.generatorUUID();
		TokenUtil.setTokenHolder(token);
		RedisPool.getRedisPool().set(user.getUserId()+"", KeyGenerator.generateTokenKey(token));
		RedisPool.getRedisPool().setObject(KeyGenerator.generateTokenKey(token), user, expire);
	}
	
	//更新缓存中当前用户信息
	public static void updateCurrentCachUser(CurrentCachUser user){
		RedisPool.getRedisPool().setObject(KeyGenerator.generateTokenKey(TokenUtil.getCurrentToken()), user, 3600);
	}
	
	//将用户菜单放入缓存中
	public static void setCurrenCachMenu(List<TIbenSysMenu> menus){
		String key = getCurrentCachUser().getUserId() +"";
		RedisPool.getRedisPool().setObject(key, menus, 3600);
	}
	
	//保持token有效期
	public static void keepCashUser(CurrentCachUser user){
		String token = TokenUtil.getCurrentToken();
		RedisPool.getRedisPool().setObject(KeyGenerator.generateTokenKey(token), user, 3600);
	}
	
	public static void setBaseBeanByInsert(Object obj){
		setBaseBeanByInsert(obj, null);
	}
	
	public static void setBaseBeanByModify(Object obj){
		setBaseBeanByModify(obj, null);
	}
	
	public static void setBaseBeanByInsert(Object obj, CurrentCachUser userCacheBean){
		if(obj instanceof BaseBean){
			if(userCacheBean == null){
				userCacheBean = getCurrentCachUser();
			}
			if(userCacheBean == null){
				userCacheBean = new CurrentCachUser();
				userCacheBean.setUserId(0l);

				userCacheBean.setFullName("samton");
			}
			((BaseBean) obj).setCreateUserId(userCacheBean.getUserId());
			((BaseBean) obj).setCreateUserName(userCacheBean.getFullName());
			((BaseBean) obj).setModifyUserId(userCacheBean.getUserId());
			((BaseBean) obj).setModifyUserName(userCacheBean.getFullName());

			Date nowDate = new Date();
			((BaseBean) obj).setCreateDate(nowDate);
			((BaseBean) obj).setModifyDate(nowDate);
		}
	}
	
	public static void setBaseBeanByModify(Object obj, CurrentCachUser userCacheBean){
		if(obj instanceof BaseBean){
			if(userCacheBean == null){
				userCacheBean = getCurrentCachUser();
			}
			if(userCacheBean==null){
				userCacheBean = new CurrentCachUser();
				userCacheBean.setUserId(0l);
				userCacheBean.setFullName("samton");
			}
			((BaseBean) obj).setModifyUserId(userCacheBean.getUserId());
			((BaseBean) obj).setModifyUserName(userCacheBean.getFullName());
			Date nowDate=new Date();
			((BaseBean) obj).setModifyDate(nowDate);
		}
	}
	
	
	
}
