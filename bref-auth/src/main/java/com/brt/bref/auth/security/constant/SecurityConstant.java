package com.brt.bref.auth.security.constant;

public interface SecurityConstant {

	/**
	 * 前缀
	 */
	String BREF_PREFIX = "bref:";

	/**
	 * oauth 相关前缀
	 */
	String OAUTH_PREFIX = "oauth:";
	/**
	 * 项目的copyright
	 */
	String BREF_COPYRIGHT = "bref© 2018-2019";
	
	/**
	 * oauth 客户端信息
	 */
	String CLIENT_DETAILS_KEY = "bref:security:oauth:client";
	
	/**
	 * sys_oauth_client_details 表的字段，不包括client_id、client_secret
	 */
	String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
		+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
		+ "refresh_token_validity, additional_information, autoapprove";

	/**
	 * JdbcClientDetailsService 查询语句
	 */
	String BASE_CLIENT_FIND_STATEMENT = "select " + CLIENT_FIELDS
		+ " from oauth_client_details";

	/**
	 * 默认的查询语句
	 */
	String DEFAULT_CLIENT_FIND_STATEMENT = BASE_CLIENT_FIND_STATEMENT + " order by client_id";

	/**
	 * 按条件client_id 查询
	 */
	String DEFAULT_CLIENT_SELECT_STATEMENT = BASE_CLIENT_FIND_STATEMENT + " where client_id = ?";
	
	/**
	 * 按条件username 查询
	 */
	String DEFAULT_USERS_BY_USERNAME_QUERY = "select id, username, CONCAT('{noop}',password) as password, enabled "
			+ "from sys_user " 
			+ "where username = ?";
	
	/**
	 * @description 获取用户权限
	 */
	String USER_PERMISSION_BY_USERID_QUERY = "SELECT sys_user_permission.permission_id "
			+ "FROM sys_user_permission "
			+ "JOIN sys_permission "
			+ "ON sys_permission.del_flag = 0 "
			+ "AND sys_user_permission.permission_id = sys_permission.id "
			+ "WHERE sys_user_permission.user_id = ?";
	
	/**
	 * @description 获取用户组织机构权限
	 */
	String CORP_PERMISSION_BY_USERID_QUERY = "SELECT sys_corporation_permission.permission_id "
			+ "FROM sys_user_corporation "
			+ "JOIN sys_corporation_permission "
			+ "ON sys_user_corporation.corporation_id = sys_corporation_permission.corporation_id "
			+ "AND sys_corporation_permission.corporation_id = ? "
			+ "JOIN sys_permission "
			+ "ON sys_permission.del_flag = 0 "
			+ "AND sys_corporation_permission.permission_id = sys_permission.id "
			+ "WHERE sys_user_corporation.user_id = ? ";
	
	/**
	 * @description 获取用户岗位权限
	 */
	String CORP_WORK_PERMISSION_BY_USERID_QUERY = "SELECT sys_corporation_work_permission.permission_id "
			+ "FROM sys_user_corporation_work "
			+ "JOIN sys_corporation_work_permission "
			+ "ON sys_user_corporation_work.corporation_work_id = sys_corporation_work_permission.corporation_work_id "
			+ "JOIN sys_permission "
			+ "ON sys_permission.del_flag = 0 "
			+ "AND sys_corporation_work_permission.permission_id = sys_permission.id "
			+ "WHERE sys_user_corporation_work.user_id = ? "
			+ "AND sys_user_corporation_work.corporation_id = ? ";
	
	/**
	 * @description 获取用户职务权限
	 */
	String JOB_POS_PERMISSION_BY_USERID_QUERY = "SELECT sys_job_position_permission.permission_id "
			+ "FROM sys_user_job_position "
			+ "JOIN sys_job_position_permission "
			+ "ON sys_user_job_position.job_position_id = sys_job_position_permission.job_position_id "
			+ "JOIN sys_permission "
			+ "ON sys_permission.del_flag = 0 "
			+ "AND sys_job_position_permission.permission_id = sys_permission.id "
			+ "WHERE sys_user_job_position.user_id = ?";
	
