package com.zjht.manager.dao;

import com.zjht.manager.entity.ErrorCode;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface ErrorCodeDao extends Mapper<ErrorCode> {
}