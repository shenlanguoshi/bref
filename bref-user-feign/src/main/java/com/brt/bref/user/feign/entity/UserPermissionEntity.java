package com.brt.bref.user.feign.entity;

import java.io.Serializable;

/**
 * @author 蒋润
 * @date 2018年11月20日
 * @description 用户权限实体类
 */
@SuppressWarnings("serial")
public class UserPermissionEntity implements Serializable {
	/**
	 * @description 用户id
	 */
	private String userId;
	/**
	 * @description 权限id
	 */
	private String permissionId;
	
	/**
	 * @description 用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @description 用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
