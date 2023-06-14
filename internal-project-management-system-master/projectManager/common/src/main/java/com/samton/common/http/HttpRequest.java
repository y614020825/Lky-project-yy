package com.samton.common.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import com.samton.common.constant.ExpCodeConstant;

public class HttpRequest {
	public static final List<String> imgList = Arrays.asList(new String[]{"bmp","jpg","jpeg","gif","png"});
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;        
        StringBuilder result = new StringBuilder(); 
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数            
            if (params != null) {
		          StringBuilder param = new StringBuilder(); 
		          for (Map.Entry<String, String> entry : params.entrySet()) {
		        	  if(param.length()>0){
		        		  param.append("&");
		        	  }	        	  
		        	  param.append(entry.getKey());
		        	  param.append("=");
		        	  param.append(entry.getValue());		        	  
		        	  //System.out.println(entry.getKey()+":"+entry.getValue());
		          }
		          //System.out.println("param:"+param.toString());
		          out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }  
    
    /** 
     * 上传图片 
     * 
     * @param urlStr 
     * @param textMap 
     * @param fileMap 
     * @return 
     */
    public static String formUploadByStream(String urlStr, Map<String, String> textMap,
      Map<String, InputStream> fileMap) { 
     String res = ""; 
     HttpURLConnection conn = null; 
     String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符 
     try { 
      URL url = new URL(urlStr); 
      conn = (HttpURLConnection) url.openConnection(); 
      conn.setConnectTimeout(5000); 
      conn.setReadTimeout(30000); 
      conn.setDoOutput(true); 
      conn.setDoInput(true); 
      conn.setUseCaches(false); 
      conn.setRequestMethod("POST"); 
      conn.setRequestProperty("Connection", "Keep-Alive"); 
      conn 
        .setRequestProperty("User-Agent", 
          "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)"); 
      conn.setRequestProperty("Content-Type", 
        "multipart/form-data; boundary=" + BOUNDARY); 
      OutputStream out = new DataOutputStream(conn.getOutputStream()); 
      // text 
      if (textMap != null) { 
       StringBuffer strBuf = new StringBuffer(); 
       Iterator iter = textMap.entrySet().iterator(); 
       while (iter.hasNext()) { 
        Map.Entry entry = (Map.Entry) iter.next(); 
        String inputName = (String) entry.getKey(); 
        String inputValue = (String) entry.getValue(); 
        if (inputValue == null) { 
         continue; 
        } 
        strBuf.append("\r\n").append("--").append(BOUNDARY).append( 
          "\r\n"); 
        strBuf.append("Content-Disposition: form-data; name=\""
          + inputName + "\"\r\n\r\n"); 
        strBuf.append(inputValue); 
       } 
       out.write(strBuf.toString().getBytes()); 
      } 
      // file 
      if (fileMap != null) { 
       Iterator iter = fileMap.entrySet().iterator(); 
       while (iter.hasNext()) { 
        Map.Entry entry = (Map.Entry) iter.next(); 
        String inputName = (String) entry.getKey(); 
        FileInputStream in = (FileInputStream) entry.getValue(); 
        if (in == null) { 
         continue; 
        } 
//        File file = new File(inputValue); 
//        String filename = file.getName(); 
//        String contentType = new MimetypesFileTypeMap() 
//          .getContentType(file); 
        String columnName = inputName.split("#,#")[0];
        String fileName = "";
        if(inputName.split("#,#").length >= 2){
        	fileName = inputName.split("#,#")[1];
        }
        String contentType = "application/octet-stream";
        String suffixName = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
    	if (HttpRequest.imgList.contains(suffixName)) {
			contentType = "image/png"; 
		} 
        StringBuffer strBuf = new StringBuffer(); 
        strBuf.append("\r\n").append("--").append(BOUNDARY).append( 
          "\r\n"); 
        strBuf.append("Content-Disposition: form-data; name=\""
          + columnName + "\"; filename=\"" + fileName 
          + "\"\r\n"); 
        strBuf.append("Content-Type:" + contentType + "\r\n\r\n"); 
        out.write(strBuf.toString().getBytes()); 
//        DataInputStream in = new DataInputStream( 
//          new FileInputStream(file)); 
        int bytes = 0; 
        byte[] bufferOut = new byte[1024]; 
        while ((bytes = in.read(bufferOut)) != -1) { 
         out.write(bufferOut, 0, bytes); 
        } 
        in.close(); 
       } 
      } 
      byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(); 
      out.write(endData); 
      out.flush(); 
      out.close(); 
      // 读取返回数据 
      StringBuffer strBuf = new StringBuffer(); 
      BufferedReader reader = new BufferedReader(new InputStreamReader( 
        conn.getInputStream())); 
      String line = null; 
      while ((line = reader.readLine()) != null) { 
       strBuf.append(line).append("\n"); 
      } 
      res = strBuf.toString(); 
      reader.close(); 
      reader = null; 
     } catch (Exception e) { 
      System.out.println("发送POST请求出错。" + urlStr); 
      e.printStackTrace(); 
     } finally { 
      if (conn != null) { 
       conn.disconnect(); 
       conn = null; 
      } 
     } 
     return res; 
    }
    
