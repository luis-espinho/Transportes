package com.akademia.planner.model.address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="morada")
@JsonIgnoreProperties(value = { "" })
public class Address {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@NotNull
	@Column(name = "morada", unique = false, nullable = false, length = 255)
	private String address;
	
	@Column(name = "localidade", unique = false, nullable = true, length = 55)
	private String city;
	
	@Column(name = "pais", unique = false, nullable = true, length = 45)
	private String country;
	
	@Column(name = "codigo_postal", unique = false, nullable = true, length = 12)
	private String zip;
	
	@Column(name = "latitude", unique = false, nullable = true)
	private Double latitude;
	
	@Column(name = "longitude", unique = false, nullable = true)
	private Double longitude;
	
	public Address(){};
	
	public Address(int id, String address, String city, String country, String zip, Double latitude, Double longitude) {
		this.id = id;
		this.address = address;
		this.city = city;
		this.country = country;
		this.zip = zip;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}