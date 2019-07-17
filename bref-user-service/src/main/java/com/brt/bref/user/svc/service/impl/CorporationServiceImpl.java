package com.brt.bref.user.svc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.brt.bref.common.util.RedisDataPermissionUtil;
import com.brt.bref.common.util.SecurityUtils;
import com.brt.bref.common.util.StringUtil;
import com.brt.bref.common.util.TreeUtil;
import com.brt.bref.user.feign.entity.CorporationEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.brt.bref.user.svc.dao.CorporationDao;
import com.brt.bref.user.svc.dao.CorporationPermissionDao;
import com.brt.bref.user.svc.dao.UserCorporationDao;
import com.brt.bref.user.svc.service.CorporationService;
import com.github.pagehelper.Page;

@Service
public class CorporationServiceImpl implements CorporationService {
	@Autowired
	private CorporationDao corporationDao;
	
	@Autowired
	private UserCorporationDao userCorporationDao;
	
	@Autowired
	private CorporationPermissionDao corporationPermissionDao;
	
	@Override
	@CacheEvict(value = "common", key = "'corporationTree'")
	public int insert(Map<String, Object> map) {
		return corporationDao.insert(map);
	}

	@Override
	@CacheEvict(value = "common", key = "'corporationTree'")
	public int delete(String id) {
		return corporationDao.deleteById(id);
	}

	@Override
	@CacheEvict(value = "common", key = "'corporationTree'")
	public int update(Map<String, Object> map, String id) {
		return corporationDao.update(map, id);
	}

	@Override
	public CorporationEntity getById(String id) {
		return corporationDao.getById(id);
	}

	@Override
	public Page<CorporationEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		JSONArray corporationTree = getTree();
		String dataSchema = SecurityUtils.getDataSchema().get("corporation");
		Map<String, List<Map<String, Object>>> dataScope = SecurityUtils.getDataScope().get("corporation");
		if(dataScope!= null && dataScope.containsKey("sys_corporation.id")) {
			JSONObject result = RedisDataPermissionUtil.filterData(corporationTree, dataSchema, dataScope);
			if(result.isEmpty()) {
				return null;
			}
			List<Map<String, Object>> idDataScope = new ArrayList<>();
			Map<String, Object> idDataScopeMap = new HashMap<>();
			idDataScopeMap.put("columnName", "sys_corporation.id");
			idDataScopeMap.put("operator", "in");
			String ids[] = (String[]) result.get("ids");
			String sql = StringUtil.idsToSql(ids);
			idDataScopeMap.put("dataScope", sql);
			dataScope.put("sys_corporation.id", idDataScope);
		}
		return corporationDao.list(mapEqual, mapLike, dataSchema, dataScope);
	}

	/**
	 * @author 方杰
	 * @date 2019年1月12日
	 * @return 完整树
	 * @description tag true:刷新redis false:从redis读数据，没有则从数据库获取 
	 */
	@Override
	@Cacheable(value = "common", key = "'corporationTree'")
	public JSONArray getTree() {
		Map<String, Object> mapEqual = new HashMap<String, Object>();
		mapEqual.put("sys_corporation.del_flag", false);
		List<CorporationEntity> corporationList = corporationDao.list(mapEqual, null, null, null);
		JSONArray corporationArray = JSONArray.parseArray(JSONArray.toJSONStringWithDateFormat(corporationList, "yyyy-MM-dd HH:mm:ss"));
		return TreeUtil.jsonArrayToTree(corporationArray, "id", "parentId");
	}

	/**
	 * @author 方杰
	 * @date 2019年1月12日
	 * @return JSONObject {list tree ids}
	 * @description 从redis获取数据 
	 */
	@Override
	public JSONObject getDataFromRedis() {
		JSONArray corporationTree = getTree();
		Map<String, String> dataSchema = SecurityUtils.getDataSchema();
		Map<String, Map<String, List<Map<String, Object>>>> dataScope = SecurityUtils.getDataScope();
		JSONObject result = RedisDataPermissionUtil.filterData(corporationTree, dataSchema.get("corporation"), dataScope.get("corporation"));
		return result;
	}
	
	/**********************************************************************/

	@Override
	public int insertCorporationUser(Map<String, Object> map) {
		return userCorporationDao.insert(map);
	}

	@Override
	public int deleteCorporationUser(String corporationId, String userId) {
		return userCorporationDao.delete(userId, corporationId);
	}
	
	@Override
	public List<UserEntity> listCorporationUser(String corporationId) {
		return userCorporationDao.listCorporationUser(corporationId);
	}
	
	//-------------------------------------------------------------------------------------------

	@Override
	public int insertCorporationPermission(Map<String, Object> map) {
		return corporationPermissionDao.insert(map);
	}

	@Override
	public int deleteCorporationPermission(String corporationId, String permissionId) {
		return corporationPermissionDao.delete(corporationId, permissionId);
	}

	@Override
	public List<PermissionEntity> listCorporationPermission(String corporationId) {
		return corporationPermissionDao.listCorporationPermission(corporationId);
	}

}
