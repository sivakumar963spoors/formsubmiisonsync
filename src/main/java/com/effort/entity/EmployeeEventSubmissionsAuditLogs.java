package com.effort.entity;


public class EmployeeEventSubmissionsAuditLogs {

	public static final int EVENT_TYPE_FORM = 1;
	public static final int EVENT_TYPE_WORK = 2;
	
	private long auditId;
	private long companyId;
	private long empId;
	private String eventId;
	private int eventType;
	private long targetConfigurationId;
	private String createdTime;
	private String modifiedTime;
	private long auditBy;
	private long auditTime;
	
public EmployeeEventSubmissionsAuditLogs() {}
	
	public EmployeeEventSubmissionsAuditLogs(long companyId,
			long empId, String eventId, int eventType, long targetConfigurationId,long auditBy) {
		super();
		this.companyId = companyId;
		this.empId = empId;
		this.eventId = eventId;
		this.eventType = eventType;
		this.targetConfigurationId = targetConfigurationId;
		this.auditBy = auditBy;
	}
	
	public long getAuditId() {
		return auditId;
	}
	public void setAuditId(long auditId) {
		this.auditId = auditId;
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
	public long getAuditBy() {
		return auditBy;
	}
	public void setAuditBy(long auditBy) {
		this.auditBy = auditBy;
	}
	public long getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(long auditTime) {
		this.auditTime = auditTime;
	}
	
	
}
