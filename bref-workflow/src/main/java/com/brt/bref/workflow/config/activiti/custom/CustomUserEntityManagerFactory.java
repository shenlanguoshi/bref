package com.brt.bref.workflow.config.activiti.custom;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomUserEntityManagerFactory implements SessionFactory {

	@Autowired
	private CustomUserEntityManager customUserEntityManager;  

	public Class<?> getSessionType() {  
		// 返回原始的UserManager类型  
		return CustomUserEntityManager.class;  
	}  

	public Session openSession() {  
		// 返回自定义的UserManager实例  
		return customUserEntityManager;  
	}
}
