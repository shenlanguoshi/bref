package com.brt.bref.user.feign.entity;

import java.io.Serializable;

/**
 * @author 方杰
 * @date 2018年11月20日
 * @description 权限模块实体类（功能权限）
 */
@SuppressWarnings("serial")
public class PermissionModuleEntity implements Serializable {
	/**
	 * @description 权限id
	 */
	private String permissionId;
	/**
	 * @description 模块id
	 */
	private String moduleId;
	
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
	/**
	 * @description 模块id
	 */
	public String getModuleId() {
		return moduleId;
	}
	/**
	 * @description 模块id
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
}
