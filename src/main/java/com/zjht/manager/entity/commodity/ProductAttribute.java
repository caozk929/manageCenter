package com.zjht.manager.entity.commodity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 商品属性
 * 
 * @author zhangyaohui
 * @since Aug 29, 2017 2:15:02 PM
 *
 */
@Table(name = "t_product_attribute")
public class ProductAttribute implements Serializable {

	private static final long serialVersionUID = 33265622157495743L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_PRODUCTATTRIBUTE'),12,'0')")
	private String id;

	/**
	 * 商品编码
	 */
	@Column(name = "product_code")
	private String productCode;

	/**
	 * 类目属性键编码
	 */
	@Column(name = "attribute_key_code")
	private String attributeKeyCode;

	/**
	 * 属性值
	 */
	@Column(name = "attribute_value")
	private String attributeValue;

	// 属性键名称
	@Transient
	@Column(name = "attribute_key_name")
	private String attributeKeyName;

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
	 * 获取类目属性键编码
	 *
	 * @return attribute_key_code - 类目属性键编码
	 */
	public String getAttributeKeyCode() {
		return attributeKeyCode;
	}

	/**
	 * 设置类目属性键编码
	 *
	 * @param attributeKeyCode
	 *            类目属性键编码
	 */
	public void setAttributeKeyCode(String attributeKeyCode) {
		this.attributeKeyCode = attributeKeyCode;
	}

	/**
	 * 获取属性值
	 *
	 * @return attribute_value - 属性值
	 */
	public String getAttributeValue() {
		return attributeValue;
	}

	/**
	 * 设置属性值
	 *
	 * @param attributeValue
	 *            属性值
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeKeyName() {
		return attributeKeyName;
	}

	public void setAttributeKeyName(String attributeKeyName) {
		this.attributeKeyName = attributeKeyName;
	}

}