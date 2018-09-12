package com.zjht.manager.entity.channelProduct;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.druid.util.StringUtils;

@Table(name = "t_channel_product")
public class ChannelProduct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 449404590877461762L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CHANNELPRODUCT'),12,'0')")
	private String id;

	/**
	 * 渠道商品编码
	 */
	@Column(name = "channel_product_code")
	private String channelProductCode;

	/**
	 * 渠道编码
	 */
	@Column(name = "channel_code")
	private String channelCode;

	/**
	 * 商品编码
	 */
	@Column(name = "product_code")
	private String productCode;

	/**
	 * 商品标题
	 */
	@Column(name = "product_title")
	private String productTitle;

	/**
	 * 价格范围
	 */
	@Column(name = "product_price")
	private String productPrice;

	/**
	 * 状态
	 */
	private Byte status;

	/**
	 * 上架时间
	 */
	@Column(name = "gmt_on_sell")
	private Timestamp gmtOnSell;
	
	/**
	 * 下架时间
	 */
	@Column(name = "gmt_off_sell")
	private Timestamp gmtOffSell;	
	
	// 渠道专有编码
	@Column(name = "specific_product_code")
	private String specificProductCode;	

	/**
	 * 所属类目编码
	 */
	@Transient
	@Column(name = "category_code")
	private String categoryCode;

	/**
	 * 商品名称
	 */
	@Transient
	@Column(name = "product_name")
	private String productName;

	/**
	 * 商品品牌
	 */
	@Transient
	private String brand;

	/**
	 * 型号/系列
	 */
	@Transient
	private String series;
	@Transient
	private String categoryName;

	@Transient
	private String orderByClause;

	@Transient
	private String statusStr;

	@Transient
	private String bindStr;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取渠道商品编码
	 *
	 * @return channel_product_code - 渠道商品编码
	 */
	public String getChannelProductCode() {
		return channelProductCode;
	}

	/**
	 * 设置渠道商品编码
	 *
	 * @param channelProductCode
	 *            渠道商品编码
	 */
	public void setChannelProductCode(String channelProductCode) {
		this.channelProductCode = channelProductCode;
	}

	/**
	 * 获取渠道编码
	 *
	 * @return channel_code - 渠道编码
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * 设置渠道编码
	 *
	 * @param channelCode
	 *            渠道编码
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * 获取商品编码
	 *
	 * @return product_code - 商品编码
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 设置商品编码
	 *
	 * @param productCode
	 *            商品编码
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 获取商品标题
	 *
	 * @return product_title - 商品标题
	 */
	public String getProductTitle() {
		return productTitle;
	}

	/**
	 * 设置商品标题
	 *
	 * @param productTitle
	 *            商品标题
	 */
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	/**
	 * 获取价格范围
	 *
	 * @return product_price - 价格范围
	 */
	public String getProductPrice() {
		return productPrice;
	}

	/**
	 * 设置价格范围
	 *
	 * @param productPrice
	 *            价格范围
	 */
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * 获取状态
	 *
	 * @return status - 状态
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 *
	 * @param status
	 *            状态
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	public Timestamp getGmtOnSell() {
		return gmtOnSell;
	}

	public void setGmtOnSell(Timestamp gmtOnSell) {
		this.gmtOnSell = gmtOnSell;
	}

	public Timestamp getGmtOffSell() {	
		return gmtOffSell;
	}

	public void setGmtOffSell(Timestamp gmtOffSell) {
		this.gmtOffSell = gmtOffSell;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getStatusStr() {
		if(null == status) {
			return "";
		}
		switch (status) {
		case 1:
			return "已上架";
		default:
			return "已下架";
		}
	}

	public String getBindStr() {
		if(StringUtils.isEmpty(channelProductCode)) {
			return "";
		}
		return "已关联";
	}

	public String getSpecificProductCode() {
		return specificProductCode;
	}

	public void setSpecificProductCode(String specificProductCode) {
		this.specificProductCode = specificProductCode;
	}	

}