package com.zjht.manager.dao;

import com.zjht.manager.entity.ApiChannel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ApiChannelDao extends Mapper<ApiChannel> {

    /**
     * 根据Id获取名字
     * @param id
     * @return
     */
    String getNameById(@Param("id") String id);

    /**
     * 获取未删除的渠道信息
     * @param status
     * @return
     */
    List<ApiChannel> getListIsNotDelete(@Param("status") int status);
}