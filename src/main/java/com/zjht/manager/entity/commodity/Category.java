package com.zjht.manager.entity.commodity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 商品类目
 * 
 * @author zhangyaohui
 * @since Aug 28, 2017 11:07:23 AM
 *
 */
@Table(name = "t_category")
public class Category implements Serializable {
	private static final long serialVersionUID = 3451511611908641783L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CATEGORY'),12,'0')")
	private String id;
	// 类目代码
	@Column(name = "category_code")
	private String categoryCode;
	// 类目名称
	@Column(name = "category_name")
	private String categoryName;
	// 类目类型：0-叶；1-枝
	@Column(name = "category_type")
	private Byte categoryType;
	// 上级类目
	@Column(name = "up_code")
	private String upCode;
	// 状态
	private Byte status;
	// 生成时间
	@Column(name = "gmt_create")
	private Timestamp gmtCreate;
	// 修改时间
	@Column(name = "gmt_modified")
	private Timestamp gmtModified;
	// 层级
	private Byte level;	

	// 类目类型
	@Transient
	private String categoryTypeStr;
	// 类目状态
	@Transient
	private String statusStr;

	@Transient
	private List<Category> children;// 扩展字段，存放下级菜单

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}
	
	@Transient
	private String upName;	

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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Byte getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Byte categoryType) {
		this.categoryType = categoryType;
	}

	public String getUpCode() {
		return upCode;
	}

	public void setUpCode(String upCode) {
		this.upCode = upCode;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Timestamp gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getStatusStr() {
		if (null == status) {
			return "";
		}
		switch (status) {
		case 1:
			return "启用";
		default:
			return "停用";
		}
	}

	public String getCategoryTypeStr() {
		if (null == categoryType) {
			return "";
		}
		switch (categoryType) {
		case 0:
			return "根";
		case 1:
			return "枝";
		case 2:
			return "叶";
		case 3:
			return "根叶";
		}
		return "";
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public String getUpName() {
		return upName;
	}

	public void setUpName(String upName) {
		this.upName = upName;
	}	

}
