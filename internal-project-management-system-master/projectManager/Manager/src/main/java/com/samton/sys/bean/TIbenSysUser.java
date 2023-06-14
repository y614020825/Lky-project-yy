package com.samton.sys.bean;

import com.samton.framework.bean.BaseBean;
import java.io.Serializable;
import java.util.Date;

public class TIbenSysUser extends BaseBean implements Serializable {
    private Long userId;

    private String loginAccount;

    private String passwd;

    private String realPasswd;

    private String fullName;

    private Long roleId;

    private Long deptId;


    private Long createUserId;

    private String createUserName;

    private Long modifyUserId;

    private String modifyUserName;

    private Date createDate;

    private Date modifyDate;

    private Short isDeleted;
    
    private String roleName;
    
    private String deptName;
    
    private String accurateLoginAccount;
    
    //数据权限，1-全公司，2-本部门及以下，3-部门，4-仅可见自己
    private Short dataAuth;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRealPasswd() {
        return realPasswd;
    }

    public void setRealPasswd(String realPasswd) {
        this.realPasswd = realPasswd;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }


    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Long getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getModifyUserName() {
        return modifyUserName;
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAccurateLoginAccount() {
		return accurateLoginAccount;
	}

	public void setAccurateLoginAccount(String accurateLoginAccount) {
		this.accurateLoginAccount = accurateLoginAccount;
	}

	public Short getDataAuth() {
		return dataAuth;
	}

	public void setDataAuth(Short dataAuth) {
		this.dataAuth = dataAuth;
	}
}