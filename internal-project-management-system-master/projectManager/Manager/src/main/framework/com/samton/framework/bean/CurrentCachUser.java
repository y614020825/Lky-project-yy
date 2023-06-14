package com.samton.framework.bean;

import java.io.Serializable;

public class CurrentCachUser implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long userId;
    private String fullName;
    private String enterpriseName;
	private String loginAccount;
	private Long deptId;
	private String roleName;
	private Long roleId;
    //用户类型，1-运营，2-代理商

    private Short dataAuth;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Short getDataAuth() {
		return dataAuth;
	}
	public void setDataAuth(Short dataAuth) {
		this.dataAuth = dataAuth;
	}
}