package com.samton.sys.dao;

import java.util.List;

import com.samton.sys.bean.TIbenSysRole;

public interface TIbenSysRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(TIbenSysRole record);

    int insertSelective(TIbenSysRole record);

    TIbenSysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(TIbenSysRole record);

    int updateByPrimaryKey(TIbenSysRole record);
    
    List<TIbenSysRole> queryList(TIbenSysRole search);
}