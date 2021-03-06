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
@Table(name="entidade_aluno")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EntityStudent {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@NotNull
	@Column(name = "nome_completo", unique = false, nullable = false, length = 255)
	private String fullName;
	
	@Column(name = "escola", unique = false, length = 100)
	private String school;
	
	@Column(name = "ano", unique = false)
	private Integer schoolYear;
	
	@Column(name = "turma", unique = false)
	private String schoolClass;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entidade_id", nullable = false)
	private MainEntity mainEntity;
	
	public EntityStudent(){};
	
	public EntityStudent(@NotNull int id, @NotNull String fullName, String school, Integer schoolYear,
			String schoolClass) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.school = school;
		this.schoolYear = schoolYear;
		this.schoolClass = schoolClass;
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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(String schoolClass) {
		this.schoolClass = schoolClass;
	}

	public MainEntity getMainEntity() {
		return mainEntity;
	}

	public void setMainEntity(MainEntity mainEntity) {
		this.mainEntity = mainEntity;
	}
}