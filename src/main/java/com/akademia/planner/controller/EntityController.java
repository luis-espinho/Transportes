package com.akademia.planner.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akademia.planner.model.address.Address;
import com.akademia.planner.model.entity.EntityDriver;
import com.akademia.planner.model.entity.EntityStudent;
import com.akademia.planner.model.entity.EntityType;
import com.akademia.planner.model.entity.EntityVehicle;
import com.akademia.planner.model.entity.MainEntity;
import com.akademia.planner.service.EntityService;
import com.akademia.planner.service.MainService;

@Controller
@RequestMapping(value = { "/entity" })
public class EntityController {
	
	@Autowired
	private EntityService entityService;
	
	/*Entity Type*/
	
	@RequestMapping(value = { "/getEntityTypeById" }, method = RequestMethod.GET)
	public @ResponseBody EntityType getEntityTypeById(Integer id) {
		return entityService.findEntityTypeById(id);
	}
	
	@RequestMapping(value = {"/createEntityType"}, method = RequestMethod.POST)
	public @ResponseBody EntityType createEntityType(@Valid EntityType entityType) {
		return entityService.createEntityType(entityType);
	}
	
	@RequestMapping(value = { "/deleteEntityTypeById" }, method = RequestMethod.POST)
	public @ResponseBody String deleteEntityTypeById(Integer id) {
		entityService.deleteEntityType(id);
		return "Entity type removed from Database";
	}
	
	@RequestMapping(value = { "/getAllEntityType" }, method = RequestMethod.GET)
	public @ResponseBody List<EntityType> getAllEntityType() {
		return entityService.getAllEntityType();
	}
	
	/*Main Entity*/
	
	@RequestMapping(value = { "/getMainEntityById" }, method = RequestMethod.GET)
	public @ResponseBody MainEntity getMainEntityById(Integer id) {
		return entityService.findMainEntityById(id);
	}
	
	@RequestMapping(value = {"/createMainEntity"}, method = RequestMethod.POST)
	public @ResponseBody MainEntity createMainEntity(@Valid MainEntity mainEntity, int entityTypeId) {
		EntityType entityType = entityService.findEntityTypeById(entityTypeId);
		mainEntity.setEntityType(entityType);
		return entityService.createMainEntity(mainEntity);
	}
	
	@RequestMapping(value = {"/updateMainEntity"}, method = RequestMethod.POST)
	public @ResponseBody MainEntity updateMainEntity(@Valid MainEntity mainEntity, int updateId, int entityTypeId) {
		EntityType entityType = entityService.findEntityTypeById(entityTypeId);
		mainEntity.setEntityType(entityType);
		entityService.updateMainEntity(updateId, mainEntity);
		return mainEntity;
	}
	
	@RequestMapping(value = {"/updateMainEntityType"}, method = RequestMethod.POST)
	public @ResponseBody MainEntity updateMainEntityType(int entityId, int entityTypeId) {
		MainEntity mainEntity = entityService.findMainEntityById(entityId);
		EntityType entityType = entityService.findEntityTypeById(entityTypeId);
		entityService.updateMainEntityType(mainEntity, entityType);
		return mainEntity;
	}
	
	@RequestMapping(value = { "/getAllMainEntity" }, method = RequestMethod.GET)
	public @ResponseBody List<MainEntity> getAllMainEntity() {
		return entityService.getAllMainEntity();
	}
	
	@RequestMapping(value = { "/getMainEntityByName" }, method = RequestMethod.GET)
	public @ResponseBody List<MainEntity> getMainEntityByName(String name) {
		return entityService.findMainEntityByName(name);
	}
	
	@RequestMapping(value = { "/getMainEntityByEntityType" }, method = RequestMethod.GET)
	public @ResponseBody List<MainEntity> getMainEntityByName(int id) {
		EntityType entityType = entityService.findEntityTypeById(id);
		return entityService.getMainEntityByEntityType(entityType);
	}
	
	@RequestMapping(value = { "/deleteMainEntityById" }, method = RequestMethod.POST)
	public @ResponseBody String deleteMainEntityById(Integer id) {
		entityService.deleteMainEntity(id);
		return "Entity removed from Database";
	}
	
	/*Entity Student*/
	
	@RequestMapping(value = { "/getEntityStudentById" }, method = RequestMethod.GET)
	public @ResponseBody EntityStudent getEntityStudentById(Integer id) {
		return entityService.findEntityStudentById(id);
	}
	
	@RequestMapping(value = {"/createEntityStudent"}, method = RequestMethod.POST)
	public @ResponseBody EntityStudent createEntityStudent(@Valid EntityStudent entityStudent) {
		return entityService.createEntityStudent(entityStudent);
	}
	
	@RequestMapping(value = { "/getAllEntityStudent" }, method = RequestMethod.GET)
	public @ResponseBody List<EntityStudent> getAllEntityStudent() {
		return entityService.getAllEntityStudent();
	}
	
	@RequestMapping(value = { "/getEntityStudentByName" }, method = RequestMethod.GET)
	public @ResponseBody List<EntityStudent> getEntityStudentByName(String name) {
		return entityService.findEntityStudentByName(name);
	}
	
	@RequestMapping(value = { "/deleteEntityStudentById" }, method = RequestMethod.POST)
	public @ResponseBody String deleteEntityStudentById(Integer id) {
		entityService.deleteEntityStudent(id);
		return "Entity removed from Database";
	}
	
