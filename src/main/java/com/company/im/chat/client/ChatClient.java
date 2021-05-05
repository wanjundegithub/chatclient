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
public class ChatClient implements ClientNode {

    private static  final Logger logger= LoggerFactory.getLogger(ChatClient.class);

    private String host;

    private int port;

    private AtomicInteger reconnectTimes=new AtomicInteger(0);

    private int maxReconnectTimes;

    private EventLoopGroup workerGroup=new NioEventLoopGroup(1);

    @Override
    public void init() {
        var config=SpringContext.getClientConfig();
        if(config==null){
            logger.info("Spring Context Load Failure");
        }
        host=config.getServerIP();
        port=config.getServerPort();
        maxReconnectTimes=config.getMaxReConnectTimes();
    }

    public void start() {
        logger.info("ready to start client");
        try{
            Bootstrap b  = new Bootstrap();
            b.group(workerGroup).channel(NioSocketChannel.class)
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
            ChannelFuture f = b.connect(new InetSocketAddress(host, port))
                    .sync();
            f.channel().closeFuture().sync();
            logger.info("client connect success");
        }catch(Exception e){
           logger.info(e.getMessage());
            //设置最大重连次数，防止服务端正常关闭导致的空循环
            if (reconnectTimes.get() < 10) {
                reConnectServer();
            }
        }finally{
            shutDown();
        }

    }

    /**
     * 断线重连
     */
    private void reConnectServer(){
        try {
            Thread.sleep(5000);
            start();
            reconnectTimes.incrementAndGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutDown() {
        if(workerGroup==null){
            return;
        }
        workerGroup.shutdownGracefully();
    }



}
