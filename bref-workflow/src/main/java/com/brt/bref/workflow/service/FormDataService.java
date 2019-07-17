package com.brt.bref.workflow.service;

import java.util.Map;

import com.brt.bref.workflow.entity.FormDataEntity;
import com.github.pagehelper.Page;

public interface FormDataService {
	int insert(Map<String, Object> map);

	int delete(String id);

	int update(Map<String, Object> map, String id);
	
	FormDataEntity getById(String id);

	Page<FormDataEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);
}
