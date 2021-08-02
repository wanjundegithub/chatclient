package com.company.im.chat;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.message.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class TestService {

    private static final Logger logger= LoggerFactory.getLogger(TestService.class);

    @Autowired
    private UserService userService;

    @Test
    public void testUserService(){
//        var service= SpringContext.getUserService();
//        if(service==null){
//            logger.error("userService is null");
//            return;
//        }
        String a="qqq";
        String b="sss";
        logger.info("service {} is not null {}",a,b);
    }
}
