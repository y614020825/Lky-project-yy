package com.samton.framework.exception;

import com.samton.common.exception.BaseException;


public class FilterException extends BaseException {
	
	private static final long serialVersionUID = 1L;
	
	public FilterException(String errorCode) {
		super(errorCode);
	}

	public FilterException(String errorCode, String bgMsg) {
		super(errorCode, bgMsg);
	}
	
	public FilterException(String errorCode, String bgMsg, boolean alertExpFlag) {
		super(errorCode, bgMsg);
	}

}