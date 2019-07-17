package com.brt.bref.user.feign.factory;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.user.feign.api.UserFeign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserFeignFallbackFactory implements FallbackFactory<UserFeign> {

	public UserFeign create(Throwable cause) {
		return new UserFeign() {
			
			public JSONObject loadUserByUsername(String username) {
				log.error("load user {} failed", username);
				return null;
			}
			
			@Override
			public JSONObject getById(String id) {
				log.error("load user {} failed", id);
				return null;
			}

			@Override
			public JSONObject listUserCorporationWork(String userId) {
				log.error("load userCorporationWork {} failed", userId);
				return null;
			}
			
		};
	}

}
