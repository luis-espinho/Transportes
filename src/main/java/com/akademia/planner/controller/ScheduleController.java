package com.akademia.planner.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akademia.planner.dao.address.AddressDao;
import com.akademia.planner.model.address.Address;
import com.akademia.planner.model.date.WeekDay;
import com.akademia.planner.model.entity.EntityDriver;
import com.akademia.planner.model.entity.EntityStudent;
import com.akademia.planner.model.entity.EntityType;
import com.akademia.planner.model.entity.EntityVehicle;
import com.akademia.planner.model.entity.MainEntity;
import com.akademia.planner.model.schedule.Schedule;
import com.akademia.planner.service.AddressService;
import com.akademia.planner.service.EntityService;
import com.akademia.planner.service.MainService;
import com.akademia.planner.service.ScheduleService;

@Controller
@RequestMapping(value = { "/schedule" })
public class ScheduleController {
	
	@Autowired
	private EntityService entityService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ScheduleService scheduleService;
	
	/*Schedule*/
	
	@RequestMapping(value = { "/getScheduleById" }, method = RequestMethod.GET)
	public @ResponseBody Schedule getScheduleById(Integer id) {
		return scheduleService.findScheduleById(id);
	}
	
	@RequestMapping(value = {"/createSchedule"}, method = RequestMethod.POST)
	public @ResponseBody Schedule createSchedule(Schedule schedule, Integer mainEntityId, 
			Integer startingAddressId, Integer destinationAddressId, Integer weekDayId) {
		MainEntity mainEntity = entityService.findMainEntityById(mainEntityId);
		Address startingAddress = addressService.findAddressById(startingAddressId);
		Address destinationAddress = addressService.findAddressById(destinationAddressId);
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		schedule.setMainEntity(mainEntity);
		schedule.setStartingAddress(startingAddress);
		schedule.setDestinationAddress(destinationAddress);
		schedule.setWeekDay(weekDay);
		return scheduleService.createSchedule(schedule);
	}
	
	@RequestMapping(value = { "/deleteScheduleById" }, method = RequestMethod.POST)
	public @ResponseBody String deleteScheduleById(Integer id) {
		scheduleService.deleteSchedule(id);
		return "Entity type removed from Database";
	}
	
	@RequestMapping(value = { "/getAllSchedule" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getAllSchedule() {
		return scheduleService.getAllSchedule();
	}
	
	@RequestMapping(value = {"/updateSchedule"}, method = RequestMethod.POST)
	public @ResponseBody Schedule createSchedule(@Valid Schedule schedule, Integer updateId, Integer mainEntityId, 
			Integer startingAddressId, Integer destinationAddressId, Integer weekDayId) {
		if (mainEntityId != null) {
			MainEntity mainEntity = entityService.findMainEntityById(mainEntityId);
			schedule.setMainEntity(mainEntity);
		}
		if (startingAddressId != null) {
			Address startingAddress = addressService.findAddressById(startingAddressId);
			schedule.setStartingAddress(startingAddress);
		}
		if (destinationAddressId != null) {
			Address destinationAddress = addressService.findAddressById(destinationAddressId);
			schedule.setDestinationAddress(destinationAddress);
		}
		if (weekDayId != null) {
			WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
			schedule.setWeekDay(weekDay);
		}
		
		scheduleService.updateSchedule(updateId, schedule);
		return scheduleService.findScheduleById(updateId);
	}
	
	@RequestMapping(value = { "/getScheduleByStartingAddress" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByStartingAddress(Integer addressId) {
		Address address = addressService.findAddressById(addressId);
		return scheduleService.getScheduleByStartingAddress(address);
	}
	
	@RequestMapping(value = { "/getScheduleByDestinationAddress" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByDestinationAddress(Integer addressId) {
		Address address = addressService.findAddressById(addressId);
		return scheduleService.getScheduleByDestinationAddress(address);
	}
	
	@RequestMapping(value = { "/getScheduleByStartingAddressAndWeekDay" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByStartingAddressAndWeekDay(Integer addressId, Integer weekDayId) {
		Address address = addressService.findAddressById(addressId);
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return scheduleService.getScheduleByStartingAddressAndWeekDay(address, weekDay);
	}
	
	@RequestMapping(value = { "/getScheduleByDestinationAddressAndWeekDay" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByDestinationAddressAndWeekDay(Integer addressId, Integer weekDayId) {
		Address address = addressService.findAddressById(addressId);
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return scheduleService.getScheduleByDestinationAddressAndWeekDay(address, weekDay);
	}
	
	@RequestMapping(value = { "/getScheduleByEntity" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByEntity(Integer mainEntityId) {
		MainEntity mainEntity = entityService.findMainEntityById(mainEntityId);
		return scheduleService.getScheduleByEntity(mainEntity);
	}
	
	@RequestMapping(value = { "/getScheduleByEntityAndWeekDay" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByEntityAndWeekDay(Integer mainEntityId, Integer weekDayId) {
		MainEntity mainEntity = entityService.findMainEntityById(mainEntityId);
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return scheduleService.getScheduleByEntityAndWeekDay(mainEntity, weekDay);
	}
	
	@RequestMapping(value = { "/getScheduleByTimeInterval" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByTimeInterval(String startingHour, String destinationHour) {
		return scheduleService.getScheduleByTimeInterval(startingHour, destinationHour);
	}
	
	@RequestMapping(value = { "/getScheduleByStartingAddressAndWeekDayAndTimeInterval" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByStartingAddressAndWeekDayAndTimeInterval(Integer addressId, Integer weekDayId, 
			String startingHour, String destinationHour) {
		Address address = addressService.findAddressById(addressId);
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return scheduleService.getScheduleByStartingAddressAndWeekDayAndTimeInterval(address, weekDay, startingHour, 
				destinationHour);
	}
	
	@RequestMapping(value = { "/getScheduleByDestinationAddressAndWeekDayAndTimeInterval" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByDestinationAddressAndWeekDayAndTimeInterval(Integer addressId, Integer weekDayId, 
			String startingHour, String destinationHour) {
		Address address = addressService.findAddressById(addressId);
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return scheduleService.getScheduleByDestinationAddressAndWeekDayAndTimeInterval(address, weekDay, startingHour, 
				destinationHour);
	}
	
	@RequestMapping(value = { "/getScheduleByWeekDayAndZip" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByWeekDayAndZip(Integer weekDayId, String zip) {
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return scheduleService.getScheduleByWeekDayAndZip(weekDay, zip);
	}
	
	@RequestMapping(value = { "/getScheduleByWeekDayAndTimeIntervalAndStartingAddressZip" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByWeekDayAndTimeIntervalAndStartingAddressZip(Integer weekDayId, String zip,
			String startingHour, String destinationHour) {
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return scheduleService.getScheduleByWeekDayAndTimeIntervalAndStartingAddressZip(weekDay, zip, startingHour, 
				destinationHour);
	}
	
	@RequestMapping(value = { "/getScheduleByWeekDayAndTimeIntervalAndDestinationAddressZip" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getScheduleByWeekDayAndTimeIntervalAndDestinationAddressZip(Integer weekDayId, String zip,
			String startingHour, String destinationHour) {
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return scheduleService.getScheduleByWeekDayAndTimeIntervalAndDestinationAddressZip(weekDay, zip, startingHour, 
				destinationHour);
	}
}