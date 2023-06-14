package com.samton.sys.service;

import com.samton.sys.bean.TIbenSysStaff;

import java.util.List;
import java.util.Map;

public interface IStaffService {
    Map<String,Object> save(List<TIbenSysStaff> staffs);
    int deleteByProjectId(Long projectId);
    List<TIbenSysStaff> queryList(Long projectId);

}
