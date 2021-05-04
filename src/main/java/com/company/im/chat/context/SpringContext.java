package com.company.im.chat.context;


import com.company.im.chat.config.ClientConfig;
import com.company.im.chat.message.InitService;
import com.company.im.chat.message.chat.ChatService;
import com.company.im.chat.message.friend.FriendService;
import com.company.im.chat.message.user.UserService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;

/*
**Spring容器配置
 */
@Service
public class SpringContext implements ApplicationContextAware {

    private static SpringContext self;

    private static  ApplicationContext applicationContext;

    private static ClientConfig clientConfig;

    private static UserService userService;

    private static FriendService friendService;

    private static ChatService chatService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @PostConstruct
    public void Init(){
        self=this;
        Arrays.stream(self.getClass().getDeclaredFields()).forEach(
                f -> {
                    try {
                        Object obj = f.get(null);
                        if (InitService.class.isAssignableFrom(f.getType())) {
                            ((InitService) obj).init();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public static ClientConfig getClientConfig() {
        return clientConfig;
    }

    @Resource
    public void setClientConfig(ClientConfig clientConfig) {
        SpringContext.clientConfig = clientConfig;
    }

    public static UserService getUserService() {
        return userService;
    }

    @Resource
    public  void setUserService(UserService userService) {
        SpringContext.userService = userService;
    }

    public static FriendService getFriendService() {
        return friendService;
    }

    @Resource
    public void setFriendService(FriendService friendService) {
        SpringContext.friendService = friendService;
    }

    public static ChatService getChatService() {
        return chatService;
    }

    @Resource
    public void setChatService(ChatService chatService) {
        SpringContext.chatService = chatService;
    }
}
