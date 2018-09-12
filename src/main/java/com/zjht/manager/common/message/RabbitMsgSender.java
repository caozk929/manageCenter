package com.zjht.manager.common.message;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2017-11-27 16:33
 */
public class RabbitMsgSender implements MsgSender<String,String> {
    @Autowired
    private AmqpTemplate amqpTemplate;
    //直连交换器名
    private String directExchange;
    //fanout交换器名
    private String fanoutExchange;
    //topic交换器名
    private String topicExchange;

    //直连路由keys
    private List<String> directRoutekeys;
    //扇形keys
    private List<String> fanoutRoutekeys;
    //topic路由keys
    private List<String> topicRoutekeys;
    //是否需要延迟发送
    private Map<String,Integer> delayMap;
    @Override
    public void sendMsg(String routekey, String key, String data) {
        String exchange = null;
        if(directRoutekeys!=null && directRoutekeys.contains(routekey)){
            exchange = directExchange;
        }else if(fanoutRoutekeys!=null && fanoutRoutekeys.contains(routekey)){
            exchange = fanoutExchange;
        }else if(topicRoutekeys!=null && topicRoutekeys.contains(routekey)){
            exchange = topicExchange;
        }
        amqpTemplate.convertAndSend(exchange, routekey, data, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                if(delayMap.containsKey(routekey)){
                   message.getMessageProperties().setDelay(delayMap.get(routekey));
                }
                return message;
            }
        });
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void setDirectExchange(String directExchange) {
        this.directExchange = directExchange;
    }

    public void setTopicExchange(String topicExchange) {
        this.topicExchange = topicExchange;
    }

    public void setFanoutExchange(String fanoutExchange) {
        this.fanoutExchange = fanoutExchange;
    }

    public void setDirectRoutekeys(List<String> directRoutekeys) {
        this.directRoutekeys = directRoutekeys;
    }

    public void setTopicRoutekeys(List<String> topicRoutekeys) {
        this.topicRoutekeys = topicRoutekeys;
    }

    public void setFanoutRoutekeys(List<String> fanoutRoutekeys) {
        this.fanoutRoutekeys = fanoutRoutekeys;
    }

    public void setDelayMap(Map<String, Integer> delayMap) {
        this.delayMap = delayMap;
    }
}
