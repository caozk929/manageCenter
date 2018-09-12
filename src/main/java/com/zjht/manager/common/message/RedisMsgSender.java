package com.zjht.manager.common.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2017-10-17 16:33
 */
public class RedisMsgSender implements MsgSender<String,String> {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void sendMsg(String topic, String key, String data) {
        redisTemplate.convertAndSend(topic,data);
    }
}
