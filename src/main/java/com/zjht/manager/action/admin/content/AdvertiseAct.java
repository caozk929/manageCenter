package com.zjht.manager.action.admin.content;

import com.zjht.manager.common.dto.*;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.AdvPosition;
import com.zjht.manager.entity.Advertise;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.AdvPositionService;
import com.zjht.manager.service.AdvertiseService;
import com.zjht.manager.service.ChannelService;
import com.zjht.manager.service.LogService;
import com.zjht.manager.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈广告图片信息管理AdvertiseAct〉
 *
 * @author wangpeng
 * @create 2017/9/4
 * @since 1.0.0
 */
@Controller
public class AdvertiseAct {

    @Autowired
    private AdvertiseService advertiseService;
    @Autowired
    private AdvPositionService advPositionService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private LogService logService;
    @Autowired
    private SessionProvider sessionProvider;

    /**
     * 进入列表页面
     */
    @RequestMapping(value = "/content/advertise/v_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(HttpServletRequest request, Advertise advertise, ModelMap modelMap) {
        WebSite.setParameters(request, modelMap);
        if (advertise == null) {
            advertise = new Advertise();
        }

        getAdvPositionMap(modelMap, null);
        modelMap.put("advertise", advertise);

        return "content/advertise/list";
    }

    /**
     * 列表JSON数据
     */
    @ResponseBody
    @RequestMapping(value = "/content/advertise/o_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(ModelMap modelMap, Advertise advertise, Integer pageNum, Integer pageSize) {
        if (advertise == null) {
            advertise = new Advertise();
        }

        int total = advertiseService.getAdvertiseListCount(advertise);
        List<Advertise> list = advertiseService.getAdvertiseList(advertise, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));

        getAdvPositionMap(modelMap, list);

        return new LayuiResultSuccessDto(null, list, total);
    }

    /**
     * 新增页面
     */
    @RequestMapping(value = "/content/advertise/v_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(HttpServletRequest request, ModelMap modelMap) {
        WebSite.setParameters(request, modelMap);
        getAdvPositionMap(modelMap, null);
        return "content/advertise/add";
    }


    /**
     * 新增图片信息
     */
    @ResponseBody
    @RequestMapping(value = "/content/advertise/o_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto v_add(HttpServletRequest request, ModelMap modelMap, Advertise advertise) {
        checkParams(advertise, request, modelMap);//参数处理

        advertise.setAdvCreateTime(new Date());

        if (advertiseService.insert(advertise)) {
            logService.add("新增广告图片信息"+advertise.getAdvId());
            return new LayuiResultSuccessDto("新增成功");
        } else {
            return new LayuiResultFailDto("新增失败");
        }
    }


    /**
     * 修改页面
     */
    @RequestMapping(value = "/content/advertise/v_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_edit(HttpServletRequest request, ModelMap modelMap, String advId) {
        WebSite.setParameters(request, modelMap);
        if (advId == null) {
            throw new IllegalArgumentException("id不能为空");
        }

        Advertise advertise = advertiseService.findByAdvId(advId);

        modelMap.put("advertise", advertise);
        getAdvPositionMap(modelMap, null);
        return "content/advertise/edit";
    }

    /**
     * 修改图片信息
     */
    @ResponseBody
    @RequestMapping(value = "/content/advertise/o_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_edit(HttpServletRequest request, ModelMap modelMap, Advertise advertise) {
        checkParams(advertise, request, modelMap);//参数处理

        advertise.setAdvUpdateTime(new Date());

        if (advertiseService.update(advertise)) {
            logService.add("更新广告图片信息"+advertise.getAdvId());
            return new LayuiResultSuccessDto("更新成功");
        } else {
            return new LayuiResultFailDto("更新失败");
        }
    }


    /**
     * 删除图片信息
     */
    @ResponseBody
    @RequestMapping(value = "/content/advertise/o_delete.do", method = RequestMethod.POST)
    public LayuiResultDto o_delete(HttpServletRequest request, ModelMap modelMap, String ids) {
        WebSite.setParameters(request, modelMap);
        if (ids == null) {
            return new LayuiResultFailDto("图片信息Id不能为空");
        }
        advertiseService.deleteByIds(ids.split(","));
        logService.add("删除广告图片信息"+ids);
        return new LayuiResultSuccessDto();

    }

    /**
     * 获取渠道名称
     *
     * @param request
     * @param modelMap
     * @param advPositionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/content/advertise/o_getChannelName.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_getChannelName(HttpServletRequest request, ModelMap modelMap, String advPositionId) {
        WebSite.setParameters(request, modelMap);
        ResultDto result = new ResultDto();

        if (StringUtils.isBlank(advPositionId)) {
            throw new IllegalArgumentException("参数不能为空");
        }
        String channelName = advertiseService.getChannelName(advPositionId);

        result.setData(channelName);
        result.setStatus(ApiConstants.SUCCESS);
        return result;
    }

    /////////////私有方法////////////

    /**
     * 生成可用的广告位置信息以及渠道map
     * key:广告位置编码  value:广告位置名称
     */
    private void getAdvPositionMap(ModelMap modelMap, List<Advertise> advertises) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", ApiChannel.Status.NORMAL.getStatus());
        List<AdvPosition> list = advPositionService.selectByParams(map);
        if (list.size() > 0) {
            Map<String, String> advPositionMap = new HashMap<>();
            for (AdvPosition advPosition : list) {
                advPositionMap.put(advPosition.getPositionCode(), advPosition.getName());
            }
            if (advertises != null && advertises.size() > 0) {
                for (Advertise advertise : advertises) {
                    advertise.setAdvPositionName(advPositionMap.get(advertise.getAdvPositionId()));
                }
            }
            modelMap.put("advPositionMap", advPositionMap);

        }

        List<ApiChannel> ApiChannels = channelService.getListIsNotDelete(ApiChannel.Status.DELETED.getStatus());
        if (list.size() > 0) {
            Map<String, String> channelMap = new HashMap<>();
            for (ApiChannel apiChannel : ApiChannels) {
                channelMap.put(apiChannel.getChannelCode(), apiChannel.getChannelName());
            }

            modelMap.put("channelMap", channelMap);
        }
    }

    /**
     * 检查参数是否存在  并且是否重复
     *
     * @param advertise
     */
    private void checkParams(Advertise advertise, HttpServletRequest request, ModelMap modelMap) {
        WebSite.setParameters(request, modelMap);//设置基础参数

        if (advertise == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isNotBlank(advertise.getAdvId())) {
            map.put("advId", advertise.getAdvId());
        }

        if (StringUtils.isNotBlank(advertise.getAdvName())) {
            map.put("advName", advertise.getAdvName());
            if (advertiseService.findByParams(map).size() > 0) {
                throw new IllegalArgumentException("该名称已存在");
            }
        }

        User user = sessionProvider.getUser();
        advertise.setUserId(Integer.valueOf(user.getId()));

    }


}