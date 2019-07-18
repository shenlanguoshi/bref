package com.brt.bref.workflow.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.brt.bref.common.util.ResponseUtil;
import com.brt.bref.common.util.StringUtil;
import com.brt.bref.common.util.UUIDUtils;
import com.brt.bref.workflow.entity.FormDataEntity;
import com.brt.bref.workflow.service.FormDataService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author 蒋润
 * @date 2018年12月24日
 * @description 表单数据控制层
 */
@RestController
public class FormDataController {
	@Autowired
	private FormDataService formDataService;

	/**
	 * @author 蒋润
	 * @date 2018年12月24日
	 * @param formDataInfo
	 * @return 是否成功
	 * @description 新增表单数据
	 */
	@RequestMapping(value = "/formData", method = RequestMethod.POST)
	public JSONObject insert(String formDataInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(formDataInfo)) {
			return ResponseUtil.responseResult(false, "参数[formDataInfo]不能为空", null);
		}else {
			JSONObject formDataJson = JSON.parseObject(formDataInfo);

			Map<String,Object> map = new HashMap<>();
			// 验证formId是否合法
			if(StringUtil.checkLength(formDataJson.getString("formId"), 1, 36)) {
				map.put("form_id", formDataJson.getString("formId"));
			}else {
				return ResponseUtil.responseResult(false, "参数[formDataInfo.formId]不符合规则", null);
			}
			// 验证表单数据json
			if(formDataJson.containsKey("json")) {
				map.put("json", formDataJson.getString("json"));
			}
			// 验证流程实例ID procinstId
			if(formDataJson.containsKey("procinstId")) {
				if(StringUtil.checkLength(formDataJson.getString("procinstId"), 0, 36)) {
					map.put("procinst_id", formDataJson.getString("procinstId"));
				}else {
					return ResponseUtil.responseResult(false, "参数[formDataInfo.procinstId]不符合规则", null);
				}
			}
			// 验证任务ID taskId
			if(formDataJson.containsKey("taskId")) {
				if(StringUtil.checkLength(formDataJson.getString("taskId"), 0, 36)) {
					map.put("task_id", formDataJson.getString("taskId"));
				}else {
					return ResponseUtil.responseResult(false, "参数[formDataInfo.taskId]不符合规则", null);
				}
			}
			// 还需要获取username参数

			map.put("id", UUIDUtils.getUUID());

			if(formDataService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}

		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年12月24日
	 * @param id
	 * @return 是否成功
	 * @description 软删除表单
	 */
	@RequestMapping(value = "/formData/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(formDataService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年12月24日
	 * @param id
	 * @return 表单数据信息
	 * @description 根据id查询表单数据
	 */
	@RequestMapping(value = "/formData/{id}", method = RequestMethod.GET)
	public JSONObject getFormDataById(@PathVariable("id") String id) {
		if(StringUtil.checkLength(id, 1, 36)) {
			FormDataEntity formDataEntity = formDataService.getById(id);
			if(formDataEntity == null) {
				return ResponseUtil.responseResult(false, "不存在该表单", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", formDataEntity);
			}
		}else {
			return ResponseUtil.responseResult(true, "参数[id]不符合规则", null);
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年12月24日
	 * @param formDataInfo
	 * @param id
	 * @return 是否成功
	 * @description 更新表单数据
	 */
	@RequestMapping(value = "/formData/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("formDataInfo") String formDataInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(formDataInfo)) {
			return ResponseUtil.responseResult(false, "参数[formDataInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject formDataJson = JSON.parseObject(formDataInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证表单数据json
			if(formDataJson.containsKey("json")) {
				map.put("json", formDataJson.getString("json"));
			}

			if(formDataService.update(map, id) > 0) {
				return ResponseUtil.responseResult(true, "修改成功", null);
			}else {
				return ResponseUtil.responseResult(false, "修改失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年12月24日
	 * @param searchParam
	 * @param pageParam
	 * @return 表单数据集合
	 * @description 分页搜索表单数据
	 */
	@RequestMapping(value = "/formData/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject listFormData(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
		// 验证pageParam参数是否合法
		if(StringUtils.isNotBlank(pageParam)) {
			JSONObject pageJson = JSON.parseObject(pageParam);
			if(!pageJson.containsKey("pageNo") || pageJson.getInteger("pageNo") < 1) {
				return ResponseUtil.responseResult(false, "参数[pageParam.pageNo]不符合规则", null);
			}
			if(!pageJson.containsKey("pageSize") || pageJson.getInteger("pageSize") < 1) {
				return ResponseUtil.responseResult(false, "参数[pageParam.pageSize]不符合规则", null);
			}
			// 分页
			PageHelper.startPage(pageJson.getInteger("pageNo"),pageJson.getInteger("pageSize"));
		}

		Map<String,Object> mapEqual = new HashMap<>();
		Map<String,Object> mapLike = new HashMap<>();
		// 验证searchParam参数是否合法
		if(StringUtils.isNotBlank(searchParam)) {
			JSONObject searchJson = JSON.parseObject(searchParam);

			// 验证formId是否合法
			if(searchJson.containsKey("json")) {
				if(StringUtil.checkLength(searchJson.getString("formId"), 0, 36)) {
					mapEqual.put("form_id", searchJson.getString("formId"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.formId]不符合规则", null);
				}
			}
			// 验证表单数据json
			if(searchJson.containsKey("json")) {
				mapLike.put("json", searchJson.getString("json"));
			}
			// 验证流程实例ID procinstId
			if(searchJson.containsKey("procinstId")) {
				if(StringUtil.checkLength(searchJson.getString("procinstId"), 0, 36)) {
					mapEqual.put("procinst_id", searchJson.getString("procinstId"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.procinstId]不符合规则", null);
				}
			}
			// 验证任务ID taskId
			if(searchJson.containsKey("taskId")) {
				if(StringUtil.checkLength(searchJson.getString("taskId"), 0, 36)) {
					mapEqual.put("task_id", searchJson.getString("taskId"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.taskId]不符合规则", null);
				}
			}
			// 验证创建人 createUsername
			if(searchJson.containsKey("createUsername")) {
				if(StringUtil.checkLength(searchJson.getString("createUsername"), 0, 50)) {
					mapLike.put("create_username", searchJson.getString("createUsername"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.createUsername]不符合规则", null);
				}
			}
			// 验证最后更新人 updateUsername
			if(searchJson.containsKey("updateUsername")) {
				if(StringUtil.checkLength(searchJson.getString("updateUsername"), 0, 50)) {
					mapLike.put("update_username", searchJson.getString("updateUsername"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.updateUsername]不符合规则", null);
				}
			}

			// 删除标志验证，放入mapEqual参数
			if(searchJson.containsKey("del") && searchJson.getBooleanValue("del")) {
				mapEqual.put("del_flag", true);
			}
		}

		if(!mapEqual.containsKey("del_flag")) {
			mapEqual.put("del_flag", false);
		}

		Page<FormDataEntity> formDataPageList = formDataService.list(mapEqual, mapLike);
		if(formDataPageList.isEmpty()) {
			return ResponseUtil.responseResult(false, "没有查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "获取表单成功", new PageInfo<>(formDataPageList));
		}
	}
}
