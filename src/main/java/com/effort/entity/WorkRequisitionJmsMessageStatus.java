package com.effort.entity;


public class WorkRequisitionJmsMessageStatus {

	public static int UN_PROCESSED = 0;
	public static int PROCESSING = 5;
	public static int SUCCESS = 1;
	public static int FAILURE = -1;
	public static int UN_PROCESSED_TEMPORARY = 99;
	
	private long workRequisitionJmsMessageStatusId;
	private long entityId;
	private int type;
	private long id;
	private long companyId;
	private long empId;
	private int changeType;
	private Boolean byClient = false;
	private int status;
	private String createdTime;
	private String modifiedTime;
	private long formId;
	
	public WorkRequisitionJmsMessageStatus() {
		
	}
	public WorkRequisitionJmsMessageStatus(int type, long id, long companyId, long empId, int changeType,long entityId,
			Boolean byClient,long formId) {
		super();
		this.type = type;
		this.id = id;
		this.companyId = companyId;
		this.empId = empId;
		this.changeType = changeType;
		this.entityId = entityId;
		this.byClient = byClient;
		this.formId = formId;
	}
	
	public long getWorkRequisitionJmsMessageStatusId() {
		return workRequisitionJmsMessageStatusId;
	}
	public void setWorkRequisitionJmsMessageStatusId(long workRequisitionJmsMessageStatusId) {
		this.workRequisitionJmsMessageStatusId = workRequisitionJmsMessageStatusId;
	}
	public long getEntityId() {
		return entityId;
	}
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public int getChangeType() {
		return changeType;
	}
	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}
	public Boolean getByClient() {
		return byClient;
	}
	public void setByClient(Boolean byClient) {
		this.byClient = byClient;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public long getFormId() {
		return formId;
	}
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	
}
