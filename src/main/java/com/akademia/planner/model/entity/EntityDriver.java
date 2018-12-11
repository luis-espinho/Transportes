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
@Table(name="entidade_motorista")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EntityDriver {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@NotNull
	@Column(name = "nome_completo", unique = false, nullable = false, length = 255)
	private String fullName;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entidade_id", nullable = false)
	private MainEntity mainEntity;
	
	public EntityDriver(){};
	
	public EntityDriver(@NotNull int id, @NotNull String fullName) {
		super();
		this.id = id;
		this.fullName = fullName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public MainEntity getMainEntity() {
		return mainEntity;
	}

	public void setMainEntity(MainEntity mainEntity) {
		this.mainEntity = mainEntity;
	}
}