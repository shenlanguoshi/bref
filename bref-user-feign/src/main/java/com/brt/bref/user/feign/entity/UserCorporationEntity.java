package com.brt.bref.user.feign.entity;

import java.io.Serializable;

/**
 * @author 方杰
 * @date 2018年11月20日
 * @description 用户组织机构实体类
 */
@SuppressWarnings("serial")
public class UserCorporationEntity implements Serializable {
	/**
	 * @description 用户id
	 */
	private String userId;
	/**
	 * @description 组织机构id
	 */
	private String corporationId;
	
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
	
}
