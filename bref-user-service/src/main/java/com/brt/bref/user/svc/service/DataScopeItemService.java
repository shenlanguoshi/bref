package com.brt.bref.user.svc.service;

import java.util.Map;

import com.brt.bref.user.feign.entity.DataScopeItemEntity;
import com.github.pagehelper.Page;

public interface DataScopeItemService {
	int insert(Map<String, Object> map);

	int delete(String id);

	int update(Map<String, Object> map, String id);

	Page<DataScopeItemEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike);
}
