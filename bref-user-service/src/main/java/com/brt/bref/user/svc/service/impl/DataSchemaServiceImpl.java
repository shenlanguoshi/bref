package com.brt.bref.user.svc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brt.bref.user.feign.entity.DataSchemaEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.svc.dao.DataSchemaDao;
import com.brt.bref.user.svc.dao.PermissionDataSchemaDao;
import com.brt.bref.user.svc.service.DataSchemaService;
import com.github.pagehelper.Page;

@Service
public class DataSchemaServiceImpl implements DataSchemaService{
	
	@Autowired
	private DataSchemaDao dataSchemaDao;
	
	@Autowired
	private PermissionDataSchemaDao permissionDataSchemaDao;
	
	@Override
	public int insert(Map<String, Object> map) {
		return dataSchemaDao.insert(map);
	}

	@Override
	public int delete(String id) {
		return dataSchemaDao.deleteById(id);
	}

	@Override
	public int update(Map<String, Object> map, String id) {
		return dataSchemaDao.update(map, id);
	}

	@Override
	public Page<DataSchemaEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		return dataSchemaDao.list(mapEqual, mapLike);
	}

	@Override
	public int insertDataSchemaPermission(Map<String, Object> map) {
		return permissionDataSchemaDao.insert(map);
	}

	@Override
	public int deleteDataSchemaPermission(String dataSchemaId, String permissionId) {
		return permissionDataSchemaDao.delete(permissionId, dataSchemaId);
	}

	@Override
	public List<PermissionEntity> listDataSchemaPermission(String dataSchemaId) {
		return permissionDataSchemaDao.listDataSchemaPermission(dataSchemaId);
	}
}
