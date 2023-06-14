package com.samton.base.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.samton.common.constant.ExpCodeConstant;

//业务级别按模块自己划分，第一位数值位为moduleId，moduleId参考t_crm_sys_module表，1\2\3\4\5\6\7\8\9依次递增
//code首位以9开头
@Component
public class PmExpCodeConstant extends ExpCodeConstant{
	
	public static final String PM_SESSION_EXPIRE="901";
	public static final String PM_ACCESS_DENIED="902";
	public static final String NO_REGISTER_CHECK_CODE="903";
	public static final String CHECK_CODE_INVALID="904";
	public static final String GENERATE_CHECK_CODE_INTERVAL_TOO_SHORT="905";
	public static final String PICTURE_CHECK_CODE_ERROR="906";
	public static final String LOGIN_NAME_IS_NOT_UNIQUE="907";
	public static final String LOGIN_NAME_OR_PWD_ERROR="908";
	public static final String USER_IS_LOCK="909";
	public static final String NO_LOGIN_NAME="910";
	public static final String CHECK_CODE_ERROR="911";
	public static final String ORIGINAL_PWD_ERROR="912";
	public static final String USER_NOT_ACTIVITY="913";
	public static final String OPEN_ID_AREADY_CONNECT="914";
	public static final String AREADY_CONNECT="915";
	public static final String PM_ACCESS_EDIT="916";
	public static final String PM_ACCESS_DEL="917";
	public static final String NO_CHECK_CODE="918"; 
	public static final String ERROR_CHECK_CODE="919";
	public static final String ENTERPRISE_NULL="920"; 
	public static final String ENTERPRISE_SERVICE_NULL="921"; 
	public static final String ENTERPRISE_SERVICE_TIME_OUT="922";
	public static final String TWO_PWD_DEF="923";
	public static final String ERROR_PARAMS="924";
	public static final String EXITS_NAME="925";
	public static final String ROLE_NULL="926";
	public static final String NO_CHOSE_USER="927";
	public static final String ENTERPRISE_NAME_NULL="928";
	public static final String RESGESROT_FAIL="929";
	public static final String OTHER_ENTERPRISE="930";
	public static final String NEED_ADMIN_REGIST="931";
	
	public static final String GENERATE_SECRPT_FAIL="932";
	public static final String GENERATE_SECRPT_NO_ENTERPRISEID="933";
	public static final String LOGININ_EXCEPTION="934";
	
	public static final String RECEPTIONCOUNTERROR="935";
	
	public static final String FILE_UNFORMAT="936";
	public static final String FILE_TOO_LARGE="937";
	public static final String USERNAME_NULL="938"; 
	public static final String LOGINTYPE_NULL="939"; 
	
	public static final String ACCOUNT_NULL="940"; 
	public static final String PASSWD_NULL="941"; 
	public static final String ENTERPRISENAME_NULL="942"; 
	public static final String EPHONENUM_NULL="943"; 
	public static final String INDUSTRY_NULL="944"; 
	public static final String USERNAME_TOLANG="945"; 
	public static final String ENTERPRISENAME_TOLANG="946"; 
	public static final String NEW_PWD_NULL="947";
	public static final String NEW_PWD_ERROR="948";
	public static final String NAME_NULL="949";
	public static final String LOGIN_NAME_NULL="950";
	public static final String LOGIN_TYPE_ERROR="951";
	public static final String NAME_TOOLONG="952";
	public static final String PHONENUM_NULL="953";
	public static final String PHONENUM_WRONG = "954";
	public static final String EMAIL_WRONG = "955";
	public static final String EMAIL_TOOLONG = "956";
	public static final String RECEPTION_COUNT_NULL = "957";
	public static final String RECEPTION_COUNT_NO_NUMBER = "958";
	public static final String DELETE_USER_QYGLY = "959";
	public static final String UPDATE_USER_QYGLY = "960";
	public static final String IS_PASS_WORD = "961";
	public static final String PARAM_IS_UNFORMAT = "962";
	public static final String PWD_IS_WRONG = "970";
	public static final String LOGIN_TYPE_IS_NULL = "971";
	public static final String PWD_ERROR="972";
	
	public static boolean isPassWord(String str){
		String regex =  "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
		return match(regex, str);
		
	}
	
