package com.brt.bref.user.svc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brt.bref.common.util.SecurityUtils;
import com.brt.bref.user.feign.entity.CorporationEntity;
import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.brt.bref.user.svc.dao.UserCorporationDao;
import com.brt.bref.user.svc.dao.UserCorporationWorkDao;
import com.brt.bref.user.svc.dao.UserDao;
import com.brt.bref.user.svc.dao.UserJobPositionDao;
import com.brt.bref.user.svc.dao.UserPermissionDao;
import com.brt.bref.user.svc.service.UserService;
import com.github.pagehelper.Page;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserCorporationDao userCorporationDao;
	
	@Autowired
	private UserCorporationWorkDao userCorporationWorkDao;
	
	@Autowired
	private UserJobPositionDao userJobPositionDao;
	
	@Autowired
	private UserPermissionDao userPermissionDao;
	
	@Override
	public UserEntity getUserByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			return null;
		}
		return userDao.getUserByUsername(username);
	}

	@Override
	public boolean checkUsername(String username) {
		if(userDao.getUserByUsername(username) == null) {
			return true;
		}else {
			return false;
		}	
	}

	@Override
	public int insert(Map<String, Object> map) {
		return userDao.insert(map);
	}

	@Override
	public int delete(String id) {
		return userDao.deleteById(id);
	}
	
	@Override
	public int update(Map<String, Object> map, String id) {
		return userDao.update(map,id);
	}
	
	@Override
	public UserEntity getById(String id) {
		return userDao.getById(id);
	}
	
	@Override
	public Page<UserEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		Map<String, String> dataSchema = SecurityUtils.getDataSchema();
		Map<String, Map<String,List<Map<String, Object>>>> dataScope = SecurityUtils.getDataScope();
		return userDao.list(mapEqual, mapLike, dataSchema.get("user:list"), dataScope.get("user:list"));
	}

	//---------------------------------------------------------------------------------------------
	
	@Override
	public int insertUserCorporation(Map<String, Object> map) {
		return userCorporationDao.insert(map);
	}
	
	@Override
	public int deleteUserCorporation(String userId, String corporationId) {
		return userCorporationDao.delete(userId, corporationId);
	}
	
	@Override
	public List<CorporationEntity> listUserCorporation(String userId) {
		return userCorporationDao.listUserCorporation(userId);
	}
	
	//---------------------------------------------------------------------------------------------
	
	@Override
	public int insertUserCorporationWork(Map<String, Object> map) {
		return userCorporationWorkDao.insert(map);
	}
	
	@Override
	public int deleteUserCorporationWork(String userId, String corporationWorkId) {
		return userCorporationWorkDao.delete(userId, corporationWorkId);
	}

	@Override
	public List<CorporationWorkEntity> listUserCorporationWork(String userId) {
		return userCorporationWorkDao.listUserCorporationWork(userId);
	}
	
	//---------------------------------------------------------------------------------------------
	
	@Override
	public int insertUserJobPosition(Map<String, Object> map) {
		return userJobPositionDao.insert(map);
	}

	@Override
	public int deleteUserJobPosition(String userId, String jobPositionId) {
		return userJobPositionDao.delete(userId, jobPositionId);
	}

	@Override
	public List<JobPositionEntity> listUserJobPosition(String userId) {
		return userJobPositionDao.listUserJobPosition(userId);
	}
	
	//---------------------------------------------------------------------------------------------

	@Override
	public int insertUserPermission(Map<String, Object> map) {
		return userPermissionDao.insert(map);
	}

	@Override
	public int deleteUserPermission(String userId, String permissionId) {
		return userPermissionDao.delete(userId, permissionId);
	}

	@Override
	public List<PermissionEntity> listUserPermission(String userId) {
		return userPermissionDao.listUserPermission(userId);
	}

	@Override
	public Page<UserEntity> test() {
		Map<String,List<Map<String, Object>>> map = new HashMap<>();
		List<Map<String, Object>> username = new ArrayList<>();
		Map<String, Object> a = new HashMap<>();
		a.put("columnName", "sys_user.username");
		a.put("operator", "=");
		a.put("dataScope", "'test'");
		username.add(a);
		Map<String, Object> b = new HashMap<>();
		b.put("columnName", "sys_user.username");
		b.put("operator", "=");
		b.put("dataScope", "'admin'");
		username.add(b);
		Map<String, Object> c = new HashMap<>();
		c.put("columnName", "sys_user.username");
		c.put("operator", "=");
		c.put("dataScope", "'ppp'");
		username.add(c);
		map.put("sys_user.username", username);
		
		List<Map<String, Object>> enabled = new ArrayList<>();
		Map<String, Object> d = new HashMap<>();
		d.put("columnName", "sys_user.enabled");
		d.put("operator", "=");
		d.put("dataScope", 1);
		enabled.add(d);
		map.put("sys_user.enabled", enabled);
		return userDao.list(null, null, null, map);
	}

}

