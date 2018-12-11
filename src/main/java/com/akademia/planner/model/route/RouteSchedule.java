package com.akademia.planner.model.route;

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
import com.akademia.planner.model.schedule.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="rota_horario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RouteSchedule {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rota_id", nullable = false)
	private Route route;
	
	@OneToOne
    @JoinColumn(name="horario_id")
	private Schedule schedule;
	
	@Column(name = "ordem", nullable = false)
	private Integer order;
	
	@Column(name = "observacoes", nullable = false)
	private String observations;
	
	//add more ...
	
	public RouteSchedule() {}
	
	public RouteSchedule(@NotNull int id, Route route, Schedule schedule, String observations) {
		super();
		this.id = id;
		this.route = route;
		this.schedule = schedule;
		this.observations = observations;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoute() {
		return route.getId();
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}
}