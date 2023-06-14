package com.samton.common.nlpToolkit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.samton.common.http.HttpClientUtils;
import com.samton.common.log.LogUtil;

/**
 * Created by djw on 2018/12/20.
 */
public class bertVectorManager {
	
	public final static String CNVecPath = "http://121.40.124.169:19879";
	public final static String EngVecPath = "http://121.40.124.169:19879";

    public static void main(String [] args){
        String s1="怎么接入APP";
//        String s2="APP如何接入";
        String s2 = "百度一下你就知道";
        System.out.println("【" + s1 + "】和【" + s2 + "】的相似度为：" + caculateScore(s1, s2));
    }


    /**
     * 计算两个向量值的值
     * @param question
     * @param knowQuestion
     * @return
     */
    public static Double caculateScore(String sentence1, String sentence2){
        if(StringUtils.isBlank(sentence1) || StringUtils.isBlank(sentence2)){
            return 0D;
        }
        SentenceVectorBean questionVec1=bertVectorManager.getVector(sentence1);
        SentenceVectorBean questionVec2=bertVectorManager.getVector(sentence2);
        Double score = caculateScore(questionVec1.getSentenceVectorList(), questionVec2.getSentenceVectorList());
        return score;
    }


    /**
     * 计算两个向量值的值
     * @param question
     * @param knowQuestion
     * @return
     */
    public static Double caculateScore(List<Double> question, List<Double> knowQuestion){
        if(question==null || knowQuestion==null){
            return null;
        }
        //防止中英文维度不一致导致的数据越界
        if(question.size()!=knowQuestion.size()){
        	return null;
        }
        double score=0;
        for(int i=0;i<question.size();i++){
            score+=question.get(i)*knowQuestion.get(i);
        }
        return score;
    }

    
    /**
     * 总方法，支持中英文-JSONArray
     * @param senQuestion
     * @return
     */
    public static SentenceVectorBean getVector(String senQuestion){
    	if(isContainsChinesesWords(senQuestion)){
    		//如果是中文，用中文的模型
			return bertVectorManager.getChineseSentenceVector(senQuestion,CNVecPath);
		}else{
			//如果是英文，用英文的模型
			return bertVectorManager.getEngSentenceVector(senQuestion,EngVecPath);
		}
    	
    }
    

    public static boolean isContainsChinesesWords(String senQuestion) {
		// TODO Auto-generated method stub
    	Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	    Matcher m = p.matcher(senQuestion);
	    if (m.find()) {
	      return true;
	    }
	    return false;
	}


	/**
     * 获取单个问题的向量值-中文
	 * @param cNVecPath 
     * @param question
     * @return
     */
    private static SentenceVectorBean getChineseSentenceVector(String sentence, String cNVecPath){
    	//String chinsesVectorUrl="http://47.99.179.135:19879";
		LogUtil.infoSystemLog("请求中文向量模型:"+sentence);
    	return getSentenceVector(cNVecPath,sentence,1);
    }
    
    /**
     * 获取单个问题的向量值-英文
     * @param engVecPath 
     * @param question
     * @return
     */
    private static SentenceVectorBean getEngSentenceVector(String sentence, String engVecPath){
        //机器人正式环境向量服务
//    	String engVectorUrl="http://47.99.179.135:19879";
		LogUtil.infoSystemLog("请求英文向量模型:"+sentence);
    	return getSentenceVector(engVecPath,sentence,2);
    }

    /**
     * 获取向量值基本方法
     * @param url
     * @param question
     * @return
     */
    public static SentenceVectorBean getSentenceVector(String url,String sentence,int languageType){
        //进行预处理
    	if(languageType==1){
        	sentence=characterDSFilter.filterAll(sentence);
    	}
//    	sentence = sentence.replaceAll(" ", "").replaceAll("]", "");
        try{
        	//1.组装请求路径
            url+="/getSentenceVector";
            //2.获取向量值
//            System.out.println("获取句子向量："+url);
            HashMap params=new HashMap<String,Object>();
            params.put("sentence", sentence);
            String result = HttpClientUtils.doPost(url, params);
            //3.解析并包装返回结果
            if(result!=null){
            	JSONObject item = JSONObject.fromObject(result);
            	if(item!=null){
                	SentenceVectorBean vectorBean=new SentenceVectorBean();
            		vectorBean.setVecOrignSentence(sentence);
            		vectorBean.setVecLanguageType(languageType);

            		if(item.has("sentenceVector")){
            			JSONArray sentenceArr = item.getJSONArray("sentenceVector"); 
                		vectorBean.setSentenceVector(sentenceArr);
            		}
            		if(item.has("vecCleanedSentence")){
   	            		//获取分词后的句子
            			String vecCleanedSentence=item.getString("vecCleanedSentence");
            			vectorBean.setVecCleanedSentence(vecCleanedSentence);
            		}
            		if(item.has("vecSegSentenceInfo")){
            			//获取分词信息
                		String vecSegSentenceInfo=item.getString("vecSegSentenceInfo");
                		vecSegSentenceInfo=vecSegSentenceInfo.replaceAll("\" \",", "");
                		vectorBean.setVecSegSentenceInfo(vecSegSentenceInfo);
            		}
            		return vectorBean;
            	}
            }
        }catch(Exception e){
        	e.printStackTrace();
        	LogUtil.infoSystemLog("获取vector异常[single]:"+sentence);
        	return null;
        }
        return null;
    }

    /**
     * 获取批量vector
     * @param questionList
     * @return
     */
    public static ArrayList<JSONArray> getVectorList(String url, List<String> questionList){
        StringBuffer sb=new StringBuffer();
        for(String question:questionList){
            sb.append(question+ "[SEP]");
        }
        if(sb.length()>10){
            sb.delete(sb.length()-5,sb.length());
        }
        if(sb.length()>0){
            ArrayList<JSONArray> vectorResult= getSentenceVectorList(url,sb.toString());
            return vectorResult;
        }
        return null;
    }
    
    
    /**
     * 获取批量vector
     * @param sentenceStr
     * @return
     */
    public static ArrayList<JSONArray> getSentenceVectorList(String url,String sentenceStr){
        LogUtil.infoSystemLog("需要转换的sentence:"+sentenceStr);
        ArrayList resultVector = new ArrayList();
        try{
        	//1.组装并发送请求
            url+="/getSentencesVectors?sentenceList="+sentenceStr;
            url=url.replaceAll("\n\r", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").replaceAll(" ", "");
            String result= HttpClientUtils.doGet(url, null);

            //2.解析结果
            if(result!=null){
                JSONObject item=JSONObject.fromObject(result);
                if(item!=null && item.has("vectorList")){
                	 JSONArray vectorList=item.getJSONArray("vectorList");

                     //3.封装返回结果
                     for(int i=0;i<vectorList.size();i++){
                         resultVector.add(vectorList.get(i));
                     }
                     return resultVector;
                }
            }
        }catch(Exception e){
        	e.printStackTrace();
        	LogUtil.infoSystemLog("获取vector异常[multi]:"+sentenceStr);
        	return null;
        }
        return null;
        
    }
    
}
