package com.zjht.manager.entity.commodity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 属性键
 * 
 * @author zhangyaohui
 * @since Aug 28, 2017 11:18:07 AM
 *
 */
@Table(name = "t_attribute_key")
public class AttributeKey implements Serializable {
	private static final long serialVersionUID = -3115870320956646807L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_ATTRIBUTEKEY'),12,'0')")
	private String id;
	// 属性键代码
	@Column(name = "attribute_key_code")
	private String attributeKeyCode;
	// 属性键名称
	@Column(name = "attribute_key_name")
	private String attributeKeyName;
	// 属性键类型0：字符；1：地址；2：图片
	@Column(name = "attribute_key_type")
	private Byte attributeKeyType;
	
	/**
	 * 属性键顺序
	 */
	@Transient
	@Column(name = "attribute_key_order")
	private Byte attributeKeyOrder;	

	@Transient
	private String attributeKeyTypeStr;

	@Transient
	private String orderByClause;

	/**
	 * 属性值
	 */
	@Transient
	private String attributeValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttributeKeyCode() {
		return attributeKeyCode;
	}

	public void setAttributeKeyCode(String attributeKeyCode) {
		this.attributeKeyCode = attributeKeyCode;
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

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public Byte getAttributeKeyOrder() {
		return attributeKeyOrder;
	}

	public void setAttributeKeyOrder(Byte attributeKeyOrder) {
		this.attributeKeyOrder = attributeKeyOrder;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
}
