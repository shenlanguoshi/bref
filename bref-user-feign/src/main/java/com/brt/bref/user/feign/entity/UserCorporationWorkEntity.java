package com.brt.bref.user.feign.entity;

import java.io.Serializable;

/**
 * @author 蒋润
 * @date 2018年11月20日
 * @description 用户岗位实体类
 */
@SuppressWarnings("serial")
public class UserCorporationWorkEntity implements Serializable {
	/**
	 * @description 用户id
	 */
	private String userId;
	/**
	 * @description 岗位id
	 */
	private String corporationWorkId;
	
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
		
}
