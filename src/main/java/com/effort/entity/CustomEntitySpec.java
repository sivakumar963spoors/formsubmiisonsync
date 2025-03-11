package com.effort.entity;



import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.effort.util.Api;



public class CustomEntitySpec implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Long customEntitySpecId;
	private String title;
	private String description;
	private String formSpecUniqueId;
	private Long createdBy;
	private Long modifiedBy;
	private int companyId;
	private String createdTime;
	private String modifiedTime;
	private Long copiedFrom=0l;
	private String copiedFromTitle;

	private int isSystemDefined=0;
	private int purpose=-1;
	private long formSpecId;
	private Long skeletonCustomEntitySpecId;
	private int productId;
	private String externalCustomEntitySpecId;
	private boolean enableDefineLogic;

	
	private Boolean deleted=false;
	
	private boolean importCustomEntity = true;
	
	private String mobileLayout;
	private boolean removeBlankLines;
	
	private String rejectionFormSpecUniqueId;
	private long rejectionFormSpecId;
	private String rejectionFormSpecTitle;
	private boolean createRejectionFormSpec = false;
	
	private boolean captureRejectionReasons = false;
	
	private boolean customEntitySharing;
	
	private boolean enableCustomEntityCheckIn;
	
	private boolean enableCustomEntityOwnerToPerformActions = true;
	private boolean enableAssignActions = true;
	private long count;
	
	private String syncIncompletedCustomEntitysForMobile;
	
	private boolean canRejectCustomEntityInvitation = true;
	
	private boolean canRejectCustomEntity = true;
	
	private boolean visibleOnlyManagerCustomEntitys = false;
	
	private List<Long> eligibleEmployeeIdsForCustomEntitySync;
	
	private boolean globalSearch;
	private boolean customEntitySpecVisibility;

	private String mapCustomEntitySpecToEmployee;
	private String visibleCustomEntitySpec;
	private String sendCustomEntitySpecForOfflineUse;
	private String sendCutomEntityDataInSyncVia;
	private boolean dayPlanAllowd;
	
	private int customEntityEmpMappingType;
	
	private int customEntitesAddedInSync;
	private int customEntitesModifiedInSync;
	private String createdByName;
	
	private boolean enableOnlineSearchForUniqueness = false;
	
	private boolean canModifyCustomEntity = true; 
	private String errorMessageForUniqueness;
	private String successMessageForUniqueness;
	
	private int enableOCRCheckIn;
	
	private int enableSelfieWhileCheckIn;
	private int enableSelfieWhileCheckOut;
	private String aliasName;
	
	public static int BASIC_MAPPING = 1;
	public static int CONFIGURATION_BASED_MAPPING = 2;
	
	public boolean isDayPlanAllowd() {
		return dayPlanAllowd;
	}

	public void setDayPlanAllowd(boolean dayPlanAllowd) {
		this.dayPlanAllowd = dayPlanAllowd;
	}

	private Map<String,String> customEntitySpecSettingsMap;
	
	private Map<String,String> locationProximitySettingsMap;
	private boolean formSpecVisibileInCustomEntityLogin = false;
	
	
	public boolean isFormSpecVisibileInCustomEntityLogin() {
		return formSpecVisibileInCustomEntityLogin;
	}

	public void setFormSpecVisibileInCustomEntityLogin(boolean formSpecVisibileInCustomEntityLogin) {
		this.formSpecVisibileInCustomEntityLogin = formSpecVisibileInCustomEntityLogin;
	}

	public Long getCustomEntitySpecId() {
		return customEntitySpecId;
	}

	public void setCustomEntitySpecId(Long customEntitySpecId) {
		this.customEntitySpecId = customEntitySpecId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}

	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
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

	public long getFormSpecId() {
		return formSpecId;
	}

	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}

	public Long getSkeletonCustomEntitySpecId() {
		return skeletonCustomEntitySpecId;
	}

	public void setSkeletonCustomEntitySpecId(Long skeletonCustomEntitySpecId) {
		this.skeletonCustomEntitySpecId = skeletonCustomEntitySpecId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getExternalCustomEntitySpecId() {
		return externalCustomEntitySpecId;
	}

	public void setExternalCustomEntitySpecId(String externalCustomEntitySpecId) {
		this.externalCustomEntitySpecId = externalCustomEntitySpecId;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isImportCustomEntity() {
		return importCustomEntity;
	}

	public void setImportCustomEntity(boolean importCustomEntity) {
		this.importCustomEntity = importCustomEntity;
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

	public String getRejectionFormSpecTitle() {
		return rejectionFormSpecTitle;
	}

	public void setRejectionFormSpecTitle(String rejectionFormSpecTitle) {
		this.rejectionFormSpecTitle = rejectionFormSpecTitle;
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

	public boolean isCustomEntitySharing() {
		return customEntitySharing;
	}

	public void setCustomEntitySharing(boolean customEntitySharing) {
		this.customEntitySharing = customEntitySharing;
	}

	public boolean isEnableCustomEntityCheckIn() {
		return enableCustomEntityCheckIn;
	}

	public void setEnableCustomEntityCheckIn(boolean enableCustomEntityCheckIn) {
		this.enableCustomEntityCheckIn = enableCustomEntityCheckIn;
	}

	public boolean isEnableCustomEntityOwnerToPerformActions() {
		return enableCustomEntityOwnerToPerformActions;
	}

	public void setEnableCustomEntityOwnerToPerformActions(boolean enableCustomEntityOwnerToPerformActions) {
		this.enableCustomEntityOwnerToPerformActions = enableCustomEntityOwnerToPerformActions;
	}

	public boolean isEnableAssignActions() {
		return enableAssignActions;
	}

	public void setEnableAssignActions(boolean enableAssignActions) {
		this.enableAssignActions = enableAssignActions;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getSyncIncompletedCustomEntitysForMobile() {
		return syncIncompletedCustomEntitysForMobile;
	}

	public void setSyncIncompletedCustomEntitysForMobile(String syncIncompletedCustomEntitysForMobile) {
		this.syncIncompletedCustomEntitysForMobile = syncIncompletedCustomEntitysForMobile;
	}

	public boolean isCanRejectCustomEntityInvitation() {
		return canRejectCustomEntityInvitation;
	}

	public void setCanRejectCustomEntityInvitation(boolean canRejectCustomEntityInvitation) {
		this.canRejectCustomEntityInvitation = canRejectCustomEntityInvitation;
	}

	public boolean isCanRejectCustomEntity() {
		return canRejectCustomEntity;
	}

	public void setCanRejectCustomEntity(boolean canRejectCustomEntity) {
		this.canRejectCustomEntity = canRejectCustomEntity;
	}

	public boolean isVisibleOnlyManagerCustomEntitys() {
		return visibleOnlyManagerCustomEntitys;
	}

	public void setVisibleOnlyManagerCustomEntitys(boolean visibleOnlyManagerCustomEntitys) {
		this.visibleOnlyManagerCustomEntitys = visibleOnlyManagerCustomEntitys;
	}

	public List<Long> getEligibleEmployeeIdsForCustomEntitySync() {
		return eligibleEmployeeIdsForCustomEntitySync;
	}

	public void setEligibleEmployeeIdsForCustomEntitySync(List<Long> eligibleEmployeeIdsForCustomEntitySync) {
		this.eligibleEmployeeIdsForCustomEntitySync = eligibleEmployeeIdsForCustomEntitySync;
	}

	public boolean isGlobalSearch() {
		return globalSearch;
	}

	public void setGlobalSearch(boolean globalSearch) {
		this.globalSearch = globalSearch;
	}

	public String getMapCustomEntitySpecToEmployee() {
		return mapCustomEntitySpecToEmployee;
	}

	public void setMapCustomEntitySpecToEmployee(String mapCustomEntitySpecToEmployee) {
		this.mapCustomEntitySpecToEmployee = mapCustomEntitySpecToEmployee;
	}

	public String getVisibleCustomEntitySpec() {
		return visibleCustomEntitySpec;
	}

	public void setVisibleCustomEntitySpec(String visibleCustomEntitySpec) {
		this.visibleCustomEntitySpec = visibleCustomEntitySpec;
	}

	public String getSendCustomEntitySpecForOfflineUse() {
		return sendCustomEntitySpecForOfflineUse;
	}

	public void setSendCustomEntitySpecForOfflineUse(String sendCustomEntitySpecForOfflineUse) {
		this.sendCustomEntitySpecForOfflineUse = sendCustomEntitySpecForOfflineUse;
	}

	public String getSendCutomEntityDataInSyncVia() {
		return sendCutomEntityDataInSyncVia;
	}

	public void setSendCutomEntityDataInSyncVia(String sendCutomEntityDataInSyncVia) {
		this.sendCutomEntityDataInSyncVia = sendCutomEntityDataInSyncVia;
	}

	public Map<String, String> getCustomEntitySpecSettingsMap() {
		return customEntitySpecSettingsMap;
	}

	public void setCustomEntitySpecSettingsMap(
			Map<String, String> customEntitySpecSettingsMap) {
		this.customEntitySpecSettingsMap = customEntitySpecSettingsMap;
	}

	public Map<String, String> getLocationProximitySettingsMap() {
		return locationProximitySettingsMap;
	}

	public void setLocationProximitySettingsMap(
			Map<String, String> locationProximitySettingsMap) {
		this.locationProximitySettingsMap = locationProximitySettingsMap;
	}

	public boolean isCustomEntitySpecVisibility() {
		return customEntitySpecVisibility;
	}

	public void setCustomEntitySpecVisibility(boolean customEntitySpecVisibility) {
		this.customEntitySpecVisibility = customEntitySpecVisibility;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CustomEntitySpec) {
			return getCustomEntitySpecId() == ((CustomEntitySpec) obj).getCustomEntitySpecId();
		} else {
			return super.equals(obj);
		}
	}

	public int getCustomEntityEmpMappingType() {
		return customEntityEmpMappingType;
	}

	public void setCustomEntityEmpMappingType(int customEntityEmpMappingType) {
		this.customEntityEmpMappingType = customEntityEmpMappingType;
	}

	public int getCustomEntitesAddedInSync() {
		return customEntitesAddedInSync;
	}

	public void setCustomEntitesAddedInSync(int customEntitesAddedInSync) {
		this.customEntitesAddedInSync = customEntitesAddedInSync;
	}

	public int getCustomEntitesModifiedInSync() {
		return customEntitesModifiedInSync;
	}

	public void setCustomEntitesModifiedInSync(int customEntitesModifiedInSync) {
		this.customEntitesModifiedInSync = customEntitesModifiedInSync;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public boolean isEnableDefineLogic() {
		return enableDefineLogic;
	}
	public void setEnableDefineLogic(boolean enableDefineLogic) {
		this.enableDefineLogic = enableDefineLogic;
	}

	public boolean isEnableOnlineSearchForUniqueness() {
		return enableOnlineSearchForUniqueness;
	}

	public void setEnableOnlineSearchForUniqueness(boolean enableOnlineSearchForUniqueness) {
		this.enableOnlineSearchForUniqueness = enableOnlineSearchForUniqueness;
	}

	public boolean isCanModifyCustomEntity() {
		return canModifyCustomEntity;
	}

	public void setCanModifyCustomEntity(boolean canModifyCustomEntity) {
		this.canModifyCustomEntity = canModifyCustomEntity;
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

	public int getEnableOCRCheckIn() {
		return enableOCRCheckIn;
	}

	public void setEnableOCRCheckIn(int enableOCRCheckIn) {
		this.enableOCRCheckIn = enableOCRCheckIn;
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

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		if(Api.isEmptyString(aliasName)) {
			aliasName = title;
		}
		this.aliasName = aliasName;
	}
	
	
	
}

