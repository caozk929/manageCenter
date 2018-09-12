package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.ResultDto;
import com.zjht.manager.entity.ApiChannel;

import java.util.List;

/**
 * 渠道service
 *
 * @outhor caozk
 * @create 2017-09-05 19:45
 */
public interface ChannelService {

    ApiChannel getById(String id);

    ResultDto addChannel(ApiChannel channel, String[] serviceIds);

    void update(ApiChannel channel, String[] serviceIds);

    ResultDto updateSelective(ApiChannel channel, String[] serviceIds);

    void delete(ApiChannel channel);

    /**
     * 通过渠道id删除
     * @param ids
     */
    void deleteByIds(String[] ids);

    /**
     * 批量更新状态
     * @param ids
     * @param status
     */
    void updateStatus(String[] ids, int status);

    List<ApiChannel> getList(ApiChannel apiChannel);

    PageInfo<ApiChannel> getPage(ApiChannel channel, Integer pageNum, Integer pageSize);

    /**
     * 根据Id获取名字
     * @param id
     * @return
     */
    String getNameById(String id);

    /**
     * 获取未删除的渠道信息
     * @param status
     * @return
     */
    List<ApiChannel> getListIsNotDelete(int status);
}
