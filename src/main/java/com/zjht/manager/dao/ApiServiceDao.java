package com.zjht.manager.dao;

import com.zjht.manager.entity.ApiService;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ApiServiceDao extends Mapper<ApiService> {
}