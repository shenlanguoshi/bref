package com.brt.bref.user.feign.factory;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.user.feign.api.CorporationWorkFeign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CorporationWorkFeignFallbackFactory implements FallbackFactory<CorporationWorkFeign> {

	public CorporationWorkFeign create(Throwable cause) {
		return new CorporationWorkFeign() {

			@Override
			public JSONObject getById(String id) {
				log.error("load corporationWork {} failed", id);
				return null;
			}
		};
	}
}
