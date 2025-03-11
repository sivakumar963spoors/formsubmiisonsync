package com.effort.entity;



import java.util.List;

public class CustomerOnDemandMapping {

	private long id;
	private String formSpecUniqueId;
	private Integer companyId;
	private Integer triggerEvent; 
	private long createdBy;
	private long modifiedBy;
	private String createdTime;
	private String modifiedTime;
	private String mapToOpp;
	private boolean updateCustomerOnModify;
	private Integer updateTriggerEvent;
	private String updateColumnUniqueId;
	private int changeType;
	private String customerRequisitionVisibilityConditionJson;
	
	private boolean sendPublicFormForApprovalForCustomerReq;
	private boolean hideActivity;
	private String customerActivityVisibilityDependencyCriteriaJson;



	public static final int CREATE = 1;
	public static final int UPDATE = 2;
	
	public static final int CUSTOMER_FIELD_TYPE = 7;
	
	public int getChangeType() {
		return changeType;
	}
	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}
	public String getUpdateColumnUniqueId() {
		return updateColumnUniqueId;
	}
	public void setUpdateColumnUniqueId(String updateColumnUniqueId) {
		this.updateColumnUniqueId = updateColumnUniqueId;
	}
	
	public static final int TRIGGER_EVENT_TYPE_APPROVED_FORM = 1;
	public static final int TRIGGER_EVENT_TYPE_SUBMITTED_FORM = 2;
	public static final int TRIGGER_EVENT_TYPE_MODIFIED_FORM = 3;
	public static final int UNPROCESSED_STATUS = 0;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(long modifiedBy) {
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
	
	public String getMapToOpp() {
		return mapToOpp;
	}
	public void setMapToOpp(String mapToOpp) {
		this.mapToOpp = mapToOpp;
	}
	public Integer getTriggerEvent() {
		return triggerEvent;
	}
	public void setTriggerEvent(Integer triggerEvent) {
		this.triggerEvent = triggerEvent;
	}
	public Integer getUpdateTriggerEvent() {
		return updateTriggerEvent;
	}
	public void setUpdateTriggerEvent(Integer updateTriggerEvent) {
		this.updateTriggerEvent = updateTriggerEvent;
	}
	public boolean isUpdateCustomerOnModify() {
		return updateCustomerOnModify;
	}
	public void setUpdateCustomerOnModify(boolean updateCustomerOnModify) {
		this.updateCustomerOnModify = updateCustomerOnModify;
	}
	public String getCustomerRequisitionVisibilityConditionJson() {
		return customerRequisitionVisibilityConditionJson;
	}
	public void setCustomerRequisitionVisibilityConditionJson(String customerRequisitionVisibilityConditionJson) {
		this.customerRequisitionVisibilityConditionJson = customerRequisitionVisibilityConditionJson;
	}

	public boolean isSendPublicFormForApprovalForCustomerReq() {
		return sendPublicFormForApprovalForCustomerReq;
	}
	public void setSendPublicFormForApprovalForCustomerReq(boolean sendPublicFormForApprovalForCustomerReq) {
		this.sendPublicFormForApprovalForCustomerReq = sendPublicFormForApprovalForCustomerReq;
	}
	public boolean isHideActivity() {
		return hideActivity;
	}
	public void setHideActivity(boolean hideActivity) {
		this.hideActivity = hideActivity;
	}
	public String getCustomerActivityVisibilityDependencyCriteriaJson() {
		return customerActivityVisibilityDependencyCriteriaJson;
	}
	public void setCustomerActivityVisibilityDependencyCriteriaJson(
			String customerActivityVisibilityDependencyCriteriaJson) {
		this.customerActivityVisibilityDependencyCriteriaJson = customerActivityVisibilityDependencyCriteriaJson;
	}

	
	
	
}