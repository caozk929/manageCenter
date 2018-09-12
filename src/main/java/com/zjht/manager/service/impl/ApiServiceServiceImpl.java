package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.ApiServiceDao;
import com.zjht.manager.entity.ApiService;
import com.zjht.manager.service.ApiServiceService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 接口服务service实现
 *
 * @outhor caozk
 * @create 2017-09-05 19:46
 */
@Transactional
@Service
public class ApiServiceServiceImpl implements ApiServiceService {
    @Autowired
    private ApiServiceDao apiServiceDao;

    @Override
    public ApiService getById(String id) {
        return apiServiceDao.selectByPrimaryKey(id);
    }

    @Override
    public int addChannel(ApiService service) {

        return apiServiceDao.insert(service);
    }

    @Override
    public int update(ApiService service) {
        return apiServiceDao.updateByPrimaryKey(service);
    }

    @Override
    public int updateSelective(ApiService service) {
        return apiServiceDao.updateByPrimaryKeySelective(service);
    }

    @Override
    public int delete(ApiService service) {
        return apiServiceDao.delete(service);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (int i=0; i<ids.length; i++){
            apiServiceDao.deleteByPrimaryKey(ids[i]);
        }
    }

    @Override
    public void updateStatus(String[] ids, int status) {
        for (int i=0; i<ids.length; i++){
            ApiService service = new ApiService();
            service.setId(ids[i]);
            service.setStatus(status);
            apiServiceDao.updateByPrimaryKeySelective(service);
        }
    }
    
    @Override
    public List<ApiService> getList(ApiService service) {
        return apiServiceDao.select(service);
    }

    @Override
    public PageInfo<ApiService> getPage(ApiService service, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(ApiService.class);
        example.setOrderByClause(service.getOrderByClause());
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(service.getServiceName())){
            criteria.andLike("serviceName","%" + service.getServiceName() + "%");
        }
        //已删除状态的不查询
        criteria.andNotEqualTo("status",ApiService.Status.DELETED.getStatus());

        List<ApiService> list = apiServiceDao.selectByExample(example);

        PageInfo<ApiService> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


}
