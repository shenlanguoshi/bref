package com.brt.bref.workflow.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brt.bref.common.util.UUIDUtils;
import com.brt.bref.workflow.service.FormDataService;
import com.brt.bref.workflow.service.ProcessInstanceService;

@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService{
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private FormDataService formDataService;

	@Override
	@Transactional
	public int startProcessInstance(String key, Map<String, Object> variabes, String formId, String formInfo) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, variabes);
		if(StringUtils.isNotBlank(formId.trim())) {
			Map<String,Object> formMap = new HashMap<>();
			formMap.put("form_id", formId);
			formMap.put("json", formInfo);
			formMap.put("procinst_id", processInstance.getId());
			formMap.put("id", UUIDUtils.getUUID());
			return formDataService.insert(formMap);
		}
		return 1;
	}


}
