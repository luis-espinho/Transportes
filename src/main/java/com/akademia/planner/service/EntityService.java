package com.akademia.planner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akademia.planner.dao.address.AddressDao;
import com.akademia.planner.dao.entity.EntityDriverDao;
import com.akademia.planner.dao.entity.EntityStudentDao;
import com.akademia.planner.dao.entity.EntityTypeDao;
import com.akademia.planner.dao.entity.EntityVehicleDao;
import com.akademia.planner.dao.entity.MainEntityDao;
import com.akademia.planner.model.address.Address;
import com.akademia.planner.model.entity.EntityDriver;
import com.akademia.planner.model.entity.EntityStudent;
import com.akademia.planner.model.entity.EntityType;
import com.akademia.planner.model.entity.EntityVehicle;
import com.akademia.planner.model.entity.MainEntity;

@Service("entityService")
@Transactional
public class EntityService {
	
	@Autowired
	private MainEntityDao mainEntityDao;
	
	@Autowired
	private EntityTypeDao entityTypeDao;
	
	@Autowired
	private EntityStudentDao entityStudentDao;
	
	@Autowired
	private EntityDriverDao entityDriverDao;
	
	@Autowired
	private EntityVehicleDao entityVehicleDao;
	
	/*Entity type*/
	
	public EntityType findEntityTypeById(int id){
		EntityType entityType = entityTypeDao.findEntityTypeById(id);
		return entityType;
	}
	
	public EntityType createEntityType(EntityType entityType) {
		entityTypeDao.persist(entityType);
		return entityType;
	}
	
	public List<EntityType> getAllEntityType(){
		return entityTypeDao.getAllEntityType();
	}
	
	public void deleteEntityType(int id) {
		EntityType entityType = entityTypeDao.findEntityTypeById(id);
		if (entityType != null) {
			entityTypeDao.delete(entityType);
		}
	}
	
	/*Main Entity*/
	
	public MainEntity findMainEntityById(int id){
		MainEntity mainEntity = mainEntityDao.findMainEntityById(id);
		return mainEntity;
	}
	
	public MainEntity createMainEntity(MainEntity mainEntity) {
		mainEntityDao.persist(mainEntity);
		return mainEntity;
	}
	
	public List<MainEntity> getAllMainEntity(){
		return mainEntityDao.getAllMainEntity();
	}
	
	public void deleteMainEntity(int id) {
		MainEntity mainEntity = mainEntityDao.findMainEntityById(id);
		if (mainEntity != null) {
			mainEntityDao.delete(mainEntity);
		}
	}
	
	public List<MainEntity> findMainEntityByName(String name){
		List<MainEntity> results = mainEntityDao.getMainEntityByDisplayName(name);
		return results;
	}
	
	public MainEntity getMainEntityByEntityStudent(EntityStudent entityStudent) {
		Hibernate.initialize(entityStudent.getMainEntity());
		return entityStudent.getMainEntity();
	}
	
	public List<MainEntity> getMainEntityListByEntityStudentList(List<EntityStudent> studentList){
		List<MainEntity> entityList = new ArrayList<MainEntity>();
		for (EntityStudent student : studentList){
			 Hibernate.initialize(student.getMainEntity());
			 if (student.getMainEntity() != null) {
				 entityList.add(student.getMainEntity());
			 }
		}
		return entityList;
	}
	
	public MainEntity getMainEntityByEntityVehicle(EntityVehicle entityVehicle) {
		Hibernate.initialize(entityVehicle.getMainEntity());
		return entityVehicle.getMainEntity();
	}
	
	public MainEntity getMainEntityByEntityDriver(EntityDriver entityDriver) {
		Hibernate.initialize(entityDriver.getMainEntity());
		return entityDriver.getMainEntity();
	}
	
	public List<MainEntity> getMainEntityByEntityType(EntityType entityType){
		return mainEntityDao.getMainEntityByEntityType(entityType);
	}
	
	public void updateMainEntity(int updateId, MainEntity mainEntity) {
		MainEntity updateEntity = mainEntityDao.findMainEntityById(updateId);
		if (updateEntity != null) {
			updateEntity.setUsername(mainEntity.getUsername());
			updateEntity.setStatus(mainEntity.isStatus());
			updateEntity.setFirstName(mainEntity.getFirstName());
			updateEntity.setLastName(mainEntity.getLastName());
			updateEntity.setDisplayName(mainEntity.getDisplayName());
			
			if (mainEntity.getEntityType() != null) {
				updateEntity.setEntityType(mainEntity.getEntityType());
			}
			
			mainEntityDao.mergeMainEntity(updateEntity);
		}
	}
	
