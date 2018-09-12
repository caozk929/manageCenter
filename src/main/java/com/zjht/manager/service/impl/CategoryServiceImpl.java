package com.zjht.manager.service.impl;

import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.AttributeKeyDao;
import com.zjht.manager.dao.CategoryAttributeKeyDao;
import com.zjht.manager.dao.CategoryDao;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.commodity.AttributeKey;
import com.zjht.manager.entity.commodity.Category;
import com.zjht.manager.entity.commodity.CategoryAttributeKey;
import com.zjht.manager.service.CategoryService;

import tk.mybatis.mapper.entity.Example;

/**
 * @author zhangyaohui
 * @since Aug 30, 2017 10:32:29 AM
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private CategoryAttributeKeyDao categoryAttributeKeyDao;

    @Resource
    private AttributeKeyDao attributeKeyDao;

    @Override
    public List<Category> selectAll() {
        // TODO Auto-generated method stub
        List<Category> lstCategory = categoryDao.selectAll();
        if (null == lstCategory) {
            return null;
        }
        Map<String, List<Category>> mapChildren = new HashMap<>();
        for (Category category : lstCategory) {
            String upCode = category.getUpCode();
            if (StringUtils.isEmpty(upCode)) {
                upCode = "";
            }
            List<Category> children = mapChildren.get(upCode);
            if (null == children) {
                children = new ArrayList<>();
            }
            children.add(category);
            mapChildren.put(upCode, children);
        }
        for (Category category : lstCategory) {
            category.setChildren(mapChildren.get(category.getCategoryCode()));
        }
        //return lstCategory;
        return mapChildren.get("");
    }

    @Override
    public PageInfo<Category> getPage(Category category, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(category.getCategoryName())) {
            criteria.andLike("categoryName", "%" + category.getCategoryName() + "%");
        }
        List<Category> list = categoryDao.selectByExample(example);

        PageInfo<Category> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Category getCategory(String id) {
        // TODO Auto-generated method stub
        return categoryDao.selectByPrimaryKey(id);
    }

    @Override
    public Category addCategroy(Category category) {
        // TODO Auto-generated method stub
        category.setId(categoryDao.findId());
        if (StringUtils.isEmpty(category.getUpCode())) {
            category.setCategoryType((byte) 0);
            category.setGmtCreate(new Timestamp(System.currentTimeMillis()) );
            category.setLevel((byte) 1);
            categoryDao.insert(category);
            return categoryDao.selectByPrimaryKey(category.getId());
        }
        categoryDao.insertCategory(category);

        return categoryDao.selectByPrimaryKey(category.getId());
    }

    @Override
    public Category updateCategroy(Category category) {
        // TODO Auto-generated method stub
        categoryDao.updateByPrimaryKeySelective(category);
        return  categoryDao.selectByPrimaryKey(category.getId());
    }

    @Override
    public int deleteCategroy(String id) {
        // TODO Auto-generated method stub
        return categoryDao.deleteByPrimaryKey(id);
    }

    @Override
    public void setAttrbuteKey(String categoryCode, String[] attributeKeys) {
        // TODO Auto-generated method stub
        if (null == attributeKeys || attributeKeys.length == 0) {
            categoryAttributeKeyDao.deleteAllAttributeKey(categoryCode);
            return;
        }
        for (String attributeKeyCode : attributeKeys) {
            categoryAttributeKeyDao.insertAttributeKey(categoryCode, attributeKeyCode);
        }

        List<String> lstAttributeKey = Arrays.asList(attributeKeys);
        categoryAttributeKeyDao.deleteAttributeKey(categoryCode, lstAttributeKey);
    }

    @Override
    public List<AttributeKey> getAttributeKey(String categoryCode) {
        // TODO Auto-generated method stub
        return attributeKeyDao.selectAttributeKeybyCategory(categoryCode);
    }

    @Override
    public int sortAttrbuteKey(String id, Byte attributeKeyOrder) {
        // TODO Auto-generated method stub
        return categoryAttributeKeyDao.updateAttributeKeyBySort(id, attributeKeyOrder);
    }

    @Override
    public PageInfo<CategoryAttributeKey> getAttributeKeyPage(String categoryCode, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        List<CategoryAttributeKey> list = categoryAttributeKeyDao.selectByCategory(categoryCode);
        PageInfo<CategoryAttributeKey> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<Select> getSelect(String categoryName) {
        // TODO Auto-generated method stub
        return categoryDao.findSelect(categoryName);
    }

    @Override
    public Category getCategoryByCode(String categoryCode) {
        // TODO Auto-generated method stub
        Category category = new Category();
        category.setCategoryCode(categoryCode);
        return categoryDao.selectOne(category);
    }

    @Override
    public List<Select> getTreeSelect() {
        // TODO Auto-generated method stub
        List<Category> lstCategory = selectAll();

        List<Select> lstSelect = new ArrayList<>();
        for (Category item : lstCategory) {
            Select select = fromCategory(item);
            lstSelect.add(select);
            List<Category> items = item.getChildren();
            if (null != items && items.size() > 0) {
                for (Category obj : items) {
                    addSelect(lstSelect, obj);
                }
            }
        }
        return lstSelect;
    }

    @Override
    public List<Object> getAll() {
        List<Category> list = categoryDao.selectAll();
        List<Object> list1 = new ArrayList<>();
        for (Category category : list) {
            list1.add(JSONObject.toJSON(category));
        }
        return list1;
    }

    private void addSelect(List<Select> lstSelect, Category item) {
        Select select = fromCategory(item);
        lstSelect.add(select);
        List<Category> items = item.getChildren();
        if (null != items && items.size() > 0) {
            for (Category obj : items) {
                addSelect(lstSelect, obj);
            }
        }
    }

    private Select fromCategory(Category category) {
        Select select = new Select();
        select.setId(category.getCategoryCode());
        select.setText(category.getCategoryName());
        String pre = StringUtils.repeat("&nbsp;&nbsp;", (category.getLevel().intValue() - 1) * 4);
        select.setDescription(pre + category.getCategoryName());
        return select;
    }

}
