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

@Repository("EntityStudentDao")
public class EntityStudentDao extends AbstractDao<Integer, EntityStudent> {
	
	public EntityStudent findEntityStudentById(Integer id) {
		return getByKey(id);
    }
	
	public void saveEntityStudent(EntityStudent entityStudent) {
        persist(entityStudent);
    }
	
	public void deleteEntityStudent(EntityStudent entityStudent){
		delete(entityStudent);
	}
	
	public void mergeEntityStudent(EntityStudent entityStudent) {
		merge(entityStudent);
	}
	
	public List<EntityStudent> getAllEntityStudent(){
		CriteriaQuery<EntityStudent> query = createCriteriaQuery();
		Root<EntityStudent> root = query.from(EntityStudent.class);
		query.select(root);
		Query<EntityStudent> q = getSession().createQuery(query);
		List<EntityStudent> list = q.getResultList();
		return list;
	}
	
	public List<EntityStudent> getEntityStudentByFullName(String name){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<EntityStudent> query = criteriaBuilder.createQuery(EntityStudent.class);
		Root<EntityStudent> root = query.from(EntityStudent.class);
		query.select(root);
		query.where(criteriaBuilder.like(root.get("fullName"), "%" + name.toLowerCase() + "%"));
		Query<EntityStudent> q = getSession().createQuery(query);
		List<EntityStudent> list = q.getResultList();
		return list;
	}
	
	public EntityStudent getEntityStudentByMainEntity(MainEntity mainEntity){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<EntityStudent> query = criteriaBuilder.createQuery(EntityStudent.class);
		Root<EntityStudent> root = query.from(EntityStudent.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("mainEntity"), mainEntity));
		Query<EntityStudent> q = getSession().createQuery(query);
		EntityStudent result = q.uniqueResult();
		return result;
	}
}