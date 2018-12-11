package com.akademia.planner.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akademia.planner.model.address.Address;
import com.akademia.planner.model.date.WeekDay;
import com.akademia.planner.model.entity.EntityDriver;
import com.akademia.planner.model.entity.EntityVehicle;
import com.akademia.planner.model.entity.MainEntity;
import com.akademia.planner.model.route.Route;
import com.akademia.planner.model.route.RouteSchedule;
import com.akademia.planner.model.schedule.Schedule;
import com.akademia.planner.service.AddressService;
import com.akademia.planner.service.EntityService;
import com.akademia.planner.service.MainService;
import com.akademia.planner.service.RouteService;
import com.akademia.planner.service.ScheduleService;
import com.akademia.planner.googleapi.ApiKey;
import com.akademia.planner.googleapi.DirectionsRequest;
import com.akademia.planner.googleapi.DistanceMatrixRequest;

@Controller
@RequestMapping(value = { "/route" })
public class RouteController {
	
	@Autowired
	private AddressService addressService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private EntityService entityService;
	
	/*route*/
	
	@RequestMapping(value = { "/getRouteById" }, method = RequestMethod.GET)
	public @ResponseBody Route getRouteById(Integer id) {
		return routeService.findRouteById(id);
	}
	
	@RequestMapping(value = {"/createRoute"}, method = RequestMethod.POST)
	public @ResponseBody Integer createRoute(Route route, Integer entityDriverId, Integer entityVehicleId,
			Integer startingAddressId, Integer destinationAddressId, Integer weekDayId) {
		//returns route id. null id means route wasn't saved
		
		EntityDriver entityDriver = entityService.findEntityDriverById(entityDriverId);
		EntityVehicle entityVehicle = entityService.findEntityVehicleById(entityVehicleId);
		Address startingAddress = addressService.findAddressById(startingAddressId);
		Address destinationAddress = addressService.findAddressById(destinationAddressId);
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		route.setEntityDriver(entityDriver);
		route.setEntityVehicle(entityVehicle);
		route.setStartingAddress(startingAddress);
		route.setDestinationAddress(destinationAddress);
		route.setWeekDay(weekDay);
		
		if (routeService.validateDriverAndVehicle(route.getWeekDay(), route.getStartingHour(), route.getDestinationHour(), 
				route.getEntityDriver(), route.getEntityVehicle())) {
			routeService.createRoute(route);
		}
		
		return route.getId();
	}
	
	@RequestMapping(value = { "/deleteRouteById" }, method = RequestMethod.POST)
	public @ResponseBody String deleteRouteById(Integer id) {
		routeService.deleteRoute(id);
		return "Route removed from Database";
	}
	
	@RequestMapping(value = { "/getAllRoute" }, method = RequestMethod.GET)
	public @ResponseBody List<Route> getAllRoute() {
		return routeService.getAllRoute();
	}
	
	@RequestMapping(value = {"/updateRoute"}, method = RequestMethod.POST)
	public @ResponseBody Boolean updateRoute(@Valid Route route, Integer updateId, Integer entityDriverId, Integer entityVehicleId,
			Integer startingAddressId, Integer destinationAddressId, Integer weekDayId) {
		if (entityDriverId != null) {
			EntityDriver entityDriver = entityService.findEntityDriverById(entityDriverId);
			route.setEntityDriver(entityDriver);
		}
		if (entityVehicleId != null) {
			EntityVehicle entityVehicle = entityService.findEntityVehicleById(entityVehicleId);
			route.setEntityVehicle(entityVehicle);
		}
		if (destinationAddressId != null) {
			Address destinationAddress = addressService.findAddressById(destinationAddressId);
			route.setDestinationAddress(destinationAddress);
		}
		if (weekDayId != null) {
			WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
			route.setWeekDay(weekDay);
		}
		
		if (routeService.validateDriverAndVehicle(route.getWeekDay(), route.getStartingHour(), route.getDestinationHour(), 
				route.getEntityDriver(), route.getEntityVehicle())) {
			routeService.updateRoute(updateId, route);
			return true;
		}
		
		return false;
	}

