package com.company.im.chat.client;

import com.company.im.chat.context.SpringContext;
import com.company.im.chat.handle.IOHandle;
import com.company.im.chat.handle.PacketDecodeHandle;
import com.company.im.chat.handle.PacketEncodeHandle;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/*
**Chat客户端
 */
public class ChatClient {

    private static  final Logger logger= LoggerFactory.getLogger(ChatClient.class);

    private String host;

    private int port;

    private AtomicInteger reconnectTimes;

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup(1);
        logger.info("ready to start client");
        try{
            Bootstrap b  = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel arg0)
                                throws Exception {
                            ChannelPipeline pipeline = arg0.pipeline();
                            pipeline.addLast(new PacketDecodeHandle(1024*4,0,4,0,4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new PacketEncodeHandle());
                            pipeline.addLast(new IOHandle());
                        }

                    });
            host="127.00.1";
            port=9090;
            ChannelFuture f = b.connect(new InetSocketAddress(host, port))
                    .sync();
            f.channel().closeFuture().sync();
            logger.info("client start success");
        }catch(Exception e){
           logger.info(e.getMessage());
        }finally{
            //          group.shutdownGracefully();  //这里不再是优雅关闭了
            //设置最大重连次数，防止服务端正常关闭导致的空循环
            if (reconnectTimes.get() < 10) {
                reConnectServer();
            }
        }

    }

    /**
     * 断线重连
     */
    private void reConnectServer(){
        try {
            Thread.sleep(5000);
            //logger.error("Client try to reconnect");
            start();
            reconnectTimes.incrementAndGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
