package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.DataSchemaEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;

public interface PermissionDataSchemaDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int delete(String permissionId, String dataSchemaId);

	List<DataSchemaEntity> listPermissionDataSchema(String permissionId);

	List<PermissionEntity> listDataSchemaPermission(String dataSchemaId);

}
