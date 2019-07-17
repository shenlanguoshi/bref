package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.PermissionEntity;

public interface CorporationWorkPermissionDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int delete(String corporationWorkId, String permissionId);

	List<PermissionEntity> listCorporationWorkPermission(String corporationWorkId);

	List<CorporationWorkEntity> listPermissionCorporationWork(String permissionId);

}
