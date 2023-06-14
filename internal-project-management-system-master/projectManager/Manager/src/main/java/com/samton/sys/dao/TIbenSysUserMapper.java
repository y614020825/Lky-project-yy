package com.samton.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.samton.base.bean.JqxParamBean;
import com.samton.sys.bean.TIbenSysUser;

public interface TIbenSysUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(TIbenSysUser record);

    int insertSelective(TIbenSysUser record);

    TIbenSysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(TIbenSysUser record);

    int updateByPrimaryKey(TIbenSysUser record);
	
	TIbenSysUser getByLoginAccount(@Param("loginAccount") String loginAccount);
	
	TIbenSysUser getByLoginAccountAndPasswd(@Param("loginAccount") String loginAccount, @Param("passwd") String passwd);
    
    List<TIbenSysUser> queryList(TIbenSysUser search);
    
	/**
	 * 分页查询
	 * @param paramBean
	 * @param rowBounds
	 * @return
	 */
    List<TIbenSysUser> queryPageList(JqxParamBean paramBean, RowBounds rowBounds);
    
    int deleteByUserId(@Param("userId") Long userId);
    
    int deleteByDeptIds(@Param("deptIds") List<Long> deptIds);
    
    int deleteByAgentId(@Param("agentId") Long agentId);
}