package com.brt.bref.user.svc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.brt.bref.common.util.TreeUtil;
import com.brt.bref.user.feign.entity.ModuleEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.svc.dao.ModuleDao;
import com.brt.bref.user.svc.dao.PermissionModuleDao;
import com.brt.bref.user.svc.service.ModuleService;
import com.github.pagehelper.Page;

@Service
public class ModuleServiceImpl implements ModuleService{
	@Autowired
	private ModuleDao moduleDao;

	@Autowired
	private PermissionModuleDao permissionModuleDao;
	
	@Override
	public int insert(Map<String, Object> map) {
		return moduleDao.insert(map);
	}

	@Override
	public int delete(String id) {
		return moduleDao.deleteById(id);
	}

	@Override
	public int update(Map<String, Object> map, String id) {
		return moduleDao.update(map, id);
	}
	
	@Override
	public JSONArray getTree(Map<String, Object> mapEqual) {
		List<ModuleEntity> moduleList = moduleDao.list(mapEqual, null);
		JSONArray moduleArray = JSONArray.parseArray(JSONArray.toJSONStringWithDateFormat(moduleList, "yyyy-MM-dd HH:mm:ss"));
		return TreeUtil.jsonArrayToTree(moduleArray, "id", "parentId");
	}

	@Override
	public Page<ModuleEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		return moduleDao.list(mapEqual, mapLike);
	}

	@Override
	public int insertModulePermission(Map<String, Object> map) {
		return permissionModuleDao.insert(map);
	}

	@Override
	public int deleteModulePermission(String moduleId, String permissionId) {
		return permissionModuleDao.delete(permissionId, moduleId);
	}

	@Override
	public List<PermissionEntity> listModulePermission(String moduleId) {
		return permissionModuleDao.listModulePermission(moduleId);
	}
	
}
