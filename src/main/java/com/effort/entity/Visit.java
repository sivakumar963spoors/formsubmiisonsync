package com.effort.entity;


import java.text.ParseException;

import javax.xml.bind.DatatypeConverter;

import com.effort.util.Api;
import com.effort.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Visit {
public static final int PRIORITY_HIGH=1;
public static final int PRIORITY_MEDIUM=2;
public static final int PRIORITY_LOW=3;


	private long visitId;
	private long visitTypeId;
	private long visitStateId;
	private String clientVisitId;
	private long empId;
	private String clientEmpId;
	private String empVisitTitle;
	private String empVisitDesc;
	@JsonProperty(access = Access.READ_ONLY)
	private Long customerId;
	private String clientCustomerId;
	private Customer customer;
	private String visitStartTime;
	private String visitEndTime;
	private boolean completed;
	private String completeTime;
	private boolean approved;
	private Long createdBy;
	private int type;
	private String modifiedTime;
	private String createTime;
	private boolean deleted;
	
	private long completedJobCount;
	private String jobDetails;
	
	private boolean addressSameAsCustomer = true;
	private String addressStreet;
	private String addressArea;
	private String addressCity;
	private String addressPincode;
	private String addressLandMark;
	private String addressState;
	private String addressCountry;
	private String addressLat;
	private String addressLng;
	
	private String createdByName;
	
	private String empFirstName;
	private String empLastName;
	private String overdueTimeStr;
	
	private String visitTypeValue;
	private String visitStateValue;
	
	private String externalId;
	private String apiUserId;
	
	private String customerExternalId;
	private String jobTypeExternalId;
	
	private String token;
	private String mapToken;
	
	private Location customerLocation;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String maptype="1";
	private String customerName;
	private String trunAroundTime;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String jobLabel="Job";
	@JsonProperty(access = Access.WRITE_ONLY)
	private String customerLabel="Customer";
	@JsonProperty(access = Access.WRITE_ONLY)
	private String distanceFromCustomer;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String jobStatus;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer createdByTzo = null;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer timezoneId = null;
	private Long parentVisitId;
	private String clientParentVisitId;
	private String phoneNo;
	private int priority=2;
	private Long empVisitFormId;
	@JsonProperty(access = Access.WRITE_ONLY)
	private FormAndField formAndField;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String error;
	private String clientEmpFormId;
	private boolean visitTypeServiveCall;
	
	private int frequency;
	private boolean checkRecurring;
	
	private Long recurringParentId;
	private boolean isTemplate=false;
	private boolean rejected=false;
	private String rejectedOn;
	
	private String track;
	private String trackSms;

	
	
	// for customer history
	private String startTime;
	private String endTime;
	private String completedTime;
	private String employeeName;
		
	// new added column 
	private long companyIdForVisit;
	private String priorityName;
	private String customerAddress;
	
	private String skillEntityIds;
	
	private Long duration;
	
	private Long completedCheckInId;
	
	private String startTimeToDisplay;
	private String endTimeToDisplay;
	
	private boolean forcePerformJob;
	private String recurrenceDays;
	private String recurringEndDate;
	
	private boolean openMap = false;
	
	public long getCompanyIdForVisit() {
		return companyIdForVisit;
	}
	public void setCompanyIdForVisit(long companyIdForVisit) {
		this.companyIdForVisit = companyIdForVisit;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getEmpVisitFormId() {
		return empVisitFormId;
	}
	public void setEmpVisitFormId(Long empVisitFormId) {
		this.empVisitFormId = empVisitFormId;
	}
	
	public String getDistanceFromCustomer() {
		return distanceFromCustomer;
	}
	public void setDistanceFromCustomer(String distanceFromCustomer) {
		this.distanceFromCustomer = distanceFromCustomer;
	}
	
	
	public String getTrunAroundTime() {
		return trunAroundTime;
	}
	public void setTrunAroundTime(String trunAroundTime) {
		this.trunAroundTime = trunAroundTime;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getMaptype() {
		return maptype;
	}
	public void setMaptype(String maptype) {
		this.maptype = maptype;
	}
	public long getVisitId() {
		return visitId;
	}
	public void setVisitId(long visitId) {
		this.visitId = visitId;
	}
	public long getVisitTypeId() {
		return visitTypeId;
	}
	public void setVisitTypeId(long visitTypeId) {
		this.visitTypeId = visitTypeId;
	}
	public long getVisitStateId() {
		return visitStateId;
	}
	public void setVisitStateId(long visitStateId) {
		this.visitStateId = visitStateId;
	}
	public String getClientVisitId() {
		return clientVisitId;
	}
	public void setClientVisitId(String clientVisitId) {
		this.clientVisitId = clientVisitId;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public String getClientEmpId() {
		return clientEmpId;
	}
	public void setClientEmpId(String clientEmpId) {
		this.clientEmpId = clientEmpId;
	}
	public String getEmpVisitTitle() {
		return empVisitTitle;
	}
	public void setEmpVisitTitle(String empVisitTitle) {
		this.empVisitTitle = empVisitTitle;
	}
	public String getEmpVisitDesc() {
		return empVisitDesc;
	}
	public void setEmpVisitDesc(String empVisitDesc) {
		this.empVisitDesc = empVisitDesc;
	}
	public long getCustomerId() {
		if(customerId == null){
			return 0;
		} else {
			return customerId;
		}
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public void setCustomerId(Long customerId) {
		if(customerId == null){
			this.customerId = -1l;
		} else {
			this.customerId = customerId;
		}
	}
	public String getClientCustomerId() {
		return clientCustomerId;
	}
	public void setClientCustomerId(String clientCustomerId) {
		this.clientCustomerId = clientCustomerId;
	}
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getVisitStartTime() {
		return visitStartTime;
	}
	public void setVisitStartTime(String visitStartTime) {
		String temp = visitStartTime;
		if(!Api.isEmptyString(visitStartTime) && !visitStartTime.endsWith("M")){
			try {
				visitStartTime = Api.toUtcXsd(visitStartTime);
				if(temp.contains("."))
				setStartTimeToDisplay(temp.substring(0,temp.lastIndexOf(".")));
				else
				setStartTimeToDisplay(temp);
					
			} catch (Exception e) {
				Log.ignore(this.getClass(), e);
			}
		}
		this.visitStartTime = visitStartTime;
	}
	public String getVisitEndTime() {
		return visitEndTime;
	}
	public void setVisitEndTime(String visitEndTime) {
		String temp = visitEndTime;
		if(!Api.isEmptyString(visitEndTime) && !visitEndTime.endsWith("M")){
			try {
				visitEndTime = Api.toUtcXsd(visitEndTime);
				if(temp.contains("."))
				setEndTimeToDisplay(temp.substring(0,temp.lastIndexOf(".")));
				else
				setEndTimeToDisplay(temp);
			} catch (Exception e) {
				Log.info(this.getClass(), e.toString());
			}
		}
		this.visitEndTime = visitEndTime;
	}
	public Boolean isCompleted() {
		return completed;
	}
	
	public String getCompletedStatus() {
		if (completed) {
			return "1";
		} else {

			if (getJobStatus() != null) {
				return getJobStatus();
			} else {
				return "3";
			}

		}
	}
	
	
	
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		if(!Api.isEmptyString(completeTime)){
			try {
				completeTime = Api.toUtcXsd(completeTime);
			} catch (Exception e) {
				Log.ignore(this.getClass(), e);
			}
		}
		this.completeTime = completeTime;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public boolean isAddressSameAsCustomer() {
		return addressSameAsCustomer;
	}
	public void setAddressSameAsCustomer(boolean addressSameAsCustomer) {
		this.addressSameAsCustomer = addressSameAsCustomer;
	}
	
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	
	public String getAddressArea() {
		return addressArea;
	}
	public void setAddressArea(String addressArea) {
		this.addressArea = addressArea;
	}
	
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	
	public String getAddressPincode() {
		return addressPincode;
	}
	public void setAddressPincode(String addressPincode) {
		this.addressPincode = addressPincode;
	}
	
	public String getAddressLandMark() {
		return addressLandMark;
	}
	public void setAddressLandMark(String addressLandMark) {
		this.addressLandMark = addressLandMark;
	}
	
	public String getAddressState() {
		return addressState;
	}
	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}
	
	public String getAddressCountry() {
		return addressCountry;
	}
	public void setAddressCountry(String addressCountry) {
		this.addressCountry = addressCountry;
	}
	
	public String getAddressLat() {
		return addressLat;
	}
	public void setAddressLat(String addressLat) {
		this.addressLat = addressLat;
	}
	
	public String getAddressLng() {
		return addressLng;
	}
	public void setAddressLng(String addressLng) {
		this.addressLng = addressLng;
	}
	
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		if(!Api.isEmptyString(modifiedTime)){
			try {
				if(!modifiedTime.contains("T")){
					modifiedTime = Api.toUtcXsd(modifiedTime);
				}
			} catch (Exception e) {
				Log.ignore(this.getClass(), e);
			}
		}
		this.modifiedTime = modifiedTime;
	}
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	public String getClientEmpFormId() {
		return clientEmpFormId;
	}
	public void setClientEmpFormId(String clientEmpFormId) {
		this.clientEmpFormId = clientEmpFormId;
	}
	
//	public void setCompleted(boolean completed) {
//		this.completed = completed;
//	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getAddress(){
		String address = "";
		
		if(!Api.isEmptyString(addressStreet)){
			address += addressStreet;
		}
		
		if(!Api.isEmptyString(addressArea)){
			if(address.length() > 0){
				address += ", ";
			}
			address += addressArea;
		}
		
		if(!Api.isEmptyString(addressCity)){
			if(address.length() > 0){
				address += ", ";
			}
			address += addressCity;
		}
		
		if(!Api.isEmptyString(addressPincode)){
			if(address.length() > 0){
				address += ", ";
			}
			address += addressPincode;
		}
		return address;
	}
	
	
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getToken() {
		return token;
	}
	@JsonProperty(access = Access.READ_ONLY)
	public void setToken(String token) {
		this.token = token;
	}
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getMapToken() {
		return mapToken;
	}
	@JsonProperty(access = Access.READ_ONLY)
	public void setMapToken(String mapToken) {
		this.mapToken = mapToken;
	}
	
	@JsonIgnore
	public String getCreateTime() {
		return createTime;
	}
	@JsonIgnore
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@JsonIgnore
	public String getApiUserId() {
		return apiUserId;
	}
	@JsonIgnore
	public void setApiUserId(String apiUserId) {
		this.apiUserId = apiUserId;
	}
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getCustomerExternalId() {
		return customerExternalId;
	}
	public void setCustomerExternalId(String customerExternalId) {
		this.customerExternalId = customerExternalId;
	}
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getJobTypeExternalId() {
		return jobTypeExternalId;
	}
	public void setJobTypeExternalId(String jobTypeExternalId) {
		this.jobTypeExternalId = jobTypeExternalId;
	}
	
	
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	@JsonIgnore
	public boolean isJobCompleted() {
//		if(completed != null){
			return completed;
//		} else {
//			return false;
//		}
	}
	
	@JsonIgnore
	public void setJobCompleted(boolean completed) {
		this.completed = completed;
	}
	
	@JsonIgnore
	public int getType() {
		return type;
	}
	@JsonIgnore
	public void setType(int type) {
		this.type = type;
	}
	
	@JsonIgnore
	public String getEmpFirstName() {
		return empFirstName;
	}
	@JsonIgnore
	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}
	@JsonIgnore
	public String getEmpLastName() {
		return empLastName;
	}
	@JsonIgnore
	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}
	@JsonIgnore
	public String getOverdueTimeStr() {
		return overdueTimeStr;
	}
	@JsonIgnore
	public void setOverdueTimeStr(String overdueTimeStr) {
		this.overdueTimeStr = overdueTimeStr;
	}
