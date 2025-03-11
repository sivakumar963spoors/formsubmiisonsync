package com.effort.beans.http.request.location;


import java.util.ArrayList;
import java.util.List;

import com.effort.util.Api;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Location {
	public static final int FOR_TRACK = 1;
	public static final int FOR_COMMENT = 2;
	public static final int FOR_VISIT_HISTORY = 3;
	public static final int FOR_FORM = 4;
	public static final int FOR_START_WORK = 5;
	public static final int FOR_STOP_WORK = 6;
	public static final int FOR_ROUTE_ACTIVITY =7 ;
	public static final int FOR_MEDIA=8;
	public static final int FOR_WORK=9;
	public static final int FOR_CUSTOMER_CHECKIN=14;
	public static final int FOR_CUSTOMER_CHECKOUT=15;
	
	
	public static final int FOR_WORK_CHECKIN=18;
	public static final int FOR_WORK_CHECKOUT=19;

	
	public static final int STATUS_TYPE_INVALID_TRACK=-2;
	
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING=-99;
	
	public static final int FOR_CUSTOM_ENTITY_CHECKIN=20;
	public static final int FOR_CUSTOM_ENTITY_CHECKOUT=21;
	
	public static final int FOR_MARK_MY_LOCATION = 22;
	
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_COMMENT =-98;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_VISIT_HISTORY =-3;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_FORM =-4;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_START_WORK =-5;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_STOP_WORK =-6;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_ROUTE_ACTIVITY =-7 ;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_MEDIA=-8;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_WORK=-9;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_CUSTOMER_CHECKIN=-14;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_CUSTOMER_CHECKOUT=-15;
	
	
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_WORK_CHECKIN=-18;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_WORK_CHECKOUT=-19;
	
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_CUSTOM_ENTITY_CHECKIN=-20;
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_CUSTOM_ENTITY_CHECKOUT=-21;
	
	public static final int STATUS_TYPE_STOP_LOCATION_RESOLVING_FOR_MARK_MY_LOCATION =-22;
	
	public static final int STATUS_TYPE_ADDRESS_RESOLVED_FROM_MOBILE = 3;
	
	
	private Long locationId;
	private Long empId;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	private Double accuracy;
	private Integer medium;
	private boolean outlier;
	private String fixTime;
	private int resolvedBy;
	private Double carrierSignalStrength;
	private Double batteryLevel;
	private Integer purpose=1;
	private boolean freshFix;
	private Double gpsLat;
	private Double gpsLng;
	private Double gpsAlt;
	private Double gpsAccuracy;
	private Double speed;
	private Double bearingAngle;
	private String gpsFixTime;
	private Boolean gpsProvider;
	private boolean gpsOutlier;
	private Double cellLat;
	private Double cellLng;
	private Double cellAccuracy;
	private String cellId;
	private String lac;
	private String mcc;
	private String mnc;
	private String cellFixTime;
	private Boolean cellProvider;
	private boolean cellOutlier;
	private Double unknownLat;
	private Double unknownLng;
	private Double unknownAlt;
	private Double unknownAccuracy;
	private String unknownFixTime;
	private Boolean otherProvider;
	private boolean unknownOutlier;
	private String location;
	private String clientTime;
	private int connectivityStatus = -1;
	private int deliveryMedium = 1;
	
	private int reason;
	private int status;
	
	private String locality;
	private String sublocality;
	private boolean enhanced;
	private Long customerId;

	private String createdTime;
	
	private int index;
	private boolean modified;
	private boolean actualOutlier;
	private String clientId;
	private String clientCode;
	
	private Long startWorkReason;
	
	private Integer airplaneMode;
	private Integer empLocationProvider;
	private String workLocation;
	private Double workLat;
	private Double workLong;
	
	private boolean mockLocation;
	
	private String clientDate;
	
	private Integer locationSource;
	private Integer distanceResolveStatus;
	private int companyId;
	
	private boolean drafted;
	private String employeeSignInReason;
	
	private String postalCode;
	private String country;
	private String adminArea;
	private List<CellInfo> nearByCellInfo = new ArrayList<CellInfo>();
	
	
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getAltitude() {
		return altitude;
	}
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}
	public Double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}
	public Integer getMedium() {
		return medium;
	}
	public void setMedium(Integer medium) {
		this.medium = medium;
	}
	public boolean isOutlier() {
		return outlier;
	}
	public void setOutlier(boolean outlier) {
		this.outlier = outlier;
	}
	public String getFixTime() {
		return fixTime;
	}
	public void setFixTime(String fixTime) {
		this.fixTime = fixTime;
	}
	public int getResolvedBy() {
		return resolvedBy;
	}
	public void setResolvedBy(int resolvedBy) {
		this.resolvedBy = resolvedBy;
	}
	public Double getCarrierSignalStrength() {
		return carrierSignalStrength;
	}
	public void setCarrierSignalStrength(Double carrierSignalStrength) {
		this.carrierSignalStrength = carrierSignalStrength;
	}
	public Double getBatteryLevel() {
		return batteryLevel;
	}
	public void setBatteryLevel(Double batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
	public boolean isFreshFix() {
		return freshFix;
	}
	public void setFreshFix(boolean freshFix) {
		this.freshFix = freshFix;
	}
	public Integer getPurpose() {
		return purpose;
	}
	public void setPurpose(Integer purpose) {
		this.purpose = purpose;
	}
	public Double getGpsLat() {
		return gpsLat;
	}
	public void setGpsLat(Double gpsLat) {
		this.gpsLat = gpsLat;
	}
	public Double getGpsLng() {
		return gpsLng;
	}
	public void setGpsLng(Double gpsLng) {
		this.gpsLng = gpsLng;
	}
	public Double getGpsAlt() {
		return gpsAlt;
	}
	public void setGpsAlt(Double gpsAlt) {
		this.gpsAlt = gpsAlt;
	}
	public Double getGpsAccuracy() {
		return gpsAccuracy;
	}
	public void setGpsAccuracy(Double gpsAccuracy) {
		this.gpsAccuracy = gpsAccuracy;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Double getBearingAngle() {
		return bearingAngle;
	}
	public void setBearingAngle(Double bearingAngle) {
		this.bearingAngle = bearingAngle;
	}
	public String getGpsFixTime() {
		return gpsFixTime;
	}
	public void setGpsFixTime(String gpsFixTime) {
		this.gpsFixTime = gpsFixTime;
	}
	public Boolean isGpsProvider() {
		return gpsProvider == null ? false : gpsProvider;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public Boolean getGpsProvider1() {
		return gpsProvider == null ? false : gpsProvider;
	}
	public void setGpsProvider(Boolean gpsProvider) {
		this.gpsProvider = gpsProvider;
	}
	public boolean isGpsOutlier() {
		return gpsOutlier;
	}
	public void setGpsOutlier(boolean gpsOutlier) {
		this.gpsOutlier = gpsOutlier;
	}
	public Double getCellLat() {
		return cellLat;
	}
	public void setCellLat(Double cellLat) {
		this.cellLat = cellLat;
	}
	public Double getCellLng() {
		return cellLng;
	}
	public void setCellLng(Double cellLng) {
		this.cellLng = cellLng;
	}
	public Double getCellAccuracy() {
		return cellAccuracy;
	}
	public void setCellAccuracy(Double cellAccuracy) {
		this.cellAccuracy = cellAccuracy;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
	public String getLac() {
		return lac;
	}
	public void setLac(String lac) {
		this.lac = lac;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getMnc() {
		return mnc;
	}
	public void setMnc(String mnc) {
		this.mnc = mnc;
	}
	public String getCellFixTime() {
		return cellFixTime;
	}
	public void setCellFixTime(String cellFixTime) {
		this.cellFixTime = cellFixTime;
	}
	public Boolean isCellProvider() {
		return cellProvider == null ? false : cellProvider;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public Boolean getCellProvider1() {
		return cellProvider == null ? false : cellProvider;
	}
	public void setCellProvider(Boolean cellProvider) {
		this.cellProvider = cellProvider;
	}
	public boolean isCellOutlier() {
		return cellOutlier;
	}
	public void setCellOutlier(boolean cellOutlier) {
		this.cellOutlier = cellOutlier;
	}
	public Double getUnknownLat() {
		return unknownLat;
	}
	public void setUnknownLat(Double unknownLat) {
		this.unknownLat = unknownLat;
	}
	public Double getUnknownLng() {
		return unknownLng;
	}
	public void setUnknownLng(Double unknownLng) {
		this.unknownLng = unknownLng;
	}
	public Double getUnknownAlt() {
		return unknownAlt;
	}
	public void setUnknownAlt(Double unknownAlt) {
		this.unknownAlt = unknownAlt;
	}
	public Double getUnknownAccuracy() {
		return unknownAccuracy;
	}
	public void setUnknownAccuracy(Double unknownAccuracy) {
		this.unknownAccuracy = unknownAccuracy;
	}
	public String getUnknownFixTime() {
		return unknownFixTime;
	}
	public void setUnknownFixTime(String unknownFixTime) {
		this.unknownFixTime = unknownFixTime;
	}
	public boolean isOtherProvider() {
		return otherProvider == null ? false : otherProvider;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public boolean getOtherProvider1() {
		return otherProvider == null ? false : otherProvider;
	}
	
	public void setOtherProvider(Boolean otherProvider) {
		this.otherProvider = otherProvider;
	}
	public boolean isUnknownOutlier() {
		return unknownOutlier;
	}
	public void setUnknownOutlier(boolean unknownOutlier) {
		this.unknownOutlier = unknownOutlier;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getClientTime() {
		return clientTime;
	}
	public void setClientTime(String clientTime) {
		this.clientTime = clientTime;
	}
	public int getConnectivityStatus() {
		return connectivityStatus;
	}
	public void setConnectivityStatus(int connectivityStatus) {
		this.connectivityStatus = connectivityStatus;
	}
	public int getDeliveryMedium() {
		return deliveryMedium;
	}
	public void setDeliveryMedium(int deliveryMedium) {
		this.deliveryMedium = deliveryMedium;
	}
	public List<CellInfo> getNearByCellInfo() {
		return nearByCellInfo;
	}
	public void setNearByCellInfo(List<CellInfo> nearByCellInfo) {
		this.nearByCellInfo = nearByCellInfo;
	}
    public int getReason() {
      return reason;
    }
    public void setReason(int reason) {
      this.reason = reason;
    }
    
    public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLocationAddress() {
		if(Api.isEmptyString(location)){
			if(latitude!=null&&longitude!=null){
				String locationaddr=latitude+", "+longitude;
				return locationaddr;
			}
		}
		return location;
	}
	
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getSublocality() {
		return sublocality;
	}
	public void setSublocality(String sublocality) {
		this.sublocality = sublocality;
	}
	public String getlocalityAddr(){
     	String localityAddr = "";
 		
 		if(!Api.isEmptyString(getSublocality())){
 			localityAddr +=getSublocality();
 		}
 		
 		if(!Api.isEmptyString(getLocality())){
 			if(!Api.isEmptyString(localityAddr)){
 				localityAddr += (", " +  getLocality());
 			} else {
 				localityAddr += getLocality();
 			}
 		}
 		if(Api.isEmptyString(localityAddr)){
 			localityAddr =getLatLng();
 		}
 		return localityAddr;
     	
     }
	
	public String getLatLng(){
		if(latitude!=null&&longitude!=null){
			String locationaddr=latitude+", "+longitude;
			return locationaddr;
		}
		
		return "";
	}
	
	
	
	public boolean getEnhanced() {
		return enhanced;
	}
	public void setEnhanced(boolean enhanced) {
		this.enhanced = enhanced;
	}
	
	public String getConnectivityStatusInfo() {

			int status = connectivityStatus;
			switch (status) {
			case 0:
				return "not Connected";
			default:
				return "Connected";
			}

	
	}

	public String getConnectedVia() {
//		if (Api.isEmptyString(provisionigTime)) {
//			return " ";
//
//		}
		
			int status =connectivityStatus;
			switch (status) {
			case -1:
				return "unknown";
			case 1:
				return "GPRS";
			case 2:
				return "WIFI";

			}
	

		return "   ";
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public boolean isModified() {
		return modified;
	}
	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isActualOutlier() {
		return actualOutlier;
	}
	public void setActualOutlier(boolean actualOutlier) {
		this.actualOutlier = actualOutlier;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
	public Long getStartWorkReason() {
		return startWorkReason;
	}
	
	public void setStartWorkReason(Long startWorkReason) {
		this.startWorkReason = startWorkReason;
	}
	
	public Integer getAirplaneMode() {
		return airplaneMode;
	}
	public Integer getEmpLocationProvider() {
		return empLocationProvider;
	}
	public void setAirplaneMode(Integer airplaneMode) {
		this.airplaneMode = airplaneMode;
	}
	public void setEmpLocationProvider(Integer empLocationProvider) {
		this.empLocationProvider = empLocationProvider;
	}
	
	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", empId=" + empId + ", latitude=" + latitude + ", longitude="
				+ longitude + ", altitude=" + altitude + ", accuracy=" + accuracy + ", medium=" + medium + ", outlier="
				+ outlier + ", fixTime=" + fixTime + ", resolvedBy=" + resolvedBy + ", carrierSignalStrength="
				+ carrierSignalStrength + ", batteryLevel=" + batteryLevel + ", purpose=" + purpose + ", freshFix="
				+ freshFix + ", gpsLat=" + gpsLat + ", gpsLng=" + gpsLng + ", gpsAlt=" + gpsAlt + ", gpsAccuracy="
				+ gpsAccuracy + ", speed=" + speed + ", bearingAngle=" + bearingAngle + ", gpsFixTime=" + gpsFixTime
				+ ", gpsProvider=" + gpsProvider + ", gpsOutlier=" + gpsOutlier + ", cellLat=" + cellLat + ", cellLng="
				+ cellLng + ", cellAccuracy=" + cellAccuracy + ", cellId=" + cellId + ", lac=" + lac + ", mcc=" + mcc
				+ ", mnc=" + mnc + ", cellFixTime=" + cellFixTime + ", cellProvider=" + cellProvider + ", cellOutlier="
				+ cellOutlier + ", unknownLat=" + unknownLat + ", unknownLng=" + unknownLng + ", unknownAlt="
				+ unknownAlt + ", unknownAccuracy=" + unknownAccuracy + ", unknownFixTime=" + unknownFixTime
				+ ", otherProvider=" + otherProvider + ", unknownOutlier=" + unknownOutlier + ", location=" + location
				+ ", clientTime=" + clientTime + ", connectivityStatus=" + connectivityStatus + ", deliveryMedium="
				+ deliveryMedium + ", nearByCellInfo=" + nearByCellInfo + ", reason=" + reason + ", status=" + status
				+ ", locality=" + locality + ", sublocality=" + sublocality + ", enhanced=" + enhanced + ", customerId="
				+ customerId + ", createdTime=" + createdTime + ", index=" + index + ", modified=" + modified
				+ ", actualOutlier=" + actualOutlier + ", clientId=" + clientId + ", clientCode=" + clientCode
				+ ", startWorkReason=" + startWorkReason + ", airplaneMode=" + airplaneMode + ", empLocationProvider="
				+ empLocationProvider + ", workLocation=" + workLocation + ", workLat=" + workLat + ", workLong="
				+ workLong + ", mockLocation=" + mockLocation + ", clientDate=" + clientDate + ", locationSource="
				+ locationSource + ", distanceResolveStatus=" + distanceResolveStatus + ", companyId=" + companyId
				+ ", drafted=" + drafted + ", postalCode="+ postalCode +", country="+ country +", adminArea="+ adminArea +" ]";
	}
	

	public void copyLocationObject(Location loc){
		
		this.locationId=loc.locationId;
		this.empId=loc.empId;
		this.latitude=loc.latitude;
		this.longitude=loc.longitude;
		this.altitude=loc.altitude;
		this.accuracy=loc.accuracy;
		this.medium=loc.medium;
		this.outlier=loc.outlier;
		this.fixTime=loc.fixTime;
		this.resolvedBy=loc.resolvedBy;
		this.carrierSignalStrength=loc.carrierSignalStrength;
		this.batteryLevel=loc.batteryLevel;
		this.purpose=loc.purpose;
		this.freshFix=loc.freshFix;
		this.gpsLat=loc.gpsLat;
		this.gpsLng=loc.gpsLng;
		this.gpsAlt=loc.gpsAlt;
		this.gpsAccuracy=loc.gpsAccuracy;
		this.speed=loc.speed;
		this.bearingAngle=loc.bearingAngle;
		this.gpsFixTime=loc.gpsFixTime;
		this.gpsProvider=loc.gpsProvider;
		this.gpsOutlier=loc.gpsOutlier;
		this.cellLat=loc.cellLat;
		this.cellLng=loc.cellLng;
		this.cellAccuracy=loc.cellAccuracy;
		this.cellId=loc.cellId;
		this.lac=loc.lac;
		this.mcc=loc.mcc;
		this.mnc=loc.mnc;
		this.cellFixTime=loc.cellFixTime;
		this.cellProvider=loc.cellProvider;
		this.cellOutlier=loc.cellOutlier;
		this.unknownLat=loc.unknownLat;
		this.unknownLng=loc.unknownLng;
		this.unknownAlt=loc.unknownAlt;
		this.unknownAccuracy=loc.unknownAccuracy;
		this.unknownFixTime=loc.unknownFixTime;
		this.otherProvider=loc.otherProvider;
		this.unknownOutlier=loc.unknownOutlier;
		this.location=loc.location;
		this.clientTime=loc.clientTime;
		this.connectivityStatus =loc.connectivityStatus;
		this.deliveryMedium = loc.deliveryMedium;
		this.reason=loc.reason;
		this.status=loc.status;
		this.locality=loc.locality;
		this.sublocality=loc.sublocality;
		this.enhanced=loc.enhanced;
		this.customerId=loc.customerId;
		this.createdTime=loc.createdTime;
		this.index=loc.index;
		this.modified=loc.modified;
		this.actualOutlier=loc.actualOutlier;
		this.clientId=loc.clientId;
		this.clientCode=loc.clientCode;
		this.startWorkReason=loc.startWorkReason;
		this.airplaneMode=loc.airplaneMode;
		this.empLocationProvider=loc.empLocationProvider;
		this.drafted=loc.drafted;
		this.postalCode=loc.postalCode;
		this.country=loc.country;
		this.adminArea=loc.adminArea;
		
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	public Double getWorkLat() {
		return workLat;
	}
	public void setWorkLat(Double workLat) {
		this.workLat = workLat;
	}
	public Double getWorkLong() {
		return workLong;
	}
	public void setWorkLong(Double workLong) {
		this.workLong = workLong;
	}
	public boolean isMockLocation() {
		return mockLocation;
	}
	public void setMockLocation(boolean mockLocation) {
		this.mockLocation = mockLocation;
	}
	@JsonIgnore
	public String getClientDate() {
		return clientDate;
	}
	@JsonIgnore
	public void setClientDate(String clientDate) {
		this.clientDate = clientDate;
	}
	public Integer getLocationSource() {
		return locationSource;
	}
	public void setLocationSource(Integer locationSource) {
		this.locationSource = locationSource;
	}
	
	@JsonIgnore
	public Integer getDistanceResolveStatus() {
		return distanceResolveStatus;
	}
	@JsonIgnore
	public void setDistanceResolveStatus(Integer distanceResolveStatus) {
		this.distanceResolveStatus = distanceResolveStatus;
	}
	@JsonIgnore
	public int getCompanyId() {
		return companyId;
	}
	@JsonIgnore
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public boolean isDrafted() {
		return drafted;
	}
	public void setDrafted(boolean drafted) {
		this.drafted = drafted;
	}
	public String getEmployeeSignInReason() {
		return employeeSignInReason;
	}
	public void setEmployeeSignInReason(String employeeSignInReason) {
		this.employeeSignInReason = employeeSignInReason;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAdminArea() {
		return adminArea;
	}
	public void setAdminArea(String adminArea) {
		this.adminArea = adminArea;
	}
	
	
	
}
