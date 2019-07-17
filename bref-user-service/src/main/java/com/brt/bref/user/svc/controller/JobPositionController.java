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
import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.brt.bref.user.svc.service.JobPositionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author 方杰
 * @date 2018年11月27日 
 * @description 职务Controller
 */
@RestController
public class JobPositionController {
	@Autowired
	private JobPositionService jobPositionService;
	
	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param jobPositionInfo 职务信息
	 * @return 是否成功
	 * @description 新增职务
	 */
	//@PreAuthorize("@auth.hasPermission('jobPosition:insert')")
	@RequestMapping(value = "/jobPosition", method = RequestMethod.POST)
	public JSONObject insert(String jobPositionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(jobPositionInfo)) {
			return ResponseUtil.responseResult(false, "参数[jobPositionInfo]不能为空", null);
		}else {
			JSONObject jobPositionJson = JSON.parseObject(jobPositionInfo);

			Map<String,Object> map = new HashMap<>();
			// 验证name是否合法
			String name = jobPositionJson.getString("name");
			if(StringUtil.checkLength(name, 1, 50)) {
				map.put("name", name);
			}else {
				return ResponseUtil.responseResult(false, "参数[jobPositionInfo.name]不符合规则", null);
			}
			
			map.put("id", UUIDUtils.getUUID());
			
			if(jobPositionService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
			
		}
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param id 职务id
	 * @return 是否成功
	 * @description 软删除职务
	 */
	//@PreAuthorize("@auth.hasPermission('jobPosition:delete')")
	@RequestMapping(value = "/jobPosition/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(jobPositionService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月27日
	 * @param jobPositionInfo 职务信息
	 * @param id 职务id
	 * @return 是否成功
	 * @description 修改职务信息
	 */
	//@PreAuthorize("@auth.hasPermission('jobPosition:update')")
	@RequestMapping(value = "/jobPosition/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("jobPositionInfo") String jobPositionInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(jobPositionInfo)) {
			return ResponseUtil.responseResult(false, "参数[jobPositionInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject jobPositionJson = JSON.parseObject(jobPositionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证name是否合法
			if(jobPositionJson.containsKey("name")) {
				String name = jobPositionJson.getString("name");
				if(StringUtil.checkLength(name,1,50)) {
					map.put("name", name);
				}else {
					return ResponseUtil.responseResult(false, "参数[jobPositionInfo.name]不符合规则", null);
				}
			}
			
			if(jobPositionService.update(map, id) > 0) {
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
	 * @return 职务集合
	 * @description 分页搜索查询职务
	 */
	//@PreAuthorize("@auth.hasPermission('jobPosition:select')")
	@RequestMapping(value = "/jobPosition/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject listJobPosition(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
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

		Page<JobPositionEntity> jobPositionPageList = jobPositionService.list(mapEqual, mapLike);
		if(jobPositionPageList.isEmpty()) {
			return ResponseUtil.responseResult(false, "没有查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "获取职务成功", new PageInfo<>(jobPositionPageList));
		}
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月28日
	 * @param jobPositionUserInfo 职务用户信息
	 * @return 是否成功
	 * @description 新增职务用户
	 */
	//@PreAuthorize("@auth.hasPermission('jobPosition:update')")
	@RequestMapping(value = "/jobPosition/user", method = RequestMethod.POST)
	public JSONObject insertJobPositionUser(String jobPositionUserInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(jobPositionUserInfo)) {
			return ResponseUtil.responseResult(false, "参数[jobPositionUserInfo]不能为空", null);
		}else {
			JSONObject jobPositionUserJson = JSON.parseObject(jobPositionUserInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证jobPositionId是否合法
			if(!jobPositionUserJson.containsKey("jobPositionId") || StringUtils.isBlank(jobPositionUserJson.getString("jobPositionId")) || !StringUtil.checkLength(jobPositionUserJson.getString("jobPositionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[jobPositionUserInfo.jobPositionId]不符合规则", null);
			}else {
				map.put("job_position_id", jobPositionUserJson.getString("jobPositionId"));
			}

			// 验证userId是否合法
			if(!jobPositionUserJson.containsKey("userId") || StringUtils.isBlank(jobPositionUserJson.getString("userId")) || !StringUtil.checkLength(jobPositionUserJson.getString("userId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[jobPositionUserInfo.userId]不符合规则", null);
			}else {
				map.put("user_id", jobPositionUserJson.getString("userId"));
			}

			if(jobPositionService.insertJobPositionUser(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月28日
	 * @param jobPositionId 职务id
	 * @param userId 用户id
	 * @return 是否成功
	 * @description 删除职务用户
	 */
	//@PreAuthorize("@auth.hasPermission('jobPosition:update')")
	@RequestMapping(value = "/jobPosition/{jobPositionId}/user/{userId}", method = RequestMethod.DELETE)
	public JSONObject deleteJobPositionUser(@PathVariable("jobPositionId") String jobPositionId, @PathVariable("userId") String userId) {
		if(StringUtils.isBlank(jobPositionId) || !StringUtil.checkLength(jobPositionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[jobPositionId]不符合规则", null);
		}else if (StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else {
			if(jobPositionService.deleteJobPositionUser(jobPositionId, userId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月28日
	 * @param jobPositionId 职务id
	 * @return 用户集合
	 * @description 获取职务用户
	 */
	//@PreAuthorize("@auth.hasPermission('jobPosition:select')")
	@RequestMapping(value = "/jobPosition/{jobPositionId}/user", method = RequestMethod.GET)
	public JSONObject listJobPositionUser(@PathVariable("jobPositionId") String jobPositionId) {
		if(StringUtils.isBlank(jobPositionId) || !StringUtil.checkLength(jobPositionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[jobPositionId]不符合规则", null);
		}else {
			List<UserEntity> userList = jobPositionService.listJobPositionUser(jobPositionId);
			if(userList == null || userList.isEmpty()) {
				return ResponseUtil.responseResult(false, "未查询到数据", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", userList);
			}
		}		
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月28日
	 * @param jobPositionPermissionInfo 职务权限信息
	 * @return 是否成功
	 * @description 新增职务权限
	 */
	//@PreAuthorize("@auth.hasPermission('jobPosition:update')")
	@RequestMapping(value = "/jobPosition/permission", method = RequestMethod.POST)
	public JSONObject insertJobPositionPermission(String jobPositionPermissionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(jobPositionPermissionInfo)) {
			return ResponseUtil.responseResult(false, "参数[jobPositionPermissionInfo]不能为空", null);
		}else {
			JSONObject jobPositionPermissionJson = JSON.parseObject(jobPositionPermissionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证jobPositionId是否合法
			if(!jobPositionPermissionJson.containsKey("jobPositionId") || StringUtils.isBlank(jobPositionPermissionJson.getString("jobPositionId")) || !StringUtil.checkLength(jobPositionPermissionJson.getString("jobPositionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[jobPositionPermissionInfo.jobPositionId]不符合规则", null);
			}else {
				map.put("job_position_id", jobPositionPermissionJson.getString("jobPositionId"));
			}

			// 验证permissionId是否合法
			if(!jobPositionPermissionJson.containsKey("permissionId") || StringUtils.isBlank(jobPositionPermissionJson.getString("permissionId")) || !StringUtil.checkLength(jobPositionPermissionJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[jobPositionPermissionInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", jobPositionPermissionJson.getString("permissionId"));
			}

			if(jobPositionService.insertJobPositionPermission(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 方杰
	 * @date 2018年11月28日
	 * @param jobPositionId 职务id
	 * @param permissionId 权限id
	 * @return 是否成功
	 * @description 删除职务权限
	 */
	//@PreAuthorize("@auth.hasPermission('jobPosition:update')")
	@RequestMapping(value = "/jobPosition/{jobPositionId}/permission/{permissionId}", method = RequestMethod.DELETE)
	public JSONObject deleteJobPositionPermission(@PathVariable("jobPositionId") String jobPositionId, @PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(jobPositionId) || !StringUtil.checkLength(jobPositionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[jobPositionId]不符合规则", null);
		}else if (StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else {
			if(jobPositionService.deleteJobPositionPermission(jobPositionId, permissionId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}
	
	/**
	 * @author 方杰
	 * @date 2018年11月28日
	 * @param jobPositionId 职务id
	 * @return 权限集合
	 * @description 查询职务权限
	 */
	//@PreAuthorize("@auth.hasPermission('jobPosition:select')")
	@RequestMapping(value = "/jobPosition/{jobPositionId}/permission", method = RequestMethod.GET)
	public JSONObject listJobPositionPermission(@PathVariable("jobPositionId") String jobPositionId) {
		if(StringUtils.isBlank(jobPositionId) || !StringUtil.checkLength(jobPositionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[jobPositionId]不符合规则", null);
		}else {
			List<PermissionEntity> permissionList = jobPositionService.listJobPositionPermission(jobPositionId);
			if(permissionList == null || permissionList.isEmpty()) {
				return ResponseUtil.responseResult(false, "未查询到数据", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", permissionList);
			}
		}		
	}

}
