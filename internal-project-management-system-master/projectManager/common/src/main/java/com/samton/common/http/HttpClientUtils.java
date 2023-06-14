package com.samton.common.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	/**
	 * 连接超时时间
	 */
	public static final int CONNECTION_TIMEOUT_MS = 360000;

	/**
	 * 读取数据超时时间
	 */
	public static final int SO_TIMEOUT_MS = 360000;

	public static final String CONTENT_TYPE_JSON_CHARSET = "application/json;charset=UFT-8";

	public static final String CONTENT_TYPE_XML_CHARSET = "application/xml;charset=UFT-8";

	/**
	 * httpclient读取内容时使用的字符集
	 */
	public static final String CONTENT_CHARSET = "UTF-8";

	public static final Charset UTF_8 = Charset.forName("UTF-8");

//	public static final Charset GBK = Charset.forName(CONTENT_CHARSET);

	/**
	 * 简单get调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String doGet(String url, Map<String, String> params)
			throws ClientProtocolException, IOException, URISyntaxException {
		return doGet(url, params,CONTENT_CHARSET);
	}
	/**
	 * 简单get调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String doGet(String url, Map<String, String> params,String charset)
			throws ClientProtocolException, IOException, URISyntaxException {

		HttpClient client = buildHttpClient(false);

		HttpGet get = buildHttpGet(url, params);

		HttpResponse response = client.execute(get);
		
		assertStatus(response);

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String returnStr = EntityUtils.toString(entity,charset);
			return returnStr;
		}
		return null;
	}

	/**
	 * 简单post调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params)
			throws URISyntaxException, ClientProtocolException, IOException {
		return doPost(url, params,CONTENT_CHARSET);
	}
	/**
	 * 简单post调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params,String charset)
			throws URISyntaxException, ClientProtocolException, IOException {

		HttpClient client = buildHttpClient(false);
		
		HttpPost postMethod = buildHttpPost(url, params);
		if(params != null && StringUtils.isNotEmpty(params.get("JSESSIONID"))){
			postMethod.addHeader("Cookie", "JSESSIONID = " + params.get("JSESSIONID"));
		}
		HttpResponse response = client.execute(postMethod);

		assertStatus(response);
		
		HttpEntity entity = response.getEntity();
		Header[] headers = response.getAllHeaders();
		String sessionId = null;
		if(headers != null && headers.length > 0){
			HeaderElement[] hes = null;
			for(Header hd : headers){
				hes = hd.getElements();
				if(hes != null && hes.length > 0){
					for(HeaderElement he : hes){
						if(he != null){
							if("JSESSIONID".equals(he.getName())){
								sessionId = he.getValue();
							}
						}
					}
				}
				
			}
		}
		
		if (entity != null) {
			String returnStr = EntityUtils.toString(entity, charset);
			try{
				JSONObject jsonObject = JSONObject.fromObject(returnStr);
				jsonObject.put("sessionId", sessionId);
				returnStr = jsonObject.toString();
				return returnStr;
			}catch(Exception e){
				return returnStr;
			}
		}
		
		return null;
	}

	/**
	 * 创建HttpClient
	 * 
	 * @param isMultiThread
	 * @return
	 */
	public static HttpClient buildHttpClient(boolean isMultiThread) {

		CloseableHttpClient client;

		if (isMultiThread)
			client = HttpClientBuilder
					.create()
					.setConnectionManager(
							new PoolingHttpClientConnectionManager()).build();
		else
			client = HttpClientBuilder.create().build();
		// 设置代理服务器地址和端口
		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
		return client;
	}

	/**
	 * 构建httpPost对象
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws URISyntaxException
	 */
	public static HttpPost buildHttpPost(String url, Map<String, String> params)
			throws UnsupportedEncodingException, URISyntaxException {
		HttpPost post = new HttpPost(url);
		if(params != null && StringUtils.isNotEmpty(params.get("_checkToken"))){
		    post.addHeader("_checkToken", params.get("_checkToken"));
        }
        if(params != null && StringUtils.isNotEmpty(params.get("_time"))){
            post.addHeader("_time", params.get("_time"));
            params = null;
        }
		setCommonHttpMethod(post);
		HttpEntity he = null;
		if (params != null) {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				formparams.add(new BasicNameValuePair(key, params.get(key)));
			}
			he = new UrlEncodedFormEntity(formparams, UTF_8);
			post.setEntity(he);
		}
		// 在RequestContent.process中会自动写入消息体的长度，自己不用写入，写入反而检测报错
		// setContentLength(post, he);
		return post;

	}

	/**
	 * 构建httpGet对象
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws URISyntaxException
	 */
	public static HttpGet buildHttpGet(String url, Map<String, String> params)
			throws URISyntaxException {
		HttpGet get = new HttpGet(buildGetUrl(url, params));
		return get;
	}

	/**
	 * build getUrl str
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private static String buildGetUrl(String url, Map<String, String> params) {
		StringBuffer uriStr = new StringBuffer(url);
		if (params != null) {
			List<NameValuePair> ps = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				ps.add(new BasicNameValuePair(key, params.get(key)));
			}
			uriStr.append("?");
			uriStr.append(URLEncodedUtils.format(ps, UTF_8));
		}
		return uriStr.toString();
	}

	/**
	 * 设置HttpMethod通用配置
	 * 
	 * @param httpMethod
	 */
	public static void setCommonHttpMethod(HttpRequestBase httpMethod) {
		httpMethod.setHeader(HTTP.CONTENT_ENCODING, CONTENT_CHARSET);// setting
																		// contextCoding
//		httpMethod.setHeader(HTTP.CHARSET_PARAM, CONTENT_CHARSET);
		// httpMethod.setHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_JSON_CHARSET);
		// httpMethod.setHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_XML_CHARSET);
	}

	/**
	 * 设置成消息体的长度 setting MessageBody length
	 * 
	 * @param httpMethod
	 * @param he
	 */
	public static void setContentLength(HttpRequestBase httpMethod,
			HttpEntity he) {
		if (he == null) {
			return;
		}
		httpMethod.setHeader(HTTP.CONTENT_LEN, String.valueOf(he.getContentLength()));
	}

	/**
	 * 构建公用RequestConfig
	 * 
	 * @return
	 */
	public static RequestConfig buildRequestConfig() {
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(SO_TIMEOUT_MS)
				.setConnectTimeout(CONNECTION_TIMEOUT_MS).build();
		return requestConfig;
	}

	/**
	 * 强验证必须是200状态否则报异常
	 * @param res
	 * @throws HttpException
	 */
 static	void assertStatus(HttpResponse res) throws IOException{
		switch (res.getStatusLine().getStatusCode()) {
		case HttpStatus.SC_OK:
//		case HttpStatus.SC_CREATED:
//		case HttpStatus.SC_ACCEPTED:
//		case HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION:
//		case HttpStatus.SC_NO_CONTENT:
//		case HttpStatus.SC_RESET_CONTENT:
//		case HttpStatus.SC_PARTIAL_CONTENT:
//		case HttpStatus.SC_MULTI_STATUS:
			break;
		default:
			throw new IOException("服务器响应状态异常,失败");
		}
	}
	private HttpClientUtils() {
	}
	
	public static String getSessionId(HttpResponse response) {
		Header[] headers = response.getAllHeaders();
		String sessionId = null;
		if(headers != null && headers.length > 0){
			HeaderElement[] hes = null;
			for(Header hd : headers){
				hes = hd.getElements();
				if(hes != null && hes.length > 0){
					for(HeaderElement he : hes){
						if(he != null){
							if("JSESSIONID".equals(he.getName())){
								sessionId = he.getValue();
							}
						}
					}
				}
				
			}
		}
		return sessionId;
	}
	
    /**
     * 
     * @desc http下载
     *
     * @param httpUrl
     * @param savePath
     * @return
     */
	public static String httpDownload(String httpUrl, String savePath, String fileName) {
		// 下载网络文件
		int bytesum = 0;
		int byteread = 0;

		URL url = null;
		try {
			url = new URL(httpUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return null;
		}

		try {
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			String name = fileName;
			if(fileName==null){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				name = df.format(new Date())+"_"+UUID.randomUUID().toString().replaceAll("-", "").substring(0,6);
			}
			
			if(url.getFile().split("wx_fmt=").length>=2){
				name += "." + url.getFile().split("wx_fmt=")[1];
			}else{
				name += ".jpg";
			}
			
			String saveFile = savePath + "/" + name;
			File filepath = new File(savePath);
			if (!filepath.exists()) {
				filepath.mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(saveFile);

			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
			}
			return saveFile;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

