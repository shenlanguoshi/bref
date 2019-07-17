package com.brt.bref.mdm.feign.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.mdm.feign.factory.MdmFeignFallbackFactory;

/**
 * @author 蒋润
 * @date 2018年10月15日
 * @description: 主数据中心服务feign接口
 */
@FeignClient(name = "mdm-service", fallbackFactory = MdmFeignFallbackFactory.class, path = "/mdm-service")
public interface MdmFeign {

	/**
	 * @author 蒋润
	 * @date 2018年10月15日
	 * @param username
	 * @return JSONObject
	 * @description: 根据主数据名获取主数据登录信息
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/company/info/{name}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	JSONObject getCompanyByName(@PathVariable("name") String name);
}
