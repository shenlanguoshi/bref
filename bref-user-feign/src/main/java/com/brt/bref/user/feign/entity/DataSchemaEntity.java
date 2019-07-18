package com.brt.bref.user.feign.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 蒋润
 * @date 2018年11月28日
 * @description 数据权限-列
 */
@SuppressWarnings("serial")
public class DataSchemaEntity implements Serializable {
	/**
	 * @description id
	 */
	private String id;
	/**
	 * @description 组织机构id
	 */
	private String corporationId;
	/**
	 * @description 标识
	 */
	private String entry;
	/**
	 * @description 优先级
	 */
	private int priority;
	/**
	 * @description 列域
	 */
	private String dataSchema;
	/**
	 * @description 名称
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorporationId() {
		return corporationId;
	}
	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getDataSchema() {
		return dataSchema;
	}
	public void setDataSchema(String dataSchema) {
		this.dataSchema = dataSchema;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
