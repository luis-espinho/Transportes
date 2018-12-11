package com.akademia.planner.googleapi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.akademia.planner.model.address.Address;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static com.akademia.planner.googleapi.ApiKey.distanceMatrixKey;

/*
 * https://developers.google.com/maps/documentation/javascript/distancematrix
	
	origins: [origin1, origin2],
    destinations: [destinationA, destinationB],
    travelMode: 'DRIVING',
    transitOptions: TransitOptions,
    drivingOptions: DrivingOptions,
    unitSystem: UnitSystem,
    avoidHighways: Boolean,
    avoidTolls: Boolean,
	
	https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Washington,DC&destinations=New+York+City,NY&key=
	https://github.com/googlemaps/google-maps-services-java/tree/master/src/main/java/com/google/maps
	
	https://maps.googleapis.com/maps/api/distancematrix/xml?origins=Vancouver+BC|
		Seattle&destinations=San+Francisco|Vancouver+BC&mode=bicycling&units=imperial&key=YOUR_API_KEY
	
	https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|
		Seattle&destinations=San+Francisco|Vancouver+BC&mode=bicycling&units=imperial&key=YOUR_API_KEY
 */

public class DistanceMatrixRequest {
	
	private String apiKey = distanceMatrixKey;
	
	private String url = "https://maps.googleapis.com/maps/api/distancematrix/";
	
	private String requestType = "json";
	
	private List<Address> origins = new ArrayList<Address>();
	
	private List<Address> destinations = new ArrayList<Address>();
	
	private String travelMode = "driving";
	
	public DistanceMatrixRequest() {}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getApiKey() {
		return apiKey;
	}

	public List<Address> getOrigins() {
		return origins;
	}

	public void setOrigins(List<Address> origins) {
		this.origins = origins;
	}

	public List<Address> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Address> destinations) {
		this.destinations = destinations;
	}

	public String getTravelMode() {
		return travelMode;
	}

	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}

	public String stringifyOrigins() {
		String finalString = "";
		
		for (int i=0; i<this.origins.size(); ++i) {
			if (i == 0) {
				finalString = finalString + this.stringifyAddress(this.origins.get(i));
			}
			else {
				finalString = finalString + "|" + this.stringifyAddress(this.origins.get(i));
			}
		}
		
		return finalString;
	}
	
	public String stringifyDestinations() {
		String finalString = "";
		
		for (int i=0; i<this.destinations.size(); ++i) {
			if (i == 0) {
				finalString = finalString + this.stringifyAddress(this.destinations.get(i));
			}
			else {
				finalString = finalString + "|" + this.stringifyAddress(this.destinations.get(i));
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
		String origins = this.stringifyOrigins();
		String destinations = this.stringifyDestinations();
		
		completeUrl = this.url + this.requestType + "?origins=" + origins + "&destinations=" + destinations
				+ "&key=" + this.apiKey;
		
		return completeUrl;
	}
}