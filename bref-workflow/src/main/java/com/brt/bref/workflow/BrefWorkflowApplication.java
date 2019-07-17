package com.brt.bref.workflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {org.activiti.spring.boot.SecurityAutoConfiguration.class, SecurityAutoConfiguration.class})
@EnableEurekaClient
@EnableDiscoveryClient
@EnableCircuitBreaker
@MapperScan(basePackages = {"com.brt.bref.workflow.dao"})
@EnableFeignClients(basePackages = {"com.brt.bref.user.feign.**"})
public class BrefWorkflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrefWorkflowApplication.class, args);
	}
}
