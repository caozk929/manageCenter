package com.zjht.manager.common.log.logback;

import ch.qos.logback.classic.PatternLayout;
/**
 * logback日志PatternLayout扩展
 * @author caozhaokui
 *
 */
public class PatternLayoutExt extends PatternLayout{
    static {
        PatternLayout.defaultConverterMap.put("T", ThreadNumConverter.class.getName());
        PatternLayout.defaultConverterMap.put("threadNum", ThreadNumConverter.class.getName());

        PatternLayout.defaultConverterMap.put("msg", MessageConverter.class.getName());
        PatternLayout.defaultConverterMap.put("message", MessageConverter.class.getName());
    }
    
}
