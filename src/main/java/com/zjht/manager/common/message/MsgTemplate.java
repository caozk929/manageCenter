package com.zjht.manager.common.message;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 消息发送模板
 *
 * @author caozhaokui
 * @create 2017-10-17 16:55
 */
public class MsgTemplate {
   @Autowired
   private MsgSender<String,String> sender;

   public void sendMsg(String topic, String data){
       sender.sendMsg(topic,null,data);
   }

    public void setSender(MsgSender<String, String> sender) {
        this.sender = sender;
    }
}
