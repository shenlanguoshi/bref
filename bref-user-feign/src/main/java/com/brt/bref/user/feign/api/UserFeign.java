package com.brt.bref.user.feign.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.user.feign.factory.UserFeignFallbackFactory;

/**
 * @author 蒋润
 * @date 2018年10月15日
 * @description: 用户中心服务feign接口
 */
@FeignClient(name = "user-service",fallbackFactory=UserFeignFallbackFactory.class, path = "/user-service")
public interface UserFeign {
	
	/**
	 * @author 蒋润
	 * @date 2018年10月15日
	 * @param username
	 * @return Login
	 * @description: 根据用户名获取用户登录信息
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/user/info/{username}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	JSONObject loadUserByUsername(@PathVariable("username") String username);
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	JSONObject getById(@PathVariable("id") String id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/corporationWork", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	JSONObject listUserCorporationWork(@PathVariable("userId") String userId);
}
