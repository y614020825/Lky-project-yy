package com.samton.config;

import java.util.Locale;

import java.util.ResourceBundle;
/**
 * 远程调用配置
 * @author sy
 *
 */
public class Config {

	// 单例对象
	private static Config instance;

	public synchronized static Config getInstance() throws Exception {
		if (instance == null) {
			// 配置参数
			instance = new Config();
		}
		return instance;
	}

	private final ResourceBundle properties;

	private Config() throws Exception {
		// 读取文件上传下载配置文件
		properties = ResourceBundle.getBundle("rmi",Locale.getDefault());
	}

	public String getProperty(String key) {
		return properties.getString(key);
	}
	public static void main(String[] args) throws Exception {
		Config config = Config.getInstance();
		String url = config.getProperty("tfidfServiceUrl");
		url = url + "/sso/sys/login.json";
		System.out.println(url);
	}
}
