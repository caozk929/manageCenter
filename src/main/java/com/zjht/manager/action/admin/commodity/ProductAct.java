package com.zjht.manager.action.admin.commodity;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.common.dto.*;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.commodity.*;
import com.zjht.manager.service.*;
import com.zjht.manager.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 商品操作
 *
 * @author zhangyaohui
 * @since Sep 13, 2017 9:11:01 AM
 */
@Controller
@RequestMapping("/commodity/product")
public class ProductAct {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private ProductService productService;
    @Resource
    private CategoryService categoryService;
    @Resource
    ProductAttributeService productAttributeService;
    @Resource
    GoodsService goodsService;
    @Resource
    GoodsAttributeService goodsAttributeService;
    @Autowired
    private ProductDetailsService productDetailsService;

    @RequestMapping("/list.do")
    public String listHtml(HttpServletRequest request, ModelMap model, String bizCode, Product product) {

        if (null == product) {
            product = new Product();
        }
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("product", product);
        return "commodity/product/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list.json")
    public LayuiResultDto<?> json(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Product product) {

        if (null == product) {
            product = new Product();
        }
        product.setOrderByClause(" id desc");
        PageInfo<Product> pageInfo = productService.getPage(product, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/add.do")
    public String add(HttpServletRequest request, ModelMap model, String bizCode) {
        Product product = new Product();
        List<Select> lstSelect = categoryService.getTreeSelect();
        model.put("tree", JSONObject.toJSONString(lstSelect));
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("product", product);
        return "commodity/product/add";
    }

    @RequestMapping(value = "/update.do")
    public String update(HttpServletRequest request, ModelMap model, String bizCode, String id) {

        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
        }

        Product product = productService.getFullProduct(id);
        if (null == product) {
            log.error("product is null！");
        }

        //查询产品详情
        ProductDetails pd = productDetailsService.getByProductId(id);
        if (pd != null) {
            product.setDetails(pd.getDetails());
        }

        String productCode = product.getProductCode();

        List<AttributeKey> lstAttributeKey = productService.getAttributeKey(productCode);

        if (0 == productService.getCountByProduct(productCode)) {
            model.put("categorymodify", "1");
        }

        List<Select> lstSelect = categoryService.getTreeSelect();
        model.put("tree", JSONObject.toJSONString(lstSelect));
        model.put("lstAttributeKey", lstAttributeKey);
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("product", product);
        return "commodity/product/update";
    }

    @ResponseBody
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    public ResultDto<?> save(Product product) {
        String id = product.getId();

        if (null == product.getStatus()) {
            product.setStatus((byte) 0);
        } else {
            product.setStatus((byte) 1);
        }

        if (StringUtils.isEmpty(id)) {//新增
            product.setId(null);
            productService.addProduct(product);
        } else {//修改
            productService.updateProduct(product);
        }

        //保存或修改产品详情
        this.saveOrUpdateProductDetails(product);

        return new ResultSuccessDto(ApiConstants.SUCCESS_MSG);
    }

    /**
     * 新增或修改产品详情
     *
     * @param product
     */
    private void saveOrUpdateProductDetails(Product product) {
        ProductDetails pd = new ProductDetails();
        pd.setProductId(product.getId());
        pd.setDetails(product.getDetails());
        if (productDetailsService.countByProductId(product.getId()) > 0) {
            productDetailsService.update(pd);
        } else {
            productDetailsService.add(pd);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/del.do", method = RequestMethod.POST)
    public ResultDto del(String id) {
        //删除产品详情
        productDetailsService.delete(id);
        int flag = productService.deleteProduct(id);

        if (1 == flag) {
            return new ResultSuccessDto("处理成功");
        } else {
            return new ResultFailDto("处理失败");
        }

    }

    @RequestMapping(value = "/attributeKey.do")
    public String attributeKey(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            return null;
        }
        Product product = productService.getProduct(id);
        if (null == product) {
            log.error("product is null!");
            return null;
        }
        return attributeKey(request, model, product, bizCode);
    }

    @ResponseBody
    @RequestMapping(value = "/attributeKey.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto<?> json(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, String productCode) {

        PageInfo<ProductAttributeKey> pageInfo = productService.getAttributeKeyPage(productCode, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @ResponseBody
    @RequestMapping(value = "/setAttributeKey.do")
    public ResultDto<Void> setAttributeKey(HttpServletRequest request, String id) {
        ResultDto<Void> result = new ResultDto<Void>();
        result.setStatus(ApiConstants.FAIL);
        result.setMsg(ApiConstants.FAIL_API_MSG);
        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            return result;
        }
        Product product = productService.getProduct(id);
        if (null == product) {
            log.error("product is null!");
            return result;
        }

        if (null == request.getParameterValues("attributeKeys")) {
            log.error("attributeKeys is null!");
            return result;
        }

        String[] attributeKeys = request.getParameterValues("attributeKeys");

        productService.setAttrbuteKey(product.getProductCode(), attributeKeys);

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    private String attributeKey(HttpServletRequest request, ModelMap model, Product product, String bizCode) {
        List<AttributeKey> lstAttributeKey = productService.getAttributeKey(product.getProductCode());
        model.put("lstAttributeKey", lstAttributeKey);
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("product", product);
        return "commodity/product/attributeKey";
    }

    @ResponseBody
    @RequestMapping(value = "/allocationAttrKey.do", method = RequestMethod.POST)
    public ResultDto<Void> allocationAttrKey(String id, Byte attributeKeyOrder) {
        productService.sortAttrbuteKey(id, attributeKeyOrder);
        ResultDto<Void> result = new ResultDto<Void>();
        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);
        return result;
    }

    @RequestMapping(value = "/attribute.do")
    public String attribute(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            return null;
        }
        Product product = productService.getProduct(id);
        if (null == product) {
            log.error("product is null!");
            return null;
        }

        List<ProductAttribute> lstProductAttribute = productAttributeService.getProductAttributeByProduct(product);
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("product", product);
        model.put("lstProductAttribute", lstProductAttribute);
        return "commodity/product/attribute";
    }

    @RequestMapping(value = "/goods.do")
    public String goods(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            return null;
        }
        Product product = productService.getProduct(id);
        if (null == product) {
            log.error("product is null!");
            return null;
        }

        return goods(request, model, bizCode, product);
    }

    private String goods(HttpServletRequest request, ModelMap model, String bizCode, Product product) {
        List<AttributeKey> lstAttributeKey = productService.getAttributeKey(product.getProductCode());
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("product", product);
        model.put("lstAttributeKey", lstAttributeKey);
        return "commodity/product/goods";
    }

    @ResponseBody
    @RequestMapping(value = "/goods.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto<?> jsonGoods(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, String productCode) {

        PageInfo<Map<String, String>> pageInfo = goodsService.getPageByProduct(productCode, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    /**
     * 保存修改或新增的单品数据信息
     *
     * @param request
     * @param goods
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveGoodsAttribute.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto saveGoodsAttribute(HttpServletRequest request, Goods goods, String productId) {
        List<GoodsAttribute> lstGoodsAttribute = new ArrayList<>();
        String goodsCode = "";
        if (StringUtils.isNotBlank(goods.getGoodsCode())) {//更新
            goodsCode = goods.getGoodsCode();
        } else {                                    //新增
            if (StringUtils.isEmpty(productId)) {
                log.error("productId is null！");
                return new ResultFailDto("商品ID为空");
            }
            Product product = productService.getProduct(productId);
            if (null == product) {
                log.error("product is null!");
                return new ResultFailDto("没有此商品");
            }
            Goods goods1 = goodsService.addGoods(product.getProductCode(), goods.getGoodsCount().toString());
            goodsCode = goods1.getGoodsCode();
        }

        Enumeration<String> paraNames = request.getParameterNames();
        while (paraNames.hasMoreElements()) {
            String name = paraNames.nextElement();
            String value = request.getParameter(name);

            if (name.startsWith("attr_")) {
                GoodsAttribute goodsAttribute = new GoodsAttribute();
                goodsAttribute.setAttributeKeyCode(name.substring(5));
                goodsAttribute.setAttributeValue(value);
                goodsAttribute.setGoodsCode(goodsCode);
                lstGoodsAttribute.add(goodsAttribute);
            }
        }

        goodsAttributeService.setGoodsAttribute(goodsCode, lstGoodsAttribute);

        if (StringUtils.isNotBlank(goods.getGoodsCode())) {//更新
            goodsService.updateGoods(goods);
        }

        return new ResultSuccessDto("操作成功");
    }

    /**
     * 删除单品信息
     *
     * @param goodsCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delGoods.do", method = RequestMethod.POST)
    public ResultDto<Void> delGoods(String goodsCode) {
        for (int i = 0; i < goodsCode.split(",").length; i++) {
            goodsService.deleteGoods(goodsCode.split(",")[i]);
        }
        ResultDto<Void> result = new ResultDto<Void>();
        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);
        return result;
    }

    /**
     * 新增修改单品信息
     *
     * @param bizCode
     * @param id
     * @param modelMap
     * @param goodsCode
     * @return
     */
    @RequestMapping(value = "addOrUpdateGoods.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String addOrUpdateGoods(String bizCode, String id, ModelMap modelMap, String goodsCode) {

        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            throw new IllegalArgumentException("商品ID为空");
        }
        Product product = productService.getProduct(id);
        if (null == product) {
            log.error("product is null!");
            throw new IllegalArgumentException("没有此商品");
        }

        List<AttributeKey> lstAttributeKey = productService.getAttributeKey(product.getProductCode());

        if (StringUtils.isNotBlank(goodsCode)) {
            Goods goods = goodsService.selectGoodsByCode(goodsCode);
            modelMap.put("goods", goods);

            //设置单品属性值
            List<GoodsAttribute> lstGoodsAttribute = goodsAttributeService.getGoodsAttributeByGoodsCode(goodsCode);
            for (GoodsAttribute goodsAttribute : lstGoodsAttribute) {
                for (AttributeKey attributeKey : lstAttributeKey) {
                    if (goodsAttribute.getAttributeKeyCode().equals(attributeKey.getAttributeKeyCode())) {
                        attributeKey.setAttributeValue(goodsAttribute.getAttributeValue());
                    }
                }
            }
        }

        modelMap.put("bizCode", bizCode);
        modelMap.put("productId", id);
        modelMap.put("lstAttributeKey", lstAttributeKey);

        return "commodity/product/addOrUpdateGoods";
    }

}
