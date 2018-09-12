package com.zjht.manager.dao;


import com.zjht.manager.entity.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface LogDao extends Mapper<Log> {

    List<Log> selectList(@Param(value = "log") Log log, @Param(value ="params") Map<String,Object> params);

    int selectListCount(@Param(value = "log") Log log, @Param(value ="params") Map<String,Object> params);


}