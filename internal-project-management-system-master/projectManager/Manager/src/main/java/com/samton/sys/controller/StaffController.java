package com.samton.sys.controller;

import com.samton.framework.base.BaseController;
import com.samton.sys.bean.TIbenSysStaff;
import com.samton.sys.check.SysPmCheck;
import com.samton.sys.service.IStaffService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("staff")
public class StaffController extends BaseController {
    @Resource
    private IStaffService staffService;
    /**
     * 新增
     */
    @SysPmCheck(menuCode = "IBEN_STAFF_ADD")
    @RequestMapping("/add")
    @ResponseBody
    public Map<String,Object> add(@RequestBody List<TIbenSysStaff> staffs){
        return staffService.save(staffs);
    }

    @SysPmCheck(menuCode = "IBEN_STAFF_DELETE")
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(Long projectId){//根据projectId去删除
        staffService.deleteByProjectId(projectId);
        Map<String,Object> result = new HashMap<>();
        result.put("rs",1);
        return result;

    }
    @SysPmCheck(menuCode = "IBEN_STAFF_UPDATE")
    @RequestMapping("/update")
    @ResponseBody
    public Map<String,Object> update(List<TIbenSysStaff> staffs){
        return staffService.save(staffs);

    }

    @SysPmCheck(menuCode = "IBEN_STAFF_QUERY")
    @RequestMapping("/queryList")
    @ResponseBody
    public Map<String,Object> queryList(Long projectId){
        List<TIbenSysStaff> staffs= staffService.queryList(projectId);
        Map<String,Object> result = new HashMap<>();
        result.put("staffs",staffs);
        result.put("rs",1);
        return result;

    }
}
