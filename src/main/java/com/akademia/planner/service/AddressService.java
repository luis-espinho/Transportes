package com.akademia.planner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akademia.planner.dao.address.AddressDao;
import com.akademia.planner.model.address.Address;

@Service("addressService")
@Transactional
public class AddressService {
	
	@Autowired
	private AddressDao addressDao;
	
	public Address findAddressById(int id){
		Address address = addressDao.findAddressById(id);
		return address;
	}
	
	public Address createAddress(Address address) {
		addressDao.persist(address);
		return address;
	}
	
	public List<Address> getAllAddress(){
		return addressDao.getAllAddress();
	}
	
	public List<Address> getAddressByName(String name){
		return addressDao.getAddressByName(name);
	}
	
	public List<Address> getAddressByCity(String city){
		return addressDao.getAddressByCity(city);
	}
	
	public List<Address> getAddressByZipCode(String zip){
		return addressDao.getAddressByZipCode(zip);
	}
	
	private Double getDistanceFromLatLonInKm(Double latitudeA, Double longitudeA, Double latitudeB, Double longitudeB) {
		/*
		 * https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
		 */
		
		Integer earthRadius = 6371;
		Double dLatitude = Math.toRadians(latitudeB - latitudeA);
		Double dLongitude = Math.toRadians(longitudeB - longitudeA);
		Double a = Math.sin(dLatitude/2) * Math.sin(dLatitude/2) + Math.cos(Math.toRadians(latitudeA)) * 
				Math.cos(Math.toRadians(latitudeB)) * Math.sin(dLongitude/2) * Math.sin(dLongitude/2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		Double finalDistance = earthRadius * c;
		return finalDistance;
	}
	
	public List<Address> getAddressByArea(Double latitude, Double longitude, Double distance){
		List<Address> allAddresses = addressDao.getAllAddress();
		List<Address> areaAddresses = new ArrayList<Address>();
		
		 for (Address address : allAddresses){
			 if (getDistanceFromLatLonInKm(latitude, longitude, address.getLatitude(), address.getLongitude()) <= distance) {
				 areaAddresses.add(address);
			 }
		 }
		 
		 return areaAddresses;
	}
	
	public void deleteAddress(int id) {
		Address address = addressDao.findAddressById(id);
		if (address != null) {
			addressDao.delete(address);
		}
	}
}