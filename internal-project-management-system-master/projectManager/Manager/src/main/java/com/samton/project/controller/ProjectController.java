package com.samton.project.controller;
import com.samton.base.bean.JqxParamBean;
import com.samton.base.constant.BaseConstant;
import com.samton.common.constant.ExpCodeConstant;
import com.samton.framework.base.BaseController;
import com.samton.framework.bean.CurrentCachUser;
import com.samton.framework.mybatis.pagination.Pagination;
import com.samton.framework.util.CurrentUtil;
import com.samton.project.bean.TIbenProject;
import com.samton.project.service.IProjectService;
import com.samton.sys.check.SysPmCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {
    @Resource
    private IProjectService projectService;

    /**
     * 添加项目
     * @param
     * @return
     */

    @SysPmCheck(menuCode = "IBEN_PROJECT_ADD")
    @RequestMapping("/add")
    @ResponseBody
    public Map<String,Object> add(TIbenProject project) {
         project.setProjectId(null);
         return projectService.save(project);
    }

    /**
     * 删除项目
     * @param
     * @return
     */
    @SysPmCheck(menuCode = "IBEN_PROJECT_DELETE")
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(Long projectId) {
        projectService.deleteByProjectId(projectId);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("rs",1);
        return result;
    }

    /**
     * 更改项目
     * @param
     * @return
     */
    @SysPmCheck(menuCode = "IBEN_PROJECT_UPDATE")
    @RequestMapping("/update")
    @ResponseBody
    public Map<String,Object> update(TIbenProject project) {
        if(project.getProjectId() == null) {
            return BaseConstant.returnErrorMsg(BaseConstant.PROJECT_ID_IS_NULL, ExpCodeConstant.explainCodeToMsg(BaseConstant.PROJECT_ID_IS_NULL));
        }
        return projectService.save(project);
    }
    /**
     * 查询项目
     * @param
     * @return
     */
    @SysPmCheck(menuCode = "IBEN_PROJECT_QUERY")
    @RequestMapping("/queryPageList")
    @ResponseBody
    public Map<String,Object> queryByPageList(JqxParamBean paramBean, TIbenProject search) {
        CurrentCachUser curUser = CurrentUtil.getCurrentCachUser();
        if (curUser.getDataAuth()==3){//仅可见被指定的

            search.setPointUser(curUser.getUserId());
        }
        else if(curUser.getDataAuth()==2){//既可以看到被指定的还有自己添加的
            search.setCreateUserId(curUser.getUserId());
//            search.setPointUser(curUser.getUserId());
        }
//        if(cachUser.getDataAuth() == 2||cachUser.getDataAuth() == 3){//权限
//            search.setCreateUserId(cachUser.getUserId());
//        }
        paramBean.setVo(search);
        Pagination<TIbenProject> pageInfo = projectService.queryPageList(paramBean);
        Map<String,Object> result=new HashMap<>();
        result.put("rs",1);
        result.put("pageInfo",pageInfo);
        return result;
    }
}
