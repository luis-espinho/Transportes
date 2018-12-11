package com.akademia.planner.dao.date;

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

@Repository("WeekDayDao")
public class WeekDayDao extends AbstractDao<Integer, WeekDay> {
	
	public WeekDay findWeekDayById(Integer id) {
		return getByKey(id);
    }
	
	public void saveWeekDay(WeekDay weekDay) {
        persist(weekDay);
    }
	
	public void mergeWeekDay(WeekDay weekDay) {
		merge(weekDay);
	}
	
	public void deleteWeekDay(WeekDay weekDay){
		delete(weekDay);
	}
	
	public List<WeekDay> getAllWeekDay(){
		CriteriaQuery<WeekDay> query = createCriteriaQuery();
		Root<WeekDay> root = query.from(WeekDay.class);
		query.select(root);
		Query<WeekDay> q = getSession().createQuery(query);
		List<WeekDay> list = q.getResultList();
		return list;
	}
	
	public List<WeekDay> getWeekDayByName(String name){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<WeekDay> query = criteriaBuilder.createQuery(WeekDay.class);
		Root<WeekDay> root = query.from(WeekDay.class);
		query.select(root);
		query.where(criteriaBuilder.like(root.get("name"), "%" + name.toLowerCase() + "%"));
		Query<WeekDay> q = getSession().createQuery(query);
		List<WeekDay> list = q.getResultList();
		return list;
	}
}