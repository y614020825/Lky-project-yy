package com.samton.sys.service.impl;

import com.samton.framework.bean.CurrentCachUser;
import com.samton.framework.util.CurrentUtil;
import com.samton.sys.bean.TIbenSysStaff;
import com.samton.sys.dao.TIbenSysRoleStaffMapper;
import com.samton.sys.service.IStaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("staffService")
public class StaffServiceImpl implements IStaffService {
    @Resource
    private TIbenSysRoleStaffMapper roleStaffMapper;
    @Override
    public Map<String, Object> save(List<TIbenSysStaff> staffs) {
        TIbenSysStaff staff = (TIbenSysStaff) staffs.get(0);
       Long projectId=staff.getProjectId();
       roleStaffMapper.deleteByProjectId(projectId);
        CurrentCachUser cachUser = CurrentUtil.getCurrentCachUser();
        CurrentUtil.setBaseBeanByInsert(staff);
        staffs.set(0,staff);
       roleStaffMapper.insertSelective(staffs);
        Map<String,Object> result = new HashMap<>();
        result.put("rs",1);
        return result;
    }

    @Override
    public int deleteByProjectId(Long projectId) {
        return roleStaffMapper.deleteByProjectId(projectId);
    }

    @Override
    public List<TIbenSysStaff> queryList(Long projectId) {
        List<TIbenSysStaff> staffs=roleStaffMapper.queryList(projectId);
        return staffs;
    }
}
