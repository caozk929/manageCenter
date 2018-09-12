package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.AdvertiseDao;
import com.zjht.manager.entity.Advertise;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.service.AdvertiseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * 〈广告图片信息管理AdvertiseServiceImpl〉
 *
 * @author wangpeng
 * @create 2017/9/4
 * @since 1.0.0
 */
@Service
@Transactional // 开启事务
public class AdvertiseServiceImpl implements AdvertiseService {

    @Autowired
    private AdvertiseDao advertiseDao;


    @Override
    public boolean insert(Advertise advertise) {
        int result = advertiseDao.insertSelective(advertise);
        if (result > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean update(Advertise advertise) {
        int result = advertiseDao.updateByPrimaryKeySelective(advertise);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Advertise> findByParams(Map<String, Object> params) {
        //封装查询条件
        Example example = new Example(Advertise.class);
        Example.Criteria criteria = example.createCriteria();
        if (params.containsKey("advId") && StringUtils.isNotBlank(params.get("advId").toString())) {
            criteria.andNotEqualTo("advId", params.get("advId").toString());
        }
        if (params.containsKey("advName") && StringUtils.isNotBlank(params.get("advName").toString())) {
            criteria.andEqualTo("advName", params.get("advName").toString());
        }

        criteria.andNotEqualTo("advStatus", ApiChannel.Status.DELETED.getStatus());

        return advertiseDao.selectByExample(example);
    }


    @Override
    public void deleteByIds(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            advertiseDao.deleteByAdvId(ids[i]);
        }
    }

    @Override
    public Advertise findByAdvId(String AdvId) {
        return advertiseDao.findByAdvId(AdvId);
    }

    @Override
    public int getAdvertiseListCount(Advertise advertise) {
        return advertiseDao.getAdvertiseListCount(advertise);
    }

    @Override
    public List<Advertise> getAdvertiseList(Advertise advertise, int pageNum, Integer pageSize) {
        return advertiseDao.getAdvertiseList(advertise, (pageNum - 1) * pageSize, pageSize);
    }

    @Override
    public String getChannelName(String advPositionId) {
        return advertiseDao.getChannelName(advPositionId);
    }

}