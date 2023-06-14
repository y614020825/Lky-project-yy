package com.samton.sys.bean;

import com.samton.framework.bean.BaseBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TIbenSysStaff extends BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long StaffId;
    private Long projectId;
    private Long roleId;
    private Long[] userId;
    private List<TIbenSysStaff> staffIds;
    private String userName;
    private Long createUserId;//创建用户id

    private String createUserName;//创建用户名

    private Long modifyUserId;//修改用户id

    private String modifyUserName;//修改用户名

    private Date createDate;//创建时间

    private Date modifyDate;//修改时间

    private Short isDeleted;//是否删除

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

    public Long[] getUserId() {
        return userId;
    }

    public void setUserId(Long[] userId) {
        this.userId = userId;
    }

    public List<TIbenSysStaff> getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(List<TIbenSysStaff> staffIds) {
        this.staffIds = staffIds;
    }



    public Long getStaffId() {
        return StaffId;
    }

    public void setStaffId(Long staffId) {
        StaffId = staffId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}