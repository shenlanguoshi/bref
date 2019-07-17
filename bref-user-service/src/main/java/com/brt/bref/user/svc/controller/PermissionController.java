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
import com.brt.bref.user.feign.entity.CorporationEntity;
import com.brt.bref.user.feign.entity.DataSchemaEntity;
import com.brt.bref.user.feign.entity.DataScopeEntity;
import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.ModuleEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.brt.bref.user.svc.service.PermissionService;
import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author 方杰
 * @date 2018年11月28日
 * @description 权限Controller
 */
@RestController
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	
	/**
	 * @author 方杰
	 * @date 2018年11月28日
	 * @param permissionInfo 权限信息
	 * @return 是否成功
	 * @description 新增权限
	 */
	//@PreAuthorize("@auth.hasPermission('permission:insert')")
	@RequestMapping(value = "/permission", method = RequestMethod.POST)
	public JSONObject insert(String permissionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(permissionInfo)) {
			return ResponseUtil.responseResult(false, "参数[permissionInfo]不能为空", null);
		}else {
			JSONObject permissionJson = JSON.parseObject(permissionInfo);

			Map<String,Object> map = new HashMap<>();
			// 验证name是否合法
			String name = permissionJson.getString("name");
			if(StringUtil.checkLength(name, 1, 50)) {
				map.put("name", name);
			}else {
				return ResponseUtil.responseResult(false, "参数[permissionInfo.name]不符合规则", null);
			}
			
			map.put("id", UUIDUtils.getUUID());
			
			if(permissionService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
			
		}
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月28日
	 * @param id 权限id
	 * @return 是否成功
	 * @description 软删除权限
	 */
	//@PreAuthorize("@auth.hasPermission('permission:delete')")
	@RequestMapping(value = "/permission/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(permissionService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月28日
	 * @param permissionInfo 权限信息
	 * @param id 权限id
	 * @return 是否成功
	 * @description 修改权限
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("permissionInfo") String permissionInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(permissionInfo)) {
			return ResponseUtil.responseResult(false, "参数[permissionInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject permissionJson = JSON.parseObject(permissionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证name是否合法
			if(permissionJson.containsKey("name")) {
				String name = permissionJson.getString("name");
				if(StringUtil.checkLength(name,1,50)) {
					map.put("name", name);
				}else {
					return ResponseUtil.responseResult(false, "参数[permissionInfo.name]不符合规则", null);
				}
			}
			
			if(permissionService.update(map, id) > 0) {
				return ResponseUtil.responseResult(true, "修改成功", null);
			}else {
				return ResponseUtil.responseResult(false, "修改失败", null);
			}

		}
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月28日
	 * @param searchParam 搜索参数
	 * @param pageParam 分页参数
	 * @return 权限集合
	 * @description 分页搜索权限
	 */
	//@PreAuthorize("@auth.hasPermission('permission:select')")
	@RequestMapping(value = "/permission/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject listPermission(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
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

			// name验证，放入mapLike参数
			if(searchJson.containsKey("name")) {
				if(StringUtil.checkLength(searchJson.getString("name").trim(), 0, 50)) {
					mapLike.put("name", searchJson.getString("name").trim());
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

		Page<PermissionEntity> permissionPageList = permissionService.list(mapEqual, mapLike);
		if(permissionPageList.isEmpty()) {
			return ResponseUtil.responseResult(false, "没有查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "获取权限成功", new PageInfo<>(permissionPageList));
		}		
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param permissionModuleInfo 权限-模块信息
	 * @return 是否成功
	 * @description 新增权限-模块
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/module", method = RequestMethod.POST)
	public JSONObject insertPermissionModule(String permissionModuleInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(permissionModuleInfo)) {
			return ResponseUtil.responseResult(false, "参数[permissionModuleInfo]不能为空", null);
		}else {
			JSONObject permissionModuleJson = JSON.parseObject(permissionModuleInfo);

			Map<String, Object> map = new HashMap<>();
			
			// 验证permissionId是否合法
			if(!permissionModuleJson.containsKey("permissionId") || StringUtils.isBlank(permissionModuleJson.getString("permissionId")) || !StringUtil.checkLength(permissionModuleJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[modulePermissionInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", permissionModuleJson.getString("permissionId"));
			}

			// 验证moduleId是否合法
			if(!permissionModuleJson.containsKey("moduleId") || StringUtils.isBlank(permissionModuleJson.getString("moduleId")) || !StringUtil.checkLength(permissionModuleJson.getString("moduleId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[modulePermissionInfo.moduleId]不符合规则", null);
			}else {
				map.put("module_id", permissionModuleJson.getString("moduleId"));
			}

			if(permissionService.insertPermissionModule(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param permissionId 权限id
	 * @param moduleId 模块id
	 * @return 是否成功
	 * @description 删除权限-模块
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/{permissionId}/module/{moduleId}", method = RequestMethod.DELETE)
	public JSONObject deletePermissionModule(@PathVariable("permissionId") String permissionId, @PathVariable("moduleId") String moduleId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else if (StringUtils.isBlank(moduleId) || !StringUtil.checkLength(moduleId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[moduleId]不符合规则", null);
		}else {
			if(permissionService.deletePermissionModule(permissionId, moduleId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param permissionId 权限id
	 * @return 模块集合
	 * @description 查询权限-模块
	 */
	//@PreAuthorize("@auth.hasPermission('permission:select')")
	@RequestMapping(value = "/permission/{permissionId}/module", method = RequestMethod.GET)
	public JSONObject listPermissionModule(@PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			List<ModuleEntity> moduleList = permissionService.listPermissionModule(permissionId);
			if(moduleList == null || moduleList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", moduleList);
			}
		}
	}	

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param permissionDataSchemaInfo 权限-数据权限列信息
	 * @return 是否成功
	 * @description 新增权限-数据权限列
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/dataSchema", method = RequestMethod.POST)
	public JSONObject insertPermissionDataSchema(String permissionDataSchemaInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(permissionDataSchemaInfo)) {
			return ResponseUtil.responseResult(false, "参数[permissionDataSchemaInfo]不能为空", null);
		}else {
			JSONObject permissionDataSchemaJson = JSON.parseObject(permissionDataSchemaInfo);

			Map<String, Object> map = new HashMap<>();
			
			// 验证permissionId是否合法
			if(!permissionDataSchemaJson.containsKey("permissionId") || StringUtils.isBlank(permissionDataSchemaJson.getString("permissionId")) || !StringUtil.checkLength(permissionDataSchemaJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionDataSchemaInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", permissionDataSchemaJson.getString("permissionId"));
			}

			// 验证dataSchemaId是否合法
			if(!permissionDataSchemaJson.containsKey("dataSchemaId") || StringUtils.isBlank(permissionDataSchemaJson.getString("dataSchemaId")) || !StringUtil.checkLength(permissionDataSchemaJson.getString("dataSchemaId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionDataSchemaInfo.dataSchemaId]不符合规则", null);
			}else {
				map.put("data_schema_id", permissionDataSchemaJson.getString("dataSchemaId"));
			}

			if(permissionService.insertPermissionDataSchema(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param permissionId 权限id
	 * @param dataSchemaId 数据权限-列 id
	 * @return 是否成功
	 * @description 删除权限-数据权限列
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/{permissionId}/dataSchema/{dataSchemaId}", method = RequestMethod.DELETE)
	public JSONObject deletePermissionDataSchema(@PathVariable("permissionId") String permissionId, @PathVariable("dataSchemaId") String dataSchemaId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else if (StringUtils.isBlank(dataSchemaId) || !StringUtil.checkLength(dataSchemaId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[dataSchemaId]不符合规则", null);
		}else {
			if(permissionService.deletePermissionDataSchema(permissionId, dataSchemaId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param permissionId 权限id
	 * @return 数据权限-列集合
	 * @description 获取权限-数据权限列
	 */
	//@PreAuthorize("@auth.hasPermission('permission:select')")
	@RequestMapping(value = "/permission/{permissionId}/dataSchema", method = RequestMethod.GET)
	public JSONObject listPermissionDataSchema(@PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			List<DataSchemaEntity> dataSchemaList = permissionService.listPermissionDataSchema(permissionId);
			if(dataSchemaList == null || dataSchemaList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", dataSchemaList);
			}
		}
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param permissionDataScopeInfo 权限-数据权限行信息
	 * @return 是否成功
	 * @description 新增权限-数据权限行
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/dataScope", method = RequestMethod.POST)
	public JSONObject insertPermissionDataScope(String permissionDataScopeInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(permissionDataScopeInfo)) {
			return ResponseUtil.responseResult(false, "参数[permissionDataScopeInfo]不能为空", null);
		}else {
			JSONObject permissionDataScopeJson = JSON.parseObject(permissionDataScopeInfo);

			Map<String, Object> map = new HashMap<>();
			
			// 验证permissionId是否合法
			if(!permissionDataScopeJson.containsKey("permissionId") || StringUtils.isBlank(permissionDataScopeJson.getString("permissionId")) || !StringUtil.checkLength(permissionDataScopeJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionDataScopeInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", permissionDataScopeJson.getString("permissionId"));
			}

			// 验证dataScopeId是否合法
			if(!permissionDataScopeJson.containsKey("dataScopeId") || StringUtils.isBlank(permissionDataScopeJson.getString("dataScopeId")) || !StringUtil.checkLength(permissionDataScopeJson.getString("dataScopeId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionDataScopeInfo.dataScopeId]不符合规则", null);
			}else {
				map.put("data_scope_id", permissionDataScopeJson.getString("dataScopeId"));
			}

			if(permissionService.insertPermissionDataScope(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param permissionId 权限id
	 * @param dataSchemaId 数据权限-行 id
	 * @return 是否成功
	 * @description 删除权限-数据权限行
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/{permissionId}/dataScope/{dataScopeId}", method = RequestMethod.DELETE)
	public JSONObject deletePermissionDataScope(@PathVariable("permissionId") String permissionId, @PathVariable("dataScopeId") String dataScopeId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else if (StringUtils.isBlank(dataScopeId) || !StringUtil.checkLength(dataScopeId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[dataScopeId]不符合规则", null);
		}else {
			if(permissionService.deletePermissionDataScope(permissionId, dataScopeId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param permissionId 权限id
	 * @return 数据权限-行集合
	 * @description 获取权限-数据权限行
	 */
	//@PreAuthorize("@auth.hasPermission('permission:select')")
	@RequestMapping(value = "/permission/{permissionId}/dataScope", method = RequestMethod.GET)
	public JSONObject listPermissionDataScope(@PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			List<DataScopeEntity> dataScopeList = permissionService.listPermissionDataScope(permissionId);
			if(dataScopeList == null || dataScopeList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", dataScopeList);
			}
		}
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param permissionUserInfo 权限用户信息
	 * @return 是否成功
	 * @description 新增权限用户
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/user", method = RequestMethod.POST)
	public JSONObject insertPermissionUser(String permissionUserInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(permissionUserInfo)) {
			return ResponseUtil.responseResult(false, "参数[permissionUserInfo]不能为空", null);
		}else {
			JSONObject permissionUserJson = JSON.parseObject(permissionUserInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证permissionId是否合法
			if(!permissionUserJson.containsKey("permissionId") || StringUtils.isBlank(permissionUserJson.getString("permissionId")) || !StringUtil.checkLength(permissionUserJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionUserInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", permissionUserJson.getString("permissionId"));
			}

			// 验证userId是否合法
			if(!permissionUserJson.containsKey("userId") || StringUtils.isBlank(permissionUserJson.getString("userId")) || !StringUtil.checkLength(permissionUserJson.getString("userId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionUserInfo.userId]不符合规则", null);
			}else {
				map.put("user_id", permissionUserJson.getString("userId"));
			}

			if(permissionService.insertPermissionUser(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param permissionId 权限id
	 * @param userId 用户id
	 * @return 是否成功
	 * @description 删除权限用户
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/{permissionId}/user/{userId}", method = RequestMethod.DELETE)
	public JSONObject deletePermissionUser(@PathVariable("permissionId") String permissionId, @PathVariable("userId") String userId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else if (StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else {
			if(permissionService.deletePermissionUser(permissionId, userId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月26日
	 * @param permissionId
	 * @return 权限用户列表
	 * @description 查询某个权限的用户
	 */
	//@PreAuthorize("@auth.hasPermission('permission:select')")
	@RequestMapping(value = "/permission/{permissionId}/user", method = RequestMethod.GET)
	public JSONObject listPermissionUser(@PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			List<UserEntity> userList = permissionService.listPermissionUser(permissionId);
			if(userList == null || userList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", userList);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月22日
	 * @param permissionCorporationInfo 权限组织机构信息
	 * @return 是否成功
	 * @description 新增权限组织机构
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/corporation", method = RequestMethod.POST)
	public JSONObject insertPermissionCorporation(String permissionCorporationInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(permissionCorporationInfo)) {
			return ResponseUtil.responseResult(false, "参数[permissionCorporationInfo]不能为空", null);
		}else {
			JSONObject permissionCorporationJson = JSON.parseObject(permissionCorporationInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证permissionId是否合法
			if(!permissionCorporationJson.containsKey("permissionId") || StringUtils.isBlank(permissionCorporationJson.getString("permissionId")) || !StringUtil.checkLength(permissionCorporationJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionCorporationInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", permissionCorporationJson.getString("permissionId"));
			}

			// 验证corporationId是否合法
			if(!permissionCorporationJson.containsKey("corporationId") || StringUtils.isBlank(permissionCorporationJson.getString("corporationId")) || !StringUtil.checkLength(permissionCorporationJson.getString("corporationId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionCorporationInfo.corporationId]不符合规则", null);
			}else {
				map.put("corporation_id", permissionCorporationJson.getString("corporationId"));
			}

			if(permissionService.insertPermissionCorporation(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param permissionId 权限id
	 * @param corporationId 组织机构id
	 * @return 是否成功
	 * @description 删除权限组织机构
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/{permissionId}/corporation/{corporationId}", method = RequestMethod.DELETE)
	public JSONObject deletePermissionCorporation(@PathVariable("permissionId") String permissionId, @PathVariable("corporationId") String corporationId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else if (StringUtils.isBlank(corporationId) || !StringUtil.checkLength(corporationId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationId]不符合规则", null);
		}else {
			if(permissionService.deletePermissionCorporation(permissionId, corporationId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月22日
	 * @param permissionId 权限id
	 * @return 权限组织机构列表
	 * @description 查询某个权限的组织机构
	 */
	//@PreAuthorize("@auth.hasPermission('permission:select')")
	@RequestMapping(value = "/permission/{permissionId}/corporation", method = RequestMethod.GET)
	public JSONObject listPermissionCorporation(@PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			List<CorporationEntity> corporationList = permissionService.listPermissionCorporation(permissionId);
			if(corporationList == null || corporationList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", corporationList);
			}
		}	
	}

	/**
	 * @author 方杰
	 * @date 2018年11月22日
	 * @param permissionCorporationWorkInfo 权限岗位信息
	 * @return 是否成功
	 * @description 新增权限岗位
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/corporationWork", method = RequestMethod.POST)
	public JSONObject insertPermissionCorporationWork(String permissionCorporationWorkInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(permissionCorporationWorkInfo)) {
			return ResponseUtil.responseResult(false, "参数[permissionCorporationWorkInfo]不能为空", null);
		}else {
			JSONObject permissionCorporationWorkJson = JSON.parseObject(permissionCorporationWorkInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证permissionId是否合法
			if(!permissionCorporationWorkJson.containsKey("permissionId") || StringUtils.isBlank(permissionCorporationWorkJson.getString("permissionId")) || !StringUtil.checkLength(permissionCorporationWorkJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionCorporationWorkInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", permissionCorporationWorkJson.getString("permissionId"));
			}

			// 验证corporationWorkId是否合法
			if(!permissionCorporationWorkJson.containsKey("corporationWorkId") || StringUtils.isBlank(permissionCorporationWorkJson.getString("corporationWorkId")) || !StringUtil.checkLength(permissionCorporationWorkJson.getString("corporationWorkId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionCorporationInfo.corporationWorkId]不符合规则", null);
			}else {
				map.put("corporation_work_id", permissionCorporationWorkJson.getString("corporationWorkId"));
			}

			if(permissionService.insertPermissionCorporationWork(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param permissionId 权限id
	 * @param corporationWorkId 岗位id
	 * @return 是否成功
	 * @description 删除权限岗位
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/{permissionId}/corporationWork/{corporationWorkId}", method = RequestMethod.DELETE)
	public JSONObject deletePermissionCorporationWork(@PathVariable("permissionId") String permissionId, @PathVariable("corporationWorkId") String corporationWorkId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else if (StringUtils.isBlank(corporationWorkId) || !StringUtil.checkLength(corporationWorkId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationWorkId]不符合规则", null);
		}else {
			if(permissionService.deletePermissionCorporationWork(permissionId, corporationWorkId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月26日
	 * @param permissionId 权限id
	 * @return 权限岗位列表
	 * @description 查询某个权限的岗位
	 */
	//@PreAuthorize("@auth.hasPermission('permission:select')")
	@RequestMapping(value = "/permission/{permissionId}/corporationWork", method = RequestMethod.GET)
	public JSONObject listPermissionCorporationWork(@PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			List<CorporationWorkEntity> corporationWorkList = permissionService.listPermissionCorporationWork(permissionId);
			if(corporationWorkList == null || corporationWorkList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", corporationWorkList);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param permissionJobPositionInfo 权限职务信息
	 * @return 是否成功
	 * @description 新增权限职务
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/jobPosition", method = RequestMethod.POST)
	public JSONObject insertPermissionJobPosition(String permissionJobPositionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(permissionJobPositionInfo)) {
			return ResponseUtil.responseResult(false, "参数[permissionJobPositionInfo]不能为空", null);
		}else {
			JSONObject permissionJobPositionJson = JSON.parseObject(permissionJobPositionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证permissionId是否合法
			if(!permissionJobPositionJson.containsKey("permissionId") || StringUtils.isBlank(permissionJobPositionJson.getString("permissionId")) || !StringUtil.checkLength(permissionJobPositionJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionJobPositionInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", permissionJobPositionJson.getString("permissionId"));
			}

			// 验证jobPositionId是否合法
			if(!permissionJobPositionJson.containsKey("jobPositionId") || StringUtils.isBlank(permissionJobPositionJson.getString("jobPositionId")) || !StringUtil.checkLength(permissionJobPositionJson.getString("jobPositionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[permissionJobPositionInfo.jobPositionId]不符合规则", null);
			}else {
				map.put("job_position_id", permissionJobPositionJson.getString("jobPositionId"));
			}

			if(permissionService.insertPermissionJobPosition(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param permissionId 权限id
	 * @param jobPositionId 职务id
	 * @return 是否成功
	 * @description 删除权限职务
	 */
	//@PreAuthorize("@auth.hasPermission('permission:update')")
	@RequestMapping(value = "/permission/{permissionId}/jobPosition/{jobPositionId}", method = RequestMethod.DELETE)
	public JSONObject deletePermissionJobPosition(@PathVariable("permissionId") String permissionId, @PathVariable("jobPositionId") String jobPositionId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else if (StringUtils.isBlank(jobPositionId) || !StringUtil.checkLength(jobPositionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[jobPositionId]不符合规则", null);
		}else {
			if(permissionService.deletePermissionJobPosition(permissionId, jobPositionId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月26日
	 * @param permissionId
	 * @return 权限职务列表
	 * @description 查询某个权限的职务
	 */
	//@PreAuthorize("@auth.hasPermission('permission:select')")
	@RequestMapping(value = "/permission/{permissionId}/jobPosition", method = RequestMethod.GET)
	public JSONObject listPermissionJobPosition(@PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			List<JobPositionEntity> jobPositionList = permissionService.listPermissionJobPosition(permissionId);
			if(jobPositionList == null || jobPositionList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", jobPositionList);
			}
		}
	}
}