//	@JsonIgnore
	public String getVisitTypeValue() {
		return visitTypeValue;
	}
//	@JsonIgnore
	public void setVisitTypeValue(String visitTypeValue) {
		this.visitTypeValue = visitTypeValue;
	}
	
	public String getVisitStateValue() {
		return visitStateValue;
	}
	
	public void setVisitStateValue(String visitStateValue) {
		this.visitStateValue = visitStateValue;
	}
	@JsonIgnore
	public Location getCustomerLocation() {
		return customerLocation;
	}
	@JsonIgnore
	public void setCustomerLocation(Location customerLocation) {
		this.customerLocation = customerLocation;
	}
	@JsonProperty(access = Access.READ_ONLY)
	public void setST(String tzo){
		/*if(visitStartTime != null && visitStartTime.length() > 0){
			visitStartTime = Api.getDateTimeInTz(DatatypeConverter.parseDateTime(visitStartTime), tzo);
		}*/
		
		if(visitStartTime != null && visitStartTime.length() > 0){
			visitStartTime = Api.getTimeZoneDates(visitStartTime, tzo,createdByTzo+"");
		}
		
		if(startTimeToDisplay != null && startTimeToDisplay.length()>0){
			startTimeToDisplay = Api.getDateTimeInTz24(startTimeToDisplay, tzo);
		}
	}
	
	@JsonProperty(access = Access.READ_ONLY)
	public void setET(String tzo){
		/*if(visitEndTime != null && visitEndTime.length() > 0){
			visitEndTime = Api.getDateTimeInTz(DatatypeConverter.parseDateTime(visitEndTime), tzo);
		}*/
		if(visitEndTime != null && visitEndTime.length() > 0){
			visitEndTime = Api.getTimeZoneDates(visitEndTime, tzo,createdByTzo+"");
		}
		
		if(endTimeToDisplay != null && endTimeToDisplay.length()>0){
			endTimeToDisplay = Api.getDateTimeInTz24(endTimeToDisplay, tzo);
			
		}
	}
	
	@JsonProperty(access = Access.READ_ONLY)
	public void setCT(String tzo){
		/*if(completeTime != null && completeTime.length() > 0){
			completeTime = Api.getDateTimeInTz(DatatypeConverter.parseDateTime(completeTime), tzo);
		}*/
		if(completeTime != null && completeTime.length() > 0){
			completeTime = Api.getTimeZoneDates(completeTime, tzo,createdByTzo+"");
		}
	}
	@JsonProperty(access = Access.READ_ONLY)
	public void setSTOld(String tzo){
		if(visitStartTime != null && visitStartTime.length() > 0){
			visitStartTime = Api.getDateTimeInTz(DatatypeConverter.parseDateTime(visitStartTime), tzo, 1);
		}
	}
	
	@JsonProperty(access = Access.READ_ONLY)
	public void setETOld(String tzo){
		if(visitEndTime != null && visitEndTime.length() > 0){
			visitEndTime = Api.getDateTimeInTz(DatatypeConverter.parseDateTime(visitEndTime), tzo, 1);
		}
	}
	
	@JsonProperty(access = Access.READ_ONLY)
	public void setCTOld(String tzo){
		if(completeTime != null && completeTime.length() > 0){
			completeTime = Api.getDateTimeInTz(DatatypeConverter.parseDateTime(completeTime), tzo, 1);
		}
	}
	@JsonProperty(access = Access.READ_ONLY)
	public void setST24(String tzo){
		if(visitStartTime != null && visitStartTime.length() > 0){
			visitStartTime = Api.getDateTimeInTz24(DatatypeConverter.parseDateTime(visitStartTime), tzo);
		}
	}
	
	@JsonProperty(access = Access.READ_ONLY)
	public void setET24(String tzo){
		if(visitEndTime != null && visitEndTime.length() > 0){
			visitEndTime = Api.getDateTimeInTz24(DatatypeConverter.parseDateTime(visitEndTime), tzo);
		}
	}
	
	@JsonProperty(access = Access.READ_ONLY)
	public void setCT24(String tzo){
		if(completeTime != null && completeTime.length() > 0){
			completeTime = Api.getDateTimeInTz24(DatatypeConverter.parseDateTime(completeTime), tzo);
		}
	}
	
	public void diffForApprove(Visit visit){
		this.empId = this.empId != visit.getEmpId() ? this.empId : null;
		this.customerId = this.customerId != visit.getCustomerId() ? this.customerId : null;
		this.empVisitTitle = this.empVisitTitle.equals(visit.getEmpVisitTitle()) ? this.empVisitTitle : null;
		this.empVisitDesc = this.empVisitDesc.equals(visit.getEmpVisitDesc()) ? this.empVisitDesc : null;
		this.visitStartTime = this.visitStartTime.equals(visit.getVisitStartTime()) ? this.visitStartTime : null;
		this.visitEndTime = this.visitEndTime.equals(visit.getVisitEndTime()) ? this.visitEndTime : null;
		this.completed = this.completed != visit.isCompleted() ? this.completed : null;
		this.completeTime = this.completeTime.equals(visit.getCompleteTime()) ? this.completeTime : null;
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String getTime(){
		String time = "";
		if(!Api.isEmptyString(getVisitStartTime())){
			time += getVisitStartTime();
			
			if(!Api.isEmptyString(getVisitEndTime())){
				time += " to " + getVisitEndTime();
			}
		}
		
		return time;
	}
	
	
	public String getJobLabel() {
		return jobLabel;
	}
	public void setJobLabel(String jobLabel) {
		this.jobLabel = jobLabel;
	}
	
	
	public String getCustomerLabel() {
		return customerLabel;
	}
	public void setCustomerLabel(String customerLabel) {
		this.customerLabel = customerLabel;
	}
	
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getCustName(){
		String custName= "";
		
		if(customer!=null){
			custName=getCustomerLabel()+":";
			custName+=Api.escapChar(Api.makeEmptyIfNull(Api.replaceAllNewLineChar(customer.getCustomerName())));
			return  custName;
		}
		custName=getCustomerLabel()+": No "+getCustomerLabel()+" associated with this "+getJobLabel();
		return  custName;
	}
	
	
	public String getEmpName(){
		String empName = "";
		
		if(!Api.isEmptyString(getEmpFirstName())){
			empName += getEmpFirstName();
		}
		
		if(!Api.isEmptyString(getEmpLastName())){
			if(!Api.isEmptyString(empName)){
				empName += (" " + getEmpLastName());
			} else {
				empName += getEmpLastName();
			}
		}
		
		return empName;
	}
	
	
	
	public FormAndField getFormAndField() {
		return formAndField;
	}
	public void setFormAndField(FormAndField formAndField) {
		this.formAndField = formAndField;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getHtml(){
		String assigntext="";
		if(getEmpId()!=0)
			assigntext="Reassign";
		else
			assigntext="assign";
		
		if(isOpenMap()){
			assigntext = "";
		}
		
		String contentString = "'<div id=\"content\">"+
				 "<div id=\"siteNotice\">"+
		         "</div>"+
		         "<div id=\"bodyContent\">"+
		         "<table border=\"0\">"+
		         "<tr>"+
		         "<td><table width=\"100%\" height=\"100%\" border=\"0\" >"+
		         "<tr>"+
		         "<td><a href=\"/"+Constants.getProject()+"/web/job/view/details/"+getVisitId()+"/:timeZoneOffsetValue\""+"   target=\"_blank\">"+Api.escapChar(Api.makeEmptyIfNull(Api.replaceAllNewLineChar(getEmpVisitTitle())),'\'', '\n', '\r')+"</a></td>"+
		         "</tr>"+
		         "<tr>"+
		         "<tr>"+
		         "<td>"+Api.escapChar(Api.makeEmptyIfNull(Api.replaceAllNewLineChar(getCustName())),'\'', '\n', '\r')+"</td>"+
		         "</tr>"+
		         "</tr>"+
		         "<tr>"+
		         "<td>"+Api.escapChar(Api.makeEmptyIfNull(Api.replaceAllNewLineChar(getAddress())),'\'', '\n', '\r')+"</td>"+
		         "</tr>"+
		         "<tr><td>Time: "+getTime()+"</td></tr>"+
		         "<tr><td><a class=\"popover-div\" style=\"cursor: pointer;\" data-container=\"body\" data-toggle=\"popover\" data-placement=\"bottom\" data-content=\"<div>Loading...</div>\" >"+
		         assigntext+"</a></td></tr>"+
		         "</table></td>"+
		         "</tr>"+
		         "</table>"+
		         "</div>"+
		         "</div>'";
		
		//return getCustomer() != null ? getCustomer().getHtmlTitle(getTime()) : contentString;
	      
//		  if(Api.isEmptyString(addressLat)&&Api.isEmptyString(addressLng)){
//			  
//			 return getCustomer() != null ? getCustomer().getHtmlTitle(getTime()) : contentString;
//		  }
		
		  return contentString;
		  
	      
	} 
	
	@Override
	public String toString() {
		if(getAddressLat()  != null&&getAddressLng()!=null){
			String resp = "[";
			resp += getHtml();
			if(isOpenMap()){
				resp += ", ol.proj.transform(["+ getAddressLng() + ", " + getAddressLat() +"], 'EPSG:4326', 'EPSG:3857') " ;//", new OpenLayers.LonLat(" + getAddressLng() + ", " + getAddressLat() + ")";
			}else{
			resp += ", new google.maps.LatLng(" + getAddressLat() + ", " + getAddressLng() + ")";
			}
			resp += ", " + maptype;
		//	resp += ", " + isCompleted();
			resp += ", " + getCompletedStatus();
			resp += ", " + getVisitId();
			resp += "]";
			return resp;
		} else if (getCustomer() != null&& getCustomer().getCustomerLat() != null&& getCustomer().getCustomerLong() != null) {
			if ((!getCustomer().getCustomerLat().equals("0") && !getCustomer().getCustomerLong().equals("0"))) {
				String resp = "[";
				resp += getHtml();
				if(isOpenMap()){
					resp += ", new OpenLayers.LonLat(" + getCustomer().getCustomerLong() + ", " + getCustomer().getCustomerLat() + ")";
				}else{
				resp += ", new google.maps.LatLng(" + getCustomer().getCustomerLat() + ", " + getCustomer().getCustomerLong() + ")";
				}
				resp += ", " + maptype;
			//	resp += ", " + isCompleted();
				resp += ", " + getCompletedStatus();
				resp += ", " + getVisitId();
				resp += "]";
				return resp;
			}

		
		}
		
		
		//else {
			return super.toString();
		//}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Visit){
			Visit visit = (Visit) obj;
			return visit.getVisitId() == this.visitId;
		} else if(obj instanceof Employee){
			Employee employee = (Employee) obj;
//			((employee.getEmpId() == this.empId) || (visit.getCreatedBy() == this.empId))
//			return employee.getEmpId() == this.empId;
			return ((this.empId == employee.getEmpId()) || (this.createdBy == employee.getEmpId()));
		} else {
			return super.equals(obj);
		}
	}
	
	public String toCSV() {
		return "[visitId=" + visitId + ", clientVisitId=" + clientVisitId
				+ ", empId=" + empId + ", empVisitTitle=" + empVisitTitle
				+ ", empVisitDesc=" + empVisitDesc + ", customerId="
				+ customerId + ", clientCustomerId=" + clientCustomerId
				+ ", customer=" + customer + ", visitStartTime="
				+ visitStartTime + ", visitEndTime=" + visitEndTime
				+ ", completed=" + completed + ", completeTime=" + completeTime
				+ ", approved=" + approved + ", createdBy=" + createdBy
				+ ", type=" + type + ", modifiedTime=" + modifiedTime
				+ ", createdByName=" + createdByName + ", empFirstName="
				+ empFirstName + ", empLastName=" + empLastName
				+ ", overdueTimeStr=" + overdueTimeStr + "]";
	}
	
	
	public boolean isEmptyRow(){
		if(!Api.isEmptyString(getEmpVisitTitle())){
			return false;
		}
		if(!Api.isEmptyString(getEmpVisitDesc())){
			return false;
		}
		if(!Api.isEmptyString(getVisitStartTime())){
			return false;
		}
		if(!Api.isEmptyString(getVisitEndTime())){
			return false;
		}
		if(getCustomerId()!=0){
			return false;
		}
		if(getEmpId()!=0){
			return false;
		}
//		if(!Api.isEmptyString(""+getCustomerId())){
//			return false;
//		}
		if(!Api.isEmptyString(getAddressStreet())){
			return false;
		}
		if(!Api.isEmptyString(getAddressArea())){
			return false;
		}
		if(!Api.isEmptyString(getAddressLandMark())){
			return false;
		}
		if(!Api.isEmptyString(getAddressState())){
			return false;
		}
		if(!Api.isEmptyString(getAddressCountry())){
			return false;
		}
		return true;
	}
	public boolean isSame(Visit visit, String tzo) throws ParseException {
		if(visit.getVisitId() != getVisitId()){
			return false;
		}
		if(visit.getEmpId() != getEmpId()){
			return false;
		}
		
		Long customerId = visit.getCustomerId();
		if(visit.getCustomerId() == -1){
			customerId = 0l;
		}
		if(!Api.isEqualLong(customerId, getCustomerId())){
			return false;
		}
		
		if(!Api.isEqualString(visit.getEmpVisitTitle(), getEmpVisitTitle(), true)){
			return false;
		}
		if(!Api.isEqualString(visit.getEmpVisitDesc(), getEmpVisitDesc(), true)){
			return false;
		}
		
		String startTime = visit.getVisitStartTime();
		startTime = Api.makeNullIfEmpty(startTime);
		if(!Api.isEmptyString(startTime)){
			startTime = Api.toUtcXsd(Api.getDateTimeInTzToUtc(DatatypeConverter.parseDateTime(startTime), tzo));
		}
		if(!Api.isEqualString(startTime, getVisitStartTime(), true)){
			return false;
		}
		
		String endTime = visit.getVisitEndTime();
		endTime = Api.makeNullIfEmpty(endTime);
		if(!Api.isEmptyString(endTime)){
			endTime = Api.toUtcXsd(Api.getDateTimeInTzToUtc(DatatypeConverter.parseDateTime(endTime), tzo));
		}
		if(!Api.isEqualString(endTime, getVisitEndTime(), true)){
			return false;
		}
		
		if(visit.isCompleted() != isCompleted()){
			return false;
		}
		
		String completeTime = visit.getCompleteTime();
		completeTime = Api.makeNullIfEmpty(completeTime);
		if(!Api.isEmptyString(completeTime)){
			completeTime = Api.toUtcXsd(Api.getDateTimeInTzToUtc(DatatypeConverter.parseDateTime(completeTime), tzo));
		}
		if(!Api.isEqualString(completeTime, getCompleteTime(), true)){
			return false;
		}
		
		if(visit.getVisitTypeId() != getVisitTypeId()){
			return false;
		}
		if(visit.getVisitStateId() != getVisitStateId()){
			return false;
		}
		if(visit.isAddressSameAsCustomer() != isAddressSameAsCustomer()){
			return false;
		}
		if(!Api.isEqualString(visit.getAddressStreet(), getAddressStreet(), true)){
			return false;
		}
		if(!Api.isEqualString(visit.getAddressArea(), getAddressArea(), true)){
			return false;
		}
		if(!Api.isEqualString(visit.getAddressCity(), getAddressCity(), true)){
			return false;
		}
		if(!Api.isEqualString(visit.getAddressPincode(), getAddressPincode(), true)){
			return false;
		}
		if(!Api.isEqualString(visit.getAddressLandMark(), getAddressLandMark(), true)){
			return false;
		}
		if(!Api.isEqualString(visit.getAddressState(), getAddressState(), true)){
			return false;
		}
		if(!Api.isEqualString(visit.getAddressCountry(), getAddressCountry(), true)){
			return false;
		}
		if(!Api.isEqualString(visit.getAddressLat(), getAddressLat(), true)){
			return false;
		}
		if(!Api.isEqualString(visit.getAddressLng(), getAddressLng(), true)){
			return false;
		}
		
		return true;
	}
	
	
	public Integer getCreatedByTzo() {
		return createdByTzo;
	}
	public void setCreatedByTzo(Integer createdByTzo) {
		this.createdByTzo = createdByTzo;
	}
	
	public Integer getTimezoneId() {
		return timezoneId;
	}
	public void setTimezoneId(Integer timezoneId) {
		this.timezoneId = timezoneId;
	}
	public Long getParentVisitId() {
		return parentVisitId;
	}
	public void setParentVisitId(Long parentVisitId) {
		this.parentVisitId = parentVisitId;
	}
	public String getClientParentVisitId() {
		return clientParentVisitId;
	}
	public void setClientParentVisitId(String clientParentVisitId) {
		this.clientParentVisitId = clientParentVisitId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		
		if(priority==PRIORITY_MEDIUM){
			this.priorityName= 	"Medium";
		}else if(priority==PRIORITY_LOW){
			this.priorityName= 	"Low";
		}else{
			this.priorityName= 	"High";
		}
		
		this.priority = priority;
	}
	/*public void setPriorityName(String priorityName) {
		if(priority==PRIORITY_MEDIUM){
			this.priorityName= 	"Medium";
		}else if(priority==PRIORITY_LOW){
			this.priorityName= 	"Low";
		}else{
			this.priorityName= 	"High";
		}

	}*/
	
	public String getPriorityName(){
		/*if(priority==PRIORITY_MEDIUM){
		return 	"Medium";
		}else if(priority==PRIORITY_LOW){
			return 	"Low";
		}else{
			return 	"High";
		}*/
		return priorityName;
	}
	public boolean isVisitTypeServiveCall() {
		return visitTypeServiveCall;
	}
	public void setVisitTypeServiveCall(boolean visitTypeServiveCall) {
		this.visitTypeServiveCall = visitTypeServiveCall;
	}
	public boolean isCheckRecurring() {
		return checkRecurring;
	}
	public void setCheckRecurring(boolean checkRecurring) {
		this.checkRecurring = checkRecurring;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public Long getRecurringParentId() {
		return recurringParentId;
	}
	public void setRecurringParentId(Long recurringParentId) {
		this.recurringParentId = recurringParentId;
	}
	public boolean isTemplate() {
		return isTemplate;
	}
	public void setIsTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}
	public boolean isRejected() {
		return rejected;
	}
	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}
	public String getRejectedOn() {
		return rejectedOn;
	}
	public void setRejectedOn(String rejectedOn) {
		if(!Api.isEmptyString(rejectedOn) && !rejectedOn.endsWith("M")){
			try {
				rejectedOn = Api.toUtcXsd(rejectedOn);
			} catch (Exception e) {
				Log.ignore(this.getClass(), e);
			}
		}
		
		this.rejectedOn = rejectedOn;
	}
	
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getTrackSms() {
		return trackSms;
	}
	public void setTrackSms(String trackSms) {
		this.trackSms = trackSms;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public long getCompletedJobCount() {
		return completedJobCount;
	}
	public void setCompletedJobCount(long completedJobCount) {
		this.completedJobCount = completedJobCount;
	}
	public String getJobDetails() {
		return jobDetails;
	}
	public void setJobDetails(String jobDetails) {
		this.jobDetails = jobDetails;
	}
	public String getSkillEntityIds() {
		return skillEntityIds;
	}
	public void setSkillEntityIds(String skillEntityIds) {
		this.skillEntityIds = skillEntityIds;
	}
	
	public Long getDuration() {
		return duration;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Long getCompletedCheckInId() {
		return completedCheckInId;
	}
	public void setCompletedCheckInId(Long completedCheckInId) {
		this.completedCheckInId = completedCheckInId;
	}
	
	public String getStartTimeToDisplay() {
		
		return startTimeToDisplay;
	}
	public void setStartTimeToDisplay(String startTimeToDisplay) {
		this.startTimeToDisplay = startTimeToDisplay;
	}
	public String getEndTimeToDisplay() {
		return endTimeToDisplay;
	}
	public void setEndTimeToDisplay(String endTimeToDisplay) {
		this.endTimeToDisplay = endTimeToDisplay;
	}
	public boolean isForcePerformJob() {
		return forcePerformJob;
	}
	public void setForcePerformJob(boolean forcePerformJob) {
		this.forcePerformJob = forcePerformJob;
	}
	public boolean isOpenMap() {
		return openMap;
	}
	public void setOpenMap(boolean openMap) {
		this.openMap = openMap;
	}
	public String getRecurrenceDays() {
		return recurrenceDays;
	}
	public void setRecurrenceDays(String recurrenceDays) {
		this.recurrenceDays = recurrenceDays;
	}
	public String getRecurringEndDate() {
		return recurringEndDate;
	}
	public void setRecurringEndDate(String recurringEndDate) {
		this.recurringEndDate = recurringEndDate;
	}
     
}
