package com.brt.bref.workflow.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.workflow.entity.FormHeadEntity;
import com.github.pagehelper.Page;

public interface FormHeadDao {
	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int deleteById(String id);

	int update(Map<String, Object> mapUpdate, String id);
	
	FormHeadEntity getById(String id);

	Page<FormHeadEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);
}
