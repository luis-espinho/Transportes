package com.akademia.planner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akademia.planner.dao.address.AddressDao;
import com.akademia.planner.dao.date.WeekDayDao;
import com.akademia.planner.dao.entity.EntityDriverDao;
import com.akademia.planner.dao.entity.EntityStudentDao;
import com.akademia.planner.dao.entity.EntityTypeDao;
import com.akademia.planner.dao.entity.EntityVehicleDao;
import com.akademia.planner.dao.entity.MainEntityDao;
import com.akademia.planner.dao.schedule.ScheduleDao;
import com.akademia.planner.model.address.Address;
import com.akademia.planner.model.date.WeekDay;
import com.akademia.planner.model.entity.EntityDriver;
import com.akademia.planner.model.entity.EntityStudent;
import com.akademia.planner.model.entity.EntityType;
import com.akademia.planner.model.entity.EntityVehicle;
import com.akademia.planner.model.entity.MainEntity;
import com.akademia.planner.model.schedule.Schedule;

@Service("scheduleService")
@Transactional
public class ScheduleService {
	
	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	private WeekDayDao weekDayDao;
	
	public Schedule findScheduleById(int id){
		Schedule schedule = scheduleDao.findScheduleById(id);
		return schedule;
	}
	
	public Schedule createSchedule(Schedule schedule) {
		scheduleDao.persist(schedule);
		return schedule;
	}
	
	public List<Schedule> getAllSchedule(){
		return scheduleDao.getAllSchedule();
	}
	
	public void deleteSchedule(int id) {
		Schedule schedule = scheduleDao.findScheduleById(id);
		if (schedule != null) {
			scheduleDao.delete(schedule);
		}
	}
	
	public void updateSchedule(int updateId, Schedule schedule) {
		Schedule updateSchedule = scheduleDao.findScheduleById(updateId);
		if (updateSchedule != null) {
			updateSchedule.setStartingHour(schedule.getStartingHour());
			updateSchedule.setDestinationHour(schedule.getDestinationHour());
			
			if (schedule.getMainEntity() != null) {
				updateSchedule.setMainEntity(schedule.getMainEntity());
			}
			if (schedule.getStartingAddress() != null) {
				updateSchedule.setStartingAddress(schedule.getStartingAddress());
			}
			if (schedule.getDestinationAddress() != null) {
				updateSchedule.setDestinationAddress(schedule.getDestinationAddress());
			}
			if (schedule.getWeekDay() != null) {
				updateSchedule.setWeekDay(schedule.getWeekDay());
			}
			
			scheduleDao.mergeSchedule(updateSchedule);
		}
	}
	
	public List<Schedule> getScheduleByStartingAddress(Address address){
		return scheduleDao.getScheduleByStartingAddress(address);
	}
	
	public List<Schedule> getScheduleByDestinationAddress(Address address){
		return scheduleDao.getScheduleByDestinationAddress(address);
	}
	
	public List<Schedule> getScheduleByStartingAddressAndWeekDay(Address address, WeekDay weekDay){
		return scheduleDao.getScheduleByStartingAddressAndWeekDay(address, weekDay);
	}
	
	public List<Schedule> getScheduleByDestinationAddressAndWeekDay(Address address, WeekDay weekDay){
		return scheduleDao.getScheduleByDestinationAddressAndWeekDay(address, weekDay);
	}
	
	public List<Schedule> getScheduleByEntity(MainEntity mainEntity){
		return scheduleDao.getScheduleByEntity(mainEntity);
	}
	
	public List<Schedule> getScheduleByEntityAndWeekDay(MainEntity mainEntity, WeekDay weekDay){
		return scheduleDao.getScheduleByEntityAndWeekDay(mainEntity, weekDay);
	}
	
	public List<Schedule> getScheduleByTimeInterval(String startingHour, String destinationHour){
		return scheduleDao.getScheduleByTimeInterval(startingHour, destinationHour);
	}
	
	public List<Schedule> getScheduleByStartingAddressAndWeekDayAndTimeInterval(Address address, WeekDay weekDay,
			String startingHour, String destinationHour){
		return scheduleDao.getScheduleByStartingAddressAndWeekDayAndTimeInterval(address, weekDay, 
				startingHour, destinationHour);
	}
	
	public List<Schedule> getScheduleByDestinationAddressAndWeekDayAndTimeInterval(Address address, WeekDay weekDay,
			String startingHour, String destinationHour){
		return scheduleDao.getScheduleByDestinationAddressAndWeekDayAndTimeInterval(address, weekDay, 
				startingHour, destinationHour);
	}
	
	public List<Schedule> getScheduleByWeekDayAndZip(WeekDay weekDay, String zip){
		return scheduleDao.getScheduleByWeekDayAndZip(weekDay, zip);
	}
	
	public List<Schedule> getScheduleByWeekDayAndTimeIntervalAndStartingAddressZip(WeekDay weekDay, String zip, String startingHour, 
			String destinationHour){
		return scheduleDao.getScheduleByWeekDayAndTimeIntervalAndStartingAddressZip(weekDay, zip, startingHour, destinationHour);
	}
	
	public List<Schedule> getScheduleByWeekDayAndTimeIntervalAndDestinationAddressZip(WeekDay weekDay, String zip, String startingHour, 
			String destinationHour){
		return scheduleDao.getScheduleByWeekDayAndTimeIntervalAndDestinationAddressZip(weekDay, zip, startingHour, destinationHour);
	}
	
	/*Week day*/
	
	public WeekDay findWeekDayById(int id){
		WeekDay weekDay = weekDayDao.findWeekDayById(id);
		return weekDay;
	}
}