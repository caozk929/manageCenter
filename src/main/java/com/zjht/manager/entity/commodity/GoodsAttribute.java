package com.zjht.manager.entity.commodity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_goods_attribute")
public class GoodsAttribute implements Serializable {

	private static final long serialVersionUID = 4252694454855136249L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_GOODSATTRIBUTE'),12,'0')")
	private String id;

	/**
	 * 单品编码
	 */
	@Column(name = "goods_code")
	private String goodsCode;

	/**
	 * 商品属性键编码
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
	 * 获取单品编码
	 *
	 * @return goods_code - 单品编码
	 */
	public String getGoodsCode() {
		return goodsCode;
	}

	/**
	 * 设置单品编码
	 *
	 * @param goodsCode
	 *            单品编码
	 */
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	/**
	 * 获取商品属性键编码
	 *
	 * @return attribute_key_code - 商品属性键编码
	 */
	public String getAttributeKeyCode() {
		return attributeKeyCode;
	}

	/**
	 * 设置商品属性键编码
	 *
	 * @param attributeKeyCode
	 *            商品属性键编码
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