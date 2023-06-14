package com.samton.common.config;

/**
 * Created by zzp on 16/6/16.
 */
public class CommonConfig extends ConfigBase {

    //redis配置
    private String redisIp;
    private Integer redisPort;
    private Integer redisDbIndex;
    private Integer redisMaxTotal;
    private Integer redisMaxIdle;
    private Long redisMaxWaitMillis;
    private Integer redisTimeout;
    private String redisPWD;
    //机器人设置图片路径
    private String robotPicPath;

    //memache配置
    private String memcacheIp;
    private Integer memcachePort;

    // ES配置
    protected String esServiceBaseUrl;

    //微信绑定url
    private String bindUrl;
    
    //服务器之间认证token
    private String tokenStr;
    
    private CommonConfig() {
    }

    public static CommonConfig getInstance() {
        return ConfigHolder.instance;
    }

    private static class ConfigHolder {
        private static CommonConfig instance = new CommonConfig();
    }

    @Override
    public void init() {
        super.init();

        redisIp = this.getStringProperty("redisIp", "");
        redisPort = this.getIntProperty("redisPort", 6379);
        redisDbIndex = this.getIntProperty("redisDbIndex", 10);
        redisMaxTotal = this.getIntProperty("redisMaxTotal", 20);
        redisMaxIdle = this.getIntProperty("redisMaxIdle", 20);
        redisMaxWaitMillis = this.getLongProperty("redisMaxWaitMillis", 500l);
        redisTimeout = this.getIntProperty("redisTimeout", 500);
        redisPWD = this.getStringProperty("redisPWD", "");
        memcacheIp = this.getStringProperty("memcacheIp", "");
        memcachePort = this.getIntProperty("memcachePort", 1);
        esServiceBaseUrl = this.getStringProperty("qaESServiceBaseUrl", null);
        tokenStr = this.getStringProperty("tokenStr", "");
        bindUrl = this.getStringProperty("bindUrl" , "http://xiaoben.samton.com");
        robotPicPath = this.getStringProperty("robotPicPath" , "/usr/local");
    }

    public String getRobotPicPath() {
		return robotPicPath;
	}

	/*
     * Redis
     */
    public String getRedisIp() {
        return redisIp;
    }

    public Integer getRedisPort() {
        return redisPort;
    }

    public Integer getRedisDbIndex() {
        return redisDbIndex;
    }

    public Integer getRedisMaxTotal() {
        return redisMaxTotal;
    }

    public Integer getRedisMaxIdle() {
        return redisMaxIdle;
    }

    public Long getRedisMaxWaitMillis() {
        return redisMaxWaitMillis;
    }

    public Integer getRedisTimeout() {
        return redisTimeout;
    }

    public String getRedisPWD() {
		return redisPWD;
	}

	/*
     * Memcache
     */
    public String getMemcacheIp() {
        return memcacheIp;
    }

    public Integer getMemcachePort() {
        return memcachePort;
    }

    /*
     * ES
     */
    public String getEsServiceBaseUrl() {
        return esServiceBaseUrl;
    }

    /*
     * BindUrl
     */
    public String getBindUrl(){
    	return bindUrl;
    }
    
    public String getTokenStr(){
        return tokenStr;
    }
    
}
