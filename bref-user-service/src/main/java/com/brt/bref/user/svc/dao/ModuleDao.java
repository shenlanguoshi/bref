package com.brt.bref.user.svc.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.ModuleEntity;
import com.github.pagehelper.Page;

public interface ModuleDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int deleteById(String id);

	int update(Map<String, Object> mapUpdate, String id);

	Page<ModuleEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);

}
