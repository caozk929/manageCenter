package com.zjht.manager.action.admin.content;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.LayuiResultDto;
import com.zjht.manager.common.dto.LayuiResultFailDto;
import com.zjht.manager.common.dto.LayuiResultSuccessDto;
import com.zjht.manager.common.web.WebSite;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.AdvPosition;
import com.zjht.manager.entity.ApiChannel;
import com.zjht.manager.entity.User;
import com.zjht.manager.service.AdvPositionService;
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
 * 〈广告图片位置管理AdvPositionAct〉
 *
 * @author wangpeng
 * @create 2017/9/4
 * @since 1.0.0
 */
@Controller
public class AdvPositionAct {
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
    @RequestMapping(value = "/content/advPosition/v_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(HttpServletRequest request, AdvPosition advPosition, ModelMap modelMap) {
        WebSite.setParameters(request, modelMap);
        if (advPosition == null) {
            advPosition = new AdvPosition();
        }

        getChannelMap(modelMap, null);
        modelMap.put("advPosition", advPosition);

        return "content/advPosition/list";
    }

    /**
     * 列表JSON数据
     */
    @ResponseBody
    @RequestMapping(value = "/content/advPosition/o_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(ModelMap modelMap, AdvPosition advPosition, Integer pageNum, Integer pageSize) {
        if (advPosition == null) {
            advPosition = new AdvPosition();
        }

        //根据分页参数查询数据
        PageInfo<AdvPosition> pageInfo = advPositionService.findPage(advPosition, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        getChannelMap(modelMap, pageInfo.getList());

        return new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 新增页面
     */
    @RequestMapping(value = "/content/advPosition/v_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(HttpServletRequest request, ModelMap modelMap) {
        WebSite.setParameters(request, modelMap);
        getChannelMap(modelMap, null);
        return "content/advPosition/add";
    }


    /**
     * 新增广告位置信息
     */
    @ResponseBody
    @RequestMapping(value = "/content/advPosition/o_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto v_add(HttpServletRequest request, ModelMap modelMap, AdvPosition advPosition) {
        checkParams(advPosition, request, modelMap);//参数处理

        advPosition.setCreateTime(new Date());

        if (advPositionService.insert(advPosition)) {
            logService.add("新增广告位置信息"+advPosition.getId());
            return new LayuiResultSuccessDto("新增成功");
        } else {
            return new LayuiResultFailDto("新增失败");
        }
    }


    /**
     * 修改页面
     */
    @RequestMapping(value = "/content/advPosition/v_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_edit(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request, modelMap);

        if (id == null) {
            throw new IllegalArgumentException("id不能为空");
        }

        modelMap.put("advPosition", advPositionService.findById(id));
        getChannelMap(modelMap, null);

        return "content/advPosition/edit";
    }

    /**
     * 修改位置信息
     */
    @ResponseBody
    @RequestMapping(value = "/content/advPosition/o_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_edit(HttpServletRequest request, ModelMap modelMap, AdvPosition advPosition) {
        checkParams(advPosition, request, modelMap);//参数处理

        if (advPosition.getId() == null) {
            throw new IllegalArgumentException("编辑id不能为空");
        }

        advPosition.setUpdateTime(new Date());

        if (advPositionService.update(advPosition)) {
            logService.add("更新广告位置信息"+advPosition.getId());
            return new LayuiResultSuccessDto("更新成功");
        } else {
            return new LayuiResultFailDto("更新失败");
        }
    }

    /**
     * 删除位置信息
     */
    @ResponseBody
    @RequestMapping(value = "/content/advPosition/o_delete.do", method = RequestMethod.POST)
    public LayuiResultDto o_delete(HttpServletRequest request, ModelMap modelMap, String ids) {
        WebSite.setParameters(request, modelMap);
        if (ids == null) {
            return new LayuiResultFailDto("位置信息Id不能为空");
        }
        advPositionService.deleteByIds(ids.split(","));
        logService.add("删除广告位置信息"+ids);
        return new LayuiResultSuccessDto();

    }

    /////////////私有方法////////////////

    /**
     * 获取渠道map
     * key:渠道编码  value:渠道名
     */
    private void getChannelMap(ModelMap modelMap, List<AdvPosition> advPositions) {
        List<ApiChannel> list = channelService.getListIsNotDelete(ApiChannel.Status.DELETED.getStatus());
        if (list.size() > 0) {
            Map<String, String> channelMap = new HashMap<>();
            for (ApiChannel apiChannel : list) {
                channelMap.put(apiChannel.getChannelCode(), apiChannel.getChannelName());
            }
            if (advPositions != null && advPositions.size() > 0) {
                for (AdvPosition advPosition : advPositions) {
                    advPosition.setChannelName(channelMap.get(advPosition.getChannel()));
                }
            }

            modelMap.put("channelMap", channelMap);
        }
    }

    /**
     * 检查参数是否存在  并且是否重复
     *
     * @param advPosition
     */
    private void checkParams(AdvPosition advPosition, HttpServletRequest request, ModelMap modelMap) {
        WebSite.setParameters(request, modelMap);//设置基础参数

        if (advPosition == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isNotBlank(advPosition.getId())) {
            map.put("id", advPosition.getId());
        }

        if (StringUtils.isNotBlank(advPosition.getName())) {
            map.put("name", advPosition.getName());
            if (advPositionService.selectByParams(map).size() > 0) {
                throw new IllegalArgumentException("该名称已存在");
            }
            map.remove("name");
        }

        if (StringUtils.isNotBlank(advPosition.getPositionCode())) {
            map.put("positionCode", advPosition.getPositionCode());
            if (advPositionService.selectByParams(map).size() > 0) {
                throw new IllegalArgumentException("该位置编码已存在");
            }
            map.remove("positionCode");
        }

        User user = sessionProvider.getUser();
        advPosition.setUserId(Integer.valueOf(user.getId()));
    }


}