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
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.brt.bref.user.svc.service.CorporationService;

/**
 * @author 方杰
 * @date 2018年11月21日
 * @description 组织机构Controller
 */
@RestController
public class CorporationController {
	@Autowired
	CorporationService corporationService;

	/**
	 * @author 方杰
	 * @date 2018年11月26日
	 * @param corporationInfo 组织机构信息
	 * @return 是否成功
	 * @description 新增组织机构
	 */
	//@PreAuthorize("@auth.hasPermission('corporation:insert')")
	@RequestMapping(value = "/corporation", method = RequestMethod.POST)
	public JSONObject insert(String corporationInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(corporationInfo)) {
			return ResponseUtil.responseResult(false, "参数[corporationInfo]不能为空", null);
		}else {
			JSONObject corporationJson = JSON.parseObject(corporationInfo);

			Map<String,Object> map = new HashMap<>();

			// 验证supervisorId是否合法
			if(corporationJson.containsKey("supervisorId")){
				String supervisorId = corporationJson.getString("supervisorId");
				if(StringUtil.checkLength(supervisorId,1,36)) {
					map.put("supervisor_id", supervisorId);
				}else {
					return ResponseUtil.responseResult(false, "参数[corporationInfo.supervisorId]不符合规则", null);
				}			
			}

			// 验证name是否合法
			String name = corporationJson.getString("name");
			if(StringUtil.checkLength(name,1,50)){
				map.put("name", name);
			}else {
				return ResponseUtil.responseResult(false, "参数[corporationInfo.name]不符合规则", null);
			}

			// 验证parentId是否合法
			if(corporationJson.containsKey("parentId")){
				String parentId = corporationJson.getString("parentId");
				if(StringUtil.checkLength(parentId,0,36)) {
					map.put("parent_id", parentId);
				}else {
					return ResponseUtil.responseResult(false, "参数[corporationInfo.parentId]不符合规则", null);
				}			
			}else {
				map.put("parent_id", "");
			}

			String id = UUIDUtils.getUUID();
			map.put("id", id);

			if(corporationService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}

		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月26日
	 * @param id 组织机构id
	 * @return 是否成功
	 * @description 软删除组织机构
	 */
	//@PreAuthorize("@auth.hasPermission('corporation:delete')")
	@RequestMapping(value = "/corporation/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(corporationService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param corporationInfo 需要修改的组织机构信息
	 * @param id 组织机构id
	 * @return 是否成功
	 * @description 修改组织机构信息
	 */
	//@PreAuthorize("@auth.hasPermission('corporation:update')")
	@RequestMapping(value = "/corporation/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("corporationInfo") String corporationInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(corporationInfo)) {
			return ResponseUtil.responseResult(false, "参数[corporationInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject corporationJson = JSON.parseObject(corporationInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证supervisorId是否合法
			if(corporationJson.containsKey("supervisorId")) {
				if(StringUtil.checkLength(corporationJson.getString("supervisorId"),0,36)) {
					map.put("supervisor_id", corporationJson.getString("supervisorId"));
				}else {
					return ResponseUtil.responseResult(false, "参数[corporationInfo.supervisorId]不符合规则", null);
				}
			}

			// 验证name是否合法
			if(corporationJson.containsKey("name")) {
				if(StringUtil.checkLength(corporationJson.getString("name"),0,50)) {
					map.put("name", corporationJson.getString("name"));
				}else {
					return ResponseUtil.responseResult(false, "参数[corporationInfo.name]不符合规则", null);
				}
			}

			// 验证parentId
			if(corporationJson.containsKey("parentId")) {
				String parentId = corporationJson.getString("parentId");
				if(StringUtil.checkLength(parentId, 0, 36)) {
					map.put("parent_id",parentId);
				}else {
					return ResponseUtil.responseResult(false, "参数[corporationInfo.parentId]不符合规则", null);
				}
			}

			if(corporationService.update(map, id) > 0) {
				return ResponseUtil.responseResult(true, "修改成功", null);
			}else {
				return ResponseUtil.responseResult(false, "修改失败", null);
			}

		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月26日
	 * @return 组织机构树
	 * @description 查询组织机构树
	 */
	//@PreAuthorize("@auth.hasPermission('corporation:select')")
	@RequestMapping(value = "/corporation", method = RequestMethod.GET)
	public JSONObject tree() {
		JSONObject result = corporationService.getDataFromRedis();
		if(result == null || result.isEmpty()) {
			return ResponseUtil.responseResult(false, "未查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "查询成功", result.get("tree"));
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param corporationUserInfo 组织机构用户信息
	 * @return 是否成功
	 * @description 新增组织机构用户
	 */
	//@PreAuthorize("@auth.hasPermission('corporation:update')")
	@RequestMapping(value = "/corporation/user", method = RequestMethod.POST)
	public JSONObject insertCorporationUser(String corporationUserInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(corporationUserInfo)) {
			return ResponseUtil.responseResult(false, "参数[corporationUserInfo]不能为空", null);
		}else {
			JSONObject corporationUserJson = JSON.parseObject(corporationUserInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证corporationId是否合法
			if(!corporationUserJson.containsKey("corporationId") || StringUtils.isBlank(corporationUserJson.getString("corporationId")) || !StringUtil.checkLength(corporationUserJson.getString("corporationId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[corporationUserInfo.corporationId]不符合规则", null);
			}else {
				map.put("corporation_id", corporationUserJson.getString("corporationId"));
			}

			// 验证userId是否合法
			if(!corporationUserJson.containsKey("userId") || StringUtils.isBlank(corporationUserJson.getString("userId")) || !StringUtil.checkLength(corporationUserJson.getString("userId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[corporationUserInfo.userId]不符合规则", null);
			}else {
				map.put("user_id", corporationUserJson.getString("userId"));
			}

			if(corporationService.insertCorporationUser(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param corporationId 组织机构id
	 * @param userId 用户id
	 * @return 是否成功
	 * @description 删除组织机构用户
	 */
	//@PreAuthorize("@auth.hasPermission('corporation:update')")
	@RequestMapping(value = "/corporation/{corporationId}/user/{userId}", method = RequestMethod.DELETE)
	public JSONObject deleteCorporationUser(@PathVariable("corporationId") String corporationId, @PathVariable("userId") String userId) {
		if(StringUtils.isBlank(corporationId) || !StringUtil.checkLength(corporationId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationId]不符合规则", null);
		}else if (StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else {
			if(corporationService.deleteCorporationUser(corporationId, userId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param corporationId 组织机构id
	 * @return 用户集合
	 * @description 查询某个组织机构的所有用户
	 */
	//@PreAuthorize("@auth.hasPermission('corporation:select')")
	@RequestMapping(value = "/corporation/{corporationId}/user", method = RequestMethod.GET)
	public JSONObject listCorporationUser(@PathVariable("corporationId") String corporationId) {
		if(StringUtils.isBlank(corporationId) || !StringUtil.checkLength(corporationId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationId]不符合规则", null);
		}else {
			List<UserEntity> userList = corporationService.listCorporationUser(corporationId);
			if(userList == null || userList.isEmpty()) {
				return ResponseUtil.responseResult(false, "未查询到数据", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", userList);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param corporationPermissionInfo 组织机构权限信息
	 * @return 是否成功
	 * @description 新增组织机构权限
	 */
	//@PreAuthorize("@auth.hasPermission('corporation:update')")
	@RequestMapping(value = "/corporation/permission", method = RequestMethod.POST)
	public JSONObject insertCorporationPermission(String corporationPermissionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(corporationPermissionInfo)) {
			return ResponseUtil.responseResult(false, "参数[corporationPermissionInfo]不能为空", null);
		}else {
			JSONObject corporationPermissionJson = JSON.parseObject(corporationPermissionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证corporationId是否合法
			if(!corporationPermissionJson.containsKey("corporationId") || StringUtils.isBlank(corporationPermissionJson.getString("corporationId")) || !StringUtil.checkLength(corporationPermissionJson.getString("corporationId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[corporationPermissionInfo.corporationId]不符合规则", null);
			}else {
				map.put("corporation_id", corporationPermissionJson.getString("corporationId"));
			}

			// 验证permissionId是否合法
			if(!corporationPermissionJson.containsKey("permissionId") || StringUtils.isBlank(corporationPermissionJson.getString("permissionId")) || !StringUtil.checkLength(corporationPermissionJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[corporationPermissionInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", corporationPermissionJson.getString("permissionId"));
			}

			if(corporationService.insertCorporationPermission(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param corporationId 组织机构id
	 * @param permissionId 权限id
	 * @return 是否成功
	 * @description 删除组织机构权限
	 */
	//@PreAuthorize("@auth.hasPermission('corporation:update')")
	@RequestMapping(value = "/corporation/{corporationId}/permission/{permissionId}", method = RequestMethod.DELETE)
	public JSONObject deleteCorporationPermission(@PathVariable("corporationId") String corporationId, @PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(corporationId) || !StringUtil.checkLength(corporationId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationId]不符合规则", null);
		}else if (StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else {
			if(corporationService.deleteCorporationPermission(corporationId, permissionId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param corporationId 组织机构id
	 * @return 权限集合
	 * @description 获取某个组织机构的权限信息
	 */
	//@PreAuthorize("@auth.hasPermission('corporation:select')")
	@RequestMapping(value = "/corporation/{corporationId}/permission", method = RequestMethod.GET)
	public JSONObject listCorporationPermission(@PathVariable("corporationId") String corporationId) {
		if(StringUtils.isBlank(corporationId) || !StringUtil.checkLength(corporationId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationId]不符合规则", null);
		}else {
			List<PermissionEntity> permissionList = corporationService.listCorporationPermission(corporationId);
			if(permissionList == null || permissionList.isEmpty()) {
				return ResponseUtil.responseResult(false, "未查询到数据", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", permissionList);
			}
		}		
	}

}
