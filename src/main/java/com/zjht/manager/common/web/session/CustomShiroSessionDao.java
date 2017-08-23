package com.zjht.manager.common.web.session;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository("customShiroSessionDao")
public class CustomShiroSessionDao extends EnterpriseCacheSessionDAO {

    @Resource(name="redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    
    /**
     * 创建session，保存到redis集群中
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        System.out.println("sessionId: " + sessionId);
        
        BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps("shiro_session_" + sessionId.toString());
        sessionValueOperations.set(session);
        sessionValueOperations.expire(30, TimeUnit.MINUTES);
        
        return sessionId;
    }

    /**
     * 获取session
     * @param sessionId sessionid
     * @return Session
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(sessionId);
        
        if(session == null){
            BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps("shiro_session_" + sessionId.toString());
            session = (Session) sessionValueOperations.get();
        }
        
        return session;
    }

    /**
     * 更新session
     * @param session session
     */
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        
        BoundValueOperations<String, Object> sessionValueOperations = redisTemplate.boundValueOps("shiro_session_" + session.getId().toString());
        sessionValueOperations.set(session);
        sessionValueOperations.expire(30, TimeUnit.MINUTES);
    }

    /**
     * 删除失效session
     */
    @Override
    protected void doDelete(Session session) {
        redisTemplate.delete("shiro_session_" + session.getId().toString());
        super.doDelete(session);
    }

}