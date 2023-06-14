package com.samton.common.config;

import com.samton.common.config.ConfigBase;

/**
 * Created by guoshubin 
 */
public class SocketConfig extends ConfigBase {

    // es service 地址
	protected String esServiceUrl;
	
    protected String localhost;

    //iben_m 地址
    protected String IbenMServiceUrl;

    //文件上传路径
    
    protected String FileUploadUrl;

    private SocketConfig() { }

    public static SocketConfig getInstance() {
        return ConfigHolder.instance;
    }

    private static class ConfigHolder {
        private static SocketConfig instance = new SocketConfig();
    }

    @Override
    public void init() {
        super.init();

        localhost = this.getStringProperty("localhost", "");
        esServiceUrl = this.getStringProperty("esServiceUrl", "");
        IbenMServiceUrl = this.getStringProperty("IbenMServiceUrl", "");
        FileUploadUrl = this.getStringProperty("FileUploadUrl","");
    }

	public String getLocalhost() {
		return localhost;
	}

	public void setLocalhost(String localhost) {
		this.localhost = localhost;
	}

	public String getIbenMServiceUrl() {
		return IbenMServiceUrl;
	}

	public void setIbenMServiceUrl(String ibenMServiceUrl) {
		IbenMServiceUrl = ibenMServiceUrl;
	}

	public String getFileUploadUrl() {
		return FileUploadUrl;
	}

	public void setFileUploadUrl(String fileUploadUrl) {
		FileUploadUrl = fileUploadUrl;
	}

	public String getEsServiceUrl() {
		return esServiceUrl;
	}

	public void setEsServiceUrl(String esServiceUrl) {
		this.esServiceUrl = esServiceUrl;
	}

    
}
