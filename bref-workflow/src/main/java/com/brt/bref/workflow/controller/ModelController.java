package com.brt.bref.workflow.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author 方杰
 * @date 2018年12月8日
 * @description 流程模版Controller
 */
@RestController
public class ModelController {
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RepositoryService repositoryService;

	/**
	 * @author 方杰
	 * @date 2018年12月8日
	 * @param request 请求
	 * @param modelInfo 流程模型信息
	 * @return 编辑页地址
	 * @description 新增流程模型
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/model", method = RequestMethod.POST)
	public JSONObject insert(HttpServletRequest request, String modelInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(modelInfo)) {
			return ResponseUtil.responseResult(false, "参数[modelInfo]不能为空", null);
		}else {
			JSONObject modelJson = JSON.parseObject(modelInfo);

			Model modelData = repositoryService.newModel();
			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			// 验证name
			if(!modelJson.containsKey("name") && !StringUtil.checkLength(modelJson.getString("name"), 1, 255)){
				return ResponseUtil.responseResult(false, "参数[modelInfo.name]不符合规则", null);
			}else {
				String name = modelJson.getString("name");
				modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
				modelData.setName(name);
			}
			// 验证key
			if(!modelJson.containsKey("key") || StringUtil.checkLength(modelJson.getString("key"), 0, 255)) {
				modelData.setKey(StringUtils.defaultString(modelJson.getString("key")));
			}else {
				return ResponseUtil.responseResult(false, "参数[modelInfo.key]不符合规则", null);
			}
			// 验证category
			if(modelJson.containsKey("category")) {
				if(StringUtil.checkLength(modelJson.getString("category"), 0, 255)) {
					modelData.setCategory(modelJson.getString("category"));
				}else {
					return ResponseUtil.responseResult(false, "参数[modelInfo.category]不符合规则", null);
				}
			}

			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);

			String description = StringUtils.defaultString(modelJson.getString("description"));
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);

			modelData.setMetaInfo(modelObjectNode.toString());

			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);

			repositoryService.saveModel(modelData);
			try {
				repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				repositoryService.deleteModel(modelData.getId());
				return ResponseUtil.responseResult(false, "新增失败", null);
			}

			return ResponseUtil.responseResult(true, "新增成功", request.getContextPath() + "/explorer/modeler.html?modelId=" +
					modelData.getId());
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年12月8日
	 * @param id 流程模型id
	 * @return 
	 * @description 
	 */
	@RequestMapping(value = "/model/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		repositoryService.deleteModel(id);
		return ResponseUtil.responseResult(true, "删除成功", null);
	}

	/**
	 * @author 方杰
	 * @date 2018年12月8日
	 * @param searchParam 搜索参数
	 * @param pageParam 分页参数
	 * @return 流程模版集合
	 * @description 分页搜索查询流程模版
	 */
	@RequestMapping(value = "/model/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject list(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
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

		ModelQuery modelQuery = repositoryService.createModelQuery();
		if(StringUtils.isNotBlank(searchParam)) {
			JSONObject searchJson = JSON.parseObject(searchParam);
			// 验证name
			if(searchJson.containsKey("name")) {
				if(StringUtil.checkLength(searchJson.getString("name"), 0, 255)) {
					modelQuery = modelQuery.modelNameLike("%" + searchJson.getString("name") + "%");
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.name]不符合规则", null);
				}
			}
			// 验证category
			if(searchJson.containsKey("category")) {
				if(StringUtil.checkLength(searchJson.getString("category"), 0, 255)) {
					modelQuery = modelQuery.modelCategoryLike("%" + searchJson.getString("category") + "%");
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.category]不符合规则", null);
				}
			}
		}

		result.put("total", modelQuery.count());
		result.put("list", modelQuery.listPage(start, size));

		return ResponseUtil.responseResult(true, "查询成功", result);
	}

	/**
	 * @author 方杰
	 * @date 2018年12月8日
	 * @param response 请求响应
	 * @param id 流程模版id
	 * @throws IOException 输入输出异常
	 * @description 导出流程xml文件
	 */
	@RequestMapping(value = "/model/{id}/export", method = RequestMethod.GET)
	public void export(HttpServletResponse response, @PathVariable("id") String id) {
		try {
			Model modelData = repositoryService.getModel(id);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode;
			editorNode = objectMapper.readTree(repositoryService.getModelEditorSource(modelData.getId()));

			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
			IOUtils.copy(in, response.getOutputStream());
			String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
