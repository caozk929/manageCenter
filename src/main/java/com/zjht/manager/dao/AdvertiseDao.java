package com.zjht.manager.dao;

import com.zjht.manager.entity.Advertise;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdvertiseDao extends Mapper<Advertise> {

    void deleteByAdvId(@Param(value = "advId") String advId);

    Advertise findByAdvId(@Param(value = "advId") String advId);

    List<Advertise> getAdvertiseList(@Param(value="advertise") Advertise advertise,@Param(value="num") int num,@Param(value="size") Integer size);

    int getAdvertiseListCount(@Param(value="advertise") Advertise advertise);

    String getChannelName(@Param(value = "advPositionId") String advPositionId );

    int updateAdvPositionId(@Param(value = "advPositionId") String advPositionId);
}