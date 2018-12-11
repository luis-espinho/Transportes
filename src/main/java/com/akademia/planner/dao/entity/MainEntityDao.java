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
import com.akademia.planner.model.entity.MainEntity;

@Repository("MainEntityDao")
public class MainEntityDao extends AbstractDao<Integer, MainEntity> {
	
	public MainEntity findMainEntityById(Integer id) {
		return getByKey(id);
    }
	
	public void saveMainEntity(MainEntity mainEntity) {
        persist(mainEntity);
    }
	
	public void deleteMainEntity(MainEntity mainEntity){
		delete(mainEntity);
	}
	
	public void mergeMainEntity(MainEntity mainEntity) {
		merge(mainEntity);
	}
	
	public List<MainEntity> getAllMainEntity(){
		CriteriaQuery<MainEntity> query = createCriteriaQuery();
		Root<MainEntity> root = query.from(MainEntity.class);
		query.select(root);
		Query<MainEntity> q = getSession().createQuery(query);
		List<MainEntity> list = q.getResultList();
		return list;
	}
	
	public List<MainEntity> getMainEntityByDisplayName(String name){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MainEntity> query = criteriaBuilder.createQuery(MainEntity.class);
		Root<MainEntity> root = query.from(MainEntity.class);
		query.select(root);
		query.where(criteriaBuilder.like(root.get("displayName"), "%" + name.toLowerCase() + "%"));
		Query<MainEntity> q = getSession().createQuery(query);
		List<MainEntity> list = q.getResultList();
		return list;
	}
	
	public List<MainEntity> getMainEntityByEntityType(EntityType entityType) {
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<MainEntity> query = criteriaBuilder.createQuery(MainEntity.class);
		Root<MainEntity> root = query.from(MainEntity.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("entityType"), entityType));
		Query<MainEntity> q = getSession().createQuery(query);
		List<MainEntity> result = q.getResultList();
		return result;
	}
}