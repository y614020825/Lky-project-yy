package com.samton.sys.service;

import java.util.List;
import java.util.Map;

import com.samton.sys.bean.TIbenSysRole;

public interface IRoleService {
	
	Long add(TIbenSysRole role);
	
	Map<String, Object> deleteRoleById(Long roleId);

    TIbenSysRole selectByRoleId(Long roleId);

    int updateByPrimaryKeySelective(TIbenSysRole role);
    
    List<TIbenSysRole> queryList(TIbenSysRole search);
	
}