package com.effort.entity;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.effort.beans.http.request.location.Location;
import com.effort.util.Api;
import com.effort.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Work implements Serializable,Cloneable {

	private static final long serialVersionUID = 1L;
	public static final int INVITATION = 1;
	public static final int UNPROCESS_SKELETON_FIELDS = 0;
	public static final int CONVERT_TO_WORK = 2;
	public static final int TRUE = 1;
	public static final int FALSE = 0;
	public static final int DELETED = 3;
	
	public static final int WEB = 1;
	public static final int ANDROID = 2;
	public static final int API = 3;
	public static final int BULKUPLOAD = 4;
	public static final int REQUISITION = 5;
	public static final int WEBLITE = 6;
	public static final int PUBLIC_FORM = 7;
	public static final Integer AUTO_CREATION = 8;

	// Audit Types
	public static final int TYPE_CREATE = 1;
	public static final int TYPE_MODIFY = 2;
	public static final int TYPE_WORK_INVITATION_CREATE = 3;
	public static final int TYPE_WORK_INVITATION_MODIFY = 4;
	public static final int TYPE_WORK_INVITATION_BROADCASTED = 5;
	public static final int TYPE_WORK_ASSIGNED = 6;
	public static final int TYPE_WORK_UNASSIGNED = 7;
	public static final int TYPE_WORK_INVITATION_ACCEPTED = 8;
	public static final int TYPE_WORK_INVITATION_REJECTED = 9;
	public static final int TYPE_WORK_ACTION_COMPLETION = 10;
	public static final int TYPE_WORK_COMPLETION = 11;
	public static final int TYPE_WORK_UNASSIGNMENT = 12;
	public static final int TYPE_WORK_INTEGRATION_LOGS = 13;
	public static final int TYPE_WORK_INVITATION_BROADCAST_INFO_LOGS = 14;
	
	
	private long workId;
	private long workSpecId;
	private long createdBy;
	private long modifiedBy;
	private Long formId;
	private int companyId;
	private String createdTime;
	private String modifiedTime;
	private String clientFormId;
	@JsonProperty("clientWorkId")
	private String clientSideId;
	private String identifier;
	
	private Long parentWorkId;
	private Long parentWorkActionSpecId;
	private String clientParentWorkId;
	private Long assignTo;
	private int type;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String workName;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String description;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long customerId;
	//@JsonProperty(access = Access.WRITE_ONLY)
	private String startTime;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String endTime;
	private String  priority;
	@JsonProperty(access = Access.WRITE_ONLY)
	private  boolean addressSameAsCustomer;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String phoneNumber;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String street;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String area;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String city;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String landMark;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String coutry;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String state;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String pincode;
	//@JsonProperty(access = Access.WRITE_ONLY)
	private String location;
	
	private int frequency;
	private boolean checkRecurring;
	private int eventCount;
	private String recurringFromDate;
	private Long recurringParentId;
	private boolean isTemplate=false;
	private boolean rejected=false;
	private String rejectedOn;
	
	private Boolean completed=false;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String workLabel="Work";
	@JsonProperty(access = Access.WRITE_ONLY)
	private String customerLabel="Customer";
	@JsonProperty(access = Access.WRITE_ONLY)
	private String workStatus;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Customer customer;
	
	//below 3 fields are used for hcl work creation through api service order duplication 
	private String externalId; // serviceOrderNumber
	private String apiUserId;
	private String archiveExternalId;
	//@JsonProperty(access = Access.WRITE_ONLY)
	private String addressLat;
	//@JsonProperty(access = Access.WRITE_ONLY)
	private String addressLng;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String maptype="11";
	private String completeTime;

//below empName used for customer history details
	
	private String empFirstName;
	private String empLastName;
	private String employeeName;
	
	private String customerName;
	
	private String title;
	
	private String workDetails;
	private long completedWorkCount;
	private boolean invitation;
	private int invitationState;
	private int invitationAccept;
	private String completedTime;
	private Long lastActionFormId;
	private int status;
	@JsonProperty("lastActionSpecId")
	private Long lastActionSpecId;
	@JsonProperty("nextActionSpecId")
	private String nextActionSpecId;
	private String invitationEventTime;
	
	private boolean deleted;
	
	private Long rejectedBy;
	private Long rejectionFormId;
	private boolean workSharing;
	private String workSharingEmpIds;
	private String timeOnly;
	private String workTemplateName;
	private String checkedInTime;
	private Long checkedInBy; 
	private boolean smartScheduling;
	private String lastActionCompletedTime;
	private String empNo;
	
	
	private boolean canRejectWorkInvitation = true;
	
	private boolean openMaps = false;
	
	private String track;
	private String trackSms;
	@JsonProperty(access = Access.WRITE_ONLY)
	private FormAndField formAndField;
	private String externalWorkSpecId;
	
	private String workCheckInLocationTime;
	private String workCheckOutLocationTime;
	private String identifiers;
	
	private boolean subTaskWork;
	private Long subTaskParentWorkId;
	private Long workProcessSubTaskSpecId;
	
	private String workFieldsUniqueKey;
	private String subTaskParentClientSideWorkId;
	
	private String workActionAssignee;
	
	//used for Siemens FS Report 
	private boolean onTime;
	
	//used for Siemens Sales Chart
	private String orderDetailId;
	
	private String clientCode;
	private boolean includeWorkInvitations;
	
	private String actionName;
	private Long notifiedWorkActionSpecId; 
	
	private Map<String,Object> titleFieldsMap;
	
	private String appVersion;
	private String workSpecTitle;
	private Long applicationNumber;
	private Integer currentRank;
	
	private Integer currentRankUpdateCount = 0;
	
	private Integer currentRole;//Not used will be removed
	
	private Long abmAuthority;
	private String authorityAcquiredTime;
	private Long currentOwner;
	private Location locationObj;
	private Long workCreatedLocationId;
	private Long workCompletedLocationId;
	private String workCreatedLocationAddrs;
	private String workCompletedLocationAddrs;
	private Long workModifiedLocationId;
	private String workModifiedLocationAddrs;
	
	private boolean enableOnlineWorkInvitation;
	private String workInvitationDisplayTime;
	
	private Long completedTat;
	private String month;
	
	private String branchCode;
	private List<Form> attachmentForms;
	
	private List<Form> responseDetails;
	
	private String workActionSpecId;
	private String elapsedTime;
	
	private boolean locationCaptured;

	private String groupByField;

	private Long workStateTypeEntityId;
	private boolean liveMonitor;
	private String existingWorkId;
	private boolean timeExceeds = false;
	
	private long stateTypeFieldValue;
	private long zoneFieldValue;
	private String StateTypeDisplayValue;
	private String ZoneDisplayValue;
	private String fieldValue;
	private Integer sourceOfAction;
	private Integer auditType;
	private String remarks;
	private boolean ignoreCaseStageUpdation;
	private boolean disableDataPost;
	private String restrictWorkDataPostConfigId;
 	private long InCompletedWorkCount;
 	private String incompletedFrom;
	private String assignedBy;
	private Integer sourceOfModification;

	private int invitationType=1;
	private Integer dataPostChangeType;
	private String pendingDays;
	private boolean fromMigrationFields;
	private Long migrationFilledBy;
	private Long migrationModifiedBy;
	private String migrationCreatedTime;
	private String migrationModifiedTime;
	private String completedByName;
	private Long workActionFormId;
	private String workTitleName;
	private boolean sharedWork;
	private String createdByEmpName;
	private String assignToName;
	private String empNameForNextAction;
	private String previousAction;
	private String oldWorkSharingEmpIds;
	private Long managerId;
	private boolean hasSmartWorkAllocationConfiguration;
	private String workTat;
	
	private Long customerCheckInId;
	private Long customEntityCheckInId;
	private String clientSideCustomerCheckInId;
	private String clientSideCustomEntityCheckInId; 
	private int processSkeleton = 1;
	private String nextAction;
	private String lastAction;
	private Long workDataPostTriggerEventId;
	private String workDataPostResponse;
	
	private boolean mapMyIndia = false;
	
	private boolean skipCaseStageUpdation;
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public Long getNotifiedWorkActionSpecId() {
		return notifiedWorkActionSpecId;
	}
	public void setNotifiedWorkActionSpecId(Long notifiedWorkActionSpecId) {
		this.notifiedWorkActionSpecId = notifiedWorkActionSpecId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	public long getWorkId() {
		return workId;
	}
	public void setWorkId(long workId) {
		this.workId = workId;
	}
	public long getWorkSpecId() {
		return workSpecId;
	}
	public void setWorkSpecId(long workSpecId) {
		this.workSpecId = workSpecId;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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
	public String getClientFormId() {
		return clientFormId;
	}
	public void setClientFormId(String clientFormId) {
		this.clientFormId = clientFormId;
	}
	@JsonIgnore
	public String getClientSideId() {
		return clientSideId;
	}
	@JsonIgnore
	public void setClientSideId(String clientSideId) {
		this.clientSideId = clientSideId;
	}
	public Long getParentWorkId() {
		return parentWorkId;
	}
	public void setParentWorkId(Long parentWorkId) {
		this.parentWorkId = parentWorkId;
	}
	
	public String getClientParentWorkId() {
		return clientParentWorkId;
	}
	public void setClientParentWorkId(String clientParentWorkId) {
		this.clientParentWorkId = clientParentWorkId;
	}
	
	public Long getParentWorkActionSpecId() {
		return parentWorkActionSpecId;
	}
	public void setParentWorkActionSpecId(Long parentWorkActionSpecId) {
		this.parentWorkActionSpecId = parentWorkActionSpecId;
	}
	public Long getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(Long assignTo) {
		this.assignTo = assignTo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public boolean isAddressSameAsCustomer() {
		return addressSameAsCustomer;
	}
	public void setAddressSameAsCustomer(boolean addressSameAsCustomer) {
		this.addressSameAsCustomer = addressSameAsCustomer;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getLandMark() {
		return landMark;
	}
	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}
	
	public String getCoutry() {
		return coutry;
	}
	public void setCoutry(String coutry) {
		this.coutry = coutry;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public boolean equals(Object obj) {
		Work work=(Work)obj;
	    if(work.getWorkId()==this.getWorkId()){
	    	return true;
	    }
		return false;
	}
	
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public boolean isCheckRecurring() {
		return checkRecurring;
	}
	public void setCheckRecurring(boolean checkRecurring) {
		this.checkRecurring = checkRecurring;
	}
	public int getEventCount() {
		return eventCount;
	}
	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}
	public String getRecurringFromDate() {
		return recurringFromDate;
	}
	public void setRecurringFromDate(String recurringFromDate) {
		this.recurringFromDate = recurringFromDate;
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
	public void setTemplate(boolean isTemplate) {
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
	
	
	public void setRejectedTimeToDisplayFormat(String tzo){
		if(rejectedOn != null && rejectedOn.length() > 0){
			rejectedOn = Api.getTimeZoneDates(rejectedOn, tzo,null);
		}
		
	}
	
	
	public String getWorkLabel() {
		return workLabel;
	}
	public void setWorkLabel(String workLabel) {
		this.workLabel = workLabel;
	}
	
	
	public String getCustomerLabel() {
		return customerLabel;
	}
	public void setCustomerLabel(String customerLabel) {
		this.customerLabel = customerLabel;
	}
	
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	
	
	@Override
	public String toString() {
		if(getAddressLat()  != null&&getAddressLng()!=null){
			String resp = "[";
			resp += getHtml();
			if(isOpenMaps()){
				resp += ", ol.proj.transform(["+ getAddressLng() + ", " + getAddressLat()  +"], 'EPSG:4326', 'EPSG:3857') " ;//", new OpenLayers.LonLat(" + getAddressLng() + ", " + getAddressLat() + ")";
			}else if(isMapMyIndia()){
				List<String> latLngArray = new ArrayList<String>();
				latLngArray.add(getAddressLat()+"");
				latLngArray.add(getAddressLng()+"");
				resp += ","+latLngArray.toString();
			}else{
			resp += ", new google.maps.LatLng(" + getAddressLat() + ", " + getAddressLng() + ")";
			}
			resp += ", " + maptype;
		//	resp += ", " + isCompleted();
			resp += ", " + getCompletedStatus();
			resp += ", " + getWorkId();
			resp += "]";
			return resp;
		} else if (getCustomer() != null&& getCustomer().getCustomerLat() != null&& getCustomer().getCustomerLong() != null) {
			if ((!getCustomer().getCustomerLat().equals("0") && !getCustomer().getCustomerLong().equals("0"))) {
				String resp = "[";
				resp += getHtml();
				if(isOpenMaps()){
					resp += ", new OpenLayers.LonLat(" + getCustomer().getCustomerLong() + ", " + getCustomer().getCustomerLat() + ")";
				}else if(isMapMyIndia()){
					List<String> latLngArray = new ArrayList<String>();
					latLngArray.add(getCustomer().getCustomerLat()+"");
					latLngArray.add(getCustomer().getCustomerLong()+"");
					resp += ","+latLngArray.toString();
				}else{
				resp += ", new google.maps.LatLng(" + getCustomer().getCustomerLat() + ", " + getCustomer().getCustomerLong() + ")";
				}
				resp += ", " + maptype;
			//	resp += ", " + isCompleted();
				resp += ", " + getCompletedStatus();
				resp += ", " + getWorkId();
				resp += "]";
				return resp;
			}

		
		}
		
		
		//else {
			return super.toString();
		//}
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getHtml(){
		String assigntext="";
		if(getAssignTo()!=null && getAssignTo()!=0)
			assigntext="Reassign";
		else
			assigntext="assign"; 
		
		if(isOpenMaps()){
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
		         "<td><a href=\"/"+Constants.getProject()+"/web/work/details/view/"+getWorkId()+"/\""+"   target=\"_blank\">"+Api.escapChar(Api.makeEmptyIfNull(Api.replaceAllNewLineChar(getWorkName())),'\'', '\n', '\r')+"</a></td>"+
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
		if(isLiveMonitor())
		{
			contentString = "'<div id=\"content\">"+
					 "<div id=\"siteNotice\">"+
			         "</div>"+
			         "<div id=\"bodyContent\">"+
			         "<table border=\"0\">"+
			         "<tr>"+
			         "<td><table width=\"100%\" height=\"100%\" border=\"0\" >"+
			         "<tr>"+
			         "<td><a href=\"/"+Constants.getProject()+"/web/work/details/view/"+getWorkId()+"/\""+"   target=\"_blank\">"+Api.escapChar(Api.makeEmptyIfNull(Api.replaceAllNewLineChar(getWorkName())),'\'', '\n', '\r')+"</a></td>"+
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
			         "</table></td>"+
			         "</tr>"+
			         "</table>"+
			         "</div>"+
			         "</div>'";
		}
		
		
		//return getCustomer() != null ? getCustomer().getHtmlTitle(getTime()) : contentString;
	      
//		  if(Api.isEmptyString(addressLat)&&Api.isEmptyString(addressLng)){
//			  
//			 return getCustomer() != null ? getCustomer().getHtmlTitle(getTime()) : contentString;
//		  }
		
		  return contentString;
		  
	      
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getCustName(){
		String custName= "";
		
		if(customer!=null){
			custName=getCustomerLabel()+":";
			custName+=Api.escapChar(Api.makeEmptyIfNull(Api.replaceAllNewLineChar(customer.getCustomerName())));
			return  custName;
		}
		custName=getCustomerLabel()+": No "+getCustomerLabel()+" associated with this "+getWorkLabel();
		return  custName;
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String getTime(){
		String time = "";
		String startTimeIn24hr = startTime;
		if(startTime != null && (startTime.indexOf("AM") > -1 || startTime.indexOf("PM") > -1)){
			 startTimeIn24hr = Api.getDateTimeIn24HrFormat(startTime); 
		}
		
		String endTimeIn24hr = endTime;
		if(endTime != null && (endTime.indexOf("AM") > -1 || endTime.indexOf("PM") > -1)){
			endTimeIn24hr = Api.getDateTimeIn24HrFormat(endTime); 
		}
		
		if(!Api.isEmptyString(startTimeIn24hr) && !Api.isEmptyString(Api.getTimeZoneDatesInDBFromDB1(startTimeIn24hr, null, null))){
			time += Api.getTimeZoneDatesInDBFromDB1(startTimeIn24hr, null , null);
			
			if(!Api.isEmptyString(endTimeIn24hr) && !Api.isEmptyString(Api.getTimeZoneDatesInDBFromDB1(endTimeIn24hr, null, null))){
				time += " to " + Api.getTimeZoneDatesInDBFromDB1(endTimeIn24hr, null , null);
			}
		}
		
		return time;
	}
	
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getAddress(){
		String address = "";
		
		if(!Api.isEmptyString(street)){
			address += street;
		}
		
		if(!Api.isEmptyString(area)){
			if(address.length() > 0){
				address += ", ";
			}
			address += area;
		}
		
		if(!Api.isEmptyString(landMark)){
			if(address.length() > 0){
				address += ", ";
			}
			address += landMark;
		}
		
		if(!Api.isEmptyString(city)){
			if(address.length() > 0){
				address += ", ";
			}
			address += city;
		}
		
		if(!Api.isEmptyString(state)){
			if(address.length() > 0){
				address += ", ";
			}
			address += state;
		}
		
		if(!Api.isEmptyString(coutry)){
			if(address.length() > 0){
				address += ", ";
			}
			address += coutry;
		}
		
		if(!Api.isEmptyString(pincode)){
			if(address.length() > 0){
				address += ", ";
			}
			address += pincode;
		}
		return address;
	}
	
	
	public String getMaptype() {
		return maptype;
	}
	public void setMaptype(String maptype) {
		this.maptype = maptype;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	
	public String getCompletedStatus() {
		if (completed) {
			return "1";
		} else {

			if (getWorkStatus() != null) {
				return getWorkStatus();
			} else {
				return "3";
			}

		}
	}
	
	
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getApiUserId() {
		return apiUserId;
	}
	public void setApiUserId(String apiUserId) {
		this.apiUserId = apiUserId;
	}
	public String getArchiveExternalId() {
		return archiveExternalId;
	}
	public void setArchiveExternalId(String archiveExternalId) {
		this.archiveExternalId = archiveExternalId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	
	@JsonProperty(access = Access.WRITE_ONLY)
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
	
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWorkDetails() {
		return workDetails;
	}
	public void setWorkDetails(String workDetails) {
		this.workDetails = workDetails;
	}
	public long getCompletedWorkCount() {
		return completedWorkCount;
	}
	public void setCompletedWorkCount(long completedWorkCount) {
		this.completedWorkCount = completedWorkCount;
	}
	public boolean isInvitation() {
		return invitation;
	}
	public void setInvitation(boolean invitation) {
		this.invitation = invitation;
	}
	public int getInvitationState() {
		return invitationState;
	}
	public void setInvitationState(int invitationState) {
		this.invitationState = invitationState;
	}
	public int getInvitationAccept() {
		return invitationAccept;
	}
	public void setInvitationAccept(int invitationAccept) {
		this.invitationAccept = invitationAccept;
	}
	@JsonIgnore
	public Long getLastActionSpecId() {
		return lastActionSpecId;
	}
	@JsonIgnore
	public void setLastActionSpecId(Long lastAction) {
		this.lastActionSpecId = lastAction;
	}
	
	public Long getLastActionFormId() {
		return lastActionFormId;
	}
	
	public void setLastActionFormId(Long lastActionFormId) {
		this.lastActionFormId = lastActionFormId;
	}
	@JsonIgnore
	public String getNextActionSpecId() {
		return nextActionSpecId;
	}
	@JsonIgnore
	public void setNextActionSpecId(String nextAction) {
		this.nextActionSpecId = nextAction;
	}
	public String getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getInvitationEventTime() {
		return invitationEventTime;
	}
	public void setInvitationEventTime(String invitationEventTime) {
		this.invitationEventTime = invitationEventTime;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Long getRejectedBy() {
		return rejectedBy;
	}
	public void setRejectedBy(Long rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	public Long getRejectionFormId() {
		return rejectionFormId;
	}
	public void setRejectionFormId(Long rejectionFormId) {
		this.rejectionFormId = rejectionFormId;
	}
	public boolean isWorkSharing() {
		return workSharing;
	}
	public void setWorkSharing(boolean workSharing) {
		this.workSharing = workSharing;
	}
	public String getWorkSharingEmpIds() {
		return workSharingEmpIds;
	}
	public void setWorkSharingEmpIds(String workSharingEmpIds) {
		this.workSharingEmpIds = workSharingEmpIds;
	}
	public String getTimeOnly() {
		return timeOnly;
	}
	public void setTimeOnly(String timeOnly) {
		this.timeOnly = timeOnly;
	}
	public String getWorkTemplateName() {
		return workTemplateName;
	}
	public void setWorkTemplateName(String workTemplateName) {
		this.workTemplateName = workTemplateName;
	}
	public String getCheckedInTime() {
		return checkedInTime;
	}
	public void setCheckedInTime(String checkedInTime) {
		this.checkedInTime = checkedInTime;
	}
	public Long getCheckedInBy() {
		return checkedInBy;
	}
	public void setCheckedInBy(Long checkedInBy) {
		this.checkedInBy = checkedInBy;
	}
	public boolean isSmartScheduling() {
		return smartScheduling;
	}
	public void setSmartScheduling(boolean smartScheduling) {
		this.smartScheduling = smartScheduling;
	}
	public String getLastActionCompletedTime() {
		return lastActionCompletedTime;
	}
	public void setLastActionCompletedTime(String lastActionCompletedTime) {
		this.lastActionCompletedTime = lastActionCompletedTime;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public boolean isCanRejectWorkInvitation() {
		return canRejectWorkInvitation;
	}
	public void setCanRejectWorkInvitation(boolean canRejectWorkInvitation) {
		this.canRejectWorkInvitation = canRejectWorkInvitation;
	}
	public boolean isOpenMaps() {
		return openMaps;
	}
	public void setOpenMaps(boolean openMaps) {
		this.openMaps = openMaps;
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
	
	
	
	
	public FormAndField getFormAndField() {
		return formAndField;
	}
	public void setFormAndField(FormAndField formAndField) {
		this.formAndField = formAndField;
	}
	public String getExternalWorkSpecId() {
		return externalWorkSpecId;
	}
	public void setExternalWorkSpecId(String externalWorkSpecId) {
		this.externalWorkSpecId = externalWorkSpecId;
	}
	public String getWorkCheckInLocationTime() {
		return workCheckInLocationTime;
	}
	public void setWorkCheckInLocationTime(String workCheckInLocationTime) {
		this.workCheckInLocationTime = workCheckInLocationTime;
	}
	public String getWorkCheckOutLocationTime() {
		return workCheckOutLocationTime;
	}
	public void setWorkCheckOutLocationTime(String workCheckOutLocationTime) {
		this.workCheckOutLocationTime = workCheckOutLocationTime;
	}
	public String getIdentifiers() {
		return identifiers;
	}
	public void setIdentifiers(String identifiers) {
		this.identifiers = identifiers;
	}
	public boolean isSubTaskWork() {
		return subTaskWork;
	}
	public void setSubTaskWork(boolean subTaskWork) {
		this.subTaskWork = subTaskWork;
	}
	public Long getSubTaskParentWorkId() {
		return subTaskParentWorkId;
	}
	public void setSubTaskParentWorkId(Long subTaskParentWorkId) {
		this.subTaskParentWorkId = subTaskParentWorkId;
	}
	public Long getWorkProcessSubTaskSpecId() {
		return workProcessSubTaskSpecId;
	}
	public void setWorkProcessSubTaskSpecId(Long workProcessSubTaskSpecId) {
		this.workProcessSubTaskSpecId = workProcessSubTaskSpecId;
	}
	public String getWorkFieldsUniqueKey() {
		return workFieldsUniqueKey;
	}
	public void setWorkFieldsUniqueKey(String workFieldsUniqueKey) {
		this.workFieldsUniqueKey = workFieldsUniqueKey;
	}
	
	public String getSubTaskParentClientSideWorkId() {
		return subTaskParentClientSideWorkId;
	}
	public void setSubTaskParentClientSideWorkId(
			String subTaskParentClientSideWorkId) {
		this.subTaskParentClientSideWorkId = subTaskParentClientSideWorkId;
	}
	public String getWorkActionAssignee() {
		return workActionAssignee;
	}
	public void setWorkActionAssignee(String workActionAssignee) {
		this.workActionAssignee = workActionAssignee;
	}
	public boolean isOnTime() {
		return onTime;
	}
	public void setOnTime(boolean onTime) {
		this.onTime = onTime;
	}
	public String getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@JsonIgnore
	public boolean isIncludeWorkInvitations() {
		return includeWorkInvitations;
	}
	@JsonIgnore
	public void setIncludeWorkInvitations(boolean includeWorkInvitations) {
		this.includeWorkInvitations = includeWorkInvitations;
	}
	public Map<String, Object> getTitleFieldsMap() {
		return titleFieldsMap;
	}
	public void setTitleFieldsMap(Map<String, Object> titleFieldsMap) {
		this.titleFieldsMap = titleFieldsMap;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	@JsonIgnore
	public String getWorkSpecTitle() {
		return workSpecTitle;
	}
	@JsonIgnore
	public void setWorkSpecTitle(String workSpecTitle) {
		this.workSpecTitle = workSpecTitle;
	}
	public Long getApplicationNumber() {
		return applicationNumber;
	}
	public void setApplicationNumber(Long applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	public Integer getCurrentRank() {
		return currentRank;
	}
	public void setCurrentRank(Integer currentRank) {
		this.currentRank = currentRank;
	}
	public Integer getCurrentRole() {
		return currentRole;
	}
	public void setCurrentRole(Integer currentRole) {
		this.currentRole = currentRole;
	}
	public Integer getCurrentRankUpdateCount() {
		return currentRankUpdateCount;
	}
	public void setCurrentRankUpdateCount(Integer currentRankUpdateCount) {
		this.currentRankUpdateCount = currentRankUpdateCount;
	}
	public Long getAbmAuthority() {
		return abmAuthority;
	}
	public void setAbmAuthority(Long abmAuthority) {
		this.abmAuthority = abmAuthority;
	}
	public String getAuthorityAcquiredTime() {
		return authorityAcquiredTime;
	}
	public void setAuthorityAcquiredTime(String authorityAcquiredTime) {
		this.authorityAcquiredTime = authorityAcquiredTime;
	}
	public Long getCurrentOwner() {
		return currentOwner;
	}
	public void setCurrentOwner(Long currentOwner) {
		this.currentOwner = currentOwner;
	}

	
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Location getLocationObj() {
		return locationObj;
	}
	public void setLocationObj(Location locationObj) {
		this.locationObj = locationObj;
	}
	public Long getWorkCreatedLocationId() {
		return workCreatedLocationId;
	}
	public void setWorkCreatedLocationId(Long workCreatedLocationId) {
		this.workCreatedLocationId = workCreatedLocationId;
	}
	public Long getWorkCompletedLocationId() {
		return workCompletedLocationId;
	}
	public void setWorkCompletedLocationId(Long workCompletedLocationId) {
		this.workCompletedLocationId = workCompletedLocationId;
	}
	public String getWorkCreatedLocationAddrs() {
		return workCreatedLocationAddrs;
	}
	public void setWorkCreatedLocationAddrs(String workCreatedLocationAddrs) {
		this.workCreatedLocationAddrs = workCreatedLocationAddrs;
	}
	public String getWorkCompletedLocationAddrs() {
		return workCompletedLocationAddrs;
	}
	public void setWorkCompletedLocationAddrs(String workCompletedLocationAddrs) {
		this.workCompletedLocationAddrs = workCompletedLocationAddrs;
	}
	public Long getWorkModifiedLocationId() {
		return workModifiedLocationId;
	}
	public void setWorkModifiedLocationId(Long workModifiedLocationId) {
		this.workModifiedLocationId = workModifiedLocationId;
	}
	public String getWorkModifiedLocationAddrs() {
		return workModifiedLocationAddrs;
	}
	public void setWorkModifiedLocationAddrs(String workModifiedLocationAddrs) {
		this.workModifiedLocationAddrs = workModifiedLocationAddrs;
	}
	public Long getCompletedTat() {
		return completedTat;
	}
	public void setCompletedTat(Long completedTat) {
		this.completedTat = completedTat;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	@JsonIgnore
	public List<Form> getAttachmentForms() {
		return attachmentForms;
	}
	@JsonIgnore
	public void setAttachmentForms(List<Form> attachmentForms) {
		this.attachmentForms = attachmentForms;
	}
	@JsonIgnore
	public List<Form> getResponseDetails() {
		return responseDetails;
	}
	@JsonIgnore
	public void setResponseDetails(List<Form> responseDetails) {
		this.responseDetails = responseDetails;
	}
	@JsonIgnore
	public String getWorkActionSpecId() {
		return workActionSpecId;
	}
	@JsonIgnore
	public void setWorkActionSpecId(String workActionSpecId) {
		this.workActionSpecId = workActionSpecId;
	}
	
	public boolean isEnableOnlineWorkInvitation() {
		return enableOnlineWorkInvitation;
	}
	public void setEnableOnlineWorkInvitation(boolean enableOnlineWorkInvitation) {
		this.enableOnlineWorkInvitation = enableOnlineWorkInvitation;
	}
	public String getWorkInvitationDisplayTime() {
		return workInvitationDisplayTime;
	}
	public void setWorkInvitationDisplayTime(String workInvitationDisplayTime) {
		this.workInvitationDisplayTime = workInvitationDisplayTime;
	}
	
	public String getElapsedTime() {
		return elapsedTime;
	}
	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public boolean isLocationCaptured() {
		return locationCaptured;
	}
	public void setLocationCaptured(boolean locationCaptured) {
		this.locationCaptured = locationCaptured;
	}

	public String getGroupByField() {
		return groupByField;
	}
	public void setGroupByField(String groupByField) {
		this.groupByField = groupByField;
	}

	public Long getWorkStateTypeEntityId() {
		return workStateTypeEntityId;
	}
	public void setWorkStateTypeEntityId(Long workStateTypeEntityId) {
		this.workStateTypeEntityId = workStateTypeEntityId;
	}
	public boolean isLiveMonitor() {
		return liveMonitor;
	}
	public void setLiveMonitor(boolean liveMonitor) {
		this.liveMonitor = liveMonitor;
	}
	
	public String getExistingWorkId() {
		return existingWorkId;
	}
	public void setExistingWorkId(String existingWorkId) {
		this.existingWorkId = existingWorkId;
	}

	public boolean isTimeExceeds() {
		return timeExceeds;
	}
	public void setTimeExceeds(boolean timeExceeds) {
		this.timeExceeds = timeExceeds;
	}
	public long getStateTypeFieldValue() {
		return stateTypeFieldValue;
	}

	public void setStateTypeFieldValue(long stateTypeFieldValue) {
		this.stateTypeFieldValue = stateTypeFieldValue;
	}

	public long getZoneFieldValue() {
		return zoneFieldValue;
	}

	public void setZoneFieldValue(long zoneFieldValue) {
		this.zoneFieldValue = zoneFieldValue;
	}
	public String getStateTypeDisplayValue() {
		return StateTypeDisplayValue;
	}
	public void setStateTypeDisplayValue(String stateTypeDisplayValue) {
		StateTypeDisplayValue = stateTypeDisplayValue;
	}
	public String getZoneDisplayValue() {
		return ZoneDisplayValue;
	}
	public void setZoneDisplayValue(String zoneDisplayValue) {
		ZoneDisplayValue = zoneDisplayValue;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public Integer getSourceOfAction() {
		return sourceOfAction;
	}
	public void setSourceOfAction(Integer sourceOfAction) {
		this.sourceOfAction = sourceOfAction;
	}
	public Integer getAuditType() {
		return auditType;
	}
	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public boolean isIgnoreCaseStageUpdation() {
		return ignoreCaseStageUpdation;
	}
	public void setIgnoreCaseStageUpdation(boolean ignoreCaseStageUpdation) {
		this.ignoreCaseStageUpdation = ignoreCaseStageUpdation;
	}
	public boolean isDisableDataPost() {
		return disableDataPost;
	}
	public void setDisableDataPost(boolean disableDataPost) {
		this.disableDataPost = disableDataPost;
	}
	public String getRestrictWorkDataPostConfigId() {
		return restrictWorkDataPostConfigId;
	}
	public void setRestrictWorkDataPostConfigId(String restrictWorkDataPostConfigId) {
		this.restrictWorkDataPostConfigId = restrictWorkDataPostConfigId;
	}

	public Integer getSourceOfModification() {
		return sourceOfModification;
	}
	public void setSourceOfModification(Integer sourceOfModification) {
		this.sourceOfModification = sourceOfModification;
	}
	public int getInvitationType() {
		return invitationType;
	}
	public void setInvitationType(int invitationType) {
		this.invitationType = invitationType;

	}
	public Integer getDataPostChangeType() {
		return dataPostChangeType;
	}
	public long getInCompletedWorkCount() {
		return InCompletedWorkCount;
	}
	public void setInCompletedWorkCount(long inCompletedWorkCount) {
		InCompletedWorkCount = inCompletedWorkCount;
	}
	public String getIncompletedFrom() {
		return incompletedFrom;
	}
	public void setIncompletedFrom(String incompletedFrom) {
		this.incompletedFrom = incompletedFrom;
	}
	public String getAssignedBy() {
		return assignedBy;
	}
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}
	public void setDataPostChangeType(Integer dataPostChangeType) {
		this.dataPostChangeType = dataPostChangeType;
	}
	public String getPendingDays() {
		return pendingDays;
	}
	public void setPendingDays(String pendingDays) {
		this.pendingDays = pendingDays;
	}
	public boolean isFromMigrationFields() {
		return fromMigrationFields;
	}
	public void setFromMigrationFields(boolean fromMigrationFields) {
		this.fromMigrationFields = fromMigrationFields;
	}
	public Long getMigrationFilledBy() {
		return migrationFilledBy;
	}

	public void setMigrationFilledBy(Long migrationFilledBy) {
		this.migrationFilledBy = migrationFilledBy;
	}

	public Long getMigrationModifiedBy() {
		return migrationModifiedBy;
	}

	public void setMigrationModifiedBy(Long migrationModifiedBy) {
		this.migrationModifiedBy = migrationModifiedBy;
	}

	public String getMigrationCreatedTime() {
		return migrationCreatedTime;
	}

	public void setMigrationCreatedTime(String migrationCreatedTime) {
		this.migrationCreatedTime = migrationCreatedTime;
	}

	public String getMigrationModifiedTime() {
		return migrationModifiedTime;
	}

	public void setMigrationModifiedTime(String migrationModifiedTime) {
		this.migrationModifiedTime = migrationModifiedTime;
	}

	public String getCompletedByName() {
		return completedByName;
	}
	public void setCompletedByName(String completedByName) {
		this.completedByName = completedByName;
	}
	public Long getWorkActionFormId() {
		return workActionFormId;
	}
	public void setWorkActionFormId(Long workActionFormId) {
		this.workActionFormId = workActionFormId;
	}
	
	public String getWorkTitleName() {
		return workTitleName;
	}
	public void setWorkTitleName(String workTitleName) {
		this.workTitleName = workTitleName;
	}
	public boolean isSharedWork() {
		return sharedWork;
	}
	public void setSharedWork(boolean sharedWork) {
		this.sharedWork = sharedWork;
	}
	public String getCreatedByEmpName() {
		return createdByEmpName;
	}
	public void setCreatedByEmpName(String createdByEmpName) {
		this.createdByEmpName = createdByEmpName;
	}
	public String getAssignToName() {
		return assignToName;
	}
	public void setAssignToName(String assignToName) {
		this.assignToName = assignToName;
	}
	public String getEmpNameForNextAction() {
		return empNameForNextAction;
	}
	public void setEmpNameForNextAction(String empNameForNextAction) {
		this.empNameForNextAction = empNameForNextAction;
	}
	public String getPreviousAction() {
		return previousAction;
	}
	public void setPreviousAction(String previousAction) {
		this.previousAction = previousAction;
	}
	@JsonIgnore
	public String getOldWorkSharingEmpIds() {
		return oldWorkSharingEmpIds;
	}
	@JsonIgnore
	public void setOldWorkSharingEmpIds(String oldWorkSharingEmpIds) {
		this.oldWorkSharingEmpIds = oldWorkSharingEmpIds;
	}
	public boolean isMapMyIndia() {
		return mapMyIndia;
	}
	public void setMapMyIndia(boolean mapMyIndia) {
		this.mapMyIndia = mapMyIndia;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public boolean isHasSmartWorkAllocationConfiguration() {
		return hasSmartWorkAllocationConfiguration;
	}
	public void setHasSmartWorkAllocationConfiguration(boolean hasSmartWorkAllocationConfiguration) {
		this.hasSmartWorkAllocationConfiguration = hasSmartWorkAllocationConfiguration;
	}
	public String getWorkTat() {
		return workTat;
	}
	public void setWorkTat(String workTat) {
		this.workTat = workTat;
	}
	public Long getCustomerCheckInId() {
		return customerCheckInId;
	}
	public void setCustomerCheckInId(Long customerCheckInId) {
		this.customerCheckInId = customerCheckInId;
	}
	public Long getCustomEntityCheckInId() {
		return customEntityCheckInId;
	}
	public void setCustomEntityCheckInId(Long customEntityCheckInId) {
		this.customEntityCheckInId = customEntityCheckInId;
	}
	public String getClientSideCustomerCheckInId() {
		return clientSideCustomerCheckInId;
	}
	public void setClientSideCustomerCheckInId(String clientSideCustomerCheckInId) {
		this.clientSideCustomerCheckInId = clientSideCustomerCheckInId;
	}
	public String getClientSideCustomEntityCheckInId() {
		return clientSideCustomEntityCheckInId;
	}
	public void setClientSideCustomEntityCheckInId(String clientSideCustomEntityCheckInId) {
		this.clientSideCustomEntityCheckInId = clientSideCustomEntityCheckInId;
	}
	public int getProcessSkeleton() {
		return processSkeleton;
	}
	public void setProcessSkeleton(int processSkeleton) {
		this.processSkeleton = processSkeleton;
	}
	public String getNextAction() {
		return nextAction;
	}
	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}
	public String getLastAction() {
		return lastAction;
	}
	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}
	public Long getWorkDataPostTriggerEventId() {
		return workDataPostTriggerEventId;
	}
	public void setWorkDataPostTriggerEventId(Long workDataPostTriggerEventId) {
		this.workDataPostTriggerEventId = workDataPostTriggerEventId;
	}
	public String getWorkDataPostResponse() {
		return workDataPostResponse;
	}
	public void setWorkDataPostResponse(String workDataPostResponse) {
		this.workDataPostResponse = workDataPostResponse;
	}
	public boolean isSkipCaseStageUpdation() {
		return skipCaseStageUpdation;
	}
	public void setSkipCaseStageUpdation(boolean skipCaseStageUpdation) {
		this.skipCaseStageUpdation = skipCaseStageUpdation;
	}
	
	
	
}
