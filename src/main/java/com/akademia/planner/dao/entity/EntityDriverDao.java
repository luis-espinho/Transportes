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
import com.akademia.planner.model.entity.EntityDriver;
import com.akademia.planner.model.entity.EntityStudent;
import com.akademia.planner.model.entity.EntityType;
import com.akademia.planner.model.entity.MainEntity;

@Repository("EntityDriverDao")
public class EntityDriverDao extends AbstractDao<Integer, EntityDriver> {
	
	public EntityDriver findEntityDriverById(Integer id) {
		return getByKey(id);
    }
	
	public void saveEntityDriver(EntityDriver entityDriver) {
        persist(entityDriver);
    }
	
	public void deleteEntityDriver(EntityDriver entityDriver){
		delete(entityDriver);
	}
	
	public void mergeEntityDriver(EntityDriver entityDriver) {
		merge(entityDriver);
	}
	
	public List<EntityDriver> getAllEntityDriver(){
		CriteriaQuery<EntityDriver> query = createCriteriaQuery();
		Root<EntityDriver> root = query.from(EntityDriver.class);
		query.select(root);
		Query<EntityDriver> q = getSession().createQuery(query);
		List<EntityDriver> list = q.getResultList();
		return list;
	}
	
	public List<EntityDriver> getEntityDriverByFullName(String name){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<EntityDriver> query = criteriaBuilder.createQuery(EntityDriver.class);
		Root<EntityDriver> root = query.from(EntityDriver.class);
		query.select(root);
		query.where(criteriaBuilder.like(root.get("fullName"), "%" + name.toLowerCase() + "%"));
		Query<EntityDriver> q = getSession().createQuery(query);
		List<EntityDriver> list = q.getResultList();
		return list;
	}
	
	public EntityDriver getEntityDriverByMainEntity(MainEntity mainEntity){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<EntityDriver> query = criteriaBuilder.createQuery(EntityDriver.class);
		Root<EntityDriver> root = query.from(EntityDriver.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("mainEntity"), mainEntity));
		Query<EntityDriver> q = getSession().createQuery(query);
		EntityDriver result = q.uniqueResult();
		return result;
	}
}