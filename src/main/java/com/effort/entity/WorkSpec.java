package com.effort.entity;



import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;



public class WorkSpec implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	public static final int SOFT_DELETE = 1;
	public static final int HARD_DELETE = 2;
	
	private Long workSpecId;
	private String workSpecTitle;
	private String workSpecDescription;
	private String formSpecUniqueId;
	private Long createdBy;
	private Long modifiedBy;
	private int companyId;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String createdTime;
	private String modifiedTime;
	private Long copiedFrom=0l;
	private String copiedFromTitle;

	private int isSystemDefined=0;
	private int purpose=-1;
	@JsonProperty(access = Access.READ_ONLY)
	private long formSpecId;
	private Long skeletonWorkSpecId;
	private int productId;
	private String externalWorkSpecId;
	
	
	private Boolean deleted=false;
	
	private boolean importWork = true;
	
	private String mobileLayout;
	private boolean removeBlankLines;
	
	private String rejectionFormSpecUniqueId;
	private long rejectionFormSpecId;
	private String rejectionFormSpecTitle;
	private boolean createRejectionFormSpec = false;
	
	private boolean captureRejectionReasons = false;
	
	private boolean workSharing;
	
	private boolean enableWorkCheckIn;
	
	private boolean enableWorkOwnerToPerformActions;
	private boolean enableWorkCreatorToPerformActions;
	private boolean restrictHierarchyToPerformActions ;
	private boolean enableAssignActions;
	private long count =0;
	
	private Integer syncIncompletedWorksForMobile;
	
	private boolean canRejectWorkInvitation;
	
	private boolean canRejectWork;
	
	private boolean visibleOnlyManagerWorks = false;
	private boolean sendOnlyActionableWorksToManager = false;
	
	private List<Long> eligibleEmployeeIdsForWorkSync;
	
	private boolean globalSearch;
	
	private boolean hideOnEndTimeComplete = false;
	
	private boolean hideOnWorkComplete =false;
	
	private boolean deleteOnEndTimeComplete=false;
	private boolean cleanOnWorkComplete=false;
	
	private boolean canModifyWork = true; 
	
	
	private boolean cleanUpNotActionableWorks;
	private boolean openToCustomer;
	private boolean allowWorkCreationFromMobile;
	private boolean onlineWork = false;
	private boolean accessibleToEmp = false;
	
	private boolean enableEndTimeDurationCheck = false;
	private long workEndTimeDuration;
	private boolean enableAssigmentService = true;
	private boolean resendUnAssignedWorks = false;
	private Integer limitForWorkAssignMentService;
	private Integer durationForUnAssigment;
	private Integer maxWorksForAcceptence;
	
	private boolean workSpecPermission;
	private boolean viewPermission;
	private boolean addPermission;
	private boolean modifyPermission;
	private boolean deletePermission;
	
	private boolean downloadWorkMedias;
	
	private boolean blockedByOps;
	
	private boolean enableCheckInFormSubmission;
	private String checkInFormSpecUniqueId;
	
	private int mobileViewForActionDisplay;
	
	private Integer worksDeletionType;
	private boolean workProcessFlowRequired;
	private boolean workActionFlowMandatory;
	private boolean workTreeStructureEnabled;
	private boolean enableWorkClone;
	private boolean triggerPushNotification;
	private String notificationContent;
	
	private int addedWork;
	private int modifiedWork;
	private int deletedWork;
	private int workStatusCount;
	private int workStatusHistoryCount;
	private int workActionAssignmentsCount;
	private int workActionInvitationCount;
	private int forceComplete;
	
	private Long workStartsFieldSpecId;
	private Long workEndsFieldSpecId;
	private Long workEmployeeFieldSpecId;
	private Long workAddressFieldSpecId;
	private Long workCutomerFieldSpecId;
	private Map<Long, FormFieldSpec> skeletonFieldSpecIdAndFormFieldSpecMap;
	private Map<String, FormFieldSpec> uniqueIdAndSkeletonFieldSpecMap;

	private Map<Long, FormFieldSpec> fieldSpecIdAndFormFieldSpecMap;
	private String includeWorkInvitations;
	
	private  boolean autoComplete;
	private String autoCompleteFieldSpecUniqueId;
	private boolean enableWorkLocationCapture;
	private Long defaultWorkSortInMobile;
	private boolean enableSendVerificationToCustomerCheckInWork;
	private boolean enableRecurringCheckInWork;

	private Long helpDocumentMediaId;
	private String linkText;
	private String sampleFileDownloadUrl;
	
	private int includeDetailedReport;
	
	private int isAdvancedWorkPrintTemplate;
	
	private boolean canRejectWorkActionInvitations;
	private boolean sendWorksBasedOnStartTime;
	private boolean showOnlyActionableWorksForWeb;
	
	private boolean enableWebPrintOnMobile;
	private boolean displayApiLogs;
	private String empGroupIds;
	private boolean skipSystemAction;
	
	private boolean hiddenFieldsToDisplayInViewMode;
	private String createdByName;
	private long actionableWorksCount;
	
	private int enableMultiPrintTemplate;
	
	private boolean caseManagement;
	private boolean enableWorkInvitationAlarm;
	private String workInvitationAlarmTime;
	private boolean enableOnlineWorkInvitation;
	private String workInvitationDisplayTime;
	private boolean ignoreWorkFieldsPrefixOnApiCreation;
	private boolean mandateExternalId;
	private boolean considerOnlyLocationForAutoAllocateWorks;
	private boolean openWorksUniqueCheck;
	private boolean cleanUpWorks;
	private Long cleanUpWorksCriteria;
	
	private boolean enableOnlineSearchForUniqueness = false;
	private String errorMessageForUniqueness;
	private String successMessageForUniqueness;
	private boolean disableEmployeeAssignment;

	private Long defaultWorkStateTypeEntityIdForListView = -1l;
	
	private int processForUniqueTitles;
	private boolean sendInvitationBasedOnActiveInvitations;
	private int allowBulkActionPerform;
	private boolean unAssign;
	private Long customerId;
	private boolean sortWorkStatusHistory;
	private boolean enableSaveAndNew;
	private String workReportBuilderSpecId;
	private int workUniquenessType;
	private boolean listLevelVisibility;
	
	private boolean allowdefaultAddWorkNotification;
	private boolean allowdefaultModifyWorkNotification;
	private boolean allowdefaultWorkComplitionNotification;
	private boolean allowdefaultWorkRejectedNotification;
	private boolean allowdefaultWorkAllocationNotification;
	private boolean allowdefaultWorkInvitaionAcceptNotification;
	private boolean allowdefaultWorkInvitaionRejectionNotification;
	

	private boolean enableDefineLogic;
	
	private boolean branchLevelActionVisibility;
	
	private boolean overrideDMSValues; 

	private boolean actionLevelAssignment;
	
	private boolean performWorkAtWorkLocation;
	private Long workActivityGeoLocationDeviationAllowedRadius;
	private boolean forceWorkCheckInCheckOut;
	private boolean enforceWorkCheckIn;
	private int pendingfForMyApprovalCount;
	private int pendingForTeamApprovalCount;
	private int noOfPendingInvitaions;
	
	private boolean canSendWorkInvitation;
	private boolean enableCustomDashBoard;
	private boolean considerGroupBasedAllocationInWorkQueries;
	
	private int enableSelfieWhileCheckIn;
	private int enableSelfieWhileCheckOut;
	private boolean workActivityEntityCheckInRestriction;
	private String workActivityRestrictionFormFieldSpecUniqueId;
	

	
	@JsonIgnore
	public String getIncludeWorkInvitations() {
		return includeWorkInvitations;
	}
	@JsonIgnore
	public void setIncludeWorkInvitations(String includeWorkInvitations) {
		this.includeWorkInvitations = includeWorkInvitations;
	}
	@JsonIgnore
	public Map<Long, FormFieldSpec> getFieldSpecIdAndFormFieldSpecMap() {
		return fieldSpecIdAndFormFieldSpecMap;
	}
	@JsonIgnore
	public void setFieldSpecIdAndFormFieldSpecMap(Map<Long, FormFieldSpec> fieldSpecIdAndFormFieldSpecMap) {
		this.fieldSpecIdAndFormFieldSpecMap = fieldSpecIdAndFormFieldSpecMap;
	}
	
	@JsonIgnore
	public Map<Long, FormFieldSpec> getSkeletonFieldSpecIdAndFormFieldSpecMap() {
		return skeletonFieldSpecIdAndFormFieldSpecMap;
	}
	@JsonIgnore
	public void setSkeletonFieldSpecIdAndFormFieldSpecMap(Map<Long, FormFieldSpec> skeletonFieldSpecIdAndFormFieldSpecMap) {
		this.skeletonFieldSpecIdAndFormFieldSpecMap = skeletonFieldSpecIdAndFormFieldSpecMap;
	}
	
	@JsonIgnore
	public Map<String, FormFieldSpec> getUniqueIdAndSkeletonFieldSpecMap() {
		return uniqueIdAndSkeletonFieldSpecMap;
	}
	@JsonIgnore
	public void setUniqueIdAndSkeletonFieldSpecMap(Map<String, FormFieldSpec> uniqueIdAndSkeletonFieldSpecMap) {
		this.uniqueIdAndSkeletonFieldSpecMap = uniqueIdAndSkeletonFieldSpecMap;
	}
	
	@JsonIgnore
	public Long getWorkAddressFieldSpecId() {
		return workAddressFieldSpecId;
	}
	@JsonIgnore
	public void setWorkAddressFieldSpecId(Long workAddressFieldSpecId) {
		this.workAddressFieldSpecId = workAddressFieldSpecId;
	}
	@JsonIgnore
	public Long getWorkCutomerFieldSpecId() {
		return workCutomerFieldSpecId;
	}
	@JsonIgnore
	public void setWorkCutomerFieldSpecId(Long workCutomerFieldSpecId) {
		this.workCutomerFieldSpecId = workCutomerFieldSpecId;
	}
	@JsonIgnore
	public Long getWorkStartsFieldSpecId() {
		return workStartsFieldSpecId;
	}
	@JsonIgnore
	public void setWorkStartsFieldSpecId(Long workStartsFieldSpecId) {
		this.workStartsFieldSpecId = workStartsFieldSpecId;
	}
	@JsonIgnore
	public Long getWorkEndsFieldSpecId() {
		return workEndsFieldSpecId;
	}
	@JsonIgnore
	public void setWorkEndsFieldSpecId(Long workEndsFieldSpecId) {
		this.workEndsFieldSpecId = workEndsFieldSpecId;
	}
	@JsonIgnore
	public Long getWorkEmployeeFieldSpecId() {
		return workEmployeeFieldSpecId;
	}
	@JsonIgnore
	public void setWorkEmployeeFieldSpecId(Long workEmployeeFieldSpecId) {
		this.workEmployeeFieldSpecId = workEmployeeFieldSpecId;
	}
	
	public Integer getWorksDeletionType() {
		return worksDeletionType;
	}
	public void setWorksDeletionType(Integer worksDeletionType) {
		this.worksDeletionType = worksDeletionType;
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
	public boolean isEnableEndTimeDurationCheck() {
		return enableEndTimeDurationCheck;
	}
	public void setEnableEndTimeDurationCheck(boolean enableEndTimeDurationCheck) {
		this.enableEndTimeDurationCheck = enableEndTimeDurationCheck;
	}
	public long getWorkEndTimeDuration() {
		return workEndTimeDuration;
	}
	public void setWorkEndTimeDuration(long workEndTimeDuration) {
		this.workEndTimeDuration = workEndTimeDuration;
	}
	public boolean isAllowWorkCreationFromMobile() {
		return allowWorkCreationFromMobile;
	}
	public void setAllowWorkCreationFromMobile(boolean allowWorkCreationFromMobile) {
		this.allowWorkCreationFromMobile = allowWorkCreationFromMobile;
	}
	public Long getWorkSpecId() {
		return workSpecId;
	}
	public void setWorkSpecId(Long workSpecId) {
		this.workSpecId = workSpecId;
	}
	public String getWorkSpecTitle() {
		return workSpecTitle;
	}
	public void setWorkSpecTitle(String workSpecTitle) {
		this.workSpecTitle = workSpecTitle;
	}
	public String getWorkSpecDescription() {
		return workSpecDescription;
	}
	public void setWorkSpecDescription(String workSpecDescription) {
		this.workSpecDescription = workSpecDescription;
	}
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
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
	
	public int getIsSystemDefined() {
		return isSystemDefined;
	}
	public void setIsSystemDefined(int isSystemDefined) {
		this.isSystemDefined = isSystemDefined;
	}
	public int getPurpose() {
		return purpose;
	}
	public void setPurpose(int purpose) {
		this.purpose = purpose;
	}
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Long getCopiedFrom() {
		return copiedFrom;
	}
	public void setCopiedFrom(Long copiedFrom) {
		this.copiedFrom = copiedFrom;
	}
	
	public String getCopiedFromTitle() {
		return copiedFromTitle;
	}
	public void setCopiedFromTitle(String copiedFromTitle) {
		this.copiedFromTitle = copiedFromTitle;
	}

	
	public String getCreatedDate() {
		return getCreatedTime().substring(0, getCreatedTime().indexOf(" "));
	}
	
	public long getFormSpecId() {
		return formSpecId;
	}
	
	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public Long getSkeletonWorkSpecId() {
		return skeletonWorkSpecId;
	}
	public void setSkeletonWorkSpecId(Long skeletonWorkSpecId) {
		this.skeletonWorkSpecId = skeletonWorkSpecId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getExternalWorkSpecId() {
		return externalWorkSpecId;
	}
	public void setExternalWorkSpecId(String externalWorkSpecId) {
		this.externalWorkSpecId = externalWorkSpecId;
	}
	public boolean isImportWork() {
		return importWork;
	}
	public void setImportWork(boolean importWork) {
		this.importWork = importWork;
	}
	public String getMobileLayout() {
		return mobileLayout;
	}
	public void setMobileLayout(String mobileLayout) {
		this.mobileLayout = mobileLayout;
	}
	public boolean isRemoveBlankLines() {
		return removeBlankLines;
	}
	public void setRemoveBlankLines(boolean removeBlankLines) {
		this.removeBlankLines = removeBlankLines;
	}
	public String getRejectionFormSpecUniqueId() {
		return rejectionFormSpecUniqueId;
	}
	public void setRejectionFormSpecUniqueId(String rejectionFormSpecUniqueId) {
		this.rejectionFormSpecUniqueId = rejectionFormSpecUniqueId;
	}
	public long getRejectionFormSpecId() {
		return rejectionFormSpecId;
	}
	public void setRejectionFormSpecId(long rejectionFormSpecId) {
		this.rejectionFormSpecId = rejectionFormSpecId;
	}
	public boolean isCreateRejectionFormSpec() {
		return createRejectionFormSpec;
	}
	public void setCreateRejectionFormSpec(boolean createRejectionFormSpec) {
		this.createRejectionFormSpec = createRejectionFormSpec;
	}
	public boolean isCaptureRejectionReasons() {
		return captureRejectionReasons;
	}
	public void setCaptureRejectionReasons(boolean captureRejectionReasons) {
		this.captureRejectionReasons = captureRejectionReasons;
	}
	public String getRejectionFormSpecTitle() {
		return rejectionFormSpecTitle;
	}
	public void setRejectionFormSpecTitle(String rejectionFormSpecTitle) {
		this.rejectionFormSpecTitle = rejectionFormSpecTitle;
	}
	public boolean isWorkSharing() {
		return workSharing;
	}
	public void setWorkSharing(boolean workSharing) {
		this.workSharing = workSharing;
	}
	public boolean isEnableWorkCheckIn() {
		return enableWorkCheckIn;
	}
	public void setEnableWorkCheckIn(boolean enableWorkCheckIn) {
		this.enableWorkCheckIn = enableWorkCheckIn;
	}
	public boolean isEnableWorkOwnerToPerformActions() {
		return enableWorkOwnerToPerformActions;
	}
	public void setEnableWorkOwnerToPerformActions(
			boolean enableWorkOwnerToPerformActions) {
		this.enableWorkOwnerToPerformActions = enableWorkOwnerToPerformActions;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public Integer getSyncIncompletedWorksForMobile() {
		return syncIncompletedWorksForMobile;
	}
	public void setSyncIncompletedWorksForMobile(Integer syncIncompletedWorksForMobile) {
		this.syncIncompletedWorksForMobile = syncIncompletedWorksForMobile;
	}
	public boolean isCanRejectWorkInvitation() {
		return canRejectWorkInvitation;
	}
	public void setCanRejectWorkInvitation(boolean canRejectWorkInvitation) {
		this.canRejectWorkInvitation = canRejectWorkInvitation;
	}
	public boolean isEnableAssignActions() {
		return enableAssignActions;
	}
	public void setEnableAssignActions(boolean enableAssignActions) {
		this.enableAssignActions = enableAssignActions;
	}
	public boolean isCanRejectWork() {
		return canRejectWork;
	}
	public void setCanRejectWork(boolean canRejectWork) {
		this.canRejectWork = canRejectWork;
	}
	public boolean isVisibleOnlyManagerWorks() {
		return visibleOnlyManagerWorks;
	}
	public void setVisibleOnlyManagerWorks(boolean visibleOnlyManagerWorks) {
		this.visibleOnlyManagerWorks = visibleOnlyManagerWorks;
	}
	public List<Long> getEligibleEmployeeIdsForWorkSync() {
		return eligibleEmployeeIdsForWorkSync;
	}
	public void setEligibleEmployeeIdsForWorkSync(
			List<Long> eligibleEmployeeIdsForWorkSync) {
		this.eligibleEmployeeIdsForWorkSync = eligibleEmployeeIdsForWorkSync;
	}
	public boolean isGlobalSearch() {
		return globalSearch;
	}
	public void setGlobalSearch(boolean globalSearch) {
		this.globalSearch = globalSearch;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WorkSpec) {
			return getWorkSpecId().equals(((WorkSpec) obj).getWorkSpecId());
		} else {
			return super.equals(obj);
		}
	}
	public boolean isHideOnEndTimeComplete() {
		return hideOnEndTimeComplete;
	}
	public void setHideOnEndTimeComplete(boolean hideOnEndTimeComplete) {
		this.hideOnEndTimeComplete = hideOnEndTimeComplete;
	}
	public boolean isHideOnWorkComplete() {
		return hideOnWorkComplete;
	}
	public void setHideOnWorkComplete(boolean hideOnWorkComplete) {
		this.hideOnWorkComplete = hideOnWorkComplete;
	}
	public boolean isEnableWorkCreatorToPerformActions() {
		return enableWorkCreatorToPerformActions;
	}
	public void setEnableWorkCreatorToPerformActions(boolean enableWorkCreatorToPerformActions) {
		this.enableWorkCreatorToPerformActions = enableWorkCreatorToPerformActions;
	}
	public boolean isCanModifyWork() {
		return canModifyWork;
	}
	public void setCanModifyWork(boolean canModifyWork) {
		this.canModifyWork = canModifyWork;
	}
	public boolean isDeleteOnEndTimeComplete() {
		return deleteOnEndTimeComplete;
	}
	public void setDeleteOnEndTimeComplete(boolean deleteOnEndTimeComplete) {
		this.deleteOnEndTimeComplete = deleteOnEndTimeComplete;
	}
	public boolean isCleanOnWorkComplete() {
		return cleanOnWorkComplete;
	}
	public void setCleanOnWorkComplete(boolean cleanOnWorkComplete) {
		this.cleanOnWorkComplete = cleanOnWorkComplete;
	}
	public boolean isCleanUpNotActionableWorks() {
		return cleanUpNotActionableWorks;
	}
	public void setCleanUpNotActionableWorks(boolean cleanUpNotActionableWorks) {
		this.cleanUpNotActionableWorks = cleanUpNotActionableWorks;
	}
	public boolean isRestrictHierarchyToPerformActions() {
		return restrictHierarchyToPerformActions;
	}
	public void setRestrictHierarchyToPerformActions(
			boolean restrictHierarchyToPerformActions) {
		this.restrictHierarchyToPerformActions = restrictHierarchyToPerformActions;
	}
	public boolean isOpenToCustomer() {
		return openToCustomer;
	}
	public void setOpenToCustomer(boolean openToCustomer) {
		this.openToCustomer = openToCustomer;
	}
	public boolean isSendOnlyActionableWorksToManager() {
		return sendOnlyActionableWorksToManager;
	}
	public void setSendOnlyActionableWorksToManager(
			boolean sendOnlyActionableWorksToManager) {
		this.sendOnlyActionableWorksToManager = sendOnlyActionableWorksToManager;
	}
	public boolean isOnlineWork() {
		return onlineWork;
	}
	public void setOnlineWork(boolean onlineWork) {
		this.onlineWork = onlineWork;
	}
	public boolean isAccessibleToEmp() {
		return accessibleToEmp;
	}
	public void setAccessibleToEmp(boolean accessibleToEmp) {
		this.accessibleToEmp = accessibleToEmp;
	}
	public boolean isWorkSpecPermission() {
		return workSpecPermission;
	}
	public void setWorkSpecPermission(boolean workSpecPermission) {
		this.workSpecPermission = workSpecPermission;
	}
	
	public boolean isEnableAssigmentService() {
		return enableAssigmentService;
	}
	public void setEnableAssigmentService(boolean enableAssigmentService) {
		this.enableAssigmentService = enableAssigmentService;
	}
	public boolean isResendUnAssignedWorks() {
		return resendUnAssignedWorks;
	}
	public void setResendUnAssignedWorks(boolean resendUnAssignedWorks) {
		this.resendUnAssignedWorks = resendUnAssignedWorks;
	}
	public Integer getLimitForWorkAssignMentService() {
		return limitForWorkAssignMentService;
	}
	public void setLimitForWorkAssignMentService(Integer limitForWorkAssignMentService) {
		this.limitForWorkAssignMentService = limitForWorkAssignMentService;
	}
	public Integer getDurationForUnAssigment() {
		return durationForUnAssigment;
	}
	public void setDurationForUnAssigment(Integer durationForUnAssigment) {
		this.durationForUnAssigment = durationForUnAssigment;
	}
	public Integer getMaxWorksForAcceptence() {
		return maxWorksForAcceptence;
	}
	public void setMaxWorksForAcceptence(Integer maxWorksForAcceptence) {
		this.maxWorksForAcceptence = maxWorksForAcceptence;
	}
	public boolean isDownloadWorkMedias() {
		return downloadWorkMedias;
	}
	public void setDownloadWorkMedias(boolean downloadWorkMedias) {
		this.downloadWorkMedias = downloadWorkMedias;
	}
	public boolean isBlockedByOps() {
		return blockedByOps;
	}
	public void setBlockedByOps(boolean blockedByOps) {
		this.blockedByOps = blockedByOps;
	}
	
	public String getCheckInFormSpecUniqueId() {
		return checkInFormSpecUniqueId;
	}
	public void setCheckInFormSpecUniqueId(String checkInFormSpecUniqueId) {
		this.checkInFormSpecUniqueId = checkInFormSpecUniqueId;
	}
	public boolean isEnableCheckInFormSubmission() {
		return enableCheckInFormSubmission;
	}
	public void setEnableCheckInFormSubmission(boolean enableCheckInFormSubmission) {
		this.enableCheckInFormSubmission = enableCheckInFormSubmission;
	}
	public int getMobileViewForActionDisplay() {
		return mobileViewForActionDisplay;
	}
	public void setMobileViewForActionDisplay(int mobileViewForActionDisplay) {
		this.mobileViewForActionDisplay = mobileViewForActionDisplay;
	}

	public boolean isAutoComplete() {
		return autoComplete;
	}
	public void setAutoComplete(boolean autoComplete) {
		this.autoComplete = autoComplete;
	}
	public String getAutoCompleteFieldSpecUniqueId() {
		return autoCompleteFieldSpecUniqueId;
	}
	public void setAutoCompleteFieldSpecUniqueId(String autoCompleteFieldSpecUniqueId) {
		this.autoCompleteFieldSpecUniqueId = autoCompleteFieldSpecUniqueId;
	}
	
	public int getAddedWork() {
		return addedWork;
	}
	public void setAddedWork(int addedWork) {
		this.addedWork = addedWork;
	}
	public int getModifiedWork() {
		return modifiedWork;
	}
	public void setModifiedWork(int modifiedWork) {
		this.modifiedWork = modifiedWork;
	}
	public int getDeletedWork() {
		return deletedWork;
	}
	public void setDeletedWork(int deletedWork) {
		this.deletedWork = deletedWork;
	}
	public int getWorkStatusCount() {
		return workStatusCount;
	}
	public void setWorkStatusCount(int workStatusCount) {
		this.workStatusCount = workStatusCount;
	}
	public int getWorkStatusHistoryCount() {
		return workStatusHistoryCount;
	}
	public void setWorkStatusHistoryCount(int workStatusHistoryCount) {
		this.workStatusHistoryCount = workStatusHistoryCount;
	}
	public int getWorkActionAssignmentsCount() {
		return workActionAssignmentsCount;
	}
	public void setWorkActionAssignmentsCount(int workActionAssignmentsCount) {
		this.workActionAssignmentsCount = workActionAssignmentsCount;
	}		
	public boolean isWorkProcessFlowRequired() {
		return workProcessFlowRequired;
	}
	public void setWorkProcessFlowRequired(boolean workProcessFlowRequired) {
		this.workProcessFlowRequired = workProcessFlowRequired;
	}
	public boolean isWorkActionFlowMandatory() {
		return workActionFlowMandatory;
	}
	public void setWorkActionFlowMandatory(boolean workActionFlowMandatory) {
		this.workActionFlowMandatory = workActionFlowMandatory;
	}
	public boolean isWorkTreeStructureEnabled() {
		return workTreeStructureEnabled;
	}
	public void setWorkTreeStructureEnabled(boolean workTreeStructureEnabled) {
		this.workTreeStructureEnabled = workTreeStructureEnabled;
	}
	
	public boolean isEnableWorkClone() {
		return enableWorkClone;
	}
	public void setEnableWorkClone(boolean enableWorkClone) {
		this.enableWorkClone = enableWorkClone;
	}
	
	public boolean isTriggerPushNotification() {
		return triggerPushNotification;
	}
	public void setTriggerPushNotification(boolean triggerPushNotification) {
		this.triggerPushNotification = triggerPushNotification;
	}
	public String getNotificationContent() {
		return notificationContent;
	}
	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}
	public int getWorkActionInvitationCount() {
		return workActionInvitationCount;
	}
	public void setWorkActionInvitationCount(int workActionInvitationCount) {
		this.workActionInvitationCount = workActionInvitationCount;
	}
	public boolean isEnableWorkLocationCapture() {
		return enableWorkLocationCapture;
	}
	public void setEnableWorkLocationCapture(boolean enableWorkLocationCapture) {
		this.enableWorkLocationCapture = enableWorkLocationCapture;
	}
	public Long getDefaultWorkSortInMobile() {
		return defaultWorkSortInMobile;
	}
	public void setDefaultWorkSortInMobile(Long defaultWorkSortInMobile) {
		this.defaultWorkSortInMobile = defaultWorkSortInMobile;
	}
	public Long getHelpDocumentMediaId() {
		return helpDocumentMediaId;
	}
	public void setHelpDocumentMediaId(Long helpDocumentMediaId) {
		this.helpDocumentMediaId = helpDocumentMediaId;
	}
	public String getLinkText() {
		return linkText;
	}
	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}
	public String getSampleFileDownloadUrl() {
		return sampleFileDownloadUrl;
	}
	public void setSampleFileDownloadUrl(String sampleFileDownloadUrl) {
		this.sampleFileDownloadUrl = sampleFileDownloadUrl;
	}
	public int getIncludeDetailedReport() {
		return includeDetailedReport;
	}
	public void setIncludeDetailedReport(int includeDetailedReport) {
		this.includeDetailedReport = includeDetailedReport;
	}
	public int getIsAdvancedWorkPrintTemplate() {
		return isAdvancedWorkPrintTemplate;
	}
	public void setIsAdvancedWorkPrintTemplate(int isAdvancedWorkPrintTemplate) {
		this.isAdvancedWorkPrintTemplate = isAdvancedWorkPrintTemplate;
	}
	 
	public boolean isEnableSendVerificationToCustomerCheckInWork() {
		return enableSendVerificationToCustomerCheckInWork;
	}
	public void setEnableSendVerificationToCustomerCheckInWork(boolean enableSendVerificationToCustomerCheckInWork) {
		this.enableSendVerificationToCustomerCheckInWork = enableSendVerificationToCustomerCheckInWork;
	}
	public boolean isEnableRecurringCheckInWork() {
		return enableRecurringCheckInWork;
	}
	public void setEnableRecurringCheckInWork(boolean enableRecurringCheckInWork) {
		this.enableRecurringCheckInWork = enableRecurringCheckInWork;

	}
	public boolean isCanRejectWorkActionInvitations() {
		return canRejectWorkActionInvitations;
	}
	public void setCanRejectWorkActionInvitations(boolean canRejectWorkActionInvitations) {
		this.canRejectWorkActionInvitations = canRejectWorkActionInvitations;
	}
	public boolean isSendWorksBasedOnStartTime() {
		return sendWorksBasedOnStartTime;
	}
	public void setSendWorksBasedOnStartTime(boolean sendWorksBasedOnStartTime) {
		this.sendWorksBasedOnStartTime = sendWorksBasedOnStartTime;
	}
	public boolean isShowOnlyActionableWorksForWeb() {
		return showOnlyActionableWorksForWeb;
	}
	public void setShowOnlyActionableWorksForWeb(boolean showOnlyActionableWorksForWeb) {
		this.showOnlyActionableWorksForWeb = showOnlyActionableWorksForWeb;
	}
	
	public boolean isEnableWebPrintOnMobile() {
		return enableWebPrintOnMobile;
	}
	public void setEnableWebPrintOnMobile(boolean enableWebPrintOnMobile) {
		this.enableWebPrintOnMobile = enableWebPrintOnMobile;
	}
	public boolean isDisplayApiLogs() {
		return displayApiLogs;
	}
	public void setDisplayApiLogs(boolean displayApiLogs) {
		this.displayApiLogs = displayApiLogs;
	}
	public String getEmpGroupIds() {
		return empGroupIds;
	}
	public void setEmpGroupIds(String empGroupIds) {
		this.empGroupIds = empGroupIds;
	}
	public boolean isSkipSystemAction() {
		return skipSystemAction;
	}
	public void setSkipSystemAction(boolean skipSystemAction) {
		this.skipSystemAction = skipSystemAction;
	}
	public boolean isHiddenFieldsToDisplayInViewMode() {
		return hiddenFieldsToDisplayInViewMode;
	}
	public void setHiddenFieldsToDisplayInViewMode(boolean hiddenFieldsToDisplayInViewMode) {
		this.hiddenFieldsToDisplayInViewMode = hiddenFieldsToDisplayInViewMode;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public long getActionableWorksCount() {
		return actionableWorksCount;
	}
	public void setActionableWorksCount(long actionableWorksCount) {
		this.actionableWorksCount = actionableWorksCount;
	}
	
	public int getEnableMultiPrintTemplate() {
		return enableMultiPrintTemplate;
	}
	public void setEnableMultiPrintTemplate(int enableMultiPrintTemplate) {
		this.enableMultiPrintTemplate = enableMultiPrintTemplate;
	}
	public boolean isCaseManagement() {
		return caseManagement;
	}
	public void setCaseManagement(boolean caseManagement) {
		this.caseManagement = caseManagement;
	}
	public boolean isEnableWorkInvitationAlarm() {
		return enableWorkInvitationAlarm;
	}
	public void setEnableWorkInvitationAlarm(boolean enableWorkInvitationAlarm) {
		this.enableWorkInvitationAlarm = enableWorkInvitationAlarm;
	}
	public String getWorkInvitationAlarmTime() {
		return workInvitationAlarmTime;
	}
	public void setWorkInvitationAlarmTime(String workInvitationAlarmTime) {
		this.workInvitationAlarmTime = workInvitationAlarmTime;
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
	public boolean isIgnoreWorkFieldsPrefixOnApiCreation() {
		return ignoreWorkFieldsPrefixOnApiCreation;
	}
	public void setIgnoreWorkFieldsPrefixOnApiCreation(boolean ignoreWorkFieldsPrefixOnApiCreation) {
		this.ignoreWorkFieldsPrefixOnApiCreation = ignoreWorkFieldsPrefixOnApiCreation;
	}
	public boolean isMandateExternalId() {
		return mandateExternalId;
	}
	public void setMandateExternalId(boolean mandateExternalId) {
		this.mandateExternalId = mandateExternalId;
	}
	public boolean isConsiderOnlyLocationForAutoAllocateWorks() {
		return considerOnlyLocationForAutoAllocateWorks;
	}
	public void setConsiderOnlyLocationForAutoAllocateWorks(boolean considerOnlyLocationForAutoAllocateWorks) {
		this.considerOnlyLocationForAutoAllocateWorks = considerOnlyLocationForAutoAllocateWorks;
	}
	public Long getDefaultWorkStateTypeEntityIdForListView() {
		return defaultWorkStateTypeEntityIdForListView;
	}
	public void setDefaultWorkStateTypeEntityIdForListView(Long defaultWorkStateTypeEntityIdForListView) {
		this.defaultWorkStateTypeEntityIdForListView = defaultWorkStateTypeEntityIdForListView;
	}
	public boolean isOpenWorksUniqueCheck() {
		return openWorksUniqueCheck;
	}
	public void setOpenWorksUniqueCheck(boolean openWorksUniqueCheck) {
		this.openWorksUniqueCheck = openWorksUniqueCheck;
	}
	public int getProcessForUniqueTitles() {
		return processForUniqueTitles;
	}
	public void setProcessForUniqueTitles(int processForUniqueTitles) {
		this.processForUniqueTitles = processForUniqueTitles;
	}
	public boolean isCleanUpWorks() {
		return cleanUpWorks;
	}
	public void setCleanUpWorks(boolean cleanUpWorks) {
		this.cleanUpWorks = cleanUpWorks;
	}
	public Long getCleanUpWorksCriteria() {
		return cleanUpWorksCriteria;
	}
	public void setCleanUpWorksCriteria(Long cleanUpWorksCriteria) {
		this.cleanUpWorksCriteria = cleanUpWorksCriteria;
	}
	
	public boolean isEnableOnlineSearchForUniqueness() {
		return enableOnlineSearchForUniqueness;
	}
	public void setEnableOnlineSearchForUniqueness(boolean enableOnlineSearchForUniqueness) {
		this.enableOnlineSearchForUniqueness = enableOnlineSearchForUniqueness;
	}
	public String getErrorMessageForUniqueness() {
		return errorMessageForUniqueness;
	}
	public void setErrorMessageForUniqueness(String errorMessageForUniqueness) {
		this.errorMessageForUniqueness = errorMessageForUniqueness;
	}
	public String getSuccessMessageForUniqueness() {
		return successMessageForUniqueness;
	}
	public void setSuccessMessageForUniqueness(String successMessageForUniqueness) {
		this.successMessageForUniqueness = successMessageForUniqueness;
	}
	
	public boolean isSendInvitationBasedOnActiveInvitations() {
		return sendInvitationBasedOnActiveInvitations;
	}
	public void setSendInvitationBasedOnActiveInvitations(boolean sendInvitationBasedOnActiveInvitations) {
		this.sendInvitationBasedOnActiveInvitations = sendInvitationBasedOnActiveInvitations;
	}
	public int getForceComplete() {
		return forceComplete;
	}
	public void setForceComplete(int forceComplete) {
		this.forceComplete = forceComplete;
	}
	public boolean isDisableEmployeeAssignment() {
		return disableEmployeeAssignment;
	}
	public void setDisableEmployeeAssignment(boolean disableEmployeeAssignment) {
		this.disableEmployeeAssignment = disableEmployeeAssignment;
	}
	public int getAllowBulkActionPerform() {
		return allowBulkActionPerform;
	}
	public void setAllowBulkActionPerform(int allowBulkActionPerform) {
		this.allowBulkActionPerform = allowBulkActionPerform;
	}
	public boolean isUnAssign() {
		return unAssign;
	}
	public void setUnAssign(boolean unAssign) {
		this.unAssign = unAssign;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public boolean isSortWorkStatusHistory() {
		return sortWorkStatusHistory;
	}
	public void setSortWorkStatusHistory(boolean sortWorkStatusHistory) {
		this.sortWorkStatusHistory = sortWorkStatusHistory;
	}
	public boolean isEnableSaveAndNew() {
		return enableSaveAndNew;
	}
	public void setEnableSaveAndNew(boolean enableSaveAndNew) {
		this.enableSaveAndNew = enableSaveAndNew;
	}
	public String getWorkReportBuilderSpecId() {
		return workReportBuilderSpecId;
	}
	public void setWorkReportBuilderSpecId(String workReportBuilderSpecId) {
		this.workReportBuilderSpecId = workReportBuilderSpecId;
	}
	public int getWorkUniquenessType() {
		return workUniquenessType;
	}
	public void setWorkUniquenessType(int workUniquenessType) {
		this.workUniquenessType = workUniquenessType;
	}
	public boolean isListLevelVisibility() {
		return listLevelVisibility;
	}
	public void setListLevelVisibility(boolean listLevelVisibility) {
		this.listLevelVisibility = listLevelVisibility;
	}

	public boolean isAllowdefaultAddWorkNotification() {
		return allowdefaultAddWorkNotification;
	}
	public void setAllowdefaultAddWorkNotification(boolean allowdefaultAddWorkNotification) {
		this.allowdefaultAddWorkNotification = allowdefaultAddWorkNotification;
	}
	public boolean isAllowdefaultModifyWorkNotification() {
		return allowdefaultModifyWorkNotification;
	}
	public void setAllowdefaultModifyWorkNotification(boolean allowdefaultModifyWorkNotification) {
		this.allowdefaultModifyWorkNotification = allowdefaultModifyWorkNotification;
	}
	public boolean isAllowdefaultWorkComplitionNotification() {
		return allowdefaultWorkComplitionNotification;
	}
	public void setAllowdefaultWorkComplitionNotification(boolean allowdefaultWorkComplitionNotification) {
		this.allowdefaultWorkComplitionNotification = allowdefaultWorkComplitionNotification;
	}
	public boolean isAllowdefaultWorkRejectedNotification() {
		return allowdefaultWorkRejectedNotification;
	}
	public void setAllowdefaultWorkRejectedNotification(boolean allowdefaultWorkRejectedNotification) {
		this.allowdefaultWorkRejectedNotification = allowdefaultWorkRejectedNotification;
	}
	public boolean isAllowdefaultWorkAllocationNotification() {
		return allowdefaultWorkAllocationNotification;
	}
	public void setAllowdefaultWorkAllocationNotification(boolean allowdefaultWorkAllocationNotification) {
		this.allowdefaultWorkAllocationNotification = allowdefaultWorkAllocationNotification;
	}
	public boolean isAllowdefaultWorkInvitaionAcceptNotification() {
		return allowdefaultWorkInvitaionAcceptNotification;
	}
	public void setAllowdefaultWorkInvitaionAcceptNotification(boolean allowdefaultWorkInvitaionAcceptNotification) {
		this.allowdefaultWorkInvitaionAcceptNotification = allowdefaultWorkInvitaionAcceptNotification;
	}
	public boolean isAllowdefaultWorkInvitaionRejectionNotification() {
		return allowdefaultWorkInvitaionRejectionNotification;
	}
	public void setAllowdefaultWorkInvitaionRejectionNotification(boolean allowdefaultWorkInvitaionRejectionNotification) {
		this.allowdefaultWorkInvitaionRejectionNotification = allowdefaultWorkInvitaionRejectionNotification;
	}
	

	public boolean isEnableDefineLogic() {
		return enableDefineLogic;
	}
	public void setEnableDefineLogic(boolean enableDefineLogic) {
		this.enableDefineLogic = enableDefineLogic;
	}
	public boolean isBranchLevelActionVisibility() {
		return branchLevelActionVisibility;
	}
	public void setBranchLevelActionVisibility(boolean branchLevelActionVisibility) {
		this.branchLevelActionVisibility = branchLevelActionVisibility;
	}
	public boolean isOverrideDMSValues() {
		return overrideDMSValues;
	}
	public void setOverrideDMSValues(boolean overrideDMSValues) {
		this.overrideDMSValues = overrideDMSValues;
	}
	public boolean isActionLevelAssignment() {
		return actionLevelAssignment;
	}
	public void setActionLevelAssignment(boolean actionLevelAssignment) {
		this.actionLevelAssignment = actionLevelAssignment;
	}
	public boolean isPerformWorkAtWorkLocation() {
		return performWorkAtWorkLocation;
	}
	public void setPerformWorkAtWorkLocation(boolean performWorkAtWorkLocation) {
		this.performWorkAtWorkLocation = performWorkAtWorkLocation;
	}
	public Long getWorkActivityGeoLocationDeviationAllowedRadius() {
		return workActivityGeoLocationDeviationAllowedRadius;
	}
	public void setWorkActivityGeoLocationDeviationAllowedRadius(Long workActivityGeoLocationDeviationAllowedRadius) {
		this.workActivityGeoLocationDeviationAllowedRadius = workActivityGeoLocationDeviationAllowedRadius;
	}
	public boolean isForceWorkCheckInCheckOut() {
		return forceWorkCheckInCheckOut;
	}
	public void setForceWorkCheckInCheckOut(boolean forceWorkCheckInCheckOut) {
		this.forceWorkCheckInCheckOut = forceWorkCheckInCheckOut;
	}
	public boolean isEnforceWorkCheckIn() {
		return enforceWorkCheckIn;
	}
	public void setEnforceWorkCheckIn(boolean enforceWorkCheckIn) {
		this.enforceWorkCheckIn = enforceWorkCheckIn;
	}
	public int getPendingfForMyApprovalCount() {
		return pendingfForMyApprovalCount;
	}
	public void setPendingfForMyApprovalCount(int pendingfForMyApprovalCount) {
		this.pendingfForMyApprovalCount = pendingfForMyApprovalCount;
	}
	public int getPendingForTeamApprovalCount() {
		return pendingForTeamApprovalCount;
	}
	public void setPendingForTeamApprovalCount(int pendingForTeamApprovalCount) {
		this.pendingForTeamApprovalCount = pendingForTeamApprovalCount;
	}
	public int getNoOfPendingInvitaions() {
		return noOfPendingInvitaions;
	}
	public void setNoOfPendingInvitaions(int noOfPendingInvitaions) {
		this.noOfPendingInvitaions = noOfPendingInvitaions;
	}
	public boolean isCanSendWorkInvitation() {
		return canSendWorkInvitation;
	}
	public void setCanSendWorkInvitation(boolean canSendWorkInvitation) {
		this.canSendWorkInvitation = canSendWorkInvitation;
	}
	public boolean isEnableCustomDashBoard() {
		return enableCustomDashBoard;
	}
	public void setEnableCustomDashBoard(boolean enableCustomDashBoard) {
		this.enableCustomDashBoard = enableCustomDashBoard;
	}
	public boolean isConsiderGroupBasedAllocationInWorkQueries() {
		return considerGroupBasedAllocationInWorkQueries;
	}
	public void setConsiderGroupBasedAllocationInWorkQueries(boolean considerGroupBasedAllocationInWorkQueries) {
		this.considerGroupBasedAllocationInWorkQueries = considerGroupBasedAllocationInWorkQueries;
	}
	public int getEnableSelfieWhileCheckIn() {
		return enableSelfieWhileCheckIn;
	}
	public void setEnableSelfieWhileCheckIn(int enableSelfieWhileCheckIn) {
		this.enableSelfieWhileCheckIn = enableSelfieWhileCheckIn;
	}
	public int getEnableSelfieWhileCheckOut() {
		return enableSelfieWhileCheckOut;
	}
	public void setEnableSelfieWhileCheckOut(int enableSelfieWhileCheckOut) {
		this.enableSelfieWhileCheckOut = enableSelfieWhileCheckOut;
	}
	public boolean isWorkActivityEntityCheckInRestriction() {
		return workActivityEntityCheckInRestriction;
	}
	public void setWorkActivityEntityCheckInRestriction(boolean workActivityEntityCheckInRestriction) {
		this.workActivityEntityCheckInRestriction = workActivityEntityCheckInRestriction;
	}
	public String getWorkActivityRestrictionFormFieldSpecUniqueId() {
		return workActivityRestrictionFormFieldSpecUniqueId;
	}
	public void setWorkActivityRestrictionFormFieldSpecUniqueId(String workActivityRestrictionFormFieldSpecUniqueId) {
		this.workActivityRestrictionFormFieldSpecUniqueId = workActivityRestrictionFormFieldSpecUniqueId;
	}
	
	
	

	
}

