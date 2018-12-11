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
import com.akademia.planner.model.entity.EntityVehicle;
import com.akademia.planner.model.entity.MainEntity;

@Repository("EntityVehicleDao")
public class EntityVehicleDao extends AbstractDao<Integer, EntityVehicle> {
	
	public EntityVehicle findEntityVehicleById(Integer id) {
		return getByKey(id);
    }
	
	public void saveEntityVehicle(EntityVehicle entityVehicle) {
        persist(entityVehicle);
    }
	
	public void deleteEntityVehicle(EntityVehicle entityVehicle){
		delete(entityVehicle);
	}
	
	public void mergeEntityVehicle(EntityVehicle entityVehicle) {
		merge(entityVehicle);
	}
	
	public List<EntityVehicle> getAllEntityVehicle(){
		CriteriaQuery<EntityVehicle> query = createCriteriaQuery();
		Root<EntityVehicle> root = query.from(EntityVehicle.class);
		query.select(root);
		Query<EntityVehicle> q = getSession().createQuery(query);
		List<EntityVehicle> list = q.getResultList();
		return list;
	}
	
	public List<EntityVehicle> getEntityVehicleByName(String name){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<EntityVehicle> query = criteriaBuilder.createQuery(EntityVehicle.class);
		Root<EntityVehicle> root = query.from(EntityVehicle.class);
		query.select(root);
		query.where(criteriaBuilder.like(root.get("name"), "%" + name.toLowerCase() + "%"));
		Query<EntityVehicle> q = getSession().createQuery(query);
		List<EntityVehicle> list = q.getResultList();
		return list;
	}
	
	public EntityVehicle getEntityVehicleByMainEntity(MainEntity mainEntity){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<EntityVehicle> query = criteriaBuilder.createQuery(EntityVehicle.class);
		Root<EntityVehicle> root = query.from(EntityVehicle.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("mainEntity"), mainEntity));
		Query<EntityVehicle> q = getSession().createQuery(query);
		EntityVehicle result = q.uniqueResult();
		return result;
	}
}