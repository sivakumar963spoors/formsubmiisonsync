package com.effort.entity;



public class FormListUpdate {

	private long formId;
	private int listUpdateStatus;
	private String lastModifiedTime;
	private String remarks;
	private Long companyId;
	private String createdTime;
	
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
	public int getListUpdateStatus() {
		return listUpdateStatus;
	}
	public void setListUpdateStatus(int listUpdateStatus) {
		this.listUpdateStatus = listUpdateStatus;
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
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	
	
	
}
