package com.brt.bref.mdm.svc.dao;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.common.dao.BaseDao;
import com.brt.bref.mdm.feign.entity.CompanyEntity;

public interface CompanyDao extends BaseDao<CompanyEntity> {

	CompanyEntity getCompanyByName(@Param("name") String name);
}
