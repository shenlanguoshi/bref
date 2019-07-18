package com.brt.bref.user.feign.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 蒋润
 * @date 2018年11月28日
 * @description 数据权限-行-item
 */
@SuppressWarnings("serial")
public class DataScopeItemEntity implements Serializable {
	/**
	 * @description id
	 */
	private String id;
	/**
	 * @description 行域id
	 */
	private String dataScopeId;
	/**
	 * @description 列名
	 */
	private String columnName;
	/**
	 * @description 判断符号
	 */
	private String operator;
	/**
	 * @description 行域
	 */
	private String dataScope;
	/**
	 * @description 创建时间
	 */
	private Date createAt;
	/**
	 * @description 修改时间
	 */
	private Date updateAt;
	
	/**
	 * @description id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @description id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @description 行域id
	 */
	public String getDataScopeId() {
		return dataScopeId;
	}
	/**
	 * @description 行域id
	 */
	public void setDataScopeId(String dataScopeId) {
		this.dataScopeId = dataScopeId;
	}
	/**
	 * @description 列名
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @description 列名
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @description 判断符号
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @description 判断符号
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * @description 行域
	 */
	public String getDataScope() {
		return dataScope;
	}
	/**
	 * @description 行域
	 */
	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
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
