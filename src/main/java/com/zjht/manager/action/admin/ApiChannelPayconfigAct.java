package com.zjht.manager.action.admin;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.*;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.entity.ApiChannelPayconfig;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.ApiChannelPayconfigService;
import com.zjht.manager.service.ChannelService;
import com.zjht.manager.service.LogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道支付配置
 * Created by vip on 2017/9/19.
 */
@Controller
public class ApiChannelPayconfigAct {

    @Autowired
    private ApiChannelPayconfigService acpServicen;//支付配置
    @Autowired
    private ChannelService channelService;//渠道
    @Autowired
    private LogService logService;//日志记录
    @Autowired
    private SessionProvider sessionProvider;
    //进入列表
    @RequestMapping(value = "/acp/v_list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_list(ModelMap modelMap, HttpServletRequest request, ApiChannelPayconfig acp) {
        //1.保存请求参数
        WebSite.setParameters(request, modelMap);
        //2.保存查询参数
        modelMap.put("acp", acp);
        getChannelMap(modelMap,null);
        return "channel/payconfig/list";
    }

    //异步获取数据
    @ResponseBody
    @RequestMapping(value = "/acp/o_list.json", method = {RequestMethod.POST})
    public LayuiResultDto o_list(ApiChannelPayconfig acp, Integer pageNum, Integer pageSize,ModelMap modelMap) {
        //1.查询分页查询
        PageInfo<ApiChannelPayconfig> pageInfo =  acpServicen.findPage(acp, pageNum, pageSize);
        //1.1重新查询渠道名称
        List<ApiChannelPayconfig> list = pageInfo.getList();
        getChannelMap(modelMap,list);
        //2.封装参数返回
        return new LayuiResultSuccessDto(null, list, pageInfo.getTotal());
    }

    //进入新增页面
    @RequestMapping(value = "/acp/v_add.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_add(ModelMap modelMap, HttpServletRequest request) {
        //1.保存请求参数
        WebSite.setParameters(request, modelMap);
        //2.查询渠道信息
        //2.1构建查询参数
        ApiChannel apiChannel = new ApiChannel();
        apiChannel.setStatus(1);//只查询在用的
        //2.2查询数据并保存
        List<ApiChannel> apiChannels = channelService.getList(apiChannel);
        modelMap.put("apiChannels", apiChannels);
        return "channel/payconfig/add";
    }

    //异步保存
    @ResponseBody
    @RequestMapping(value = "/acp/o_add.do", method = RequestMethod.POST)
    public ResultDto o_add(ApiChannelPayconfig acp) {
        //1.校验参数是否正确和合法
        if (checkParamData(acp)) {
            return new ResultFailDto("参数错误...");
        }
        //2.设置对应的参数{1.创建时间，2.操作人员}
        acp.setCreateTime(new Date());
        User user = sessionProvider.getUser();
        acp.setUserId(Long.valueOf(user.getId()));
        //3.保存记录
        acpServicen.add(acp);
        //4.保存操作日志
        logService.add("新增渠道支付配置");
        return new ResultSuccessDto("操作成功...");
    }

    //进入编辑页面
    @RequestMapping(value = "/acp/v_edit.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_edit(ModelMap modelMap, HttpServletRequest request, String id) {
        //1.保存请求参数
        WebSite.setParameters(request, modelMap);
        //2.查询支付配置信息
        //2.1判断参数
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("支付配置不存在，原因：已经被删除");
        }
        ApiChannelPayconfig acp = acpServicen.findById(id);
        if (acp == null) {
            throw new IllegalArgumentException("支付配置不存在，原因：已经被删除");
        }
        modelMap.put("acp", acp);
        //3.查询渠道信息
        //3.1构建查询对象
        ApiChannel apiChannel = new ApiChannel();
        apiChannel.setStatus(1);//只查询在用的
        //3.2查询数据并保存
        List<ApiChannel> apiChannels = channelService.getList(apiChannel);
        modelMap.put("apiChannels", apiChannels);
        return "channel/payconfig/edit";
    }

    //异步保存数据
    @ResponseBody
    @RequestMapping(value = "/acp/o_edit.do", method = RequestMethod.POST)
    public ResultDto o_edit(ApiChannelPayconfig acp) {
        //1.校验参数
        if (checkParamData(acp)) {
            return new ResultFailDto("参数错误...");
        }
        //2.设置修改时间
        acp.setUpdateTime(new Date());
        //3.保存数据
        acpServicen.update(acp);
        //4.保存操作记录
        logService.add("编辑渠道支付配置");
        return new ResultSuccessDto("操作成功...");
    }

    //单个删除
    @ResponseBody
    @RequestMapping(value = "/acp/deleteById.do", method = RequestMethod.POST)
    public ResultDto deleteById(String id) {
        //1.校验参数
        if (StringUtils.isBlank(id)) {
            return new ResultFailDto("参数错误...");
        }
        //2.删除
        acpServicen.deleteById(id);
        //3.记录操作日志
        logService.add("删除渠道支付配置Id:" + id);
        return new ResultSuccessDto("操作成功...");
    }

    //批量删除
    @ResponseBody
    @RequestMapping(value = "/acp/deleteByIds.do", method = RequestMethod.POST)
    public ResultDto deleteByIds(@RequestParam(value = "ids[]") String[] ids) {
        //1.校验参数
        if (ids == null || ids.length == 0) {
            return new ResultFailDto("参数错误...");
        }
        //2.删除
        acpServicen.deleteByIds(ids);
        //3.记录操作日志
        logService.add("批量删除渠道支付配置Ids:" + ids);
        return new ResultSuccessDto("操作成功...");
    }

    /**
     * 校验参数
     * @param acp
     * @return
     */
    private boolean checkParamData(ApiChannelPayconfig acp) {
        if (acp == null) {
            return true;
        }
        if (StringUtils.isBlank(acp.getConfigName())) {//名称
            return true;
        }
        if (StringUtils.isBlank(acp.getConfigCode())) {//编码
            return true;
        }
        if (StringUtils.isBlank(acp.getPartnerCode())) {//合作商编码
            return true;
        }
        if (StringUtils.isBlank(acp.getPayAccountName())) {//支付账号
            return true;
        }
        if (StringUtils.isBlank(acp.getPrivateKeyFileName())) {//私钥文件名
            return true;
        }
        if (StringUtils.isBlank(acp.getPrivateKeyPwd())) {//私钥密码
            return true;
        }
        if (StringUtils.isBlank(acp.getPublicKeyFileName())) {//公钥文件名
            return true;
        }
        if (StringUtils.isBlank(acp.getSignType())) {//签名加密类型
            return true;
        }
        return false;
    }

    /**
     * 生成渠道Map
     * @param modelMap
     * @param apiChannelPayconfigs
     */
    private void getChannelMap(ModelMap modelMap,List<ApiChannelPayconfig> apiChannelPayconfigs){
        List<ApiChannel> ApiChannels = channelService.getListIsNotDelete(ApiChannel.Status.DELETED.getStatus());
        if (ApiChannels.size() > 0) {
            Map<String, String> channelMap = new HashMap<>();
            for (ApiChannel apiChannel : ApiChannels) {
                channelMap.put(apiChannel.getId(), apiChannel.getChannelName());
            }
            if(apiChannelPayconfigs!=null && apiChannelPayconfigs.size()>0){
                for(ApiChannelPayconfig apiChannelPayconfig : apiChannelPayconfigs){
                    apiChannelPayconfig.setChannelName(channelMap.get(apiChannelPayconfig.getChannelId()));
                }
            }

            modelMap.put("channelMap", channelMap);
        }
    }
}
