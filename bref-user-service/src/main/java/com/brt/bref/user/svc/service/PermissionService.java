package com.brt.bref.user.svc.service;

import java.util.List;
import java.util.Map;

import com.brt.bref.user.feign.entity.CorporationEntity;
import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.DataSchemaEntity;
import com.brt.bref.user.feign.entity.DataScopeEntity;
import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.ModuleEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.github.pagehelper.Page;

public interface PermissionService {

	int insert(Map<String, Object> map);

	int delete(String id);

	int update(Map<String, Object> map, String id);

	Page<PermissionEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);
	
	int insertPermissionDataSchema(Map<String, Object> map);

	int deletePermissionDataSchema(String permissionId, String dataSchemaId);

	List<DataSchemaEntity> listPermissionDataSchema(String permissionId);
	
	int insertPermissionDataScope(Map<String, Object> map);

	int deletePermissionDataScope(String permissionId, String dataScopeId);

	List<DataScopeEntity> listPermissionDataScope(String permissionId);
	
	int insertPermissionModule(Map<String, Object> map);

	int deletePermissionModule(String permissionId, String moduleId);

	List<ModuleEntity> listPermissionModule(String permissionId);
	
	int insertPermissionUser(Map<String, Object> map);

	int deletePermissionUser(String permissionId, String userId);

	List<UserEntity> listPermissionUser(String permissionId);
	
	int insertPermissionCorporation(Map<String, Object> map);

	int deletePermissionCorporation(String permissionId, String corporationId);

	List<CorporationEntity> listPermissionCorporation(String permissionId);

	int insertPermissionCorporationWork(Map<String, Object> map);

	int deletePermissionCorporationWork(String permissionId, String corporationWorkId);

	List<CorporationWorkEntity> listPermissionCorporationWork(String permissionId);
	
	int insertPermissionJobPosition(Map<String, Object> map);

	int deletePermissionJobPosition(String permissionId, String jobPositionId);

	List<JobPositionEntity> listPermissionJobPosition(String permissionId);
}
