package com.zjht.manager.action.admin.commodity;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.zjht.manager.common.dto.*;
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
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.commodity.AttributeKey;
import com.zjht.manager.entity.commodity.Category;
import com.zjht.manager.entity.commodity.CategoryAttributeKey;
import com.zjht.manager.service.CategoryService;
import com.zjht.manager.service.RedisCacheService;
import com.zjht.manager.util.PageUtil;

/**
 * 商品类目操作
 *
 * @author zhangyaohui
 * @since Aug 29, 2017 4:36:21 PM
 */
@Controller
@RequestMapping("/commodity/category")
public class CategoryAct {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private CategoryService categoryService;
    @Resource
    private RedisCacheService<?> redisCacheService;
    private static final String CATEGORY_REDIS_KEY = "category:";
    private static final String CATEGORY_REDIS_ALL_KEY = "categoryAll";

    /**
     * @param request
     * @param model
     * @param bizCode
     * @param category
     * @return
     * @author zhangyaohui
     * @since Sep 1, 2017 11:02:02 AM
     */
    @RequestMapping("/list.do")
    public String listHtml(HttpServletRequest request, ModelMap model, String bizCode, Category category) {

        if (null == category) {
            category = new Category();
        }

        List<Category> lstCategory = categoryService.selectAll();
        model.put("lstCategory", lstCategory);

        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("category", category);
        return "commodity/category/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list.json")
    public LayuiResultDto<?> json(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Category category) {

        if (null == category) {
            category = new Category();
        }
        PageInfo<Category> pageInfo = categoryService.getPage(category, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto<?> resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    /**
     * 增加/编辑类目信息
     *
     * @param request
     * @param model
     * @param bizCode
     * @param id
     * @return
     * @author zhangyaohui
     * @since Sep 6, 2017 3:58:18 PM
     */
    @RequestMapping(value = "/addOrUpdate.do")
    public String addOrUpdate(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        Category category = new Category();
        if (StringUtils.isNotEmpty(id)) {
            category = categoryService.getCategory(id);
            if (null == category) {
                log.error("category is null!");
            }
            if (StringUtils.isNotEmpty(category.getUpCode())) {
                Category up = categoryService.getCategoryByCode(category.getUpCode());
                category.setUpName(up.getCategoryName());
            }
        }
        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("category", category);
        if (StringUtils.isNotEmpty(id)) {
            return "commodity/category/addOrUpdate";
        }
        return "commodity/category/add";
    }

    @ResponseBody
    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    public ResultDto<Void> save(Category category) {
        String id = category.getId();
        if (null == category.getStatus()) {
            category.setStatus((byte) 0);
        } else {
            category.setStatus((byte) 1);
        }
        ResultDto<Void> result = new ResultDto<Void>();
        if (StringUtils.isEmpty(id)) {
            category.setId(null);
            //新增类目数据保存到redis中
            Category category1 = categoryService.addCategroy(category);
            redisCacheService.setCacheObject(CATEGORY_REDIS_KEY + category1.getCategoryCode(), JSONObject.toJSON(category1));

            //如果新增的类目有上层目录 则更新上层目录的类目类型
            if (StringUtils.isNotEmpty(category.getUpCode())) {
                Category upCategory = categoryService.getCategoryByCode(category.getUpCode());
                upCategory.setCategoryType((byte) 1);
                categoryService.updateCategroy(upCategory);
                redisCacheService.setCacheObject(CATEGORY_REDIS_KEY + upCategory.getCategoryCode(), JSONObject.toJSON(upCategory));
            }
            //更新总类目数据list
            updateRedisCache(redisCacheService, categoryService);

        } else {
            Category category2 = categoryService.updateCategroy(category);
            //更新类目数据保存到redis中
            redisCacheService.setCacheObject(CATEGORY_REDIS_KEY + category2.getCategoryCode(), JSONObject.toJSON(category2));
            //更新总类目数据list
            updateRedisCache(redisCacheService, categoryService);

        }

        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }

    private static void updateRedisCache(RedisCacheService<?> redisCacheService, CategoryService categoryService) {
        redisCacheService.remove(CATEGORY_REDIS_ALL_KEY);
        List<Object> list = categoryService.getAll();
        redisCacheService.setCacheList(CATEGORY_REDIS_ALL_KEY, list);
    }

    @ResponseBody
    @RequestMapping(value = "/del.do", method = RequestMethod.POST)
    public ResultDto<Void> del(String id, String categoryCode) {
        int flag = categoryService.deleteCategroy(id);

        ResultDto<Void> result = new ResultDto<Void>();
        if (1 == flag) {

            //删除redis中对应数据
            redisCacheService.remove(CATEGORY_REDIS_KEY + categoryCode);
            //更新总类目数据list
            updateRedisCache(redisCacheService, categoryService);

            result.setStatus(ApiConstants.SUCCESS);
            result.setMsg(ApiConstants.SUCCESS_MSG);
        } else {
            result.setStatus(ApiConstants.FAIL);
            result.setMsg(ApiConstants.FAIL_API_MSG);
        }
        return result;
    }

    @RequestMapping(value = "/addChildren.do")
    public String addChildren(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            return null;
        }
        Category category = categoryService.getCategory(id);
        if (null == category) {
            log.error("category is null!");
            return null;
        }

        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("category", category);
        return "commodity/category/addChildren";
    }

    @RequestMapping(value = "/attributeKey.do")
    public String attributeKey(HttpServletRequest request, ModelMap model, String bizCode, String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("id is null！");
            return null;
        }
        Category category = categoryService.getCategory(id);
        if (null == category) {
            log.error("category is null!");
            return null;
        }

        return attributeKey(request, model, category, bizCode);
    }

