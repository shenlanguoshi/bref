package com.brt.bref.user.svc.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.user.feign.entity.DataScopeEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.svc.dao.DataScopeDao;
import com.brt.bref.user.svc.dao.PermissionDataScopeDao;
import com.brt.bref.user.svc.service.DataScopeService;
import com.github.pagehelper.Page;

@Service
public class DataScopeServiceImpl implements DataScopeService{
	
	@Autowired
	private DataScopeDao dataScopeDao;
	
	@Autowired
	private PermissionDataScopeDao permissionDataScopeDao;

	@Override
	public int insert(Map<String, Object> map) {
		return dataScopeDao.insert(map);
	}

	@Override
	public int delete(String id) {
		return dataScopeDao.deleteById(id);
	}

	@Override
	public int update(Map<String, Object> map, String id) {
		return dataScopeDao.update(map, id);
	}

	@Override
	public Page<DataScopeEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		return dataScopeDao.list(mapEqual, mapLike);
	}

	@Override
	public int insertDataScopePermission(Map<String, Object> map) {
		return permissionDataScopeDao.insert(map);
	}

	@Override
	public int deleteDataScopePermission(String dataScopeId, String permissionId) {
		return permissionDataScopeDao.delete(permissionId, dataScopeId);
	}

	@Override
	public List<PermissionEntity> listDataScopePermission(String dataScopeId) {
		return permissionDataScopeDao.listDataScopePermission(dataScopeId);
	}
	
	@Override
	public JSONObject getDataScopeJson() {
		try {
			InputStream dataScopeJsonInputStream = this.getClass().getClassLoader().getResourceAsStream("dataScopeJson.json");
			byte[] bytes = new byte[0];
			bytes = new byte[dataScopeJsonInputStream.available()];
			dataScopeJsonInputStream.read(bytes);
			String str = new String(bytes);
			return JSONObject.parseObject(str);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
