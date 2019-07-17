package com.brt.bref.workflow.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.workflow.entity.FormDataEntity;
import com.github.pagehelper.Page;

public interface FormDataDao {
	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int deleteById(String id);

	int update(Map<String, Object> mapUpdate, String id);
	
	FormDataEntity getById(String id);

	Page<FormDataEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);
}
