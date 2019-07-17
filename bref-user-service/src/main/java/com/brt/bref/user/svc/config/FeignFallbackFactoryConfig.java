package com.brt.bref.user.svc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brt.bref.mdm.feign.factory.MdmFeignFallbackFactory;

@Configuration
public class FeignFallbackFactoryConfig {

	@Bean
	public MdmFeignFallbackFactory mdmFeignFallbackFactoryBin() {
		return new MdmFeignFallbackFactory();
	}
}
