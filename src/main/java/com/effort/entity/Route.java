package com.effort.entity;

import java.util.List;

public class Route {

	private Long routeId;
	private Long companyId;
	private String companyName;
	private String routeName;
	private Long createdBy;
	private String createdByName;
	private Long modifiedBy;
	private String modifiedByName;
	private Integer isDeleted;
	private Integer isAssigned;
	private Integer duration;
	private String creationTime;
	private String modifiedTime;
	private Long assignedTo;
	private String assignedToName;
	private Long locationId;
	private Integer status;
	private String completedTime;
	private String completedAt;
	private String startDate;
	private String endDate;
    private Integer minCustomersToComplete=1;
    private String remarks;
	private Integer minNightsToStay = 0;
	private String routeNumber;
	private Integer order;
	
	private List<Long> routeCustomers;
	
	private int routeCustomersCount;
	
	private String overview_polyline;
	private String response;
    
	private String fromCustomer;
	private long startCustomerId;
	

	private  Integer bufferTimeForAutoCompleteInDays;
	private boolean autoComplete;
	
	public static final int TYPE_CREATE = 0;
	public static final int TYPE_MODIFY = 1;
	public static final int TYPE_REPLACE = 2;
	
    
	public Long getRouteId() {
		return routeId;
	}
	



	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Integer isAssigned) {
		this.isAssigned = isAssigned;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Long getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getAssignedToName() {
		return assignedToName;
	}

	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCompletedTime() {
		return completedTime;
	}

	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}

	public String getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(String completedAt) {
		this.completedAt = completedAt;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Integer getMinCustomersToComplete() {
		return minCustomersToComplete;
	}

	public void setMinCustomersToComplete(Integer minCustomersToComplete) {
		this.minCustomersToComplete = minCustomersToComplete;
	}
	
	public Integer getMinNightsToStay() {
		return minNightsToStay;
	}
	
	public void setMinNightsToStay(Integer minNightsToStay) {
		this.minNightsToStay = minNightsToStay;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

	public List<Long> getRouteCustomers() {
		return routeCustomers;
	}

	public void setRouteCustomers(List<Long> routeCustomers) {
		this.routeCustomers = routeCustomers;
	}

	
	public String getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(String routeNumber) {
		this.routeNumber = routeNumber;
	}
	
	public int getRouteCustomersCount() {
		return routeCustomersCount;
	}

	public void setRouteCustomersCount(int routeCustomersCount) {
		this.routeCustomersCount = routeCustomersCount;
	}
	
	

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	
	public String getOverview_polyline() {
		return overview_polyline;
	}

	public void setOverview_polyline(String overview_polyline) {
		this.overview_polyline = overview_polyline;
	}

	@Override
	public String toString() {
		return "Route [routeId=" + routeId + ", companyId=" + companyId
				+ ", companyName=" + companyName + ", routeName=" + routeName
				+ ", createdBy=" + createdBy + ", createdByName="
				+ createdByName + ", modifiedBy=" + modifiedBy
				+ ", modifiedByName=" + modifiedByName + ", isDeleted="
				+ isDeleted + ", isAssigned=" + isAssigned + ", duration="
				+ duration + ", creationTime=" + creationTime
				+ ", modifiedTime=" + modifiedTime + ", assignedTo="
				+ assignedTo + ", assignedToName=" + assignedToName
				+ ", locationId=" + locationId + ", status=" + status
				+ ", completedTime=" + completedTime + ", completedAt="
				+ completedAt + ", startDate=" + startDate + ", endDate="
				+ endDate + "routeNumber="+routeNumber+"]";
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getFromCustomer() {
		return fromCustomer;
	}

	public void setFromCustomer(String fromCustomer) {
		this.fromCustomer = fromCustomer;
	}

	public long getStartCustomerId() {
		return startCustomerId;
	}

	public void setStartCustomerId(long startCustomerId) {
		this.startCustomerId = startCustomerId;
	}
	
	public Integer getBufferTimeForAutoCompleteInDays() {
		return bufferTimeForAutoCompleteInDays;
	}

	public void setBufferTimeForAutoCompleteInDays(Integer bufferTimeForAutoCompleteInDays) {
		this.bufferTimeForAutoCompleteInDays = bufferTimeForAutoCompleteInDays;
	}

	public boolean isAutoComplete() {
		return autoComplete;
	}

	public void setAutoComplete(boolean autoComplete) {
		this.autoComplete = autoComplete;
	}
	

}
