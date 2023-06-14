package com.samton.base.constant;

import com.samton.common.constant.ExpCodeConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BaseConstant extends ExpCodeConstant{
	public static final List<String> imgList = Arrays.asList(new String[]{"bmp","jpg","jpeg","png"});
	//图片大小，单位M
	public static final int IMG_SIZE = 8;
	
 	//角色管理--------------------------------------------------start
  	public static final String ROLE_ID_IS_NULL = "1001";
  	public static final String ROLE_ID_IS_NULL2 = "1002";
  	public static final String ROLE_NAME_IS_NULL = "1003";
  	public static final String ROLE_NAME_IS_TOO_LONG = "1004";
  	public static final String ROLE_DATA_AUTH_IS_NULL = "1005";
  	public static final String ROLE_DESCRIPTION_IS_TOO_LONG = "1006";
  	public static final String ROLE_DEL_FAIL_BECAUSE_HAS_BEEN_USED = "1007";
     static{
 		msgMap.put(ROLE_ID_IS_NULL, "角色id不能为空");
 		msgMap.put(ROLE_ID_IS_NULL2, "请选择角色");
 		msgMap.put(ROLE_NAME_IS_NULL, "角色名称不能为空");
 		msgMap.put(ROLE_NAME_IS_TOO_LONG, "角色名称不能超过20个字");
 		msgMap.put(ROLE_DATA_AUTH_IS_NULL, "请选择数据权限");
 		msgMap.put(ROLE_DESCRIPTION_IS_TOO_LONG, "角色描述不能超过100个字");
 		msgMap.put(ROLE_DEL_FAIL_BECAUSE_HAS_BEEN_USED, "该角色已被用户使用，不可删除");
 	}
 	//角色管理--------------------------------------------------end

 	//部门管理--------------------------------------------------start
     public static final String DEPT_ID_IS_NULL = "2001";
     public static final String DEPT_ID_IS_NULL2 = "2002";
     public static final String DEPT_DOES_NOT_EXIST = "2003";
     public static final String DEPT_NAME_IS_NULL = "2004";
     public static final String DEPT_NAME_IS_TOO_LONG = "2005";
     public static final String DEPT_PARENT_ID_IS_NULL = "2006";
     public static final String DEPT_PARENT_CATALOG_DOES_NOT_EXIST = "2007";
     public static final String DEPT_PARENT_CANNOT_BE_SELF = "2008";
     public static final String DEPT_SORT_DATAS_IS_WRONG = "2009";
     static{
 		msgMap.put(DEPT_ID_IS_NULL, "部门id不能为空");
 		msgMap.put(DEPT_ID_IS_NULL2, "请选择部门");
 		msgMap.put(DEPT_DOES_NOT_EXIST, "部门不存在，请确认是否已删除");
 		msgMap.put(DEPT_NAME_IS_NULL, "部门名称不能为空");
 		msgMap.put(DEPT_NAME_IS_TOO_LONG, "部门名称不能超过20个字");
 		msgMap.put(DEPT_PARENT_ID_IS_NULL, "请选择上级部门");
 		msgMap.put(DEPT_PARENT_CATALOG_DOES_NOT_EXIST, "上级部门不存在，请确认是否已删除");
 		msgMap.put(DEPT_PARENT_CANNOT_BE_SELF, "上级部门不能是自己");
 		msgMap.put(DEPT_SORT_DATAS_IS_WRONG, "部门排序数据格式有误");
 	}
 	//部门管理--------------------------------------------------end

  	//用户管理--------------------------------------------------start
   	public static final String USER_ID_IS_NULL = "3001";
   	public static final String USER_DOES_NOT_EXIST = "3002";
   	public static final String USER_LOGIN_ACCOUNT_IS_NULL = "3003";
   	public static final String USER_LOGIN_ACCOUNT_IS_WRONG = "3004";
   	public static final String USER_LOGIN_ACCOUNT_ALREADY_EXISTS = "3005";
   	public static final String USER_FULL_NAME_IS_NULL = "3006";
   	public static final String USER_FULL_NAME_IS_TOO_LONG = "3007";
   	public static final String USER_PASSWD_IS_NULL = "3007";
   	public static final String USER_PASSWD_IS_WRONG = "3008";
      static{
  		msgMap.put(USER_ID_IS_NULL, "销售人员id不能为空");
 		msgMap.put(USER_DOES_NOT_EXIST, "销售人员不存在，请确认是否已删除");
  		msgMap.put(USER_LOGIN_ACCOUNT_IS_NULL, "登录账号不能为空");
  		msgMap.put(USER_LOGIN_ACCOUNT_IS_WRONG, "登录账号格式有误（30位以内的英文数字组合，必须包含英文）");
 		msgMap.put(USER_LOGIN_ACCOUNT_ALREADY_EXISTS, "登录账号已被占用");
  		msgMap.put(USER_FULL_NAME_IS_NULL, "姓名不能为空");
  		msgMap.put(USER_FULL_NAME_IS_TOO_LONG, "姓名不能超过20个字");
		msgMap.put(USER_PASSWD_IS_NULL, "密码不能为空");
		msgMap.put(USER_PASSWD_IS_WRONG, "密码只能由6-18位的字母和数字组成");
  	}
  	//用户管理--------------------------------------------------end

	//代理商管理--------------------------------------------------start
 	public static final String AGENT_ID_IS_NULL = "4001";
 	public static final String AGENT_IS_NULL = "4002";
 	public static final String AGENT_DOES_NOT_EXIST = "4003";
 	public static final String AGENT_DOSE_NOT_BELONG_TO_YOU = "4004";
 	public static final String AGENT_ENTERPRISE_NAME_IS_NULL = "4005";
 	public static final String AGENT_ENTERPRISE_NAME_IS_TOO_LONG = "4006";
 	public static final String AGENT_PHONE_NUMBER_IS_WRONG = "4007";
 	public static final String AGENT_CANTACT_IS_TOO_LONG = "4008";
 	public static final String AGENT_BUSINESS_LICENCE_IS_WRONG = "4009";
 	public static final String AGENT_BUSINESS_LICENCE_IS_TOO_LARGE = "4010";
 	public static final String AGENT_APPLICATION_SCENE_IS_TOO_LONG = "4011";
 	public static final String AGENT_ENTERPRISE_SCALE_IS_TOO_LONG = "4012";
 	public static final String AGENT_ENTERPRISE_LEVEL_IS_TOO_LONG = "4013";
 	public static final String AGENT_REMARK_IS_TOO_LONG = "4014";
 	public static final String AGENT_AUDIT_FAIL_PASS = "4015";
 	public static final String AGENT_AUDIT_FAIL_REJECT = "4016";
 	public static final String AGENT_AUDIT_STATUS_IS_NULL = "4017";
 	public static final String AGENT_AUDIT_STATUS_IS_WRONG = "4018";
 	public static final String LOGIN_FILE_AGENT_TO_BE_AUDIT = "4019";
 	public static final String LOGIN_FILE_AGENT_AUDIT_REJECT = "4020";
    static{
		msgMap.put(AGENT_ID_IS_NULL, "代理商id不能为空");
		msgMap.put(AGENT_IS_NULL, "请选择代理商");
		msgMap.put(AGENT_DOES_NOT_EXIST, "代理商不存在，请确认是否已删除");
		msgMap.put(AGENT_DOSE_NOT_BELONG_TO_YOU, "该代理商不在你名下，不能给其创建项目");
		msgMap.put(AGENT_ENTERPRISE_NAME_IS_NULL, "企业名称不能为空");
		msgMap.put(AGENT_ENTERPRISE_NAME_IS_TOO_LONG, "企业名称不能超过50个字");
		msgMap.put(AGENT_PHONE_NUMBER_IS_WRONG, "企业电话格式有误");
		msgMap.put(AGENT_CANTACT_IS_TOO_LONG, "联系人不能超过30个字");
		msgMap.put(AGENT_BUSINESS_LICENCE_IS_WRONG, "营业执照格式有误，支持的格式为：" + StringUtils.join(imgList, ","));
		msgMap.put(AGENT_BUSINESS_LICENCE_IS_TOO_LARGE, "营业执照不能大于" + IMG_SIZE + "M");
		msgMap.put(AGENT_APPLICATION_SCENE_IS_TOO_LONG, "应用场景不能超过30个字");
		msgMap.put(AGENT_ENTERPRISE_SCALE_IS_TOO_LONG, "企业规模不能超过30个字");
		msgMap.put(AGENT_ENTERPRISE_LEVEL_IS_TOO_LONG, "请选择企业等级");
		msgMap.put(AGENT_REMARK_IS_TOO_LONG, "备注不能超过200个字");
		msgMap.put(AGENT_AUDIT_FAIL_PASS, "审核失败，该代理商已为审核通过状态");
		msgMap.put(AGENT_AUDIT_FAIL_REJECT, "审核失败，该代理商已为审核不通过状态");
		msgMap.put(AGENT_AUDIT_STATUS_IS_NULL, "请选择审核状态");
		msgMap.put(AGENT_AUDIT_STATUS_IS_WRONG, "审核状态有误");
		msgMap.put(LOGIN_FILE_AGENT_TO_BE_AUDIT, "登录失败，代理商尚未审核");
		msgMap.put(LOGIN_FILE_AGENT_AUDIT_REJECT, "登录失败，代理商审核未通过");

	}
	//代理商管理--------------------------------------------------end

	//项目管理--------------------------------------------------start
	public static final String PROJECT_ID_IS_NULL = "5001";
	public static final String PROJECT_DOES_NOT_EXIST = "5002";
	public static final String PROJECT_NAME_IS_NULL = "5003";
	public static final String PROJECT_NAME_IS_TOO_LONG = "5004";
	public static final String PROJECT_NAME_ALREADY_EXISTS = "5005";
	public static final String PROJECT_CITY_IS_TOO_LONG = "5006";
	public static final String PROJECT_DEMAND_IS_TOO_LONG = "5007";
	public static final String PROJECT_FOLLOW_UP_IS_TOO_LONG = "5008";
	public static final String PROJECT_PHONE_NUMBER_IS_WRONG = "5009";
	public static final String PROJECT_BUDGET_IS_TOO_LONG = "5010";
	public static final String PROJECT_PRODUCT_CATEGORY_IS_TOO_LONG = "5011";
	public static final String PROJECT_CREATE_FAIL = "5012";
	public static final String PROJECT_CREATE_FAIL_HAS_EXIST_SIMILAR = "5013";
	public static final String PROJECT_CREATE_FAIL_PWD_IS_WRONG = "5014";
	public static final String PROJECT_TERMINAL_NAME_IS_TO_LONG = "5015";
	public static final String PROJECT_TERMINAL_INDUSTRY_IS_TO_LONG = "5016";
	public static final String PROJECT_TERMINAL_ADDRESS_IS_TO_LONG = "5017";
	public static final String PROJECT_MIDDLE_MAN_NAME_IS_TO_LONG = "5018";
	public static final String PROJECT_MIDDLE_MAN_INDUSTRY_IS_TO_LONG = "5019";
	public static final String PROJECT_MIDDLE_MAN_ADDRESS_IS_TO_LONG = "5020";
	public static final String PROJECT_COMMUNICATE_PERSON_IS_TO_LONG = "5021";
	public static final String PROJECT_COMMUNICATE_ROLE_IS_TO_LONG = "5022";
	public static final String PROJECT_COMMUNICATE_PERSON_PHONE_NUMBER_IS_TO_LONG = "5023";
	public static final String PROJECT_COMMERCIAL_PERSONNEL_IS_TO_LONG = "5024";
	public static final String PROJECT_PROGRESS_IS_WRONG = "5025";
	public static final String PROJECT_CONTRACT_TIME_IS_WRONG = "5026";
	public static final String PROJECT_ID_IS_NUL = "5027";
	public static final String PROJECT_ID_IS_NU = "5028";


  static{
		msgMap.put(PROJECT_ID_IS_NULL, "项目id不能为空");
		msgMap.put(PROJECT_DOES_NOT_EXIST, "项目不存在，请确认是否已删除");
		msgMap.put(PROJECT_NAME_IS_NULL, "项目名称不能为空");
		msgMap.put(PROJECT_NAME_IS_TOO_LONG, "项目名称不能超过50个字");
 		msgMap.put(PROJECT_NAME_ALREADY_EXISTS, "项目名称已存在");
		msgMap.put(PROJECT_CITY_IS_TOO_LONG, "所在城市不能超过20个字");
		msgMap.put(PROJECT_DEMAND_IS_TOO_LONG, "项目需求不能超过500个字");
		msgMap.put(PROJECT_FOLLOW_UP_IS_TOO_LONG, "跟进人不能超过30个字");
		msgMap.put(PROJECT_PHONE_NUMBER_IS_WRONG, "联系方式格式有误");
		msgMap.put(PROJECT_BUDGET_IS_TOO_LONG, "项目预算不能超过30个字");
		msgMap.put(PROJECT_PRODUCT_CATEGORY_IS_TOO_LONG, "请选择产品类别");
		msgMap.put(PROJECT_CREATE_FAIL, "项目报备失败，请联系系统管理员");
		msgMap.put(PROJECT_CREATE_FAIL_HAS_EXIST_SIMILAR, "此项目可能已报备，请联系销售管理人员（可输入项目密码强制报备该项目）");
		msgMap.put(PROJECT_CREATE_FAIL_PWD_IS_WRONG, "项目报备失败，项目密码错误");
		msgMap.put(PROJECT_TERMINAL_NAME_IS_TO_LONG, "");
		msgMap.put(PROJECT_TERMINAL_INDUSTRY_IS_TO_LONG, "");
		msgMap.put(PROJECT_TERMINAL_ADDRESS_IS_TO_LONG, "");
		msgMap.put(PROJECT_MIDDLE_MAN_NAME_IS_TO_LONG, "");
		msgMap.put(PROJECT_MIDDLE_MAN_INDUSTRY_IS_TO_LONG, "");
		msgMap.put(PROJECT_MIDDLE_MAN_ADDRESS_IS_TO_LONG, "");
		msgMap.put(PROJECT_COMMUNICATE_PERSON_IS_TO_LONG, "");
		msgMap.put(PROJECT_COMMUNICATE_ROLE_IS_TO_LONG, "");
		msgMap.put(PROJECT_COMMUNICATE_PERSON_PHONE_NUMBER_IS_TO_LONG, "");
		msgMap.put(PROJECT_COMMERCIAL_PERSONNEL_IS_TO_LONG, "");
		msgMap.put(PROJECT_PROGRESS_IS_WRONG, "");
		msgMap.put(PROJECT_CONTRACT_TIME_IS_WRONG, "");
		msgMap.put(PROJECT_ID_IS_NULL, "");
		msgMap.put(PROJECT_ID_IS_NULL, "");







	}
	//项目管理--------------------------------------------------end

	//停用词管理--------------------------------------------------start
	public static final String WORD_ID_IS_NULL = "6001";
	public static final String WORD_DOES_NOT_EXIST = "6002";

	public static final String WORD_IS_NULL = "6003";
  	public static final String WORD_IS_TOO_LONG = "6004";
  	public static final String WORD_CREATE_NAME_IS_NULL ="6005";
  	public static final String WORD_CREATE_NAME_IS_TOO_LONG = "6006";
  	public static final String WORD_ALREADY_EXISTS = "6007";
	public static final String WORD_IS_TOO_SHORT = "6008";
//	public static final String WORD_IS_TOO_SHORT1 = "6009";


//	public static final String WORD_ALREADY_EXISTS = "6005";
//	public static final String PROJECT_CITY_IS_TOO_LONG = "6007";
//	public static final String PROJECT_DEMAND_IS_TOO_LONG = "5007";
//	public static final String PROJECT_FOLLOW_UP_IS_TOO_LONG = "5008";
//	public static final String PROJECT_PHONE_NUMBER_IS_WRONG = "5009";
//	public static final String PROJECT_BUDGET_IS_TOO_LONG = "5010";
//	public static final String PROJECT_PRODUCT_CATEGORY_IS_TOO_LONG = "5011";
//	public static final String PROJECT_CREATE_FAIL = "5012";
//	public static final String PROJECT_CREATE_FAIL_HAS_EXIST_SIMILAR = "5013";
//	public static final String PROJECT_CREATE_FAIL_PWD_IS_WRONG = "5014";

  static {
  		msgMap.put(WORD_ID_IS_NULL,"停用词id不能为空");
	  	msgMap.put(WORD_DOES_NOT_EXIST,"停用词不存在，请确认已删除");
  		msgMap.put(WORD_IS_NULL,"停用词不能为空");
  		msgMap.put(WORD_IS_TOO_LONG,"停用词不能超过30个字");
	  	msgMap.put(WORD_CREATE_NAME_IS_NULL,"停用词创建人不能为空");
	  	msgMap.put(WORD_CREATE_NAME_IS_TOO_LONG,"停用词创建人不能超过255个字");
	  	msgMap.put(WORD_ALREADY_EXISTS,"【%s】无法添加，已存在【%s】");
	  	msgMap.put(WORD_IS_TOO_SHORT,"停用词不能低于2个字");
//	  	msgMap.put(WORD_IS_TOO_SHORT1,"停用词不能低于2个字%s%s");

	}
	//停用词管理--------------------------------------------------end
//	public static Map<String,Object> stopErrorMsg(String errorCode,String errorMsg,String c,String r){
//		if(errorCode=="6007"){
//			msgMap.put(WORD_ALREADY_EXISTS,"【"+c+"】"+"无法添加"+','+"已存在"+"【"+r+"】");
//		}
//		Map<String,Object> result = new HashMap<>();
//		Map<String,Object> data = new HashMap<>();
//
//		result.put("rs", -1);
//		data.put("errorCode", errorCode);
//		data.put("errorMsg", errorMsg);
//		result.put("data", data);
//		return result;
//	}


	public static Map<String,Object> returnErrorMsg(String errorCode,String errorMsg){

		Map<String,Object> result = new HashMap<>();
		Map<String,Object> data = new HashMap<>();
		result.put("rs", -1);
		data.put("errorCode", errorCode);
		data.put("errorMsg", errorMsg);
		result.put("data", data);
		return result;
	}
}
