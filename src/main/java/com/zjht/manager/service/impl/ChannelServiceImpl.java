package com.zjht.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.LayuiResultFailDto;
import com.zjht.manager.common.dto.ResultDto;
import com.zjht.manager.common.dto.ResultFailDto;
import com.zjht.manager.common.dto.ResultSuccessDto;
import com.zjht.manager.dao.ApiChannelAuthDao;
import com.zjht.manager.dao.ApiChannelDao;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.entity.ApiChannelAuth;
import com.zjht.manager.service.ChannelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 渠道service实现
 *
 * @outhor caozk
 * @create 2017-09-05 19:46
 */
@Transactional
@Service
public class ChannelServiceImpl implements ChannelService{
    @Autowired
    private ApiChannelDao apiChannelDao;
    @Autowired
    private ApiChannelAuthDao channelAuthDao;
    @Override
    public ApiChannel getById(String id) {
        return apiChannelDao.selectByPrimaryKey(id);
    }

    @Override
    public ResultDto addChannel(ApiChannel channel, String[] serviceIds) {

        ApiChannel channelQuery = new ApiChannel();
        channelQuery.setChannelCode(channel.getChannelCode());
        List<ApiChannel> existCodeList = apiChannelDao.select(channelQuery);
        if(existCodeList!=null && !existCodeList.isEmpty()){
            return new ResultFailDto("渠道编码已经存在");
        }
        //插入渠道
        apiChannelDao.insert(channel);
        //插入渠道权限
        insertChannelAuth(channel, serviceIds);
        /*List<ApiChannelAuth> serviceList = new ArrayList<ApiChannelAuth>();
        ApiChannelAuth sevice1 = new ApiChannelAuth();
        sevice1.setId("1");
        sevice1.setChannelId(channel.getId());
        ApiChannelAuth sevice2 = new ApiChannelAuth();
        sevice2.setChannelId(channel.getId());
        sevice2.setId("2");
        serviceList.add(sevice1);
        serviceList.add(sevice2);
        channelAuthDao.batchInsert(serviceList);*/
        return new ResultSuccessDto();
    }

    @Override
    public void update(ApiChannel channel, String[] serviceIds) {
        //更新渠道信息
        apiChannelDao.updateByPrimaryKey(channel);
        //根据渠道id删除渠道权限
        ApiChannelAuth delQuery = new ApiChannelAuth();
        delQuery.setChannelId(channel.getId());
        channelAuthDao.delete(delQuery);
        //重新插入渠道权限
        insertChannelAuth(channel, serviceIds);
    }

    @Override
    public ResultDto updateSelective(ApiChannel channel, String[] serviceIds) {

        if(channel.getChannelCode() != null){
            ApiChannel channelQuery = new ApiChannel();
            channelQuery.setChannelCode(channel.getChannelCode());
            List<ApiChannel> existCodeList = apiChannelDao.select(channelQuery);
            if(existCodeList!=null &&
                    (existCodeList.size()>1 ||(existCodeList.size()==1 && !existCodeList.get(0).getId().equals(channel.getId())))){
                return new ResultFailDto("渠道编码已经存在");
            }
        }
        apiChannelDao.updateByPrimaryKeySelective(channel);
        //根据渠道id删除渠道权限
        ApiChannelAuth delQuery = new ApiChannelAuth();
        delQuery.setChannelId(channel.getId());
        channelAuthDao.delete(delQuery);
        //重新插入渠道权限
        insertChannelAuth(channel, serviceIds);
        return new ResultSuccessDto();
    }

    /**
     * 插入渠道权限
     * @param channel
     * @param serviceIds
     */
    private void insertChannelAuth(ApiChannel channel, String[] serviceIds) {
        if(serviceIds != null){
            for(int i=0; i<serviceIds.length; i++){
                ApiChannelAuth channelAuth = new ApiChannelAuth();
                channelAuth.setChannelId(channel.getId());
                channelAuth.setServiceId(serviceIds[i]);
                channelAuthDao.insert(channelAuth);
            }
        }
    }

    @Override
    public void delete(ApiChannel channel) {
        //根据渠道id删除渠道权限
        ApiChannelAuth delQuery = new ApiChannelAuth();
        delQuery.setChannelId(channel.getId());
        channelAuthDao.delete(delQuery);
        //删除渠道
        apiChannelDao.delete(channel);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (int i=0; i<ids.length; i++){
            //根据渠道id删除渠道权限
            ApiChannelAuth delQuery = new ApiChannelAuth();
            delQuery.setChannelId(ids[i]);
            channelAuthDao.delete(delQuery);

            apiChannelDao.deleteByPrimaryKey(ids[i]);
        }
    }

    @Override
    public void updateStatus(String[] ids, int status) {
        for (int i=0; i<ids.length; i++){
            ApiChannel channel = new ApiChannel();
            channel.setId(ids[i]);
            channel.setStatus(status);
            apiChannelDao.updateByPrimaryKeySelective(channel);
        }
    }

    @Override
    public List<ApiChannel> getList(ApiChannel channel) {
        return apiChannelDao.select(channel);
    }

    @Override
    public PageInfo<ApiChannel> getPage(ApiChannel channel, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(ApiChannel.class);
        example.setOrderByClause(channel.getOrderByClause());
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(channel.getChannelName())){
            criteria.andLike("channelName","%" + channel.getChannelName() + "%");
        }
        //已删除状态的不查询
        criteria.andNotEqualTo("status",ApiChannel.Status.DELETED.getStatus());

        List<ApiChannel> list = apiChannelDao.selectByExample(example);

        PageInfo<ApiChannel> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 根据Id获取名字
     *
     * @param id
     * @return
     */
    @Override
    public String getNameById(String id) {
        return apiChannelDao.getNameById(id);
    }

    @Override
    public List<ApiChannel> getListIsNotDelete(int status) {
        return apiChannelDao.getListIsNotDelete(status);
    }
}
