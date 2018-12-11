package com.akademia.planner.googleapi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.akademia.planner.model.address.Address;
import com.akademia.planner.model.route.RouteSchedule;
import com.akademia.planner.model.schedule.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static com.akademia.planner.googleapi.ApiKey.directionsKey;

/*
 * https://developers.google.com/maps/documentation/javascript/directions
	
	https://maps.googleapis.com/maps/api/directions/json?origin=Boston,MA&destination=Concord,
		MA&waypoints=Charlestown,MA|Lexington,MA&key=YOUR_API_KEY
 */

public class DirectionsRequest {
	
	private String apiKey = directionsKey;
	
	private String url = "https://maps.googleapis.com/maps/api/directions/";
	
	private String requestType = "json";
	
	private Address origin;
	
	private Address destination;
	
	private List<Address> waypoints = new ArrayList<Address>();
	
	private String travelMode = "driving";
	
	private Boolean optimizeWaypoints = true;
	
	public DirectionsRequest() {}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getApiKey() {
		return apiKey;
	}

	public Address getOrigin() {
		return origin;
	}

	public void setOrigin(Address origin) {
		this.origin = origin;
	}

	public Address getDestination() {
		return destination;
	}

	public void setDestination(Address destination) {
		this.destination = destination;
	}

	public List<Address> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<Address> waypoints) {
		this.waypoints = waypoints;
	}
	
	public void addWaypoint(Schedule schedule) {
		if (schedule.getDestinationAddress() == this.destination){
			this.waypoints.add(schedule.getStartingAddress());
		}
		else if (schedule.getStartingAddress() == this.origin) {
			this.waypoints.add(schedule.getDestinationAddress());
		}
		else {
			//maybe add both?
		}
	}
	
	public String getTravelMode() {
		return travelMode;
	}

	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}
	
	public Boolean getOptimizeWaypoints() {
		return optimizeWaypoints;
	}

	public void setOptimizeWaypoints(Boolean optimizeWaypoints) {
		this.optimizeWaypoints = optimizeWaypoints;
	}

	public String stringifyOrigin() {
		return this.stringifyAddress(this.origin);
	}
	
	public String stringifyDestination() {
		return this.stringifyAddress(this.destination);
	}
	
	public String stringifyWaypoints() {
		String finalString = "";
		
		for (int i=0; i<this.waypoints.size(); ++i) {
			if (i == 0) {
				finalString = finalString + this.stringifyAddress(this.waypoints.get(i));
			}
			else {
				finalString = finalString + "|" + this.stringifyAddress(this.waypoints.get(i));
			}
		}
		
		return finalString;
	}
	
	public String stringifyAddress(Address address) {
		String addressString = "";
		addressString = address.getAddress() + "," + address.getCity() + "," + address.getCountry();
		addressString = addressString.replace(' ', '+');
		return addressString;
	}
	
	public String getRequestUrl() {
		String completeUrl = "";
		String origin = this.stringifyOrigin();
		String destination = this.stringifyDestination();
		String waypoints = this.stringifyWaypoints();
		String optimize = this.optimizeWaypoints.toString();
		
		completeUrl = this.url + this.requestType + "?origin=" + origin + "&destination=" + destination
				+ "&waypoints=optimize:" + optimize + "|" + waypoints + "&key=" + this.apiKey;
		
		return completeUrl;
	}
}