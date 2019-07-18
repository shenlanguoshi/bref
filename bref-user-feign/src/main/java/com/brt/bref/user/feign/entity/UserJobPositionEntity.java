package com.brt.bref.user.feign.entity;

import java.io.Serializable;

/**
 * @author 蒋润
 * @date 2018年11月20日
 * @description 用户职务实体类
 */
@SuppressWarnings("serial")
public class UserJobPositionEntity implements Serializable {
	/**
	 * @description 用户id
	 */
	private String userId;
	/**
	 * @description 职务id
	 */
	private String jobPositionId;
	
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
}
