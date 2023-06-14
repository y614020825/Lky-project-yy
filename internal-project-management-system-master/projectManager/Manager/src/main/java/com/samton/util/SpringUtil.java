package com.samton.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * 
 * @desc 获取applicationContext
 * @author xielinqi
 * @date 2016年6月28日 下午6:29:37
 *
 */
@Service
public class SpringUtil implements ApplicationListener<ContextRefreshedEvent>{
	
	private static ApplicationContext applicationContext = null;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (applicationContext == null) {
			applicationContext = event.getApplicationContext();
		}
//	    SensitivewordUtil.addToRedis();
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
