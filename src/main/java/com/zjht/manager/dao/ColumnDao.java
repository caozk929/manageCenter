package com.zjht.manager.dao;

import com.zjht.manager.entity.Column;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ColumnDao extends Mapper<Column> {
}