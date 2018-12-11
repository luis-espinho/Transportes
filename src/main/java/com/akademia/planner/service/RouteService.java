package com.akademia.planner.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.base.AbstractPartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.akademia.planner.dao.address.AddressDao;
import com.akademia.planner.dao.entity.EntityDriverDao;
import com.akademia.planner.dao.entity.EntityVehicleDao;
import com.akademia.planner.dao.route.RouteDao;
import com.akademia.planner.dao.route.RouteScheduleDao;
import com.akademia.planner.dao.schedule.ScheduleDao;
import com.akademia.planner.model.address.Address;
import com.akademia.planner.model.date.WeekDay;
import com.akademia.planner.model.entity.EntityDriver;
import com.akademia.planner.model.entity.EntityVehicle;
import com.akademia.planner.model.route.Route;
import com.akademia.planner.model.route.RouteSchedule;
import com.akademia.planner.model.schedule.Schedule;
import com.akademia.planner.googleapi.ApiKey;
import com.akademia.planner.googleapi.DistanceMatrixRequest;

@Service("routeService")
@Transactional
public class RouteService {
		
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private RouteDao routeDao;
	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	private RouteScheduleDao routeScheduleDao;
	@Autowired
	private EntityDriverDao entityDriverDao;
	@Autowired
	private EntityVehicleDao entityVehicleDao;
	
	/*route*/
	
	public Route findRouteById(int id){
		Route route = routeDao.findRouteById(id);
		return route;
	}
	
	public Route createRoute(Route route) {
		routeDao.persist(route);
		return route;
	}
	
	public List<Route> getAllRoute(){
		return routeDao.getAllRoute();
	}
	
	public void deleteRoute(int id) {
		Route route = routeDao.findRouteById(id);
		if (route != null) {
			routeDao.delete(route);
		}
	}
	
	public void mergeRoute(Route route) {
		routeDao.mergeRoute(route);
	}
	
	public void updateRoute(int updateId, Route route) {
		Route updateRoute = routeDao.findRouteById(updateId);
		if (updateRoute != null) {
			updateRoute.setStartingHour(route.getStartingHour());
			updateRoute.setDestinationHour(route.getDestinationHour());
			
			if (route.getEntityDriver() != null) {
				updateRoute.setEntityDriver(route.getEntityDriver());
			}
			if (route.getEntityVehicle() != null) {
				updateRoute.setEntityVehicle(route.getEntityVehicle());
			}
			if (route.getStartingAddress() != null) {
				updateRoute.setStartingAddress(route.getStartingAddress());
			}
			if (route.getDestinationAddress() != null) {
				updateRoute.setDestinationAddress(route.getDestinationAddress());
			}
			if (route.getWeekDay() != null) {
				updateRoute.setWeekDay(route.getWeekDay());
			}
			
			routeDao.mergeRoute(updateRoute);
		}
	}
	
	public List<Route> getAllRoutesByWeekDay(WeekDay weekDay){
		return routeDao.getRouteByWeekDay(weekDay);
	}
	
	public List<RouteSchedule> getRouteScheduleByRoute(Route route){
		return routeScheduleDao.getRouteScheduleByRoute(route);
	}
	
	public void initializeRouteSchedule(Route route){
		Hibernate.initialize(route.getRouteSchedules());
	}
	
	public List<Schedule> getSchedulesWithoutRoutes(WeekDay weekDay){
		//gets all schedules not associated with a route for the selected week day (not tested)
		
		List<Schedule> schedules = scheduleDao.getScheduleByWeekDay(weekDay);
		List<Route> routes = routeDao.getRouteByWeekDay(weekDay);
		
		for (Route route : routes) {
			List<RouteSchedule> routeSchedules = getRouteScheduleByRoute(route);
			
			for (RouteSchedule routeSchedule : routeSchedules) {
				schedules.remove(routeSchedule.getSchedule());
			}
		}
		
		return schedules;
	}
	
	public List<Schedule> filterSchedulesWithoutRoutes(WeekDay weekDay, List<Schedule> schedules){
		List<Route> routes = routeDao.getRouteByWeekDay(weekDay);
		
		for (Route route : routes) {
			List<RouteSchedule> routeSchedules = getRouteScheduleByRoute(route);
			
			for (RouteSchedule routeSchedule : routeSchedules) {
				schedules.remove(routeSchedule.getSchedule());
			}
		}
		
		return schedules;
	}
	
