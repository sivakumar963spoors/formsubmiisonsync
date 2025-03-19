package com.effort.entity;


import com.effort.beans.http.request.location.Location;
import com.effort.util.Api;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class StartWorkStopWorkLocations {
	
	public static final int NOT_VERFIED = 0;
	public static final int RETRY = -2;
	public static final String START_WORK_LOCATION_TIME = "startWorkLocationTime";
	public static final String STOP_WORK_LOCATION_TIME = "stopWorkLocationTime";
	public static final int UPDATED_BY_SYSTEM = 1;
	public static final int SIGNED_IN_MOBILE = 0;
	public static final int SIGNED_IN_WEBLITE = 1;
	public static final int SIGNOUT_IN_MOBILE = 0;
	public static final int SIGNOUT_IN_WEBLITE = 1;

	public static final int SIGN_IN_DATA_PUSH = 1;
	public static final int SIGN_OUT_DATA_PUSH = 1;
	
	private Long id;
	private String startWorkLocationId;
	private String startWorkLocationTime;
	private String startWorkReasonId;
	private String startWorkReason;
	private String stopWorkLocationId;
	private String stopWorkLocationTime;
	private String clientCreatedTime;
	private String clientModifiedTime;
	private String createdTime;
	private String modifiedTime;
	private String clientSideId;
	private String customerId;
	private Location startWorkLocation;
	private Location stopWorkLocaton;
	private String companyId;
	private String empId;
	private Integer autoStartWork;
	private Integer autoStopWork;
	
	private String empNo;
	private String empName;
	private String empPhone;
	private String empEmail;
	private String signInLocation;
	private String signOutLocation;
	private boolean currentlySignedIn;
	
	private boolean previousSignIn;
	private boolean previousSignOut;
	
	private Long startWorkMediaId;
	private Integer signOutConditionSetting;
	
	private String lastSyncTime;
	
	private Long signInFormId;
	private Long signOutFormId;
	
	private String signInTime;
	private String signOutTime;
	
	private String signInDate;
	private String signOutDate;
	
	private Integer startWorkVerificationType;
	private Integer startWorkVerificationStatus = 0;
	private String latLng;
	private String lastKnownLocation;
	private String clientCode;
	private Long updatedBySystem;
	
	//Added for Flatten Tables
	private String startWorkLatitude;
	private String startWorkLongitude;
	private String stopWorkLatitude;
	private String startWorkLocations;
	private String stopWorkLocatons;
	private String stopWorkLongitude;
	private Integer signInSource; 
	private Integer signOutSource; 
	private String signInClientSideFormId;
	private String signOutClientSideFormId;
	private String startWorkMediaBase64;
	private String timeDiffMin;
	private int signInDataPush;
	private int signOutDataPush;
	private Long checkInId;
	private Long checkOutId;
	private String previousSignIn1;
	private String previousSignOut1;
	

	public String getLastKnownLocation() {
		return lastKnownLocation;
	}
	public void setLastKnownLocation(String lastKnownLocation) {
		this.lastKnownLocation = lastKnownLocation;
	}
	public String getLatLng() {
		return latLng;
	}
	public void setLatLng(String latLng) {
		this.latLng = latLng;
	}
	public StartWorkStopWorkLocations() {
	}
	public StartWorkStopWorkLocations(String empId) {
	 this.empId=empId;
	}
	
	@JsonIgnore
	public Integer getStartWorkVerificationType() {
		return startWorkVerificationType;
	}

	@JsonIgnore
	public void setStartWorkVerificationType(Integer startWorkVerificationType) {
		this.startWorkVerificationType = startWorkVerificationType;
	}
	@JsonIgnore
	public Integer getStartWorkVerificationStatus() {
		return startWorkVerificationStatus;
	}
	
	@JsonIgnore
	public void setStartWorkVerificationStatus(Integer startWorkVerificationStatus) {
		this.startWorkVerificationStatus = startWorkVerificationStatus;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}
	
	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getStartWorkLocationId() {
		return startWorkLocationId;
	}
	
	public String getStartWorkReasonId() {
		if(!"null".equals(startWorkReasonId))
		{
			return startWorkReasonId;
		}else{
			return null;
		}
		
	}
	
	public String getStartWorkReason() {
		return startWorkReason;
	}
	public String getStopWorkLocationId() {
		return stopWorkLocationId;
	}
	public String getStopWorkLocationTime() {
		stopWorkLocationTime = Api.removeTrailingZeroFromDateTime(stopWorkLocationTime);
		return stopWorkLocationTime;
	}
	public String getClientCreatedTime() {
		return clientCreatedTime;
	}
	public String getClientModifiedTime() {
		return clientModifiedTime;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public String getClientSideId() {
		return clientSideId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public Location getStartWorkLocation() {
		return startWorkLocation;
	}
	public Location getStopWorkLocaton() {
		return stopWorkLocaton;
	}
	public void setStartWorkLocationId(String startWorkLocationId) {
		this.startWorkLocationId = startWorkLocationId;
	}
	
	public void setStartWorkReasonId(String startWorkReasonId) {
		this.startWorkReasonId = startWorkReasonId;
	}
	public void setStartWorkReason(String startWorkReason) {
		this.startWorkReason = startWorkReason;
	}
	public void setStopWorkLocationId(String stopWorkLocationId) {
		this.stopWorkLocationId = stopWorkLocationId;
	}
	public void setStopWorkLocationTime(String stopWorkLocationTime) {
		stopWorkLocationTime = Api.removeTrailingZeroFromDateTime(stopWorkLocationTime);
		this.stopWorkLocationTime = stopWorkLocationTime;
	}
	public void setClientCreatedTime(String clientCreatedTime) {
		this.clientCreatedTime = clientCreatedTime;
	}
	public void setClientModifiedTime(String clientModifiedTime) {
		this.clientModifiedTime = clientModifiedTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public void setClientSideId(String clientSideId) {
		this.clientSideId = clientSideId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setStartWorkLocation(Location startWorkLocation) {
		this.startWorkLocation = startWorkLocation;
	}
	public void setStopWorkLocaton(Location stopWorkLocaton) {
		this.stopWorkLocaton = stopWorkLocaton;
	}
	public Long getId() {
		return id;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getEmpId() {
		
		return empId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getStartWorkLocationTime() {
		
		startWorkLocationTime = Api.removeTrailingZeroFromDateTime(startWorkLocationTime);
		return startWorkLocationTime;
	}

	public void setStartWorkLocationTime(String startWorkLocationTime) {
		startWorkLocationTime = Api.removeTrailingZeroFromDateTime(startWorkLocationTime);
		this.startWorkLocationTime = startWorkLocationTime;
	}

	public Integer getAutoStartWork() {
		return autoStartWork;
	}

	public void setAutoStartWork(Integer autoStartWork) {
		this.autoStartWork = autoStartWork;
	}

	public Integer getAutoStopWork() {
		return autoStopWork;
	}

	public void setAutoStopWork(Integer autoStopWork) {
		this.autoStopWork = autoStopWork;
	}

	public String getEmpNo() {
		if(empNo == null || empNo.equalsIgnoreCase("null")){
			empNo = "";
		}
		return empNo;
	}

	public void setEmpNo(String empNo) {
		if(empNo == null || empNo.equalsIgnoreCase("null")){
			empNo = "";
		}
		this.empNo = empNo;
	}

	public String getEmpName() {
		if(empName == null || empName.equalsIgnoreCase("null")){
			empName = "";
		}
		return empName;
	}

	public void setEmpName(String empName) {
		if(empName == null || empName.equalsIgnoreCase("null")){
			empName = "";
		}
		this.empName = empName;
	}

	public String getSignInLocation() {
		if(signInLocation == null)
			signInLocation = "";
		return signInLocation;
	}

	public void setSignInLocation(String signInLocation) {
		if(signInLocation == null)
			signInLocation = "";
		this.signInLocation = signInLocation;
	}

	public boolean isCurrentlySignedIn() {
		
		if(startWorkLocationTime != null && stopWorkLocationTime == null){
			currentlySignedIn = true;
		}else{
			currentlySignedIn = false;
		}
		
		return currentlySignedIn;
	}

	public void setCurrentlySignedIn(boolean currentlySignedIn) {
		
		if(startWorkLocationTime != null && stopWorkLocationTime == null){
			currentlySignedIn = true;
		}else{
			currentlySignedIn = false;
		}

		
		this.currentlySignedIn = currentlySignedIn;
	}

	public String getSignOutLocation() {
		if(signOutLocation == null)
			signOutLocation = "";
		return signOutLocation;
	}

	public void setSignOutLocation(String signOutLocation) {
		if(signOutLocation == null)
			signOutLocation = "";
		this.signOutLocation = signOutLocation;
	}

	public boolean isPreviousSignIn() {
		return previousSignIn;
	}

	public void setPreviousSignIn(boolean previousSignIn) {
		this.previousSignIn = previousSignIn;
	}

	public Long getStartWorkMediaId() {
		return startWorkMediaId;
	}

	public void setStartWorkMediaId(Long startWorkMediaId) {
		this.startWorkMediaId = startWorkMediaId;
	}

	public Integer getSignOutConditionSetting() {
		return signOutConditionSetting;
	}

	public void setSignOutConditionSetting(Integer signOutConditionSetting) {
		this.signOutConditionSetting = signOutConditionSetting;
	}

	public String getLastSyncTime() 
	{
		lastSyncTime = Api.removeTrailingZeroFromDateTime(lastSyncTime);
		return lastSyncTime;
	}

	public void setLastSyncTime(String lastSyncTime) {
		lastSyncTime = Api.removeTrailingZeroFromDateTime(lastSyncTime);
		this.lastSyncTime = lastSyncTime;
	}

	public boolean isPreviousSignOut() {
		return previousSignOut;
	}

	public void setPreviousSignOut(boolean previousSignOut) {
		this.previousSignOut = previousSignOut;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Leave) {
			return Long.parseLong(getEmpId()) == ((Leave) obj).getEmpId();
		} else {
			return super.equals(obj);
		}
	}

	public Long getSignInFormId() {
		return signInFormId;
	}

	public void setSignInFormId(Long signInFormId) {
		this.signInFormId = signInFormId;
	}

	public Long getSignOutFormId() {
		return signOutFormId;
	}

	public void setSignOutFormId(Long signOutFormId) {
		this.signOutFormId = signOutFormId;
	}

	public String getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}

	public String getSignOutTime() {
		return signOutTime;
	}

	public void setSignOutTime(String signOutTime) {
		this.signOutTime = signOutTime;
	}

	public String getSignInDate() {
		return signInDate;
	}

	public void setSignInDate(String signInDate) {
		this.signInDate = signInDate;
	}

	public String getSignOutDate() {
		return signOutDate;
	}

	public void setSignOutDate(String signOutDate) {
		this.signOutDate = signOutDate;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public Long getUpdatedBySystem() {
		return updatedBySystem;
	}
	public void setUpdatedBySystem(Long updatedBySystem) {
		this.updatedBySystem = updatedBySystem;
	}
	
	public String getStartWorkLatitude() {
		return startWorkLatitude;
	}

	public String getStartWorkLongitude() {
		return startWorkLongitude;
	}

	public String getStopWorkLatitude() {
		return stopWorkLatitude;
	}

	public String getStopWorkLongitude() {
		return stopWorkLongitude;
	}

	public void setStartWorkLatitude(String startWorkLatitude) {
		this.startWorkLatitude = startWorkLatitude;
	}

	public void setStartWorkLongitude(String startWorkLongitude) {
		this.startWorkLongitude = startWorkLongitude;
	}

	public void setStopWorkLatitude(String stopWorkLatitude) {
		this.stopWorkLatitude = stopWorkLatitude;
	}

	public void setStopWorkLongitude(String stopWorkLongitude) {
		this.stopWorkLongitude = stopWorkLongitude;
	}

	public String getStartWorkLocations() {
		return startWorkLocations;
	}

	public String getStopWorkLocatons() {
		return stopWorkLocatons;
	}

	public void setStartWorkLocations(String startWorkLocations) {
		this.startWorkLocations = startWorkLocations;
	}

	public void setStopWorkLocatons(String stopWorkLocatons) {
		this.stopWorkLocatons = stopWorkLocatons;
	}
	
	
	public Integer getSignInSource() {
		return signInSource;
	}
	public void setSignInSource(Integer signInSource) {
		this.signInSource = signInSource;
	}
	public Integer getSignOutSource() {
		return signOutSource;
	}
	public void setSignOutSource(Integer signOutSource) {
		this.signOutSource = signOutSource;
	}
	@Override
	public String toString() {
		return "StartWorkStopWorkLocations [id=" + id + ", startWorkLocationId=" + startWorkLocationId
				+ ", startWorkLocationTime=" + startWorkLocationTime + ", startWorkReasonId=" + startWorkReasonId
				+ ", startWorkReason=" + startWorkReason + ", stopWorkLocationId=" + stopWorkLocationId
				+ ", stopWorkLocationTime=" + stopWorkLocationTime + ", clientCreatedTime=" + clientCreatedTime
				+ ", clientModifiedTime=" + clientModifiedTime + ", createdTime=" + createdTime + ", modifiedTime="
				+ modifiedTime + ", clientSideId=" + clientSideId + ", clientCode=" + clientCode + ", customerId="
				+ customerId + ", startWorkLocation=" + startWorkLocation + ", stopWorkLocaton=" + stopWorkLocaton
				+ ", companyId=" + companyId + ", empId=" + empId + ", autoStartWork=" + autoStartWork
				+ ", autoStopWork=" + autoStopWork + ", empNo=" + empNo + ", empName=" + empName + ", empPhone="
				+ empPhone + ", signInLocation=" + signInLocation + ", signOutLocation=" + signOutLocation
				+ ", currentlySignedIn=" + currentlySignedIn + ", previousSignIn=" + previousSignIn
				+ ", previousSignOut=" + previousSignOut + ", startWorkMediaId=" + startWorkMediaId
				+ ", signOutConditionSetting=" + signOutConditionSetting + ", lastSyncTime=" + lastSyncTime
				+ ", signInFormId=" + signInFormId + ", signOutFormId=" + signOutFormId + ", startWorkLatitude="
				+ startWorkLatitude + ", startWorkLongitude=" + startWorkLongitude + ", stopWorkLatitude="
				+ stopWorkLatitude + ", startWorkLocations=" + startWorkLocations + ", stopWorkLocatons="
				+ stopWorkLocatons + ", stopWorkLongitude=" + stopWorkLongitude + ", signInSource=" + signInSource +", signOutSource=" + signOutSource +"]";
	}
	public String getSignInClientSideFormId() {
		return signInClientSideFormId;
	}
	public void setSignInClientSideFormId(String signInClientSideFormId) {
		this.signInClientSideFormId = signInClientSideFormId;
	}
	public String getSignOutClientSideFormId() {
		return signOutClientSideFormId;
	}
	public void setSignOutClientSideFormId(String signOutClientSideFormId) {
		this.signOutClientSideFormId = signOutClientSideFormId;
	}
	public String getStartWorkMediaBase64() {
		return startWorkMediaBase64;
	}
	public void setStartWorkMediaBase64(String startWorkMediaBase64) {
		this.startWorkMediaBase64 = startWorkMediaBase64;
	}
	public String getTimeDiffMin() {
		return timeDiffMin;
	}
	public void setTimeDiffMin(String timeDiffMin) {
		this.timeDiffMin = timeDiffMin;
	}
	public int getSignInDataPush() {
		return signInDataPush;
	}
	public void setSignInDataPush(int signInDataPush) {
		this.signInDataPush = signInDataPush;
	}
	public int getSignOutDataPush() {
		return signOutDataPush;
	}
	public void setSignOutDataPush(int signOutDataPush) {
		this.signOutDataPush = signOutDataPush;
	}
	public Long getCheckInId() {
		return checkInId;
	}
	public void setCheckInId(Long checkInId) {
		this.checkInId = checkInId;
	}
	public Long getCheckOutId() {
		return checkOutId;
	}
	public void setCheckOutId(Long checkOutId) {
		this.checkOutId = checkOutId;
	}
	public String getPreviousSignIn1() {
		return previousSignIn1;
	}
	public void setPreviousSignIn1(String previousSignIn1) {
		this.previousSignIn1 = previousSignIn1;
	}
	public String getPreviousSignOut1() {
		return previousSignOut1;
	}
	public void setPreviousSignOut1(String previousSignOut1) {
		this.previousSignOut1 = previousSignOut1;
	}

	
}
