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
import com.brt.bref.common.util.SecurityUtils;
import com.brt.bref.common.util.StringUtil;
import com.brt.bref.common.util.UUIDUtils;
import com.brt.bref.mdm.feign.api.MdmFeign;
import com.brt.bref.user.feign.entity.CorporationEntity;
import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.brt.bref.user.svc.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 蒋润
 * @date 2018年11月27日 用户Controller
 * @description 
 */
@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MdmFeign mdmFeign;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public JSONObject test() {
		
		return ResponseUtil.responseResult(true, "获取用户成功", userService.test());
	}

	@RequestMapping(value = "/info/{username}", method = RequestMethod.GET)
	public JSONObject loadUserByUsername(@PathVariable String username) {
		if (StringUtils.isBlank(username)) {
			return ResponseUtil.responseResult(false, "参数[username]不能为空", null);
		}
		UserEntity user = userService.getUserByUsername(username);
		if (user == null) {
			return ResponseUtil.responseResult(false, "用户不存在", null);
		}
		return ResponseUtil.responseResult(true, "获取用户成功", user);
	}

	@RequestMapping(value = "/company/{name}", method = RequestMethod.GET)
	//@PreAuthorize("@auth.hasPermission('user:detail')" )
	public JSONObject getCompanyByName(@PathVariable String name) {
		log.info("name = {}", name);
		if (StringUtils.isBlank(name)) {
			return ResponseUtil.responseResult(false, "参数[name]不能为空", null);
		}
		JSONObject company = mdmFeign.getCompanyByName(name);
		log.info("company = {}", company == null ? "null" : company.toJSONString());
		if (company == null) {
			return ResponseUtil.responseResult(false, "网络故障，请重试", null);
		}
		if (!company.getBooleanValue(ResponseUtil.JSON_SUCCESS)) {
			return ResponseUtil.responseResult(false, company.getString(ResponseUtil.JSON_MESSAGE), null);
		}
		return ResponseUtil.responseResult(true, "获取用户成功", company);
	}

	@RequestMapping(value = "/companydeny/{name}", method = RequestMethod.GET)
	//@PreAuthorize("@auth.hasPermission('user:company:detail')" )
	public JSONObject getCompanyDenyByName(@PathVariable String name) {
		if (StringUtils.isBlank(name)) {
			return ResponseUtil.responseResult(false, "参数[name]不能为空", null);
		}
		JSONObject company = mdmFeign.getCompanyByName(name);
		if (company == null) {
			return ResponseUtil.responseResult(false, "网络故障，请重试", null);
		}
		if (!company.getBooleanValue(ResponseUtil.JSON_SUCCESS)) {
			return ResponseUtil.responseResult(false, company.getString(ResponseUtil.JSON_MESSAGE), null);
		}
		return ResponseUtil.responseResult(true, "获取用户成功", company);
	}

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public JSONObject me() {
		JSONObject user = SecurityUtils.getUser();
		if(user == null) {
			return ResponseUtil.responseResult(false, "查询失败", null);
		}else {
			return ResponseUtil.responseResult(true, "查询成功", user);
		}
	}
	
	/**
	 * @author 蒋润
	 * @date 2019年1月10日
	 * @param username
	 * @param password
	 * @return 组织机构
	 * @description 根据用户名密码查询组织机构
	 */
	@RequestMapping(value = "/username/corporation", method = RequestMethod.GET)
	public JSONObject getCorporationByUsername(String username, String password) {
		// 验证username是否合法
		if (!StringUtil.checkLength(username, 1, 50)) {
			return ResponseUtil.responseResult(false, "参数[username]不符合规则", null);
		}
		// 验证password是否合法
		if (!StringUtil.checkLength(password, 1, 255)) {
			return ResponseUtil.responseResult(false, "参数[password]不符合规则", null);
		}
		// 验证用户是否存在
		UserEntity user = userService.getUserByUsername(username);
		if (user == null) {
			return ResponseUtil.responseResult(false, "用户不存在", null);
		}
		// 验证密码是否正确
		if(!user.getPassword().equals(password)) {
			return ResponseUtil.responseResult(false, "用户名密码错误", null);
		}
		// 查询用户的组织机构
		List<CorporationEntity> corporationList = userService.listUserCorporation(user.getId());
		return ResponseUtil.responseResult(true, "查询成功", corporationList);
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月22日
	 * @param userInfo 用户信息
	 * @return 注册成功/失败
	 * @description 用户注册（用户新增）
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public JSONObject insert(String userInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(userInfo)) {
			return ResponseUtil.responseResult(false, "参数[userInfo]不能为空", null);
		}else {
			JSONObject userJson = JSON.parseObject(userInfo);

			Map<String, Object> map = new HashMap<>();
			// 验证username是否合法
			String username = userJson.getString("username");
			if(!StringUtil.checkLength(username,6,50)){
				return ResponseUtil.responseResult(false, "参数[userInfo.username]不符合规则", null);
			}else if(!userService.checkUsername(username)){
				return ResponseUtil.responseResult(false, "参数用户名[userInfo.username]被占用", null);
			}else{
				map.put("username", username);
			}
			// 验证password是否合法
			String password = userJson.getString("password");
			if(!userJson.containsKey("password") || StringUtils.isBlank(password) || !StringUtil.checkLength(password,6,255)){
				return ResponseUtil.responseResult(false, "参数[userInfo.password]不符合规则", null);
			}else {
				map.put("password", password);
			}

			map.put("enabled", true);
			map.put("id", UUIDUtils.getUUID());
			if(userService.insert(map) > 0) {
				return ResponseUtil.responseResult(true, "注册成功", null);
			}else {
				return ResponseUtil.responseResult(false, "注册失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月22日
	 * @param id
	 * @return 删除用户成功/失败
	 * @description 软删除用户
	 */
	//@PreAuthorize("@auth.hasPermission('user:delete')")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			if(userService.delete(id) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @param userInfo
	 * @param id 用户id
	 * @return 用户信息
	 * @description 根据用户id更新用户信息
	 */
	//@PreAuthorize("@auth.hasPermission('user:update')")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public JSONObject update(@RequestParam("userInfo") String userInfo, @PathVariable("id") String id) {
		if(StringUtils.isBlank(userInfo)) {
			return ResponseUtil.responseResult(false, "参数[userInfo]不能为空", null);
		}else if(StringUtils.isBlank(id)){
			return ResponseUtil.responseResult(false, "参数[id]不能为空", null);
		}else {
			JSONObject userJson = JSON.parseObject(userInfo);

			Map<String, Object> map = new HashMap<>();

			if(userJson.containsKey("password")){
				String password = userJson.getString("password");
				if(StringUtil.checkLength(password,6,255)) {
					map.put("password", password);
				}else {
					return ResponseUtil.responseResult(false, "参数[userInfo.password]不符合规则", null);
				}
			}

			if(userService.update(map,id) > 0) {
				return ResponseUtil.responseResult(true, "修改成功", null);
			}else {
				return ResponseUtil.responseResult(false, "修改失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @param id 用户id
	 * @return 用户基本信息
	 * @description 查询某个用户的基本信息
	 */
	//@PreAuthorize("@auth.hasPermission('user:select')")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public JSONObject getUserById(@PathVariable("id") String id) {
		if(StringUtils.isBlank(id) || !StringUtil.checkLength(id,1,36)) {
			return ResponseUtil.responseResult(false, "参数[id]不符合规则", null);
		}else {
			UserEntity user = userService.getById(id);
			if(user == null) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", user);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月22日
	 * @param searchParam 搜索参数
	 * @param pageParam 分页参数
	 * @return 用户集合
	 * @description 分页搜索用户
	 */
	//@PreAuthorize("@auth.hasPermission('user:select')")
	@RequestMapping(value = "/user/{pageParam}/{searchParam}", method = RequestMethod.GET)
	public JSONObject listUser(@PathVariable("searchParam") String searchParam, @PathVariable("pageParam") String pageParam) {
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
			// 用户名（username）验证，放入maplike参数
			if(searchJson.containsKey("username")) {
				if(StringUtil.checkLength(searchJson.getString("username").trim(), 0, 50)) {
					mapLike.put("username", searchJson.getString("username").trim());
				}else {
					return ResponseUtil.responseResult(false, "参数[searchParam.username]不符合规则", null);
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

		Page<UserEntity> userPageList = userService.list(mapEqual, mapLike);
		if(userPageList.isEmpty()) {
			return ResponseUtil.responseResult(false, "没有查询到数据", null);
		}else {
			return ResponseUtil.responseResult(true, "获取用户成功", new PageInfo<>(userPageList));
		}
		
	}


	/**
	 * @author 蒋润
	 * @date 2018年11月22日
	 * @param userCorporationInfo 用户组织机构信息
	 * @return 是否成功
	 * @description 新增用户组织机构
	 */
	//@PreAuthorize("@auth.hasPermission('user:update')")
	@RequestMapping(value = "/user/corporation", method = RequestMethod.POST)
	public JSONObject insertUserCorporation(String userCorporationInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(userCorporationInfo)) {
			return ResponseUtil.responseResult(false, "参数[userCorporationInfo]不能为空", null);
		}else {
			JSONObject userCorporationJson = JSON.parseObject(userCorporationInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证userId是否合法
			if(!userCorporationJson.containsKey("userId") || StringUtils.isBlank(userCorporationJson.getString("userId")) || !StringUtil.checkLength(userCorporationJson.getString("userId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[userCorporationInfo.userId]不符合规则", null);
			}else {
				map.put("user_id", userCorporationJson.getString("userId"));
			}

			// 验证corporationId是否合法
			if(!userCorporationJson.containsKey("corporationId") || StringUtils.isBlank(userCorporationJson.getString("corporationId")) || !StringUtil.checkLength(userCorporationJson.getString("corporationId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[userCorporationInfo.corporationId]不符合规则", null);
			}else {
				map.put("corporation_id", userCorporationJson.getString("corporationId"));
			}

			if(userService.insertUserCorporation(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param userId 用户id
	 * @param corporationId 组织机构id
	 * @return 是否成功
	 * @description 删除用户组织机构
	 */
	//@PreAuthorize("@auth.hasPermission('user:update')")
	@RequestMapping(value = "/user/{userId}/corporation/{corporationId}", method = RequestMethod.DELETE)
	public JSONObject deleteUserCorporation(@PathVariable("userId") String userId, @PathVariable("corporationId") String corporationId) {
		if(StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else if (StringUtils.isBlank(corporationId) || !StringUtil.checkLength(corporationId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationId]不符合规则", null);
		}else {
			if(userService.deleteUserCorporation(userId, corporationId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月22日
	 * @param userId 用户id
	 * @return 用户组织机构列表
	 * @description 查询某个用户的组织机构
	 */
	//@PreAuthorize("@auth.hasPermission('user:select')")
	@RequestMapping(value = "/user/{userId}/corporation", method = RequestMethod.GET)
	public JSONObject listUserCorporation(@PathVariable("userId") String userId) {
		if(StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else {
			List<CorporationEntity> corporationList = userService.listUserCorporation(userId);
			if(corporationList == null || corporationList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", corporationList);
			}
		}	
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月22日
	 * @param userCorporationWorkInfo 用户岗位信息
	 * @return 是否成功
	 * @description 新增用户岗位
	 */
	//@PreAuthorize("@auth.hasPermission('user:update')")
	@RequestMapping(value = "/user/corporationWork", method = RequestMethod.POST)
	public JSONObject insertUserCorporationWork(String userCorporationWorkInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(userCorporationWorkInfo)) {
			return ResponseUtil.responseResult(false, "参数[userCorporationWorkInfo]不能为空", null);
		}else {
			JSONObject userCorporationWorkJson = JSON.parseObject(userCorporationWorkInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证userId是否合法
			if(!userCorporationWorkJson.containsKey("userId") || StringUtils.isBlank(userCorporationWorkJson.getString("userId")) || !StringUtil.checkLength(userCorporationWorkJson.getString("userId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[userCorporationWorkInfo.userId]不符合规则", null);
			}else {
				map.put("user_id", userCorporationWorkJson.getString("userId"));
			}

			// 验证corporationWorkId是否合法
			if(!userCorporationWorkJson.containsKey("corporationWorkId") || StringUtils.isBlank(userCorporationWorkJson.getString("corporationWorkId")) || !StringUtil.checkLength(userCorporationWorkJson.getString("corporationWorkId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[userCorporationInfo.corporationWorkId]不符合规则", null);
			}else {
				map.put("corporation_work_id", userCorporationWorkJson.getString("corporationWorkId"));
			}

			if(userService.insertUserCorporationWork(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param userId 用户id
	 * @param corporationWorkId 岗位id
	 * @return 是否成功
	 * @description 删除用户岗位
	 */
	//@PreAuthorize("@auth.hasPermission('user:update')")
	@RequestMapping(value = "/user/{userId}/corporationWork/{corporationWorkId}", method = RequestMethod.DELETE)
	public JSONObject deleteUserCorporationWork(@PathVariable("userId") String userId, @PathVariable("corporationWorkId") String corporationWorkId) {
		if(StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else if (StringUtils.isBlank(corporationWorkId) || !StringUtil.checkLength(corporationWorkId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[corporationWorkId]不符合规则", null);
		}else {
			if(userService.deleteUserCorporationWork(userId, corporationWorkId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @param userId 用户id
	 * @return 用户岗位列表
	 * @description 查询某个用户的岗位
	 */
	//@PreAuthorize("@auth.hasPermission('user:select')")
	@RequestMapping(value = "/user/{userId}/corporationWork", method = RequestMethod.GET)
	public JSONObject listUserCorporationWork(@PathVariable("userId") String userId) {
		if(StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else {
			List<CorporationWorkEntity> corporationWorkList = userService.listUserCorporationWork(userId);
			if(corporationWorkList == null || corporationWorkList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", corporationWorkList);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param userJobPositionInfo 用户职务信息
	 * @return 是否成功
	 * @description 新增用户职务
	 */
	//@PreAuthorize("@auth.hasPermission('user:update')")
	@RequestMapping(value = "/user/jobPosition", method = RequestMethod.POST)
	public JSONObject insertUserJobPosition(String userJobPositionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(userJobPositionInfo)) {
			return ResponseUtil.responseResult(false, "参数[userJobPositionInfo]不能为空", null);
		}else {
			JSONObject userJobPositionJson = JSON.parseObject(userJobPositionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证userId是否合法
			if(!userJobPositionJson.containsKey("userId") || StringUtils.isBlank(userJobPositionJson.getString("userId")) || !StringUtil.checkLength(userJobPositionJson.getString("userId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[userJobPositionInfo.userId]不符合规则", null);
			}else {
				map.put("user_id", userJobPositionJson.getString("userId"));
			}

			// 验证jobPositionId是否合法
			if(!userJobPositionJson.containsKey("jobPositionId") || StringUtils.isBlank(userJobPositionJson.getString("jobPositionId")) || !StringUtil.checkLength(userJobPositionJson.getString("jobPositionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[userJobPositionInfo.jobPositionId]不符合规则", null);
			}else {
				map.put("job_position_id", userJobPositionJson.getString("jobPositionId"));
			}

			if(userService.insertUserJobPosition(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param userId 用户id
	 * @param jobPositionId 职务id
	 * @return 是否成功
	 * @description 删除用户职务
	 */
	//@PreAuthorize("@auth.hasPermission('user:update')")
	@RequestMapping(value = "/user/{userId}/jobPosition/{jobPositionId}", method = RequestMethod.DELETE)
	public JSONObject deleteUserJobPosition(@PathVariable("userId") String userId, @PathVariable("jobPositionId") String jobPositionId) {
		if(StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else if (StringUtils.isBlank(jobPositionId) || !StringUtil.checkLength(jobPositionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[jobPositionId]不符合规则", null);
		}else {
			if(userService.deleteUserJobPosition(userId, jobPositionId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月26日
	 * @param userId
	 * @return 用户职务列表
	 * @description 查询某个用户的职务
	 */
	//@PreAuthorize("@auth.hasPermission('user:select')")
	@RequestMapping(value = "/user/{userId}/jobPosition", method = RequestMethod.GET)
	public JSONObject listUserJobPosition(@PathVariable("userId") String userId) {
		if(StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else {
			List<JobPositionEntity> jobPositionList = userService.listUserJobPosition(userId);
			if(jobPositionList == null || jobPositionList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", jobPositionList);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param userPermissionInfo 用户权限信息
	 * @return 是否成功
	 * @description 新增用户权限
	 */
	//@PreAuthorize("@auth.hasPermission('user:update')")
	@RequestMapping(value = "/user/permission", method = RequestMethod.POST)
	public JSONObject insertUserPermission(String userPermissionInfo) {
		// 验证参数不为空
		if(StringUtils.isBlank(userPermissionInfo)) {
			return ResponseUtil.responseResult(false, "参数[userPermissionInfo]不能为空", null);
		}else {
			JSONObject userPermissionJson = JSON.parseObject(userPermissionInfo);

			Map<String, Object> map = new HashMap<>();

			// 验证userId是否合法
			if(!userPermissionJson.containsKey("userId") || StringUtils.isBlank(userPermissionJson.getString("userId")) || !StringUtil.checkLength(userPermissionJson.getString("userId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[userPermissionInfo.userId]不符合规则", null);
			}else {
				map.put("user_id", userPermissionJson.getString("userId"));
			}

			// 验证permissionId是否合法
			if(!userPermissionJson.containsKey("permissionId") || StringUtils.isBlank(userPermissionJson.getString("permissionId")) || !StringUtil.checkLength(userPermissionJson.getString("permissionId"),1,36)){
				return ResponseUtil.responseResult(false, "参数[userPermissionInfo.permissionId]不符合规则", null);
			}else {
				map.put("permission_id", userPermissionJson.getString("permissionId"));
			}

			if(userService.insertUserPermission(map) > 0) {
				return ResponseUtil.responseResult(true, "新增成功", null);
			}else {
				return ResponseUtil.responseResult(false, "新增失败", null);
			}
		}
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param userId 用户id
	 * @param permissionId 权限id
	 * @return 是否成功
	 * @description 删除用户权限
	 */
	//@PreAuthorize("@auth.hasPermission('user:update')")
	@RequestMapping(value = "/user/{userId}/permission/{permissionId}", method = RequestMethod.DELETE)
	public JSONObject deleteUserPermission(@PathVariable("userId") String userId, @PathVariable("permissionId") String permissionId) {
		if(StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else if (StringUtils.isBlank(permissionId) || !StringUtil.checkLength(permissionId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[permissionId]不符合规则", null);
		}else {
			if(userService.deleteUserPermission(userId, permissionId) > 0) {
				return ResponseUtil.responseResult(true, "删除成功", null);
			}else {
				return ResponseUtil.responseResult(false, "删除失败", null);
			}
		}		
	}

	/**
	 * @author 蒋润
	 * @date 2018年11月27日
	 * @param id 用户id
	 * @return 用户权限集合
	 * @description 获取用户权限
	 */
	//@PreAuthorize("@auth.hasPermission('user:select')")
	@RequestMapping(value = "/user/{userId}/permission", method = RequestMethod.GET)
	public JSONObject listUserPermission(@PathVariable("userId") String userId) {
		if(StringUtils.isBlank(userId) || !StringUtil.checkLength(userId,1,36)) {
			return ResponseUtil.responseResult(false, "参数[userId]不符合规则", null);
		}else {
			List<PermissionEntity> permissionList = userService.listUserPermission(userId);
			if(permissionList == null || permissionList.isEmpty()) {
				return ResponseUtil.responseResult(false, "查询失败", null);
			}else {
				return ResponseUtil.responseResult(true, "查询成功", permissionList);
			}
		}
	}

}
