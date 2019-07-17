package com.brt.bref.workflow.config.activiti.custom;

import org.activiti.engine.impl.cfg.IdGenerator;

import com.brt.bref.common.util.UUIDUtils;

public class CustomIdGenerator implements IdGenerator{

	@Override
	public String getNextId() {
		return UUIDUtils.getUUID();
	}

}
