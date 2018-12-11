package com.akademia.planner.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akademia.planner.model.address.Address;
import com.akademia.planner.service.AddressService;
import com.akademia.planner.service.MainService;

@Controller
@RequestMapping(value = { "/address" })
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping(value = { "/getAddressById" }, method = RequestMethod.GET)
	public @ResponseBody Address getAddressById(Integer id) {
		return addressService.findAddressById(id);
	}
	
	@RequestMapping(value = {"/createAddress"}, method = RequestMethod.POST)
	public @ResponseBody Address createAddress(@Valid Address address) {
		return addressService.createAddress(address);
	}
	
	@RequestMapping(value = { "/getAddressByName" }, method = RequestMethod.GET)
	public @ResponseBody List<Address> getAddressByName(String name) {
		return addressService.getAddressByName(name);
	}
	
	@RequestMapping(value = { "/getAddressByCity" }, method = RequestMethod.GET)
	public @ResponseBody List<Address> getAddressByCity(String cityName) {
		return addressService.getAddressByCity(cityName);
	}
	
	@RequestMapping(value = { "/getAddressByZipCode" }, method = RequestMethod.GET)
	public @ResponseBody List<Address> getAddressByZipCode(String zip) {
		return addressService.getAddressByZipCode(zip);
	}
	
	@RequestMapping(value = { "/getAddressByArea" }, method = RequestMethod.GET)
	public @ResponseBody List<Address> getAddressByArea(Double latitude, Double longitude, Double distance) {
		return addressService.getAddressByArea(latitude, longitude, distance);
	}
	
	@RequestMapping(value = { "/getAllAddress" }, method = RequestMethod.GET)
	public @ResponseBody List<Address> getAllAddress() {
		return addressService.getAllAddress();
	}
	
	@RequestMapping(value = { "/deleteAddressById" }, method = RequestMethod.POST)
	public @ResponseBody String deleteAddressById(Integer id) {
		addressService.deleteAddress(id);
		return "Address removed from Database";
	}
	
	//testing array parameters (it works!) - delete this
	@RequestMapping(value = { "/getAddressArray" }, method = RequestMethod.GET)
	public @ResponseBody Integer[] getAddressArray(Integer[] idList) {
		return idList;
	}
}