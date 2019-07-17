package com.brt.bref.user.svc.service;

import java.util.List;
import java.util.Map;

import com.brt.bref.user.feign.entity.CorporationEntity;
import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.github.pagehelper.Page;

public interface UserService {

	UserEntity getUserByUsername(String username);	

	public boolean checkUsername(String username);

	int insert(Map<String, Object> map);

	int delete(String id);
	
	int update(Map<String, Object> map, String id);
	
	UserEntity getById(String id);
	
	Page<UserEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);
	
	//---------------------------------------------------------------------------------------------
	
	int insertUserCorporation(Map<String, Object> map);
	
	int deleteUserCorporation(String userId, String corporationId);

	List<CorporationEntity> listUserCorporation(String userId);
	
	//---------------------------------------------------------------------------------------------
	
	int insertUserCorporationWork(Map<String, Object> map);
	
	int deleteUserCorporationWork(String userId, String corporationWorkId);

	List<CorporationWorkEntity> listUserCorporationWork(String userId);
	
	//---------------------------------------------------------------------------------------------
	
	int insertUserJobPosition(Map<String, Object> map);
	
	int deleteUserJobPosition(String userId, String jobPositionId);

	List<JobPositionEntity> listUserJobPosition(String userId);
	
	//---------------------------------------------------------------------------------------------

	int insertUserPermission(Map<String, Object> map);

	int deleteUserPermission(String userId, String permissionId);

	List<PermissionEntity> listUserPermission(String userId);

	Page<UserEntity> test();
}
