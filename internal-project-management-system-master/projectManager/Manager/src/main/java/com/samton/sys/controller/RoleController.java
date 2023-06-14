package com.samton.sys.controller;

import com.samton.base.constant.BaseConstant;
import com.samton.common.constant.ExpCodeConstant;
import com.samton.framework.base.BaseController;
import com.samton.sys.bean.TIbenSysMenu;
import com.samton.sys.bean.TIbenSysRole;
import com.samton.sys.check.SysPmCheck;
import com.samton.sys.service.IMenuService;
import com.samton.sys.service.IRoleService;
import com.samton.sys.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	@Resource
	private IUserService userService;
	@Resource
	private IMenuService menuService;
	@Resource
	private IRoleService roleService;
	
	/**
	 * 查询列表
	 * @return
	 */
	@SysPmCheck(menuCode = "IBEN_ROLE_SELECT")
	@RequestMapping("/queryList")
	@ResponseBody
	public Map<String, Object> queryList() {
		Map<String,Object> result = new HashMap<String, Object>();
		TIbenSysRole search = new TIbenSysRole();

		List<TIbenSysRole> list = roleService.queryList(search);
		result.put("data", list);
		result.put("rs", 1);
		return result;
	}
	
	/**
	 * 查询列表
	 * @return
	 */
	@SysPmCheck(menuCode = "IBEN_ROLE_SELECT")
	@RequestMapping("/queryList2")
	@ResponseBody
	public Map<String, Object> queryList2() {
		return queryList();
	}
	
	/**
	 * 查询详情
	 * @param roleId
	 * @return
	 */
    @SysPmCheck(menuCode = "IBEN_ROLE_SELECT")
	@RequestMapping("/queryDetail")
	@ResponseBody
	public Map<String,Object> queryDetail(Long roleId){
    	TIbenSysRole RoleInfo= roleService.selectByRoleId(roleId);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("rs", 1);
		result.put("data", RoleInfo);
		return result;
	}
	
	/**
	 * 保存
	 * @param role
	 * @return
	 */
	@ResponseBody
	public Map<String,Object> save(TIbenSysRole role) {
    	if(StringUtils.isBlank(role.getRoleName())) {
    		return BaseConstant.returnErrorMsg(BaseConstant.ROLE_NAME_IS_NULL, ExpCodeConstant.explainCodeToMsg(BaseConstant.ROLE_NAME_IS_NULL));
    	}else if(role.getRoleName().length() > 20) {
    		return BaseConstant.returnErrorMsg(BaseConstant.ROLE_NAME_IS_TOO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.ROLE_NAME_IS_TOO_LONG));
    	}
    	if(role.getDataAuth() == null) {
    		return BaseConstant.returnErrorMsg(BaseConstant.ROLE_DATA_AUTH_IS_NULL, ExpCodeConstant.explainCodeToMsg(BaseConstant.ROLE_DATA_AUTH_IS_NULL));
    	}
    	if(StringUtils.isBlank(role.getDescription()) && role.getDescription().length() > 100) {
    		return BaseConstant.returnErrorMsg(BaseConstant.ROLE_DESCRIPTION_IS_TOO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.ROLE_DESCRIPTION_IS_TOO_LONG));
    	}
		Map<String,Object> result = new HashMap<String, Object>();
		if(role.getRoleId() == null) {

			result.put("roleId", roleService.add(role));
		}else {
			if(role.getDescription() == null) {
				role.setDescription("");
			}
			roleService.updateByPrimaryKeySelective(role);
		}
		result.put("rs", 1);
		return result;
	}
	
	/**
	 * 新增
	 * @param role
	 * @return
	 */
    @SysPmCheck(menuCode = "IBEN_ROLE_ADD")
	@RequestMapping("/add")   
    @ResponseBody
	public Map<String,Object> add(TIbenSysRole role) {
    	role.setRoleId(null);
		return save(role);
	}
	
	/**
	 * 修改
	 * @param role
	 * @return
	 */
	@SysPmCheck(menuCode = "IBEN_ROLE_UPDATE")
	@RequestMapping("/update")   
	@ResponseBody
	public Map<String,Object> update(TIbenSysRole role) {
		if(role.getRoleId() == null) {
			return BaseConstant.returnErrorMsg(BaseConstant.ROLE_ID_IS_NULL, ExpCodeConstant.explainCodeToMsg(BaseConstant.ROLE_ID_IS_NULL));
		}
		return save(role);
	}
	
	/**
	 * 删除
	 * @param roleId
	 * @return
	 */
    @SysPmCheck(menuCode = "IBEN_ROLE_DELETE")
	@RequestMapping("/delete")   
    @ResponseBody
	public Map<String,Object> delete(Long roleId){
		return roleService.deleteRoleById(roleId);
	}
	
	/**
	 * 查询角色权限（树形菜单）
	 * @param roleId
	 * @return
	 */
	@SysPmCheck(menuCode = "IBEN_ROLE_SELECT")
	@RequestMapping("/queryMenu")
	@ResponseBody
	public Map<String, Object> queryMenu(Long roleId) {
		Map<String,Object> result = new HashMap<String, Object>();
		List<TIbenSysMenu> menuList = menuService.getMenuTree(roleId);
		result.put("data", menuList);
		result.put("rs", 1);
		return result;
	}
	
	/**
	 * 修改按钮权限
	 * @param menuIds
	 * @param roleId
	 * @return
	 */
	@SysPmCheck(menuCode = "IBEN_ROLE_UPDATE_AUTH")
	@RequestMapping("/savePmData")   
	@ResponseBody
	public Map<String,Object> savePmData(Long[] menuIds,Long roleId){
		Map<String,Object> result = new HashMap<String,Object>();
		if(roleId!=null && roleId!=1){//角色id不为1时方可修改权限
			menuService.savePmMenuRoleData(menuIds, roleId);
		}
		result.put("rs",1);
		return result;
	}
}
