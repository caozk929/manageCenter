package com.zjht.manager.action.admin.channelProduct;

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
import com.zjht.manager.entity.channelProduct.ChannelStrategy;
import com.zjht.manager.entity.channelProduct.ChannelStrategyParameter;
import com.zjht.manager.service.ChannelService;
import com.zjht.manager.service.ChannelStrategyParameterService;
import com.zjht.manager.service.ChannelStrategyService;
import com.zjht.manager.util.PageUtil;

@Controller
@RequestMapping("/channelProduct/strategy")
public class ChannelStrategyAct {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private ChannelStrategyService channelStrategyService;
    @Resource
    private SessionProvider sessionProvider;
    @Resource
    private ChannelService channelService;
    @Resource
    private ChannelStrategyParameterService channelStrategyParameterService;

    @RequestMapping("/list.do")
    public String listHtml(HttpServletRequest request, ModelMap model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, String bizCode, ChannelStrategy channelStrategy) {

        if (null == channelStrategy) {
            channelStrategy = new ChannelStrategy();
        }

        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("channelStrategy", channelStrategy);
        return "channelProduct/strategy/list";
    }

    @RequestMapping(value = "/add.do")
    public String add(HttpServletRequest request, ModelMap model, String bizCode) {
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        return "channelProduct/strategy/add";
    }

    @RequestMapping(value = "/update.do")
    public String update(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        ChannelStrategy channelStrategy = new ChannelStrategy();
        if (StringUtils.isNotEmpty(id)) {
            channelStrategy = channelStrategyService.getChannelStrategy(id);
            if (null == channelStrategy) {
                log.error("channelStrategy is null!");
            }
        }
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("channelStrategy", channelStrategy);
        return "channelProduct/strategy/update";
    }

    @ResponseBody
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    public ResultDto<Void> save(ChannelStrategy channelStrategy) {
        String id = channelStrategy.getId();

        ResultDto<Void> result = new ResultDto<Void>();
        int flag = 0;
        channelStrategy.setChannelCode(getChannelCode());
        if (StringUtils.isEmpty(id)) {
            channelStrategy.setId(null);
            flag = channelStrategyService.addChannelStrategy(channelStrategy);
        } else {
            flag = channelStrategyService.updateChannelStrategy(channelStrategy);
        }
        setResult(flag, result);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/del.do", method = RequestMethod.POST)
    public ResultDto<Void> del(String id) {
        int flag = channelStrategyService.deleteChannelStrategy(id);
        ResultDto<Void> result = new ResultDto<Void>();
        setResult(flag, result);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto<?> json(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, ChannelStrategy channelStrategy) {
        if (null == channelStrategy) {
            channelStrategy = new ChannelStrategy();
        }

        PageInfo<ChannelStrategy> pageInfo = channelStrategyService.getPage(channelStrategy, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    private String getChannelCode() {
        return channelService.getById(sessionProvider.getUser().getChannelId()).getChannelCode();
    }

    @ResponseBody
    @RequestMapping(value = "/addParameter.do", method = RequestMethod.POST)
    public ResultDto<Void> addParameter(String strategyId) {
        ChannelStrategyParameter channelStrategyParameter = new ChannelStrategyParameter();
        channelStrategyParameter.setStrategyId(strategyId);
        int flag = channelStrategyParameterService.insertChannelStrategyParameter(channelStrategyParameter);
        ResultDto<Void> result = new ResultDto<Void>();
        setResult(flag, result);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/listParameter.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto<?> listParameter(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, String strategyId) {

        ChannelStrategyParameter channelStrategyParameter = new ChannelStrategyParameter();
        channelStrategyParameter.setStrategyId(strategyId);

        PageInfo<ChannelStrategyParameter> pageInfo = channelStrategyParameterService.getPage(channelStrategyParameter, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @ResponseBody
    @RequestMapping(value = "/saveParameter.do", method = RequestMethod.POST)
    public ResultDto<Void> saveParameter(ChannelStrategyParameter channelStrategyParameter) {

        int flag = 0;
        if (StringUtils.isEmpty(channelStrategyParameter.getId())) {
            flag = channelStrategyParameterService.insertChannelStrategyParameter(channelStrategyParameter);
        } else {
            flag = channelStrategyParameterService.updateChannelStrategyParameter(channelStrategyParameter);
        }
        ResultDto<Void> result = new ResultDto<Void>();
        setResult(flag, result);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/delParameter.do", method = RequestMethod.POST)
    public ResultDto<Void> delParameter(ChannelStrategyParameter channelStrategyParameter) {
        int flag = channelStrategyParameterService.deleteChannelStrategyParameter(channelStrategyParameter.getId());
        ResultDto<Void> result = new ResultDto<Void>();
        setResult(flag, result);
        return result;
    }

    private void setResult(int flag, ResultDto<Void> result) {
        if (1 == flag) {
            result.setStatus(ApiConstants.SUCCESS);
            result.setMsg(ApiConstants.SUCCESS_MSG);
        } else {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
        }
    }

    @RequestMapping(value = "/addParam.do", method = RequestMethod.POST)
    public String addParam(HttpServletRequest request, ModelMap model, String bizCode, String strategyId) {
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("strategyId", strategyId);
        return "channelProduct/strategy/addParam";
    }

    @RequestMapping(value = "/updateParam.do", method = RequestMethod.POST)
    public String updateParam(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        ChannelStrategyParameter channelStrategyParameter = channelStrategyParameterService.getChannelStrategyParameter(id);
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("channelStrategyParameter", channelStrategyParameter);
        return "channelProduct/strategy/updateParam";
    }
}
