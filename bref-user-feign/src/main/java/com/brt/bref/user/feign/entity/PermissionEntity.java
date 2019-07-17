package com.brt.bref.user.feign.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 方杰
 * @date 2018年11月20日
 * @description 权限实体类
 */
@SuppressWarnings("serial")
public class PermissionEntity implements Serializable {
	/**
	 * @description 主键id
	 */
	private String id;
	/**
	 * @description 权限名
	 */
	private String name;
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
	 * @description 权限名
	 */
	public String getName() {
		return name;
	}
	/**
	 * @description 权限名
	 */
	public void setName(String name) {
		this.name = name;
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