	@RequestMapping(value = { "/getAllRoutesByWeekDay" }, method = RequestMethod.GET)
	public @ResponseBody List<Route> getAllRoutesByWeekDay(int weekDayId) {
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return routeService.getAllRoutesByWeekDay(weekDay);
	}
	
	@RequestMapping(value = { "/addRouteSchedulesToRoute" }, method = RequestMethod.POST)
	public @ResponseBody List<RouteSchedule> addRouteSchedulesToRoute(Integer routeId, Integer[] scheduleIds) {
		Route route = routeService.findRouteById(routeId);
		List<Integer> idList = Arrays.asList(scheduleIds);
		routeService.addSchedulesToRoute(idList, route);
		return routeService.getRouteScheduleByRoute(route);
	}
	
	/*route schedule*/
	
	@RequestMapping(value = { "/getRouteScheduleById" }, method = RequestMethod.GET)
	public @ResponseBody RouteSchedule getRouteScheduleById(Integer id) {
		return routeService.findRouteScheduleById(id);
	}
	
	@RequestMapping(value = {"/createRouteSchedule"}, method = RequestMethod.POST)
	public @ResponseBody RouteSchedule createRouteSchedule(RouteSchedule routeSchedule, Integer routeId, Integer scheduleId) {
		Route route = routeService.findRouteById(routeId);
		Schedule schedule = scheduleService.findScheduleById(scheduleId);
		routeSchedule.setRoute(route);
		routeSchedule.setSchedule(schedule);
		return routeService.createRouteSchedule(routeSchedule);
	}
	
	@RequestMapping(value = { "/deleteRouteScheduleById" }, method = RequestMethod.POST)
	public @ResponseBody String deleteRouteScheduleById(Integer id) {
		routeService.deleteRouteSchedule(id);
		return "RouteSchedule removed from Database";
	}
	
	@RequestMapping(value = { "/getAllRouteSchedule" }, method = RequestMethod.GET)
	public @ResponseBody List<RouteSchedule> getAllRouteSchedule() {
		return routeService.getAllRouteSchedule();
	}
	
	@RequestMapping(value = {"/updateRouteSchedule"}, method = RequestMethod.POST)
	public @ResponseBody void updateRouteSchedule(RouteSchedule routeSchedule, Integer routeId, Integer scheduleId) {
		Route route = routeService.findRouteById(routeId);
		Schedule schedule = scheduleService.findScheduleById(scheduleId);
		routeSchedule.setRoute(route);
		routeSchedule.setSchedule(schedule);
		routeService.mergeRouteSchedule(routeSchedule);
	}
	
	@RequestMapping(value = { "/getRouteScheduleByRoute" }, method = RequestMethod.GET)
	public @ResponseBody List<RouteSchedule> getRouteScheduleByRoute(Integer routeId) {
		Route route = routeService.findRouteById(routeId);
		return routeService.getRouteScheduleByRoute(route);
	}
	
	@RequestMapping(value = { "/getSchedulesWithoutRoutes" }, method = RequestMethod.GET)
	public @ResponseBody List<Schedule> getSchedulesWithoutRoutes(Integer weekDayId) {
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return routeService.getSchedulesWithoutRoutes(weekDay);
	}
	
	@RequestMapping(value = { "/getDriversWithoutRoutes" }, method = RequestMethod.GET)
	public @ResponseBody List<EntityDriver> getDriversWithoutRoutes(Integer weekDayId, String startingHour, String destinationHour) {
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return routeService.getDriversWithoutRoutes(weekDay, startingHour, destinationHour);
	}
	
	@RequestMapping(value = { "/getVehiclesWithoutRoutes" }, method = RequestMethod.GET)
	public @ResponseBody List<EntityVehicle> getVehiclesWithoutRoutes(Integer weekDayId, String startingHour, String destinationHour) {
		WeekDay weekDay = scheduleService.findWeekDayById(weekDayId);
		return routeService.getVehiclesWithoutRoutes(weekDay, startingHour, destinationHour);
	}
	
