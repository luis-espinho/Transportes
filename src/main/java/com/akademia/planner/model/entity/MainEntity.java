package com.akademia.planner.model.entity;

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

import com.akademia.planner.model.schedule.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="entidade")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MainEntity {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@NotNull
	@Column(name = "ativo", unique = false, nullable = false)
	private boolean status;
	
	@NotNull
	@Column(name = "username", unique = true, nullable = false, length = 20)
	private String username;
	
	@JsonIgnore
	@NotNull
	@Column(name = "password", unique = false, nullable = false, length = 20)
	private String password;
	
	@NotNull
	@Column(name = "primeiro_nome", unique = false, nullable = false, length = 20)
	private String firstName;
	
	@NotNull
	@Column(name = "ultimo_nome", unique = false, nullable = false, length = 20)
	private String lastName;
	
	@NotNull
	@Column(name = "display_nome", unique = false, nullable = false, length = 45)
	private String displayName;
	
	@OneToOne
	@JoinColumn(name = "entidade_tipo_id", nullable = false)
	private EntityType entityType;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "mainEntity")
	private Set<Schedule> schedule;
	
	public MainEntity(){};
	
	public MainEntity(int id, boolean status, String username, String password,
			String firstName, String lastName, String displayName) {
		this.id = id;
		this.status = status;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public Set<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(Set<Schedule> schedule) {
		this.schedule = schedule;
	}
}