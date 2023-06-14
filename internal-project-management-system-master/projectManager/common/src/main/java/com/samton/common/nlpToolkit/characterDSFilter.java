package com.samton.common.nlpToolkit;

import com.samton.common.pattern.Preprocessor;

/**
 * Created by djw on 2019/2/20.
 */
public class characterDSFilter {

    /**
     * 过滤出中文
     * @param chin
     * @return
     */
    public static String filterChinese(String chin)
    {
        chin = chin.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        return chin;
    }

    /**
     * 过考虑出字母
     * @param alph
     * @return
     */
    public static String filterCharacter(String alph)
    {
        alph = alph.replaceAll("[^(A-Za-z)]", "");
        return alph;
    }

    /**
     * 过滤出数字
     * @param number
     * @return
     */
    public static String filterNumber(String number)
    {
        number = number.replaceAll("[^(0-9)]", "");
        return number;
    }


    /**
     * 过滤出中文，字母，数字
     * @param character
     * @return
     */
    public static String filterAll(String character){
        character = character.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "");
        return character;
    }
    
    /**
     * 去除问题中的非法字符
     * @param question
     * @return
     */
    public static String getCleanedSentence(String question){
    	
    	//如果是英文，不去除空格。如果是中文则去除空格
    	if(Preprocessor.isEnglish(question)){
    		
    	}else{
    		return question.replaceAll(" ","");
    	}
    	
    	return question;
    }
    
    
    public static  String excelFilter(String orignStr){
		for(int i=10;i<14;i++)
		{
			orignStr = orignStr.replaceAll(String.valueOf((char)i), "");
		}
		return orignStr;
    }
    
    public static boolean isPureEnglishStr(String orignStr){
    	return orignStr.replaceAll(" ","").matches("[a-zA-Z]+");
    }
    

    public static void main(String [] args){
        System.out.println(filterChinese("cd &你好北京 *词 2 "));
        System.out.println(filterCharacter("cd &你好北京 *词 2 "));
        System.out.println(filterNumber("cd &你好北京 *词 2 "));
        System.out.println(filterAll("个人填报住房贷款相关信息时，“是否婚前各自首套贷款，且婚后分别扣除50%”是什么意思？我该如何填写该栏？"));
        System.out.println(isPureEnglishStr("vu hbjnk"));
    }
}
