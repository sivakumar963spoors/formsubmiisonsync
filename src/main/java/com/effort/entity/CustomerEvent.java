package com.effort.entity;



import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerEvent {
	
	private long id;
	private Long customerId;
	private Long entityId;
	private Integer year;
	private int month;
	private int day;
	private Long createdBy;
	private String createdTime;
	private String clientSideId;
	private String clientCode;
	private String clientCustomerId;
	private boolean notified;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
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
	public String getClientCustomerId() {
		return clientCustomerId;
	}
	public void setClientCustomerId(String clientCustomerId) {
		this.clientCustomerId = clientCustomerId;
	}
	public boolean isNotified() {
		return notified;
	}
	public void setNotified(boolean notified) {
		this.notified = notified;
	}
	
	
	

}
