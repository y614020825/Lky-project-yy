package com.samton.sys.service;

import java.util.Map;

import com.samton.base.bean.JqxParamBean;
import com.samton.framework.mybatis.pagination.Pagination;
import com.samton.sys.bean.TIbenSysUser;

public interface IUserService {
	
	TIbenSysUser getByLoginAccount(String loginAccount);
	
	TIbenSysUser getByLoginAccountAndPasswd(String loginAccount, String passwd);
	
	/**
	 * 分页查询
	 * @param paramBean
	 * @return
	 */
	public Pagination<TIbenSysUser> queryPageList(JqxParamBean paramBean);
	
	int insertSelective(TIbenSysUser user);

    TIbenSysUser selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(TIbenSysUser user);

    Map<String,Object> save(TIbenSysUser user);
    
    int deleteByUserId(Long userId);
    
}