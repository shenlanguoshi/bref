package com.brt.bref.user.svc.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.DataScopeItemEntity;
import com.github.pagehelper.Page;

public interface DataScopeItemDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int deleteById(String id);

	int update(Map<String, Object> mapUpdate, String id);

	Page<DataScopeItemEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);

}
