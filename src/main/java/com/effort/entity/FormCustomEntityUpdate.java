package com.effort.entity;


public class FormCustomEntityUpdate {
	private long formId;
	private int customEntityUpdateStatus;
	private String createdTime;
	private String lastModifiedTime;
	private String remarks;
	private int retryCount;
	private Long companyId;
	
	public static final int INQUEUE = 0;
	public static final int PROCESSING = 5;
	public static final int SUCCESS = 1;
	public static final int FAILED = -1;
	
	public long getFormId() {
		return formId;
	}
	public void setFormId(long formId) {
		this.formId = formId;
	}
	public int getCustomEntityUpdateStatus() {
		return customEntityUpdateStatus;
	}
	public void setCustomEntityUpdateStatus(int customEntityUpdateStatus) {
		this.customEntityUpdateStatus = customEntityUpdateStatus;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	 
}
