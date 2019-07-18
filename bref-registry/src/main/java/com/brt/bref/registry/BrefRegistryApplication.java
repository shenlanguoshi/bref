package com.brt.bref.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 蒋润
 * @date 2018年15月10日
 * 微服务注册中心
 */
@EnableEurekaServer
@SpringBootApplication
public class BrefRegistryApplication {
	
	/**
	 * @author 蒋润
	 * @date 2018年11月16日
	 * @param args 
	 * 启动方法
	 */
	public static void main(String[] args) {
		SpringApplication.run(BrefRegistryApplication.class, args);
	}
}
