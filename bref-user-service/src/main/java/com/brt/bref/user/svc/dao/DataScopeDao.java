package com.brt.bref.user.svc.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.DataScopeEntity;
import com.github.pagehelper.Page;

public interface DataScopeDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int deleteById(String id);

	int update(Map<String, Object> mapUpdate, String id);

	Page<DataScopeEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);

}
