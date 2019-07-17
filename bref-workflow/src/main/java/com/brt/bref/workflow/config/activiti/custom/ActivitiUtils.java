package com.brt.bref.workflow.config.activiti.custom;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.brt.bref.user.feign.entity.CorporationWorkEntity;

public class ActivitiUtils {  
    
    public static org.activiti.engine.impl.persistence.entity.UserEntity toActivitiUser(com.brt.bref.user.feign.entity.UserEntity bUser){  
        UserEntity userEntity = new UserEntity();  
        userEntity.setId(bUser.getId());  
        userEntity.setFirstName(bUser.getUsername());  
        userEntity.setLastName(bUser.getUsername());  
        userEntity.setPassword(bUser.getPassword());  
        userEntity.setEmail("");  
        userEntity.setRevision(1);  
        return userEntity;  
    }  
      
    public static GroupEntity toActivitiGroup(CorporationWorkEntity bGroup){  
        GroupEntity groupEntity = new GroupEntity();  
        groupEntity.setRevision(1);  
        groupEntity.setType("assignment");  
  
        groupEntity.setId(bGroup.getId());  
        groupEntity.setName(bGroup.getName());  
        return groupEntity;  
    }  
      
    public static List<org.activiti.engine.identity.Group> toActivitiGroups(List<CorporationWorkEntity> bGroups){  
          
        List<org.activiti.engine.identity.Group> groupEntitys = new ArrayList<org.activiti.engine.identity.Group>();  
          
        for (CorporationWorkEntity bGroup : bGroups) {  
            GroupEntity groupEntity = toActivitiGroup(bGroup);  
            groupEntitys.add(groupEntity);  
        }  
        return groupEntitys;  
    }  
}  