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
import com.akademia.planner.model.route.RouteSchedule;

@Repository("RouteScheduleDao")
public class RouteScheduleDao extends AbstractDao<Integer, RouteSchedule> {
	
	public RouteSchedule findRouteScheduleById(Integer id) {
		return getByKey(id);
    }
	
	public void saveRouteSchedule(RouteSchedule routeSchedule) {
		persist(routeSchedule);
    }
	
	public void deleteRouteSchedule(RouteSchedule routeSchedule){
		delete(routeSchedule);
	}
	
	public void mergeRouteSchedule(RouteSchedule routeSchedule) {
		merge(routeSchedule);
	}
	
	public List<RouteSchedule> getAllRouteSchedule(){
		CriteriaQuery<RouteSchedule> query = createCriteriaQuery();
		Root<RouteSchedule> root = query.from(RouteSchedule.class);
		query.select(root);
		Query<RouteSchedule> q = getSession().createQuery(query);
		List<RouteSchedule> list = q.getResultList();
		return list;
	}
	
	public List<RouteSchedule> getRouteScheduleByRoute(Route route){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<RouteSchedule> query = criteriaBuilder.createQuery(RouteSchedule.class);
		Root<RouteSchedule> root = query.from(RouteSchedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("route"), route));
		Query<RouteSchedule> q = getSession().createQuery(query);
		List<RouteSchedule> result = q.getResultList();
		return result;
	}
}