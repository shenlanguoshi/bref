package com.brt.bref.workflow.config.activiti.custom;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.brt.bref.user.feign.api.CorporationWorkFeign;
import com.brt.bref.user.feign.api.UserFeign;
import com.brt.bref.user.feign.entity.CorporationWorkEntity;

@Service
public class CustomGroupEntityManager extends GroupEntityManager {

	@Autowired  
	private CorporationWorkFeign corporationWorkFeign;
	
	@Autowired
	private UserFeign userFeign;

	public GroupEntity findGroupById(final String groupCode) {  
		if (groupCode == null)  
			return null;  

		try {  
			CorporationWorkEntity corporationWorkEntity = (CorporationWorkEntity) corporationWorkFeign.getById(groupCode).get("data");

			GroupEntity e = new GroupEntity();  
			e.setRevision(1);  

			// activiti有3种预定义的组类型：security-role、assignment、user  
			// 如果使用Activiti  
			// Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务  
			e.setType("assignment");  

			e.setId(corporationWorkEntity.getId());  
			e.setName(corporationWorkEntity.getName());  
			return e;  
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
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {  
		throw new RuntimeException("not implement method.");  
	}  

	@Override  
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {  
		throw new RuntimeException("not implement method.");  
	}  
}  
