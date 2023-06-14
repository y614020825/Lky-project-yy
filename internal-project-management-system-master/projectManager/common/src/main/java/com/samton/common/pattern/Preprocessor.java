package com.samton.common.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preprocessor {

    private static final String pattern = "[\\u4e00-\\u9fa5_a-zA-Z0-9 ]+";
    
    private static final String shu = "^\\d+$";
    
    private static final String abc = "^[a-zA-Z;]+$";
    
    private static final String abcAndShu = "^(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]+$";
    
    

    private static final String[] filters = {",", "，", "<", ">", "《", "》", ".", "。", "/", "!", "！", "、", "$", "?", "？", "~", "～",
            ":", "：", "#", "@", "%", "(", ")", "（", "）", "\"", "“", "”"};

    private static final String[] filters2 = {"它们的", "我们的", "你们的", "他们的", "她们的", "它们", "我们", "你们", "他们", "她们", "我的", "你的", "他的",
            "她的", "它的", "我", "你", "他", "她", "它", "想知道", "不知道", "知道", "怎么办", "应该", "该", "想问一下", "能问一下", "问一下",
            "如果", "能告诉", "告诉", "想", "能否", "能不能", "应该", "啊", "呀", "吗", "呗", "嗷", "哦", "噢"};

    /**
     * 过滤所有汉字、英文、数字以外的字符,以逗号分割符合条件各个字符组
     * @param str
     * @return 过滤的字符串
     */
    public String filterInvalidCharacters(String str) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        StringBuilder sb = new StringBuilder();

        while (m.find()) {
            sb.append("," + m.group());
        }

        if (sb.length() > 0) {
            return sb.delete(0, 1).toString();
        }

        return "";
    }

    public String process(String s) {
        return this.process(s, false);
    }

    public String process(String s, boolean isRemoveSymbolOnly) {
        s = s.replaceAll("\\s+|,|\\?|，|？|\\\\|/", "");
        s = s.replaceAll("\\(", "");
        s = s.replaceAll("\\)", "");

        for (String e : filters) {
            s = s.replace(e, "");
        }

        if (isRemoveSymbolOnly) {
            return s;
        }

        for (String e : filters2) {
            s = s.replace(e, "");
        }

        return s;
    }
    
    public static boolean isShuOrAbc(String str){
    	if(str.matches(abc)||str.matches(shu)||str.matches(abcAndShu)){
    		return true;
    	}
    	return false;
    }
    
    public static boolean isNumber(String str){
    	if(str.matches(shu)){
    		return true;
    	}
    	return false;
    }
    
    public static boolean isEnglish(String str){
    	if(str.matches(abc)){
    		return true;
    	}
    	return false;
    }
    
    public static void main(String[] a) {
//        String s = new Preprocessor().filterInvalidCharacters("we我f");
    	System.out.println(isShuOrAbc("魂牵梦萦魂牵梦萦"));
    }
}
