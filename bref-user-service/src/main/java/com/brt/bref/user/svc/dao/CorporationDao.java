package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.CorporationEntity;
import com.github.pagehelper.Page;

public interface CorporationDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int deleteById(String id);

	int update(Map<String, Object> mapUpdate, String id);
	
	CorporationEntity getById(String id);

	Page<CorporationEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike, String dataSchema, Map<String, List<Map<String, Object>>> dataScope);

}
