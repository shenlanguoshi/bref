package com.brt.bref.user.svc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.brt.bref.user.feign.entity.JobPositionEntity;
import com.brt.bref.user.feign.entity.UserEntity;

public interface UserJobPositionDao {

	int insert(@Param("mapInsert") Map<String, Object> mapInsert);

	int delete(String userId, String jobPositionId);

	List<JobPositionEntity> listUserJobPosition(String userId);

	List<UserEntity> listJobPositionUser(String jobPositionId);

}
