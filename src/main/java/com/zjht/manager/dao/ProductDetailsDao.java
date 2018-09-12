package com.zjht.manager.dao;

import org.springframework.stereotype.Repository;

import com.zjht.manager.entity.commodity.ProductDetails;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ProductDetailsDao extends Mapper<ProductDetails> {
}