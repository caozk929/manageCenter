package com.zjht.manager.common.mybatis;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface BatchInsertMapper<T> {

    /**
     * 批量插入，支持数据库自增字段，支持回写
     *
     * @param recordList
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @InsertProvider(type = BatchInsertProvider.class, method = "dynamicSQL")
    int batchInsert(List<T> recordList);
}