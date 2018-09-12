package com.zjht.manager.entity.commodity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 商品属性键
 * 
 * @author zhangyaohui
 * @since Aug 29, 2017 11:42:48 AM
 *
 */
@Table(name = "t_product_attribute_key")
public class ProductAttributeKey implements Serializable {

	private static final long serialVersionUID = 4502870358847351759L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_PRODUCTATTRIBUTEKEY'),12,'0')")
	private String id;

	/**
	 * 商品编码
	 */
	@Column(name = "product_code")
	private String productCode;

	/**
	 * 属性键编码
	 */
	@Column(name = "attribute_key_code")
	private String attributeKeyCode;

	/**
	 * 属性键顺序
	 */
	@Column(name = "attribute_key_order")
	private Byte attributeKeyOrder;

	// 属性键名称
	@Transient
	@Column(name = "attribute_key_name")
	private String attributeKeyName;
	// 属性键类型0：字符；1：地址；2：图片
	@Transient
	@Column(name = "attribute_key_type")
	private Byte attributeKeyType;

	@Transient
	private String attributeKeyTypeStr;

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
	 * 获取属性键编码
	 *
	 * @return attribute_key_code - 属性键编码
	 */
	public String getAttributeKeyCode() {
		return attributeKeyCode;
	}

	/**
	 * 设置属性键编码
	 *
	 * @param attributeKeyCode
	 *            属性键编码
	 */
	public void setAttributeKeyCode(String attributeKeyCode) {
		this.attributeKeyCode = attributeKeyCode;
	}

	/**
	 * 获取属性键顺序
	 *
	 * @return attribute_key_order - 属性键顺序
	 */
	public Byte getAttributeKeyOrder() {
		return attributeKeyOrder;
	}

	/**
	 * 设置属性键顺序
	 *
	 * @param attributeKeyOrder
	 *            属性键顺序
	 */
	public void setAttributeKeyOrder(Byte attributeKeyOrder) {
		this.attributeKeyOrder = attributeKeyOrder;
	}

	public String getAttributeKeyName() {
		return attributeKeyName;
	}

	public void setAttributeKeyName(String attributeKeyName) {
		this.attributeKeyName = attributeKeyName;
	}

	public Byte getAttributeKeyType() {
		return attributeKeyType;
	}

	public void setAttributeKeyType(Byte attributeKeyType) {
		this.attributeKeyType = attributeKeyType;
	}

	public String getAttributeKeyTypeStr() {
		if (null == attributeKeyType) {
			return "";
		}
		switch (attributeKeyType) {
		case 0:
			return "字符";
		case 1:
			return "地址";
		case 2:
			return "图片";
		}
		return "";
	}
}