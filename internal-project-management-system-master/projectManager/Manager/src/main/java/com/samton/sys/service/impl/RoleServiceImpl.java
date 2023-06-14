package com.samton.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.samton.base.constant.BaseConstant;
import com.samton.base.constant.PmExpCodeConstant;
import com.samton.common.constant.ExpCodeConstant;
import com.samton.framework.util.CurrentUtil;
import com.samton.sys.bean.TIbenSysRole;
import com.samton.sys.bean.TIbenSysUser;
import com.samton.sys.dao.TIbenSysRoleMapper;
import com.samton.sys.dao.TIbenSysRoleMenuMapper;
import com.samton.sys.dao.TIbenSysUserMapper;
import com.samton.sys.service.IRoleService;

@Service("roleService")
public class RoleServiceImpl implements IRoleService {
    
    @Resource
    private TIbenSysRoleMapper roleMapper;
    @Resource
    private TIbenSysUserMapper userMapper;
    @Resource
    private TIbenSysRoleMenuMapper roleMenuMapper;

	@Override
	public Long add(TIbenSysRole role) {
		CurrentUtil.setBaseBeanByInsert(role);
		roleMapper.insertSelective(role);
		return role.getRoleId();
	}

	@Override
	public Map<String, Object> deleteRoleById(Long roleId) {
		if(roleId == null) {
			return BaseConstant.returnErrorMsg(BaseConstant.ROLE_ID_IS_NULL, ExpCodeConstant.explainCodeToMsg(BaseConstant.ROLE_ID_IS_NULL));
		}

    	if(roleId <= 2) {
			return BaseConstant.returnErrorMsg(PmExpCodeConstant.PM_ACCESS_DENIED, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.PM_ACCESS_DENIED));
		}
		TIbenSysUser search = new TIbenSysUser();
		search.setRoleId(roleId);
		List<TIbenSysUser> userList = userMapper.queryList(search);
		if(userList.size() > 0) {
			return BaseConstant.returnErrorMsg(BaseConstant.ROLE_DEL_FAIL_BECAUSE_HAS_BEEN_USED, ExpCodeConstant.explainCodeToMsg(BaseConstant.ROLE_DEL_FAIL_BECAUSE_HAS_BEEN_USED));
		}
        Map<String,Object> map = new HashMap<String, Object>();
        roleMapper.deleteByPrimaryKey(roleId);
        roleMenuMapper.deleteByRoleId(roleId);
		map.put("rs",1);
		return map;
	}

	@Override
	public TIbenSysRole selectByRoleId(Long roleId) {
		return roleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public int updateByPrimaryKeySelective(TIbenSysRole role) {
		CurrentUtil.setBaseBeanByModify(role);
		return roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public List<TIbenSysRole> queryList(TIbenSysRole search) {
		return roleMapper.queryList(search);
	}

}