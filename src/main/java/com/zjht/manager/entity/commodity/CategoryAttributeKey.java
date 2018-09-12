package com.zjht.manager.entity.commodity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 类目属性键
 * 
 * @author zhangyaohui
 * @since Aug 28, 2017 11:48:59 AM
 *
 */
@Table(name = "t_category_attribute_key")
public class CategoryAttributeKey implements Serializable {
	private static final long serialVersionUID = -4633099114174695442L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CATEGORYATTRIBUTEKEY'),12,'0')")
	private String id;
	// 类目代码（只有类型为叶的类目才能建属性键）
	@Column(name = "category_code")
	private String categoryCode;
	// 属性键代码
	@Column(name = "attribute_key_code")
	private String attributeKeyCode;
	// 属性键顺序
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getAttributeKeyCode() {
		return attributeKeyCode;
	}

	public void setAttributeKeyCode(String attributeKeyCode) {
		this.attributeKeyCode = attributeKeyCode;
	}

	public Byte getAttributeKeyOrder() {
		return attributeKeyOrder;
	}

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
		if(null == attributeKeyType) {
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
