package com.akademia.planner.dao.route;

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
import com.akademia.planner.model.date.WeekDay;
import com.akademia.planner.model.entity.EntityStudent;
import com.akademia.planner.model.entity.EntityType;
import com.akademia.planner.model.entity.MainEntity;
import com.akademia.planner.model.route.Route;
import com.akademia.planner.model.schedule.Schedule;

@Repository("RouteDao")
public class RouteDao extends AbstractDao<Integer, Route> {
	
	public Route findRouteById(Integer id) {
		return getByKey(id);
    }
	
	public void saveRoute(Route route) {
		persist(route);
    }
	
	public void deleteRoute(Route route){
		delete(route);
	}
	
	public void mergeRoute(Route route) {
		merge(route);
	}
	
	public List<Route> getAllRoute(){
		CriteriaQuery<Route> query = createCriteriaQuery();
		Root<Route> root = query.from(Route.class);
		query.select(root);
		Query<Route> q = getSession().createQuery(query);
		List<Route> list = q.getResultList();
		return list;
	}
	
	public List<Route> getRouteByWeekDay(WeekDay weekDay){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Route> query = criteriaBuilder.createQuery(Route.class);
		Root<Route> root = query.from(Route.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("weekDay"), weekDay));
		Query<Route> q = getSession().createQuery(query);
		List<Route> result = q.getResultList();
		return result;
	}
}