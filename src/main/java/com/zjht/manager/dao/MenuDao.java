package com.zjht.manager.dao;

import com.zjht.manager.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MenuDao extends Mapper<Menu> {

    /**
     * 根据ID查询子Id
     * @param pid
     * @return
     */
    List<String> listChildrenIdByPid(@Param("pid") String pid);

    /**
     * 查询全部菜单Code
     * @return
     */
    List<String> listAllCode();

    /**
     * 根据用户Id级联查询菜单
     * @param userId
     * @param parentId
     * @return
     */
    List<Menu> listMenuByUserId(@Param("userId") String userId, @Param("parentId") String parentId);
}