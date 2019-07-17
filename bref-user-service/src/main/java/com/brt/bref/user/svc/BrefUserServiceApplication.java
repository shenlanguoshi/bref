package com.brt.bref.user.svc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableEurekaClient
@EnableCaching
@MapperScan(basePackages = {"com.brt.bref.user.svc.dao"})
@EnableFeignClients(basePackages = {"com.brt.bref.mdm.feign.**"})
public class BrefUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrefUserServiceApplication.class, args);
	}
}
