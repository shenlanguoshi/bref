package com.brt.bref.user.svc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brt.bref.common.util.OSSClientUtil;


@Configuration
public class OSSConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "oss")
	public OSSClientUtil getOSSClientUtil() {
		return new OSSClientUtil();
	}
}
