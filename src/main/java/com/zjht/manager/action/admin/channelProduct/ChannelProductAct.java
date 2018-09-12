package com.zjht.manager.action.admin.channelProduct;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.*;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.channelProduct.*;
import com.zjht.manager.entity.commodity.AttributeKey;
import com.zjht.manager.entity.commodity.Category;
import com.zjht.manager.entity.commodity.Product;
import com.zjht.manager.service.*;
import com.zjht.manager.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyaohui
 * @since Sep 19, 2017 10:27:14 AM
 */
@Controller
@RequestMapping("/channelProduct")
public class ChannelProductAct {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private ChannelProductService channelProductService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private ProductService productService;
    @Resource
    private ChannelGoodsService channelGoodsService;
    @Resource
    private SessionProvider sessionProvider;
    @Resource
    private ChannelService channelService;

    @RequestMapping("/list.do")
    public String listHtml(HttpServletRequest request, ModelMap model, String bizCode, ChannelProduct channelProduct) {

        if (null == channelProduct) {
            channelProduct = new ChannelProduct();
        }

        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("channelProduct", channelProduct);
        return "channelProduct/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list.json")
    public LayuiResultDto<?> json(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, ChannelProduct channelProduct) {
        if (null == channelProduct) {
            channelProduct = new ChannelProduct();
        }
        String channelCode = getChannelCode();
        channelProduct.setChannelCode(channelCode);
        PageInfo<ChannelProduct> pageInfo = channelProductService.getPage(channelProduct, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/bind.do")
    public String bind(HttpServletRequest request, ModelMap model, String bizCode, ChannelProduct channelProduct) {
        if (null == channelProduct) {
            channelProduct = new ChannelProduct();
        } else if (StringUtils.isNotEmpty(channelProduct.getCategoryCode())) {
            Category category = categoryService.getCategoryByCode(channelProduct.getCategoryCode());
            if (null == category) {
                log.error("category is null！");
            } else {
                model.put("category", category);
            }
        }
        List<Select> lstSelect = categoryService.getTreeSelect();
        model.put("tree", JSONObject.toJSONString(lstSelect));
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("channelProduct", channelProduct);
        return "channelProduct/bind";
    }

    @ResponseBody
    @RequestMapping(value = "/bind.json")
    public LayuiResultDto<?> bindJson(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, ChannelProduct channelProduct) {
        if (null == channelProduct) {
            channelProduct = new ChannelProduct();
        }

        String channelCode = getChannelCode();
        channelProduct.setChannelCode(channelCode);
        PageInfo<ChannelProduct> pageInfo = channelProductService.getPageForBind(channelProduct, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @ResponseBody
    @RequestMapping(value = "/bindSave.do", method = RequestMethod.POST)
    public ResultDto<Void> bindSave(String productCodes) {
        ResultDto<Void> result = new ResultDto<Void>();

        if (StringUtils.isEmpty(productCodes)) {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
            return result;
        }

        String[] arrayProductCode = productCodes.split(",");
        String channelCode = getChannelCode();
        channelProductService.bindChannel(arrayProductCode, channelCode);

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    @RequestMapping(value = "/update.do")
    public String update(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
        }
        ChannelProduct channelProduct = channelProductService.getChannelProduct(id);

        String productCode = channelProduct.getProductCode();
        Product product = productService.getFullProductByCode(productCode);

        List<AttributeKey> lstAttributeKey = productService.getAttributeKey(productCode);

        ChannelProductDetails channelProductDetails = channelProductService.getChannelProductDetailsByCode(channelProduct.getChannelProductCode());
        if (null == channelProductDetails) {
            channelProductDetails = new ChannelProductDetails();
        }

        List<ChannelTag> lstProductTag = channelProductService.getTagByProduct(channelProduct.getChannelProductCode(), getChannelCode());
        model.put("lstProductTag", lstProductTag);
        model.put("lstAttributeKey", lstAttributeKey);
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("channelProduct", channelProduct);
        model.put("product", product);
        model.put("channelProductDetails", channelProductDetails);
        return "channelProduct/update";
    }

    @ResponseBody
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    public ResultDto<Void> save(HttpServletRequest request, ChannelProduct channelProduct, String details) {
        ResultDto<Void> result = new ResultDto<Void>();

        if (null == channelProduct) {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
            return result;
        }

        String onSell = request.getParameter("onSell");
        if (StringUtils.isNotEmpty(onSell)) {
            channelProduct.setGmtOnSell(Timestamp.valueOf(onSell));
        } else {
            channelProduct.setGmtOnSell(null);
        }
        String offSell = request.getParameter("offSell");
        if (StringUtils.isNotEmpty(offSell)) {
            channelProduct.setGmtOffSell(Timestamp.valueOf(offSell));
        } else {
            channelProduct.setGmtOffSell(null);
        }
        channelProductService.updateChannelProduct(channelProduct);
        ChannelProductDetails channelProductDetails = new ChannelProductDetails();
        channelProductDetails.setChannelProductCode(channelProduct.getChannelProductCode());
        channelProductDetails.setDetails(details);
        String detailsId = request.getParameter("detailsId");
        if (StringUtils.isNotEmpty(detailsId)) {
            channelProductDetails.setId(detailsId);
        }
        channelProductService.updateChannelProductDetails(channelProductDetails);

        String strTag = request.getParameter("tags");
        String[] tags = null;
        if (StringUtils.isNotEmpty(strTag)) {
            tags = strTag.split(",");
        }

        channelProductService.setTag(channelProduct.getChannelProductCode(), tags);

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/goods.json")
    public LayuiResultDto<?> goodsJson(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, ChannelProduct channelProduct) {
        PageInfo<Map<String, String>> pageInfo = channelGoodsService.getPageByProduct(channelProduct, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @ResponseBody
    @RequestMapping(value = "/goodsSave.do", method = RequestMethod.POST)
    public ResultDto<Void> goodsSave(ChannelGoods channelGoods) {
        ResultDto<Void> result = new ResultDto<Void>();

        if (null == channelGoods) {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
            return result;
        }

        if (StringUtils.isEmpty(channelGoods.getChannelGoodsCode())) {
            channelGoods.setId(null);
            channelGoods.setChannelCode(getChannelCode());
            String channelCode = getChannelCode();
            channelGoods.setChannelGoodsCode(channelCode + "_" + channelGoods.getGoodsCode());
            channelGoodsService.addChannelGoods(channelGoods);
        } else {

            channelGoodsService.updateChannelGoods(channelGoods);
        }

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/goodsDel.do", method = RequestMethod.POST)
    public ResultDto<Void> goodsDel(String id) {
        ResultDto<Void> result = new ResultDto<Void>();

        if (StringUtils.isEmpty(id)) {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
            return result;
        }

        channelGoodsService.deleteChannelGoods(id);

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/alive.do", method = RequestMethod.POST)
    public ResultDto<Void> alive(HttpServletRequest request, String id) {
        ResultDto<Void> result = new ResultDto<Void>();

        if (StringUtils.isEmpty(id)) {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
            return result;
        }

        ChannelProduct channelProduct = new ChannelProduct();
        channelProduct.setId(id);
        String onSell = request.getParameter("onSell");
        if (StringUtils.isNotEmpty(onSell)) {
            channelProduct.setGmtOnSell(Timestamp.valueOf(onSell));
        } else {
            channelProduct.setGmtOnSell(null);
        }
        String offSell = request.getParameter("offSell");
        if (StringUtils.isNotEmpty(offSell)) {
            channelProduct.setGmtOffSell(Timestamp.valueOf(offSell));
        } else {
            channelProduct.setGmtOffSell(null);
        }
        channelProductService.updateChannelProductSell(channelProduct);

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/del.do", method = RequestMethod.POST)
    public ResultDto<Void> del(String id) {
        ResultDto<Void> result = new ResultDto<Void>();

        if (StringUtils.isEmpty(id)) {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
            return result;
        }

        channelProductService.deleteChannelProduct(id);

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/close.do", method = RequestMethod.POST)
    public ResultDto<Void> close(String id) {
        ResultDto<Void> result = new ResultDto<Void>();

        if (StringUtils.isEmpty(id)) {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
            return result;
        }

        ChannelProduct channelProduct = new ChannelProduct();
        channelProduct.setId(id);
        channelProduct.setStatus((byte) 0);
        channelProduct.setGmtOnSell(null);
        channelProduct.setGmtOffSell(new Timestamp(System.currentTimeMillis()));
        channelProductService.updateChannelProductSell(channelProduct);

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    @RequestMapping("/onSell.do")
    public String onSell(HttpServletRequest request, ModelMap model, String bizCode, String id) {

        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            return null;
        }

        ChannelProduct channelProduct = channelProductService.getChannelProduct(id);

        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("channelProduct", channelProduct);
        return "channelProduct/onSell";
    }

    private String getChannelCode() {
        return channelService.getById(sessionProvider.getUser().getChannelId()).getChannelCode();
    }


    @RequestMapping("/tagGoods.do")
    public String tagGoods(HttpServletRequest request, ModelMap model, String bizCode, String id) {

        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            return null;
        }

        ChannelGoods channelGoods = channelGoodsService.getChannelGoods(id);

        List<ChannelTag> lstTag = channelGoodsService.getTagByGoods(channelGoods.getChannelGoodsCode(), getChannelCode());

        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("channelGoods", channelGoods);
        model.put("lstTag", lstTag);
        return "channelProduct/tagGoods";
    }

    @ResponseBody
    @RequestMapping(value = "/setTagGoods.do")
    public ResultDto<Void> setTagGoods(HttpServletRequest request, String id) {
        ResultDto<Void> result = new ResultDto<Void>();
        result.setStatus(ApiConstants.FAIL);
        result.setMsg(ApiConstants.FAIL_API_MSG);
        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            return result;
        }
        ChannelGoods channelGoods = channelGoodsService.getChannelGoods(id);
        if (null == channelGoods) {
            log.error("product is null!");
            return result;
        }

        String[] tags = request.getParameterValues("tags");

        channelGoodsService.setTag(channelGoods.getChannelGoodsCode(), tags);

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    @RequestMapping("/strategyGoods.do")
    public String strategy(HttpServletRequest request, ModelMap model, String bizCode, String id) {

        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            return null;
        }

        ChannelGoods channelGoods = channelGoodsService.getChannelGoods(id);

        List<ChannelGoodsStrategy> lstChannelGoodsStrategy = channelGoodsService.getGoodsStrategyByGoods(channelGoods.getChannelGoodsCode());

        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("channelGoods", channelGoods);
        model.put("lstChannelGoodsStrategy", lstChannelGoodsStrategy);
        return "channelProduct/strategyGoods";
    }

    @ResponseBody
    @RequestMapping(value = "/strategyParam.do", method = RequestMethod.POST)
    public ResultDto<Void> strategyParam(String channelGoodsCode, String strategyId) {
        ResultDto<Void> result = new ResultDto<Void>();

        if (StringUtils.isEmpty(strategyId)) {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
            return result;
        }

        String msg = channelGoodsService.getGoodsStrategyParamView(channelGoodsCode, strategyId);

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(msg);

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/setStrategyGoods.do", method = RequestMethod.POST)
    public ResultDto<Void> setStrategyGoods(HttpServletRequest request, String channelGoodsCode) {
        String[] strategyIds = request.getParameterValues("strategyId");
        Map<String, String[]> mapStrategyParam = request.getParameterMap();
        channelGoodsService.setStrategy(channelGoodsCode, strategyIds, mapStrategyParam);

        ResultDto<Void> result = new ResultDto<Void>();
        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    @RequestMapping(value = "/updateChannelGoods.do", method = RequestMethod.GET)
    public String updateChannelGoods(String id, String bizCode, ModelMap modelMap) {
        ChannelGoods channelGoods = channelGoodsService.getChannelGoods(id);
        modelMap.put("channelGoods", channelGoods);
        modelMap.put("bizCode", bizCode);
        return "channelProduct/updateChannelGoods";
    }

    @ResponseBody
    @RequestMapping(value = "/saveChannelGoods.do", method = RequestMethod.POST)
    public ResultDto saveChannelGoods(ChannelGoods channelGoods) {
        boolean flag = channelGoodsService.saveChannelGoods(channelGoods);
        if (flag) {
            return new ResultSuccessDto("操作成功");
        } else {
            return new ResultFailDto("操作失败");
        }
    }
}
