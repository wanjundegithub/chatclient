package com.company.im.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class ClientConfig {

    @Value("${ClientConfig.serverIP}")
    private String serverIP;

    @Value("${ClientConfig.serverPort}")
    private int serverPort;

    @Value("${ClientConfig.clientIP}")
    private String clientIP;

    @Value("${ClientConfig.clientPort}")
    private int clientPort;

    @Value("${ClientConfig.maxReConnectTimes}")
    private int maxReConnectTimes;

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getClientIP() {
        return clientIP;
    }

    public int getClientPort() {
        return clientPort;
    }

    public int getMaxReConnectTimes() {
        return maxReConnectTimes;
    }
}
