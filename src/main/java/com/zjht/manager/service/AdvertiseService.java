package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.Advertise;

import java.util.List;
import java.util.Map;

/**
 * 〈广告图片信息管理AdvertiseService〉
 *
 * @author Administrator
 * @create 2017/9/4
 * @since 1.0.0
 */
public interface AdvertiseService {

    /**
     * 新增图片信息
     *
     * @param advertise
     * @return:boolean
     */
    boolean insert(Advertise advertise);

    /**
     * 更新图片信息
     *
     * @param advertise
     * @return:boolean
     */
    boolean update(Advertise advertise);

    /**
     * 查询广告图片信息
     *
     * @param params
     * @return:List<Advertise>
     */
    List<Advertise> findByParams(Map<String, Object> params);

    /**
     * 删除广告图片信息
     *
     * @param ids
     * @return:boolean
     */
    void deleteByIds(String[] ids);

    /**
     * 根据ID查找广告图片信息
     *
     * @param AdvId
     * @return:com.zjht.manager.entity.Advertise
     */
    Advertise findByAdvId(String AdvId);

    /**
     * 获取广告图片信息列表数据条数
     *
     * @param advertise
     * @return
     */
    int getAdvertiseListCount(Advertise advertise);

    /**
     * 获取广告图片信息列表数据
     *
     * @param advertise
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Advertise> getAdvertiseList(Advertise advertise, int pageNum, Integer pageSize);

    /**
     * 获取渠道名称
     *
     * @param advPositionId
     * @return
     */
    String getChannelName(String advPositionId);

}