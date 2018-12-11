package com.akademia.planner.dao.schedule;

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
import com.akademia.planner.model.schedule.Schedule;

@Repository("ScheduleDao")
public class ScheduleDao extends AbstractDao<Integer, Schedule> {
	
	public Schedule findScheduleById(Integer id) {
		return getByKey(id);
    }
	
	public void saveSchedule(Schedule schedule) {
        persist(schedule);
    }
	
	public void deleteSchedule(Schedule schedule){
		delete(schedule);
	}
	
	public void mergeSchedule(Schedule schedule) {
		merge(schedule);
	}
	
	public List<Schedule> getAllSchedule(){
		CriteriaQuery<Schedule> query = createCriteriaQuery();
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> list = q.getResultList();
		return list;
	}
	
	public List<Schedule> getScheduleByStartingAddress(Address address){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("startingAddress"), address));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByWeekDay(WeekDay weekDay){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("weekDay"), weekDay));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByDestinationAddress(Address address){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("destinationAddress"), address));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByStartingAddressAndWeekDay(Address address, WeekDay weekDay){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("startingAddress"), address), 
				criteriaBuilder.equal(root.get("weekDay"), weekDay));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByDestinationAddressAndWeekDay(Address address, WeekDay weekDay){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("destinationAddress"), address), 
				criteriaBuilder.equal(root.get("weekDay"), weekDay));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByTimeInterval(String startingHour, String destinationHour){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.greaterThanOrEqualTo(root.get("startingHour"), startingHour),
				criteriaBuilder.lessThanOrEqualTo(root.get("destinationHour"), destinationHour));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByStartingAddressAndWeekDayAndTimeInterval(Address address, WeekDay weekDay,
			String startingHour, String destinationHour){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("startingAddress"), address), 
				criteriaBuilder.equal(root.get("weekDay"), weekDay), 
				criteriaBuilder.greaterThanOrEqualTo(root.get("startingHour"), startingHour),
				criteriaBuilder.lessThanOrEqualTo(root.get("destinationHour"), destinationHour));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByDestinationAddressAndWeekDayAndTimeInterval(Address address, WeekDay weekDay,
			String startingHour, String destinationHour){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("destinationAddress"), address), 
				criteriaBuilder.equal(root.get("weekDay"), weekDay), 
				criteriaBuilder.greaterThanOrEqualTo(root.get("startingHour"), startingHour),
				criteriaBuilder.lessThanOrEqualTo(root.get("destinationHour"), destinationHour));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	//TODO: address + weekDay + hour interval + zip
	
	public List<Schedule> getScheduleByEntity(MainEntity mainEntity){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("mainEntity"), mainEntity));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByEntityAndWeekDay(MainEntity mainEntity, WeekDay weekDay){
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("mainEntity"), mainEntity), 
				criteriaBuilder.equal(root.get("weekDay"), weekDay));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByWeekDayAndZip(WeekDay weekDay, String zip){
		//TODO: distinction between starting and destination
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("weekDay"), weekDay), 
				criteriaBuilder.like(root.get("startingAddress").get("zip"), "%" + zip + "%"),
				criteriaBuilder.like(root.get("destinationAddress").get("zip"), "%" + zip + "%"));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByWeekDayAndTimeIntervalAndStartingAddressZip(WeekDay weekDay, String zip, 
			String startingHour, String destinationHour){
		//TODO: distinction between starting and destination
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("weekDay"), weekDay), 
				criteriaBuilder.like(root.get("startingAddress").get("zip"), "%" + zip + "%"),
				criteriaBuilder.greaterThanOrEqualTo(root.get("startingHour"), startingHour),
				criteriaBuilder.lessThanOrEqualTo(root.get("destinationHour"), destinationHour));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	public List<Schedule> getScheduleByWeekDayAndTimeIntervalAndDestinationAddressZip(WeekDay weekDay, String zip,
			String startingHour, String destinationHour){
		//TODO: distinction between starting and destination
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<Schedule> query = criteriaBuilder.createQuery(Schedule.class);
		Root<Schedule> root = query.from(Schedule.class);
		query.select(root);
		query.where(criteriaBuilder.equal(root.get("weekDay"), weekDay), 
				criteriaBuilder.like(root.get("destinationAddress").get("zip"), "%" + zip + "%"),
				criteriaBuilder.greaterThanOrEqualTo(root.get("startingHour"), startingHour),
				criteriaBuilder.lessThanOrEqualTo(root.get("destinationHour"), destinationHour));
		Query<Schedule> q = getSession().createQuery(query);
		List<Schedule> result = q.getResultList();
		return result;
	}
	
	//TODO: get schedule by zip code interval
}