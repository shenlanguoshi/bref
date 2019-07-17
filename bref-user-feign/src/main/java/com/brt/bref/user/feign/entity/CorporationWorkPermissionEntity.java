package com.brt.bref.user.feign.entity;

import java.io.Serializable;

/**
 * @author 方杰
 * @date 2018年11月20日
 * @description 岗位权限实体类
 */
@SuppressWarnings("serial")
public class CorporationWorkPermissionEntity implements Serializable {
	/**
	 * @description 岗位id
	 */
	private String corporationWorkId;
	/**
	 * @description 权限id
	 */
	private String permissionId;
	
	/**
	 * @description 岗位id
	 */
	public String getCorporationWorkId() {
		return corporationWorkId;
	}
	/**
	 * @description 岗位id
	 */
	public void setCorporationWorkId(String corporationWorkId) {
		this.corporationWorkId = corporationWorkId;
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
