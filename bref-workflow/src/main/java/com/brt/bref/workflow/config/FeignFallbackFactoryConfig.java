package com.brt.bref.workflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brt.bref.user.feign.factory.CorporationWorkFeignFallbackFactory;
import com.brt.bref.user.feign.factory.UserFeignFallbackFactory;

@Configuration
public class FeignFallbackFactoryConfig {

	@Bean
	public CorporationWorkFeignFallbackFactory corporationWorkFeignFallbackBin() {
		return new CorporationWorkFeignFallbackFactory();
	}
	
	@Bean
	public UserFeignFallbackFactory userFeignFallbackFactoryBin() {
		return new UserFeignFallbackFactory();
	}
}