    /** 
     * 上传图片 
     * 
     * @param urlStr 
     * @param textMap 
     * @param fileMap 
     * @return 
     */
    public static String formUpload(String urlStr, Map<String, String> textMap,
      Map<String, String> fileMap) { 
     String res = ""; 
     HttpURLConnection conn = null; 
     String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符 
     try { 
      URL url = new URL(urlStr); 
      conn = (HttpURLConnection) url.openConnection(); 
      conn.setConnectTimeout(5000); 
      conn.setReadTimeout(30000); 
      conn.setDoOutput(true); 
      conn.setDoInput(true); 
      conn.setUseCaches(false); 
      conn.setRequestMethod("POST"); 
      conn.setRequestProperty("Connection", "Keep-Alive"); 
      conn 
        .setRequestProperty("User-Agent", 
          "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)"); 
      conn.setRequestProperty("Content-Type", 
        "multipart/form-data; boundary=" + BOUNDARY); 
      OutputStream out = new DataOutputStream(conn.getOutputStream()); 
      // text 
      if (textMap != null) { 
       StringBuffer strBuf = new StringBuffer(); 
       Iterator iter = textMap.entrySet().iterator(); 
       while (iter.hasNext()) { 
        Map.Entry entry = (Map.Entry) iter.next(); 
        String inputName = (String) entry.getKey(); 
        String inputValue = (String) entry.getValue(); 
        if (inputValue == null) { 
         continue; 
        } 
        strBuf.append("\r\n").append("--").append(BOUNDARY).append( 
          "\r\n"); 
        strBuf.append("Content-Disposition: form-data; name=\""
          + inputName + "\"\r\n\r\n"); 
        strBuf.append(inputValue); 
       } 
       out.write(strBuf.toString().getBytes()); 
      } 
      // file 
      if (fileMap != null) { 
       Iterator iter = fileMap.entrySet().iterator(); 
       while (iter.hasNext()) { 
        Map.Entry entry = (Map.Entry) iter.next(); 
        String inputName = (String) entry.getKey(); 
        String inputValue = (String) entry.getValue(); 
        if (inputValue == null) { 
         continue; 
        } 
        File file = new File(inputValue); 
        String filename = file.getName(); 
        String contentType = new MimetypesFileTypeMap() 
          .getContentType(file); 
        if (filename.endsWith(".png")) { 
         contentType = "image/png"; 
        } 
        if (contentType == null || contentType.equals("")) { 
         contentType = "application/octet-stream"; 
        } 
        StringBuffer strBuf = new StringBuffer(); 
        strBuf.append("\r\n").append("--").append(BOUNDARY).append( 
          "\r\n"); 
        strBuf.append("Content-Disposition: form-data; name=\""
          + inputName + "\"; filename=\"" + filename 
          + "\"\r\n"); 
        strBuf.append("Content-Type:" + contentType + "\r\n\r\n"); 
        out.write(strBuf.toString().getBytes()); 
        DataInputStream in = new DataInputStream( 
          new FileInputStream(file)); 
        int bytes = 0; 
        byte[] bufferOut = new byte[1024]; 
        while ((bytes = in.read(bufferOut)) != -1) { 
         out.write(bufferOut, 0, bytes); 
        } 
        in.close(); 
       } 
      } 
      byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(); 
      out.write(endData); 
      out.flush(); 
      out.close(); 
      // 读取返回数据 
      StringBuffer strBuf = new StringBuffer(); 
      BufferedReader reader = new BufferedReader(new InputStreamReader( 
        conn.getInputStream())); 
      String line = null; 
      while ((line = reader.readLine()) != null) { 
       strBuf.append(line).append("\n"); 
      } 
      res = strBuf.toString(); 
      reader.close(); 
      reader = null; 
     } catch (Exception e) { 
      System.out.println("发送POST请求出错。" + urlStr); 
      e.printStackTrace(); 
     } finally { 
      if (conn != null) { 
       conn.disconnect(); 
       conn = null; 
      } 
     } 
     return res; 
    } 
    
    public static void main(String[] args) { 
    	  String filepath="/usr/local/img/print/2017-06-15_616817.jpg"; 
    	  String urlStr = "http://127.0.0.1:8080/minicms/up/up_result.jsp"; 
    	  Map<String, String> textMap = new HashMap<String, String>(); 
    	  textMap.put("name", "testname"); 
    	  Map<String, String> fileMap = new HashMap<String, String>(); 
    	  fileMap.put("userfile", filepath); 
    	  String ret = formUpload(urlStr, textMap, fileMap); 
    	  System.out.println(ret); 
    	 } 
}