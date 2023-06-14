package com.samton.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.samton.sys.bean.TIbenSysRoleMenu;

public interface TIbenSysRoleMenuMapper {
    int deleteByPrimaryKey(Long roleMenuId);

    int insert(TIbenSysRoleMenu record);

    int insertSelective(TIbenSysRoleMenu record);

    TIbenSysRoleMenu selectByPrimaryKey(Long roleMenuId);

    int updateByPrimaryKeySelective(TIbenSysRoleMenu record);

    int updateByPrimaryKey(TIbenSysRoleMenu record);
	
	/**
	 * 逻辑删除原有的menuIds外的菜单角色关系
	 * @param menuIds
	 * @param roleId
	 * @return
	 */
	int deleteByRoleIdBesidesMenuId(@Param("menuIds") List<Long> menuIds, @Param("roleId") Long roleId);
	
    List<TIbenSysRoleMenu> getRoleMenuByRoleId(@Param("roleId") Long roleId);
    
    /**
     * 批量新增
     * @param record
     * @param menuIds
     * @return
     */
    int addForBat(@Param("record") TIbenSysRoleMenu record, @Param("menuIds") Long[] menuIds);
	
	/**
	 * 根据角色id逻辑删除
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(@Param("roleId") Long roleId);
}