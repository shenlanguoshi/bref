package com.brt.bref.mdm.svc.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brt.bref.mdm.feign.entity.CompanyEntity;
import com.brt.bref.mdm.svc.dao.CompanyDao;
import com.brt.bref.mdm.svc.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public CompanyEntity getCompanyByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		return companyDao.getCompanyByName(name);
	}

}
