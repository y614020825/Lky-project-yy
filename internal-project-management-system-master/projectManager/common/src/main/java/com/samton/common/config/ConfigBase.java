package com.samton.common.config;

import com.samton.common.log.LogUtil;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author yangpeng
 * 系统中涉及到的配置文件属性都赋值给这个类对象
 * 赋值时需考虑值不存在的情况，异常需在赋值时处理
 * 对应配置文件放在自己项目路径下，放的位置要为代码编译路径
 * 如果需要对这个类进行扩充，可在自己项目中继承这个类
 */
public class ConfigBase {

    Properties pro = new Properties();

    public ConfigBase() {
        this.init();
    }

    public void init() {
        try {
            pro.load(new InputStreamReader(ConfigBase.class.getResourceAsStream("/config.properties"), "UTF-8"));
        } catch (IOException e) {
            LogUtil.errorSystemLog("配置文件读取失败", e);
        }
    }

    protected Integer getIntProperty(String strValue, Integer defaultValue) {
        try {
            return Integer.parseInt(pro.getProperty(strValue));
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    protected Long getLongProperty(String strValue, Long defaultValue) {
        try {
            return Long.parseLong(pro.getProperty(strValue));
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    protected Double getDoubleProperty(String strValue, Double defaultValue) {
        try {
            return Double.parseDouble(pro.getProperty(strValue));
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    protected String getStringProperty(String strValue, String defaultValue) {
        String v = pro.getProperty(strValue);
        if (StringUtils.isEmpty(v)) {
            return defaultValue;
        }

        return v;
    }
}
