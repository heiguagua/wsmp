package com.chinawiserv.wsmp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/1/13.
 */
@Component
public class SpringContextManager {

    private static ApplicationContext applicationContext;

    @Autowired
    public void  setContxt(ApplicationContext context){
        applicationContext = context;
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
        return (T)applicationContext.getBean(name);
    }

}
