package com.brt.bref.user.svc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.brt.bref.user.svc.dao.JobPositionDao;
import com.brt.bref.user.svc.dao.JobPositionPermissionDao;
import com.brt.bref.user.svc.dao.UserJobPositionDao;
import com.brt.bref.user.svc.service.JobPositionService;
import com.github.pagehelper.Page;

@Service
public class JobPositionServiceImpl implements JobPositionService{
	
	@Autowired
	private JobPositionDao jobPositionDao;
	
	@Autowired
	private UserJobPositionDao userJobPositionDao;
	
	@Autowired
	private JobPositionPermissionDao jobPositionPermissionDao;

	@Override
	public int insert(Map<String, Object> map) {
		return jobPositionDao.insert(map);
	}

	@Override
	public int delete(String id) {
		return jobPositionDao.deleteById(id);
	}

	@Override
	public int update(Map<String, Object> map, String id) {
		return jobPositionDao.update(map, id);
	}

	@Override
	public Page<JobPositionEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		return jobPositionDao.list(mapEqual, mapLike);
	}

	@Override
	public int insertJobPositionUser(Map<String, Object> map) {
		return userJobPositionDao.insert(map);
	}

	@Override
	public int deleteJobPositionUser(String jobPositionId, String userId) {
		return userJobPositionDao.delete(userId, jobPositionId);
	}

	@Override
	public List<UserEntity> listJobPositionUser(String jobPositionId) {
		return userJobPositionDao.listJobPositionUser(jobPositionId);
	}

	@Override
	public int insertJobPositionPermission(Map<String, Object> map) {
		return jobPositionPermissionDao.insert(map);
	}

	@Override
	public int deleteJobPositionPermission(String jobPositionId, String permissionId) {
		return jobPositionPermissionDao.delete(jobPositionId, permissionId);
	}

	@Override
	public List<PermissionEntity> listJobPositionPermission(String jobPositionId) {
		return jobPositionPermissionDao.listJobPositionPermission(jobPositionId);
	}

}
