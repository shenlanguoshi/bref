package com.brt.bref.user.svc.controller;

import java.util.HashMap;
import java.util.List;
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
import com.brt.bref.user.feign.entity.DataSchemaEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.svc.service.DataSchemaService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author 蒋润
 * @date 2018年11月30日
 * @description 数据权限-列Controller
 */
@RestController
public class DataSchemaController {
	@Autowired
	private DataSchemaService dataSchemaService;

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param dataSchemaInfo
	 * @return 是否成功
	 * @description 新增数据权限-列
	 */
	//@PreAuthorize("@auth.hasPermission('dataSchema:insert')")
	@RequestMapping(value = "/dataSchema", method = RequestMethod.POST)
	public JSONObject insert(String dataSchemaInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(dataSchemaInfo)) {
			return ResponseUtil.responseResult(false, "参数[dataSchemaInfo]不能为空", null);
		}else {
			JSONObject dataSchemaJson = JSON.parseObject(dataSchemaInfo);

			Map<String,Object> map = new HashMap<>();

			// 验证corporationId是否合法
			if(dataSchemaJson.containsKey("corporationId")){
				String corporationId = dataSchemaJson.getString("corporationId");
				if(StringUtil.checkLength(corporationId,0,36)) {
					map.put("corporation_id", corporationId);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataSchemaInfo.corporationId]不符合规则", null);
				}			
			}
			// 验证name是否合法
			String name = dataSchemaJson.getString("name");
			if(StringUtil.checkLength(name, 1, 50)) {
				map.put("name", name);
			}else {
				return ResponseUtil.responseResult(false, "参数[dataSchemaInfo.name]不符合规则", null);
			}
			// 验证entry是否合法
			String entry = dataSchemaJson.getString("entry");
			if(StringUtil.checkLength(entry, 1, 50)) {
				map.put("entry", entry);
			}else {
				return ResponseUtil.responseResult(false, "参数[dataSchemaInfo.entry]不符合规则", null);
			}
			// 验证priority是否合法
			if(dataSchemaJson.containsKey("priority") && (dataSchemaJson.get("priority") instanceof Integer)) {
				map.put("priority", dataSchemaJson.getInteger("priority"));
			}else {
				return ResponseUtil.responseResult(false, "参数[dataSchemaInfo.priority]不符合规则", null);
			}
			// 验证dataSchema是否合法
			if(dataSchemaJson.containsKey("dataSchema")) {
				if(StringUtil.checkDataSchema(dataSchemaJson.getString("dataSchema"))) {
					map.put("data_schema", dataSchemaJson.getString("dataSchema"));
				}else {
					return ResponseUtil.responseResult(false, "参数[dataSchemaInfo.dataSchema]不符合规则", null);
				}			
			}

			map.put("id", UUIDUtils.getUUID());

			if(dataSchemaService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}

		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param id 数据权限-列id
	 * @return 是否成功
	 * @description 软删除数据权限-列
	 */
	//@PreAuthorize("@auth.hasPermission('dataSchema:delete')")
	@RequestMapping(value = "/dataSchema/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(dataSchemaService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param dataSchemaInfo 数据权限-列信息
	 * @param id 数据权限-列id
	 * @return 是否成功
	 * @description 修改数据权限-列
	 */
	//@PreAuthorize("@auth.hasPermission('dataSchema:update')")
	@RequestMapping(value = "/dataSchema/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("dataSchemaInfo") String dataSchemaInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(dataSchemaInfo)) {
			return ResponseUtil.responseResult(false, "参数[dataSchemaInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject dataSchemaJson = JSON.parseObject(dataSchemaInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证corporationId是否合法
			if(dataSchemaJson.containsKey("corporationId")){
				String corporationId = dataSchemaJson.getString("corporationId");
				if(StringUtil.checkLength(corporationId,0,36)) {
					map.put("corporation_id", corporationId);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataSchemaInfo.corporationId]不符合规则", null);
				}			
			}
			// 验证name是否合法
			if(dataSchemaJson.containsKey("name")) {
				String name = dataSchemaJson.getString("name");
				if(StringUtil.checkLength(name, 1, 50)) {
					map.put("name", name);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataSchemaInfo.name]不符合规则", null);
				}			
			}
			// 验证entry是否合法
			if(dataSchemaJson.containsKey("entry")) {
				String entry = dataSchemaJson.getString("entry");
				if(StringUtil.checkLength(entry, 1, 50)) {
					map.put("entry", entry);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataSchemaInfo.entry]不符合规则", null);
				}			
			}
			// 验证priority是否合法
			if(dataSchemaJson.containsKey("priority")) {
				if(dataSchemaJson.get("priority") instanceof Integer) {
					map.put("priority", dataSchemaJson.getInteger("priority"));
				}else {
					return ResponseUtil.responseResult(false, "参数[dataSchemaInfo.priority]不符合规则", null);
				}			
			}
			// 验证dataSchema是否合法
			if(dataSchemaJson.containsKey("dataSchema")) {
				if(StringUtil.checkDataSchema(dataSchemaJson.getString("dataSchema"))) {
					map.put("dataSchema", dataSchemaJson.getString("dataSchema"));
				}else {
					return ResponseUtil.responseResult(false, "参数[dataSchemaInfo.dataSchema]不符合规则", null);
				}			
			}