	/* google api */
	
	@RequestMapping(value = { "/getDirectionsByRoute" }, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getDirectionsByRoute(Integer routeId) throws IOException {
		
		Route route = routeService.findRouteById(routeId);
		List<RouteSchedule> routeSchedules = routeService.getRouteScheduleByRoute(route);
		
		DirectionsRequest request = new DirectionsRequest();
		request.setOrigin(route.getStartingAddress());
		request.setDestination(route.getDestinationAddress());
		
		//TODO: starting or destination (add to route object)
		for (RouteSchedule routeSchedule : routeSchedules) {
			request.getWaypoints().add(routeSchedule.getSchedule().getDestinationAddress());
		}
		
		String url = request.getRequestUrl();
		
		String USER_AGENT = "Mozilla/5.0";
		
		System.out.print(url);
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine +"\n");
		}
		in.close();

		//print result
		System.out.println(response.toString());
		
		return response.toString();
	}
	
	@RequestMapping(value = { "/getDirectionByAddress" }, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getDirectionByAddress(Integer originId, Integer destinationId, Integer[] waypointIds) throws IOException {
		
		DirectionsRequest request = new DirectionsRequest();
		request.setOrigin(addressService.findAddressById(originId));
		request.setDestination(addressService.findAddressById(destinationId));
		
		for (Integer waypointId : waypointIds) {
			request.getWaypoints().add(addressService.findAddressById(waypointId));
		}
		
		String url = request.getRequestUrl();
		
		String USER_AGENT = "Mozilla/5.0";
		
		System.out.print(url);
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine +"\n");
		}
		in.close();

		//print result
		System.out.println(response.toString());
		
		return response.toString();
	}
	
	/* -------------------- test requests to google maps API ---------------------- */

	@RequestMapping(value = { "/testDistanceMatrixRequestThree" }, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String testDistanceMatrixRequestThree() throws IOException {
		
		DirectionsRequest request = new DirectionsRequest();
		Address origin = addressService.findAddressById(1);
		Address destination = addressService.findAddressById(5);
		request.getWaypoints().add(addressService.findAddressById(6));
		request.getWaypoints().add(addressService.findAddressById(7));
		request.setOrigin(origin);
		request.setDestination(destination);
		String url = request.getRequestUrl();
		
		String USER_AGENT = "Mozilla/5.0";
		
		System.out.print(url);
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine +"\n");
		}
		in.close();

		//print result
		System.out.println(response.toString());
		
		return response.toString();
	}
	
	@RequestMapping(value = { "/testDistanceMatrixRequestTwo" }, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String testDistanceMatrixRequestTwo() throws IOException {
		
		DistanceMatrixRequest request = new DistanceMatrixRequest();
		List<Address> origins = addressService.getAllAddress();
		List<Address> destinations = addressService.getAllAddress();
		request.setOrigins(origins);
		request.setDestinations(destinations);
		String url = request.getRequestUrl();
		
		String USER_AGENT = "Mozilla/5.0";
		
		System.out.print(url);
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine +"\n");
		}
		in.close();

		//print result
		System.out.println(response.toString());
		
		return response.toString();
	}
	
	@RequestMapping(value = { "/testDistanceMatrixRequest" }, method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String testDistanceMatrixRequest() throws IOException {
		
		String USER_AGENT = "Mozilla/5.0";
		String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?";
		String origin = "origins=Vancouver+BC";
		String destinations = "destinations=San+Francisco|Vancouver+BC";
		String key = "key=" + ApiKey.distanceMatrixKey;
		String url = baseUrl + origin + "&" + destinations + "&" + key;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine +"\n");
		}
		in.close();

		//print result
		System.out.println(response.toString());
		
		return response.toString();
	}
	
}