package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.UserEntity;
import com.github.pagehelper.Page;

public interface UserDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int deleteById(String id);

	int update(Map<String, Object> mapUpdate, String id);

	UserEntity getById(String id);

	Page<UserEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike, String dataSchema, Map<String, List<Map<String, Object>>> dataScope);

	UserEntity getUserByUsername(@Param("username") String username);

}
