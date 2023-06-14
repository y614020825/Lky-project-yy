package com.samton.framework.bean;

import java.util.List;
import java.util.Map;

public class ExportBean {
	private int variableSize;
	private List<Map<String,Object>> data;
	public int getVariableSize() {
		return variableSize;
	}
	public void setVariableSize(int variableSize) {
		this.variableSize = variableSize;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
}
