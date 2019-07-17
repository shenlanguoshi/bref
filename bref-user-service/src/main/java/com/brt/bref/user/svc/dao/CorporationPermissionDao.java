package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.CorporationEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;

public interface CorporationPermissionDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int delete(String corporationId, String permissionId);

	List<PermissionEntity> listCorporationPermission(String corporationId);

	List<CorporationEntity> listPermissionCorporation(String permissionId);

}
