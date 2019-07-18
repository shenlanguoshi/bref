package com.brt.bref.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author 蒋润
 * @date 2018年11月16日
 * 配置中心
 */
@EnableConfigServer
@SpringBootApplication
public class BrefConfigApplication {
	
	/**
	 * @author 蒋润
	 * @date 2018年11月16日
	 * @param args 
	 * 启动方法
	 */
	public static void main(String[] args) {
		SpringApplication.run(BrefConfigApplication.class, args);
	}
}
