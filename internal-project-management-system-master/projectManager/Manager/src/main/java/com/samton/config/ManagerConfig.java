package com.samton.config;

import com.samton.common.config.ConfigBase;

/**
 * Created by zzp on 16/7/12.
 *
 */
public class ManagerConfig extends ConfigBase {

	private ManagerConfig() { }

    public static ManagerConfig getInstance() {
        return ConfigHolder.instance;
    }

    @Override
    public void init() {
        super.init();
    }
    
    private static class ConfigHolder {
        private static ManagerConfig instance = new ManagerConfig();
    }

}
