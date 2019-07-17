package com.brt.bref.user.feign.entity;

import java.io.Serializable;

/**
 * @author 方杰
 * @date 2018年11月29日
 * @description 权限列数据权限关联实体
 */
@SuppressWarnings("serial")
public class PermissionDataSchemaEntity implements Serializable {
	/**
	 * @description 权限id
	 */
	private String permissionId;
	/**
	 * @description 数据权限-列id
	 */
	private String dataSchemaId;
	
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
	 * @description 数据权限-列id
	 */
	public String getDataSchemaId() {
		return dataSchemaId;
	}
	/**
	 * @description 数据权限-列id
	 */
	public void setDataSchemaId(String dataSchemaId) {
		this.dataSchemaId = dataSchemaId;
	}
	
}
