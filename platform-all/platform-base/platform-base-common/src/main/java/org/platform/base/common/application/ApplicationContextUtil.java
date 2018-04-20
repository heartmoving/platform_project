package org.platform.base.common.application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring 对象信息
 * @author otvcloud
 */
@Component  
public class ApplicationContextUtil implements ApplicationContextAware {  
	
    private static ApplicationContext context;  
  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
        context = applicationContext;  
    }  
      
    public static ApplicationContext getApplicationContext() {  
        return context;  
    }
    
} 
