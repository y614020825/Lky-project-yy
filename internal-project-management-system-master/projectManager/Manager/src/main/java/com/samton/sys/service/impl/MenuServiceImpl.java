package com.samton.sys.service.impl;

import com.samton.framework.util.CurrentUtil;
import com.samton.sys.bean.TIbenSysMenu;
import com.samton.sys.bean.TIbenSysRoleMenu;
import com.samton.sys.dao.TIbenSysMenuMapper;
import com.samton.sys.dao.TIbenSysRoleMenuMapper;
import com.samton.sys.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("menuService")
public class MenuServiceImpl implements IMenuService {
    
    @Resource
    private TIbenSysMenuMapper menuMapper;
    @Resource
    private TIbenSysRoleMenuMapper roleMenuMapper;

	@Override
	public List<TIbenSysMenu> selectAllMenuByRoleId(Long roleId) {
		return menuMapper.selectAllMenuByRoleId(roleId);
	}

	@Override
	public List<TIbenSysMenu> makeTree(List<TIbenSysMenu> list, boolean showBtn, Integer level) {
		List<TIbenSysMenu> parent = new ArrayList<TIbenSysMenu>();
		
		//菜单按钮
		List<TIbenSysMenu> btns = new ArrayList<TIbenSysMenu>();
		for (TIbenSysMenu m : list) {
			if (m.getParentId()==null || m.getParentId()==0) {
				m.setChildren(new ArrayList<TIbenSysMenu>(0));
				parent.add(m);
			}else if(!showBtn && m.getIsLast() == 1) {
				btns.add(m);
			}
		}
		if(level==null || level > 1) {
			list.removeAll(parent);
			list.removeAll(btns);
			
			makeRoleChildren(parent, list, level, 1);
		}
		
		return parent;
	}
	
	/**
	 * 
	 * @param parent
	 * @param children
	 * @param totalLevel
	 * @param curLevel
	 */
	private void makeRoleChildren(List<TIbenSysMenu> parent, List<TIbenSysMenu> children, Integer totalLevel, int curLevel) {
		if(totalLevel != null && totalLevel <= curLevel) {
			return ;
		}
		curLevel++;
		if (children.isEmpty() || parent.isEmpty()) {
			return ;
		}
		List<TIbenSysMenu> tmp = new ArrayList<TIbenSysMenu>();
		for (TIbenSysMenu c1 : parent) {
			for (TIbenSysMenu c2 : children) {
				c2.setChildren(new ArrayList<TIbenSysMenu>(0));
				if (c1.getMenuId().equals(c2.getParentId())) {
					c1.getChildren().add(c2);
					tmp.add(c2);
				}
			}
		}
		children.removeAll(tmp);
		makeRoleChildren(tmp, children, totalLevel, curLevel);
	}

	@Override
	public List<TIbenSysMenu> getMenuTree(Long roleId) {
		List<TIbenSysMenu> list = menuMapper.selectAllMenu(roleId);
		List<TIbenSysMenu> rootList = makeTree(list, true, null);
		return rootList;
	}

	@Override
	public void savePmMenuRoleData(Long[] menuIds, Long roleId) {
		//先删除原有的menuIds外的菜单角色关系
		roleMenuMapper.deleteByRoleIdBesidesMenuId(menuIds==null?null:Arrays.asList(menuIds), roleId);//Arrays.asList将数组转变成集合
		if(menuIds != null && menuIds.length > 0){
			//再找出原中间表没有的数据-------------start
			List<Long> newPmIds = new ArrayList(Arrays.asList(menuIds));
			List<TIbenSysRoleMenu> oldList = roleMenuMapper.getRoleMenuByRoleId(roleId);
			for(TIbenSysRoleMenu old : oldList){
				newPmIds.remove(old.getMenuId());
			}
			
			//再找出原中间表没有的数据-------------end
			//将新关系插入表中
			if(newPmIds.size() > 0){
				Long[] ids = new Long[newPmIds.size()];
				newPmIds.toArray(ids);
				TIbenSysRoleMenu roleMenu = new TIbenSysRoleMenu();
				roleMenu.setRoleId(roleId);
				CurrentUtil.setBaseBeanByInsert(roleMenu);
				roleMenuMapper.addForBat(roleMenu, ids);
			}
		}
	}
}