	 private static boolean match(String regex, String str) {
		 Pattern pattern = Pattern.compile(regex);   
	        Matcher matcher = pattern.matcher(str);   
	        return matcher.matches();   
	}
	
	
	static{
		msgMap.put(ERROR_PARAMS, "参数错误");
		msgMap.put(EXITS_NAME, "用户名已存在");
		msgMap.put(ROLE_NULL, "请选择角色信息");
		msgMap.put(PM_SESSION_EXPIRE, "登录超时，请重新登录");
		msgMap.put(PM_ACCESS_DENIED, "抱歉，您没有操作权限");
		msgMap.put(PM_ACCESS_EDIT, "抱歉，您没有编辑权限");
		msgMap.put(PM_ACCESS_DEL, "抱歉，您没有删除权限");
		msgMap.put(NO_CHOSE_USER, "未找到要修改的用户信息");
		
		msgMap.put(NO_REGISTER_CHECK_CODE, "未生成注册验证码");
		msgMap.put(CHECK_CODE_INVALID, "验证码超时，请重新获取");
		msgMap.put(GENERATE_CHECK_CODE_INTERVAL_TOO_SHORT, "请在一分钟后再重新获取验证码");
		msgMap.put(PICTURE_CHECK_CODE_ERROR, "图片识别码错误");
		msgMap.put(LOGIN_NAME_IS_NOT_UNIQUE, "该用户已注册");
		msgMap.put(LOGIN_NAME_OR_PWD_ERROR, "您的用户名或密码输入有误，请重新输入");
		msgMap.put(USER_IS_LOCK, "用户被锁定，请联系管理员！");
		msgMap.put(NO_LOGIN_NAME,"当前账号未注册");
		msgMap.put(CHECK_CODE_ERROR, "请输入正确的验证码");
		msgMap.put(ORIGINAL_PWD_ERROR, "原密码错误");
		msgMap.put(USER_NOT_ACTIVITY, "用户未激活，请联系管理员！");
		msgMap.put(NO_CHECK_CODE, "未生成验证码！");
		msgMap.put(ERROR_CHECK_CODE, "验证码不正确！");
		msgMap.put(ENTERPRISE_NULL, "未找到企业信息！");
		msgMap.put(ENTERPRISE_SERVICE_NULL, "企业未开通服务！");
		msgMap.put(ENTERPRISE_SERVICE_TIME_OUT, "企业服务过期！");
		msgMap.put(TWO_PWD_DEF, "两次密码不相同！");
		msgMap.put(ENTERPRISE_NAME_NULL, "请输入企业名称！");
		msgMap.put(RESGESROT_FAIL, "注册用户失败！");
		msgMap.put(OTHER_ENTERPRISE, "该账号已在被其他企业注册!");
		msgMap.put(NEED_ADMIN_REGIST, "请联系您的管理员给你注册账号!");
		msgMap.put(GENERATE_SECRPT_FAIL, "数据生成失败，请重试！");
		msgMap.put(GENERATE_SECRPT_NO_ENTERPRISEID, "生成异常，请检查用户是否登录！");
		msgMap.put(LOGININ_EXCEPTION, "登录异常，请重新登录!");
		msgMap.put(RECEPTIONCOUNTERROR, "坐席接待数超过企业坐席最大接待数！");
		msgMap.put(FILE_UNFORMAT, "文件格式不正确");
		msgMap.put(FILE_TOO_LARGE, "图片过大，请上传小于8M的图片");
		msgMap.put(USERNAME_NULL, "用户名为空");
		msgMap.put(USERNAME_TOLANG, "用户名不能超过20个字符");
		msgMap.put(ENTERPRISENAME_TOLANG, "企业名称不能超过20个字符");
		msgMap.put(LOGINTYPE_NULL, "登录类型为空");
		msgMap.put(ACCOUNT_NULL, "登录账号为空");
		msgMap.put(PASSWD_NULL, "登录密码为空");
		msgMap.put(ENTERPRISENAME_NULL, "企业名称为空");
		msgMap.put(EPHONENUM_NULL, "企业电话为空");
		msgMap.put(INDUSTRY_NULL, "行业类型为空");
		msgMap.put(NEW_PWD_NULL, "新密码不能为空");
		msgMap.put(NEW_PWD_ERROR, "新密码格式错误");
		msgMap.put(NAME_NULL, "用户名不能为空");
		msgMap.put(LOGIN_NAME_NULL, "登录账号不能为空");
		msgMap.put(LOGIN_TYPE_ERROR, "登录方式填写错误");
		msgMap.put(NAME_TOOLONG, "用户名不能超过20个字");
		msgMap.put(PHONENUM_NULL, "手机号不能为空");
		msgMap.put(PHONENUM_WRONG, "请输入正确的手机号");
		msgMap.put(EMAIL_WRONG, "请输入正确的邮箱");
		msgMap.put(EMAIL_TOOLONG, "邮箱不能超过30个字");
		msgMap.put(RECEPTION_COUNT_NULL, "坐席接待数不能为空");
		msgMap.put(RECEPTION_COUNT_NO_NUMBER, "请输入正确的坐席接待数");
		msgMap.put(DELETE_USER_QYGLY, "企业管理员不允许删除");
		msgMap.put(UPDATE_USER_QYGLY, "企业管理员不允许修改");
		msgMap.put(IS_PASS_WORD, "密码由6-18位的字母和数字组成，必须包含一个字母和数字， 不能由纯数字或字母组成。");
		msgMap.put(PARAM_IS_UNFORMAT,"参数绑定异常，请检查参数格式是否正确！1.请检查数字类型的参数是否传入了非数字类型（如汉字，字母等）或超过了数据类型的表示范围2.请检查日期格式是否正确，日期格式为 yyyy-MM-dd 并且日期要符合逻辑，不能出现2017-02-31这种逻辑错误");
		msgMap.put(PWD_IS_WRONG, "原密码输入错误，请重新输入");
		msgMap.put(LOGIN_TYPE_IS_NULL, "请选择登录类型");
		msgMap.put(PWD_ERROR, "您的密码输入有误，请重新输入");
	}
	
}
