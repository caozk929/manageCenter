package com.zjht.manager.service;

import com.zjht.manager.entity.Log;

import java.util.List;

/**
 * 日志Service
 */
public interface LogService {
    /**
     * 添加日志，默认会取当前用户和当前模块的bizCode保存
     * @param content 日志内容
     * @return
     */
    int add(String content);

    /**
     * 添加日志
     * @param businessCode 模块编码
     * @param content
     */
    void add(String businessCode, String content);

    /**
     * example查询
     * @param log
     * @return
     */
    List<Log> selectByExample(Log log);

    /**
     * 查询日志列表
     * @param log
     * @param num
     * @param size
     * @return
     */
    List<Log> selectList(Log log,Integer num,Integer size);

    /**
     * 查询日志条数
     * @param log
     * @return
     */
    int selectListCount(Log log);

}
