package com.effort.entity;



public class EmpMasterMappingStatus {

	public final static int EMPLOYEE_EVENT_TRIGGER = 1; 
	public final static int CUSTOMER_EVENT_TRIGGER = 2; 
	public final static int CUSTOM_ENTITY_EVENT_TRIGGER = 3; 
	public final static int LIST_EVENT_TRIGGER = 4; 
	
	public final static int INQUEUE= 0; 
	public final static int INPROGRESS = 5; 
	public final static int FAILED = -1; 
	public final static int SUCCESS = 1;
	public final static int PARTIALLY_SUCCESSFUL = -2;
	
	private Long empMasterMappingStatusId;
	private Long companyId;
	private Integer triggerEventType;
	private Long masterId;
	private Integer mappingStatus;
	private Long empMasterMappingConfigurationId;
	private Long eventPerformedBy;
	private String eventCapturedTime;
	private String mappingProcessedTime;
	private String message;
	
	public EmpMasterMappingStatus() {
		
	}
	
	public EmpMasterMappingStatus(Long companyId, Integer triggerEventType, Long masterId,Long empMasterMappingConfigurationId, Long eventPerformedBy) {
		this.companyId = companyId;
		this.triggerEventType = triggerEventType;
		this.masterId = masterId;
		this.empMasterMappingConfigurationId = empMasterMappingConfigurationId;
		this.eventPerformedBy = eventPerformedBy;
	}
	
	
	
	
	@Override
	public String toString() {
		return "EmpMasterMappingStatus [empMasterMappingStatusId=" + empMasterMappingStatusId + ", companyId="
				+ companyId + ", triggerEventType=" + triggerEventType + ", masterId=" + masterId + ", mappingStatus="
				+ mappingStatus + ", eventPerformedBy=" + eventPerformedBy + ", eventCapturedTime=" + eventCapturedTime
				+ ", mappingProcessedTime=" + mappingProcessedTime + "]";
	}
	public Long getEmpMasterMappingStatusId() {
		return empMasterMappingStatusId;
	}
	public void setEmpMasterMappingStatusId(Long empMasterMappingStatusId) {
		this.empMasterMappingStatusId = empMasterMappingStatusId;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Integer getTriggerEventType() {
		return triggerEventType;
	}
	public void setTriggerEventType(Integer triggerEventType) {
		this.triggerEventType = triggerEventType;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public Integer getMappingStatus() {
		return mappingStatus;
	}
	public void setMappingStatus(Integer mappingStatus) {
		this.mappingStatus = mappingStatus;
	}
	public Long getEventPerformedBy() {
		return eventPerformedBy;
	}
	public void setEventPerformedBy(Long eventPerformedBy) {
		this.eventPerformedBy = eventPerformedBy;
	}
	public String getEventCapturedTime() {
		return eventCapturedTime;
	}
	public void setEventCapturedTime(String eventCapturedTime) {
		this.eventCapturedTime = eventCapturedTime;
	}
	public String getMappingProcessedTime() {
		return mappingProcessedTime;
	}
	public void setMappingProcessedTime(String mappingProcessedTime) {
		this.mappingProcessedTime = mappingProcessedTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getEmpMasterMappingConfigurationId() {
		return empMasterMappingConfigurationId;
	}

	public void setEmpMasterMappingConfigurationId(Long empMasterMappingConfigurationId) {
		this.empMasterMappingConfigurationId = empMasterMappingConfigurationId;
	}

	
}
