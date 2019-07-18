package com.brt.bref.workflow.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 蒋润
 * @date 2018年12月17日
 * @description 表单内容实体类
 */
@SuppressWarnings("serial")
public class FormDataEntity implements Serializable {
	private String id;
	private String formId;
	private String json;
	private String procinstId;
	private String taskId;
	private Date createAt;
	private Date updateAt;
	private String createUsername;
	private String updateUsername;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getProcinstId() {
		return procinstId;
	}
	public void setProcinstId(String procinstId) {
		this.procinstId = procinstId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	public String getCreateUsername() {
		return createUsername;
	}
	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}
	public String getUpdateUsername() {
		return updateUsername;
	}
	public void setUpdateUsername(String updateUsername) {
		this.updateUsername = updateUsername;
	}
	
}

