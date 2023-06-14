package com.samton.base.bean;

import com.samton.framework.util.CurrentUtil;


public class JqxParamBean{

	private Integer pagenum;
	private Integer pagesize;
	private Object vo;

	private Long currentUserId = CurrentUtil.getCurrentCachUser()==null?null:CurrentUtil.getCurrentCachUser().getUserId();
	private String orderSql;
	
	public Integer getPagenum() {
		if(pagenum==null) pagenum=1;
		return pagenum;
	}
	public void setPagenum(Integer pagenum) {
		this.pagenum = pagenum;
	}
	public Integer getPagesize() {
		//默认单页20
		if(pagesize==null)pagesize=20;
		return pagesize;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
	public Object getVo() {
		return vo;
	}
	public void setVo(Object vo) {
		this.vo = vo;
	}
	public Long getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}
	public String getOrderSql() {
		return orderSql;
	}
	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}
	
}