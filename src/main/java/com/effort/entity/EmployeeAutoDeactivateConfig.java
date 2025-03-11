package com.effort.entity;


import java.util.List;



public class EmployeeAutoDeactivateConfig {

	public static final short SELECT_ALL_EMPS = 1;
	public static final short SELECT_INDVIDUAL_EMPS = 2;
	public static final short SELECT_EMP_GROUP = 3;
	
	public static final short ACCESS_TYPE_WEB_USER = 1;
	public static final short ACCESS_TYPE_MOBILE = 2;
	public static final short ACCESS_TYPE_BOTH = 3;
	
	private Long id;
	private Integer companyId;
	private Short disableEmployeeAccessType = 0;
	private Short disableThresholdValue;
	private Short blockEmployeeAccessType;
	private Short blockThresholdValue;
	private Short employeeSelectionType;
	private Long createdBy;
	private Long modifiedBy;
	private String createdTime;
	private String modifiedTime;
	private Short deleted;
	private String lastProcessedTime;
	
	private List<Long> employeeGroupIds;
	private List<Long>  employeeIds;
	
	private List<Employee> selectedEmployees;
	private List<EmployeeGroup> selectedEmployeeGroups;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Short getDisableEmployeeAccessType() {
		return disableEmployeeAccessType;
	}
	public void setDisableEmployeeAccessType(Short disableEmployeeAccessType) {
		this.disableEmployeeAccessType = disableEmployeeAccessType;
	}
	public Short getDisableThresholdValue() {
		return disableThresholdValue;
	}
	public void setDisableThresholdValue(Short disableThresholdValue) {
		this.disableThresholdValue = disableThresholdValue;
	}
	public Short getBlockEmployeeAccessType() {
		return blockEmployeeAccessType;
	}
	public void setBlockEmployeeAccessType(Short blockEmployeeAccessType) {
		this.blockEmployeeAccessType = blockEmployeeAccessType;
	}
	public Short getBlockThresholdValue() {
		return blockThresholdValue;
	}
	public void setBlockThresholdValue(Short blockThresholdValue) {
		this.blockThresholdValue = blockThresholdValue;
	}
	public Short getEmployeeSelectionType() {
		return employeeSelectionType;
	}
	public void setEmployeeSelectionType(Short employeeSelectionType) {
		this.employeeSelectionType = employeeSelectionType;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Short getDeleted() {
		return deleted;
	}
	public void setDeleted(Short deleted) {
		this.deleted = deleted;
	}
	public String getLastProcessedTime() {
		return lastProcessedTime;
	}
	public void setLastProcessedTime(String lastProcessedTime) {
		this.lastProcessedTime = lastProcessedTime;
	}
	public List<Long> getEmployeeGroupIds() {
		return employeeGroupIds;
	}
	public void setEmployeeGroupIds(List<Long> employeeGroupIds) {
		this.employeeGroupIds = employeeGroupIds;
	}
	public List<Long> getEmployeeIds() {
		return employeeIds;
	}
	public void setEmployeeIds(List<Long> employeeIds) {
		this.employeeIds = employeeIds;
	}
	public List<Employee> getSelectedEmployees() {
		return selectedEmployees;
	}
	public void setSelectedEmployees(List<Employee> selectedEmployees) {
		this.selectedEmployees = selectedEmployees;
	}
	public List<EmployeeGroup> getSelectedEmployeeGroups() {
		return selectedEmployeeGroups;
	}
	public void setSelectedEmployeeGroups(List<EmployeeGroup> selectedEmployeeGroups) {
		this.selectedEmployeeGroups = selectedEmployeeGroups;
	}
	
	
}
