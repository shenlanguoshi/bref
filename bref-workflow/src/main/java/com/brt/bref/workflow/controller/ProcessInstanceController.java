package com.brt.bref.workflow.controller;

import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.common.util.ResponseUtil;
import com.brt.bref.common.util.StringUtil;
import com.brt.bref.workflow.entity.FormHeadEntity;
import com.brt.bref.workflow.service.FormHeadService;
import com.brt.bref.workflow.service.ProcessInstanceService;

@RestController
public class ProcessInstanceController {


	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private FormService formService;

	@Autowired
	private FormHeadService formHeadService;

	@Autowired
	private ProcessInstanceService processInstanceService;

	// 流程启动前检测
	@RequestMapping("/processInstance/beforeStart")
	public JSONObject test(String key) {
		if(!StringUtil.checkLength(key, 1, 255)) {
			return ResponseUtil.responseResult(false, "参数[key]不符合规则", null);
		}
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).latestVersion().singleResult();
		String startFormKey = formService.getStartFormKey(processDefinition.getId());
		if(StringUtils.isNotBlank(startFormKey)) {
			FormHeadEntity formHead = formHeadService.getById(startFormKey);
			if(formHead != null) {
				return ResponseUtil.responseResult(true, "需要填写表单才能启动流程", formHead);
			}else {
				return ResponseUtil.responseResult(false, "流程启动表单存在问题，请检查流程定义", formHead);
			}
		}else {
			return ResponseUtil.responseResult(true, "可直接启动流程", null);
		}
	}

	// 启动流程
	@SuppressWarnings("unchecked")
	@RequestMapping("/processInstance/start")
	public JSONObject startProcessInstance(String key, String taskVariabesInfo, String formId, String formInfo) {
		if(!StringUtil.checkLength(key, 1, 255)) {
			return ResponseUtil.responseResult(false, "参数[key]不符合规则", null);
		}
		
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).latestVersion().singleResult();
		String startFormKey = formService.getStartFormKey(processDefinition.getId());
		if(!formId.equals(startFormKey)) {
			return ResponseUtil.responseResult(false, "参数[formId]不符合规则", null);
		}
		
		Map<String, Object> variabes = JSONObject.parseObject(taskVariabesInfo, Map.class);
		if(processInstanceService.startProcessInstance(key, variabes, formId, formInfo) > 0) {
			return ResponseUtil.responseResult(true, "启动成功", null);
		}else {
			return ResponseUtil.responseResult(true, "启动失败", null);
		}
	}

}
