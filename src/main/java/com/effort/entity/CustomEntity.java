package com.effort.entity;



import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class CustomEntity {

	private Long customEntityId;
	private String customEntityNo;
	private String customEntityName;
	private String customEntityLocation;
	private Long customEntitySpecId;
	//for report DayPlanner
	private String customEntitySpecName;
	private Long createdBy;
	private Long modifiedBy;
	private Long formId;
	private int companyId;
	private String createdTime;
	private String modifiedTime;
	private String clientSideId;
	private String clientCode;
	private Long parentCustomEntityId;
	private Long assignTo;
	private int deleted;
	
	private String contactEmailId;
	private boolean webLogin;

	private String externalId;
	private String externalCustomEntitySpecId;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String apiEntityIdFieldLabel;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String action;//add or modify
	@JsonProperty(access = Access.WRITE_ONLY)
	private String modifyStatus;//modified or No form change detected
	private boolean customEntityModify;
	
	private String empName;
	
	public static final int CUSTOM_ENTITY_ID = 1;
	public static final String CUSTOM_ENTITY_BULK_UPLOAD_UNIQUE_ID_PREFIX = "CE_";
	
	private String lastActivityName;
	private String lastActivityTime;
	private List<FormField> customEntityFields;
	
	private String customEntityFieldsUniqueKey;
	
	private Integer totalVists;
	private Integer visitCount;
	private Integer allocationDays;
	
	private String visitDay;
	private String visitDayGroup;
	private List<Long> groupedCustomEntityIds;
	
	private String clientFormId;
	
	private String customEntityTitle;
	private String checkInLocationTime;
	private String checkOutLocationTime;
	private String identifier;
	
	
	
	
	public String getLastActivityName() {
		return lastActivityName;
	}
	public void setLastActivityName(String lastActivityName) {
		this.lastActivityName = lastActivityName;
	}
	public String getLastActivityTime() {
		return lastActivityTime;
	}
	public void setLastActivityTime(String lastActivityTime) {
		this.lastActivityTime = lastActivityTime;
	}
	public Long getCustomEntityId() {
		return customEntityId;
	}
	public void setCustomEntityId(Long customEntityId) {
		this.customEntityId = customEntityId;
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

	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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
	public String getClientSideId() {
		return clientSideId;
	}
	public void setClientSideId(String clientSideId) {
		this.clientSideId = clientSideId;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public Long getParentCustomEntityId() {
		return parentCustomEntityId;
	}
	public void setParentCustomEntityId(Long parentCustomEntityId) {
		this.parentCustomEntityId = parentCustomEntityId;
	}
	public Long getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(Long assignTo) {
		this.assignTo = assignTo;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
		
	public String getCustomEntityLocation() {
		return customEntityLocation;
	}
	public void setCustomEntityLocation(String customEntityLocation) {
		this.customEntityLocation = customEntityLocation;
	}
	public Long getCustomEntitySpecId() {
		return customEntitySpecId;
	}
	public void setCustomEntitySpecId(Long customEntitySpecId) {
		this.customEntitySpecId = customEntitySpecId;
	}
	public String getCustomEntityNo() {
		return customEntityNo;
	}
	public void setCustomEntityNo(String customEntityNo) {
		this.customEntityNo = customEntityNo;
	}
	
	
	public String getCustomEntityName() {
		return customEntityName;
	}
	public void setCustomEntityName(String customEntityName) {
		this.customEntityName = customEntityName;
	}
	
	public String getContactEmailId() {
		return contactEmailId;
	}
	public void setContactEmailId(String contactEmailId) {
		this.contactEmailId = contactEmailId;
	}
	public boolean isWebLogin() {
		return webLogin;
	}
	public void setWebLogin(boolean webLogin) {
		this.webLogin = webLogin;
	}
	
	@Override
	public String toString() {
		return "CustomEntity [customEntityId=" + customEntityId
				+ ", customEntityNo=" + customEntityNo + ", customEntityName="
				+ customEntityName + ", customEntityLocation="
				+ customEntityLocation + ", customEntitySpecId="
				+ customEntitySpecId + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", formId=" + formId
				+ ", companyId=" + companyId + ", createdTime=" + createdTime
				+ ", modifiedTime=" + modifiedTime + ", clientSideId="
				+ clientSideId + ", clientCode=" + clientCode
				+ ", parentCustomEntityId=" + parentCustomEntityId
				+ ", assignTo=" + assignTo + ", deleted=" + deleted + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CustomEntity) {
			return getCustomEntityId() == ((CustomEntity) obj).getCustomEntityId();
		} else {
			return super.equals(obj);
		}
	}
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getExternalCustomEntitySpecId() {
		return externalCustomEntitySpecId;
	}
	public void setExternalCustomEntitySpecId(String externalCustomEntitySpecId) {
		this.externalCustomEntitySpecId = externalCustomEntitySpecId;
	}
	
	public String getApiEntityIdFieldLabel() {
		return apiEntityIdFieldLabel;
	}
	public void setApiEntityIdFieldLabel(String apiEntityIdFieldLabel) {
		this.apiEntityIdFieldLabel = apiEntityIdFieldLabel;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getModifyStatus() {
		return modifyStatus;
	}
	public void setModifyStatus(String modifyStatus) {
		this.modifyStatus = modifyStatus;
	}
	public boolean isCustomEntityModify() {
		return customEntityModify;
	}
	public void setCustomEntityModify(boolean customEntityModify) {
		this.customEntityModify = customEntityModify;
	}
	public String getCustomEntitySpecName() {
		return customEntitySpecName;
	}
	public void setCustomEntitySpecName(String customEntitySpecName) {
		this.customEntitySpecName = customEntitySpecName;
	}
	public List<FormField> getCustomEntityFields() {
		return customEntityFields;
	}
	public void setCustomEntityFields(List<FormField> customEntityFields) {
		this.customEntityFields = customEntityFields;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getCustomEntityFieldsUniqueKey() {
		return customEntityFieldsUniqueKey;
	}
	public void setCustomEntityFieldsUniqueKey(String customEntityFieldsUniqueKey) {
		this.customEntityFieldsUniqueKey = customEntityFieldsUniqueKey;
	}
	public Integer getTotalVists() {
		return totalVists;
	}
	public void setTotalVists(Integer totalVists) {
		this.totalVists = totalVists;
	}
	public Integer getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}
	public Integer getAllocationDays() {
		return allocationDays;
	}
	public void setAllocationDays(Integer allocationDays) {
		this.allocationDays = allocationDays;
	}
	public String getVisitDay() {
		return visitDay;
	}
	public void setVisitDay(String visitDay) {
		this.visitDay = visitDay;
	}
	public String getVisitDayGroup() {
		return visitDayGroup;
	}
	public void setVisitDayGroup(String visitDayGroup) {
		this.visitDayGroup = visitDayGroup;
	}
	public List<Long> getGroupedCustomEntityIds() {
		return groupedCustomEntityIds;
	}
	public void setGroupedCustomEntityIds(List<Long> groupedCustomEntityIds) {
		this.groupedCustomEntityIds = groupedCustomEntityIds;
	}
	public String getClientFormId() {
		return clientFormId;
	}
	public void setClientFormId(String clientFormId) {
		this.clientFormId = clientFormId;
	}
	public String getCustomEntityTitle() {
		return customEntityTitle;
	}
	public void setCustomEntityTitle(String customEntityTitle) {
		this.customEntityTitle = customEntityTitle;
	}
	public String getCheckInLocationTime() {
		return checkInLocationTime;
	}
	public void setCheckInLocationTime(String checkInLocationTime) {
		this.checkInLocationTime = checkInLocationTime;
	}
	public String getCheckOutLocationTime() {
		return checkOutLocationTime;
	}
	public void setCheckOutLocationTime(String checkOutLocationTime) {
		this.checkOutLocationTime = checkOutLocationTime;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	
	
	
	
}
