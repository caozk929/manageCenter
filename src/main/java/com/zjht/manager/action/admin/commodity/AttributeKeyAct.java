package com.zjht.manager.action.admin.commodity;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.zjht.manager.service.RedisCacheService;
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
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.commodity.AttributeKey;
import com.zjht.manager.service.AttributeKeyService;
import com.zjht.manager.util.PageUtil;

@Controller
@RequestMapping("/commodity/attributeKey")
public class AttributeKeyAct {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private AttributeKeyService attributeKeyService;
    @Resource
    private RedisCacheService redisCacheService;
    private static final String ATTRIBUTEKEY_REDIS_KEY = "attrubuteKey:";

    @RequestMapping("/list.do")
    public String listHtml(HttpServletRequest request, ModelMap model, String bizCode, AttributeKey attributeKey) {

        if (null == attributeKey) {
            attributeKey = new AttributeKey();
        }

        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("attributeKey", attributeKey);

        return "commodity/attributeKey/list";
    }

    @RequestMapping(value = "/addOrUpdate.do")
    public String addOrUpdate(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        AttributeKey attributeKey = new AttributeKey();
        if (StringUtils.isNotEmpty(id)) {
            attributeKey = attributeKeyService.getAttributeKey(id);
            if (null == attributeKey) {
                log.error("attributeKey is null!");
            }
        }
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("attributeKey", attributeKey);
        return "commodity/attributeKey/addOrUpdate";
    }

    @ResponseBody
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    public ResultDto<Void> save(AttributeKey attributeKey) {
        String id = attributeKey.getId();

        ResultDto<Void> result = new ResultDto<Void>();

        if (StringUtils.isEmpty(id)) {
            attributeKey.setId(null);
            AttributeKey attributeKey1 = attributeKeyService.addAttributeKey(attributeKey);

            redisCacheService.setCacheObject(ATTRIBUTEKEY_REDIS_KEY + attributeKey1.getAttributeKeyCode(), JSONObject.toJSON(attributeKey1));
        } else {
            AttributeKey attributeKey2=  attributeKeyService.updateAttributeKey(attributeKey);
            redisCacheService.setCacheObject(ATTRIBUTEKEY_REDIS_KEY + attributeKey2.getAttributeKeyCode(), JSONObject.toJSON(attributeKey2));
        }

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/del.do", method = RequestMethod.POST)
    public ResultDto<Void> del(String id, String attributeKeyCode) {
        int flag = attributeKeyService.deleteAttributeKey(id);
        ResultDto<Void> result = new ResultDto<Void>();
        if (1 == flag) {
            redisCacheService.remove(ATTRIBUTEKEY_REDIS_KEY + attributeKeyCode);//删除Redis缓存中对应的数据
            result.setStatus(ApiConstants.SUCCESS);
            result.setMsg(ApiConstants.SUCCESS_MSG);
        } else {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto<?> json( @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, AttributeKey attributeKey) {
        if (null == attributeKey) {
            attributeKey = new AttributeKey();
        }
        attributeKey.setOrderByClause(" id desc");
        PageInfo<AttributeKey> pageInfo = attributeKeyService.getPage(attributeKey, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @ResponseBody
    @RequestMapping(value = "/select.json")
    public List<Select> select(String attributeKeyName) {
        return attributeKeyService.getSelect(attributeKeyName);
    }
}
