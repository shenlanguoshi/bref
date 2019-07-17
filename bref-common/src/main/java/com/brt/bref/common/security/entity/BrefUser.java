package com.brt.bref.common.security.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.alibaba.fastjson.JSONArray;

public class BrefUser extends User {
	private static final long serialVersionUID = -1472568301884926115L;
	/**
	 * @description id
	 */
	String id;
	/**
	 * @description 菜单树
	 */
	private JSONArray menu;
	/**
	 * @description 列域 key:标识 value:可查询的列 
	 * 				例如: "dataSchema": {
            				"user:list": "id,username",
            				"corporation:list": "id,name"
        				}				
	 */
	private Map<String,String> dataSchema; 
	
	/**
	 * @description 行域 key:标识 value 需过滤的列
	 * 				例如:"dataScope": {
     *       				"user:list": {
     *           				"corporation_id": {corporation_id = '1',corporation_id = '2'},
     *           				"id": {id IN ('1','2')}
     *       				}
     *   				}
	 */
	private Map<String, Map<String,List<Map<String, Object>>>> dataScope;
	
	public JSONArray getMenu() {
		return menu;
	}

	public void setMenu(JSONArray menu) {
		this.menu = menu;
	}

	public Map<String, String> getDataSchema() {
		return dataSchema;
	}

	public void setDataSchema(Map<String, String> dataSchema) {
		this.dataSchema = dataSchema;
	}
	
	public Map<String, Map<String,List<Map<String, Object>>>> getDataScope() {
		return dataScope;
	}

	public void setDataScope(Map<String, Map<String,List<Map<String, Object>>>> dataScope) {
		this.dataScope = dataScope;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public BrefUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,
			String id,
			JSONArray menu,
			Map<String,String> dataSchema, 
			Map<String, Map<String,List<Map<String, Object>>>> dataScope) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.menu = menu;
		this.dataSchema = dataSchema;
		this.dataScope = dataScope;
	}	
	
}
