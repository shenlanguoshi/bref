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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.brt.bref.common.util.ResponseUtil;
import com.brt.bref.common.util.StringUtil;
import com.brt.bref.common.util.UUIDUtils;
import com.brt.bref.user.feign.entity.ModuleEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.svc.service.ModuleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author 蒋润
 * @date 2018年11月27日
 * @description 模块Controller
 */
@RestController
public class ModuleController {
	@Autowired
	private ModuleService moduleService;

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param moduleInfo 模块信息
	 * @return 是否成功
	 * @description 新增模块
	 */
	//@PreAuthorize("@auth.hasPermission('module:insert')")
	@RequestMapping(value = "/module", method = RequestMethod.POST)
	public JSONObject insert(String moduleInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(moduleInfo)) {
			return ResponseUtil.responseResult(false, "参数[moduleInfo]不能为空", null);
		}else {
			JSONObject moduleJson = JSON.parseObject(moduleInfo);

			Map<String,Object> map = new HashMap<>();
			// 验证title是否合法
			if(moduleJson.containsKey("title")) {
				String title = moduleJson.getString("title");
				if(StringUtil.checkLength(title, 0, 50)) {
					map.put("title", title);
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.title]不符合规则", null);
				}
			}
			// 验证parentId是否合法
			if(moduleJson.containsKey("parentId")) {
				String parentId = moduleJson.getString("parentId");
				if(StringUtil.checkLength(parentId, 0, 36)) {
					map.put("parent_id", parentId);
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.parentId]不符合规则", null);
				}	
			}
			// 验证uri是否合法
			if(moduleJson.containsKey("uri")) {
				String uri = moduleJson.getString("uri");
				if(StringUtil.checkLength(uri, 0, 255)) {
					map.put("uri", uri);
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.uri]不符合规则", null);
				}	
			}
			// 验证icon是否合法
			if(moduleJson.containsKey("icon")) {
				String icon = moduleJson.getString("icon");
				if(!StringUtil.checkLength(icon, 0, 255)) {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.icon]不符合规则", null);
				}else {
					map.put("icon", icon);
				}	
			}
			// 验证sort是否合法
			if(moduleJson.containsKey("sort")) {
				if(moduleJson.get("sort") instanceof Integer) {
					map.put("sort", moduleJson.getInteger("sort"));
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.sort]不符合规则", null);
				}		
			}
			// 验证menu是否合法
			if(moduleJson.containsKey("menu") && (moduleJson.get("menu") instanceof Boolean)) {
				map.put("is_menu", moduleJson.getBoolean("menu"));
			}else {
				return ResponseUtil.responseResult(false, "参数[moduleInfo.menu]不符合规则", null);
			}
			// 验证controls是否合法
			if(moduleJson.containsKey("controls")) {
				String controls = moduleJson.getString("controls");
				if(StringUtil.checkLength(controls, 0, 255)) {
					map.put("controls", controls);
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.controls]不符合规则", null);
				}		
			}

			map.put("id", UUIDUtils.getUUID());

			if(moduleService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}

		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param id 模块id
	 * @return 是否成功
	 * @description 软删除模块
	 */
	//@PreAuthorize("@auth.hasPermission('module:delete')")
	@RequestMapping(value = "/module/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(moduleService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param moduleInfo 模块信息
	 * @param id 模块id
	 * @return 是否成功
	 * @description 修改模块
	 */
	//@PreAuthorize("@auth.hasPermission('module:update')")
	@RequestMapping(value = "/module/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("moduleInfo") String moduleInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(moduleInfo)) {
			return ResponseUtil.responseResult(false, "参数[moduleInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject moduleJson = JSON.parseObject(moduleInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证title是否合法
			if(moduleJson.containsKey("title")) {
				String title = moduleJson.getString("title");
				if(StringUtil.checkLength(title,0,20)) {
					map.put("title", title);
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.title]不符合规则", null);
				}
			}
			// 验证parentId是否合法
			if(moduleJson.containsKey("parentId")) {
				String parentId = moduleJson.getString("parentId");
				if(StringUtil.checkLength(parentId,0,36)) {
					map.put("parent_id", parentId);
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.parentId]不符合规则", null);
				}
			}
			// 验证uri是否合法
			if(moduleJson.containsKey("uri")) {
				String uri = moduleJson.getString("uri");
				if(StringUtil.checkLength(uri,0,255)) {
					map.put("uri", uri);
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.uri]不符合规则", null);
				}
			}
			// 验证icon是否合法
			if(moduleJson.containsKey("icon")) {
				String icon = moduleJson.getString("icon");
				if(StringUtil.checkLength(icon,0,255)) {
					map.put("icon", icon);
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.icon]不符合规则", null);
				}
			}
			// 验证sort是否合法
			if(moduleJson.containsKey("sort")) {
				if(moduleJson.get("sort") instanceof Integer) {
					map.put("sort", moduleJson.getInteger("sort"));
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.sort]不符合规则", null);
				}
			}
			// 验证menu是否合法
			if(moduleJson.containsKey("menu")) {
				if(moduleJson.get("menu") instanceof Boolean) {
					map.put("is_menu", moduleJson.getBoolean("menu"));
				}else {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.menu]不符合规则", null);
				}			
			}
			// 验证controls是否合法
			if(moduleJson.containsKey("controls")) {
				String controls = moduleJson.getString("controls");
				if(!StringUtil.checkLength(controls, 0, 255)) {
					return ResponseUtil.responseResult(false, "参数[moduleInfo.controls]不符合规则", null);
				}else {
					map.put("controls", controls);
				}		
			}

			if(moduleService.update(map, id) > 0) {
				return ResponseUtil.responseResult(true, "修改成功", null);
			}else {
				return ResponseUtil.responseResult(false, "修改失败", null);
			}

		}
	}


	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @return 组织机构树
	 * @description 查询组织机构树
	 */
	//@PreAuthorize("@auth.hasPermission('module:select')")
	@RequestMapping(value = "/module", method = RequestMethod.GET)
	public JSONObject tree() {
		Map<String,Object> mapEqual = new HashMap<>();
		mapEqual.put("del_flag", false);
		JSONArray moduleTree = moduleService.getTree(mapEqual);
		if(moduleTree.isEmpty()) {
			return ResponseUtil.responseResult(false, "未查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "查询成功", moduleTree);
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月28日
	 * @param searchParam 搜索参数
	 * @param pageParam 分页参数
	 * @return 模块集合
	 * @description 分页搜索模块
	 */
	//@PreAuthorize("@auth.hasPermission('module:select')")
	@RequestMapping(value = "/module/{pageParam}/{searchParam}", method = RequestMethod.GET)
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

			// title验证，放入mapLike参数
			if(searchJson.containsKey("title")) {
				if(StringUtil.checkLength(searchJson.getString("title").trim(), 0, 20)) {
					mapLike.put("title", searchJson.getString("title").trim());
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.title]不符合规则", null);
				}		
			}

			// parentId验证，放入mapEqual参数
			if(searchJson.containsKey("parentId")) {
				if(StringUtil.checkLength(searchJson.getString("parentId").trim(), 1, 36)) {
					mapEqual.put("parent_id", searchJson.getString("parentId").trim());
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.parentId]不符合规则", null);
				}		
			}

			// uri验证，放入mapLike参数
			if(searchJson.containsKey("uri")) {
				if(StringUtil.checkLength(searchJson.getString("uri").trim(), 0, 255)) {
					mapLike.put("uri", searchJson.getString("uri").trim());
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.uri]不符合规则", null);
				}		
			}

			// icon验证，放入mapLike参数
			if(searchJson.containsKey("icon")) {
				if(StringUtil.checkLength(searchJson.getString("icon").trim(), 0, 255)) {
					mapLike.put("icon", searchJson.getString("icon").trim());
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.icon]不符合规则", null);
				}		
			}

			// parentId验证，放入mapEqual参数
			if(searchJson.containsKey("menu")) {
				if(searchJson.get("menu") instanceof Boolean) {
					mapEqual.put("is_menu", searchJson.getBoolean("menu"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.menu]不符合规则", null);
				}		
			}

			// controls验证，放入mapLike参数
			if(searchJson.containsKey("controls")) {
				if(!StringUtil.checkLength(searchJson.getString("controls"), 0, 255)) {
					return ResponseUtil.responseResult(false, "参数[searchParam.controls]不符合规则", null);
				}else {
					mapLike.put("controls", searchJson.getString("controls"));
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

		Page<ModuleEntity> modulePageList = moduleService.list(mapEqual, mapLike);
		if(modulePageList.isEmpty()) {
			return ResponseUtil.responseResult(false, "没有查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "获取功能权限成功", new PageInfo<>(modulePageList));
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param modulePermissionInfo 模块权限信息
	 * @return 是否成功
	 * @description 插入模块权限
	 */
	//@PreAuthorize("@auth.hasPermission('module:update')")
	@RequestMapping(value = "/module/permission", method = RequestMethod.POST)
	public JSONObject insertModulePermission(String modulePermissionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(modulePermissionInfo)) {
			return ResponseUtil.responseResult(false, "参数[modulePermissionInfo]不能为空", null);
		}else {
			JSONObject modulePermissionJson = JSON.parseObject(modulePermissionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证moduleId是否合法
			if(!modulePermissionJson.containsKey("moduleId") || StringUtils.isBlank(modulePermissionJson.getString("moduleId")) || !StringUtil.checkLength(modulePermissionJson.getString("moduleId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[modulePermissionInfo.moduleId]不符合规则", null);
			}else {
				map.put("module_id", modulePermissionJson.getString("moduleId"));
			}

			// 验证permissionId是否合法
			if(!modulePermissionJson.containsKey("permissionId") || StringUtils.isBlank(modulePermissionJson.getString("permissionId")) || !StringUtil.checkLength(modulePermissionJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[modulePermissionInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", modulePermissionJson.getString("permissionId"));
			}

			if(moduleService.insertModulePermission(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param moduleId 模块id
	 * @param permissionId 权限id
	 * @return 是否成功
	 * @description 删除模块权限
	 */
	//@PreAuthorize("@auth.hasPermission('module:update')")
	@RequestMapping(value = "/module/{moduleId}/permission/{permissionId}", method = RequestMethod.DELETE)
	public JSONObject deleteModulePermission(@PathVariable("moduleId") String moduleId, @PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(moduleId) || !StringUtil.checkLength(moduleId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[moduleId]不符合规则", null);
		}else if (StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			if(moduleService.deleteModulePermission(moduleId, permissionId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月30日
	 * @param moduleId 模块id
	 * @return 权限集合
	 * @description 查询模块权限
	 */
	//@PreAuthorize("@auth.hasPermission('module:select')")
	@RequestMapping(value = "/module/{moduleId}/permission", method = RequestMethod.GET)
	public JSONObject listModulePermission(@PathVariable("moduleId") String moduleId) {
		if(StringUtils.isBlank(moduleId) || !StringUtil.checkLength(moduleId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[moduleId]不符合规则", null);
		}else {
			List<PermissionEntity> permissionList = moduleService.listModulePermission(moduleId);
			if(permissionList == null || permissionList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", permissionList);
			}
		}
	}

}
