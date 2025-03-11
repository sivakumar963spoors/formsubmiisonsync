package com.effort.entity;



import java.util.List;

public class EmployeeOnDemandMapping {
	private long id;
	private String formSpecUniqueId;
	private Integer companyId;
	private Integer triggerEvent; 
	private long createdBy;
	private long modifiedBy;
	private String createdTime;
	private String modifiedTime;
	private boolean deleted;
	private boolean sendPublicFormForApprovalForEmployeeReq;
	
	
	
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
	public Integer getTriggerEvent() {
		return triggerEvent;
	}
	public void setTriggerEvent(Integer triggerEvent) {
		this.triggerEvent = triggerEvent;
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
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isSendPublicFormForApprovalForEmployeeReq() {
		return sendPublicFormForApprovalForEmployeeReq;
	}
	public void setSendPublicFormForApprovalForEmployeeReq(boolean sendPublicFormForApprovalForEmployeeReq) {
		this.sendPublicFormForApprovalForEmployeeReq = sendPublicFormForApprovalForEmployeeReq;
	}
	
	
	

}
