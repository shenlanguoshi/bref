package com.brt.bref.workflow.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.brt.bref.common.util.ResponseUtil;
import com.brt.bref.common.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author 蒋润
 * @date 2018年12月8日
 * @description 部署Controller
 */
@RestController
public class DeploymentController {
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RepositoryService repositoryService;

	/**
	 * @author 蒋润
	 * @date 2018年12月8日
	 * @param request
	 * @param deploymentInfo 部署信息
	 * @return 是否部署成功
	 * @description 部署流程模版
	 */
	@RequestMapping(value = "/deployment", method = RequestMethod.POST)
	public JSONObject insert(HttpServletRequest request, String deploymentInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(deploymentInfo)) {
			return ResponseUtil.responseResult(false, "参数[deploymentInfo]不能为空", null);
		}else {
			JSONObject deploymentJson = JSON.parseObject(deploymentInfo);
			// 验证流程模版id
			String modelId = deploymentJson.getString("modelId");
			if(!StringUtil.checkLength(modelId, 1, 255)) {
				return ResponseUtil.responseResult(false, "参数[deploymentInfo.modelId]不符合规则", null);
			}

			try {
				Model modelData = repositoryService.getModel(modelId);
				ObjectNode modelNode = (ObjectNode)objectMapper.readTree(repositoryService.getModelEditorSource(modelData.getId()));
				byte[] bpmnBytes = null;

				BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
				bpmnBytes = new BpmnXMLConverter().convertToXML(model);

				String processName = modelData.getName() + ".bpmn20.xml";
				Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
						.addString(processName, new String(bpmnBytes)).deploy();
				modelData.setDeploymentId(deployment.getId());
				repositoryService.saveModel(modelData);
				return ResponseUtil.responseResult(true, "部署成功，部署ID=" + deployment.getId(), null);
			} catch (Exception e) {
				return ResponseUtil.responseResult(false, "部署失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年12月8日
	 * @param id 部署实例id
	 * @param type true:将给定的部署和级联删除删除到流程实例、历史流程实例和作业 false:删除给定的部署
	 * @return 是否成功
	 * @description 删除部署实例
	 */
	@RequestMapping(value = "/deployment/{id}/{type}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id, @PathVariable("type") boolean type) {
		repositoryService.deleteDeployment(id, type);
		return ResponseUtil.responseResult(true, "删除成功", null);
	}

	/**
	 * @author 蒋润
	 * @date 2018年12月8日
	 * @param searchParam 搜索参数
	 * @param pageParam 分页参数
	 * @return 部署实例集合
	 * @description 查询已部署的实例
	 */
	@RequestMapping(value = "/deployment/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject listDeployment(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
		int start = 0;
		int size = 20;
		JSONObject result = new JSONObject();
		// 验证pageParam参数是否合法
		if(StringUtils.isNotBlank(pageParam)) {
			JSONObject pageJson = JSON.parseObject(pageParam);
			if(!pageJson.containsKey("pageNo") || pageJson.getInteger("pageNo") < 1) {
				return ResponseUtil.responseResult(false, "参数[pageParam.pageNo]不符合规则", null);
			}
			if(!pageJson.containsKey("pageSize") || pageJson.getInteger("pageSize") < 1) {
				return ResponseUtil.responseResult(false, "参数[pageParam.pageSize]不符合规则", null);
			}
			size = pageJson.getInteger("pageSize");
			start = (pageJson.getInteger("pageNo") - 1) * size;
			result.put("pageNo", pageJson.getInteger("pageNo"));
			result.put("pageSize", size);
		}else {
			result.put("pageNo", 1);
			result.put("pageSize", size);
		}
		
		DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
		if(StringUtils.isNotBlank(searchParam)) {
			JSONObject searchJson = JSON.parseObject(searchParam);
			// 验证name
			if(searchJson.containsKey("name")) {
				if(StringUtil.checkLength(searchJson.getString("name"), 0, 255)) {
					deploymentQuery = deploymentQuery.deploymentNameLike("%" + searchJson.getString("name") + "%");
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.name]不符合规则", null);
				}
			}
		}
		
		result.put("total", deploymentQuery.count());
		List<Deployment> deployments = deploymentQuery.listPage(start, size);

		List<JSONObject> list = new ArrayList<>();
		for (Deployment deployment : deployments) {
			JSONObject item = new JSONObject();
			item.put("id", deployment.getId());
			item.put("name", deployment.getName());
			item.put("category", deployment.getCategory());
			item.put("deploymentTime", deployment.getDeploymentTime());
			list.add(item);
		}
		result.put("list", list);
		
		return ResponseUtil.responseResult(true, "查询成功", result);		
	}
}
