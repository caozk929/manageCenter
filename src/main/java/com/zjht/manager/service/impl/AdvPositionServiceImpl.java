package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.AdvPositionDao;
import com.zjht.manager.dao.AdvertiseDao;
import com.zjht.manager.entity.AdvPosition;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.service.AdvPositionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * 〈广告图片位置管理AdvPositionServiceImpl〉
 *
 * @author wangpeng
 * @create 2017/9/4
 * @since 1.0.0
 */
@Service
@Transactional // 开启事务
public class AdvPositionServiceImpl implements AdvPositionService {
    @Autowired
    private AdvPositionDao advPositionDao;
    @Autowired
    private AdvertiseDao advertiseDao;


    @Override
    public List<AdvPosition> selectByParams(Map<String, Object> params) {
        //封装查询条件
        Example example = new Example(AdvPosition.class);
        Example.Criteria criteria = example.createCriteria();
        if (params.containsKey("status") && StringUtils.isNotBlank(params.get("status").toString())) {
            criteria.andEqualTo("status", params.get("status").toString());
        }
        if (params.containsKey("name") && StringUtils.isNotBlank(params.get("name").toString())) {
            criteria.andEqualTo("name", params.get("name").toString());
        }
        if (params.containsKey("positionCode") && StringUtils.isNotBlank(params.get("positionCode").toString())) {
            criteria.andEqualTo("positionCode", params.get("positionCode").toString());
        }
        if (params.containsKey("id") && StringUtils.isNotBlank(params.get("id").toString())) {
            criteria.andNotEqualTo("id", params.get("id").toString());
        }
        criteria.andNotEqualTo("status", ApiChannel.Status.DELETED.getStatus());//未删除的
        return advPositionDao.selectByExample(example);
    }

    @Override
    public boolean insert(AdvPosition advPosition) {
        int result = advPositionDao.insertSelective(advPosition);
        if (result > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean update(AdvPosition advPosition) {
        int result = advPositionDao.updateByPrimaryKeySelective(advPosition);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public AdvPosition findById(String id) {
        AdvPosition advPosition = advPositionDao.selectByPrimaryKey(id);
        return advPosition;
    }


    @Override
    public void deleteByIds(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            advertiseDao.updateAdvPositionId(advPositionDao.selectByPrimaryKey(ids[i]).getPositionCode());
            advPositionDao.deleteById(ids[i]);
        }
    }

    @Override
    public PageInfo<AdvPosition> findPage(AdvPosition advPosition, Integer pageNum, Integer pageSize) {
        //封装分页参数
        PageHelper.startPage(pageNum, pageSize);
        //封装查询条件
        Example example = new Example(AdvPosition.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(advPosition.getName())) {//名称搜索
            criteria.andLike("name", "%" + advPosition.getName() + "%");
        }
        if (StringUtils.isNotBlank(advPosition.getBeginDate())) {//创建开始日期搜索
            criteria.andGreaterThanOrEqualTo("createTime", advPosition.getBeginDate());
        }
        if (StringUtils.isNotBlank(advPosition.getEndDate())) {//创建结束日期搜索
            criteria.andLessThanOrEqualTo("createTime", advPosition.getEndDate() + " 23:59:59");
        }
        if (StringUtils.isNotBlank(advPosition.getChannel())) {//渠道搜索
            criteria.andEqualTo("channel", advPosition.getChannel());
        }
        criteria.andNotEqualTo("status", ApiChannel.Status.DELETED.getStatus());//未删除的
        List<AdvPosition> advPositions = advPositionDao.selectByExample(example);
        return new PageInfo<AdvPosition>(advPositions);
    }
}