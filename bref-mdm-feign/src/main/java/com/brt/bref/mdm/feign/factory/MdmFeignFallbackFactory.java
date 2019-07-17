package com.brt.bref.mdm.feign.factory;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.mdm.feign.api.MdmFeign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MdmFeignFallbackFactory implements FallbackFactory<MdmFeign> {

	@Override
	public MdmFeign create(Throwable cause) {
		return new MdmFeign() {
			
			@Override
			public JSONObject getCompanyByName(String name) {
				log.error("get company {} failed", name);
				return null;
			}
		};
	}

}
