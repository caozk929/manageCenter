package com.zjht.manager.action.admin.channelProduct;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.ApiConstants;
import com.zjht.manager.common.dto.LayuiResultDto;
import com.zjht.manager.common.dto.LayuiResultSuccessDto;
import com.zjht.manager.common.dto.ResultDto;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.channelProduct.ChannelTag;
import com.zjht.manager.service.ChannelService;
import com.zjht.manager.service.ChannelTagService;
import com.zjht.manager.util.PageUtil;

@Controller
@RequestMapping("/channelProduct/tag")
public class ChannelTagAct {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private ChannelTagService channelTagService;
    @Resource
    private SessionProvider sessionProvider;
    @Resource
    private ChannelService channelService;

    @RequestMapping("/list.do")
    public String listHtml(HttpServletRequest request, ModelMap model, String bizCode, ChannelTag tag) {

        if (null == tag) {
            tag = new ChannelTag();
        }

        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("tag", tag);

        return "channelProduct/tag/list";
    }

    @RequestMapping(value = "/addOrUpdate.do")
    public String addOrUpdate(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        ChannelTag tag = new ChannelTag();
        if (StringUtils.isNotEmpty(id)) {
            tag = channelTagService.getTag(id);
            if (null == tag) {
                log.error("tag is null!");
            }
        }
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("tag", tag);
        return "channelProduct/tag/addOrUpdate";
    }

    @ResponseBody
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    public ResultDto<Void> save(ChannelTag tag) {
        String id = tag.getId();

        ResultDto<Void> result = new ResultDto<Void>();
        int flag = 0;
        if (StringUtils.isEmpty(id)) {
            tag.setId(null);
            tag.setChannelCode(getChannelCode());
            flag = channelTagService.addTag(tag);
        } else {
            flag = channelTagService.updateTag(tag);
        }
        setResult(result, flag);
        return result;
    }

    private void setResult(ResultDto<Void> result, int flag) {
        if (1 == flag) {
            result.setStatus(ApiConstants.SUCCESS);
            result.setMsg(ApiConstants.SUCCESS_MSG);
        } else {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/del.do", method = RequestMethod.POST)
    public ResultDto<Void> del(String id) {
        int flag = channelTagService.deleteTag(id);
        ResultDto<Void> result = new ResultDto<Void>();
        setResult(result, flag);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto<?> json(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, ChannelTag tag) {
        if (null == tag) {
            tag = new ChannelTag();
        }
        tag.setChannelCode(getChannelCode());
        PageInfo<ChannelTag> pageInfo = channelTagService.getPage(tag, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @ResponseBody
    @RequestMapping(value = "/select.json")
    public List<Select> select(String tagName) {
        return channelTagService.getSelect(tagName, getChannelCode());
    }

    private String getChannelCode() {
        return channelService.getById(sessionProvider.getUser().getChannelId()).getChannelCode();
    }
}
