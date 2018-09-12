package com.zjht.manager.dao;

import com.zjht.manager.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ArticleDao extends Mapper<Article> {

    /**
     * 单个update
     * @param columnId
     * @return
     */
    int updateColumcIdToNullByColumnId(@Param("columnId") String columnId);

}