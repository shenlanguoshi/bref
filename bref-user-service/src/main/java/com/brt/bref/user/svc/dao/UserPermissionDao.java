package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.PermissionEntity;
import com.brt.bref.user.feign.entity.UserEntity;

public interface UserPermissionDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int delete(String userId, String permissionId);

	List<PermissionEntity> listUserPermission(String userId);

	List<UserEntity> listPermissionUser(String permissionId);

}
