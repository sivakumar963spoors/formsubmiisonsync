package com.effort.entity;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import com.effort.beans.http.request.location.*;
import com.effort.util.Api;
import com.effort.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Form implements Cloneable {
	public static int ACTION_TYPE_CREATE = 1;
	public static int ACTION_TYPE_MODIFY = 2;
	
	public  static final int FORM_ACTION_SAVE_OR_UPDATE_TO_DB=1;
	public  static final int FORM_ACTION_RETRIVED_FROM_DB=2;

	private long formId;
	private int companyId;
	private long formSpecId;
	private String formSpecName;
	private int formStatus;
	private Long filledBy;
	private Long modifiedBy;
	@JsonProperty(access = Access.READ_ONLY)
	private Long assignTo;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String createdTime;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String modifiedTime;
	private boolean deleted;
	private String locationId;
	private Location location;
	@JsonProperty(access = Access.READ_ONLY)
	private String filledByName;
	@JsonProperty(access = Access.READ_ONLY)
	private String modifiedByName;
	@JsonProperty(access = Access.READ_ONLY)
	private String assignToName;
	private String clientFormId;
	private int type;
	private String tzo;
	private String identifier;
	private String locationAddress;
	private String latitude;
	private String longitude;

	private String externalId;
	private String apiUserId;

	private boolean forJob;
	private Integer filledByTzo;
	private boolean workFlowForm;
	private boolean canShowEditBasedOnWorkflowCondition;
	private String workflowStatusMessage;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String serverCreatedTime;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String serverModifiedTime;
	private boolean showEdit;
	private boolean showDelete;
	private String formTitle;
	private String uniqueId;
	private String printedOn;
	private boolean commited=true;
	private boolean mediasCommitted;
	

	private String fieldValue; // used for report
	private String displayValue; // used for report
	
	private List<Location> locations;
	private String formFilledLocation;
	
	private Long checkInId;
	
	private Integer stockStatus = -1;
	private Long stockStatusModifiedBy;
	
	private boolean forcePerformActivity = false;
	private int draftForm;
	private List<String> formDraftLocation;
	private Integer isInGroup;
	private String statusMessage;
	private String approvalStatusColor;
	private boolean formSpecPermission;
	private boolean viewPermission;
	private boolean addPermission;
	private boolean modifyPermission;
	private boolean deletePermission;
	
	private Long dependentWorkId;
	private Integer dependentType;
	public static int  DEPENDENT_TYPE_FOR_WORK_FORM = 1;
	public static int  DEPENDENT_TYPE_FOR_WORK_ACTION_FORM = 2;
	private String createdDisplayTime;
	private String dataPushStatus;
	private String error;
	private String formFieldsUniqueKey;
	
	
	public boolean isFormSpecPermission() {
		return formSpecPermission;
	}

	public void setFormSpecPermission(boolean formSpecPermission) {
		this.formSpecPermission = formSpecPermission;
	}

	public boolean isViewPermission() {
		return viewPermission;
	}

	public void setViewPermission(boolean viewPermission) {
		this.viewPermission = viewPermission;
	}

	public boolean isAddPermission() {
		return addPermission;
	}

	public void setAddPermission(boolean addPermission) {
		this.addPermission = addPermission;
	}

	public boolean isModifyPermission() {
		return modifyPermission;
	}

	public void setModifyPermission(boolean modifyPermission) {
		this.modifyPermission = modifyPermission;
	}

	public boolean isDeletePermission() {
		return deletePermission;
	}

	public void setDeletePermission(boolean deletePermission) {
		this.deletePermission = deletePermission;
	}
	
	public Long getApprovalSubmissionTAT() {
		return approvalSubmissionTAT;
	}

	public void setApprovalSubmissionTAT(Long approvalSubmissionTAT) {
		this.approvalSubmissionTAT = approvalSubmissionTAT;
	}

	private String ipAddress;
	private boolean publicForm = false;
	
	private String clientCode;
	

	private boolean formProcessed = false; 
	
	private Long customEntityId;
	
	private Long customEntityCheckInId;
	
	private long workId;
	
	private int count;
	
	private int listUpdateStatus = -1;
	
	private int customEntityUpdateStatus = -1;
	
	private Long approvalSubmissionTAT;
	
	private Long turnAroundTime;
	
	private Boolean listUpdatedFromMobile;
	
	private long customerReqFormId;
	private int customerProcessingResult;
	private String customerRemarks;
	
	private long workReqFormId;
	private int workProcessingResult;
	private String workRemarks;
	
	private long customEntityReqFormId;
	private int customEntityProcessingResult;
	private String customEntityRemarks;
	private String workFlowFormStatus;
	private Integer sourceOfAction;
	private Integer sourceOfModification;

	private String externalFormSpecId;
	
	
	private Map<String,Object> titleFieldsMap;
	
	private String appVersion;
	private Integer formUpdateCount;
	
	private String workActionSpecId;
	private boolean fromMigrationFields;
	private Long migrationFilledBy;
	private Long migrationModifiedBy;
	private String migrationCreatedTime;
	private String migrationModifiedTime;
	
	

	public boolean isMediasCommitted() {
		return mediasCommitted;
	}

	public void setMediasCommitted(boolean mediasCommitted) {
		this.mediasCommitted = mediasCommitted;
	}

	public String getWorkflowStatusMessage() {
		return workflowStatusMessage;
	}

	public void setWorkflowStatusMessage(String workflowStatusMessage) {
		this.workflowStatusMessage = workflowStatusMessage;
	}

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public long getFormSpecId() {
		return formSpecId;
	}

	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}

	public int getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(int formStatus) {
		this.formStatus = formStatus;
	}

	public Long getFilledBy() {
		return filledBy;
	}

	public void setFilledBy(Long filledBy) {
		this.filledBy = filledBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getAssignTo() {
		return assignTo;
	}

	
	public void setAssignTo(Long assignTo) {
		this.assignTo = assignTo;
	}

	public String getClientFormId() {
		return clientFormId;
	}

	public void setClientFormId(String clientFormId) {
		this.clientFormId = clientFormId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	
	@JsonIgnore
	public Integer getFilledByTzo() {
		return filledByTzo;
	}

	@JsonIgnore
	public void setFilledByTzo(Integer filledByTzo) {
		this.filledByTzo = filledByTzo;
	}

	public boolean isCanShowEditBasedOnWorkflowCondition() {
		return canShowEditBasedOnWorkflowCondition;
	}

	public void setCanShowEditBasedOnWorkflowCondition(
			boolean canShowEditBasedOnWorkflowCondition) {
		this.canShowEditBasedOnWorkflowCondition = canShowEditBasedOnWorkflowCondition;
	}

	@JsonProperty("createdTime")
	public String getCreatedTimeXsd() {
		if (!Api.isEmptyString(createdTime)) {
			try {
				return Api.toUtcXsd(createdTime);
			} catch (ParseException e) {
				Log.info(this.getClass(), e.toString(), e);
				return createdTime;
			}
		} else {
			return createdTime;
		}
	}

	@JsonProperty("modifiedTime")
	public String getModifiedTimeXsd() {
		if (!Api.isEmptyString(modifiedTime)) {
			try {
				return Api.toUtcXsd(modifiedTime);
			} catch (ParseException e) {
				Log.info(this.getClass(), e.toString(), e);
				return modifiedTime;
			}
		} else {
			return modifiedTime;
		}
	}
	
	@JsonProperty("serverModifiedTime")
	public String getServerModifiedTimeXsd() {
		if (!Api.isEmptyString(serverModifiedTime)) {
			try {
				return Api.toUtcXsd(serverModifiedTime);
			} catch (ParseException e) {
				Log.info(this.getClass(), e.toString(), e);
				return serverModifiedTime;
			}
		} else {
			return serverModifiedTime;
		}
	}
	
	@JsonProperty("serverCreatedTime")
	public String getServerCreatedTimeXsd() {
		if (!Api.isEmptyString(serverCreatedTime)) {
			try {
				return Api.toUtcXsd(serverCreatedTime);
			} catch (ParseException e) {
				Log.info(this.getClass(), e.toString(), e);
				return serverCreatedTime;
			}
		} else {
			return serverCreatedTime;
		}
	}

	
	public String getFilledByName() {
		return filledByName;
	}

	
	public void setFilledByName(String filledByName) {
		this.filledByName = filledByName;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	
	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public String getAssignToName() {
		return assignToName;
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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	
	public void setAssignToName(String assignToName) {
		this.assignToName = assignToName;
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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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
	public String getTzo() {
		return tzo;
	}

	@JsonIgnore
	public void setTzo(String tzo) {
		this.tzo = tzo;
	}

	@JsonIgnore
	public String getLocationAddress() {
		if (!Api.isEmptyString(locationAddress)) {
			return locationAddress;
		} else if (!Api.isEmptyString(latitude)
				&& !Api.isEmptyString(longitude)) {
			return latitude + "," + longitude;
		}
		return "";
	}

	@JsonIgnore
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@JsonIgnore
	public boolean isForJob() {
		return forJob;
	}

	@JsonIgnore
	public void setForJob(boolean forJob) {
		this.forJob = forJob;
	}

	@JsonIgnore
	public boolean isWorkFlowForm() {
		return workFlowForm;
	}

	@JsonIgnore
	public void setWorkFlowForm(boolean workFlowForm) {
		this.workFlowForm = workFlowForm;
	}




	public String getServerCreatedTime() {
		return serverCreatedTime;
	}

	public void setServerCreatedTime(String serverCreatedTime) {
		this.serverCreatedTime = serverCreatedTime;
	}

	
	public String getServerModifiedTime() {
		return serverModifiedTime;
	}

	public void setServerModifiedTime(String serverModifiedTime) {
		this.serverModifiedTime = serverModifiedTime;
	}

	
	public boolean isShowEdit() {
		return showEdit;
	}

	public void setShowEdit(boolean showEdit) {
		this.showEdit = showEdit;
	}

	public boolean isShowDelete() {
		return showDelete;
	}

	public void setShowDelete(boolean showDelete) {
		this.showDelete = showDelete;
	}

	public String toCSV() {
		return "[formId=" + formId + ", companyId=" + companyId
				+ ", formSpecId=" + formSpecId + ", formStatus=" + formStatus
				+ ", filledBy=" + filledBy + ", modifiedBy=" + modifiedBy
				+ ", clientFormId=" + clientFormId + ", type=" + type
				+ ", assignTo=" + assignTo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Form) {
			return getFormId() == ((Form) obj).getFormId();
		} else {
			return super.equals(obj);
		}
	}

	public String getFormTitle() {
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getPrintedOn() {
		return printedOn;
	}

	public void setPrintedOn(String printedOn) {
		this.printedOn = printedOn;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public boolean isCommited() {
		return commited;
	}

	public void setCommited(boolean commited) {
		this.commited = commited;
	}

	@JsonIgnore
	public String getFieldValue() {
		return fieldValue;
	}

	@JsonIgnore
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@JsonIgnore
	public String getDisplayValue() {
		return displayValue;
	}

	@JsonIgnore
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getFormSpecName() {
		return formSpecName;
	}

	public void setFormSpecName(String formSpecName) {
		this.formSpecName = formSpecName;
	}

	public String getFormFilledLocation() {
		return formFilledLocation;
	}

	public void setFormFilledLocation(String formFilledLocation) {
		this.formFilledLocation = formFilledLocation;
	}

	public Long getCheckInId() {
		return checkInId;
	}

	public void setCheckInId(Long checkInId) {
		this.checkInId = checkInId;
	}

	public Integer getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(Integer stockStatus) {
		this.stockStatus = stockStatus;
	}

	public Long getStockStatusModifiedBy() {
		return stockStatusModifiedBy;
	}

	public void setStockStatusModifiedBy(Long stockStatusModifiedBy) {
		this.stockStatusModifiedBy = stockStatusModifiedBy;
	}

	public boolean isForcePerformActivity() {
		return forcePerformActivity;
	}

	public void setForcePerformActivity(boolean forcePerformActivity) {
		this.forcePerformActivity = forcePerformActivity;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isPublicForm() {
		return publicForm;
	}

	public void setPublicForm(boolean publicForm) {
		this.publicForm = publicForm;
	}

	@JsonIgnore
	public String getClientCode() {
		return clientCode;
	}
	@JsonIgnore
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
	public boolean isFormProcessed() {
		return formProcessed;
	}

	public void setFormProcessed(boolean formProcessed) {
		this.formProcessed = formProcessed;
	}

	public Long getCustomEntityId() {
		return customEntityId;
	}

	public void setCustomEntityId(Long customEntityId) {
		this.customEntityId = customEntityId;
	}

	public Long getCustomEntityCheckInId() {
		return customEntityCheckInId;
	}

	public void setCustomEntityCheckInId(Long customEntityCheckInId) {
		this.customEntityCheckInId = customEntityCheckInId;
	}

	public long getWorkId() {
		return workId;
	}

	public void setWorkId(long workId) {
		this.workId = workId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getListUpdateStatus() {
		return listUpdateStatus;
	}

	public void setListUpdateStatus(int listUpdateStatus) {
		this.listUpdateStatus = listUpdateStatus;
	}

	public String getExternalFormSpecId() {
		return externalFormSpecId;
	}

	public void setExternalFormSpecId(String externalFormSpecId) {
		this.externalFormSpecId = externalFormSpecId;
	}

	public Map<String, Object> getTitleFieldsMap() {
		return titleFieldsMap;
	}

	public void setTitleFieldsMap(Map<String, Object> titleFieldsMap) {
		this.titleFieldsMap = titleFieldsMap;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public int getDraftForm() {
		return draftForm;
	}

	public void setDraftForm(int draftForm) {
		this.draftForm = draftForm;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public List<String> getFormDraftLocation() {
		return formDraftLocation;
	}

	public void setFormDraftLocation(List<String> formDraftLocation) {
		this.formDraftLocation = formDraftLocation;
	}

	public int getCustomEntityUpdateStatus() {
		return customEntityUpdateStatus;
	}

	public void setCustomEntityUpdateStatus(int customEntityUpdateStatus) {
		this.customEntityUpdateStatus = customEntityUpdateStatus;
	}
	public Integer getFormUpdateCount() {
		return formUpdateCount;
	}

	public void setFormUpdateCount(Integer formUpdateCount) {
		this.formUpdateCount = formUpdateCount;
	}

	public String getWorkActionSpecId() {
		return workActionSpecId;
	}

	public void setWorkActionSpecId(String workActionSpecId) {
		this.workActionSpecId = workActionSpecId;
	}

	public Long getTurnAroundTime() {
		return turnAroundTime;
	}

	public long getCustomerReqFormId() {
		return customerReqFormId;
	}

	public void setCustomerReqFormId(long customerReqFormId) {
		this.customerReqFormId = customerReqFormId;
	}

	public String getCustomerRemarks() {
		return customerRemarks;
	}

	public void setCustomerRemarks(String customerRemarks) {
		this.customerRemarks = customerRemarks;
	}

	public long getWorkReqFormId() {
		return workReqFormId;
	}

	public void setWorkReqFormId(long workReqFormId) {
		this.workReqFormId = workReqFormId;
	}

	public String getWorkRemarks() {
		return workRemarks;
	}

	public void setWorkRemarks(String workRemarks) {
		this.workRemarks = workRemarks;
	}

	public long getCustomEntityReqFormId() {
		return customEntityReqFormId;
	}

	public void setCustomEntityReqFormId(long customEntityReqFormId) {
		this.customEntityReqFormId = customEntityReqFormId;
	}

	public String getCustomEntityRemarks() {
		return customEntityRemarks;
	}

	public void setCustomEntityRemarks(String customEntityRemarks) {
		this.customEntityRemarks = customEntityRemarks;
	}

	public void setTurnAroundTime(Long turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}

	public Boolean getListUpdatedFromMobile() {
		return listUpdatedFromMobile;
	}

	public void setListUpdatedFromMobile(Boolean listUpdatedFromMobile) {
		this.listUpdatedFromMobile = listUpdatedFromMobile;
	}

	public int getCustomerProcessingResult() {
		return customerProcessingResult;
	}

	public void setCustomerProcessingResult(int customerProcessingResult) {
		this.customerProcessingResult = customerProcessingResult;
	}

	public int getWorkProcessingResult() {
		return workProcessingResult;
	}

	public void setWorkProcessingResult(int workProcessingResult) {
		this.workProcessingResult = workProcessingResult;
	}

	public int getCustomEntityProcessingResult() {
		return customEntityProcessingResult;
	}

	public void setCustomEntityProcessingResult(int customEntityProcessingResult) {
		this.customEntityProcessingResult = customEntityProcessingResult;
	}

	public String getWorkFlowFormStatus() {
		return workFlowFormStatus;
	}

	public void setWorkFlowFormStatus(String workFlowFormStatus) {
		this.workFlowFormStatus = workFlowFormStatus;
	}

	public Integer getSourceOfAction() {
		return sourceOfAction;
	}

	public void setSourceOfAction(Integer sourceOfAction) {
		this.sourceOfAction = sourceOfAction;
	}

	public Integer getSourceOfModification() {
		return sourceOfModification;
	}

	public void setSourceOfModification(Integer sourceOfModification) {
		this.sourceOfModification = sourceOfModification;
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

	public Integer getIsInGroup() {
		return isInGroup;
	}

	public void setIsInGroup(Integer isInGroup) {
		this.isInGroup = isInGroup;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getApprovalStatusColor() {
		return approvalStatusColor;
	}

	public void setApprovalStatusColor(String approvalStatusColor) {
		this.approvalStatusColor = approvalStatusColor;
	}

	public Long getDependentWorkId() {
		return dependentWorkId;
	}

	public void setDependentWorkId(Long dependentWorkId) {
		this.dependentWorkId = dependentWorkId;
	}

	public Integer getDependentType() {
		return dependentType;
	}

	public void setDependentType(Integer dependentType) {
		this.dependentType = dependentType;
	}
	
	public String getCreatedDisplayTime() {
		return createdDisplayTime;
	}

	public void setCreatedDisplayTime(String createdDisplayTime) {
		this.createdDisplayTime = createdDisplayTime;
	}

	public String getDataPushStatus() {
		return dataPushStatus;
	}

	public void setDataPushStatus(String dataPushStatus) {
		this.dataPushStatus = dataPushStatus;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getFormFieldsUniqueKey() {
		return formFieldsUniqueKey;
	}

	public void setFormFieldsUniqueKey(String formFieldsUniqueKey) {
		this.formFieldsUniqueKey = formFieldsUniqueKey;
	}
	
	
	
}
