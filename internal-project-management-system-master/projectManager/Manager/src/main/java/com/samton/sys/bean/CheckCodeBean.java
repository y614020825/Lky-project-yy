package com.samton.sys.bean;

import java.io.Serializable;
import java.util.Random;

public class CheckCodeBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loginAccount;
	private String code;
	private boolean checkState;
	private Long time;
	
	public static CheckCodeBean generateCheckCode(String loginAccount){
		CheckCodeBean code = new CheckCodeBean();
		code.setLoginAccount(loginAccount);
		Random random = new Random();
		String sRand="";
		for (int i = 0;i < 4;i++){
			sRand+=String.valueOf(random.nextInt(10));
		}
		sRand = sRand.replaceAll("4", "6");
		code.setCode(sRand);
		code.setTime(System.currentTimeMillis());
		code.setCheckState(false);
		return code;
	}
	
	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isCheckState() {
		return checkState;
	}
	public void setCheckState(boolean checkState) {
		this.checkState = checkState;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}
