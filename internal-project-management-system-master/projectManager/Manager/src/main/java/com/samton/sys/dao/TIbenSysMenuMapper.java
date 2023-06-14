package com.samton.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samton.sys.bean.TIbenSysMenu;

public interface TIbenSysMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(TIbenSysMenu record);

    int insertSelective(TIbenSysMenu record);

    TIbenSysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(TIbenSysMenu record);

    int updateByPrimaryKey(TIbenSysMenu record);
    
	/**
	 * 查询菜单列表
	 * @param roleId
	 * @return
	 */
	List<TIbenSysMenu> selectAllMenuByRoleId(@Param("roleId")Long roleId);
	
    /**
     * 查询菜单列表
     * @param roleId
     * @return
     */
	List<TIbenSysMenu> selectAllMenu(@Param("roleId")Long roleId);
}