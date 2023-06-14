package com.samton.sys.service.impl;

import com.samton.base.bean.JqxParamBean;
import com.samton.base.constant.BaseConstant;
import com.samton.common.MD5.MD5Util;
import com.samton.common.constant.ExpCodeConstant;
import com.samton.framework.mybatis.pagination.PageContext;
import com.samton.framework.mybatis.pagination.Pagination;
import com.samton.framework.util.CurrentUtil;
import com.samton.sys.bean.TIbenSysUser;
import com.samton.sys.dao.TIbenSysUserMapper;
import com.samton.sys.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements IUserService {
    
    @Resource
    private TIbenSysUserMapper userMapper;

	@Override
	public TIbenSysUser getByLoginAccount(String loginAccount) {
		return userMapper.getByLoginAccount(loginAccount);
	}
	@Override
	public TIbenSysUser getByLoginAccountAndPasswd(String loginAccount, String passwd) {
		return userMapper.getByLoginAccountAndPasswd(loginAccount, passwd);
	}
	@Override
	public Pagination<TIbenSysUser> queryPageList(JqxParamBean paramBean) {
		Pagination<TIbenSysUser> pagination = PageContext.initialize(paramBean.getPagenum(),paramBean.getPagesize());
		List<TIbenSysUser> list = userMapper.queryPageList(paramBean,pagination.getRowBounds());
		for(TIbenSysUser user : list) {
			user.setPasswd(null);
			user.setRealPasswd(null);
		}
		pagination.setData(list);
		return pagination;
	}
	@Override
	public int insertSelective(TIbenSysUser user) {

		CurrentUtil.setBaseBeanByInsert(user);

		return userMapper.insertSelective(user);
	}
	@Override
	public TIbenSysUser selectByPrimaryKey(Long userId) {
		TIbenSysUser user = userMapper.selectByPrimaryKey(userId);
		return user;
	}
	@Override
	public int updateByPrimaryKeySelective(TIbenSysUser user) {
		CurrentUtil.setBaseBeanByModify(user);
		return userMapper.updateByPrimaryKeySelective(user);
	}
	@Override
	public Map<String, Object> save(TIbenSysUser user) {
		TIbenSysUser old = null;
		if(user.getUserId() != null) {
			old = this.selectByPrimaryKey(user.getUserId());
			if(old == null) {
				return BaseConstant.returnErrorMsg(BaseConstant.USER_DOES_NOT_EXIST, ExpCodeConstant.explainCodeToMsg(BaseConstant.USER_DOES_NOT_EXIST));
			}
		}
		if(StringUtils.isBlank(user.getLoginAccount())) {
			return BaseConstant.returnErrorMsg(BaseConstant.USER_LOGIN_ACCOUNT_IS_NULL, ExpCodeConstant.explainCodeToMsg(BaseConstant.USER_LOGIN_ACCOUNT_IS_NULL));
		}else if(!com.samton.common.util.StringUtils.checkPattern(user.getLoginAccount(), "^[a-zA-Z]?[a-zA-Z0-9]{0,29}$")) {
			return BaseConstant.returnErrorMsg(BaseConstant.USER_LOGIN_ACCOUNT_IS_WRONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.USER_LOGIN_ACCOUNT_IS_WRONG));
		}
		
		TIbenSysUser search = new TIbenSysUser();
		search.setAccurateLoginAccount(user.getLoginAccount());
		List<TIbenSysUser> users = userMapper.queryList(search);
		if(users.size() > 0) {
			if(user.getUserId() == null) {//新增
				return BaseConstant.returnErrorMsg(BaseConstant.USER_LOGIN_ACCOUNT_ALREADY_EXISTS, ExpCodeConstant.explainCodeToMsg(BaseConstant.USER_LOGIN_ACCOUNT_ALREADY_EXISTS));
			}else {//修改
				for(TIbenSysUser u : users) {
					if(user.getUserId().longValue() != u.getUserId().longValue()) {
						return BaseConstant.returnErrorMsg(BaseConstant.USER_LOGIN_ACCOUNT_ALREADY_EXISTS, ExpCodeConstant.explainCodeToMsg(BaseConstant.USER_LOGIN_ACCOUNT_ALREADY_EXISTS));
					}
				}
			}
		}
		
		if(user.getUserId() == null && StringUtils.isBlank(user.getRealPasswd())) {//新增时密码不能为空
			return BaseConstant.returnErrorMsg(BaseConstant.USER_PASSWD_IS_NULL, ExpCodeConstant.explainCodeToMsg(BaseConstant.USER_PASSWD_IS_NULL));
		}
		
		//判断新密码格式是否正确
		if(StringUtils.isNotBlank(user.getRealPasswd())) {
			if(!com.samton.common.util.StringUtils.checkPattern(user.getRealPasswd(), "^[0-9a-zA-Z]{6,18}$")){
				return BaseConstant.returnErrorMsg(BaseConstant.USER_PASSWD_IS_WRONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.USER_PASSWD_IS_WRONG));
			}
			user.setPasswd(MD5Util.MD5(user.getRealPasswd()));
		}
		
		if(StringUtils.isBlank(user.getFullName())) {
			return BaseConstant.returnErrorMsg(BaseConstant.USER_FULL_NAME_IS_NULL, ExpCodeConstant.explainCodeToMsg(BaseConstant.USER_FULL_NAME_IS_NULL));
		}else if(user.getFullName().length() > 20) {
			return BaseConstant.returnErrorMsg(BaseConstant.USER_FULL_NAME_IS_TOO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.USER_FULL_NAME_IS_TOO_LONG));
		}
		
		if(user.getRoleId() == null) {
			return BaseConstant.returnErrorMsg(BaseConstant.ROLE_ID_IS_NULL2, ExpCodeConstant.explainCodeToMsg(BaseConstant.ROLE_ID_IS_NULL2));
		}
		
//		if(user.getDeptId() == null) {
//			return BaseConstant.returnErrorMsg(BaseConstant.DEPT_ID_IS_NULL2, ExpCodeConstant.explainCodeToMsg(BaseConstant.DEPT_ID_IS_NULL2));
//		}
		
		if(user.getUserId() == null) {//新增

			this.insertSelective(user);
		}else {//修改
			this.updateByPrimaryKeySelective(user);
		}
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("rs", 1);
		return result;
	}
	@Override
	public int deleteByUserId(Long userId) {
		return userMapper.deleteByUserId(userId);
	}
}