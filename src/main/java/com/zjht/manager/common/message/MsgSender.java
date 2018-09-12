package com.zjht.manager.common.message;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2017-10-17 15:13
 */
public interface MsgSender<K,V> {
    void sendMsg(String topic, K key, V data);
}
