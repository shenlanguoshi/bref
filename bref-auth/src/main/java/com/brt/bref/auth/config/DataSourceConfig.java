package com.brt.bref.auth.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

	@Bean(name = "clientDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.client")
	@Primary
	public DataSource clientDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "userDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.user")
	public DataSource userDataSource() {
		return DataSourceBuilder.create().build();
	}
}
