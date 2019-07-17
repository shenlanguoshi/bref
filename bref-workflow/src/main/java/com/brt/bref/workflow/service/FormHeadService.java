package com.brt.bref.workflow.service;

import java.util.Map;

import com.brt.bref.workflow.entity.FormHeadEntity;
import com.github.pagehelper.Page;

public interface FormHeadService {
	int insert(Map<String, Object> map);

	int delete(String id);

	int update(Map<String, Object> map, String id);
	
	FormHeadEntity getById(String id);

	Page<FormHeadEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);
}
