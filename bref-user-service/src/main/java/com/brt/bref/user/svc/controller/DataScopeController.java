package com.brt.bref.user.svc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.brt.bref.user.feign.entity.DataScopeEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.svc.service.DataScopeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author 方杰
 * @date 2018年11月30日
 * @description 数据权限-行Controller
 */
@RestController
public class DataScopeController {
	@Autowired
	private DataScopeService dataScopeService;

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param dataScopeInfo
	 * @return 是否成功
	 * @description 新增数据权限-行
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:insert')")
	@RequestMapping(value = "/dataScope", method = RequestMethod.POST)
	public JSONObject insert(String dataScopeInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(dataScopeInfo)) {
			return ResponseUtil.responseResult(false, "参数[dataScopeInfo]不能为空", null);
		}else {
			JSONObject dataScopeJson = JSON.parseObject(dataScopeInfo);

			Map<String,Object> map = new HashMap<>();

			// 验证corporationId是否合法
			if(dataScopeJson.containsKey("corporationId")){
				String corporationId = dataScopeJson.getString("corporationId");
				if(StringUtil.checkLength(corporationId,0,36)) {
					map.put("corporation_id", corporationId);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataScopeInfo.corporationId]不符合规则", null);
				}			
			}
			// 验证name是否合法
			String name = dataScopeJson.getString("name");
			if(StringUtil.checkLength(name, 1, 50)) {
				map.put("name", name);
			}else {
				return ResponseUtil.responseResult(false, "参数[dataScopeInfo.name]不符合规则", null);
			}
			// 验证entry是否合法
			String entry = dataScopeJson.getString("entry");
			if(StringUtil.checkLength(entry, 1, 50)) {
				map.put("entry", entry);
			}else {
				return ResponseUtil.responseResult(false, "参数[dataScopeInfo.entry]不符合规则", null);
			}
			// 验证priority是否合法
			if(dataScopeJson.containsKey("priority") && (dataScopeJson.get("priority") instanceof Integer)) {
				map.put("priority", dataScopeJson.getInteger("priority"));
			}else {
				return ResponseUtil.responseResult(false, "参数[dataScopeInfo.priority]不符合规则", null);
			}

			map.put("id", UUIDUtils.getUUID());

