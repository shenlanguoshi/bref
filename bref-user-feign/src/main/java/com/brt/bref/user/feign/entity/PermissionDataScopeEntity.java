package com.brt.bref.user.feign.entity;

import java.io.Serializable;

/**
 * @author 蒋润
 * @date 2018年11月29日
 * @description 权限行数据权限关联实体
 */
@SuppressWarnings("serial")
public class PermissionDataScopeEntity implements Serializable {
	/**
	 * @description 权限id
	 */
	private String permissionId;
	
	/**
	 * @description 数据权限-行id
	 */
	private String dataScopeId;

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
	 * @description 数据权限-行id
	 */
	public String getDataScopeId() {
		return dataScopeId;
	}
	/**
	 * @description 数据权限-行id
	 */
	public void setDataScopeId(String dataScopeId) {
		this.dataScopeId = dataScopeId;
	}
	
}
