package com.akademia.planner.dao.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.websocket.Session;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.akademia.planner.dao.general.AbstractDao;
import com.akademia.planner.model.address.Address;
import com.akademia.planner.model.entity.EntityStudent;
import com.akademia.planner.model.entity.EntityType;

@Repository("EntityTypeDao")
public class EntityTypeDao extends AbstractDao<Integer, EntityType> {
	
	public EntityType findEntityTypeById(Integer id) {
		return getByKey(id);
    }
	
	public void saveEntityType(EntityType entityType) {
        persist(entityType);
    }
	
	public void deleteEntityType(EntityType entityType){
		delete(entityType);
	}
	
	public void mergeEntityType(EntityType entityType) {
		merge(entityType);
	}
	
	public List<EntityType> getAllEntityType(){
		CriteriaQuery<EntityType> query = createCriteriaQuery();
		Root<EntityType> root = query.from(EntityType.class);
		query.select(root);
		Query<EntityType> q = getSession().createQuery(query);
		List<EntityType> list = q.getResultList();
		return list;
	}
	
}