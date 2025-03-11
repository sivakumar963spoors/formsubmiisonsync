package com.effort.entity;



import java.util.List;

import com.effort.beans.http.response.extra.FormMainContainer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormHistoryForForm {

	private FormMainContainer formMainContainer;
	private List<Customer> customers;
	private List<Entity> entities;
	private List<EntityField> entityFields;
	private List<EntitySectionField> entitySectionFields;
	private List<Employee> employees;
	private List<Long> addedMappedEntityIds;
	private List<Long> deletedMappedEntityIds;
	private List<CustomEntity> customEntities;
	

	private List<String> orderedWorkIds;
	private Long unAssignedWorkIdsCount;
	private long totalCount;
	
	private List<WorkFlowFormStatusHistory>workflowFormStatusHistory;
	private List<WorkFlowFormStatus>   workflowFormStatus;

	@JsonProperty("forms")
	public FormMainContainer getFormMainContainer() {
		return formMainContainer;
	}

	public void setFormMainContainer(FormMainContainer formMainContainer) {
		this.formMainContainer = formMainContainer;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public List<EntityField> getEntityFields() {
		return entityFields;
	}

	public void setEntityFields(List<EntityField> entityFields) {
		this.entityFields = entityFields;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Long> getAddedMappedEntityIds() {
		return addedMappedEntityIds;
	}

	public void setAddedMappedEntityIds(List<Long> addedMappedEntityIds) {
		this.addedMappedEntityIds = addedMappedEntityIds;
	}

	public List<Long> getDeletedMappedEntityIds() {
		return deletedMappedEntityIds;
	}

	public void setDeletedMappedEntityIds(List<Long> deletedMappedEntityIds) {
		this.deletedMappedEntityIds = deletedMappedEntityIds;
	}



	public List<EntitySectionField> getEntitySectionFields() {
		return entitySectionFields;
	}

	public void setEntitySectionFields(List<EntitySectionField> entitySectionFields) {
		this.entitySectionFields = entitySectionFields;
	}

	public List<String> getOrderedWorkIds() {
		return orderedWorkIds;
	}

	public void setOrderedWorkIds(List<String> orderedWorkIds) {
		this.orderedWorkIds = orderedWorkIds;
	}

	public Long getUnAssignedWorkIdsCount() {
		return unAssignedWorkIdsCount;
	}

	public void setUnAssignedWorkIdsCount(Long unAssignedWorkIdsCount) {
		this.unAssignedWorkIdsCount = unAssignedWorkIdsCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<WorkFlowFormStatusHistory> getWorkflowFormStatusHistory() {
		return workflowFormStatusHistory;
	}

	public void setWorkflowFormStatusHistory(List<WorkFlowFormStatusHistory> workflowFormStatusHistory) {
		this.workflowFormStatusHistory = workflowFormStatusHistory;
	}

	public List<WorkFlowFormStatus> getWorkflowFormStatus() {
		return workflowFormStatus;
	}

	public void setWorkflowFormStatus(List<WorkFlowFormStatus> workflowFormStatus) {
		this.workflowFormStatus = workflowFormStatus;
	}
	public void setCustomEntities(List<CustomEntity> customEntities) {
		this.customEntities = customEntities;
	}
	
	

}
