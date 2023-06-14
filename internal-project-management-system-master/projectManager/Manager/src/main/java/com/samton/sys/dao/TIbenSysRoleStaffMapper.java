package com.samton.sys.dao;

import com.samton.sys.bean.TIbenSysStaff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TIbenSysRoleStaffMapper {
    int insertSelective(@Param("staffs") List<TIbenSysStaff> staffs);
    TIbenSysStaff selectByPrimaryKey(@Param("staffId") Long staffId);
    int deleteByProjectId(@Param("projectId") Long projectId);
    List<TIbenSysStaff> queryList(@Param("projectId")Long projectId);
}
