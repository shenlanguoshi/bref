package com.brt.bref.workflow.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brt.bref.workflow.entity.FormDataEntity;
import com.brt.bref.workflow.dao.FormDataDao;
import com.brt.bref.workflow.service.FormDataService;
import com.github.pagehelper.Page;

@Service
public class FormDataServiceImpl implements FormDataService {
	@Autowired
	private FormDataDao formDataDao;
	
	@Override
	public int insert(Map<String, Object> map) {
		return formDataDao.insert(map);
	}

	@Override
	public int delete(String id) {
		return formDataDao.deleteById(id);
	}

	@Override
	public int update(Map<String, Object> map, String id) {
		return formDataDao.update(map, id);
	}
	
	@Override
	public FormDataEntity getById(String id) {
		return formDataDao.getById(id);
	}

	@Override
	public Page<FormDataEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		return formDataDao.list(mapEqual, mapLike);
	}
	
}
