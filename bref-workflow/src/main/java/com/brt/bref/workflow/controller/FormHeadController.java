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
import com.brt.bref.workflow.entity.FormHeadEntity;
import com.brt.bref.workflow.service.FormHeadService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author 方杰
 * @date 2018年12月24日
 * @description 表单控制层
 */
@RestController
public class FormHeadController {
	@Autowired
	private FormHeadService formHeadService;

	/**
	 * @author 方杰
	 * @date 2018年12月24日
	 * @param formHeadInfo
	 * @return 是否成功
	 * @description 新增表单
	 */
	@RequestMapping(value = "/formHead", method = RequestMethod.POST)
	public JSONObject insert(String formHeadInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(formHeadInfo)) {
			return ResponseUtil.responseResult(false, "参数[formHeadInfo]不能为空", null);
		}else {
			JSONObject formHeadJson = JSON.parseObject(formHeadInfo);

			Map<String,Object> map = new HashMap<>();
			// 验证name是否合法
			if(StringUtil.checkLength(formHeadJson.getString("name"), 1, 50)) {
				map.put("name", formHeadJson.getString("name"));
			}else {
				return ResponseUtil.responseResult(false, "参数[formHeadInfo.name]不符合规则", null);
			}
			// 验证表单json
			if(formHeadJson.containsKey("json")) {
				map.put("json", formHeadJson.getString("json"));
			}

			map.put("id", UUIDUtils.getUUID());

			if(formHeadService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}

		}
	}

	/**
	 * @author 方杰
	 * @date 2018年12月24日
	 * @param id
	 * @return 是否成功
	 * @description 软删除表单
	 */
	@RequestMapping(value = "/formHead/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(formHeadService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年12月24日
	 * @param id
	 * @return 表单信息
	 * @description 根据id查询表单
	 */
	@RequestMapping(value = "/formHead/{id}", method = RequestMethod.GET)
	public JSONObject getFormHeadById(@PathVariable("id") String id) {
		if(StringUtil.checkLength(id, 1, 36)) {
			FormHeadEntity formHeadEntity = formHeadService.getById(id);
			if(formHeadEntity == null) {
				return ResponseUtil.responseResult(false, "不存在该表单", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", formHeadEntity);
			}
		}else {
			return ResponseUtil.responseResult(true, "参数[id]不符合规则", null);
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年12月24日
	 * @param formHeadInfo
	 * @param id
	 * @return 是否成功
	 * @description 更新表单
	 */
	@RequestMapping(value = "/formHead/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("formHeadInfo") String formHeadInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(formHeadInfo)) {
			return ResponseUtil.responseResult(false, "参数[formHeadInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject formHeadJson = JSON.parseObject(formHeadInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证name是否合法
			if(formHeadJson.containsKey("name")) {
				if(StringUtil.checkLength(formHeadJson.getString("name"), 0, 20)) {
					map.put("name", formHeadJson.getString("name"));
				}else {
					return ResponseUtil.responseResult(false, "参数[formHeadInfo.name]不符合规则", null);
				}			
			}
			// 验证表单json
			if(formHeadJson.containsKey("json")) {
				map.put("json", formHeadJson.getString("json"));
			}

			if(formHeadService.update(map, id) > 0) {
				return ResponseUtil.responseResult(true, "修改成功", null);
			}else {
				return ResponseUtil.responseResult(false, "修改失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年12月24日
	 * @param searchParam
	 * @param pageParam
	 * @return 表单集合
	 * @description 分页搜索表单
	 */
	@RequestMapping(value = "/formHead/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject listFormHead(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
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

			// 验证name是否合法
			if(searchJson.containsKey("name")) {
				if(StringUtil.checkLength(searchJson.getString("name"), 0, 20)) {
					mapLike.put("name", searchJson.getString("name"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.name]不符合规则", null);
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

		Page<FormHeadEntity> formHeadPageList = formHeadService.list(mapEqual, mapLike);
		if(formHeadPageList.isEmpty()) {
			return ResponseUtil.responseResult(false, "没有查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "获取表单成功", new PageInfo<>(formHeadPageList));
		}
	}
}
