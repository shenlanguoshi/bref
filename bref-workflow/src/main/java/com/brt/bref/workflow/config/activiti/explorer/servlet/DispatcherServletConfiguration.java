package com.brt.bref.workflow.config.activiti.explorer.servlet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Configuration
@ComponentScan({ "org.activiti.rest.editor", "org.activiti.rest.diagram" })
@EnableAsync
@Slf4j
public class DispatcherServletConfiguration extends WebMvcConfigurationSupport {

	@Autowired
	private ObjectMapper objectMapper;

	@SuppressWarnings("unused")
	@Autowired
	private Environment environment;

	@Bean
	public SessionLocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		log.debug("Configuring localeChangeInterceptor");
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		log.debug("Creating requestMappingHandlerMapping");
		RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
		requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
		Object[] interceptors = { localeChangeInterceptor() };
		requestMappingHandlerMapping.setInterceptors(interceptors);
		return requestMappingHandlerMapping;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		addDefaultHttpMessageConverters(converters);
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter) converter;
				jackson2HttpMessageConverter.setObjectMapper(objectMapper);
				break;
			}
		}
	}

	@Override
	protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/explorer/** 访问都映射到classpath:/activiti/ 目录下
        registry.addResourceHandler("/explorer/**").addResourceLocations("classpath:/activiti/");
        
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
    }
	
}
