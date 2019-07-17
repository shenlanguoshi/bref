package com.brt.bref.user.feign.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 方杰
 * @date 2018年11月20日
 * @description 用户实体类
 */
@SuppressWarnings("serial")
public class UserEntity implements Serializable {

	/**
	 * @description 主键id
	 */
	private String id;
	/**
	 * @description 用户名
	 */
	private String username;
	/**
	 * @description 密码
	 */
	private String password;
	/**
	 * @description 是否启用 1：已启用 0：禁止
	 */
	private boolean enabled;
	/**
	 * @description 创建时间
	 */
	private Date createAt;
	/**
	 * @description 修改时间
	 */
	private Date updateAt;
	
	/**
	 * @description 主键id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @description 主键id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @description 用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @description 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @description 密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @description 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @description 是否启用 1：已启用 0：禁止
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @description 是否启用 1：已启用 0：禁止
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @description 创建时间
	 */
	public Date getCreateAt() {
		return createAt;
	}
	/**
	 * @description 创建时间
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	/**
	 * @description 修改时间
	 */
	public Date getUpdateAt() {
		return updateAt;
	}
	/**
	 * @description 修改时间
	 */
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
		
}
