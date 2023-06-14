package com.samton.project.bean;

import com.samton.framework.bean.BaseBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class TIbenProject extends BaseBean implements Serializable {
    private Long projectId;//项目id

    private String projectName;//项目名字

    private Short projectType;//项目类型

    private Double projectProgress;//项目进度

    private Date projectDeliverDate;//项目交货时间

    private String commercialPersonnel;//商务人员（商务负责人）

    private Integer[] robotCategory;//机器人类型（10项多选）1.智大屏 2.智灵动 3.智小鱼
    // 4.慧消毒_MO1 5.慧消毒_MO2 6.慧消毒_紫外灯 7.慧消毒_脉冲灯 8.慧晨检 9.慧定制 10.其他

    private Short projectStage;//项目阶段：1.售前支持 2.合同签订 3.研发阶段 4.交付阶段 5.结项

    private Date createDate;//创建时间

    private Date payDate;//付款时间

    private Integer robotAmount;//机器人数量

    private Short projectStatus;//项目状态：1.未启动 2.正常 3.异常 4.挂起 5.完成

    private Date leadTime;//交付时间

    private String terminalName;//终端名称

    private String terminalIndustry;//终端行业

    private String terminalAddress;//终端地址

    private String middlemanName;//中间商名称

    private String middlemanIndustry;//中间商产业

    private String middlemanAddress;//中间商地址

    private String communicatePerson;//对接人

    private String communicatePersonPhonenumber;//对接人联系电话

    private String communicateRole;//对接人角色

    private Date modifyDate;//修改时间

    private Short isDeleted;//是否被删除 0-未被删除 1-被删除了

    private String createUserName;//创建人姓名

    private Long createUserId;//创建人ID

    private String modifyUserName;//修改人姓名

    private Long modifyUserId;//修改人ID

    private Integer[] customType;//定制类型

    private Double contractTime;//合同工时
   private Long pointUser;//
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leadingStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leadingEndTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creatingStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creatingEndTime;
    private static final long serialVersionUID = 1L;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public Double getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(Double projectProgress) {
        this.projectProgress = projectProgress;
    }

    public Date getProjectDeliverDate() {
        return projectDeliverDate;
    }

    public void setProjectDeliverDate(Date projectDeliverDate) {
        this.projectDeliverDate = projectDeliverDate;
    }

    public String getCommercialPersonnel() {
        return commercialPersonnel;
    }

    public void setCommercialPersonnel(String commercialPersonnel) {
        this.commercialPersonnel = commercialPersonnel;
    }

    public Integer[] getRobotCategory() {
        return robotCategory;
    }

    public void setRobotCategory(Integer[] robotCategory) {
        this.robotCategory = robotCategory;
    }



    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Integer getRobotAmount() {
        return robotAmount;
    }

    public void setRobotAmount(Integer robotAmount) {
        this.robotAmount = robotAmount;
    }


    public Date getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Date leadTime) {
        this.leadTime = leadTime;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getTerminalIndustry() {
        return terminalIndustry;
    }

    public void setTerminalIndustry(String terminalIndustry) {
        this.terminalIndustry = terminalIndustry;
    }

    public String getTerminalAddress() {
        return terminalAddress;
    }

    public void setTerminalAddress(String terminalAddress) {
        this.terminalAddress = terminalAddress;
    }

    public String getMiddlemanName() {
        return middlemanName;
    }

    public void setMiddlemanName(String middlemanName) {
        this.middlemanName = middlemanName;
    }

    public String getMiddlemanIndustry() {
        return middlemanIndustry;
    }

    public void setMiddlemanIndustry(String middlemanIndustry) {
        this.middlemanIndustry = middlemanIndustry;
    }

    public String getMiddlemanAddress() {
        return middlemanAddress;
    }

    public void setMiddlemanAddress(String middlemanAddress) {
        this.middlemanAddress = middlemanAddress;
    }

    public String getCommunicatePerson() {
        return communicatePerson;
    }

    public void setCommunicatePerson(String communicatePerson) {
        this.communicatePerson = communicatePerson;
    }

    public String getCommunicatePersonPhonenumber() {
        return communicatePersonPhonenumber;
    }

    public void setCommunicatePersonPhonenumber(String communicatePersonPhonenumber) {
        this.communicatePersonPhonenumber = communicatePersonPhonenumber;
    }

    public String getCommunicateRole() {
        return communicateRole;
    }

    public void setCommunicateRole(String communicateRole) {
        this.communicateRole = communicateRole;
    }

    @Override
    public Date getModifyDate() {
        return modifyDate;
    }

    @Override
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }


    @Override
    public String getCreateUserName() {
        return createUserName;
    }

    @Override
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    @Override
    public Long getCreateUserId() {
        return createUserId;
    }

    @Override
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String getModifyUserName() {
        return modifyUserName;
    }

    @Override
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    @Override
    public Long getModifyUserId() {
        return modifyUserId;
    }

    @Override
    public void setModifyUserId(Long modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public Integer[] getCustomType() {
        return customType;
    }

    public void setCustomType(Integer[] customType) {
        this.customType = customType;
    }

    public Double getContractTime() {
        return contractTime;
    }

    public void setContractTime(Double contractTime) {
        this.contractTime = contractTime;
    }

    public Date getLeadingStartTime() {
        return leadingStartTime;
    }

    public void setLeadingStartTime(Date leadingStartTime) {
        this.leadingStartTime = leadingStartTime;
    }

    public Date getLeadingEndTime() {
        return leadingEndTime;
    }

    public void setLeadingEndTime(Date leadingEndTime) {
        this.leadingEndTime = leadingEndTime;
    }

    public Date getCreatingStartTime() {
        return creatingStartTime;
    }

    public void setCreatingStartTime(Date creatingStartTime) {
        this.creatingStartTime = creatingStartTime;
    }

    public Date getCreatingEndTime() {
        return creatingEndTime;
    }

    public void setCreatingEndTime(Date creatingEndTime) {
        this.creatingEndTime = creatingEndTime;
    }

    public Short getProjectType() {
        return projectType;
    }

    public void setProjectType(Short projectType) {
        this.projectType = projectType;
    }

    public Short getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(Short projectStage) {
        this.projectStage = projectStage;
    }

    public Short getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Short projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getPointUser() {
        return pointUser;
    }

    public void setPointUser(Long pointUser) {
        this.pointUser = pointUser;
    }
}