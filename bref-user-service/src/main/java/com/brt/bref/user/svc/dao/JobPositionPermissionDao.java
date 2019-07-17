package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;

public interface JobPositionPermissionDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int delete(String jobPositionId, String permissionId);

	List<PermissionEntity> listJobPositionPermission(String jobPositionId);

	List<JobPositionEntity> listPermissionJobPosition(String permissionId);

}
