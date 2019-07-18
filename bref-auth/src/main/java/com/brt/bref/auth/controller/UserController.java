package com.brt.bref.auth.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.auth.security.constant.SecurityConstant;
import com.brt.bref.common.security.entity.BrefUser;
import com.brt.bref.common.util.ResponseUtil;
import com.brt.bref.common.util.StringUtil;

@RestController
public class UserController {
	private static final String BREF_OAUTH_ACCESS = SecurityConstant.BREF_PREFIX + SecurityConstant.OAUTH_PREFIX + "access:";
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private StringRedisTemplate redisTemplate;
	
    @RequestMapping(value = "/user")
    public Principal user(Principal user) {
        return user;
    }
    
	/**
	 * @author 蒋润
	 * @date 2018年12月1日
	 * @param authHeader
	 * @return 是否成功
	 * @description 退出登录
	 */
	@RequestMapping("/removeToken")
	public JSONObject logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
		if (StringUtils.isNotBlank(authHeader)) {
			String tokenValue = authHeader.replace("bearer", "").trim();
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
			if (accessToken == null || StringUtils.isBlank(accessToken.getValue())) {
				return ResponseUtil.responseResult(false, "退出失败，token为空", null);
			}
			tokenStore.removeAccessToken(accessToken);
		}

		return ResponseUtil.responseResult(true, "退出成功", null);
	}
	
	
	/**
	 * 查询token
	 *
	 * @param params 分页参数
	 * @param from   标志
	 * @return
	 */
	@RequestMapping("/listToken")
	public JSONObject tokenList() {

		List<Map<String, String>> list = new ArrayList<>();
		
		Set<String> keys = redisTemplate.keys(BREF_OAUTH_ACCESS + "*");
		

		for (String page : keys) {
			String accessToken = StringUtil.subAfter(page, BREF_OAUTH_ACCESS, true);
			OAuth2AccessToken token = tokenStore.readAccessToken(accessToken);
			Map<String, String> map = new HashMap<>(8);


			map.put("token_type", token.getTokenType());
			map.put("token_value", token.getValue());
			map.put("expires_in", token.getExpiresIn() + "");


			OAuth2Authentication oAuth2Auth = tokenStore.readAuthentication(token);
			Authentication authentication = oAuth2Auth.getUserAuthentication();

			map.put("client_id", oAuth2Auth.getOAuth2Request().getClientId());
			map.put("grant_type", oAuth2Auth.getOAuth2Request().getGrantType());

			if (authentication instanceof UsernamePasswordAuthenticationToken) {
				UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

				if (authenticationToken.getPrincipal() instanceof BrefUser) {
					BrefUser user = (BrefUser) authenticationToken.getPrincipal();
					map.put("user_id", user.getId() + "");
					map.put("user_name", user.getUsername() + "");
				}
			} else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
				//刷新token方式
				PreAuthenticatedAuthenticationToken authenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
				if (authenticationToken.getPrincipal() instanceof BrefUser) {
					BrefUser user = (BrefUser) authenticationToken.getPrincipal();
					map.put("user_id", user.getId() + "");
					map.put("user_name", user.getUsername() + "");
				}
			}
			list.add(map);
		}
		//result.setTotal(redisTemplate.keys(BREF_OAUTH_ACCESS + "*").size());
		return ResponseUtil.responseResult(true, "查询成功", list);
	}
}
