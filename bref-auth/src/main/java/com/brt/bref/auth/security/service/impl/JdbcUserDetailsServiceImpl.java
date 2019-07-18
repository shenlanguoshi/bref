package com.brt.bref.auth.security.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.brt.bref.auth.security.constant.SecurityConstant;
import com.brt.bref.auth.security.service.JdbcUserDetailsService;
import com.brt.bref.common.security.entity.BrefUser;
import com.brt.bref.common.util.TreeUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JdbcUserDetailsServiceImpl extends JdbcDaoSupport implements JdbcUserDetailsService {

	public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY = "";

	private String authPrefix = "bref:";
	//private boolean usernameBasedPrimaryKey = true;
	private boolean enableUserAuthorities = true;
	private boolean enableCorpAuthorities = true;
	private boolean enableCorpWorkAuthorities = true;
	private boolean enableJobPosAuthorities = true;

	public JdbcUserDetailsServiceImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserDetails loadUserByUsername(String jsonStr) throws UsernameNotFoundException {
		JSONObject json = JSON.parseObject(jsonStr);
		String username = json.getString("username");
		String corporationId = json.getString("corporationId");
		
		List<Map<String,Object>> users = loadUsersByUsername(username);

		if (users.size() == 0) {
			log.debug("Query returned no results for user '" + username + "'");
			throw new UsernameNotFoundException("Username '" + username + "' not found");
		}

		Map<String,Object> userMap = users.get(0);
		String id = (String) userMap.get("id");
		String password = (String) userMap.get("password");
		boolean enabled = (boolean) userMap.get("enabled");

		// 权限id集合
		Set<String> permission = new HashSet<>();

		permission.add("bref:basic");

		// 获取用户权限
		if (this.enableUserAuthorities) {
			permission.addAll(loadUserPermission(id));
		}
		
		// 根据登录的组织机构获取对应权限
		if(StringUtils.isNotBlank(corporationId)) {
			// 获取用户组织机构权限
			if (this.enableCorpAuthorities) {
				permission.addAll(loadCorpPermission(id, corporationId));
			}
			// 获取用户岗位权限
			if (this.enableCorpWorkAuthorities) {
				permission.addAll(loadCorpWorkPermission(id, corporationId));
			}
		}
		
		// 获取用户职务权限
		if (this.enableJobPosAuthorities) {
			permission.addAll(loadJobPosPermission(id));
		}

		if(permission.isEmpty()) {
			this.logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");

			throw new UsernameNotFoundException("User '" + username + "' has no GrantedAuthority");
		}
		// 拼接权限
		String in = "(";
		String[] items = new String[permission.size()];
		int i = 0;
		for (String str : permission) {
			in += "?,";
			items[i] = str;
			i++;
		}
		in = in.substring(0, in.length()-1) + ")";

		// 模块（功能+菜单）权限
		Map<String, Object> modules = loadModule(in,items);
		// 功能权限
		Set<GrantedAuthority> dbAuthsSet = new HashSet<>();
		dbAuthsSet.addAll((List<GrantedAuthority>) modules.get("authorities"));
		List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthsSet);
		addCustomAuthorities(username, dbAuths);
		// 菜单权限
		JSONArray menu = (JSONArray) modules.get("menu");
		// 数据权限-列域
		Map<String,String> dataSchema = loadDataSchema(in,items);
		// 数据权限-行域
		Map<String, Map<String, List<Map<String, Object>>>> dataScope = loadDataScope(in,items);

		return createUserDetails(id, username, password, enabled, dbAuths, menu, dataSchema, dataScope);
	}

	protected List<Map<String,Object>> loadUsersByUsername(String username) {
		return getJdbcTemplate().query(SecurityConstant.DEFAULT_USERS_BY_USERNAME_QUERY,
				new String[] { username }, new RowMapper<Map<String,Object>>() {
			@Override
			public Map<String,Object> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Map<String,Object> map = new HashMap<>();
				map.put("id", rs.getString(1));
				map.put("username", rs.getString(2));
				map.put("password", rs.getString(3));
				map.put("enabled", rs.getBoolean(4));
				return map;
			}
		});
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月23日
	 * @param username
	 * @return List<String> 用户权限集合
	 * @description 获取用户权限
	 */
	protected List<String> loadUserPermission(String id) {
		return getJdbcTemplate().query(SecurityConstant.USER_PERMISSION_BY_USERID_QUERY,
				new String[] { id }, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}				
		});
	}

	/**
	 * @author 蒋润
	 * @date 2019年1月10日
	 * @param userId 用户id
	 * @param corporationId 组织机构id
     * @return List<String> 用户组织机构权限集合
	 * @description 获取用户组织机构权限
	 */
	protected List<String> loadCorpPermission(String userId, String corporationId) {
		return getJdbcTemplate().query(SecurityConstant.CORP_PERMISSION_BY_USERID_QUERY,
				new String[] { corporationId, userId }, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}				
		});
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月23日
	 * @param username
	 * @return List<String> 用户岗位权限集合
	 * @description 获取用户岗位权限
	 */
	protected List<String> loadCorpWorkPermission(String userId, String corporationId) {
		return getJdbcTemplate().query(SecurityConstant.CORP_WORK_PERMISSION_BY_USERID_QUERY,
				new String[] { userId, corporationId }, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}				
		});
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月23日
	 * @param username
	 * @return List<String> 用户职务权限集合
	 * @description 获取用户职务权限
	 */
	protected List<String> loadJobPosPermission(String id) {
		return getJdbcTemplate().query(SecurityConstant.JOB_POS_PERMISSION_BY_USERID_QUERY,
				new String[] { id }, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}				
		});
	}


	/**
	 * @author 蒋润
	 * @date 2018年11月24日
	 * @param in 
	 * @param items
	 * @return 模块（功能，菜单）
	 * @description 获取功能菜单
	 */
	protected Map<String, Object> loadModule(String in, Object[] items) {
		// 查询功能权限
		List<Map<String, Object>> result = getJdbcTemplate().queryForList(SecurityConstant.ALL_AUTHORITIES_BY_PERMISSION_QUERY + in, items);
		// 去除重复数据
		// result = CollectionUtil.removeDuplicate(result);

		// 功能
		List<GrantedAuthority> authorities = new ArrayList<>();
		// 菜单
		JSONArray menu = new JSONArray();
		// 功能菜单分类
		for(Map<String, Object> item : result) {
			JSONObject json = new JSONObject();
			json.put("id", item.get("id"));
			json.put("parentId", item.get("parentId"));
			json.put("title", item.get("title"));
			json.put("uri", item.get("uri"));
			json.put("icon", item.get("icon"));
			json.put("menu", item.get("menu"));
			json.put("controls", item.get("controls"));
			json.put("sort", item.get("sort"));
			menu.add(json);
			if(!(boolean) item.get("menu")) {
				authorities.add(new SimpleGrantedAuthority(this.authPrefix + (String) item.get("uri")));
			}
		}
		// 菜单转换成树
		menu = TreeUtil.jsonArrayToTree(menu, "id", "parentId");
		menu = TreeUtil.sortTree(menu, "sort", "children");
		// 结果放入map
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("authorities", authorities);
		resultMap.put("menu", menu);
		return resultMap;
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月24日
	 * @param in
	 * @param items
	 * @return Map<String,String> key=entry value=列名字符串
	 * @description 获取列域
	 */
	protected Map<String,String> loadDataSchema(String in, Object[] items) {
		// 获取列域
		List<Map<String, Object>> dataSchema = getJdbcTemplate().queryForList(SecurityConstant.ALL_DATASCHEMA_BY_PERMISSION_QUERY + in, items);
		// 去重
		// dataSchema = CollectionUtil.removeDuplicate(dataSchema);

		// 中间件map
		Map<String, Map<String, Object>> middleware = new HashMap<>();
		for(Map<String, Object> item : dataSchema) {
			if(middleware.containsKey(item.get("entry"))) {
				if((Integer) item.get("priority") > (Integer) ((Map<String, Object>) middleware.get(item.get("entry"))).get("priority")) {
					middleware.put((String) item.get("entry"), item);
				}
			}else {
				middleware.put((String) item.get("entry"), item);
			}
		}

		// 构建resultMap
		Map<String,String> resultMap = new HashMap<>();		
		for (Entry<String, Map<String, Object>> entry : middleware.entrySet()) { 
			resultMap.put(entry.getKey(), (String) entry.getValue().get("dataSchema"));
		}

		return resultMap;
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月24日
	 * @param in
	 * @param items
	 * @return Map<String,Map<String,String>> key=entry value=[key=列名 value=数据权限sql]
	 * @description 获取行域
	 */
	protected Map<String, Map<String, List<Map<String, Object>>>> loadDataScope(String in, Object[] items) {
		// 获取列域
		List<Map<String, Object>> dataScope = getJdbcTemplate().queryForList(SecurityConstant.ALL_DATASCOPE_BY_PERMISSION_QUERY + in, items);
		// 去重
		// dataSchema = CollectionUtil.removeDuplicate(dataSchema);
		
		// 中间件map
		Map<String, List<Map<String, Object>>> middleware = new HashMap<>();
		for(Map<String, Object> item : dataScope) {
			if(middleware.containsKey(item.get("entry"))) {
				if((Integer) item.get("priority") > (Integer) middleware.get(item.get("entry")).get(0).get("priority")) {
					List<Map<String, Object>> list = new ArrayList<>();
					list.add(item);
					middleware.put((String) item.get("entry"), list);
				}else if((Integer) item.get("priority") == (Integer) middleware.get(item.get("entry")).get(0).get("priority")) {
					List<Map<String, Object>> list = middleware.get(item.get("entry"));
					list.add(item);
					middleware.put((String) item.get("entry"), list);
				}
			}else {
				List<Map<String, Object>> list = new ArrayList<>();
				list.add(item);
				middleware.put((String) item.get("entry"), list);
			}
		}
		
		// 构建resultMap
		Map<String, Map<String,List<Map<String, Object>>>> resultMap = new HashMap<>();
		for (Entry<String, List<Map<String, Object>>> entry : middleware.entrySet()) { 
			Map<String,List<Map<String, Object>>> map = new HashMap<>();
			for(Map<String, Object> column : entry.getValue()) {
				String columnName = (String) column.get("columnName");
				List<Map<String, Object>> list;
				if(map.containsKey(columnName)) {
					list = map.get(columnName);
				}else {
					list = new ArrayList<>();
				}
				Map<String, Object> item = new HashMap<>();
				item.put("columnName", columnName);
				item.put("operator", column.get("operator"));
				item.put("dataScope", column.get("dataScope"));
				list.add(item);
				map.put(columnName, list);
			}
			resultMap.put(entry.getKey(), map);
		}

		return resultMap;
	}

	protected void addCustomAuthorities(String username,
			List<GrantedAuthority> authorities) {
	}

	protected UserDetails createUserDetails(
			String id,
			String username,
			String password,
			boolean enabled,
			List<GrantedAuthority> combinedAuthorities, 
			JSONArray menu, 
			Map<String, String> dataSchema, 
			Map<String, Map<String, List<Map<String, Object>>>> dataScope) {
		/*String returnUsername = userFromUserQuery.getUsername();

		if (!this.usernameBasedPrimaryKey) {
			returnUsername = username;
		}*/

		return new BrefUser(username, password,
				enabled, true, true, true, combinedAuthorities, id, menu, dataSchema, dataScope);
	}
}
