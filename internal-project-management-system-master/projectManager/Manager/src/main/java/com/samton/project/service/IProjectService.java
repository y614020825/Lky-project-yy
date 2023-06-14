package com.samton.project.service;

import com.samton.base.bean.JqxParamBean;
import com.samton.framework.mybatis.pagination.Pagination;
import com.samton.project.bean.TIbenProject;

import java.util.Map;

public interface IProjectService {
    /**
     * 添加
     */
    Map<String,Object> save(TIbenProject projcet);
     public Pagination<TIbenProject> queryPageList(JqxParamBean paramBean);


    /**
     * 删除
     */
//    void deleteByPrimaryKey(Long projectId);
    int deleteByProjectId(Long projectId);

    /**
     * 修改
     */
    void updateByPrimaryKey();

    /**
     * 查询
     */
    void queryByProjectName();

}
