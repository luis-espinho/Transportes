package com.akademia.planner.dao.address;

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
import com.akademia.planner.model.entity.MainEntity;

@Repository("AddressDao")
public class AddressDao extends AbstractDao<Integer, Address> {
	
	public Address findAddressById(Integer id) {
		return getByKey(id);
    }
	
	public void saveAddress(Address address) {
        persist(address);
    }
	
	public void mergeAddress(Address address) {
		merge(address);
	}
	
	public void deleteAddress(Address address){
		delete(address);
	}
	
	public List<Address> getAllAddress(){
		CriteriaQuery<Address> query = createCriteriaQuery();
		Root<Address> root = query.from(Address.class);
		query.select(root);
		Query<Address> q = getSession().createQuery(query);
		List<Address> list = q.getResultList();
		return list;
	}
	
	public List<Address> getAddressByName(String name){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Address> query = criteriaBuilder.createQuery(Address.class);
		Root<Address> root = query.from(Address.class);
		query.select(root);
		query.where(criteriaBuilder.like(root.get("address"), "%" + name.toLowerCase() + "%"));
		Query<Address> q = getSession().createQuery(query);
		List<Address> list = q.getResultList();
		return list;
		
		//query.where(criteriaBuilder.equal(root.get("morada"), name));
		
		/*
		TypedQuery<Address> query = entityManager.createQuery("SELECT Address FROM morada WHERE morada like '%" + name + "%'", 
			Address.class);
		return query.getResultList();
		*/
	}
	
	public List<Address> getAddressByCity(String city){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Address> query = criteriaBuilder.createQuery(Address.class);
		Root<Address> root = query.from(Address.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("city"), city.toLowerCase()));
		Query<Address> q = getSession().createQuery(query);
		List<Address> list = q.getResultList();
		return list;
	}
	
	public List<Address> getAddressByZipCode(String zip){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Address> query = criteriaBuilder.createQuery(Address.class);
		Root<Address> root = query.from(Address.class);
		query.select(root);
		query.where(criteriaBuilder.like(root.get("zip"), "%" + zip + "%"));
		Query<Address> q = getSession().createQuery(query);
		List<Address> list = q.getResultList();
		return list;
	}
	
	public List<Address> getAddressByArea(Float latitude, Float longitude, Integer radius){
		//radius in latitude
		
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Address> query = criteriaBuilder.createQuery(Address.class);
		Root<Address> root = query.from(Address.class);
		query.select(root);
		
		query.where(criteriaBuilder.lessThanOrEqualTo(root.get("latitude"), latitude));
		
		/*
		query.where(criteriaBuilder.equal(root.get("destinationAddress"), address), 
				criteriaBuilder.equal(root.get("weekDay"), weekDay), 
				criteriaBuilder.greaterThanOrEqualTo(root.get("startingHour"), startingHour),
				criteriaBuilder.lessThanOrEqualTo(root.get("destinationHour"), destinationHour));
		*/
		
		Query<Address> q = getSession().createQuery(query);
		List<Address> list = q.getResultList();
		return list;
	}
}