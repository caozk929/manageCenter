package com.zjht.manager.service;

import com.zjht.manager.entity.ErrorCode;

import java.util.List;

public interface ErrorCodeService {
    int insert(ErrorCode record);

    List<ErrorCode> selectPage(ErrorCode record, int pageNum, int pageSize);
        }
