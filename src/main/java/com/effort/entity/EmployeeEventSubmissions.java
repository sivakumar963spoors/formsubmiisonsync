package com.effort.entity;


public class EmployeeEventSubmissions {

	public static final int EVENT_TYPE_FORM = 1;
	public static final int EVENT_TYPE_WORK = 2;
	
	public static final int PROESSING_STATUS_PENDING = 0;
	public static final int PROESSING_STATUS_PROCESSING = 5;
	public static final int PROESSING_STATUS_SUCCESS = 2;
	public static final int PROESSING_STATUS_INQUEUE = 3;
	public static final int PROESSING_STATUS_FAILED = -1;
	public static final int STATUS_TARGET_NOT_CONFIGURED = -2;
	
	
	private long id;
	private long companyId;
	private long empId;
	private String eventId;
	private int eventType;
	private long targetConfigurationId;
	private String createdTime;
	private String modifiedTime;
	private int processingStatus;
	private Integer processingTimeInMillis;
	private String remarks;
	
public EmployeeEventSubmissions() {}
	
	public EmployeeEventSubmissions(long companyId,
			long empId, String eventId, int eventType, long targetConfigurationId) {
		super();
		this.companyId = companyId;
		this.empId = empId;
		this.eventId = eventId;
		this.eventType = eventType;
		this.targetConfigurationId = targetConfigurationId;
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
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public long getTargetConfigurationId() {
		return targetConfigurationId;
	}
	public void setTargetConfigurationId(long targetConfigurationId) {
		this.targetConfigurationId = targetConfigurationId;
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
	public int getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(int processingStatus) {
		this.processingStatus = processingStatus;
	}
	public Integer getProcessingTimeInMillis() {
		return processingTimeInMillis;
	}
	public void setProcessingTimeInMillis(Integer processingTimeInMillis) {
		this.processingTimeInMillis = processingTimeInMillis;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
