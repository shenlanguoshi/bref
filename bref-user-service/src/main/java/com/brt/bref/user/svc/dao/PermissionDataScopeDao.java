package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.DataScopeEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;

public interface PermissionDataScopeDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int delete(String permissionId, String dataScopeId);

	List<DataScopeEntity> listPermissionDataScope(String permissionId);

	List<PermissionEntity> listDataScopePermission(String dataScopeId);

}
