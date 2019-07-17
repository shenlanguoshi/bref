package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.UserEntity;

public interface UserCorporationWorkDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int delete(String userId, String corporationWorkId);

	List<CorporationWorkEntity> listUserCorporationWork(String userId);

	List<UserEntity> listCorporationWorkUser(String corporationWorkId);

}
