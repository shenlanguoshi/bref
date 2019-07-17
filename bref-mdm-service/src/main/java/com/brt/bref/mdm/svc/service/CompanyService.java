package com.brt.bref.mdm.svc.service;

import com.brt.bref.mdm.feign.entity.CompanyEntity;

public interface CompanyService {

	CompanyEntity getCompanyByName(String name);
}
