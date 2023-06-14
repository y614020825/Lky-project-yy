package com.samton.common.nlpToolkit;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

/**
 * 句子向量Bean
 * @author djw
 * 2019/08/30
 *
 */

public class SentenceVectorBean {
	
	//原始句子
	private String vecOrignSentence;
	
	//去除停用词后的句子
	private String vecCleanedSentence;
	
	//分词信息（带词性信息）
	private String vecSegSentenceInfo;
	
	//句子向量
	private JSONArray sentenceVector;
	
	private List<Double> sentenceVectorList;
	
	//语言了类型  1 汉语 2 英语
	private int vecLanguageType;

	public String getVecOrignSentence() {
		return vecOrignSentence;
	}

	public void setVecOrignSentence(String vecOrignSentence) {
		this.vecOrignSentence = vecOrignSentence;
	}

	public String getVecCleanedSentence() {
		return vecCleanedSentence;
	}

	public void setVecCleanedSentence(String vecCleanedSentence) {
		this.vecCleanedSentence = vecCleanedSentence;
	}

	public String getVecSegSentenceInfo() {
		return vecSegSentenceInfo;
	}

	public void setVecSegSentenceInfo(String vecSegSentenceInfo) {
		this.vecSegSentenceInfo = vecSegSentenceInfo;
	}

	public JSONArray getSentenceVector() {
		return sentenceVector;
	}

	public void setSentenceVector(JSONArray sentenceVector) {
		this.sentenceVector = sentenceVector;
	}

	public int getVecLanguageType() {
		return vecLanguageType;
	}

	public void setVecLanguageType(int vecLanguageType) {
		this.vecLanguageType = vecLanguageType;
	}

	
	public List<Double> getSentenceVectorList(){
		sentenceVectorList=new ArrayList<Double>();
		if(sentenceVector!=null){
			for(int i=0;i<sentenceVector.size();i++){
				sentenceVectorList.add(sentenceVector.getDouble(i));
			}
		}
		return sentenceVectorList;
	}

	@Override
	public String toString() {
		return "SentenceVectorBean [vecOrignSentence=" + vecOrignSentence + ", vecCleanedSentence=" + vecCleanedSentence
				+ ", vecSegSentenceInfo=" + vecSegSentenceInfo + ", sentenceVector=" + sentenceVector
				+ ", sentenceVectorList=" + sentenceVectorList + ", vecLanguageType=" + vecLanguageType + "]";
	}
	

}
