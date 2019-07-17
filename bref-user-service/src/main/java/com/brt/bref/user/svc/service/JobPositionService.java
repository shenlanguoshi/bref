package com.brt.bref.user.svc.service;

import java.util.List;
import java.util.Map;

import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;
import com.github.pagehelper.Page;

public interface JobPositionService {

	int insert(Map<String, Object> map);

	int delete(String id);

	int update(Map<String, Object> map, String id);

	Page<JobPositionEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);

	int insertJobPositionUser(Map<String, Object> map);

	int deleteJobPositionUser(String jobPositionId, String userId);

	List<UserEntity> listJobPositionUser(String jobPositionId);

	int insertJobPositionPermission(Map<String, Object> map);

	int deleteJobPositionPermission(String jobPositionId, String permissionId);

	List<PermissionEntity> listJobPositionPermission(String jobPositionId);

}
