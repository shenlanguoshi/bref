package com.brt.bref.user.feign.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.user.feign.factory.CorporationWorkFeignFallbackFactory;

@FeignClient(name = "user-service",fallbackFactory=CorporationWorkFeignFallbackFactory.class, path = "/user-service")
public interface CorporationWorkFeign {
	@RequestMapping(method = RequestMethod.GET, value = "/corporationWork/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	JSONObject getById(@PathVariable("id") String id);
}
