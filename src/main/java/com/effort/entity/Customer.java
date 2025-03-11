package com.effort.entity;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.effort.util.Api;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public final static int CUSTOMER_NAME=1;
	public final static int CUSTOMER_ID=2;
	public final static int CUSTOMER_PHONE=3;
	public final static int CUSTOMER_LOCATION=4;
	public final static int CUSTOMER_STREET=5;
	public final static int CUSTOMER_AREA=6;
	public final static int CUSTOMER_CITY=7;
	public final static int CUSTOMER_LANDMARK=8;
	public final static int CUSTOMER_STATE=9;
	public final static int CUSTOMER_COUNTRY=10;
	public final static int CUSTOMER_PINCODE=11;
	public final static int CUSTOMER_PRIMARY_CONTACT_FIRST_NAME=12;
	public final static int CUSTOMER_PRIMARY_CONTACT_LAST_NAME=13;
	public final static int CUSTOMER_PRIMARY_CONTACT_TITLE=14;
	public final static int CUSTOMER_PRIMARY_CONTACT_EMAIL=15;
	public final static int CUSTOMER_PRIMARY_CONTACT_PHONE=16;
	public final static int CUSTOMER_SECONDARY_CONTACT_FIRST_NAME=17;
	public final static int CUSTOMER_SECONDARY_CONTACT_LAST_NAME=18;
	public final static int CUSTOMER_SECONDARY_CONTACT_TITLE=19;
	public final static int CUSTOMER_SECONDARY_CONTACT_EMAIL=20;
	public final static int CUSTOMER_SECONDARY_CONTACT_PHONE=21;
	public final static int CUSTOMER_DISTRICT=22;
	public final static int CUSTOMER_TYPE=23;
	public final static int CUSTOMER_TERRITORY=24;
	public final static int EMPLOYEEIDS_MAPPED_TOCUSTOMERS=25;
	public final static int EMPLOYEENAMES_MAPPED_TOCUSTOMERS=26;
	public final static int CUSTOMER_CUSTOM_TAGS=-1;
	public final static int CUSTOMER_SPECIAL_TAGS=-2;
	public final static int CUSTOMER_PARENT_ID=-3;
	public final static int CUSTOMER_IS_PARENT=-4;
	
	public final static int CUSTOMER_LONGITUDE=-5;
	public final static int CUSTOMER_LATITITUDE=-6;
	public final static int MAPPED_TO_EMPLOYEES=-7;
	public final static int PARENT_NAME=-8;//in export only
	
	//following 2 used in bulk upload template xls sheet
	public final static String CUSTOMER_STATIC_FIELD_PREFIX = "CFID_"; 
	public final static String CUSTOMER_FORM_FIELD_PREFIX = "FFID_"; 
	
	public final static int SUCCESS = 1;
	public final static int FAIL = 0;
	
	public final static int ACTION_FROM_WEB = 1;
	public final static int ACTION_FROM_CLIENT = 2;
	public final static int ACTION_FROM_API = 3;
	public final static int ACTION_FROM_BULK_UPLOAD = 4;
	public final static int ACTION_FROM_REQUITION = 5;
	
	public final static int SUCCESS_CUSTOMER_EMAIL_VERIFICATION_STATUS = 1;
	public final static int PENDING_CUSTOMER_EMAIL_VERIFICATION_STATUS = 0;
	public final static int ACTION_FROM_XERO_INTEGRATION = 6;
	
	

		
	
	private long customerId;
	private String clientCustomerId;
	private long companyId;
	private String customerNo;
	private String customerName;
	private Long customerTypeId;
	private String customerTypeColorName;
	private String customerTypeName;
	private String customerAddressStreet;
	private String customerAddressArea;
	private String customerAddressCity;
	private String customerAddressPincode;
	private String customerAddressLandMark;
	private String customerAddressState;
	private String customerAddressCountry;
	private String customerPhone;
	private String customerLat;
	private String customerLong;
	private String primaryContactFirstName;
	private String primaryContactLastName;
	private String primaryContactPhone;
	private String primaryContactTitle;
	private String primaryContactEmail;
	private String primaryContactPhotoUrl;
	private String secondaryContactFirstName;
	private String secondaryContactLastName;
	private String secondaryContactPhone;
	private String secondaryContactTitle;
	private String secondaryContactEmail;
	private String secondaryContactPhotoUrl;
	private String logoUrl;
	private boolean deleted;
	private String customerIds;
	private String customerAddress;
	private String externalId;
	private String apiUserId;
	private String clientCustomerFormId;
	private String customerType;
	private boolean webuser;
	
	private boolean mapeed;
	private String jobLabel="Job";
	
	private boolean isBulkUpload;
	private String c1001;
	
	
	private Long customerFormId;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String error;
	private Long parentFormId;
	private String customersMapped;
	
	private boolean locked = false;
	private Long lockedBy;
	private String empName;
	private String lockedTime;
	
	private List<Customer> nearestCustomers;


	private String presentInRoutes;
	
	private String brNo;
	
	private String tagId;
	private String tagName;
	private String tagIds;
	private String customerTypes;
	
	
	
	private String customTagsCSV;
	private String specialTagsCSV;
 	private String createTime;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String modifiedTime;
	private String customerAddressDistrict;
	
	private String filtCustomersCondition;
	
	private String clientSideId;
	private String empIdForMappedCustomers;
	
	private String fromDateTime;
	private String toDateTime;
	private boolean createdTimeChecked;
	private boolean modifiedTimeChecked;
	
	private String employeeId;
	
	private boolean isOpenMap = false;
	private boolean customerCheckIn = false;
	/**
	 * 
	 * fieldsListFilteringMap property is used to filter customers in create Form and Modify Form only.. 
	 * @return
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	private String fieldsListFilteringMap;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long customerCreatedBy;
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long customerModifiedBy;
	
	private Long parentId=-1l;

	private boolean parent;
	
	private String parentName;
	
	private Integer customerHierarchy;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String checksum;
	private Long customerTerritoryId;
	private String territoryIds;
	private String territoryName;
	private Long count;
	private String customerSearchName;
	
	private String isParentString;

	private String mapToEmpIds;
	private String mapToEmpNos;
	private String CustomerNoForDelete;
	

	private List<Long> yearList;
	private Map<Long,String> monthMap;
	private List<Long> dayList;
	private String filtType;
	private String filtCustomerIds;
	
	private Integer order;
	private Integer sourceOfAction;
	private String htmlTitle;
	private boolean ignoreCustomerUserDefinedFields;
	private boolean newCustomer;
	
	private String customerCreatedByName;
	private String plannedVisitFromDate;
	private String plannedVisitToDate;
	
	private Long customerCheckInId;
	private String categoryFieldValue;
	private String categoryDisplayValue;
	
	private String managerNamesCsvL1;
	private String managerNamesCsvL2;
	
	private String mappedEmpName;
	
	private String currentOAFieldValue;
	private String currentOADisplayValue;
	
	private String checkInLocationTime;
	
	private boolean isUpdateFromRequisition;
	private boolean customerCheckInCheckOut;
	private String lastCheckedInTime;
	private boolean madatoryActivities;
	private String mandatoryActivityCondition;
	private boolean incompleteCustInDP;
	private boolean customerVisitCompleted;
	private String oaCategoryEntityId;
	private String dayPlanDate;
	private String empFirstName;
	private String empLastName;
	private Integer sourceOfModification;
	private String remarks;
	private boolean customerGraphFilter;
	private String graphFilterCustomerIds;
	private long incompleteWorksCount;
	private String requisitionFormId;
	private Integer customerEmailVerification = 0;

	private String requestDomain;
	private boolean parentPropExistInAPI;

	public Integer getSourceOfAction() {
		return sourceOfAction;
	}
	public void setSourceOfAction(Integer sourceOfAction) {
		this.sourceOfAction = sourceOfAction;
	}
	public String getFieldsListFilteringMap() {
		return fieldsListFilteringMap;
	}
	public void setFieldsListFilteringMap(String fieldsListFilteringMap) {
		this.fieldsListFilteringMap = fieldsListFilteringMap;
	}
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	public FormAndField getFormAndField() {
		return formAndField;
	}
	public void setFormAndField(FormAndField formAndField) {
		this.formAndField = formAndField;
	}
	
	
	public Long getCustomerFormId() {
		return customerFormId;
	}
	public void setCustomerFormId(Long customerFormId) {
		this.customerFormId = customerFormId;
	}
	/// For Effort Map feature 
	
	public String getCustomerTypeName() {
		if(Api.isEmptyString(customerTypeName)){
			return "Not defined";
		}
		return customerTypeName;
	}
	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}
	
	public String getCustomerTypeColorName() {
		if(Api.isEmptyString(customerTypeColorName)){
			return "default";
		}
		return customerTypeColorName;
	}
	public void setCustomerTypeColorName(String customerTypeColorName) {
		this.customerTypeColorName = customerTypeColorName;
	}
	public String getJobLabel() {
		return jobLabel;
	}
	public void setJobLabel(String jobLabel) {
		this.jobLabel = jobLabel;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getClientCustomerId() {
		return clientCustomerId;
	}
	public void setClientCustomerId(String clientCustomerId) {
		this.clientCustomerId = clientCustomerId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = Api.makeNullIfEmpty(customerName);
	}
	public String getCustomerNameForMap(){
		if(!Api.isEmptyString(customerName)){
			customerName=customerName.replaceAll("'", "&apos;");
		}
		return customerName;
	}
	public Long getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(Long customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getCustomerAddressStreet() {
		return customerAddressStreet;
	}
	public void setCustomerAddressStreet(String customerAddressStreet) {
		this.customerAddressStreet = customerAddressStreet;
	}
	public String getCustomerAddressArea() {
		return customerAddressArea;
	}
	public void setCustomerAddressArea(String customerAddressArea) {
		this.customerAddressArea = customerAddressArea;
	}
	public String getCustomerAddressCity() {
		return customerAddressCity;
	}
	public void setCustomerAddressCity(String customerAddressCity) {
		this.customerAddressCity = customerAddressCity;
	}
	public String getCustomerAddressPincode() {
		return customerAddressPincode;
	}
	public void setCustomerAddressPincode(String customerAddressPincode) {
		this.customerAddressPincode = customerAddressPincode;
	}
	public String getCustomerAddressLandMark() {
		return customerAddressLandMark;
	}
	public void setCustomerAddressLandMark(String customerAddressLandMark) {
		this.customerAddressLandMark = customerAddressLandMark;
	}
	public String getCustomerAddressState() {
		return customerAddressState;
	}
	public void setCustomerAddressState(String customerAddressState) {
		this.customerAddressState = customerAddressState;
	}
	public String getCustomerAddressCountry() {
		return customerAddressCountry;
	}
	public void setCustomerAddressCountry(String customerAddressCountry) {
		this.customerAddressCountry = customerAddressCountry;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = Api.makeNullIfEmpty(customerPhone);
	}
	public String getCustomerLat() {
		return customerLat;
	}
	public void setCustomerLat(String customerLat) {
		this.customerLat = Api.makeNullIfEmpty(customerLat);
	}
	public String getCustomerLong() {
		return customerLong;
	}
	public void setCustomerLong(String customerLong) {
		this.customerLong = Api.makeNullIfEmpty(customerLong);
	}
	public String getPrimaryContactFirstName() {
		return primaryContactFirstName;
	}
	public void setPrimaryContactFirstName(String primaryContactFirstName) {
		this.primaryContactFirstName = primaryContactFirstName;
	}
	public String getPrimaryContactLastName() {
		return primaryContactLastName;
	}
	public void setPrimaryContactLastName(String primaryContactLastName) {
		this.primaryContactLastName = primaryContactLastName;
	}
	public String getPrimaryContactPhone() {
		return primaryContactPhone;
	}
	public void setPrimaryContactPhone(String primaryContactPhone) {
		this.primaryContactPhone = Api.makeNullIfEmpty(primaryContactPhone);
	}
	public String getPrimaryContactTitle() {
		return primaryContactTitle;
	}
	public void setPrimaryContactTitle(String primaryContactTitle) {
		this.primaryContactTitle = Api.makeNullIfEmpty(primaryContactTitle);
	}
	public String getPrimaryContactEmail() {
		return primaryContactEmail;
	}
	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = Api.makeNullIfEmpty(primaryContactEmail);
	}
	public String getPrimaryContactPhotoUrl() {
		return primaryContactPhotoUrl;
	}
	public void setPrimaryContactPhotoUrl(String primaryContactPhotoUrl) {
		this.primaryContactPhotoUrl = Api.makeNullIfEmpty(primaryContactPhotoUrl);
	}
	public String getSecondaryContactFirstName() {
		return secondaryContactFirstName;
	}
	public void setSecondaryContactFirstName(String secondaryContactFirstName) {
		this.secondaryContactFirstName = secondaryContactFirstName;
	}
	public String getSecondaryContactLastName() {
		return secondaryContactLastName;
	}
	public void setSecondaryContactLastName(String secondaryContactLastName) {
		this.secondaryContactLastName = secondaryContactLastName;
	}
	public String getSecondaryContactPhone() {
		return secondaryContactPhone;
	}
	public void setSecondaryContactPhone(String secondaryContactPhone) {
		this.secondaryContactPhone = Api.makeNullIfEmpty(secondaryContactPhone);
	}
	public String getSecondaryContactTitle() {
		return secondaryContactTitle;
	}
	public void setSecondaryContactTitle(String secondaryContactTitle) {
		this.secondaryContactTitle = Api.makeNullIfEmpty(secondaryContactTitle);
	}
	public String getSecondaryContactEmail() {
		return secondaryContactEmail;
	}
	public void setSecondaryContactEmail(String secondaryContactEmail) {
		this.secondaryContactEmail = Api.makeNullIfEmpty(secondaryContactEmail);
	}
	public String getSecondaryContactPhotoUrl() {
		return secondaryContactPhotoUrl;
	}
	public void setSecondaryContactPhotoUrl(String secondaryContactPhotoUrl) {
		this.secondaryContactPhotoUrl = Api.makeNullIfEmpty(secondaryContactPhotoUrl);
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	public String getExternalId() {
		return customerNo;
	}
	public void setExternalId(String customerNo) {
		this.customerNo = customerNo;
	}	
	public String getCustomerType() {
		return customerType;
	}
	
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public String getClientCustomerFormId() {
		return clientCustomerFormId;
	}
	public void setClientCustomerFormId(String clientCustomerFormId) {
		this.clientCustomerFormId = clientCustomerFormId;
	}
	
	@JsonIgnore
	public boolean isWebuser() {
		return webuser;
	}
	@JsonIgnore
	public void setWebuser(boolean webuser) {
		this.webuser = webuser;
	}
	@JsonIgnore
	public String getApiUserId() {
		return apiUserId;
	}
	@JsonIgnore
	public void setApiUserId(String apiUserId) {
		this.apiUserId = apiUserId;
	}
	@JsonIgnore
	public CustomerAccessSettings getCustomerAccessSettings() {
		return customerAccessSettings;
	}
	@JsonIgnore
	public void setCustomerAccessSettings(CustomerAccessSettings customerAccessSettings) {
		this.customerAccessSettings = customerAccessSettings;
	}
	@JsonIgnore
	public boolean isMapeed() {
		return mapeed;
	}
	@JsonIgnore
	public void setMapeed(boolean mapeed) {
		this.mapeed = mapeed;
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getAddress(){
		String address = "";
		
		if(!Api.isEmptyString(customerAddressStreet)){
			address += customerAddressStreet;
		}
		
		if(!Api.isEmptyString(customerAddressArea)){
			if(address.length() > 0){
				address += ", ";
			}
			address += customerAddressArea;
		}
		
		if(!Api.isEmptyString(customerAddressCity)){
			if(address.length() > 0){
				address += ", ";
			}
			address += customerAddressCity;
		}
		
		if(!Api.isEmptyString(customerAddressDistrict)){
			if(address.length() > 0){
				address += ", ";
			}
			address += customerAddressDistrict;
		}
		
		if(!Api.isEmptyString(customerAddressPincode)){
			if(address.length() > 0){
				address += ", ";
			}
			address += customerAddressPincode;
		}
		return address;
	}
	
	private boolean enableAddJobInMap = true;
	@JsonIgnore
	public boolean isEnableAddJobInMap() {
		return enableAddJobInMap;
	}
	@JsonIgnore
	public void setEnableAddJobInMap(boolean enableAddJobInMap) {
		this.enableAddJobInMap = enableAddJobInMap;
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getHtmlTitle(String time){
		if(!Api.isEmptyString(htmlTitle)) {
			return htmlTitle;
		}
		String contentString = "'<div id=\"content\">"+
				 "<div id=\"siteNotice\">"+
		         "</div>"+
		         "<div id=\"bodyContent\">"+
		         "<table border=\"0\">"+
		         "<tr>"+
		         "<td><img src=\"/"+Constants.getProject()+"/resources/img/company_ico.png\" /></td>"+
		         "<td><table width=\"100%\" height=\"100%\" border=\"0\" >"+
		         "<tr>"
		         ;
		 contentString += "<tr>"+
		         //${pageContext.servletContext.contextPath}/web/customer/modifi/
				 "<td><a href=\"/"+Constants.getProject()+"/web/customer/view/new/"+getCustomerId()+"\" target=\"_blank\">"+getCustomerNameForMap()+"</a></td>"+
		         "</tr><tr>"+
		         "<td>"+Api.escapChar(Api.makeEmptyIfNull(Api.replaceAllNewLineChar("Type :"+getCustomerTypeName())),'\'', '\n', '\r')+"</td>"+
		         "</tr>"+
		         "<tr>"+
		         "<td>"+Api.escapChar(Api.makeEmptyIfNull(Api.replaceAllNewLineChar(getCustomerPhone())),'\'', '\n', '\r')+"</td>"+
		         "</tr>"+
		         "<tr>"+
		         "<td>"+Api.escapChar(Api.makeEmptyIfNull(Api.replaceAllNewLineChar(getAddress())),'\'', '\n', '\r')+"</td>"+
		         "</tr>";
		
		 if(enableAddJobInMap){
			 contentString += "<tr>"+
		         "<td><a style=\"display:block\" href=\"/"+Constants.getProject()+"/web/job/create?customerId="+getCustomerId()+"\" target=\"_blank\">Create :job</a></td> <td style=\"text-align: right;\"><a onclick=\"openAddWorkForCustomer("+getCustomerId()+")\" href=\"#\"> Create :work </a></td>  "+
		         "</tr>";
		 }
		 
		 if(!Api.isEmptyString(time)){
			 contentString += "<tr>"+
		         "<td>Time: "+time+"</td>"+
		         "</tr>";
		 }
		 
		 contentString += "</table></td>"+
		         "</tr>"+
		         "</table>"+
		         "</div>"+
		         "</div>'";
		 
		 return contentString;
	}
	
	@Override
	public String toString() {
		String resp = "[";
		resp += getHtmlTitle(null);
		if(isOpenMap()){
			resp += ", ol.proj.transform(["+ getCustomerLong() + ", " + getCustomerLat() +"], 'EPSG:4326', 'EPSG:3857') " ;//", new OpenLayers.LonLat(" + getCustomerLong() + ", " + getCustomerLat() + ")";
		}else{
		resp += ", new google.maps.LatLng(" + getCustomerLat() + ", " + getCustomerLong() + ")";
		}
		resp += ", " + 6;
		resp += ", '" + Api.replaceAllNewLineChar(getCustomerName()) + "'";
		resp += ", '" + Api.replaceAllNewLineChar(getCustomerTypeColorName()) + "'";
		resp += ", " + getCustomerId();
		resp += "]";
		return resp;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Customer){
			return getCustomerId() == ((Customer) obj).getCustomerId();
		} else {
			return super.equals(obj);
		}
	}
	
	public String toCSV() {
		return "[customerId=" + customerId + ", clientCustomerId="
				+ clientCustomerId + ", companyId=" + companyId
				+ ", customerName=" + customerName + ", customerAddressStreet="
				+ customerAddressStreet + ", customerAddressArea="
				+ customerAddressArea + ", customerAddressCity="
				+ customerAddressCity + ", customerAddressDistrict=" + customerAddressDistrict + ", customerAddressPincode="
				+ customerAddressPincode + ", customerPhone=" + customerPhone
				+ ", customerLat=" + customerLat + ", customerLong="
				+ customerLong + ", primaryContactFirstName="
				+ primaryContactFirstName + ", primaryContactLastName="
				+ primaryContactLastName + ", primaryContactPhone="
				+ primaryContactPhone + ", primaryContactTitle="
				+ primaryContactTitle + ", primaryContactEmail="
				+ primaryContactEmail + ", primaryContactPhotoUrl="
				+ primaryContactPhotoUrl + ", secondaryContactFirstName="
				+ secondaryContactFirstName + ", secondaryContactLastName="
				+ secondaryContactLastName + ", secondaryContactPhone="
				+ secondaryContactPhone + ", secondaryContactTitle="
				+ secondaryContactTitle + ", secondaryContactEmail="
				+ secondaryContactEmail + ", secondaryContactPhotoUrl="
				+ secondaryContactPhotoUrl + ", logoUrl=" + logoUrl + "]";
	}
	
	public boolean isEmptyRow(){
		if(!Api.isEmptyString(getCustomerName())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerNo())){
			return false;
		}
		if(getCustomerTypeId()!=null && getCustomerTypeId()!=0){
			return false;
		}
		if(!Api.isEmptyString(getCustomerPhone())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerLat())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerLong())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerAddressStreet())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerAddressArea())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerAddressCity())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerAddressDistrict())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerAddressPincode())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerAddressLandMark())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerAddressState())){
			return false;
		}
		if(!Api.isEmptyString(getCustomerAddressCountry())){
			return false;
		}
		if(!Api.isEmptyString(getPrimaryContactFirstName())){
			return false;
		}
		if(!Api.isEmptyString(getPrimaryContactLastName())){
			return false;
		}
		if(!Api.isEmptyString(getPrimaryContactTitle())){
			return false;
		}
		if(!Api.isEmptyString(getPrimaryContactEmail())){
			return false;
		}
		if(!Api.isEmptyString(getSecondaryContactFirstName())){
			return false;
		}
		if(!Api.isEmptyString(getSecondaryContactLastName())){
			return false;
		}
		
		if(!Api.isEmptyString(getSecondaryContactTitle())){
			return false;
		}
		
		if(!Api.isEmptyString(getSecondaryContactPhone())){
			return false;
		}
		
		if(!Api.isEmptyString(getSecondaryContactEmail())){
			return false;
		}
		
		if(getCustomTagsCSV() != null && !Api.isEmptyString(getCustomTagsCSV()))
		{
			return false;
		}
		
		if(getSpecialTagsCSV() != null && !Api.isEmptyString(getSpecialTagsCSV()))
		{
			return false;
		}
		
		return true;
	}
	
	
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getPrimaryContactName(){
		String primaryContactName = ""; 
		
		if(!Api.isEmptyString(getPrimaryContactFirstName())){
			primaryContactName += getPrimaryContactFirstName();
		}
		
		if(!Api.isEmptyString(getPrimaryContactLastName())){
			if(!Api.isEmptyString(primaryContactName)){
				primaryContactName += (" " + getPrimaryContactLastName());
			} else {
				primaryContactName += getPrimaryContactLastName();
			}
		}
		
		return primaryContactName;
	}
	public boolean isBulkUpload() {
		return isBulkUpload;
	}
	public void setBulkUpload(boolean isBulkUpload) {
		this.isBulkUpload = isBulkUpload;
	}
	public Long getParentFormId() {
		return parentFormId;
	}
	public void setParentFormId(Long parentFormId) {
		this.parentFormId = parentFormId;
	}
	public String getCustomersMapped() {
		return customersMapped;
	}
	public void setCustomersMapped(String customersMapped) {
		this.customersMapped = customersMapped;
	}
	
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean isLocked) {
		this.locked = isLocked;
	}
	
	public Long getLockedBy() {
		return lockedBy;
	}
	public void setLockedBy(Long lockedBy) {
		this.lockedBy = lockedBy;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getLockedTime() {
		return lockedTime;
	}
	public void setLockedTime(String lockedTime) {
		this.lockedTime = lockedTime;
	}
	
	public List<Customer> getNearestCustomers() {
		return nearestCustomers;
	}
	
	public void setNearestCustomers(List<Customer> nearestCustomers) {
		this.nearestCustomers = nearestCustomers;
	}
	
	public List<NearestCustomer> getNearestCustomers1() {
		return nearestCustomers1;
	}
	
	
	public void setNearestCustomers1(List<NearestCustomer> nearestCustomers1) {
		this.nearestCustomers1 = nearestCustomers1;
	}
	
	public String getPresentInRoutes() {
		return presentInRoutes;
	}
	
	public void setPresentInRoutes(String presentInRoutes) {
		this.presentInRoutes = presentInRoutes;
	}
	
	public String getBrNo() {
		return brNo;
	}
	
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public static List<Columns> getCustomersColumns(){
		List<Columns> columns=new ArrayList<Columns>();
		Columns column=new Columns();
		column.setTitle("No");column.setDataType("string");column.setDataIndx("customerNo");column.setWidth(150);columns.add(column);
		column = new Columns();column.setTitle("Name");column.setDataType("string");column.setDataIndx("customerName");column.setWidth(150);columns.add(column);
		column= new Columns();column.setTitle("Phone");column.setDataType("string");column.setDataIndx("customerPhone");column.setWidth(150);columns.add(column);
		column= new Columns();column.setTitle("Email");column.setDataType("string");column.setDataIndx("primaryContactEmail");column.setWidth(150);columns.add(column);
		//column= new Columns();column.setTitle("Address");column.setDataType("string");column.setDataIndx("customerAddressStreet");column.setWidth(150);columns.add(column);
		column= new Columns();column.setTitle("Address");column.setDataType("string");column.setDataIndx("customerAddress");column.setWidth(150);columns.add(column);
		return columns;
	}

	@JsonIgnore
	public String getTagId() {
		return tagId;
	}
	@JsonIgnore
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@JsonIgnore
	public String getTagName() {
		return tagName;
	}
	@JsonIgnore
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	

	
	@JsonIgnore
	public String getCustomTagsCSV() {
		return customTagsCSV;
	}
	
	@JsonIgnore
	public void setCustomTagsCSV(String customTagsCSV) {
		this.customTagsCSV = customTagsCSV;
	}
	
	@JsonIgnore
	public String getSpecialTagsCSV() {
		return specialTagsCSV;
	}
	
	@JsonIgnore
	public void setSpecialTagsCSV(String specialTagsCSV) {
		this.specialTagsCSV = specialTagsCSV;
	}
	
	
	public Long getCustomerCreatedBy() {
		return customerCreatedBy;
	}
	public void setCustomerCreatedBy(Long customerCreatedBy) {
		this.customerCreatedBy = customerCreatedBy;
	}
	
	public Long getCustomerModifiedBy() {
		return customerModifiedBy;
	}
	public void setCustomerModifiedBy(Long customerModifiedBy) {
		this.customerModifiedBy = customerModifiedBy;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getCustomerAddressDistrict() {
		return customerAddressDistrict;
	}
	public void setCustomerAddressDistrict(String customerAddressDistrict) {
		this.customerAddressDistrict = customerAddressDistrict;
	}
	
	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public boolean isParent() {
		return parent;
	}
	public void setParent(boolean parent) {
		this.parent = parent;
	}
	
	public String getParentName() {
		return parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public Integer getCustomerHierarchy() {
		return customerHierarchy;
	}
	public void setCustomerHierarchy(Integer customerHierarchy) {
		this.customerHierarchy = customerHierarchy;
	}
	public String getCustomerIds() {
		return customerIds;
	}
	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}
	public String getTagIds() {
		return tagIds;
	}
	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}
	public String getCustomerTypes() {
		return customerTypes;
	}
	public void setCustomerTypes(String customerTypes) {
		this.customerTypes = customerTypes;
	}
	public String getCustomerAddress() {
		
		if(customerAddress!=null)
		{
			customerAddress = Api.removeTrailingCommaFormString(customerAddress);
		}
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	
	public String getChecksum() {
		if(checksum !=null)
			return checksum;
		else
			return Api.getMD5EncodedValue(getConcatenatedCustomerObj());
	}
	
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public String getConcatenatedCustomerObj(){
		
		/*return	customerName+customerTypeId+customerAddressStreet+customerAddressArea+
							customerAddressCity+customerAddressPincode+customerAddressLandMark+customerAddressState+customerAddressCountry+
							customerPhone+customerLat+customerLong+primaryContactFirstName+primaryContactLastName+primaryContactPhone+
							primaryContactTitle+primaryContactEmail+primaryContactPhotoUrl+secondaryContactFirstName+secondaryContactLastName+
							secondaryContactPhone+secondaryContactTitle+secondaryContactEmail+secondaryContactPhotoUrl+logoUrl+createTime+modifiedTime+
							externalId+apiUserId+deleted+customerFormId+parentFormId+lockedBy+locked+lockedTime+customerCreatedBy+
							customerModifiedBy+customerAddressDistrict+parentId+parent;*/
		return	customerName+customerTypeId+customerAddressStreet+customerAddressArea+
				customerAddressCity+customerAddressPincode+customerAddressLandMark+customerAddressState+customerAddressCountry+
				customerPhone+customerLat+customerLong+primaryContactFirstName+primaryContactLastName+primaryContactPhone+
				primaryContactTitle+primaryContactEmail+primaryContactPhotoUrl+secondaryContactFirstName+secondaryContactLastName+
				secondaryContactPhone+secondaryContactTitle+secondaryContactEmail+secondaryContactPhotoUrl+logoUrl+
				externalId+apiUserId+deleted+customerFormId+parentFormId+lockedBy+locked+lockedTime+customerCreatedBy+
				customerModifiedBy+customerAddressDistrict+parentId+parent;
							
	}
	public String getFiltCustomersCondition() {
		return filtCustomersCondition;
	}
	public void setFiltCustomersCondition(String filtCustomersCondition) {
		this.filtCustomersCondition = filtCustomersCondition;
	}
	public Long getCustomerTerritoryId() {
		return customerTerritoryId;
	}
	public void setCustomerTerritoryId(Long customerTerritoryId) {
		this.customerTerritoryId = customerTerritoryId;
	}
	public String getTerritoryIds() {
		return territoryIds;
	}
	public void setTerritoryIds(String territoryIds) {
		this.territoryIds = territoryIds;
	}
	public String getTerritoryName() {
		return territoryName;
	}
	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}
	public String getClientSideId() {
		return clientSideId;
	}
	public void setClientSideId(String clientSideId) {
		this.clientSideId = clientSideId;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getCustomerSearchName() {
		return customerSearchName;
	}
	public void setCustomerSearchName(String customerSearchName) {
		this.customerSearchName = customerSearchName;
	}
	
	
	
	public String getEmpIdForMappedCustomers() {
		return empIdForMappedCustomers;
	}
	public void setEmpIdForMappedCustomers(String empIdForMappedCustomers) {
		this.empIdForMappedCustomers = empIdForMappedCustomers;
	}
	public String getFromDateTime() {
		return fromDateTime;
	}
	public void setFromDateTime(String fromDateTime) {
		this.fromDateTime = fromDateTime;
	}
	public String getToDateTime() {
		return toDateTime;
	}
	public void setToDateTime(String toDateTime) {
		this.toDateTime = toDateTime;
	}
	public boolean isCreatedTimeChecked() {
		return createdTimeChecked;
	}
	public void setCreatedTimeChecked(boolean createdTimeChecked) {
		this.createdTimeChecked = createdTimeChecked;
	}
	public boolean isModifiedTimeChecked() {
		return modifiedTimeChecked;
	}
	public void setModifiedTimeChecked(boolean modifiedTimeChecked) {
		this.modifiedTimeChecked = modifiedTimeChecked;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	
	public boolean isOpenMap() {
		return isOpenMap;
	}
	public void setOpenMap(boolean isOpenMap) {
		this.isOpenMap = isOpenMap;
	}
	public String getIsParentString() {
		return isParentString;
	}
	public void setIsParentString(String isParentString) {
		this.isParentString = isParentString;
	}
	public List<Fields> getFields() {
		return fields;
	}
	public void setFields(List<Fields> fields) {
		this.fields = fields;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	public String getCustomerFieldValuById(Integer customerId){
		String fieldValue = "";
		
		switch(customerId){
		
		case 1 : if(!Api.isEmptyString(customerName)){
					fieldValue = customerName;
		}
		
		case 23 : if(customerTypeId != null){
			fieldValue = customerTypeId+"";
		}
		
		}
		return fieldValue;
	}
	
	
	@JsonProperty(access = Access.WRITE_ONLY)
	public Integer getFieldTyoeValuByCustomerFieldId(Integer customerId){
		Integer fieldType = 0;
		
		if(customerId==23){
			fieldType = 24;
		}
		
		return fieldType;
	}
	public String getMapToEmpIds() {
		return mapToEmpIds;
	}
	public void setMapToEmpIds(String mapToEmpIds) {
		this.mapToEmpIds = mapToEmpIds;
	}
	public String getMapToEmpNos() {
		return mapToEmpNos;
	}
	public void setMapToEmpNos(String mapToEmpNos) {
		this.mapToEmpNos = mapToEmpNos;
	}
	public String getCustomerNoForDelete() {
		return CustomerNoForDelete;
	}

	public void setCustomerNoForDelete(String customerNoForDelete) {
		CustomerNoForDelete = customerNoForDelete;
	}

	public List<Long> getYearList() {
		return yearList;
	}
	public void setYearList(List<Long> yearList) {
		this.yearList = yearList;
	}
	
	public Map<Long, String> getMonthMap() {
		return monthMap;
	}
	public void setMonthMap(Map<Long, String> monthMap) {
		this.monthMap = monthMap;
	}
	public List<Long> getDayList() {
		return dayList;
	}
	public void setDayList(List<Long> dayList) {
		this.dayList = dayList;
	}
	
	public String getFiltType() {
		return filtType;
	}
	public void setFiltType(String filtType) {
		this.filtType = filtType;
	}
	public String getFiltCustomerIds() {
		return filtCustomerIds;
	}
	public void setFiltCustomerIds(String filtCustomerIds) {
		this.filtCustomerIds = filtCustomerIds;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getC1001() {
		return c1001;
	}
	public void setC1001(String c1001) {
		this.c1001 = c1001;
	}
	
	public void setHtmlTitle(String htmlTitle)
	{
		this.htmlTitle=htmlTitle;
	}
	public boolean isIgnoreCustomerUserDefinedFields() {
		return ignoreCustomerUserDefinedFields;
	}
	public void setIgnoreCustomerUserDefinedFields(boolean ignoreCustomerUserDefinedFields) {
		this.ignoreCustomerUserDefinedFields = ignoreCustomerUserDefinedFields;
	}
	@JsonIgnore
    public boolean isNewCustomer() {
            return newCustomer;
    }
    @JsonIgnore
    public void setNewCustomer(boolean newCustomer) {
            this.newCustomer = newCustomer;
    }
    @JsonIgnore
	public String getCustomerCreatedByName() {
		return customerCreatedByName;
	}
    @JsonIgnore
	public void setCustomerCreatedByName(String customerCreatedByName) {
		this.customerCreatedByName = customerCreatedByName;
	}
	public boolean isCustomerCheckIn() {
		return customerCheckIn;
	}
	public void setCustomerCheckIn(boolean customerCheckIn) {
		this.customerCheckIn = customerCheckIn;
	}
	public String getPlannedVisitFromDate() {
		return plannedVisitFromDate;
	}
	public void setPlannedVisitFromDate(String plannedVisitFromDate) {
		this.plannedVisitFromDate = plannedVisitFromDate;
	}
	public String getPlannedVisitToDate() {
		return plannedVisitToDate;
	}
	public void setPlannedVisitToDate(String plannedVisitToDate) {
		this.plannedVisitToDate = plannedVisitToDate;
	}
	public Long getCustomerCheckInId() {
		return customerCheckInId;
	}
	public void setCustomerCheckInId(Long customerCheckInId) {
		this.customerCheckInId = customerCheckInId;
	}
	public String getCategoryFieldValue() {
		return categoryFieldValue;
	}
	public void setCategoryFieldValue(String categoryFieldValue) {
		this.categoryFieldValue = categoryFieldValue;
	}
	public String getCategoryDisplayValue() {
		return categoryDisplayValue;
	}
	public void setCategoryDisplayValue(String categoryDisplayValue) {
		this.categoryDisplayValue = categoryDisplayValue;
	}
	
	public String getCurrentOAFieldValue() {
		return currentOAFieldValue;
	}
	public void setCurrentOAFieldValue(String currentOAFieldValue) {
		this.currentOAFieldValue = currentOAFieldValue;
	}
	public String getCurrentOADisplayValue() {
		return currentOADisplayValue;
	}
	public void setCurrentOADisplayValue(String currentOADisplayValue) {
		this.currentOADisplayValue = currentOADisplayValue;
	}
	public String getCheckInLocationTime() {
		return checkInLocationTime;
	}
	public void setCheckInLocationTime(String checkInLocationTime) {
		this.checkInLocationTime = checkInLocationTime;
	}
	
	public boolean isUpdateFromRequisition() {
		return isUpdateFromRequisition;
	}
	public void setUpdateFromRequisition(boolean isUpdateFromRequisition) {
		this.isUpdateFromRequisition = isUpdateFromRequisition;
	}
	public String getManagerNamesCsvL1() {
		return managerNamesCsvL1;
	}
	public void setManagerNamesCsvL1(String managerNamesCsvL1) {
		this.managerNamesCsvL1 = managerNamesCsvL1;
	}
	public String getManagerNamesCsvL2() {
		return managerNamesCsvL2;
	}
	public void setManagerNamesCsvL2(String managerNamesCsvL2) {
		this.managerNamesCsvL2 = managerNamesCsvL2;
	}
	public String getMappedEmpName() {
		return mappedEmpName;
	}
	public void setMappedEmpName(String mappedEmpName) {
		this.mappedEmpName = mappedEmpName;
	}
	
	public boolean isCustomerCheckInCheckOut() {
		return customerCheckInCheckOut;
	}
	public void setCustomerCheckInCheckOut(boolean customerCheckInCheckOut) {
		this.customerCheckInCheckOut = customerCheckInCheckOut;
	}
	public String getLastCheckedInTime() {
		return lastCheckedInTime;
	}
	public void setLastCheckedInTime(String lastCheckedInTime) {
		this.lastCheckedInTime = lastCheckedInTime;
	}
	public boolean isMadatoryActivities() {
		return madatoryActivities;
	}
	public void setMadatoryActivities(boolean madatoryActivities) {
		this.madatoryActivities = madatoryActivities;
	}
	public String getMandatoryActivityCondition() {
		return mandatoryActivityCondition;
	}
	public void setMandatoryActivityCondition(String mandatoryActivityCondition) {
		this.mandatoryActivityCondition = mandatoryActivityCondition;
	}
	public boolean isIncompleteCustInDP() {
		return incompleteCustInDP;
	}
	public void setIncompleteCustInDP(boolean incompleteCustInDP) {
		this.incompleteCustInDP = incompleteCustInDP;
	}
	public boolean isCustomerVisitCompleted() {
		return customerVisitCompleted;
	}
	public void setCustomerVisitCompleted(boolean customerVisitCompleted) {
		this.customerVisitCompleted = customerVisitCompleted;
	}
	@JsonIgnore
	public String getOaCategoryEntityId() {
		return oaCategoryEntityId;
	}
	@JsonIgnore
	public void setOaCategoryEntityId(String oaCategoryEntityId) {
		this.oaCategoryEntityId = oaCategoryEntityId;
	}
	public String getDayPlanDate() {
		return dayPlanDate;
	}
	public void setDayPlanDate(String dayPlanDate) {
		this.dayPlanDate = dayPlanDate;
	}
	public String getEmpFirstName() {
		return empFirstName;
	}
	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}
	public String getEmpLastName() {
		return empLastName;
	}
	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}



	public Integer getSourceOfModification() {
		return sourceOfModification;
	}
	public void setSourceOfModification(Integer sourceOfModification) {
		this.sourceOfModification = sourceOfModification;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public boolean isCustomerGraphFilter() {
		return customerGraphFilter;
	}
	public void setCustomerGraphFilter(boolean customerGraphFilter) {
		this.customerGraphFilter = customerGraphFilter;
	}
	public String getGraphFilterCustomerIds() {
		return graphFilterCustomerIds;
	}
	public void setGraphFilterCustomerIds(String graphFilterCustomerIds) {
		this.graphFilterCustomerIds = graphFilterCustomerIds;
	}
	public long getIncompleteWorksCount() {
		return incompleteWorksCount;
	}
	public void setIncompleteWorksCount(long incompleteWorksCount) {
		this.incompleteWorksCount = incompleteWorksCount;
	}
	public String getRequisitionFormId() {
		return requisitionFormId;
	}
	public void setRequisitionFormId(String requisitionFormId) {
		this.requisitionFormId = requisitionFormId;
	}
	
	public Integer getCustomerEmailVerification() {
		return customerEmailVerification;
	}
	public void setCustomerEmailVerification(Integer customerEmailVerification) {
		this.customerEmailVerification = customerEmailVerification;
	}
	
	public String getRequestDomain() {
		return requestDomain;
	}
	public void setRequestDomain(String requestDomain) {
		this.requestDomain = requestDomain;
	}
	public boolean isParentPropExistInAPI() {
		return parentPropExistInAPI;
	}
	public void setParentPropExistInAPI(boolean parentPropExistInAPI) {
		this.parentPropExistInAPI = parentPropExistInAPI;
	}
	
}
