package com.brt.bref.user.svc.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.user.feign.entity.DataScopeEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.github.pagehelper.Page;

public interface DataScopeService {
	int insert(Map<String, Object> map);

	int delete(String id);

	int update(Map<String, Object> map, String id);

	Page<DataScopeEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);
	
	int insertDataScopePermission(Map<String, Object> map);

	int deleteDataScopePermission(String dataScopeId, String permissionId);

	List<PermissionEntity> listDataScopePermission(String dataScopeId);
	
	JSONObject getDataScopeJson(); 
	
}
