package com.brt.bref.mdm.svc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringCloudApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.brt.bref.mdm.svc.dao"})
@EnableFeignClients(basePackages = {"com.brt.bref.user.feign.**"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BrefMdmServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrefMdmServiceApplication.class, args);
	}
}
