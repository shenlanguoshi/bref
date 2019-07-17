package com.brt.bref.user.feign.entity;

import java.io.Serializable;

/**
 * @author 方杰
 * @date 2018年11月20日
 * @description 组织机构权限实体类
 */
@SuppressWarnings("serial")
public class CorporationPermissionEntity implements Serializable {
	/**
	 * @description 组织机构id
	 */
	private String corporationId;
	/**
	 * @description 权限id
	 */
	private String permissionId;
	
	/**
	 * @description 组织机构id
	 */
	public String getCorporationId() {
		return corporationId;
	}
	/**
	 * @description 组织机构id
	 */
	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}
	/**
	 * @description 权限id
	 */
	public String getPermissionId() {
		return permissionId;
	}
	/**
	 * @description 权限id
	 */
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
}
