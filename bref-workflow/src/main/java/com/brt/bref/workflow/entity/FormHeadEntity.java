package com.brt.bref.workflow.entity;

import java.io.Serializable;

/**
 * @author 蒋润
 * @date 2018年12月17日
 * @description 表单实体
 */
@SuppressWarnings("serial")
public class FormHeadEntity implements Serializable {
	private String id;
	private String name;
	private String json;
	private String createAt;
	private String updateAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public String getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

}

