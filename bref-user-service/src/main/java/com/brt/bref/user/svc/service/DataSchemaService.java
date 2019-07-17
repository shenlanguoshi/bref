package com.brt.bref.user.svc.service;

import java.util.List;
import java.util.Map;

import com.brt.bref.user.feign.entity.DataSchemaEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.github.pagehelper.Page;

public interface DataSchemaService {
	int insert(Map<String, Object> map);

	int delete(String id);

	int update(Map<String, Object> map, String id);

	Page<DataSchemaEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);
	
	int insertDataSchemaPermission(Map<String, Object> map);

	int deleteDataSchemaPermission(String dataSchemaId, String permissionId);

	List<PermissionEntity> listDataSchemaPermission(String dataSchemaId);
}
