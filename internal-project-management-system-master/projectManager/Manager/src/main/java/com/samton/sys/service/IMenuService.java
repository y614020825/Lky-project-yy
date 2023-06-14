package com.samton.sys.service;

import java.util.List;

import com.samton.sys.bean.TIbenSysMenu;

public interface IMenuService {
    /**
     * 查询菜单列表
     * @param roleId
     * @return
     */
	List<TIbenSysMenu> selectAllMenuByRoleId(Long roleId);
	
	/**
	 * 生成树形菜单
	 * @param list
	 * @param showBtn
	 * @param level
	 * @return
	 */
	List<TIbenSysMenu> makeTree(List<TIbenSysMenu> list, boolean showBtn, Integer level);
	
	/**
	 * 获取树形菜单
	 * @param roleId
	 * @return
	 */
	List<TIbenSysMenu> getMenuTree(Long roleId);
	
	/**
	 * 修改权限
	 * @param menuIds
	 * @param roleId
	 */
	public void savePmMenuRoleData(Long[] menuIds, Long roleId);
    
}