package com.brt.bref.user.feign.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 蒋润
 * @date 2018年12月4日
 * @description 组织机构实体
 */
@SuppressWarnings("serial")
public class CorporationEntity implements Serializable {

	/**
	 * @description 组织机构id
	 */
	private String id;
	/**
	 * @description 组织机构主管id
	 */
	private String supervisorId;
	/**
	 * @description 组织机构主管登录名
	 */
	private String supervisorName;
	/**
	 * @description 组织机构名称
	 */
	private String name;
	/**
	 * @description 父节点id
	 */
	private String parentId;
	/**
	 * @description 创建时间
	 */
	private Date createAt;
	/**
	 * @description 修改时间
	 */
	private Date updateAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSupervisorId() {
		return supervisorId;
	}
	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
}
