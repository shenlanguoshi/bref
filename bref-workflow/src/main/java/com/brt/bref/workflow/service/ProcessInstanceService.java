package com.brt.bref.workflow.service;

import java.util.Map;

public interface ProcessInstanceService {
	public int startProcessInstance(String key, Map<String,Object> variabes, String formId, String formInfo);
}
