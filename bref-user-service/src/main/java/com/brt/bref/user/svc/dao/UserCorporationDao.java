package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.CorporationEntity;
import com.brt.bref.user.feign.entity.UserEntity;

public interface UserCorporationDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int delete(String userId, String corporationId);

	List<CorporationEntity> listUserCorporation(String userId);

	List<UserEntity> listCorporationUser(String corporationId);

}
