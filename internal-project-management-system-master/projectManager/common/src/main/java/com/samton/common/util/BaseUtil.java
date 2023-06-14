package com.samton.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaseUtil {
	//复姓
	public static final List<String> COMPOUND_SURNAMES = new ArrayList<String>(Arrays.asList(new String[]{"欧阳", "太史", "端木", "上官", "司马", "东方", "独孤", "南宫", "万俟", "闻人", "夏侯", "诸葛", "尉迟", "公羊", "赫连", "澹台", "皇甫", "宗政", "濮阳", "公冶", "太叔", "申屠", "公孙", "慕容", "仲孙", "钟离", "长孙", "宇文", "司徒", "鲜于", "司空", "闾丘", "子车", "亓官", "司寇", "巫马", "公西", "颛孙", "壤驷", "公良", "漆雕", "乐正", "宰父", "谷梁", "拓跋", "夹谷", "轩辕", "令狐", "段干", "百里", "呼延", "东郭", "南门", "羊舌", "微生", "公户", "公玉", "公仪", "梁丘", "公仲", "公上", "公门", "公山", "公坚", "左丘", "公伯", "西门", "公祖", "第五", "公乘", "贯丘", "公皙", "南荣", "东里", "东宫", "仲长", "子书", "子桑", "即墨", "淳于", "达奚", "褚师", "吴铭", "纳兰", "归海"}));
	public static final String APPCODE = "ac46d7d0931d435491a94a1ffad89a37";
	
	/**
	 * 最大公约数
	 * @author xielinqi
	 * @date 2017年12月5日 下午2:35:55
	 * @param x
	 * @param y
	 * @return
	 */
	public static int maxGYS(int x, int y) { // 这个是运用辗转相除法求 两个数的 最大公约数
		if (y == 0)
			return x;
		else
			return maxGYS(y, x % y);
	}

	/**
	 * 字符长度（中文占两个字符，英文点1个字符）
	 * @author xielinqi
	 * @date 2017年12月20日 下午2:55:59
	 * @param value
	 * @return
	 */
	public static int String_length(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}
	
	public static int makeRandom(int start, int end) {
		int random = 0;
		if(start <= end) {
			random = (int) (Math.random() * (end - start + 1)) + start;
		}
		return random;
	}
	
	/**
     * 半角转全角
     * @param input String.
     * @return 全角字符串.
     */
	public static String ToSBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

    /**
     * 全角转半角
     * @param input String.
     * @return 半角字符串
     */
	public static String ToDBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);

			}
		}
		String returnString = new String(c);
		return returnString;
	}

	/**
	 * 获取姓氏
	 * @param name
	 * @return
	 */
	public static String getSurname(String name) {
		String surname = "";
		if(name != null) {
			name = name.replaceAll(" ", "");
			if(name.length() > 2) {
				for(String compoundSurname : COMPOUND_SURNAMES) {
					if(name.indexOf(compoundSurname) == 0) {
						surname = compoundSurname;
					}
				}
			}
			if(StringUtils.isBlank(surname) && name.length() > 0) {
				surname = name.substring(0,1);
			}
		}
		return surname;
	}
	/**
	 * 数字转中文
	 * @param src
	 * @return
	 */
	public static String int2chineseNum(int src) {
        final String num[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        final String unit[] = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        String dst = "";
        int count = 0;
        while(src > 0) {
            dst = (num[src % 10] + unit[count]) + dst;
            src = src / 10;
            count++;
        }
        return dst.replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
                .replaceAll("零+", "零").replaceAll("零$", "");
    }
	
//	/**
//	 * 根据ip查询城市
//	 * @param ip
//	 * @return
//	 */
//	public static JSONObject getCityInfoByIp(String ip) {
//		JSONObject info = null;
//		String host = "http://saip.market.alicloudapi.com";
//        String path = "/ip";
//        String method = "GET";
//        Map<String, String> headers = new HashMap<String, String>();
//        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + APPCODE);
//        Map<String, String> querys = new HashMap<String, String>();
//        querys.put("ip", ip);
//
//        try {
//          /**
//          * 重要提示如下:
//          * HttpUtils请从
//          * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//          * 下载
//          *
//          * 相应的依赖请参照
//          * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//          */
//          HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//          HttpEntity entity = response.getEntity();
//	  		if (entity != null) {
//	  			String returnStr = EntityUtils.toString(entity,"UTF-8");
//	  			info = JSONObject.fromObject(returnStr);
//	  		}
//        } catch (Exception e) {
//          e.printStackTrace();
//        }
//        return info;
//    }
//	
//	/**
//	 * 根据ip查询城市
//	 * @param ip
//	 * @return
//	 */
//	public static String getCityByIp(String ip) {
//		JSONArray json = new JSONArray();
//		JSONObject info = getCityInfoByIp(ip);
//		if(info != null) {
//			if(info.has("showapi_res_body")) {
//				JSONObject showapi_res_body = info.getJSONObject("showapi_res_body");
//				if(showapi_res_body.has("country")) {
//					json.add(showapi_res_body.getString("country"));
//				}else {
//					json.add("");
//				}
//				if(showapi_res_body.has("region")) {
//					json.add(showapi_res_body.getString("region"));
//				}else {
//					json.add("");
//				}
//				if(showapi_res_body.has("city")) {
//					json.add(showapi_res_body.getString("city"));
//				}else {
//					json.add("");
//				}
//				json.add("");
//				json.add("");
//			}
//		}
//		System.out.println("获取城市new：" + json.toString());
//		return json.toString();
//    }
	
	/**
	 * 
	 * @Description 中文1-10转数字
	 * @author libo
	 * @param number
	 * @return
	 * String
	 */
	public static String chindesNumToInt(String number) {
        final String num[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        for (int i = 0; i < number.length(); i++) {
        	for (int j = 0; j < num.length; j++) {
				if(number.substring(i, i + 1).equals(num[j]) && number.indexOf("航站楼") != -1) {
					number = number.replace(num[j], j + "");
				}
			}
		}
        return number;
    }
	
	/**
	 * 数字转英文
	 * @param number
	 * @return
	 */
	public static String numToDiJi(int number) {
        String diji = number + "";
        //取最后一位数字
        String str = (diji).substring((diji).length()-1);
        if("1".equals(str)) {
        	diji += "st";
        }else if("2".equals(str)) {
        	diji += "nd";
        }else if("3".equals(str)) {
        	diji += "rd";
        }else {
        	diji += "th";
        }
        
        return diji;
    }
	
	/**
	 * 对字符串按长度大小进行排序
	 * @param strs
	 * @param sort desc：倒序，其他：升序
	 * @return
	 */
    public static JSONArray sortFromJsonArray(JSONArray array, String sort) {
    	if(array == null || array.size() < 2){
    		return array;
    	}
    	List<String> collection = (List<String>) JSONArray.toCollection(array);
		List<String> list = sortFromList(collection, sort);
		return JSONArray.fromObject(list);
	}
    
    /**
	 * 对字符串按长度大小进行排序
	 * @param strs
	 * @param sort desc：倒序，其他：升序
	 * @return
	 */
    public static List<String> sortFromList(List<String> strs, String sort) {
    	
    	if(strs == null){
    		return new ArrayList<String>();
    	}else if(strs.size() < 2){
    		return strs;
    	}
    	
		long startTime = System.currentTimeMillis();
		String[] arr = new String[strs.size()];
		strs.toArray(arr);
		for (int i = 0; i < arr.length - 1; i++) {// 外层循环控制排序趟数
			for (int j = 0; j < arr.length - 1 - i; j++) {// 内层循环控制每一趟排序多少次
				if("desc".equals(sort)){//按字符串长度倒序
					if (arr[j].length() < arr[j + 1].length()) {
						String temp = arr[j];
						arr[j] = arr[j + 1];
						arr[j + 1] = temp;
					}
				}else{
					if (arr[j].length() > arr[j + 1].length()) {
						String temp = arr[j];
						arr[j] = arr[j + 1];
						arr[j + 1] = temp;
					}
				}
			}
		}
		strs = new ArrayList<String>(Arrays.asList(arr));
    	long endTime = System.currentTimeMillis();
		return strs;
	}
	
	
	public static void main(String[] args) {
//		String surname = int2chineseNum(2020);
//		String regString = "F2009我想问一下百佳物业在9月份的办公费用是多少钱代号是0103";
//		regString = com.samton.common.string.StringUtils.regString(regString);
//		System.out.println(regString);
//		System.out.println(getCityByIp("123.118.201.23"));
//		System.out.println(chindesNumToInt("地铁酒吧是一号线十二十"));
		System.out.println(numToDiJi(23));
		
	}

}
