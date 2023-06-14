package com.samton.common.keygen;

public class KeyGenerator {

    public static String allEnterpriseKey() {
        return "IBEN_ALL_ENTERPRISEIDS";
    }
    
	public static String generateTokenKey(String token){
		return "USER_TOKEN_" + token;
	}
	
	/**
	 * api对应的token
	 * @return
	 */
	public static String getApiKeyToken() {
		return "APIKEY_TOKEN";
	}
    
	public static String generateMenusRequestPathKey(Long userId){
		return "REQUEST_PATH_" + userId ;
	}
	
	public static String generateResetPwdCode(String loginName){
		return "RESET_PWD_CODE_" + loginName;
	}
	
	public static String projectPwd(){
		return "PROJECT_PWD";
	}

	/**
	 * 获取所有停用词
	 * @return
	 */
	public static String getAllStopWords() {
		return "ALL_STOP_WORDS";
	}
}
