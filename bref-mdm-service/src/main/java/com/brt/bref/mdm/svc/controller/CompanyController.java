package com.brt.bref.mdm.svc.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.brt.bref.common.util.ResponseUtil;
import com.brt.bref.common.util.SecurityUtils;
import com.brt.bref.mdm.feign.entity.CompanyEntity;
import com.brt.bref.mdm.svc.service.CompanyService;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;

	@RequestMapping(value = "/info/{name}", method = RequestMethod.GET)
	public JSONObject getCompanyByName(@PathVariable String name) {
		System.out.println(SecurityUtils.getUser().getString("username"));
		if (StringUtils.isBlank(name)) {
			return ResponseUtil.responseResult(false, "公司名不能为空", null);
		}
		CompanyEntity company = companyService.getCompanyByName(name);
		if (company == null) {
			return ResponseUtil.responseResult(false, "公司不存在", null);
		}
		return ResponseUtil.responseResult(true, "获取公司成功", company);
	}
}
