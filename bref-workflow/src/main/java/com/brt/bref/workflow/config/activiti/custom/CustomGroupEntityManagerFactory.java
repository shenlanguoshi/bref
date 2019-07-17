package com.brt.bref.workflow.config.activiti.custom;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomGroupEntityManagerFactory implements SessionFactory {
	
	@Autowired
	private CustomGroupEntityManager customGroupEntityManager;

	public Class<?> getSessionType() {  
		// 返回原始的GroupEntityManager类型  
		return CustomGroupEntityManager.class;  
	}  

	@Override
	public Session openSession() {
		return customGroupEntityManager;
	}
}