			if(dataScopeService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}

		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param id 数据权限-行id
	 * @return 是否成功
	 * @description 软删除数据权限-行
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:delete')")
	@RequestMapping(value = "/dataScope/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(dataScopeService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param dataScopeInfo 数据权限-行信息
	 * @param id 数据权限-行id
	 * @return 是否成功
	 * @description 修改数据权限-行
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:update')")
	@RequestMapping(value = "/dataScope/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("dataScopeInfo") String dataScopeInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(dataScopeInfo)) {
			return ResponseUtil.responseResult(false, "参数[dataScopeInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject dataScopeJson = JSON.parseObject(dataScopeInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证corporationId是否合法
			if(dataScopeJson.containsKey("corporationId")){
				String corporationId = dataScopeJson.getString("corporationId");
				if(StringUtil.checkLength(corporationId,0,36)) {
					map.put("corporation_id", corporationId);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataScopeInfo.corporationId]不符合规则", null);
				}			
			}
			// 验证name是否合法
			if(dataScopeJson.containsKey("name")) {
				String name = dataScopeJson.getString("name");
				if(StringUtil.checkLength(name, 1, 50)) {
					map.put("name", name);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataScopeInfo.name]不符合规则", null);
				}			
			}
			// 验证entry是否合法
			if(dataScopeJson.containsKey("entry")) {
				String entry = dataScopeJson.getString("entry");
				if(StringUtil.checkLength(entry, 1, 50)) {
					map.put("entry", entry);
				}else {
					return ResponseUtil.responseResult(false, "参数[dataScopeInfo.entry]不符合规则", null);
				}			
			}
			// 验证priority是否合法
			if(dataScopeJson.containsKey("priority")) {
				if(dataScopeJson.get("priority") instanceof Integer) {
					map.put("priority", dataScopeJson.getInteger("priority"));
				}else {
					return ResponseUtil.responseResult(false, "参数[dataScopeInfo.priority]不符合规则", null);
				}			
			}

			if(dataScopeService.update(map, id) > 0) {
				return ResponseUtil.responseResult(true, "修改成功", null);
			}else {
				return ResponseUtil.responseResult(false, "修改失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param searchParam 搜索参数
	 * @param pageParam 分页参数
	 * @return 数据权限-行集合
	 * @description 分页搜索数据权限-行
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:select')")
	@RequestMapping(value = "/dataScope/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject listDataScope(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
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
			// 验证name是否合法
			if(searchJson.containsKey("name")) {
				if(StringUtil.checkLength(searchJson.getString("name"), 0, 50)) {
					mapLike.put("name", searchJson.getString("name"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.name]不符合规则", null);
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

			// 删除标志验证，放入mapEqual参数
			if(searchJson.containsKey("del") && searchJson.getBooleanValue("del")) {
				mapEqual.put("del_flag", true);
			}
		}

		if(!mapEqual.containsKey("del_flag")) {
			mapEqual.put("del_flag", false);
		}

		Page<DataScopeEntity> dataScopePageList = dataScopeService.list(mapEqual, mapLike);
		if(dataScopePageList.isEmpty()) {
			return ResponseUtil.responseResult(false, "没有查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "获取数据权限-行成功", new PageInfo<>(dataScopePageList));
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param dataScopePermissionInfo 数据权限行-权限信息
	 * @return 是否成功
	 * @description 新增数据权限行-权限
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:update')")
	@RequestMapping(value = "/dataScope/permission", method = RequestMethod.POST)
	public JSONObject insertDataScopePermission(String dataScopePermissionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(dataScopePermissionInfo)) {
			return ResponseUtil.responseResult(false, "参数[dataScopePermissionInfo]不能为空", null);
		}else {
			JSONObject dataScopePermissionJson = JSON.parseObject(dataScopePermissionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证dataScopeId是否合法
			if(!dataScopePermissionJson.containsKey("dataScopeId") || StringUtils.isBlank(dataScopePermissionJson.getString("dataScopeId")) || !StringUtil.checkLength(dataScopePermissionJson.getString("dataScopeId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[dataScopePermissionInfo.dataScopeId]不符合规则", null);
			}else {
				map.put("data_scope_id", dataScopePermissionJson.getString("dataScopeId"));
			}

			// 验证permissionId是否合法
			if(!dataScopePermissionJson.containsKey("permissionId") || StringUtils.isBlank(dataScopePermissionJson.getString("permissionId")) || !StringUtil.checkLength(dataScopePermissionJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[dataScopePermissionInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", dataScopePermissionJson.getString("permissionId"));
			}

			if(dataScopeService.insertDataScopePermission(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param dataScopeId 数据权限-行id
	 * @param permissionId 权限id
	 * @return 是否成功
	 * @description 删除数据权限行-权限
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:update')")
	@RequestMapping(value = "/dataScope/{dataScopeId}/permission/{permissionId}", method = RequestMethod.DELETE)
	public JSONObject deleteDataScopePermission(@PathVariable("dataScopeId") String dataScopeId, @PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(dataScopeId) || !StringUtil.checkLength(dataScopeId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[dataScopeId]不符合规则", null);
		}else if (StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			if(dataScopeService.deleteDataScopePermission(dataScopeId, permissionId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月30日
	 * @param dataScopeId 数据权限-行 id
	 * @return 权限集合
	 * @description 查询数据权限行-权限
	 */
	//@PreAuthorize("@auth.hasPermission('dataScope:select')")
	@RequestMapping(value = "/dataScope/{dataScopeId}/permission", method = RequestMethod.GET)
	public JSONObject listDataScopePermission(@PathVariable("dataScopeId") String dataScopeId) {
		if(StringUtils.isBlank(dataScopeId) || !StringUtil.checkLength(dataScopeId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[dataScopeId]不符合规则", null);
		}else {
			List<PermissionEntity> permissionList = dataScopeService.listDataScopePermission(dataScopeId);
			if(permissionList == null || permissionList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", permissionList);
			}
		}
	}

	@GetMapping("/dataScope/Json")
	public JSONObject getDataScopeJson() {
		JSONObject dataScopeJson = dataScopeService.getDataScopeJson();
		if (dataScopeJson == null) {
			return ResponseUtil.responseResult(false, "获取dataScopeJson失败", null);
		} else {
			return ResponseUtil.responseResult(true, "获取dataScopeJson成功", dataScopeJson);
		}
	}
}