	public void updateMainEntityType(MainEntity mainEntity, EntityType entityType) {
		if (mainEntity != null && mainEntity.getEntityType() != null) {
			mainEntity.setEntityType(entityType);
			mainEntityDao.mergeMainEntity(mainEntity);
		}
	}
	
	/*Entity Student*/
	
	public EntityStudent findEntityStudentById(int id){
		EntityStudent entityStudent = entityStudentDao.findEntityStudentById(id);
		loadMainEntityByEntityStudent(entityStudent);
		return entityStudent;
	}
	
	public EntityStudent createEntityStudent(EntityStudent entityStudent) {
		//TODO: add to main entity object
		entityStudentDao.persist(entityStudent);
		return entityStudent;
	}
	
	public List<EntityStudent> getAllEntityStudent(){
		return entityStudentDao.getAllEntityStudent();
	}
	
	public void deleteEntityStudent(int id) {
		EntityStudent entityStudent = entityStudentDao.findEntityStudentById(id);
		if (entityStudent != null) {
			entityStudentDao.delete(entityStudent);
		}
	}
	
	public List<EntityStudent> findEntityStudentByName(String name){
		List<EntityStudent> results = entityStudentDao.getEntityStudentByFullName(name);
		return results;
	}
	
	public EntityStudent getEntityStudentByMainEntity(MainEntity mainEntity) {
		EntityStudent entityStudent = entityStudentDao.getEntityStudentByMainEntity(mainEntity);
		return entityStudent;
	}
	
	public void loadMainEntityByEntityStudent(EntityStudent entityStudent) {
		Hibernate.initialize(entityStudent.getMainEntity());
	}
	
	public void appendEntityStudentToMainEntity(MainEntity mainEntity, EntityStudent entityStudent) {
		if (mainEntity != null) {
			entityStudent.setMainEntity(mainEntity);
			entityStudentDao.merge(entityStudent);
		}
	}
	
	/*Entity Driver*/
	
	public EntityDriver findEntityDriverById(int id){
		EntityDriver entityDriver = entityDriverDao.findEntityDriverById(id);
		loadMainEntityByEntityDriver(entityDriver);
		return entityDriver;
	}
	
	public EntityDriver createEntityDriver(EntityDriver entityDriver) {
		//TODO: add to main entity object
		entityDriverDao.persist(entityDriver);
		return entityDriver;
	}
	
	public List<EntityDriver> getAllEntityDriver(){
		return entityDriverDao.getAllEntityDriver();
	}
	
	public void deleteEntityDriver(int id) {
		EntityDriver entityDriver = entityDriverDao.findEntityDriverById(id);
		if (entityDriver != null) {
			entityDriverDao.delete(entityDriver);
		}
	}
	
	public List<EntityDriver> findEntityDriverByName(String name){
		List<EntityDriver> results = entityDriverDao.getEntityDriverByFullName(name);
		return results;
	}
	
	public EntityDriver getEntityDriverByMainEntity(MainEntity mainEntity) {
		EntityDriver entityDriver = entityDriverDao.getEntityDriverByMainEntity(mainEntity);
		return entityDriver;
	}
	
	public void loadMainEntityByEntityDriver(EntityDriver entityDriver) {
		Hibernate.initialize(entityDriver.getMainEntity());
	}
	
	/*Entity Vehicle*/
	
	public EntityVehicle findEntityVehicleById(int id){
		EntityVehicle entityVehicle = entityVehicleDao.findEntityVehicleById(id);
		loadMainEntityByEntityVehicle(entityVehicle);
		return entityVehicle;
	}
	
	public EntityVehicle createEntityVehicle(EntityVehicle entityVehicle) {
		//TODO: add to main entity object
		entityVehicleDao.persist(entityVehicle);
		return entityVehicle;
	}
	
	public List<EntityVehicle> getAllEntityVehicle(){
		return entityVehicleDao.getAllEntityVehicle();
	}
	
	public void deleteEntityVehicle(int id) {
		EntityVehicle entityVehicle = entityVehicleDao.findEntityVehicleById(id);
		if (entityVehicle != null) {
			entityVehicleDao.delete(entityVehicle);
		}
	}
	
	public List<EntityVehicle> findEntityVehicleByName(String name){
		List<EntityVehicle> results = entityVehicleDao.getEntityVehicleByName(name);
		return results;
	}
	
	public EntityVehicle getEntityVehicleByMainEntity(MainEntity mainEntity) {
		EntityVehicle entityVehicle = entityVehicleDao.getEntityVehicleByMainEntity(mainEntity);
		return entityVehicle;
	}
	
	public void loadMainEntityByEntityVehicle(EntityVehicle entityVehicle) {
		Hibernate.initialize(entityVehicle.getMainEntity());
	}
	
}