			if(dataSchemaService.update(map, id) > 0) {
				return ResponseUtil.responseResult(true, "修改成功", null);
			}else {
				return ResponseUtil.responseResult(false, "修改失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param searchParam 搜索参数
	 * @param pageParam 分页参数
	 * @return 数据权限-列集合
	 * @description 分页搜索数据权限-列
	 */
	//@PreAuthorize("@auth.hasPermission('dataSchema:select')")
	@RequestMapping(value = "/dataSchema/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject listDataSchema(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
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

			// 验证corporationId是否合法
			if(searchJson.containsKey("corporationId")){
				String corporationId = searchJson.getString("corporationId");
				if(StringUtil.checkLength(corporationId,0,36)) {
					mapEqual.put("corporation_id", corporationId);
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.corporationId]不符合规则", null);
				}			
			}
			// 验证entry是否合法
			if(searchJson.containsKey("entry")) {
				if(StringUtil.checkLength(searchJson.getString("entry"), 0, 50)) {
					mapLike.put("entry", searchJson.getString("entry"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.entry]不符合规则", null);
				}			
			}
			// 验证priority是否合法
			if(searchJson.containsKey("priority")) {
				if(searchJson.get("priority") instanceof Integer) {
					mapEqual.put("priority", searchJson.getInteger("priority"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.priority]不符合规则", null);
				}			
			}
			// 验证dataSchema是否合法
			if(searchJson.containsKey("dataSchema")) {
				if(StringUtil.checkDataSchema(searchJson.getString("dataSchema"))) {
					mapLike.put("dataSchema", searchJson.getString("dataSchema"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.dataSchema]不符合规则", null);
				}			
			}
			// 验证name是否合法
			if(searchJson.containsKey("name")) {
				if(StringUtil.checkLength(searchJson.getString("name"), 0, 50)) {
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

		Page<DataSchemaEntity> dataSchemaPageList = dataSchemaService.list(mapEqual, mapLike);
		if(dataSchemaPageList.isEmpty()) {
			return ResponseUtil.responseResult(false, "没有查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "获取数据权限-列成功", new PageInfo<>(dataSchemaPageList));
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param dataSchemaPermissionInfo 数据权限列-权限信息
	 * @return 是否成功
	 * @description 新增数据权限列-权限
	 */
	//@PreAuthorize("@auth.hasPermission('dataSchema:update')")
	@RequestMapping(value = "/dataSchema/permission", method = RequestMethod.POST)
	public JSONObject insertDataSchemaPermission(String dataSchemaPermissionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(dataSchemaPermissionInfo)) {
			return ResponseUtil.responseResult(false, "参数[dataSchemaPermissionInfo]不能为空", null);
		}else {
			JSONObject dataSchemaPermissionJson = JSON.parseObject(dataSchemaPermissionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证dataSchemaId是否合法
			if(!dataSchemaPermissionJson.containsKey("dataSchemaId") || StringUtils.isBlank(dataSchemaPermissionJson.getString("dataSchemaId")) || !StringUtil.checkLength(dataSchemaPermissionJson.getString("dataSchemaId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[dataSchemaPermissionInfo.dataSchemaId]不符合规则", null);
			}else {
				map.put("data_schema_id", dataSchemaPermissionJson.getString("dataSchemaId"));
			}

			// 验证permissionId是否合法
			if(!dataSchemaPermissionJson.containsKey("permissionId") || StringUtils.isBlank(dataSchemaPermissionJson.getString("permissionId")) || !StringUtil.checkLength(dataSchemaPermissionJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[dataSchemaPermissionInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", dataSchemaPermissionJson.getString("permissionId"));
			}

			if(dataSchemaService.insertDataSchemaPermission(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param dataSchemaId 数据权限-列id
	 * @param permissionId 权限id
	 * @return 是否成功
	 * @description 删除数据权限列-权限
	 */
	//@PreAuthorize("@auth.hasPermission('dataSchema:update')")
	@RequestMapping(value = "/dataSchema/{dataSchemaId}/permission/{permissionId}", method = RequestMethod.DELETE)
	public JSONObject deleteDataSchemaPermission(@PathVariable("dataSchemaId") String dataSchemaId, @PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(dataSchemaId) || !StringUtil.checkLength(dataSchemaId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[dataSchemaId]不符合规则", null);
		}else if (StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			if(dataSchemaService.deleteDataSchemaPermission(dataSchemaId, permissionId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param dataSchemaId 数据权限-列 id
	 * @return 权限集合
	 * @description 查询数据权限列-权限
	 */
	//@PreAuthorize("@auth.hasPermission('dataSchema:select')")
	@RequestMapping(value = "/dataSchema/{dataSchemaId}/permission", method = RequestMethod.GET)
	public JSONObject listDataSchemaPermission(@PathVariable("dataSchemaId") String dataSchemaId) {
		if(StringUtils.isBlank(dataSchemaId) || !StringUtil.checkLength(dataSchemaId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[dataSchemaId]不符合规则", null);
		}else {
			List<PermissionEntity> permissionList = dataSchemaService.listDataSchemaPermission(dataSchemaId);
			if(permissionList == null || permissionList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", permissionList);
			}
		}
	}
}
