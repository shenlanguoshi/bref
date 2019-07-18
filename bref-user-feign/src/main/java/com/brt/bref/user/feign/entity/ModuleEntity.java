package com.brt.bref.user.feign.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 蒋润
 * @date 2018年11月20日
 * @description 模块实体类
 */
@SuppressWarnings("serial")
public class ModuleEntity implements Serializable {
	/**
	 * @description 主键id
	 */
	private String id;
	/**
	 * @description 模块名
	 */
	private String title;
	/**
	 * @description 父模块id
	 */
	private String parentId;
	/**
	 * @description 模块连接地址
	 */
	private String uri;
	/**
	 * @description 模块图标
	 */
	private String icon;
	/**
	 * @description 模块的排序
	 */
	private int sort;
	/**
	 * @description 模块是否是菜单
	 */
	private boolean menu;
	/**
	 * @description 前端控件
	 */
	private String controls;
	/**
	 * @description 创建时间
	 */
	private Date createAt;
	/**
	 * @description 修改时间
	 */
	private Date updateAt;
	
	/**
	 * @description 主键id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @description 主键id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @description 模块名
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @description 模块名
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @description 父模块id
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @description 父模块id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @description 模块连接地址
	 */
	public String getUri() {
		return uri;
	}
	/**
	 * @description 模块连接地址
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}
	/**
	 * @description 模块图标
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @description 模块图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @description 模块的排序
	 */
	public int getSort() {
		return sort;
	}
	/**
	 * @description 模块的排序
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}
	/**
	 * @description 模块是否是菜单
	 */
	public boolean isMenu() {
		return menu;
	}
	/**
	 * @description 模块是否是菜单
	 */
	public void setMenu(boolean menu) {
		this.menu = menu;
	}
	/**
	 * @description 前端控件
	 */
	public String getControls() {
		return controls;
	}
	/**
	 * @description 前端控件
	 */
	public void setControls(String controls) {
		this.controls = controls;
	}
	/**
	 * @description 创建时间
	 */
	public Date getCreateAt() {
		return createAt;
	}
	/**
	 * @description 创建时间
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	/**
	 * @description 修改时间
	 */
	public Date getUpdateAt() {
		return updateAt;
	}
	/**
	 * @description 修改时间
	 */
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
	
}
