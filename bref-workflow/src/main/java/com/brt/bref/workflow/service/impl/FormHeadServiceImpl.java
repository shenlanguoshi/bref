package com.brt.bref.workflow.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brt.bref.workflow.entity.FormHeadEntity;
import com.brt.bref.common.util.UUIDUtils;
import com.brt.bref.workflow.dao.FormHeadDao;
import com.brt.bref.workflow.service.FormHeadService;
import com.github.pagehelper.Page;

@Service
public class FormHeadServiceImpl implements FormHeadService {
	@Autowired
	private FormHeadDao formHeadDao;
	
	@Override
	public int insert(Map<String, Object> map) {
		return formHeadDao.insert(map);
	}

	@Override
	public int delete(String id) {
		return formHeadDao.deleteById(id);
	}

	@Override
	public int update(Map<String, Object> map, String id) {
		FormHeadEntity formHead = formHeadDao.getById(id);
		formHeadDao.deleteById(id);
		Map<String, Object> insertMap = new HashMap<>();
		BeanUtils.copyProperties(formHead, insertMap);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			insertMap.put(entry.getKey(), entry.getValue());
		}
		insertMap.put("id", UUIDUtils.getUUID());
		insertMap.remove("createAt");
		insertMap.remove("updateAt");
		return formHeadDao.insert(insertMap);
	}
	
	@Override
	public FormHeadEntity getById(String id) {
		return formHeadDao.getById(id);
	}

	@Override
	public Page<FormHeadEntity> list(Map<String, Object> mapEqual, Map<String, Object> mapLike) {
		return formHeadDao.list(mapEqual, mapLike);
	}
	
}
