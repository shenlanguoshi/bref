package com.brt.bref.user.feign.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 方杰
 * @date 2018年11月20日
 * @description 岗位表
 */
@SuppressWarnings("serial")
public class CorporationWorkEntity implements Serializable {
	/**
	 * @description 主键id
	 */
	private String id;
	/**
	 * @description 组织机构id
	 */
	private String corporationId;
	/**
	 * @description 组织机构name
	 */
	private String corporationName;
	/**
	 * @description 岗位名称
	 */
	private String name;
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
	/**
	 * @description 组织机构Name
	 */
	public String getCorporationName() {
		return corporationName;
	}
	/**
	 * @description 组织机构Name
	 */
	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}
	/**
	 * @description 岗位名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * @description 岗位名称
	 */
	public void setName(String name) {
		this.name = name;
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
