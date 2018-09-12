package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.ApiChannelPayconfigDao;
import com.zjht.manager.entity.ApiChannelPayconfig;
import com.zjht.manager.service.ApiChannelPayconfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;

/**
 * 渠道支付配置
 * Created by vip on 2017/9/19.
 */
@Service
@Transactional//开启事务
public class ApiChannelPayconfigServiceImpl implements ApiChannelPayconfigService {

    @Autowired
    private ApiChannelPayconfigDao acpDao;

    /**
     * 查询分页数据
     *
     * @param acp
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<ApiChannelPayconfig> findPage(ApiChannelPayconfig acp, int pageNum, int pageSize) {
        //1.设置分页参数
        PageHelper.startPage(pageNum, pageSize);
        //2.构建查询参数条件
        Example example = new Example(ApiChannelPayconfig.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(acp.getConfigName())) {//渠道配置名称
            criteria.andLike("configName", "%" + acp.getConfigName() + "%");
        }
        if (StringUtils.isNotBlank(acp.getConfigCode())) {//渠道配置代码
            criteria.andLike("configCode", "%" + acp.getConfigCode() + "%");
        }
        if (StringUtils.isNotBlank(acp.getChannelId())) {//渠道搜索
            criteria.andEqualTo("channelId", acp.getChannelId());
        }
        return new PageInfo<ApiChannelPayconfig>(acpDao.selectByExample(example));
    }

    /**
     * 新增
     *
     * @param acp
     * @return
     */
    @Override
    public int add(ApiChannelPayconfig acp) {
        return acpDao.insert(acp);
    }

    /**
     * 修改
     *
     * @param acp
     * @return
     */
    @Override
    public int update(ApiChannelPayconfig acp) {
        return acpDao.updateByPrimaryKeySelective(acp);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        return acpDao.deleteByPrimaryKey(id);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public ApiChannelPayconfig findById(String id) {
        return acpDao.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public int deleteByIds(String[] ids) {
        //1.构建条件对象
        Example example = new Example(ApiChannelPayconfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        return acpDao.deleteByExample(example);
    }
}
