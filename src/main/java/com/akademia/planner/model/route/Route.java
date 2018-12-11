package com.akademia.planner.model.route;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.akademia.planner.model.address.Address;
import com.akademia.planner.model.date.WeekDay;
import com.akademia.planner.model.entity.EntityDriver;
import com.akademia.planner.model.entity.EntityVehicle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="rota")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Route {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "dia_semana_id", nullable = false)
	private WeekDay weekDay;
	
	@Column(name = "origem_hora", nullable = false)
	private String startingHour;
	
	@Column(name = "destino_hora", nullable = false)
	private String destinationHour;
	
	@OneToOne
	@JoinColumn(name = "origem_morada_id", nullable = false)
	private Address startingAddress;
	
	@OneToOne
	@JoinColumn(name = "destino_morada_id", nullable = false)
	private Address destinationAddress;
	
	@OneToOne
	@JoinColumn(name = "entidade_motorista_id", nullable = false)
	private EntityDriver entityDriver;
	
	@OneToOne
	@JoinColumn(name = "entidade_viatura_id", nullable = false)
	private EntityVehicle entityVehicle;
	
	//TODO: lazy exception not working!
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "route")
	private List<RouteSchedule> routeSchedules;
	
	public Route() {};
	
	public Route(@NotNull int id, WeekDay weekDay, String startingHour, String destinationHour, Address startingAddress,
			Address destinationAddress, EntityDriver entityDriver, EntityVehicle entityVehicle) {
		super();
		this.id = id;
		this.weekDay = weekDay;
		this.startingHour = startingHour;
		this.destinationHour = destinationHour;
		this.startingAddress = startingAddress;
		this.destinationAddress = destinationAddress;
		this.entityDriver = entityDriver;
		this.entityVehicle = entityVehicle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public WeekDay getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(WeekDay weekDay) {
		this.weekDay = weekDay;
	}

	public String getStartingHour() {
		return startingHour;
	}

	public void setStartingHour(String startingHour) {
		this.startingHour = startingHour;
	}

	public String getDestinationHour() {
		return destinationHour;
	}

	public void setDestinationHour(String destinationHour) {
		this.destinationHour = destinationHour;
	}

	public Address getStartingAddress() {
		return startingAddress;
	}

	public void setStartingAddress(Address startingAddress) {
		this.startingAddress = startingAddress;
	}

	public Address getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(Address destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public EntityDriver getEntityDriver() {
		return entityDriver;
	}

	public void setEntityDriver(EntityDriver entityDriver) {
		this.entityDriver = entityDriver;
	}

	public EntityVehicle getEntityVehicle() {
		return entityVehicle;
	}

	public void setEntityVehicle(EntityVehicle entityVehicle) {
		this.entityVehicle = entityVehicle;
	}

	public List<RouteSchedule> getRouteSchedules() {
		return routeSchedules;
	}

	public void setRouteSchedules(List<RouteSchedule> routeSchedules) {
		this.routeSchedules = routeSchedules;
	}
}