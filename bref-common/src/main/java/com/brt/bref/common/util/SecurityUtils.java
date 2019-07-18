package com.brt.bref.common.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.brt.bref.common.constant.EntryConstant;

/**
 * @author 蒋润
 * @date 2018年11月26日
 * @description 安全工具类
 */
public class SecurityUtils {

	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @return Authentication
	 * @description 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	//	private static JSONObject getUser(Authentication authentication) {
	//		return JSONObject.parseObject(JSON.toJSONString(authentication))
	//				.getJSONObject("userAuthentication")
	//				.getJSONObject("details")
	//				.getJSONObject("principal");
	//	}
	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @param authentication
	 * @return JSONObject
	 * @description 获取用户
	 */
	@SuppressWarnings("rawtypes")
	private static JSONObject getUser(Authentication authentication) {
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
			Object user = ((LinkedHashMap) ((UsernamePasswordAuthenticationToken)auth2Authentication.getUserAuthentication()).getDetails()).get("principal");
			return JSONObject.parseObject(JSONObject.toJSONString(user));
		}
		return null;
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @return 
	 * @description 获取ClientId
	 */
	public static String getClientId() {
		Authentication authentication = getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
			return auth2Authentication.getOAuth2Request().getClientId();
		}
		return null;
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @return JSONObject
	 * @description 获取用户
	 */
	public static JSONObject getUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getUser(authentication);
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @return JSONObject
	 * @description 获取菜单
	 */
	public static JSONArray getMenu() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getUser(authentication).getJSONArray("menu");
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @return JSONObject
	 * @description 获取列域
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getDataSchema() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		String dataSchemaJsonString = getUser(authentication).getString(EntryConstant.DATA_SCHEMA);
		Map<String,String> dataSchemaMap = JSON.parseObject(dataSchemaJsonString, Map.class);
		return dataSchemaMap;
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @return JSONObject
	 * @description 获取行域
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Map<String,List<Map<String, Object>>>> getDataScope() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		String dataScopeJsonString = getUser(authentication).getString(EntryConstant.DATA_SCOPE);
		Map<String, Map<String,List<Map<String, Object>>>> dataScopeMap = JSON.parseObject(dataScopeJsonString, Map.class);
		return dataScopeMap;
	}

}
