package com.effort.entity;



public class FormSubmissionInstanceStatus {
	
	public static final int STATUS_OPEN = 1;
	public static final int STATUS_CLOSED = 2;
	public static final int STATUS_DELETED = -1;
	
	 private Long formSubmissionInstanceStatusId;
	 private Long formSubmissionInstanceConfigurationId;
	 private Long uniqueInstanceFormId;
	 private Long empId;
	 private String formUniqueInstanceSubmissionDate;
	 private Long closeFormId;
	 private Integer status;
	 private Long companyId;
	 private String clientSideId;
	 private String clientCode;
	 private String createdTime;
	 private String modifiedTime;
	 private String clientCreatedTime;
	 private String clientModifiedTime;
	 
	public Long getFormSubmissionInstanceStatusId() {
		return formSubmissionInstanceStatusId;
	}
	public void setFormSubmissionInstanceStatusId(Long formSubmissionInstanceStatusId) {
		this.formSubmissionInstanceStatusId = formSubmissionInstanceStatusId;
	}
	public Long getFormSubmissionInstanceConfigurationId() {
		return formSubmissionInstanceConfigurationId;
	}
	public void setFormSubmissionInstanceConfigurationId(Long formSubmissionInstanceConfigurationId) {
		this.formSubmissionInstanceConfigurationId = formSubmissionInstanceConfigurationId;
	}
	public Long getUniqueInstanceFormId() {
		return uniqueInstanceFormId;
	}
	public void setUniqueInstanceFormId(Long uniqueInstanceFormId) {
		this.uniqueInstanceFormId = uniqueInstanceFormId;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public String getFormUniqueInstanceSubmissionDate() {
		return formUniqueInstanceSubmissionDate;
	}
	public void setFormUniqueInstanceSubmissionDate(String formUniqueInstanceSubmissionDate) {
		this.formUniqueInstanceSubmissionDate = formUniqueInstanceSubmissionDate;
	}
	public Long getCloseFormId() {
		return closeFormId;
	}
	public void setCloseFormId(Long closeFormId) {
		this.closeFormId = closeFormId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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
	public String getClientCreatedTime() {
		return clientCreatedTime;
	}
	public void setClientCreatedTime(String clientCreatedTime) {
		this.clientCreatedTime = clientCreatedTime;
	}
	public String getClientModifiedTime() {
		return clientModifiedTime;
	}
	public void setClientModifiedTime(String clientModifiedTime) {
		this.clientModifiedTime = clientModifiedTime;
	}
	 
	 
	 

}
