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
import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.brt.bref.user.svc.service.CorporationWorkService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author 蒋润
 * @date 2018年11月27日
 * @description 岗位Controller
 */
@RestController
public class CorporationWorkController {
	@Autowired
	private CorporationWorkService corporationWorkService;

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param corporationWorkInfo 岗位信息
	 * @return 是否成功
	 * @description 新增岗位
	 */
	//@PreAuthorize("@auth.hasPermission('corporationWork:insert')")
	@RequestMapping(value = "/corporationWork", method = RequestMethod.POST)
	public JSONObject insert(String corporationWorkInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(corporationWorkInfo)) {
			return ResponseUtil.responseResult(false, "参数[corporationWorkInfo]不能为空", null);
		}else {
			JSONObject corporationWorkJson = JSON.parseObject(corporationWorkInfo);

			Map<String,Object> map = new HashMap<>();

			// 验证corporationId是否合法
			String corporationId = corporationWorkJson.getString("corporationId");
			if(StringUtil.checkLength(corporationId, 1, 36)) {
				map.put("corporation_id", corporationId);
			}else {
				return ResponseUtil.responseResult(false, "参数[corporationWorkInfo.corporationId]不符合规则", null);
			}

			// 验证name是否合法
			String name = corporationWorkJson.getString("name");
			if(StringUtil.checkLength(name, 1, 50)) {
				map.put("name", name);
			}else {
				return ResponseUtil.responseResult(false, "参数[corporationWorkInfo.name]不符合规则", null);
			}

			map.put("id", UUIDUtils.getUUID());

			if(corporationWorkService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}

		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param id 岗位id
	 * @return 是否成功
	 * @description 软删除岗位
	 */
	//@PreAuthorize("@auth.hasPermission('corporationWork:delete')")
	@RequestMapping(value = "/corporationWork/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(corporationWorkService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param corporationWorkInfo 岗位信息
	 * @param id 岗位id
	 * @return 是否成功
	 * @description 修改岗位信息
	 */
	//@PreAuthorize("@auth.hasPermission('corporationWork:update')")
	@RequestMapping(value = "/corporationWork/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("corporationWorkInfo") String corporationWorkInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(corporationWorkInfo)) {
			return ResponseUtil.responseResult(false, "参数[corporationWorkInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject corporationWorkJson = JSON.parseObject(corporationWorkInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证corporationId是否合法
			if(corporationWorkJson.containsKey("corporationId")) {
				if(StringUtil.checkLength(corporationWorkJson.getString("corporationId"),1,36)) {
					map.put("corporation_id", corporationWorkJson.getString("corporationId"));
				}else {
					return ResponseUtil.responseResult(false, "参数[corporationWorkInfo.corporationId]不符合规则", null);
				}
			}

			// 验证name是否合法
			if(corporationWorkJson.containsKey("name")) {
				if(StringUtil.checkLength(corporationWorkJson.getString("name"),1,50)) {
					map.put("name", corporationWorkJson.getString("name"));
				}else {
					return ResponseUtil.responseResult(false, "参数[corporationWorkInfo.name]不符合规则", null);
				}
			}

			if(corporationWorkService.update(map, id) > 0) {
				return ResponseUtil.responseResult(true, "更新成功", null);
			}else {
				return ResponseUtil.responseResult(false, "更新失败", null);
			}

		}
	}
	
	//@PreAuthorize("@auth.hasPermission('corporationWork:select')")
	@RequestMapping(value = "/corporationWork/{id}", method = RequestMethod.GET)
	public JSONObject getCorporationWorkById(@PathVariable("id") String id) {
		if(StringUtil.checkLength(id, 1, 36)) {
			CorporationWorkEntity corporationWorkEntity = corporationWorkService.getById(id);
			if(corporationWorkEntity == null) {
				return ResponseUtil.responseResult(false, "不存在该岗位", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", corporationWorkEntity);
			}
		}else {
			return ResponseUtil.responseResult(true, "参数[id]不符合规则", null);
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月28日
	 * @param searchParam 搜索参数
	 * @param pageParam 分页参数
	 * @return 岗位集合
	 * @description 分页搜索查询岗位
	 */
	//@PreAuthorize("@auth.hasPermission('corporationWork:select')")
	@RequestMapping(value = "/corporationWork/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject listCorporationWork(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
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
				if(StringUtil.checkLength(searchJson.getString("name"), 0, 50)) {
					mapLike.put("sys_corporation_work.name", searchJson.getString("name"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.name]不符合规则", null);
				}		
			}

			// 组织机构id验证，放入mapEqual参数
			if(searchJson.containsKey("corporationId")) {
				if(StringUtil.checkLength(searchJson.getString("corporationId"), 1, 36)) {
					mapEqual.put("sys_corporation_work.corporation_id", searchJson.getString("corporationId"));
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.corporationId]不符合规则", null);
				}		
			}

			// 删除标志验证，放入mapEqual参数
			if(searchJson.containsKey("del") && searchJson.getBooleanValue("del")) {
				mapEqual.put("sys_corporation_work.del_flag", true);
			}
		}

		if(!mapEqual.containsKey("sys_corporation_work.del_flag")) {
			mapEqual.put("sys_corporation_work.del_flag", false);
		}

		Page<CorporationWorkEntity> corporationWorkPageList = corporationWorkService.list(mapEqual, mapLike);
		if(corporationWorkPageList.isEmpty()) {
			return ResponseUtil.responseResult(false, "没有查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "获取岗位成功", new PageInfo<>(corporationWorkPageList));
		}
	}
	
	/**
	 * @author 蒋润
	 * @date 2018年11月28日
	 * @param corporationWorkUserInfo 岗位用户信息
	 * @return 是否成功
	 * @description 新增岗位用户
	 */
	//@PreAuthorize("@auth.hasPermission('corporationWork:update')")
	@RequestMapping(value = "/corporationWork/user", method = RequestMethod.POST)
	public JSONObject insertCorporationWorkUser(String corporationWorkUserInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(corporationWorkUserInfo)) {
			return ResponseUtil.responseResult(false, "参数[corporationWorkUserInfo]不能为空", null);
		}else {
			JSONObject corporationWorkUserJson = JSON.parseObject(corporationWorkUserInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证corporationWorkId是否合法
			if(!corporationWorkUserJson.containsKey("corporationWorkId") || StringUtils.isBlank(corporationWorkUserJson.getString("corporationWorkId")) || !StringUtil.checkLength(corporationWorkUserJson.getString("corporationWorkId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[corporationWorkUserInfo.corporationWorkId]不符合规则", null);
			}else {
				map.put("corporation_work_id", corporationWorkUserJson.getString("corporationWorkId"));
			}

			// 验证userId是否合法
			if(!corporationWorkUserJson.containsKey("userId") || StringUtils.isBlank(corporationWorkUserJson.getString("userId")) || !StringUtil.checkLength(corporationWorkUserJson.getString("userId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[corporationWorkUserInfo.userId]不符合规则", null);
			}else {
				map.put("user_id", corporationWorkUserJson.getString("userId"));
			}

			if(corporationWorkService.insertCorporationWorkUser(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}
	
	/**
	 * @author 蒋润
	 * @date 2018年11月28日
	 * @param corporationWorkId 岗位id
	 * @param userId 用户id
	 * @return 是否成功
	 * @description 删除岗位用户
	 */
	//@PreAuthorize("@auth.hasPermission('corporationWork:update')")
	@RequestMapping(value = "/corporationWork/{corporationWorkId}/user/{userId}", method = RequestMethod.DELETE)
	public JSONObject deleteCorporationWorkUser(@PathVariable("corporationWorkId") String corporationWorkId, @PathVariable("userId") String userId) {
		if(StringUtils.isBlank(corporationWorkId) || !StringUtil.checkLength(corporationWorkId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationWorkId]不符合规则", null);
		}else if (StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else {
			if(corporationWorkService.deleteCorporationWorkUser(corporationWorkId, userId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}
	
	/**
	 * @author 蒋润
	 * @date 2018年11月28日
	 * @param corporationWorkId 岗位id
	 * @return 用户集合
	 * @description 查询岗位用户
	 */
	//@PreAuthorize("@auth.hasPermission('corporationWork:select')")
	@RequestMapping(value = "/corporationWork/{corporationWorkId}/user", method = RequestMethod.GET)
	public JSONObject listCorporationWorkUser(@PathVariable("corporationWorkId") String corporationWorkId) {
		if(StringUtils.isBlank(corporationWorkId) || !StringUtil.checkLength(corporationWorkId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationWorkId]不符合规则", null);
		}else {
			List<UserEntity> userList = corporationWorkService.listCorporationWorkUser(corporationWorkId);
			if(userList == null || userList.isEmpty()) {
				return ResponseUtil.responseResult(false, "未查询到数据", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", userList);
			}
		}		
	}
	
	/**
	 * @author 蒋润
	 * @date 2018年11月28日
	 * @param corporationWorkPermissionInfo 岗位权限信息
	 * @return 是否成功
	 * @description 新增岗位权限
	 */
	//@PreAuthorize("@auth.hasPermission('corporationWork:update')")
	@RequestMapping(value = "/corporationWork/permission", method = RequestMethod.POST)
	public JSONObject insertCorporationWorkPermission(String corporationWorkPermissionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(corporationWorkPermissionInfo)) {
			return ResponseUtil.responseResult(false, "参数[corporationWorkPermissionInfo]不能为空", null);
		}else {
			JSONObject corporationWorkPermissionJson = JSON.parseObject(corporationWorkPermissionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证corporationWorkId是否合法
			if(!corporationWorkPermissionJson.containsKey("corporationWorkId") || StringUtils.isBlank(corporationWorkPermissionJson.getString("corporationWorkId")) || !StringUtil.checkLength(corporationWorkPermissionJson.getString("corporationWorkId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[corporationWorkPermissionInfo.corporationWorkId]不符合规则", null);
			}else {
				map.put("corporation_work_id", corporationWorkPermissionJson.getString("corporationWorkId"));
			}

			// 验证permissionId是否合法
			if(!corporationWorkPermissionJson.containsKey("permissionId") || StringUtils.isBlank(corporationWorkPermissionJson.getString("permissionId")) || !StringUtil.checkLength(corporationWorkPermissionJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[corporationWorkPermissionInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", corporationWorkPermissionJson.getString("permissionId"));
			}

			if(corporationWorkService.insertCorporationWorkPermission(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月28日
	 * @param corporationWorkId 岗位id
	 * @param permissionId 权限id
	 * @return 时候成功
	 * @description 删除岗位权限
	 */
	//@PreAuthorize("@auth.hasPermission('corporationWork:update')")
	@RequestMapping(value = "/corporationWork/{corporationWorkId}/permission/{permissionId}", method = RequestMethod.DELETE)
	public JSONObject deleteCorporationWorkPermission(@PathVariable("corporationWorkId") String corporationWorkId, @PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(corporationWorkId) || !StringUtil.checkLength(corporationWorkId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationWorkId]不符合规则", null);
		}else if (StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			if(corporationWorkService.deleteCorporationWorkPermission(corporationWorkId, permissionId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月28日
	 * @param id 
	 * @return 权限集合
	 * @description 获取岗位权限集合
	 */
	//@PreAuthorize("@auth.hasPermission('corporationWork:select')")
	@RequestMapping(value = "/corporationWork/{corporationWorkId}/permission", method = RequestMethod.GET)
	public JSONObject listCorporationWorkPermission(@PathVariable("corporationWorkId") String corporationWorkId) {
		if(StringUtils.isBlank(corporationWorkId) || !StringUtil.checkLength(corporationWorkId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationWorkId]不符合规则", null);
		}else {
			List<PermissionEntity> permissionList = corporationWorkService.listCorporationWorkPermission(corporationWorkId);
			if(permissionList == null || permissionList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", permissionList);
			}
		}
	}

}
