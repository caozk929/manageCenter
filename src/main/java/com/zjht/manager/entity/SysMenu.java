package com.zjht.manager.entity;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zjht.manager.entity.base.BaseSysMenu;

/**
 * SysMenu entity. @author MyEclipse Persistence Tools
 */

public class SysMenu extends BaseSysMenu {

	public SysMenu() {
		super();
	}
	
	public SysMenu(Integer id){
	    super(id);
	}

	public SysMenu(String name, String parentIds, String path, String target,
			String icon, Integer sort, Integer viewStatus, String permission,
			Integer delStatus, Date createTime, Date updateTime) {
		super(name, parentIds, path, target, icon, sort, viewStatus, permission,
				delStatus, createTime, updateTime);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3267152584813525812L;
	/**
	 * 未删除（在用）
	 */
	public static final int DEL_UNDONE=0;
	/**
	 * 已删除
	 */
	public static final int DEL_DONE=1;
	/**
	 * 可见
	 */
	public static final int VIEW_TRUE=1;
	/**
	 * 不可见
	 */
	public static final int VIEW_FALSE=0;
	
	/**
	 * 菜单等级(用于计算菜单列表显示的左边距)
	 */
	private int deep;
	/**
	 * 是否可见
	 */
	private String viewStatusStr;

	public static void sortList(List<SysMenu> list,List<SysMenu> listAll,Integer pid){
		for (int i=0; i<listAll.size(); i++){
			SysMenu e = listAll.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(pid)){
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<listAll.size(); j++){
					SysMenu child = listAll.get(j);
					if (child.getParent()!=null && child.getParent().getId()!=null
							&& child.getParent().getId().equals(e.getId())){
						sortList(list, listAll, e.getId());
						break;
					}
				}
			}
		}
	}
	
	public int getDeep() {
		String pids=getParentIds();
		if (StringUtils.isBlank(pids)) {
			return 0;
		}
		String[] arr=pids.split(",");
		deep=arr.length;
		return deep;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}
	
	public String getViewStatusStr() {
		Integer viewstatus=getViewStatus();
		if (viewstatus==null||viewstatus==0) {
			viewStatusStr="隐藏";
		}else{
			viewStatusStr="显示";
		}
		return viewStatusStr;
	}

	public void setViewStatusStr(String viewStatusStr) {
		this.viewStatusStr = viewStatusStr;
	}
}