package com.brt.bref.user.feign.entity;

import java.io.Serializable;

/**
 * @author 方杰
 * @date 2018年11月20日
 * @description 职务权限实体类
 */
@SuppressWarnings("serial")
public class JobPositionPermissionEntity implements Serializable {
	/**
	 * @description 职务id
	 */
	private String jobPositionId;
	/**
	 * @description 权限id
	 */
	private String permissionId;
	
	/**
	 * @description 职务id
	 */
	public String getJobPositionId() {
		return jobPositionId;
	}
	/**
	 * @description 职务id
	 */
	public void setJobPositionId(String jobPositionId) {
		this.jobPositionId = jobPositionId;
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
