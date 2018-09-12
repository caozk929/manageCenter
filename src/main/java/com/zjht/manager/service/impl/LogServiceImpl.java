package com.zjht.manager.service.impl;

import com.zjht.manager.common.web.Constants;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.common.web.threadvariable.RequestThread;
import com.zjht.manager.dao.LogDao;
import com.zjht.manager.entity.Log;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.LogService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Transactional
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @Autowired
    private SessionProvider sessionProvider;

    @Override
    public int add(String content) {
        User user = sessionProvider.getUser();
        String userId = user == null ? "" : user.getId();
        Log log = new Log();
        HttpServletRequest request = RequestThread.get();
        String bizCode = request.getParameter(Constants.BUSINESS_CODE);
        bizCode = bizCode == null ? (String) request.getAttribute(Constants.BUSINESS_CODE) : bizCode;
        log.setBusinessCode(bizCode);
        log.setContent(content);
        log.setSubjectId(userId);
        log.setCreateTime(new Date());
        log.setType(1);
        return logDao.insert(log);
    }

    @Override
    public void add(String businessCode, String content) {
        User user = sessionProvider.getUser();
        String userId = user == null ? "" : user.getId();
        Log log = new Log();
        log.setBusinessCode(businessCode);
        log.setContent(content);
        log.setSubjectId(userId);
        log.setCreateTime(new Date());
        log.setType(1);
        logDao.insert(log);
    }

    @Override
    public List<Log> selectByExample(Log log) {
        Example example = new Example(Log.class);
        List<Log> list1 = logDao.select(log);
        Example.Criteria criteria = example.createCriteria();
        if (log.getBusinessCode() != null) {
            criteria.andNotEqualTo("businessCode", log.getBusinessCode());
        }
        if (log.getCreateTimeBegin() != null) {
            criteria.andBetween("createTime", log.getCreateTimeBegin(), null);
        }
        if (log.getCreateTimeEnd() != null) {
            criteria.andBetween("createTime", null, log.getCreateTimeEnd());
        }
        return logDao.selectByExample(example);
    }

    @Override
    public List<Log> selectList(Log log, Integer num, Integer size) {
        Map<String, Object> params = new HashMap<>();
        params.put("pagenum", (num-1)*size);
        params.put("log", log);
        params.put("pagesize", size);
        if (log.getType() == 1) {
            params.put("field", "tl.*,tu.username");
            params.put("table", "left join t_user tu on tl.subject_id=tu.id");
            params.put("condition", "1=1");
        } else if (log.getType() == 2) {
            params.put("field", " tl.*,ta.channel_name,ts.service_name");
            params.put("table", ",t_api_channel ta,t_api_service ts");
            params.put("condition", "tl.subject_id=ta.channel_code and tl.business_code=ts.service_code");
        }

        return logDao.selectList(log,params);
    }

    @Override
    public int selectListCount(Log log) {
        Map<String, Object> params = new HashMap<>();
        params.put("log", log);
        if (log.getType() == 1) {
            params.put("table", "left join t_user tu on tl.subject_id=tu.id");
            params.put("condition", "1=1");
        } else if (log.getType() == 2) {
            params.put("table", ",t_api_channel ta,t_api_service ts");
            params.put("condition", "tl.subject_id=ta.channel_code and tl.business_code=ts.service_code");
        }
        return logDao.selectListCount(log,params);
    }

}
