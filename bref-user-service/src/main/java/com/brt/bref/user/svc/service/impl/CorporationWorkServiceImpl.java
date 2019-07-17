package com.brt.bref.user.svc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.brt.bref.user.svc.dao.CorporationWorkDao;
import com.brt.bref.user.svc.dao.CorporationWorkPermissionDao;
import com.brt.bref.user.svc.dao.UserCorporationWorkDao;
import com.brt.bref.user.svc.service.CorporationWorkService;
import com.github.pagehelper.Page;

@Service
public class CorporationWorkServiceImpl implements CorporationWorkService{
	
	@Autowired
	private CorporationWorkDao corporationWorkDao;
	
	@Autowired
	private CorporationWorkPermissionDao corporationWorkPermissionDao;
	
	@Autowired
	private UserCorporationWorkDao userCorporationWorkDao;

	@Override
	public int insert(Map<String, Object> map) {
		return corporationWorkDao.insert(map);
	}

	@Override
	public int delete(String id) {
		return corporationWorkDao.deleteById(id);
	}

	@Override
	public int update(Map<String, Object> map, String id) {
		return corporationWorkDao.update(map, id);
	}
	
	@Override
	public CorporationWorkEntity getById(String id) {
		return corporationWorkDao.getById(id);
	}

	@Override
	public Page<CorporationWorkEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		return corporationWorkDao.list(mapEqual, mapLike);
	}
	
	@Override
	public int insertCorporationWorkUser(Map<String, Object> map) {
		return userCorporationWorkDao.insert(map);
	}

	@Override
	public int deleteCorporationWorkUser(String corporationWorkId, String userId) {
		return userCorporationWorkDao.delete(userId, corporationWorkId);
	}

	@Override
	public List<UserEntity> listCorporationWorkUser(String corporationWorkId) {
		return userCorporationWorkDao.listCorporationWorkUser(corporationWorkId);
	}

	@Override
	public int insertCorporationWorkPermission(Map<String, Object> map) {
		return corporationWorkPermissionDao.insert(map);
	}

	@Override
	public int deleteCorporationWorkPermission(String corporationWorkId, String permissionId) {
		return corporationWorkPermissionDao.delete(corporationWorkId, permissionId);
	}

	@Override
	public List<PermissionEntity> listCorporationWorkPermission(String corporationWorkId) {
		return corporationWorkPermissionDao.listCorporationWorkPermission(corporationWorkId);
	}

}
