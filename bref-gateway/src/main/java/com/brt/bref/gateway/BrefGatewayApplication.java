package com.brt.bref.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BrefGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrefGatewayApplication.class, args);
	}
}
