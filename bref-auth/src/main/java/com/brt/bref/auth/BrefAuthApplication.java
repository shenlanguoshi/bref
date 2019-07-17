package com.brt.bref.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringCloudApplication
@EnableEurekaClient
public class BrefAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrefAuthApplication.class, args);
	}
}