	@RequestMapping(value = { "/getMainEntityByEntityStudent" }, method = RequestMethod.GET)
	public @ResponseBody MainEntity getMainEntityByEntityStudent(Integer id) {
		//return main entity, no student entity loaded
		EntityStudent entityStudent = entityService.findEntityStudentById(id);
		return entityService.getMainEntityByEntityStudent(entityStudent);
	}
	
	@RequestMapping(value = { "/getEntityStudentByMainEntity" }, method = RequestMethod.GET)
	public @ResponseBody EntityStudent getEntityStudentByMainEntity(Integer id) {
		//return entity student, no main entity loaded
		MainEntity mainEntity = entityService.findMainEntityById(id);
		return entityService.getEntityStudentByMainEntity(mainEntity);
	}
	
	@RequestMapping(value = { "/appendEntityStudentToMainEntity" }, method = RequestMethod.POST)
	public @ResponseBody EntityStudent appendEntityStudentToMainEntity(Integer mainEntityId, Integer entityStudentId) {
		MainEntity mainEntity = entityService.findMainEntityById(mainEntityId);
		EntityStudent entityStudent = entityService.findEntityStudentById(entityStudentId);
		entityService.appendEntityStudentToMainEntity(mainEntity, entityStudent);
		return entityStudent;
	}
	
	/*Entity Driver*/
	
	@RequestMapping(value = { "/getEntityDriverById" }, method = RequestMethod.GET)
	public @ResponseBody EntityDriver getEntityDriverById(Integer id) {
		return entityService.findEntityDriverById(id);
	}
	
	@RequestMapping(value = {"/createEntityDriver"}, method = RequestMethod.POST)
	public @ResponseBody EntityDriver createEntityDriver(@Valid EntityDriver entityDriver) {
		return entityService.createEntityDriver(entityDriver);
	}
	
	@RequestMapping(value = { "/getAllEntityDriver" }, method = RequestMethod.GET)
	public @ResponseBody List<EntityDriver> getAllEntityDriver() {
		return entityService.getAllEntityDriver();
	}
	
	@RequestMapping(value = { "/getEntityDriverByName" }, method = RequestMethod.GET)
	public @ResponseBody List<EntityDriver> getEntityDriverByName(String name) {
		return entityService.findEntityDriverByName(name);
	}
	
	@RequestMapping(value = { "/deleteEntityDriverById" }, method = RequestMethod.POST)
	public @ResponseBody String deleteEntityDriverById(Integer id) {
		entityService.deleteEntityDriver(id);
		return "Entity removed from Database";
	}
	
	@RequestMapping(value = { "/getMainEntityByEntityDriver" }, method = RequestMethod.GET)
	public @ResponseBody MainEntity getMainEntityByEntityDriver(Integer id) {
		//return main entity, no driver entity loaded
		EntityDriver entityDriver = entityService.findEntityDriverById(id);
		return entityService.getMainEntityByEntityDriver(entityDriver);
	}
	
	@RequestMapping(value = { "/getEntityDriverByMainEntity" }, method = RequestMethod.GET)
	public @ResponseBody EntityDriver getEntityDriverByMainEntity(Integer id) {
		//return entity driver, no main entity loaded
		MainEntity mainEntity = entityService.findMainEntityById(id);
		return entityService.getEntityDriverByMainEntity(mainEntity);
	}
	
	/*Entity Vehicle*/
	
	@RequestMapping(value = { "/getEntityVehicleById" }, method = RequestMethod.GET)
	public @ResponseBody EntityVehicle getEntityVehicleById(Integer id) {
		return entityService.findEntityVehicleById(id);
	}
	
	@RequestMapping(value = {"/createEntityVehicle"}, method = RequestMethod.POST)
	public @ResponseBody EntityVehicle createEntityVehicle(@Valid EntityVehicle entityVehicle) {
		return entityService.createEntityVehicle(entityVehicle);
	}
	
	@RequestMapping(value = { "/getAllEntityVehicle" }, method = RequestMethod.GET)
	public @ResponseBody List<EntityVehicle> getAllEntityVehicle() {
		return entityService.getAllEntityVehicle();
	}
	
	@RequestMapping(value = { "/getEntityVehicleByName" }, method = RequestMethod.GET)
	public @ResponseBody List<EntityVehicle> getEntityVehicleByName(String name) {
		return entityService.findEntityVehicleByName(name);
	}
	
	@RequestMapping(value = { "/deleteEntityVehicleById" }, method = RequestMethod.POST)
	public @ResponseBody String deleteEntityVehicleById(Integer id) {
		entityService.deleteEntityVehicle(id);
		return "Entity removed from Database";
	}
	
	@RequestMapping(value = { "/getMainEntityByEntityVehicle" }, method = RequestMethod.GET)
	public @ResponseBody MainEntity getMainEntityByEntityVehicle(Integer id) {
		//return main entity, no vehicle entity loaded
		EntityVehicle entityVehicle = entityService.findEntityVehicleById(id);
		return entityService.getMainEntityByEntityVehicle(entityVehicle);
	}
	
	@RequestMapping(value = { "/getEntityVehicleByMainEntity" }, method = RequestMethod.GET)
	public @ResponseBody EntityVehicle getEntityVehicleByMainEntity(Integer id) {
		//return entity vehicle, no main entity loaded
		MainEntity mainEntity = entityService.findMainEntityById(id);
		return entityService.getEntityVehicleByMainEntity(mainEntity);
	}
}