    @ResponseBody
    @RequestMapping(value = "/listAttributeKey.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto<?> json(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, String categoryCode) {

        PageInfo<CategoryAttributeKey> pageInfo = categoryService.getAttributeKeyPage(categoryCode, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
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
        Category category = categoryService.getCategory(id);
        if (null == category) {
            log.error("category is null!");
            return result;
        }

        String[] attributeKeys = request.getParameterValues("attributeKeys");

        categoryService.setAttrbuteKey(category.getCategoryCode(), attributeKeys);


        result.setStatus(ApiConstants.SUCCESS);
        result.setMsg(ApiConstants.SUCCESS_MSG);

        return result;
    }


    private String attributeKey(HttpServletRequest request, ModelMap model, Category category, String bizCode) {
        String categoryCode = category.getCategoryCode();
        List<AttributeKey> lstAttributeKey = categoryService.getAttributeKey(categoryCode);
        if (null != lstAttributeKey && !lstAttributeKey.isEmpty()) {
            model.put("lstAttributeKey", lstAttributeKey);
            String[] arrayAttrKey = new String[lstAttributeKey.size()];
            for (int i = 0; i < lstAttributeKey.size(); i++) {
                arrayAttrKey[i] = lstAttributeKey.get(i).getAttributeKeyCode();
            }
            String attributeKeys = StringUtils.join(arrayAttrKey, "','");
            model.put("attributeKeys", attributeKeys);
        }

        model.put("path", request.getContextPath());
        model.put("bizCode", bizCode.substring(0, 5));
        model.put("category", category);
        return "commodity/category/attributeKey";
    }

    @ResponseBody
    @RequestMapping(value = "/allocationAttrKey.do", method = RequestMethod.POST)
    public ResultDto allocationAttrKey(String id, Byte attributeKeyOrder) {
        categoryService.sortAttrbuteKey(id, attributeKeyOrder);
        return new ResultSuccessDto("处理成功");
    }

    @ResponseBody
    @RequestMapping(value = "/select.json")
    public List<Select> select(String categoryName) {
        return categoryService.getSelect(categoryName);
    }
}
