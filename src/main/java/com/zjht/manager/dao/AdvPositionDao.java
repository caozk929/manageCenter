package com.zjht.manager.dao;

import com.zjht.manager.entity.AdvPosition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdvPositionDao extends Mapper<AdvPosition> {

    void deleteById(@Param(value = "id") String id);
}