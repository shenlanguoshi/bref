package com.brt.bref.user.svc.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brt.bref.user.feign.entity.DataScopeItemEntity;
import com.brt.bref.user.svc.dao.DataScopeItemDao;
import com.brt.bref.user.svc.service.DataScopeItemService;
import com.github.pagehelper.Page;

@Service
public class DataScopeItemServiceImpl implements DataScopeItemService {
	@Autowired
	private DataScopeItemDao dataScopeItemDao;
	
	@Override
	public int insert(Map<String, Object> map) {
		return dataScopeItemDao.insert(map);
	}

	@Override
	public int delete(String id) {
		return dataScopeItemDao.deleteById(id);
	}

	@Override
	public int update(Map<String, Object> map, String id) {
		return dataScopeItemDao.update(map, id);
	}

	@Override
	public Page<DataScopeItemEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		return dataScopeItemDao.list(mapEqual, mapLike);
	}

}
