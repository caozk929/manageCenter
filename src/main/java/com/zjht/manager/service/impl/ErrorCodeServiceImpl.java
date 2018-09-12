package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.ErrorCodeDao;
import com.zjht.manager.entity.ErrorCode;
import com.zjht.manager.service.ErrorCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public class ErrorCodeServiceImpl implements ErrorCodeService {

    @Autowired
    private ErrorCodeDao errorCodeDao;
    @Override
    public int insert(ErrorCode record) {
        return errorCodeDao.insert(record);
    }

    @Override
    public List<ErrorCode> selectPage(ErrorCode record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ErrorCode> list = errorCodeDao.select(record);
        PageInfo pageInfo = (PageInfo)list;
        return errorCodeDao.select(record);
    }
}
