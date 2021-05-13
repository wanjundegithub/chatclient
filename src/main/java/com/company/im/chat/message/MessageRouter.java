package com.company.im.chat.message;

import com.company.im.chat.session.IOSession;
import com.company.im.chat.util.ClassScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
**消息路由
 */
public enum MessageRouter {

    Instance;

    private  final Logger logger= LoggerFactory.getLogger(MessageRouter.class);

    private Map<Integer,Class<? extends AbstractPacket>> msgPools=new HashMap<>();

    private Map<Integer,MessageHandle> msgHandles=new HashMap<>();

    /*
    **初始化msgPools
     */
    MessageRouter(){
        var packetClazz= ClassScanner.getAllSubClass(
                "com.company.im.chat.message", AbstractPacket.class);
        packetClazz.forEach(c->{
            try {
                var packetInstance=(AbstractPacket)c.getDeclaredConstructor().newInstance();
                if(packetInstance==null){
                    logger.error(c.getName()+" cannot instance");
                }
                var oldMsgPacket=msgPools.put
                        (packetInstance.getPacketID(), (Class<? extends AbstractPacket>) c);
                if(oldMsgPacket!=null){
                    logger.error(oldMsgPacket.toString()+" repeat register");
                    System.exit(1);
                }
            }
            catch (Exception e){
                logger.error(e.getMessage()+" it will exit");
            }
        });
        var handleClazz=ClassScanner.getAllSubClass(
                "com.company.im.chat.message",MessageHandle.class);
        handleClazz.forEach(c->{
            Arrays.stream(c.getDeclaredMethods()).forEach(method->{
                if(method.getParameterCount()==1){
                    Class<?> parameterType=method.getParameterTypes()[0];
                    if(AbstractPacket.class.isAssignableFrom(parameterType)&&
                    !Modifier.isAbstract(parameterType.getModifiers())){
                        MessageHandle msgHandle=null;
                        try{
                            AbstractPacket packet=(AbstractPacket)
                                    parameterType.getDeclaredConstructor().newInstance();
                            msgHandle=(MessageHandle) c.getDeclaredConstructor().newInstance();
                            var oldHandle=msgHandles.put(packet.getPacketID(),msgHandle);
                            if(oldHandle!=null){
                                logger.error("repeat register handle"+packet.getPacketID());
                                System.exit(-1);
                            }
                        }catch (Exception e){
                            logger.error(e.getMessage());
                        }
                    }
                }
            });
        });
    }

//    /*
//    **注册消息处理器
//     */
//    public boolean registerHandle(int packetType,MessageHandle handle){
//        MessageHandle oldHandle=msgHandles.put(packetType,handle);
//        if(oldHandle!=null){
//            logger.error(packetType+"repeat register handle");
//            return false;
//        }
//        return true;
//    }

    /*
    **处理消息
     */
    public void execPacket(AbstractPacket packet){
        var packetType=packet.getPacketID();
        var handle=msgHandles.get(packetType);
        if(handle==null){
            logger.error(packetType+ " message handle is null");
            return;
        }
        handle.action(packet);
    }

    /*
    **根据消息类型创建消息对象
     */
    public AbstractPacket createPacket(int packetType) {
        AbstractPacket packet=null;
        var packetClazz=msgPools.get(packetType);
        if(packetClazz==null){
            logger.error(packetType+"message packet is null");
            return null;
        }
        try{
            packet= packetClazz.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return packet;
    }

    public MessageHandle getHandle(int packetType){
        return msgHandles.get(packetType);
    }

}
