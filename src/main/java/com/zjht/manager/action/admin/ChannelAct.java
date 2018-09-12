package com.zjht.manager.action.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.api.dto.EventData;
import com.zjht.manager.common.dto.LayuiResultDto;
import com.zjht.manager.common.dto.LayuiResultFailDto;
import com.zjht.manager.common.dto.LayuiResultSuccessDto;
import com.zjht.manager.common.dto.ResultDto;
import com.zjht.manager.common.dto.ResultSuccessDto;
import com.zjht.manager.common.message.MsgTemplate;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.common.web.annotation.NoNeedAuth;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.entity.ApiChannelAuth;
import com.zjht.manager.entity.ApiService;
import com.zjht.manager.service.ApiChannelAuthService;
import com.zjht.manager.service.ApiServiceService;
import com.zjht.manager.service.ChannelService;
import com.zjht.manager.service.LogService;
import com.zjht.manager.util.PageUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 *  渠道ACTION
 * @outhor caozk
 * @create 2017-09-05 19:44
 */
@Controller
public class ChannelAct {
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ApiServiceService apiServiceService;
    @Autowired
    private ApiChannelAuthService channelAuthService;
    @Autowired
    private LogService logService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private MsgTemplate msgTemplate;

    private final Logger log = LoggerFactory.getLogger(ChannelAct.class);

    @RequestMapping(value = "/channel/v_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_list(HttpServletRequest request,ModelMap modelMap,
                         ApiChannel channel, Integer pageNum, Integer pageSize) {
        WebSite.setParameters(request,modelMap);
        if(channel == null){
            channel = new ApiChannel();
        }
        modelMap.put("channel", channel);
        return "channel/list";
    }

    /**
     * 新增页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/channel/v_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(HttpServletRequest request,ModelMap modelMap) {
        WebSite.setParameters(request,modelMap);
        List<ApiService> serviceList = apiServiceService.getList(null);
        modelMap.addAttribute("serviceList", serviceList);
        return "channel/add";
    }

    /**
     * 新增请求
     * @param request
     * @param modelMap
     * @param channel
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channel/o_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto v_add(HttpServletRequest request, ModelMap modelMap, ApiChannel channel,String[] serviceIds) {
        WebSite.setParameters(request,modelMap);
        if(channel == null){
            channel = new ApiChannel();
        }
        if(StringUtils.isBlank(channel.getChannelName())){
            return new LayuiResultFailDto("渠道不能为空");
        }
        if(StringUtils.isBlank(channel.getChannelCode())){
            return new LayuiResultFailDto("渠道编码不能为空");
        }
        if(StringUtils.isBlank(channel.getChannelAccount())){
            return new LayuiResultFailDto("渠道账号不能为空");
        }
        channel.setCreateTime(new Date());
        //添加渠道结果
        ResultDto dealResult = channelService.addChannel(channel, serviceIds);
        logService.add("添加渠道" + channel.getId());
        try {
            EventData eventData = new EventData<ApiChannel>(EventData.OperatType.insert.name(),channel);
            msgTemplate.sendMsg(ApiChannel.TOPIC, JSONObject.toJSONString(eventData));
        } catch (Exception e) {
            log.error("渠道新增信息发布失败：", e);
        }
        return dealResult;
    }
    @ResponseBody
    @RequestMapping(value = "/channel/o_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap,
                                 ApiChannel channel, Integer pageNum, Integer pageSize) {
        if(channel == null){
            channel = new ApiChannel();
        }
        //1.根据分页参数查询数据
        channel.setOrderByClause(" id desc");
        PageInfo<ApiChannel> pageInfo = channelService.getPage(channel, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null,pageInfo.getList(),pageInfo.getTotal());
        return resultDto;
    }
    /**
     * 修改页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/channel/v_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_edit(HttpServletRequest request,ModelMap modelMap,String id) {
        WebSite.setParameters(request,modelMap);
        if(id == null){
            throw new IllegalArgumentException("id不能为空");
        }
        ApiChannel apiChannel = channelService.getById(id);
        if(apiChannel == null){
            throw new IllegalArgumentException("渠道不存在，请勿非法操作");
        }
        List<ApiService> serviceList = apiServiceService.getList(null);
        List<ApiChannelAuth> channelAuthList = channelAuthService.getListByChannelId(id);
        modelMap.addAttribute("serviceList", serviceList);
        modelMap.put("channelAuthList", channelAuthList);
        modelMap.put("apiChannel", apiChannel);
        return "channel/edit";
    }

    /**
     * 修改页面
     * @param request
     * @param modelMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channel/o_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_edit(HttpServletRequest request,ModelMap modelMap,ApiChannel channel,String[] serviceIds, boolean isUpdateField) {
        WebSite.setParameters(request,modelMap);
        if(channel == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        if(channel.getId() == null){
            throw new IllegalArgumentException("编辑id不能为空");
        }
        //如果不是更新域
        if(!isUpdateField){
            if(StringUtils.isBlank(channel.getChannelName())){
                return new LayuiResultFailDto("渠道不能为空");
            }
            if(StringUtils.isBlank(channel.getChannelCode())){
                return new LayuiResultFailDto("渠道编码不能为空");
            }
            if(StringUtils.isBlank(channel.getChannelAccount())){
                return new LayuiResultFailDto("渠道账号不能为空");
            }
        }
        logService.add("修改渠道" + channel.getId());
        ResultDto resultDto = channelService.updateSelective(channel, serviceIds);
        try {
            EventData eventData = new EventData<ApiChannel>(EventData.OperatType.update.name(),channel);
            msgTemplate.sendMsg(ApiChannel.TOPIC, JSONObject.toJSONString(eventData));
        } catch (Exception e) {
            log.error("渠道修改信息发布失败：", e);
        }
        return resultDto;
    }

    /**
     * 删除渠道
     * @param request
     * @param modelMap
     * @param ids id串
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/channel/o_delete.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_delete(HttpServletRequest request, ModelMap modelMap, String ids) {
        WebSite.setParameters(request,modelMap);
        if(ids == null){
            return new LayuiResultFailDto("渠道id不能为空");
        }
        String[] idArr = ids.split(",");
        channelService.deleteByIds(idArr);
        logService.add("删除渠道" + ids);
        LayuiResultDto resultDto = new LayuiResultSuccessDto();
        return resultDto;
    }

    /**
     * 渠道秘钥生成
     * @param request
     * @param modelMap
     * @param channel
     * @return
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/channel/generateKey.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto generateKey(HttpServletRequest request, ModelMap modelMap, ApiChannel channel) {
        WebSite.setParameters(request,modelMap);
        if(channel == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        //如果不是修改状态
        if(StringUtils.isBlank(channel.getChannelName())){
            return new LayuiResultFailDto("渠道不能为空");
        }
        if(StringUtils.isBlank(channel.getChannelCode())){
            return new LayuiResultFailDto("渠道编码不能为空");
        }
        if(StringUtils.isBlank(channel.getChannelAccount())){
            return new LayuiResultFailDto("渠道账号不能为空");
        }
        String plaintext = channel.getChannelCode() + channel.getChannelAccount() + channel.getChannelName() + System.currentTimeMillis();
        channel.setChannelKey(DigestUtils.md5Hex(plaintext));
        ResultDto resultDto = new ResultSuccessDto();
        resultDto.setData(channel);
        return resultDto;
    }
}
