package com.company.im.chat.handle;

import com.company.im.chat.message.AbstractPacket;
import com.company.im.chat.message.MessageRouter;
import com.company.im.chat.session.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
**IO通道处理（包括连接，读写）
 */
public class IOHandle extends ChannelInboundHandlerAdapter {

    private static final Logger logger= LoggerFactory.getLogger(IOHandle.class);

    /*
    **channel连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        var channel=ctx.channel();
        SessionManager.Instance.registerSession(channel);
    }

    /*
    **channel读写
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractPacket packet=(AbstractPacket) msg;
        if(packet==null){
            logger.error("message is null");
            return;
        }
        //处理消息
        MessageRouter.Instance.execPacket(packet);
    }

    /*
    **channel异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        var channel=ctx.channel();
        if(channel.isActive()){
            logger.error("exception:"+cause.getMessage());
        }
    }
}
