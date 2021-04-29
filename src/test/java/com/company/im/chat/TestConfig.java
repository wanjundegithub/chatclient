package com.company.im.chat;

import com.company.im.chat.config.ClientConfig;
import com.company.im.chat.context.SpringContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class TestConfig {

    private static final Logger logger=LoggerFactory.getLogger(TestConfig.class);

    @Autowired
    private ClientConfig clientConfig;

    @Test
    public void testConfig(){
        if(clientConfig==null){
            logger.error("配置失败");
            return;
        }
        logger.info(clientConfig.getClientIP()+clientConfig.getMaxReConnectTimes());
    }

    @Test
    public void testSpringConfig(){
        var config= SpringContext.getClientConfig();
        if(clientConfig==null){
            logger.error("配置失败");
            return;
        }
        logger.info(clientConfig.getClientIP()+clientConfig.getMaxReConnectTimes());
    }
}
