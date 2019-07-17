package com.brt.bref.workflow.config.activiti;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.brt.bref.workflow.config.activiti.custom.CustomGroupEntityManagerFactory;
import com.brt.bref.workflow.config.activiti.custom.CustomIdGenerator;
import com.brt.bref.workflow.config.activiti.custom.CustomUserEntityManagerFactory;

@Configuration
public class ActitytiDataSourceConfig extends AbstractProcessEngineAutoConfiguration {
 
    @Autowired
    DruidDataSource dataSource;
    
    @Autowired
    CustomUserEntityManagerFactory customUserEntityManagerFactory;
    
    @Autowired
    CustomGroupEntityManagerFactory customGroupEntityManagerFactory;
 
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean
    public List<SessionFactory> customSessionFactories(){
    	List<SessionFactory> customSessionFactories = new ArrayList<>();
    	customSessionFactories.add(customUserEntityManagerFactory);
    	customSessionFactories.add(customGroupEntityManagerFactory);
    	return customSessionFactories;
    }
 
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
    	
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setActivityFontName("宋体");
        configuration.setLabelFontName("宋体");
        configuration.setDataSource(dataSource);
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE);
        configuration.setJobExecutorActivate(true);
        configuration.setTransactionManager(transactionManager());
        configuration.setCustomSessionFactories(customSessionFactories());
        configuration.setIdGenerator(new CustomIdGenerator());
        return configuration;
    }
 
}
