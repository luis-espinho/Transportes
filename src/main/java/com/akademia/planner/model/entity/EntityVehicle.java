package com.akademia.planner.model.entity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="entidade_viatura")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EntityVehicle {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@NotNull
	@Column(name = "nome", unique = false, nullable = false, length = 255)
	private String name;
	
	@NotNull
	@Column(name = "numero_lugares", unique = false, nullable = false)
	private Integer seats;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entidade_id", nullable = false)
	private MainEntity mainEntity;
	
	public EntityVehicle(){};
	
	public EntityVehicle(@NotNull int id, @NotNull String name, @NotNull Integer seats) {
		super();
		this.id = id;
		this.name = name;
		this.seats = seats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public MainEntity getMainEntity() {
		return mainEntity;
	}

	public void setMainEntity(MainEntity mainEntity) {
		this.mainEntity = mainEntity;
	}
}