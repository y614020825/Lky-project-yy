package com.samton.project.dao;

import com.samton.base.bean.JqxParamBean;
import com.samton.project.bean.TIbenProject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface TIbenProjectMapper {
    int deleteByPrimaryKey(Long projectId);
    int deleteByProjectId(@Param("projectId") Long projectId);
    int insert(TIbenProject record);

    int insertSelective(TIbenProject record);

    TIbenProject selectByPrimaryKey(Long projectId);

    int updateByPrimaryKeySelective(TIbenProject record);

    int updateByPrimaryKey(TIbenProject record);
    List<TIbenProject> querySameList(@Param("projectName")String projectName);
    List<TIbenProject> queryPageList(JqxParamBean paramBean, RowBounds rowBounds);



}