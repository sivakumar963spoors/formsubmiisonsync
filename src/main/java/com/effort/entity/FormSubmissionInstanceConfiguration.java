package com.effort.entity;



public class FormSubmissionInstanceConfiguration {
	
	private Long formSubmissionInstanceConfigurationId;
	private String title;
	private String formUniqueInstanceUniqueId;
	private String closeFormUniqueId;
	private boolean selfCheck = true;
	private String failureMessage;
	private String createdTime;
	private String modifiedTime;
	private Long createdBy;
	private Long modifiedBy;
	private boolean deleted;
	private Long companyId;
	private String referenceFormUniqueIdCsv;
	
	
	public Long getFormSubmissionInstanceConfigurationId() {
		return formSubmissionInstanceConfigurationId;
	}
	public void setFormSubmissionInstanceConfigurationId(Long formSubmissionInstanceConfigurationId) {
		this.formSubmissionInstanceConfigurationId = formSubmissionInstanceConfigurationId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFormUniqueInstanceUniqueId() {
		return formUniqueInstanceUniqueId;
	}
	public void setFormUniqueInstanceUniqueId(String formUniqueInstanceUniqueId) {
		this.formUniqueInstanceUniqueId = formUniqueInstanceUniqueId;
	}
	public String getCloseFormUniqueId() {
		return closeFormUniqueId;
	}
	public void setCloseFormUniqueId(String closeFormUniqueId) {
		this.closeFormUniqueId = closeFormUniqueId;
	}
	public boolean isSelfCheck() {
		return selfCheck;
	}
	public void setSelfCheck(boolean selfCheck) {
		this.selfCheck = selfCheck;
	}
	public String getFailureMessage() {
		return failureMessage;
	}
	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
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
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getReferenceFormUniqueIdCsv() {
		return referenceFormUniqueIdCsv;
	}
	public void setReferenceFormUniqueIdCsv(String referenceFormUniqueIdCsv) {
		this.referenceFormUniqueIdCsv = referenceFormUniqueIdCsv;
	}
	
	
	
	
}
