package com.brt.bref.workflow.config.activiti.custom;

import java.util.ArrayList;
import java.util.List;  


import org.activiti.engine.identity.Group;  
import org.activiti.engine.identity.User;  
import org.activiti.engine.impl.Page;  
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.IdentityInfoEntity;  
import org.activiti.engine.impl.persistence.entity.UserEntityManager;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.dao.EmptyResultDataAccessException;  
import org.springframework.stereotype.Service;

import com.brt.bref.user.feign.api.UserFeign;
import com.brt.bref.user.feign.entity.CorporationWorkEntity;
import com.brt.bref.user.feign.entity.UserEntity;  
  
@Service  
public class CustomUserEntityManager extends UserEntityManager {  
	@Autowired
	private UserFeign userFeign;
  
    @Override  
    public org.activiti.engine.impl.persistence.entity.UserEntity findUserById(final String userCode) {  
        if (userCode == null)  
            return null;  
  
        try {  
        	org.activiti.engine.impl.persistence.entity.UserEntity userEntity = null;  
            UserEntity bUser = (UserEntity) userFeign.getById(userCode).get("data");
            userEntity = ActivitiUtils.toActivitiUser(bUser);  
            return userEntity;  
        } catch (EmptyResultDataAccessException e) {  
            return null;  
        }  
    }  
  
    @SuppressWarnings("unchecked")
	@Override  
    public List<Group> findGroupsByUser(final String userCode) {  
    	if (userCode == null)
			return null;

		List<CorporationWorkEntity> bGroupList = (List<CorporationWorkEntity>) userFeign.listUserCorporationWork(userCode).get("data");

		List<Group> gs = new ArrayList<Group>();
		GroupEntity g;
		for (CorporationWorkEntity bGroup : bGroupList) {
			g = new GroupEntity();
			g.setRevision(1);
			g.setType("assignment");

			g.setId(bGroup.getId());
			g.setName(bGroup.getName());
			gs.add(g);
		}
		return gs;
    }  
  
    @Override  
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public IdentityInfoEntity findUserInfoByUserIdAndKey(String userId,  
            String key) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public List<String> findUserInfoKeysByUserIdAndType(String userId,  
            String type) {  
        throw new RuntimeException("not implement method.");  
    }  
  
    @Override  
    public long findUserCountByQueryCriteria(UserQueryImpl query) {  
        throw new RuntimeException("not implement method.");  
    }  
}  
