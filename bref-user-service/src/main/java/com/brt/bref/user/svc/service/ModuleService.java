package com.brt.bref.user.svc.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.brt.bref.user.feign.entity.ModuleEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.github.pagehelper.Page;

public interface ModuleService {

	int insert(Map<String, Object> map);

	int delete(String id);

	int update(Map<String, Object> map, String id);
	
	JSONArray getTree(Map<String, Object> mapEqual);

	Page<ModuleEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);

	int insertModulePermission(Map<String, Object> mapEqual);

	int deleteModulePermission(String moduleId, String permissionId);

	List<PermissionEntity> listModulePermission(String moduleId);

}