	public List<EntityDriver> getDriversWithoutRoutes(WeekDay weekDay, String startingHour, String destinationHour){
		//TODO: test this
		
		List<EntityDriver> drivers = entityDriverDao.getAllEntityDriver();
		List<Route> routes = routeDao.getRouteByWeekDay(weekDay);
		
		DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm:ss");
		DateTime startTime = df.parseLocalTime(startingHour).toDateTimeToday();
		DateTime endTime = df.parseLocalTime(destinationHour).toDateTimeToday();
		Interval timeInterval = new Interval(startTime, endTime);
		DateTime routeStart = df.parseLocalTime(startingHour).toDateTimeToday();
		DateTime routeEnd = df.parseLocalTime(destinationHour).toDateTimeToday();
		
		for (Route route : routes) {
			routeStart = df.parseLocalTime(route.getStartingHour()).toDateTimeToday();
			routeEnd = df.parseLocalTime(route.getDestinationHour()).toDateTimeToday();
			Interval routeInterval = new Interval(routeStart, routeEnd);
			
			if (routeInterval.overlaps(timeInterval)) {
				drivers.remove(route.getEntityDriver());
			}
		}
		
		return drivers;
	}
	
	public List<EntityVehicle> getVehiclesWithoutRoutes(WeekDay weekDay, String startingHour, String destinationHour){
		//TODO: test this
		
		List<EntityVehicle> vehicles = entityVehicleDao.getAllEntityVehicle();
		List<Route> routes = routeDao.getRouteByWeekDay(weekDay);
		
		DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm:ss");
		DateTime startTime = df.parseLocalTime(startingHour).toDateTimeToday();
		DateTime endTime = df.parseLocalTime(destinationHour).toDateTimeToday();
		Interval timeInterval = new Interval(startTime, endTime);
		DateTime routeStart = df.parseLocalTime(startingHour).toDateTimeToday();
		DateTime routeEnd = df.parseLocalTime(destinationHour).toDateTimeToday();
		
		for (Route route : routes) {
			routeStart = df.parseLocalTime(route.getStartingHour()).toDateTimeToday();
			routeEnd = df.parseLocalTime(route.getDestinationHour()).toDateTimeToday();
			Interval routeInterval = new Interval(routeStart, routeEnd);
			
			if (routeInterval.overlaps(timeInterval)) {
				vehicles.remove(route.getEntityVehicle());
			}
		}
		
		return vehicles;
	}
	
