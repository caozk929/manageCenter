package com.zjht.manager.service;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.AdvPosition;

import java.util.List;
import java.util.Map;

/**
 * 〈广告图片位置管理AdvPositionService〉
 *
 * @author wangpeng
 * @create 2017/9/4
 * @since 1.0.0
 */
public interface AdvPositionService {

    /**
     * 根据指定参数查询
     *
     * @param params
     * @return:List<AdvPosition>
     */
    List<AdvPosition> selectByParams(Map<String,Object> params);

    /**
     * 新增位置信息
     *
     * @param advPosition
     * @return:boolean
     */
    boolean insert(AdvPosition advPosition);

    /**
     * 更新位置信息
     *
     * @param advPosition
     * @return:boolean
     */
    boolean update(AdvPosition advPosition);

    /**
     * 根据Id查询广告位置信息
     *
     * @param id
     * @return:com.zjht.manager.entity.AdvPosition
     */
    AdvPosition findById(String id);


    /**
     * 删除广告位置信息
     *
     * @param ids
     * @return:
     */
    void deleteByIds(String[]  ids);

    /**
     * 获取广告位置信息
     *
     * @param advPosition
     * @return:AdvPosition
     */
    PageInfo<AdvPosition> findPage(AdvPosition advPosition, Integer pageNum, Integer pageSize);
}