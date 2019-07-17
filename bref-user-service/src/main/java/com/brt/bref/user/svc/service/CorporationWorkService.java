package com.brt.bref.user.svc.service;

import java.util.List;
import java.util.Map;

import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.github.pagehelper.Page;

public interface CorporationWorkService {

	int insert(Map<String, Object> map);

	int delete(String id);

	int update(Map<String, Object> map, String id);
	
	CorporationWorkEntity getById(String id);	

	Page<CorporationWorkEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);
	
	int insertCorporationWorkUser(Map<String, Object> map);

	int deleteCorporationWorkUser(String corporationWorkId, String userId);

	List<UserEntity> listCorporationWorkUser(String corporationWorkId);

	int insertCorporationWorkPermission(Map<String, Object> map);

	int deleteCorporationWorkPermission(String corporationWorkId, String permissionId);

	List<PermissionEntity> listCorporationWorkPermission(String corporationWorkId);

}