	public Boolean validateSchedule(WeekDay weekDay, Schedule schedule) {
		List<Route> routes = routeDao.getRouteByWeekDay(weekDay);
		
		for (Route route : routes) {
			List<RouteSchedule> routeSchedules = getRouteScheduleByRoute(route);
			
			for (RouteSchedule routeSchedule : routeSchedules) {
				if (routeSchedule.getSchedule() == schedule) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Boolean validateRoute(Route newRoute, List<Schedule> schedules, EntityDriver routeDriver, 
		EntityVehicle routeVehicle){
		//if schedules, driver or vehicle are already set then route not valid
		
		Boolean valid = true;
		List<Route> routes = routeDao.getRouteByWeekDay(newRoute.getWeekDay());
		
		valid = validateDriver(newRoute.getStartingHour(), newRoute.getDestinationHour(), routeDriver, routes);
		valid = validateVehicle(newRoute.getStartingHour(), newRoute.getDestinationHour(), routeVehicle, routes);
		valid = validateRouteSchedules(schedules, routes);
		
		return valid;
	}
	
	public Boolean validateRouteSchedules(List<Schedule> schedules, List<Route> routes) {
		for (Route route : routes) {
			List<RouteSchedule> routeSchedules = getRouteScheduleByRoute(route);
			
			for (RouteSchedule routeSchedule : routeSchedules) {
				if (schedules.contains(routeSchedule.getSchedule())) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Boolean validateRouteSchedulesById(List<Integer> scheduleIds, List<Route> routes) {
		for (Route route : routes) {
			List<RouteSchedule> routeSchedules = getRouteScheduleByRoute(route);
			
			for (RouteSchedule routeSchedule : routeSchedules) {
				if (scheduleIds.contains(routeSchedule.getSchedule().getId())) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public List<Integer> getNonValidatedScheduleIds(List<Integer> scheduleIds, List<Route> routes) {
		for (Route route : routes) {
			List<RouteSchedule> routeSchedules = getRouteScheduleByRoute(route);
			List<Integer> nonValidIds = new ArrayList<>();
			
			for (RouteSchedule routeSchedule : routeSchedules) {
				if (scheduleIds.contains(routeSchedule.getSchedule().getId())) {
					nonValidIds.add(routeSchedule.getSchedule().getId());
				}
			}
		}
		
		return scheduleIds;
	}
	
	public Boolean validateDriver(String startingHour, String destinationHour, EntityDriver entityDriver, List<Route> routes) {
		DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm:ss");
		DateTime startTime = df.parseLocalTime(startingHour).toDateTimeToday();
		DateTime endTime = df.parseLocalTime(destinationHour).toDateTimeToday();
		Interval timeInterval = new Interval(startTime, endTime);
		DateTime routeStart = df.parseLocalTime(startingHour).toDateTimeToday();
		DateTime routeEnd = df.parseLocalTime(destinationHour).toDateTimeToday();
		
		for (Route route : routes) {
			routeStart = df.parseLocalTime(route.getStartingHour()).toDateTimeToday();
			routeEnd = df.parseLocalTime(route.getDestinationHour()).toDateTimeToday();
			Interval routeInterval = new Interval(routeStart, routeEnd);
			
			if (routeInterval.overlaps(timeInterval) && entityDriver == route.getEntityDriver()) {
				return false;
			}
		}
		
		return true;
	}
	
	public Boolean validateVehicle(String startingHour, String destinationHour, EntityVehicle entityVehicle, List<Route> routes) {
		DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm:ss");
		DateTime startTime = df.parseLocalTime(startingHour).toDateTimeToday();
		DateTime endTime = df.parseLocalTime(destinationHour).toDateTimeToday();
		Interval timeInterval = new Interval(startTime, endTime);
		DateTime routeStart = df.parseLocalTime(startingHour).toDateTimeToday();
		DateTime routeEnd = df.parseLocalTime(destinationHour).toDateTimeToday();
		
		for (Route route : routes) {
			routeStart = df.parseLocalTime(route.getStartingHour()).toDateTimeToday();
			routeEnd = df.parseLocalTime(route.getDestinationHour()).toDateTimeToday();
			Interval routeInterval = new Interval(routeStart, routeEnd);
			
			if (routeInterval.overlaps(timeInterval) && entityVehicle == route.getEntityVehicle()) {
				return false;
			}
		}
		
		return true;
	}
	
	public Boolean validateDriverAndVehicle(WeekDay weekDay, String startingHour, String destinationHour, EntityDriver entityDriver, 
			EntityVehicle entityVehicle) {
		List<Route> routes = routeDao.getRouteByWeekDay(weekDay);
		
		DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm:ss");
		DateTime startTime = df.parseLocalTime(startingHour).toDateTimeToday();
		DateTime endTime = df.parseLocalTime(destinationHour).toDateTimeToday();
		Interval timeInterval = new Interval(startTime, endTime);
		DateTime routeStart = df.parseLocalTime(startingHour).toDateTimeToday();
		DateTime routeEnd = df.parseLocalTime(destinationHour).toDateTimeToday();
		
		for (Route route : routes) {
			routeStart = df.parseLocalTime(route.getStartingHour()).toDateTimeToday();
			routeEnd = df.parseLocalTime(route.getDestinationHour()).toDateTimeToday();
			Interval routeInterval = new Interval(routeStart, routeEnd);
			
			if (routeInterval.overlaps(timeInterval) && entityDriver == route.getEntityDriver()) {
				if (entityDriver == route.getEntityDriver() || entityVehicle == route.getEntityVehicle()) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/*route schedule*/

	public RouteSchedule findRouteScheduleById(int id){
		RouteSchedule routeSchedule = routeScheduleDao.findRouteScheduleById(id);
		return routeSchedule;
	}
	
	public RouteSchedule createRouteSchedule(RouteSchedule routeSchedule) {
		routeScheduleDao.persist(routeSchedule);
		return routeSchedule;
	}
	
	public List<RouteSchedule> getAllRouteSchedule(){
		return routeScheduleDao.getAllRouteSchedule();
	}
	
	public void deleteRouteSchedule(int id) {
		RouteSchedule routeSchedule = routeScheduleDao.findRouteScheduleById(id);
		if (routeSchedule != null) {
			routeScheduleDao.delete(routeSchedule);
		}
	}
	
	public void mergeRouteSchedule(RouteSchedule routeSchedule) {
		routeScheduleDao.mergeRouteSchedule(routeSchedule);
	}
	
	public void addSchedulesToRoute(List<Integer> scheduleIds, Route route) {
		for(Integer scheduleId : scheduleIds) {
			Schedule schedule = scheduleDao.findScheduleById(scheduleId);
			
			if (schedule != null) {
				RouteSchedule routeSchedule = new RouteSchedule();
				routeSchedule.setRoute(route);
				routeSchedule.setSchedule(schedule);
				routeScheduleDao.persist(routeSchedule);
			}
		}
	}
}