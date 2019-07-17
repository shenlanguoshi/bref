package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.ModuleEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;

public interface PermissionModuleDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int delete(String permissionId, String moduleId);

	List<ModuleEntity> listPermissionModule(String permissionId);

	List<PermissionEntity> listModulePermission(String moduleId);

}
