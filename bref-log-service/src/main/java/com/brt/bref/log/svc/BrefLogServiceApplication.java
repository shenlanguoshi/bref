package com.brt.bref.log.svc;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringCloudApplication
@EnableEurekaClient
public class BrefLogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrefLogServiceApplication.class, args);
	}
}
