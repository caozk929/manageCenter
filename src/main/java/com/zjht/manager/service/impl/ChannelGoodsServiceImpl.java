package com.zjht.manager.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.manager.dao.ChannelGoodsDao;
import com.zjht.manager.dao.ChannelGoodsStrategyDao;
import com.zjht.manager.dao.ChannelGoodsStrategyParamValueDao;
import com.zjht.manager.dao.ChannelGoodsTagDao;
import com.zjht.manager.dao.ChannelTagDao;
import com.zjht.manager.dao.GoodsAttributeDao;
import com.zjht.manager.entity.channelProduct.ChannelGoods;
import com.zjht.manager.entity.channelProduct.ChannelGoodsStrategy;
import com.zjht.manager.entity.channelProduct.ChannelGoodsStrategyParamValue;
import com.zjht.manager.entity.channelProduct.ChannelProduct;
import com.zjht.manager.entity.channelProduct.ChannelTag;
import com.zjht.manager.entity.commodity.GoodsAttribute;
import com.zjht.manager.service.ChannelGoodsService;

@Service
@Transactional
public class ChannelGoodsServiceImpl implements ChannelGoodsService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());	
	
	@Resource
	private ChannelGoodsDao channelGoodsDao;
	@Resource
	private GoodsAttributeDao goodsAttributeDao;
	@Resource
	private ChannelTagDao channelTagDao;
	@Resource
	private ChannelGoodsTagDao channelGoodsTagDao;
	@Resource
	private ChannelGoodsStrategyDao channelGoodsStrategyDao;
	@Resource
	private ChannelGoodsStrategyParamValueDao channelGoodsStrategyParamValueDao;

	@Override
	public PageInfo<Map<String, String>> getPageByProduct(ChannelProduct channelProduct, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);

		List<ChannelGoods> list = channelGoodsDao.selectByChannelProduct(channelProduct.getProductCode(), channelProduct.getChannelProductCode());
		
		if(null == list || list.isEmpty()) {
			PageInfo<Map<String, String>> pageInfo = new PageInfo<>(new ArrayList<Map<String, String>>());
			return pageInfo;			
		}

		List<String> lstGoodsCode = new ArrayList<>();
		for (ChannelGoods obj : list) {
			lstGoodsCode.add(obj.getGoodsCode());
		}
		List<GoodsAttribute> lstGoodsAttribute = goodsAttributeDao.selectByGoods(lstGoodsCode);
		Map<String, Map<String, String>> mapFull = new HashMap<>();
		for(GoodsAttribute obj : lstGoodsAttribute) {
			Map<String, String> pojo = mapFull.get(obj.getGoodsCode());
			if(null == pojo) {
				pojo = new HashMap<>();	
				//pojo.put("goodsCode", obj.getGoodsCode());
			}
			pojo.put("attr_" + obj.getAttributeKeyCode(), obj.getAttributeValue());			
			mapFull.put(obj.getGoodsCode(), pojo);
		}

		List<Map<String, String>> lstPojo = new ArrayList<>();
		for (ChannelGoods obj : list) {
			Map<String, String> pojo = new HashMap<>();	
			pojo.put("id", StringUtils.defaultString(obj.getId()));
			pojo.put("goodsCode", obj.getGoodsCode());
			if(null != obj.getGoodsCount()) {
				pojo.put("goodsCount", obj.getGoodsCount() + "");
			}
			pojo.put("channelProductCode", StringUtils.defaultString(obj.getChannelProductCode()));
			pojo.put("channelGoodsCode", StringUtils.defaultString(obj.getChannelGoodsCode()));
			pojo.put("specificGoodsCode", StringUtils.defaultString(obj.getSpecificGoodsCode()));
			if(null != obj.getPrice()) {
				pojo.put("price", obj.getPrice() + "");
			} 
			if(null != obj.getAmount()) {
				pojo.put("amount", obj.getAmount() + "");
			}
			pojo.put("logo", StringUtils.defaultString(obj.getLogo()));			
			if(null != mapFull.get(obj.getGoodsCode())) {			
				pojo.putAll(mapFull.get(obj.getGoodsCode()));				
			}
			lstPojo.add(pojo);
		}		

		PageInfo<Map<String, String>> pageInfo = new PageInfo<>(lstPojo);
		return pageInfo;
	}

	@Override
	public void addChannelGoods(ChannelGoods channelGoods) {
		// TODO Auto-generated method stub
		try {
			updateCount(channelGoods);
			if(channelGoodsDao.insertChannelGoods(channelGoods) < 1) {
				throw new RuntimeException("专有编码不能重复！");
			}
			channelGoodsDao.updateProductPrice(channelGoods.getChannelProductCode());
			//channelGoodsDao.insertSelective(channelGoods);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}			
	}

	@Override
	public void updateChannelGoods(ChannelGoods channelGoods) {		
		// TODO Auto-generated method stub
		try {
			updateCount(channelGoods);
			if(channelGoodsDao.updateChannelGoods(channelGoods) < 1) {
				throw new RuntimeException("专有编码不能重复！");
			}
			channelGoodsDao.updateProductPrice(channelGoods.getChannelProductCode());
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);    
		}		
	}

	@Override
	public int deleteChannelGoods(String id) {
		// TODO Auto-generated method stub
		return channelGoodsDao.deleteByPrimaryKey(id);
	}
	
	private void updateCount(ChannelGoods channelGoods) {
		try {
			if(StringUtils.isNotEmpty(channelGoods.getId())) {
				channelGoodsDao.updateGoodsForUnfreeze(channelGoods.getId());
			}
			int flat = channelGoodsDao.updateGoodsForFreeze(channelGoods);
			if(flat < 1) {
				log.error("单品库存不足！"); 
				throw new RuntimeException("单品库存不足！");	
			}
			channelGoodsDao.updateProductCount(channelGoods.getGoodsCode());
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}		
	}

	@Override
	public ChannelGoods getChannelGoods(String id) {
		// TODO Auto-generated method stub
		return channelGoodsDao.selectByPrimaryKey(id);
	}

	@Override
	public List<ChannelTag> getTagByGoods(String channelGoodsCode, String channelCode) {
		// TODO Auto-generated method stub
		return channelTagDao.selectTagByGoods(channelGoodsCode, channelCode);
	}

	@Override
	public void setTag(String channelGoodsCode, String[] tags) {
		// TODO Auto-generated method stub
		if(null == tags) {
			channelGoodsTagDao.deleteAllTag(channelGoodsCode);
			return;
		}
		for(String tag : tags) {
			channelGoodsTagDao.insertTag(channelGoodsCode, tag);
		}
		
		List<String> lstTag = Arrays.asList(tags);  		
		channelGoodsTagDao.deleteTag(channelGoodsCode, lstTag);			
	}

	@Override
	public List<ChannelGoodsStrategy> getGoodsStrategyByGoods(String channelGoodsCode) {
		// TODO Auto-generated method stub
		List<ChannelGoodsStrategy> lstChannelGoodsStrategy = channelGoodsStrategyDao.selectStrategyByGoods(channelGoodsCode);
		for(ChannelGoodsStrategy channelGoodsStrategy : lstChannelGoodsStrategy) {
			String id = channelGoodsStrategy.getId();
			if(StringUtils.isNotEmpty(id)) {
				channelGoodsStrategy.setStrategyParamView(getGoodsStrategyParamView(channelGoodsCode, channelGoodsStrategy.getStrategyId()));
			}
		}
		return lstChannelGoodsStrategy;
	}

	@Override
	public void setStrategy(String channelGoodsCode, String[] strategyIds, Map<String, String[]> mapStrategyParam) {
		// TODO Auto-generated method stub
		if(null == strategyIds) {
			deleteAllStrategy(channelGoodsCode);
			return;
		}
		for(String strategyId : strategyIds) {
			ChannelGoodsStrategy channelGoodsStrategy = new ChannelGoodsStrategy();
			channelGoodsStrategy.setChannelGoodsCode(channelGoodsCode);
			channelGoodsStrategy.setStrategyId(strategyId);
			//channelGoodsStrategy.setId(channelGoodsStrategyDao.findChannelGoodsStrategyId());
			insertStrategy(channelGoodsStrategy, mapStrategyParam);
		}
		
		/*List<String> lstStrategyId = Arrays.asList(strategyIds);  		
		channelGoodsStrategyDao.deleteStrategy(channelGoodsCode, lstStrategyId);*/
		deleteStrategy(channelGoodsCode, strategyIds);
		

	}
	
	/**
	 * 
	 * @param channelGoodsCode
	 * @author zhangyaohui
	 * @since Oct 10, 2017 5:20:11 PM
	 */
	private void deleteAllStrategy(String channelGoodsCode) {
		channelGoodsStrategyParamValueDao.deleteAllGoodsStrategyParam(channelGoodsCode);
		channelGoodsStrategyDao.deleteAllStrategy(channelGoodsCode);		
	}
	
	/**
	 * 
	 * @param channelGoodsStrategy
	 * @param mapStrategyParam
	 * @author zhangyaohui
	 * @since Oct 10, 2017 5:05:27 PM
	 */
	private void insertStrategy(ChannelGoodsStrategy channelGoodsStrategy, Map<String, String[]> mapStrategyParam) {
		String goodsStrategyId = channelGoodsStrategyDao.selectChannelGoodsStrategyId(channelGoodsStrategy);
		int flag = 0;
		if(null == goodsStrategyId) {
			goodsStrategyId = channelGoodsStrategyDao.findChannelGoodsStrategyId();
			channelGoodsStrategy.setId(goodsStrategyId);
			flag = channelGoodsStrategyDao.insertStrategy(channelGoodsStrategy);
		}
		
		if(null == mapStrategyParam) {
			return;
		}
		
		if(0 == flag) {
			goodsStrategyId = channelGoodsStrategyDao.selectChannelGoodsStrategyId(channelGoodsStrategy);
		}
		
		String strategyId = channelGoodsStrategy.getStrategyId();
		for (Map.Entry<String, String[]> obj : mapStrategyParam.entrySet()) {
			String key = obj.getKey();
			if (key.startsWith(strategyId + '_')) {
				ChannelGoodsStrategyParamValue paramValue = new ChannelGoodsStrategyParamValue();
				paramValue.setGoodsStrategyId(goodsStrategyId);
				paramValue.setStrategyId(strategyId);
				paramValue.setStrategyParamId(key.substring(13));
				paramValue.setStrategyParamValue(obj.getValue()[0]);			
				if (1 == flag) {
					channelGoodsStrategyParamValueDao.insertSelective(paramValue);
				} else {
					/*channelGoodsStrategy.setId(null);
					channelGoodsStrategy = channelGoodsStrategyDao.selectOne(channelGoodsStrategy);
					paramValue.setGoodsStrategyId(channelGoodsStrategy.getId());*/
					if (0 == channelGoodsStrategyParamValueDao.updateGoodsStrategyParam(paramValue)) {
						channelGoodsStrategyParamValueDao.insertGoodsStrategyParam(paramValue);
					}				
				}
			}
		}		
	}
	
	/**
	 * 
	 * @param channelGoodsCode
	 * @param strategyIds
	 * @author zhangyaohui
	 * @since Oct 10, 2017 5:05:32 PM
	 */
	private void deleteStrategy(String channelGoodsCode, String[] strategyIds) {
		List<String> lstStrategyId = Arrays.asList(strategyIds);  
		channelGoodsStrategyParamValueDao.deleteGoodsStrategyParam(channelGoodsCode, lstStrategyId);
		channelGoodsStrategyDao.deleteStrategy(channelGoodsCode, lstStrategyId);		
	}

	@Override
	public String getGoodsStrategyParamView(String channelGoodsCode, String strategyId) {
		// TODO Auto-generated method stub
		StringBuilder data = new StringBuilder();
		List<ChannelGoodsStrategyParamValue> lstChannelGoodsStrategyParamValue = channelGoodsStrategyParamValueDao.selectStrategyParamByGoods(channelGoodsCode, strategyId);
		if(null == lstChannelGoodsStrategyParamValue || lstChannelGoodsStrategyParamValue.size() == 0) {
			return "";
		}
		data.append("<div class=\"layui-form-item\" id=\"").append(strategyId).append("\">");
		for(ChannelGoodsStrategyParamValue channelGoodsStrategyParamValue : lstChannelGoodsStrategyParamValue) {
			data.append(getSingleParamView(channelGoodsStrategyParamValue));
		}
		data.append("</div>");
		return data.toString();
	}

	@Override
	public boolean saveChannelGoods(ChannelGoods channelGoods) {
		int result = channelGoodsDao.updateByPrimaryKeySelective(channelGoods);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	private StringBuilder getSingleParamView(ChannelGoodsStrategyParamValue parameter) {
		StringBuilder data = new StringBuilder();
		data.append("<div class=\"layui-inline\"><label class=\"layui-form-label\">");
		data.append(parameter.getParameterName());
		data.append("</label><div class=\"layui-input-inline\"><input type=\"text\" name=\"");
		data.append(parameter.getStrategyId()).append('_').append(parameter.getStrategyParamId()).append("\"");
		data.append(paramType(parameter.getParameterType().intValue()));
		data.append(" autocomplete=\"off\" class=\"layui-input\"");
		String value = parameter.getStrategyParamValue();
		if(StringUtils.isNotEmpty(value)) {
			data.append(" value=\"").append(value).append("\"");
		}
		data.append("></div></div>");
		return data;
	}
	
	private StringBuilder paramType(int paramType) {
		StringBuilder data = new StringBuilder();
		switch (paramType) {
			case 1 :
				return data.append(" lay-verify=\"number\"");	
			case 3 :
				return data.append(" lay-verify=\"date\"");					
			default:
				return data;
			}

	}

}
