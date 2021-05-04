package com.company.im.chat.session;

import com.company.im.chat.message.AbstractPacket;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
**session
 */
public class IOSession {

    private static final  Logger logger= LoggerFactory.getLogger(IOSession.class);

    private Channel channel;

    public IOSession(Channel channel) {
        this.channel = channel;
    }

    /*
    **发送消息
     */
    public void sendPacket(AbstractPacket packet){
        if(packet==null){
            logger.error("message is null");
            return;
        }
        if(channel==null||!channel.isOpen()){
            logger.error("channel is null or closed");
            return;
        }
        channel.writeAndFlush(packet);

    }

    /*
    **关闭channel
     */
    private void close()  {
        if(channel==null||!channel.isOpen()){
            logger.error("channel is null or closed");
            return;
        }
        channel.close();

    }

    public boolean isConnectedServer(){
        if(channel==null){
            return false;
        }
        if(!channel.isOpen()||!channel.isActive()){
            return  false;
        }
       return true;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
