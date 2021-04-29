package com.company.im.chat.context;


import com.company.im.chat.config.ClientConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/*
**Spring容器配置
 */
@Service
public class SpringContext implements ApplicationContextAware {

    private static SpringContext self;

    private static  ApplicationContext applicationContext;

    private static ClientConfig clientConfig;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @PostConstruct
    public void Init(){
        self=this;
    }

    public static ClientConfig getClientConfig() {
        return clientConfig;
    }

    @Resource
    public void setClientConfig(ClientConfig clientConfig) {
        SpringContext.clientConfig = clientConfig;
    }
}
