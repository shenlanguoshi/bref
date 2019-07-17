package com.brt.bref.user.svc.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.brt.bref.user.feign.entity.CorporationEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.github.pagehelper.Page;

public interface CorporationService {

	int insert(Map<String, Object> map);

	int delete(String id);
	
	int update(Map<String, Object> map, String id);
	
	CorporationEntity getById(String id);
	
	Page<CorporationEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);

	JSONArray getTree();
	
	JSONObject getDataFromRedis();
	
	/**********************************************************************/

	int insertCorporationUser(Map<String, Object> map);

	int deleteCorporationUser(String corporationId, String userId);
	
	List<UserEntity> listCorporationUser(String corporationId);
	
	int insertCorporationPermission(Map<String, Object> map);

	int deleteCorporationPermission(String corporationId, String permissionId);

	List<PermissionEntity> listCorporationPermission(String corporationId);

}