	/**
	 * @description 获取功能权限
	 */
	String ALL_AUTHORITIES_BY_PERMISSION_QUERY = "SELECT DISTINCT "
			+ "sys_module.id AS id, "
			+ "sys_module.parent_id AS parentId, "
			+ "sys_module.title AS title, "
			+ "sys_module.uri AS uri, "
			+ "sys_module.icon AS icon,"
			+ "sys_module.is_menu AS menu, "
			+ "sys_module.controls AS controls,"
			+ "sys_module.sort AS sort "
			+ "FROM sys_permission_module "
			+ "JOIN sys_module "
			+ "ON sys_module.del_flag = 0 "
			+ "AND sys_permission_module.module_id = sys_module.id "
			+ "WHERE sys_permission_module.permission_id IN ";
		
	/**
	 * @description 获取列域
	 */
	String ALL_DATASCHEMA_BY_PERMISSION_QUERY = "SELECT DISTINCT " 
			+ "sys_data_schema.entry AS entry, "
			+ "sys_data_schema.data_schema AS dataSchema, "
			+ "sys_data_schema.priority AS priority "
			+ "FROM sys_permission_data_schema "
			+ "JOIN sys_data_schema "
			+ "ON sys_data_schema.del_flag = 0 "
			+ "AND sys_permission_data_schema.data_schema_id = sys_data_schema.id "
			+ "WHERE sys_permission_data_schema.permission_id IN ";
	
	/**
	 * @description 获取行域
	 */
	String ALL_DATASCOPE_BY_PERMISSION_QUERY = "SELECT DISTINCT "
			+ "sys_data_scope.entry AS entry, "
			+ "sys_data_scope.priority AS priority, "
			+ "sys_data_scope_item.column_name AS columnName, "
			+ "sys_data_scope_item.operator AS operator, "
			+ "sys_data_scope_item.data_scope AS dataScope "
			+ "FROM sys_permission_data_scope "
			+ "JOIN sys_data_scope "
			+ "ON sys_data_scope.del_flag = 0 "
			+ "AND sys_permission_data_scope.data_scope_id = sys_data_scope.id "
			+ "JOIN sys_data_scope_item "
			+ "ON sys_data_scope_item.del_flag = 0 "
			+ "AND sys_data_scope.id = sys_data_scope_item.data_scope_id "
			+ "WHERE sys_permission_data_scope.permission_id IN ";
	
	
	/**
	 * 用户权限
	 */
	String USER_AUTHORITIES_BY_USERNAME_QUERY = "select u.username, m.uri as authority "
			+ "from sys_user u, sys_user_permission up, sys_permission_module pm, sys_module m " 
			+ "where u.id = up.user_id " 
			+ "and up.permission_id = pm.permission_id "
			+ "and pm.module_id = m.id "
			+ "and m.is_menu = 0 "
			+ "and u.username = ?";
	/**
	 * 组织机构权限
	 */
	String CORP_AUTHORITIES_BY_USERNAME_QUERY = "select u.username, m.uri as authority "
			+ "from sys_user u, sys_user_corporation uc, sys_corporation c, sys_corporation_permission cp, sys_permission_module pm, sys_module m " 
			+ "where u.id = uc.user_id " 
			+ "and uc.corporation_id = cp.corporation_id " 
			+ "and cp.permission_id = pm.permission_id "
			+ "and pm.module_id = m.id "
			+ "and m.is_menu = 0 "
			+ "and uc.corporation_id = c.id " 
			+ "and c.supervisor_id = u.id "
			+ "and u.username = ?";
	/**
	 * 岗位权限
	 */
	String CORP_WORK_AUTHORITIES_BY_USERNAME_QUERY = "select u.username, m.uri as authority "
			+ "from sys_user u, sys_user_corporation uc, sys_corporation_work cw, sys_corporation_work_permission cwp, sys_permission_module pm, sys_module m " 
			+ "where u.id = uc.user_id " 
			+ "and uc.corporation_id = cw.corporation_id " 
			+ "and cw.id = cwp.corporation_work_id " 
			+ "and cwp.permission_id = pm.permission_id "
			+ "and pm.module_id = m.id "
			+ "and m.is_menu = 0 "
			+ "and u.username = ?";
	/**
	 * 职务权限
	 */
	String JOB_POS_AUTHORITIES_BY_USERNAME_QUERY = "select u.username, m.uri as authority "
			+ "from sys_user u, sys_job_position_permission jpp, sys_permission_module pm, sys_module m " 
			+ "where u.id = jpp.job_position_id " 
			+ "and jpp.permission_id = pm.permission_id "
			+ "and pm.module_id = m.id "
			+ "and m.is_menu = 0 "
			+ "and u.username = ?";
}
