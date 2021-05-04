package com.company.im.chat.event;

import javafx.event.Event;
import javafx.event.EventHandler;

/*
**模拟双击事件
 */
public class DoubleClickEvent <T extends Event> implements EventHandler<Event> {

    private int clickCount;

    private final long interval=300l;

    private long lastClickTime;

    @Override
    public void handle(Event event) {

    }

    /*
    **检查双击
     */
    public boolean checkValid(){
        clickCount++;
        long now=System.currentTimeMillis();
        if(clickCount%2!=0){
            lastClickTime=now;
            return false;
        }
        if((now-lastClickTime)>interval){
           return false;
        }
        lastClickTime=now;
        return true;
    }
}
