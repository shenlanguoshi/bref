package com.brt.bref.user.svc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brt.bref.user.feign.entity.CorporationEntity;
import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.DataSchemaEntity;
import com.brt.bref.user.feign.entity.DataScopeEntity;
import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.ModuleEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.brt.bref.user.svc.dao.CorporationPermissionDao;
import com.brt.bref.user.svc.dao.CorporationWorkPermissionDao;
import com.brt.bref.user.svc.dao.JobPositionPermissionDao;
import com.brt.bref.user.svc.dao.PermissionDao;
import com.brt.bref.user.svc.dao.PermissionDataSchemaDao;
import com.brt.bref.user.svc.dao.PermissionDataScopeDao;
import com.brt.bref.user.svc.dao.PermissionModuleDao;
import com.brt.bref.user.svc.dao.UserPermissionDao;
import com.brt.bref.user.svc.service.PermissionService;
import com.github.pagehelper.Page;

@Service
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private PermissionDataSchemaDao permissionDataSchemaDao;
	
	@Autowired
	private PermissionDataScopeDao permissionDataScopeDao;
	
	@Autowired
	private PermissionModuleDao permissionModuleDao;
	
	@Autowired
	private UserPermissionDao userPermissionDao;
	
	@Autowired
	private CorporationPermissionDao corporationPermissionDao;
	
	@Autowired
	private CorporationWorkPermissionDao corporationWorkPermissionDao;
	
	@Autowired
	private JobPositionPermissionDao jobPositionPermissionDao;

	@Override
	public int insert(Map<String, Object> map) {
		return permissionDao.insert(map);
	}

	@Override
	public int delete(String id) {
		return permissionDao.deleteById(id);
	}

	@Override
	public int update(Map<String, Object> map, String id) {
		return permissionDao.update(map, id);
	}

	@Override
	public Page<PermissionEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		return permissionDao.list(mapEqual, mapLike);
	}

	@Override
	public int insertPermissionDataSchema(Map<String, Object> map) {
		return permissionDataSchemaDao.insert(map);
	}

	@Override
	public int deletePermissionDataSchema(String permissionId, String dataSchemaId) {
		return permissionDataSchemaDao.delete(permissionId, dataSchemaId);
	}

	@Override
	public List<DataSchemaEntity> listPermissionDataSchema(String permissionId) {
		return permissionDataSchemaDao.listPermissionDataSchema(permissionId);
	}

	@Override
	public int insertPermissionDataScope(Map<String, Object> map) {
		return permissionDataScopeDao.insert(map);
	}

	@Override
	public int deletePermissionDataScope(String permissionId, String dataScopeId) {
		return permissionDataScopeDao.delete(permissionId, dataScopeId);
	}

	@Override
	public List<DataScopeEntity> listPermissionDataScope(String permissionId) {
		return permissionDataScopeDao.listPermissionDataScope(permissionId);
	}

	@Override
	public int insertPermissionModule(Map<String, Object> map) {
		return permissionModuleDao.insert(map);
	}

	@Override
	public int deletePermissionModule(String permissionId, String moduleId) {
		return permissionModuleDao.delete(permissionId, moduleId);
	}

	@Override
	public List<ModuleEntity> listPermissionModule(String permissionId) {
		return permissionModuleDao.listPermissionModule(permissionId);
	}

	@Override
	public int insertPermissionUser(Map<String, Object> map) {
		return userPermissionDao.insert(map);
	}

	@Override
	public int deletePermissionUser(String permissionId, String userId) {
		return userPermissionDao.delete(userId, permissionId);
	}

	@Override
	public List<UserEntity> listPermissionUser(String permissionId) {
		return userPermissionDao.listPermissionUser(permissionId);
	}

	@Override
	public int insertPermissionCorporation(Map<String, Object> map) {
		return corporationPermissionDao.insert(map);
	}

	@Override
	public int deletePermissionCorporation(String permissionId, String corporationId) {
		return corporationPermissionDao.delete(corporationId, permissionId);
	}

	@Override
	public List<CorporationEntity> listPermissionCorporation(String permissionId) {
		return corporationPermissionDao.listPermissionCorporation(permissionId);
	}

	@Override
	public int insertPermissionCorporationWork(Map<String, Object> map) {
		return corporationWorkPermissionDao.insert(map);
	}

	@Override
	public int deletePermissionCorporationWork(String permissionId, String corporationWorkId) {
		return corporationWorkPermissionDao.delete(corporationWorkId, permissionId);
	}

	@Override
	public List<CorporationWorkEntity> listPermissionCorporationWork(String permissionId) {
		return corporationWorkPermissionDao.listPermissionCorporationWork(permissionId);
	}

	@Override
	public int insertPermissionJobPosition(Map<String, Object> map) {
		return jobPositionPermissionDao.insert(map);
	}

	@Override
	public int deletePermissionJobPosition(String permissionId, String jobPositionId) {
		return jobPositionPermissionDao.delete(jobPositionId, permissionId);
	}

	@Override
	public List<JobPositionEntity> listPermissionJobPosition(String permissionId) {
		return jobPositionPermissionDao.listPermissionJobPosition(permissionId);
	}

}
