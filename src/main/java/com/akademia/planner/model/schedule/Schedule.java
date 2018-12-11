package com.akademia.planner.model.schedule;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.akademia.planner.model.address.Address;
import com.akademia.planner.model.date.WeekDay;
import com.akademia.planner.model.entity.EntityType;
import com.akademia.planner.model.entity.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="horario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Schedule {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne(targetEntity = MainEntity.class)
	@JoinColumn(name = "entidade_id", unique = true, nullable = false)
	private MainEntity mainEntity;
	
	@OneToOne
	@JoinColumn(name = "origem_morada_id", nullable = false)
	private Address startingAddress;
	
	@OneToOne
	@JoinColumn(name = "destino_morada_id", nullable = false)
	private Address destinationAddress;
	
	@Column(name = "origem_hora", nullable = false)
	private String startingHour;
	
	@Column(name = "destino_hora", nullable = false)
	private String destinationHour;
	
	@OneToOne
	@JoinColumn(name = "dia_semana_id", nullable = false)
	private WeekDay weekDay;
	
	public Schedule() {};

	public Schedule(int id, MainEntity mainEntity, Address startingAddress,
			Address destinationAddress, String startingHour, String destinationHour, WeekDay weekDay) {
		super();
		this.id = id;
		this.mainEntity = mainEntity;
		this.startingAddress = startingAddress;
		this.destinationAddress = destinationAddress;
		this.startingHour = startingHour;
		this.destinationHour = destinationHour;
		this.weekDay = weekDay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@JsonIgnore
	public MainEntity getMainEntity() {
		return mainEntity;
	}
	
	public Integer getMainEntityId() {
		return mainEntity.getId();
	}
	
	public String getMainEntityName() {
		return mainEntity.getDisplayName();
	}

	public void setMainEntity(MainEntity mainEntity) {
		this.mainEntity = mainEntity;
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

	public WeekDay getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(WeekDay weekDay) {
		this.weekDay = weekDay;
	}
}
	