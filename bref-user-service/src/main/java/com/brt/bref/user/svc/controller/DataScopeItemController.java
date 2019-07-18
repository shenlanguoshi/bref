package com.brt.bref.user.svc.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
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
import com.brt.bref.user.feign.entity.DataScopeItemEntity;
import com.brt.bref.user.svc.service.DataScopeItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author 蒋润
 * @date 2018年11月30日
 * @description 数据权限行itemController
 */
@RestController
public class DataScopeItemController {
	@Autowired
	private DataScopeItemService dataScopeItemService;

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param dataScopeItemInfo 数据权限行item信息
	 * @return 是否成功
	 * @description 新增数据权限行item
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:update')")
	@RequestMapping(value = "/dataScopeItem", method = RequestMethod.POST)
	public JSONObject insert(String dataScopeItemInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(dataScopeItemInfo)) {
			return ResponseUtil.responseResult(false, "参数[dataScopeItemInfo]不能为空", null);
		}else {
			JSONObject dataScopeItemJson = JSON.parseObject(dataScopeItemInfo);

			Map<String,Object> map = new HashMap<>();
			// 验证dataScopeId是否合法
			String dataScopeId = dataScopeItemJson.getString("dataScopeId");
			if(StringUtil.checkLength(dataScopeId, 1, 36)) {
				map.put("data_scope_id", dataScopeId);
			}else {
				return ResponseUtil.responseResult(false, "参数[dataScopeItemInfo.dataScopeId]不符合规则", null);
			}
			// 验证columnName是否合法
			String columnName = dataScopeItemJson.getString("columnName");
			if(StringUtil.checkLength(columnName, 1, 255)) {
				map.put("column_name", columnName);
			}else {
				return ResponseUtil.responseResult(false, "参数[dataScopeItemInfo.columnName]不符合规则", null);
			}
			// 验证operator是否合法
			String operator = dataScopeItemJson.getString("operator");
			if(StringUtil.checkLength(operator, 1, 20)) {
				map.put("operator", operator);
			}else {
				return ResponseUtil.responseResult(false, "参数[dataScopeItemInfo.operator]不符合规则", null);
			}
			// 验证dataScope是否合法
			map.put("data_scope", dataScopeItemJson.getString("dataScope"));

			map.put("id", UUIDUtils.getUUID());

			if(dataScopeItemService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}

		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param id dataScopeItemId
	 * @return 是否成功
	 * @description 软删除数据权限-行-字项目
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:update')")
	@RequestMapping(value = "/dataScopeItem/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(dataScopeItemService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}


	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param dataScopeItemInfo
	 * @param id
	 * @return 是否成功
	 * @description 修改DataScopeItem
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:update')")
	@RequestMapping(value = "/dataScopeItem/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("dataScopeItemInfo") String dataScopeItemInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(dataScopeItemInfo)) {
			return ResponseUtil.responseResult(false, "参数[dataScopeItemInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject dataScopeItemJson = JSON.parseObject(dataScopeItemInfo);

			Map<String, Object> map = new HashMap<>();
			// 验证dataScopeId是否合法
			if(dataScopeItemJson.containsKey("dataScopeId")) {
				String dataScopeId = dataScopeItemJson.getString("dataScopeId");
				if(StringUtil.checkLength(dataScopeId,1,36)) {
					map.put("data_scope_id", dataScopeId);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataScopeItemInfo.dataScopeId]不符合规则", null);
				}
			}
			// 验证columnName是否合法
			if(dataScopeItemJson.containsKey("columnName")) {
				String columnName = dataScopeItemJson.getString("columnName");
				if(StringUtil.checkLength(columnName,1,255)) {
					map.put("column_name", columnName);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataScopeItemInfo.columnName]不符合规则", null);
				}
			}
			// 验证operator是否合法
			if(dataScopeItemJson.containsKey("operator")) {
				String operator = dataScopeItemJson.getString("operator");
				if(StringUtil.checkLength(operator,1,20)) {
					map.put("operator", operator);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataScopeItemInfo.operator]不符合规则", null);
				}
			}
			// 验证dataScope是否合法
			if(dataScopeItemJson.containsKey("dataScope")) {
				map.put("data_scope", dataScopeItemJson.getString("dataScope"));
			}

			if(dataScopeItemService.update(map, id) > 0) {
				return ResponseUtil.responseResult(true, "修改成功", null);
			}else {
				return ResponseUtil.responseResult(false, "修改失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月28日
	 * @param searchParam 搜索参数
	 * @param pageParam 分页参数
	 * @return DataScopeItem集合
	 * @description 分页搜索DataScopeItem
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:select')")
	@RequestMapping(value = "/dataScopeItem/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject listDataScopeItem(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
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

			// 验证dataScopeId是否合法
			if(searchJson.containsKey("dataScopeId")) {
				if(StringUtil.checkLength(searchJson.getString("dataScopeId"),1,36)) {
					mapEqual.put("data_scope_id", searchJson.getString("dataScopeId"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.dataScopeId]不符合规则", null);
				}
			}
			// 验证columnName是否合法
			if(searchJson.containsKey("columnName")) {
				if(StringUtil.checkLength(searchJson.getString("columnName"),1,255)) {
					mapEqual.put("column_name", searchJson.getString("columnName"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.columnName]不符合规则", null);
				}
			}
			// 验证operator是否合法
			if(searchJson.containsKey("operator")) {
				if(StringUtil.checkLength(searchJson.getString("operator"),1,20)) {
					mapEqual.put("operator", searchJson.getString("operator"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.operator]不符合规则", null);
				}
			}
			// 验证dataScope是否合法
			if(searchJson.containsKey("dataScope")) {
				if(StringUtils.isNotBlank(searchJson.getString("dataScope"))) {
					mapLike.put("data_scope", searchJson.getString("dataScope"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.dataScope]不符合规则", null);
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

		Page<DataScopeItemEntity> dataScopeItemPageList = dataScopeItemService.list(mapEqual, mapLike);
		if(dataScopeItemPageList.isEmpty()) {
			return ResponseUtil.responseResult(false, "没有查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "获取成功", new PageInfo<>(dataScopeItemPageList));
		}
	}
}
