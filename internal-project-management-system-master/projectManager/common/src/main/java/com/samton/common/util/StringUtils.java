
package com.samton.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/**
	 * 手机号验证
	 * @param phoneNum
	 * @return
	 */
	public static boolean checkPhoneNum(String phoneNum) {
//		if(phoneNum == null){
//			return false;
//		}
//		Pattern pattern = Pattern.compile("^(130|131|132|133|134|135|136|137|138|139|145|147|150|151|152|153|155|156|157|158|159|170|176|177|178|180|181|182|183|184|185|186|187|188|189)\\d{8}$");
//		Matcher matcher = pattern.matcher(phoneNum);
//		return matcher.matches();
		return checkPhoneNumNew(phoneNum);
	}

	/**
	 * 判断手机号（1开头的11位数字）
	 * 
	 * @param phoneNum
	 * @return
	 */
	public static boolean checkPhoneNumNew(String phoneNum) {
		String pattern = "^1[\\d]{10}";
		boolean isMatch = Pattern.matches(pattern, phoneNum);
		return isMatch;
	}
	
	/**
	 * 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		if(email == null){
			return false;
		}
		Pattern pattern = Pattern.compile("^(\\w-*\\.*)+@(\\w-?)+(\\.\\w{2,})+$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * 验证正整数
	 * @param email
	 * @return
	 */
	public static boolean checkNumber(String number) {
		if(number == null){
			return false;
		}
		Pattern pattern = Pattern.compile("^\\+?[1-9][0-9]*$");
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}
	
	/**
	 * 验证是否包含特殊字符
	 * @param str
	 * @return
	 */
	public static boolean checkCharacter(String str) {
		if(str == null){
			return false;
		}
		 String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
	     Pattern pattern = Pattern.compile(regEx);
	     Matcher matcher = pattern.matcher(str);
	     return matcher.matches();
	}
	
	/**
	 * 根据正则表达式验证
	 * @param str
	 * @param checkPattern
	 * @return
	 */
	public static boolean checkPattern(String str, String checkPattern) {
		if(str == null || checkPattern == null){
			return false;
		}
		Pattern pattern = Pattern.compile(checkPattern);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 根据正则表达式验证
	 * find()方法是部分匹配
	 * matches()是全部匹配
	 * @param str
	 * @param checkPattern
	 * @return
	 */
	public static boolean checkIsContainsPattern(String str, String checkPattern) {
		if(str == null || checkPattern == null){
			return false;
		}
		Pattern pattern = Pattern.compile(checkPattern);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}
	/**
     * 判断是否含有特殊字符(","除外)
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
    	if(str.contains("-")){
    		 return true;
    	}
        String regEx = "[ _`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    
    /**
     * 将数字字符串转为数字大写，例如： 11转变为十一
     * @param str
     * @return
     */
    public static String toChinese(String str) {
    	if(str == null) {
    		return str;
    	}
    	String newStr = str;
    	String ch = "";
    	boolean flag = false;
    	while (newStr.startsWith("0")) {
    		ch += "零";
    		newStr = newStr.substring(1);
    	}
    	if(newStr.length() > 0) {
    		try {
    			int number = Integer.parseInt(newStr);
    			ch += BaseUtil.int2chineseNum(number);
    		}catch (Exception e) {
    			flag = true;
    		}
    	}
    	if(flag) {
    		ch = "";
    		String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
    		String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };
    		int n = str.length();
    		for (int i = 0; i < n; i++) {
    			int num = str.charAt(i) - '0';
    			if (i != n - 1 && num != 0) {
    				ch += s1[num] + s2[n - 2 - i];
    			} else {
    				ch += s1[num];
    			}
    		}
    	}
    	Integer integer = Integer.valueOf(str);
    	if(integer < 20 && integer > 9) {
    		ch = ch.substring(1);
    	}
    	return ch;
//	    String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
//	    String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };
//	    String result = "";
//	    int n = str.length();
//	    for (int i = 0; i < n; i++) {
//	        int num = str.charAt(i) - '0';
//	        if (i != n - 1 && num != 0) {
//	            result += s1[num] + s2[n - 2 - i];
//	        } else {
//	            result += s1[num];
//	        }
//	    }
//	    Integer integer = Integer.valueOf(str);
//	    if(integer < 20 && integer > 9) {
//	    	result = result.substring(1);
//	    }
//	    return result;
	}
   
    /**
     * 从字符串中提取出所有的数字并放在集合中，例如我有99个人和88个水果，结果是list集合[99,88]
     * @param str
     * @param max
     * @return
     */
    public static List<String> getList(String str){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++){
            if(Character.isDigit(str.charAt(i)))
                sb.append(str.charAt(i));
            else
                sb.append(",");
        }
        String[] strs = sb.toString().split(",");
        List<String> list = new ArrayList<String>();
        BigDecimal bigDecimal = null;
        BigDecimal maxbigDecimal = new BigDecimal(100000l);
        for(int i = 0; i < strs.length; i++){
            if(strs[i].trim().length() > 0){
                bigDecimal = new BigDecimal(strs[i]);
                if(bigDecimal.compareTo(maxbigDecimal) == -1)
                    list.add(strs[i].trim());
            }
        }
        return list;
    }
    
    /**
     * 把字符串中所有的数字转为大写，例如：我有99个人和88个水果，转换后变为我有九十九个人和八十八个水果
     * @param str
     * @return
     */
    public static String regString(String str) {
    	List<String> list = getList(str);
    	String all = str;
    	for (String string : list) {
    		String chinese = toChinese(string);
    		all = all.replaceAll(string, chinese);
		}
    	return all;
    }
	 public static void main(String[] args) {
		 //String string = regString("我有12个朋友和88个西瓜,还有19个苹果");
//		 System.out.println(isSpecialChar("nihao,，"));
		 String regString = "代号是000003，002090年我想问一下百佳物业在12月份的办公费用是多少钱";
		 regString = "百佳物业2019年11月份业务招待费多少钱";
		 regString = StringUtils.regString(regString);
		 System.out.println(regString);
		 System.out.println(toChinese("2019"));
	 }
	 
	 /**
	     * 去除字符串中所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
	     * @param s
	     * @return
	     */
	    public static String removeAllBlank(String s){
	        String result = "";
	        if(null!=s && !"".equals(s)){
	            result = s.replaceAll("[　*| *| *|//s*]*", "");
	        }
	        return result;
	    }
	 
	    /**
	     * 去除字符串中头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
	     * @param s
	     * @return
	     */
	    public static String myTrim(String s){
	        String result = "";
	        if(null!=s && !"".equals(s)){
	            result = s.replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", "");
	        }
	        return result;
	    }

	/**
	 * 判断身份证号
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean checkIDCard(String idCard) {
		String pattern = "\\d{18,18}|\\d{17,17}x|\\d{17,17}X";
		boolean isMatch = Pattern.matches(pattern, idCard);
		return isMatch;
	}
	
	/**
	 * 判断身份证号
	 * 
	 * @param idCard
	 * @return
	 */
	public static String getRandomNumber(int length) {
		if(length <= 0) {
			return null;
		}
		Random random = new Random();
		String sRand = "";
		for (int i=0; i<length; i++){
			sRand+=String.valueOf(random.nextInt(10));
		}
		return sRand;
	}
	
}

