package com.brt.bref.user.svc.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.github.pagehelper.Page;

public interface CorporationWorkDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int deleteById(String id);

	int update(Map<String, Object> mapUpdate, String id);
	
	CorporationWorkEntity getById(String id);

	Page<CorporationWorkEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);

}
