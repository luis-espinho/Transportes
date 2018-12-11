package com.akademia.planner.dao.general;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<PK extends Serializable, T> {
     
    private final Class<T> persistentClass;
    
    @Autowired
    protected EntityManager entityManager;
    
    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }
    
    @SuppressWarnings("unchecked")
    public AbstractDao(){
        this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    
    public T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }
 
    public void persist(T entity) {
        getSession().persist(entity);
    }
 
    public void delete(T entity) {
        getSession().delete(entity);
    }
    
    public void update(T entity) {
    	getSession().update(entity);
    }
    
    public void merge(T entity) {
    	getSession().merge(entity);
    }
    
    protected CriteriaQuery<T> createCriteriaQuery(){
    	return getSession().getCriteriaBuilder().createQuery(persistentClass);
    }
    
    protected CriteriaBuilder getCriteriaBuilder() {
    	return getSession().getCriteriaBuilder();
    }
}