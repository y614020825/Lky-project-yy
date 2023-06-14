package com.samton.framework.exception;

import com.samton.common.exception.BaseException;


/**
 * @ClassName: UndefinedException
 * @Description:(service 层的异常信息)
 */
public class UndefinedException extends BaseException {
	
	private static final long serialVersionUID = 1L;

	public UndefinedException(Throwable cause, String errorCode,String showCode) {
		super(errorCode);
	}

}