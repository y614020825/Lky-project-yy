package com.samton.project.service.impl;

import com.samton.base.bean.JqxParamBean;
import com.samton.base.constant.BaseConstant;
import com.samton.common.constant.ExpCodeConstant;
import com.samton.framework.bean.CurrentCachUser;
import com.samton.framework.mybatis.pagination.PageContext;
import com.samton.framework.mybatis.pagination.Pagination;
import com.samton.framework.util.CurrentUtil;
import com.samton.project.bean.TIbenProject;
import com.samton.project.dao.TIbenProjectMapper;
import com.samton.project.service.IProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("projectService")
public class ProjectServiceImpl implements IProjectService {
    @Resource
    private TIbenProjectMapper projectMapper;


    @Override
    public Map<String,Object> save(TIbenProject project) {
        TIbenProject old = null;
        if(project.getProjectId()!=null){
            old = projectMapper.selectByPrimaryKey(project.getProjectId());
            if (old == null){
                return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_DOES_NOT_EXIST, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_DOES_NOT_EXIST));
            }
        }
//        CurrentCachUser curUser = CurrentUtil.getCurrentCachUser();//得到当前登录的用户,序列化
//        if(project.getProjectId() == null){//新增时
//            //type，用户类型，1-运营，2-代理商
//        }
        if(StringUtils.isBlank(project.getProjectName())){//项目名是否为空判断
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_NAME_IS_NULL, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_NAME_IS_NULL));
        } else if (project.getProjectName().length()>60) {//项目名过长判断
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_NAME_IS_TOO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_NAME_IS_TOO_LONG));
        }

//         else if (project.getProjectName().length()<5) {//项目名过短判断
//
//        }

        List<TIbenProject> list = projectMapper.querySameList(project.getProjectName());//项目相同判断
        if(list.size() > 0){//如果是新增 有相同的直接退出
            if(project.getProjectId() == null){//判断当前为新增
                return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_NAME_ALREADY_EXISTS, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_NAME_ALREADY_EXISTS));
            }
            else{
                for(TIbenProject p : list) {//增强for循环（迭代器循环）
                    if(p.getProjectId().longValue() != project.getProjectId().longValue()) {//Long不能直接进行比较 得用longvalue转变成long才行
                        return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_NAME_ALREADY_EXISTS, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_NAME_ALREADY_EXISTS));
                    }
                }
            }
        }

//        project.setProjectName(project.getProjectName().trim());//将项目名的前后空字符去掉

        if(org.apache.commons.lang.StringUtils.isNotBlank(project.getTerminalName()) && project.getTerminalName().length() > 50) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_TERMINAL_NAME_IS_TO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_TERMINAL_NAME_IS_TO_LONG));
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(project.getTerminalIndustry()) && project.getTerminalIndustry().length() > 50) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_TERMINAL_INDUSTRY_IS_TO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_TERMINAL_INDUSTRY_IS_TO_LONG));
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(project.getTerminalAddress()) && project.getTerminalAddress().length() > 500) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_TERMINAL_ADDRESS_IS_TO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_TERMINAL_ADDRESS_IS_TO_LONG));
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(project.getMiddlemanName()) && project.getMiddlemanName().length() > 50) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_MIDDLE_MAN_NAME_IS_TO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_MIDDLE_MAN_NAME_IS_TO_LONG));
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(project.getMiddlemanIndustry()) && project.getMiddlemanIndustry().length() > 50) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_MIDDLE_MAN_INDUSTRY_IS_TO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_MIDDLE_MAN_INDUSTRY_IS_TO_LONG));
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(project.getMiddlemanAddress()) && project.getMiddlemanAddress().length() > 50) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_MIDDLE_MAN_ADDRESS_IS_TO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_MIDDLE_MAN_ADDRESS_IS_TO_LONG));
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(project.getCommunicatePerson()) && project.getCommunicatePerson().length() > 50) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_COMMUNICATE_PERSON_IS_TO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_COMMUNICATE_PERSON_IS_TO_LONG));
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(project.getCommunicateRole()) && project.getCommunicateRole().length() > 30) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_COMMUNICATE_ROLE_IS_TO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_COMMUNICATE_ROLE_IS_TO_LONG));
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(project.getCommunicatePersonPhonenumber()) && !com.samton.common.util.StringUtils.checkPattern(project.getCommunicatePersonPhonenumber(), "(^[1][0-9]{10}$)|(^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$)")) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_COMMUNICATE_PERSON_PHONE_NUMBER_IS_TO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_COMMUNICATE_PERSON_PHONE_NUMBER_IS_TO_LONG));
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(project.getCommercialPersonnel()) && project.getCommercialPersonnel().length() > 30) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_COMMERCIAL_PERSONNEL_IS_TO_LONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_COMMERCIAL_PERSONNEL_IS_TO_LONG));
        }
        if((project.getProjectProgress() != null)&&((project.getProjectProgress().doubleValue()>100)||(project.getProjectProgress().doubleValue()<0))) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_PROGRESS_IS_WRONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_PROGRESS_IS_WRONG));
        }
        if((project.getContractTime() != null) &&(project.getContractTime().floatValue()<0)) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_CONTRACT_TIME_IS_WRONG, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_CONTRACT_TIME_IS_WRONG));
        }
//        if(project.getProductCategory() == null) {
//
//        }
//        return null;


        if(project.getProjectId()==null) {
            CurrentUtil.setBaseBeanByInsert(project);
            projectMapper.insertSelective(project);//字段如果为空就不插入，如果不为空就插入 使用了动态拼接sql
        }else{
            CurrentUtil.setBaseBeanByModify(project);
            projectMapper.updateByPrimaryKeySelective(project);
        }

        Map<String,Object>  result = new HashMap<String, Object>();
        result.put("rs", 1);
        return result;
    }

    @Override
    public Pagination<TIbenProject> queryPageList(JqxParamBean paramBean) {
        CurrentCachUser curUSer = CurrentUtil.getCurrentCachUser();
        if(paramBean.getVo() != null && paramBean.getVo().getClass() .equals( new TIbenProject().getClass() ) ){
            TIbenProject search =(TIbenProject) paramBean.getVo();
        }
        Pagination<TIbenProject> pagination = PageContext.initialize(paramBean.getPagenum(),paramBean.getPagesize());
        List<TIbenProject> list0 = projectMapper.queryPageList(paramBean,pagination.getRowBounds());
        pagination.setData(list0);
        return pagination;
    }

    @Override
    public int deleteByProjectId(Long projectId) {
        return projectMapper.deleteByProjectId(projectId);
    }





    @Override
    public void updateByPrimaryKey() {

    }

    @Override
    public void queryByProjectName() {

    }
}
