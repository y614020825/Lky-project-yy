package com.samton.sys.controller;

import com.samton.base.bean.JqxParamBean;
import com.samton.base.constant.BaseConstant;
import com.samton.base.constant.PmExpCodeConstant;
import com.samton.common.MD5.MD5Util;
import com.samton.common.constant.ExpCodeConstant;
import com.samton.framework.base.BaseController;
import com.samton.framework.mybatis.pagination.Pagination;
import com.samton.framework.util.CurrentUtil;
import com.samton.sys.bean.TIbenSysUser;
import com.samton.sys.check.SysPmCheck;
import com.samton.sys.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Resource
	private IUserService userService;
	
	/**
	 * 分页查询
	 * @param paramBean
	 * @param search
	 * @return
	 */
	@SysPmCheck(menuCode = "IBEN_USER_SELECT")
	@RequestMapping("/queryPageList")
	@ResponseBody
	public Map<String,Object> queryPageList(JqxParamBean paramBean, TIbenSysUser search) {

		paramBean.setVo(search);
		Pagination<TIbenSysUser> pageInfo = userService.queryPageList(paramBean);
		//如果数据为空并且当前页大于总页数，再查一遍
		if(pageInfo.getData()==null || pageInfo.getData().size()==0 && paramBean.getPagenum() > pageInfo.getTotalPages()){
			paramBean.setPagenum(pageInfo.getTotalPages());
			pageInfo = userService.queryPageList(paramBean);
		}
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("rs", 1);
		result.put("pageInfo", pageInfo);
		return result;
	}
	
	/**
	 * 详情
	 * @param userId
	 * @return
	 */
	@SysPmCheck(menuCode = "IBEN_USER_SELECT")
	@RequestMapping("/queryDetail")
	@ResponseBody
	public Map<String,Object> queryDetail(Long userId) {
		TIbenSysUser user = userService.selectByPrimaryKey(userId);
		if(user != null) {
			user.setPasswd(null);
			user.setRealPasswd(null);
		}
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("rs", 1);
		result.put("data", user);
		return result;
	}

	@SysPmCheck(menuCode = "IBEN_USER_ADD")
	@RequestMapping("/add")
	@ResponseBody
	public Map<String,Object> add(TIbenSysUser user) {
		user.setUserId(null);
		return userService.save(user);
	}

	@SysPmCheck(menuCode = "IBEN_USER_UPDATE")
	@RequestMapping("/update")
	@ResponseBody
	public Map<String,Object> update(TIbenSysUser user) {
		if(user.getUserId() == null) {
			return BaseConstant.returnErrorMsg(BaseConstant.USER_ID_IS_NULL, ExpCodeConstant.explainCodeToMsg(BaseConstant.USER_ID_IS_NULL));
		}
		return userService.save(user);
	}
	
	/**
	 * 删除
	 * @param userId
	 * @return
	 */
	@SysPmCheck(menuCode = "IBEN_USER_DELETE")
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(Long userId) {
		userService.deleteByUserId(userId);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("rs", 1);
		return result;
	}
	
	@RequestMapping("/resetPwd")
	@ResponseBody
	public Map<String,Object> resetPwd(String oldPwd,String newPwdOne,String newPwdTwo) {
		Map<String,Object> result = new HashMap<String, Object>();
		//判断新密码是否为空
		if(StringUtils.isEmpty(newPwdOne)){
			return this.returnErrorMsg(PmExpCodeConstant.NEW_PWD_NULL, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.NEW_PWD_NULL));
		}
		//判断新密码格式是否正确
		if(!com.samton.common.util.StringUtils.checkPattern(newPwdOne, "^[0-9a-zA-Z]{6,18}$")){
			return this.returnErrorMsg(PmExpCodeConstant.NEW_PWD_ERROR, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.NEW_PWD_ERROR));
		}
		//判断两次密码是否相同
		if(!StringUtils.equals(newPwdOne, newPwdTwo)){
			return this.returnErrorMsg(PmExpCodeConstant.TWO_PWD_DEF, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.TWO_PWD_DEF));
		}
		
		Long userId = CurrentUtil.getCurrentCachUser().getUserId();
		
		//校验原密码是否正确
		TIbenSysUser user = userService.selectByPrimaryKey(userId);
		if(user == null || !(MD5Util.MD5(oldPwd).equals(user.getPasswd()))) {
			return this.returnErrorMsg(PmExpCodeConstant.PWD_IS_WRONG, ExpCodeConstant.explainCodeToMsg(PmExpCodeConstant.PWD_IS_WRONG));
		}
		
		TIbenSysUser newUser = new TIbenSysUser();
		newUser.setUserId(userId);
		newUser.setRealPasswd(newPwdOne);
		newUser.setPasswd(MD5Util.MD5(newPwdOne));
		userService.updateByPrimaryKeySelective(newUser);
		result.put("rs", 1);
		return result;
	}
	
}