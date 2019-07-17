package com.brt.bref.workflow.config.activiti;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brt.bref.workflow.config.activiti.explorer.servlet.JsonpCallbackFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
@Configuration
public class ActivitiConfig  {
	@Bean
	public JsonpCallbackFilter filter(){
	    return new JsonpCallbackFilter();
	}
	
	@Bean
	public ObjectMapper objectMapper(){
	    return new ObjectMapper();
	}
}
