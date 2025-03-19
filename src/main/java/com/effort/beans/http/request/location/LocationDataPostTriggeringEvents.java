package com.effort.beans.http.request.location;



public class LocationDataPostTriggeringEvents {
	
	public static final int PROCESSING_STATUS_PENDING = 0;
	public static final int PROCESSING_STATUS_FAILURE = -1;
	public static final int PROCESSING_STATUS_REST_API_NOT_FOUND = -2;
	public static final int PROCESSING_STATUS_PROCESSING = 5;
	public static final int PROCESSING_STATUS_SUCCESS = 2;
	public static final int PROCESSING_STATUS_PARTIAL_SUCCESS = 1;
	public static final int PROCESSING_STATUS_LOCATION_NOT_FOUND = -3;
	
	private Long trigeringEventId;
	private Long locationId;
	private Long empId;
	private Long companyId;
	private int eventType;
	private String createdTime;
	private String modifiedTime;
	
	public Long getTrigeringEventId() {
		return trigeringEventId;
	}
	public void setTrigeringEventId(Long trigeringEventId) {
		this.trigeringEventId = trigeringEventId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public int getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(int processingStatus) {
		this.processingStatus = processingStatus;
	}
	private int processingStatus;

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
	
	

}
