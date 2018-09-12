package com.zjht.manager.entity.commodity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 商品信息
 * 
 * @author zhangyaohui
 * @since Aug 29, 2017 11:29:20 AM
 *
 */
@Table(name = "t_product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1532139878430650436L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	/**
	 * 商品编码
	 */
	@Column(name = "product_code")
	private String productCode;

	/**
	 * 所属类目编码
	 */
	@Column(name = "category_code")
	private String categoryCode;

	/**
	 * 商品名称
	 */
	@Column(name = "product_name")
	private String productName;

	/**
	 * 商品品牌
	 */
	private String brand;

	/**
	 * 型号/系列
	 */
	private String series;

	/**
	 * 计量单位
	 */
	private String unit;

	/**
	 * 状态：0-在用；1-停用；2-删除
	 */
	private Byte status;

	/**
	 * 生成时间
	 */
	@Column(name = "gmt_create")
	private Timestamp gmtCreate;

	/**
	 * 修改时间
	 */
	@Column(name = "gmt_modified")
	private Timestamp gmtModified;
	
	/**
	 * 商品数量
	 */
	@Column(name = "product_count")
	private Long productCount;	
	
	/**
	 * 商品类型：0-实体；1-虚拟
	 */
	@Column(name = "product_type")
	private Byte productType;	

	@Transient
	private String categoryName;

	@Transient
	private String orderByClause;
	
    @Transient
    private String statusStr;

	@Transient
	private String details;//产品详情

	/**
	 * 获取产品详情
	 * @return
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * 设置产品详情
	 * @param details
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * 缩略图URL
	 */
	@Column(name = "synopsis_url")
	private String synopsisUrl;

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
	 * 获取所属类目编码
	 *
	 * @return category_code - 所属类目编码
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * 设置所属类目编码
	 *
	 * @param categoryCode
	 *            所属类目编码
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * 获取商品名称
	 *
	 * @return product_name - 商品名称
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 设置商品名称
	 *
	 * @param productName
	 *            商品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 获取商品品牌
	 *
	 * @return brand - 商品品牌
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * 设置商品品牌
	 *
	 * @param brand
	 *            商品品牌
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * 获取型号/系列
	 *
	 * @return series - 型号/系列
	 */
	public String getSeries() {
		return series;
	}

	/**
	 * 设置型号/系列
	 *
	 * @param series
	 *            型号/系列
	 */
	public void setSeries(String series) {
		this.series = series;
	}

	/**
	 * 获取计量单位
	 *
	 * @return unit - 计量单位
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * 设置计量单位
	 *
	 * @param unit
	 *            计量单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 获取状态：0-在用；1-停用；2-删除
	 *
	 * @return status - 状态：0-在用；1-停用；2-删除
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * 设置状态：0-在用；1-停用；2-删除
	 *
	 * @param status
	 *            状态：0-在用；1-停用；2-删除
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * 获取生成时间
	 *
	 * @return gmt_create - 生成时间
	 */
	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * 设置生成时间
	 *
	 * @param gmtCreate
	 *            生成时间
	 */
	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	/**
	 * 获取修改时间
	 *
	 * @return gmt_modified - 修改时间
	 */
	public Timestamp getGmtModified() {
		return gmtModified;
	}

	/**
	 * 设置修改时间
	 *
	 * @param gmtModified
	 *            修改时间
	 */
	public void setGmtModified(Timestamp gmtModified) {
		this.gmtModified = gmtModified;
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
			return "启用";
		default:
			return "停用";
		}
	}

	public Long getProductCount() {
		return productCount;
	}

	public void setProductCount(Long productCount) {
		this.productCount = productCount;
	}

	/**
	 * 获取缩略图URL
	 * @return
	 */
	public String getSynopsisUrl() {
		return synopsisUrl;
	}

	/**
	 * 设置缩略图URL
	 * @param synopsisUrl
	 */
	public void setSynopsisUrl(String synopsisUrl) {
		this.synopsisUrl = synopsisUrl;
	}

	public Byte getProductType() {
		return productType;
	}

	public void setProductType(Byte productType) {
		this.productType = productType;
	}
	
	public String getProductTypeStr() {
		if(null == productType) {
			return "";
		}
		switch (productType) {
		case 1:
			return "虚拟";
		default:
			return "实体";
		}
	}	

}