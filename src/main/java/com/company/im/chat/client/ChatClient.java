package com.company.im.chat.client;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.handle.IOHandle;
import com.company.im.chat.handle.PacketDecodeHandle;
import com.company.im.chat.handle.PacketEncodeHandle;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/*
**Chat客户端
 */
public class ChatClient implements ClientNode{

    private static  final Logger logger= LoggerFactory.getLogger(ChatClient.class);

    private String host;

    private int port;

    private AtomicInteger reconnectTimes;

    private EventLoopGroup worker=new NioEventLoopGroup(1);

    @Override
    public void init() {
        var config= SpringContext.getClientConfig();
        host= config.getClientIP();
        port=config.getClientPort();
    }

    @Override
    public void start() {
        /**
         * Netty用于接收客户端请求的线程池职责如下。
         * （1）接收客户端TCP连接，初始化Channel参数；
         * （2）将链路状态变更事件通知给ChannelPipeline
         */
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host,port)
                    .handler(new ChannelInitializer<SocketChannel> (){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            var pipeLine=socketChannel.pipeline();
                            pipeLine.addLast(new PacketDecodeHandle(1024*4,0,
                                    4,0,0));
                            pipeLine.addLast(new LengthFieldPrepender(4));
                            pipeLine.addLast(new PacketEncodeHandle());
                            pipeLine.addLast(new IOHandle());
                        }
                    });
            //绑定端口
            ChannelFuture channelFuture=bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            logger.error("connect exception: try reconncet"+e.getMessage());
            reconnectTimes.incrementAndGet();
            reConnect();
        }

    }

    @Override
    public void reConnect() {
        if(reconnectTimes.get()>10){
            logger.error(" out max reconnect times,it will exit");
            worker.shutdownGracefully();
        }
        start();
    }


}
