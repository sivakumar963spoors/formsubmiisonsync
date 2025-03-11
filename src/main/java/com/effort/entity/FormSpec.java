package com.effort.entity;



import java.io.Serializable;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormSpec implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long formSpecId;
	private String uniqueId;
	private int companyId;
	private String formTitle;
	private long createdBy;
	@JsonProperty(access = Access.READ_ONLY)
	private boolean allAccess = true;
	@JsonProperty(access = Access.READ_ONLY)
	private boolean isPublic;
	private Long parentId;
	private boolean openToCustomer;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String printTemplate;
	
	private String printTemplatePdfSaveNameFieldUniqueId;
	private String emailTemplate;
	private String mobilePrintTemplate;
	private int version;
	private boolean deleted;
	private String empId;
	private boolean enableDefineLogic;
	private int formApprovalByMe;
	private int formApprovalByTeam;
	private int approvedCount;
	private int rejectedCount;
	private int pendingCount;
	

	private int type;
	@JsonProperty(access = Access.READ_ONLY)
	private boolean customerPresent;
	@JsonProperty(access = Access.READ_ONLY)
	private String workflowId;
	private int isSystemDefined;
	private int purpose=-1;
	private Long initialFormSpecId;
	private Long skeletonFormSpecId;
	private boolean mapeed;
	private boolean select;
	
	private String modifiedTime;
	
	private String createdTime;
	
	private int enableApprovalSubmissionTAT;
	private Integer approvalSubmissionTAT;
	
	private int formAdded;
	private int formModified;
	private int formDeleted;
	private int formApprovalSizeForSync;
	private int isAdvancedWorkPrintTemplate;
	private boolean evaluateHiddenFields;
	private String createdByName;
	private boolean ignoreWorkFieldsPrefixOnApiCreation;
	private boolean showOnlySaveOnRequisionAutoApprovalEnabled;
	private Integer resetAutoGenerateSequence;
	private Integer formType;
	private Long todaySubmissionCount;
	private Long yesterDaysSubmissionCount;
	private boolean showApprovalOrRequision;
	private Long last30DaysSubmissionCount;
	private int launchFormForSectionInstance;
	
	private boolean enableFormSubmissionFrequency;
	private Integer formSubmissionFrequencyInDays;
	private String formSubmissionFrequencyModifiedTime;
	private Integer formSubmissionFrequencyInMinutes;
	
	private String actualCreatedTime;


	public int getEnableApprovalSubmissionTAT() {
		return enableApprovalSubmissionTAT;
	}
	public void setEnableApprovalSubmissionTAT(int enableApprovalSubmissionTAT) {
		this.enableApprovalSubmissionTAT = enableApprovalSubmissionTAT;
	}
	public Integer getApprovalSubmissionTAT() {
		return approvalSubmissionTAT;
	}
	public void setApprovalSubmissionTAT(Integer approvalSubmissionTAT) {
		this.approvalSubmissionTAT = approvalSubmissionTAT;
	}
	public final static int PURPOUSE_JOB=1;
//	public final static int PURPOUSE_CUSTOMER=2;
	public final static int PURPOUSE_CUSTOMER=4;
	public final static int PURPOUSE_WORK=3;
	public static final int PURPOUSE_CUSTOMER_CREATION = 5;
	
	public static final int PURPOUSE_REJECTION_FORM = 6;
	
	public static final int PURPOUSE_CUSTOM_ENTITY = 7;
	
	public static final int PURPOUSE_EMPLOYEE_FORM = 8;
	
	public static final int PURPOUSE_SIGN_IN_SIGN_OUT_UPDATE_FORM = 10;
	
	public static final int PURPOUSE_GIG_USER_REGISTRAION_FORM = 12;
	
	private Boolean isOnlineForm=false;
	
	private Boolean hasDataSource=false;
	
	private boolean importFlag = true;
	
	private boolean importForm = true;
	
	private boolean addForm = true;
	private boolean empGroupMapped;
	
//	private boolean addOnlineFormInOffline =false;

	//Form details (ibibo report)
	private long formsCount;
	private String formIdsWithIdentifier;
	private List<Form> forms;
	
	private int stockForm = 0;
	
	private int userEditRestriction = 0;
	private String userEditRestrictionReason;
	
	private Boolean isPublicLinkForm=false;
	
	private boolean customEntityPresent;
	
	private boolean avoidFormWebCreate;
	private String avoidFormWebCreateMessage;
	
	private int stockUpdateType = -1;
	private boolean updateOnFormApproval;
	
	private String publicLinkUniqueId;
	private boolean dealerAcknowledgement=false;
	
	private boolean enableDraftForm;
	private int autoSaveInterval;
	private int draftLocationEnabled;
	private boolean enableColumnSeparatorInMobilePrintTemplate ;
	private boolean enableSaveAndNew;
	private boolean enableOnlineApiForm;
	private boolean sendDraftFormsInSync;
	private String showHelpText;
	
	private boolean enableDataSourceInOfflineMode;
	public static final int DISABLE_DATA_SOURCE_IN_OFFLINE_MODE = 0;
	public static final int ENABLE_DATA_SOURCE_IN_OFFLINE_MODE = 1;
	private boolean offlineCustomEntityUpdateOnApproval;
	
	private List<VisibilityDependencyCriteria> visibilityDependencyCriterias;
	
	private Long helpDocumentMediaId;
	private String linkText;
	private String sampleFileDownloadUrl;
	
	private boolean captureTurnAroundTime;
	
	private boolean enableWebPrintOnMobile;
	
	private boolean hiddenFieldsToDisplayInViewMode;
	private boolean clone;
	private int specType;
	private String workSpecId;
	private Long qrCodeMediaId;
	private boolean ocrForm;
	
	private boolean enableCustomerActivity;
	private boolean sendPublicFormForApproval;
	private Long activityId;
	
	private String publicLinkTinyUrlId;
	private boolean sendPublicFormForApprovalForCustomerReq;
	private boolean sendPublicFormForApprovalForEntityReq;
	private boolean sendPublicFormForApprovalForEmployeeReq;

	
	public static final int FORM_MULTI_PRINT_TEMPLATE = 1;
	
	private int enableMultiPrintTemplate;
	private long yesterdayFormsCount;
	private boolean ticketingSpec;
	private Long overallPendingFormsCount=0l;
	private Long workFormSpecMapId;
	private boolean enableAutoCopyConfiguration = false;
	private boolean enableSectionToSectionAutoCopyConfiguration = false;
	
	private boolean formSpecPermission;
	private boolean viewPermission;
	private boolean addPermission;
	private boolean modifyPermission;
	private boolean deletePermission;
	private Boolean enableDraftFormForMobile;
	//private boolean enableDraftFormForMobile;
	private String dynamicSaveButton;
	private String dynamicSuccessMessage;
	
	private boolean openFormsUniqueCheck = false;

	
	private String mobileLayout;
	private boolean removeBlankLines;
	
	private String errorMessageForUniqueness;
	private String successMessageForUniqueness;
	private long modifiedBy;
	private String modifiedByName;

	
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
	public Long getInitialFormSpecId() {
		return initialFormSpecId;
	}
	public void setInitialFormSpecId(Long initialFormSpecId) {
		this.initialFormSpecId = initialFormSpecId;
	}
	public Long getSkeletonFormSpecId() {
		return skeletonFormSpecId;
	}
	public void setSkeletonFormSpecId(Long skeletonFormSpecId) {
		this.skeletonFormSpecId = skeletonFormSpecId;
	}
	public long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getFormTitle() {
		return formTitle;
	}
	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public boolean isOpenToCustomer() {
		return openToCustomer;
	}
	public void setOpenToCustomer(boolean openToCustomer) {
		this.openToCustomer = openToCustomer;
	}
	public String getMobilePrintTemplate() {
		return mobilePrintTemplate;
	}
	public void setMobilePrintTemplate(String mobilePrintTemplate) {
		this.mobilePrintTemplate = mobilePrintTemplate;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getWorkflowId() {
		return workflowId;
	}
	
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	
	public String getPrintTemplate() {
		return printTemplate;
	}
	public void setPrintTemplate(String printTemplate) {
		this.printTemplate = printTemplate;
	}
	@JsonIgnore
	public int getCompanyId() {
		return companyId;
	}
	@JsonIgnore
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public boolean isAllAccess() {
		return allAccess;
	}
	
	public void setAllAccess(boolean allAccess) {
		this.allAccess = allAccess;
	}
	
	public boolean isPublic() {
		return isPublic;
	}
	
	public boolean getIsPublic() {
		return isPublic;
	}
	
	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	@JsonIgnore
	public Long getParentId() {
		return parentId;
	}
	@JsonIgnore
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	@JsonIgnore
	public int getType() {
		return type;
	}
	@JsonIgnore
	public void setType(int type) {
		this.type = type;
	}
	
	public boolean isCustomerPresent() {
		return customerPresent;
	}
	
	public void setCustomerPresent(boolean customerPresent) {
		this.customerPresent = customerPresent;
	}
	
	public String getPrintTemplatePdfSaveNameFieldUniqueId() {
		return printTemplatePdfSaveNameFieldUniqueId;
	}
	public void setPrintTemplatePdfSaveNameFieldUniqueId(
			String printTemplatePdfSaveNameFieldUniqueId) {
		this.printTemplatePdfSaveNameFieldUniqueId = printTemplatePdfSaveNameFieldUniqueId;
	}
	public String getEmailTemplate() {
		return emailTemplate;
	}
	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}
	@JsonIgnore
	public boolean isMapeed() {
		return mapeed;
	}
	@JsonIgnore
	public void setMapeed(boolean mapeed) {
		this.mapeed = mapeed;
	}
	public String toCSV() {
		return "[formSpecId=" + formSpecId + ", uniqueId=" + uniqueId + ", companyId=" + companyId + ", formTitle=" + formTitle + ", createdBy=" + createdBy + ", allAccess=" + allAccess + ", isPublic=" + isPublic + ", parentId=" + parentId + ", openToCustomer=" + openToCustomer
				+ ", version=" + version + ", deleted=" + deleted + ", type=" + type + ", customerPresent=" + customerPresent + ", isOnlineForm=" + isOnlineForm + "]";
	}
	
	public Boolean getIsOnlineForm() {
		return isOnlineForm;
	}
	public void setIsOnlineForm(Boolean isOnlineForm) {
		this.isOnlineForm = isOnlineForm;
	}
	public Boolean getHasDataSource() {
		return hasDataSource;
	}
	public void setHasDataSource(Boolean hasDataSource) {
		this.hasDataSource = hasDataSource;
	}
	public boolean isImportFlag() {
		return importFlag;
	}
	public void setImportFlag(boolean importFlag) {
		this.importFlag = importFlag;
	}
	public long getFormsCount() {
		return formsCount;
	}
	public void setFormsCount(long formsCount) {
		this.formsCount = formsCount;
	}
	public String getFormIdsWithIdentifier() {
		return formIdsWithIdentifier;
	}
	public void setFormIdsWithIdentifier(String formIdsWithIdentifier) {
		this.formIdsWithIdentifier = formIdsWithIdentifier;
	}
	public List<Form> getForms() {
		return forms;
	}
	public void setForms(List<Form> forms) {
		this.forms = forms;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public boolean isImportForm() {
		return importForm;
	}
	public void setImportForm(boolean importForm) {
		this.importForm = importForm;
	}
	
	public int getStockForm() {
		return stockForm;
	}
	public void setStockForm(int stockForm) {
		this.stockForm = stockForm;
	}
	public int getUserEditRestriction() {
		return userEditRestriction;
	}
	public void setUserEditRestriction(int userEditRestriction) {
		this.userEditRestriction = userEditRestriction;
	}
	public String getUserEditRestrictionReason() {
		return userEditRestrictionReason;
	}
	public void setUserEditRestrictionReason(String userEditRestrictionReason) {
		this.userEditRestrictionReason = userEditRestrictionReason;
	}
	public boolean isAddForm() {
		return addForm;
	}
	public void setAddForm(boolean addForm) {
		this.addForm = addForm;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FormSpec){
			FormSpec formSpec = (FormSpec) obj;
			return formSpec.getUniqueId().equals(this.uniqueId);
		} else {
			return super.equals(obj);
		}
	}
	
	public Boolean getIsPublicLinkForm() {
		return isPublicLinkForm;
	}
	public void setIsPublicLinkForm(Boolean isPublicLinkForm) {
		this.isPublicLinkForm = isPublicLinkForm;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	/*public boolean isAddOnlineFormInOffline() {
		return addOnlineFormInOffline;
	}
	public void setAddOnlineFormInOffline(boolean addOnlineFormInOffline) {
		this.addOnlineFormInOffline = addOnlineFormInOffline;
	}*/
	public boolean isEmpGroupMapped() {
		return empGroupMapped;
	}
	public void setEmpGroupMapped(boolean empGroupMapped) {
		this.empGroupMapped = empGroupMapped;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public boolean isCustomEntityPresent() {
		return customEntityPresent;
	}
	public void setCustomEntityPresent(boolean customEntityPresent) {
		this.customEntityPresent = customEntityPresent;
	}
	@JsonIgnore
	public String getAvoidFormWebCreateMessage() {
		return avoidFormWebCreateMessage;
	}
	@JsonIgnore
	public void setAvoidFormWebCreateMessage(String avoidFormWebCreateMessage) {
		this.avoidFormWebCreateMessage = avoidFormWebCreateMessage;
	}
	@JsonIgnore
	public boolean isAvoidFormWebCreate() {
		return avoidFormWebCreate;
	}
	@JsonIgnore
	public void setAvoidFormWebCreate(boolean avoidFormWebCreate) {
		this.avoidFormWebCreate = avoidFormWebCreate;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	
	@JsonIgnore
	public int getStockUpdateType() {
		return stockUpdateType;
	}
	@JsonIgnore
	public void setStockUpdateType(int stockUpdateType) {
		this.stockUpdateType = stockUpdateType;
	}
    public boolean isUpdateOnFormApproval() {
        return updateOnFormApproval;
    }
    public void setUpdateOnFormApproval(boolean updateOnFormApproval) {
        this.updateOnFormApproval = updateOnFormApproval;
    }
	public String getPublicLinkUniqueId() {
		return publicLinkUniqueId;
	}
	public void setPublicLinkUniqueId(String publicLinkUniqueId) {
		this.publicLinkUniqueId = publicLinkUniqueId;
	}
	public boolean isDealerAcknowledgement() {
		return dealerAcknowledgement;
	}
	public void setDealerAcknowledgement(boolean dealerAcknowledgement) {
		this.dealerAcknowledgement = dealerAcknowledgement;
	}
	public boolean isEnableDraftForm() {
		return enableDraftForm;
	}
	public void setEnableDraftForm(boolean enableDraftForm) {
		this.enableDraftForm = enableDraftForm;
	}
	public int getAutoSaveInterval() {
		return autoSaveInterval;
	}
	public void setAutoSaveInterval(int autoSaveInterval) {
		this.autoSaveInterval = autoSaveInterval;
	}
	public boolean isEnableColumnSeparatorInMobilePrintTemplate() {
		return enableColumnSeparatorInMobilePrintTemplate;
	}
	public void setEnableColumnSeparatorInMobilePrintTemplate(boolean enableColumnSeparatorInMobilePrintTemplate) {
		this.enableColumnSeparatorInMobilePrintTemplate = enableColumnSeparatorInMobilePrintTemplate;
	}
	public boolean isEnableSaveAndNew() {
		return enableSaveAndNew;
	}
	public void setEnableSaveAndNew(boolean enableSaveAndNew) {
		this.enableSaveAndNew = enableSaveAndNew;
	}
	
	
	public int getDraftLocationEnabled() {
		return draftLocationEnabled;
	}
	public void setDraftLocationEnabled(int draftLocationEnabled) {
		this.draftLocationEnabled = draftLocationEnabled;
	}
	
	public boolean isEnableOnlineApiForm() {
		return enableOnlineApiForm;
	}
	public void setEnableOnlineApiForm(boolean enableOnlineApiForm) {
		this.enableOnlineApiForm = enableOnlineApiForm;
	}
	
	public boolean isSendDraftFormsInSync() {
		return sendDraftFormsInSync;
	}
	public void setSendDraftFormsInSync(boolean sendDraftFormsInSync) {
		this.sendDraftFormsInSync = sendDraftFormsInSync;
	}
	public String getShowHelpText() {
		return showHelpText;
	}

	public void setShowHelpText(String showHelpText) {
		this.showHelpText = showHelpText;
	}
	
	public boolean isEnableDataSourceInOfflineMode() {
		return enableDataSourceInOfflineMode;
	}
	
	public void setEnableDataSourceInOfflineMode(boolean enableDataSourceInOfflineMode) {
		this.enableDataSourceInOfflineMode = enableDataSourceInOfflineMode;
	}
	

	@Override
	public String toString() {
		return "FormSpec [formSpecId=" + formSpecId + ", uniqueId=" + uniqueId + ", companyId=" + companyId
				+ ", formTitle=" + formTitle + ", createdBy=" + createdBy + ", allAccess=" + allAccess + ", isPublic="
				+ isPublic + ", parentId=" + parentId + ", openToCustomer=" + openToCustomer + ", printTemplate="
				+ printTemplate + ", printTemplatePdfSaveNameFieldUniqueId=" + printTemplatePdfSaveNameFieldUniqueId
				+ ", emailTemplate=" + emailTemplate + ", mobilePrintTemplate=" + mobilePrintTemplate + ", version="
				+ version + ", deleted=" + deleted + ", empId=" + empId + ", type=" + type + ", customerPresent="
				+ customerPresent + ", workflowId=" + workflowId + ", isSystemDefined=" + isSystemDefined + ", purpose="
				+ purpose + ", initialFormSpecId=" + initialFormSpecId + ", skeletonFormSpecId=" + skeletonFormSpecId
				+ ", mapeed=" + mapeed + ", select=" + select + ", modifiedTime=" + modifiedTime + ", createdTime="
				+ createdTime + ", enableApprovalSubmissionTAT=" + enableApprovalSubmissionTAT
				+ ", approvalSubmissionTAT=" + approvalSubmissionTAT + ", formAdded=" + formAdded + ", formModified="
				+ formModified + ", formDeleted=" + formDeleted + ", formApprovalSizeForSync=" + formApprovalSizeForSync
				+ ", isOnlineForm=" + isOnlineForm + ", hasDataSource=" + hasDataSource + ", importFlag=" + importFlag
				+ ", importForm=" + importForm + ", addForm=" + addForm + ", empGroupMapped=" + empGroupMapped
				+ ", formsCount=" + formsCount + ", formIdsWithIdentifier=" + formIdsWithIdentifier + ", forms=" + forms
				+ ", stockForm=" + stockForm + ", userEditRestriction=" + userEditRestriction
				+ ", userEditRestrictionReason=" + userEditRestrictionReason + ", isPublicLinkForm=" + isPublicLinkForm
				+ ", customEntityPresent=" + customEntityPresent + ", avoidFormWebCreate=" + avoidFormWebCreate
				+ ", avoidFormWebCreateMessage=" + avoidFormWebCreateMessage + ", stockUpdateType=" + stockUpdateType
				+ ", updateOnFormApproval=" + updateOnFormApproval + ", publicLinkUniqueId=" + publicLinkUniqueId
				+ ", dealerAcknowledgement=" + dealerAcknowledgement + ", enableDraftForm=" + enableDraftForm
				+ ", autoSaveInterval=" + autoSaveInterval + ", draftLocationEnabled=" + draftLocationEnabled
				+ ", enableColumnSeparatorInMobilePrintTemplate=" + enableColumnSeparatorInMobilePrintTemplate
				+ ", enableSaveAndNew=" + enableSaveAndNew + ", enableOnlineApiForm=" + enableOnlineApiForm
				+ ", sendDraftFormsInSync=" + sendDraftFormsInSync + ", showHelpText=" + showHelpText
				+ ", enableDataSourceInOfflineMode=" + enableDataSourceInOfflineMode 
				+ ", offlineCustomEntityUpdateOnApproval=" + offlineCustomEntityUpdateOnApproval
				+ ", captureTurnAroundTime=" + captureTurnAroundTime+", publicLinkTinyUrlId="+publicLinkTinyUrlId + "]";

	}
	public int getFormDeleted() {
		return formDeleted;
	}
	public void setFormDeleted(int formDeleted) {
		this.formDeleted = formDeleted;
	}
	
	public int getFormAdded() {
		return formAdded;
	}
	public void setFormAdded(int formAdded) {
		this.formAdded = formAdded;
	}
	public int getFormModified() {
		return formModified;
	}
	public void setFormModified(int formModified) {
		this.formModified = formModified;
	}
	public int getFormApprovalSizeForSync() {
		return formApprovalSizeForSync;
	}
	public void setFormApprovalSizeForSync(int formApprovalSizeForSync) {
		this.formApprovalSizeForSync = formApprovalSizeForSync;	
	}
		
	@JsonIgnore
	public List<VisibilityDependencyCriteria> getVisibilityDependencyCriterias() {
		return visibilityDependencyCriterias;
	}
	@JsonIgnore
	public void setVisibilityDependencyCriterias(List<VisibilityDependencyCriteria> visibilityDependencyCriterias) {
		this.visibilityDependencyCriterias = visibilityDependencyCriterias;
	}
	public boolean isOfflineCustomEntityUpdateOnApproval() {
		return offlineCustomEntityUpdateOnApproval;
	}
	public void setOfflineCustomEntityUpdateOnApproval(boolean offlineCustomEntityUpdateOnApproval) {
		this.offlineCustomEntityUpdateOnApproval = offlineCustomEntityUpdateOnApproval;
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
	public int getIsAdvancedWorkPrintTemplate() {
		return isAdvancedWorkPrintTemplate;
	}
	public void setIsAdvancedWorkPrintTemplate(int isAdvancedWorkPrintTemplate) {
		this.isAdvancedWorkPrintTemplate = isAdvancedWorkPrintTemplate;
	}

	public boolean isCaptureTurnAroundTime() {
		return captureTurnAroundTime;
	}
	public void setCaptureTurnAroundTime(boolean captureTurnAroundTime) {
		this.captureTurnAroundTime = captureTurnAroundTime;
	}
	
	public boolean isEnableWebPrintOnMobile() {
		return enableWebPrintOnMobile;
	}
	public void setEnableWebPrintOnMobile(boolean enableWebPrintOnMobile) {
		this.enableWebPrintOnMobile = enableWebPrintOnMobile;
	}
	public boolean isHiddenFieldsToDisplayInViewMode() {
		return hiddenFieldsToDisplayInViewMode;
	}
	public void setHiddenFieldsToDisplayInViewMode(boolean hiddenFieldsToDisplayInViewMode) {
		this.hiddenFieldsToDisplayInViewMode = hiddenFieldsToDisplayInViewMode;
	}
	public boolean isClone() {
		return clone;
	}
	public void setClone(boolean clone) {
		this.clone = clone;
	}
	public int getSpecType() {
		return specType;
	}
	public void setSpecType(int specType) {
		this.specType = specType;
	}
	public String getWorkSpecId() {
		return workSpecId;
	}
	public void setWorkSpecId(String workSpecId) {
		this.workSpecId = workSpecId;
	}
	public boolean isEvaluateHiddenFields() {
		return evaluateHiddenFields;
	}
	public void setEvaluateHiddenFields(boolean evaluateHiddenFields) {
		this.evaluateHiddenFields = evaluateHiddenFields;
	}
	public Long getQrCodeMediaId() {
		return qrCodeMediaId;
	}
	public void setQrCodeMediaId(Long qrCodeMediaId) {
		this.qrCodeMediaId = qrCodeMediaId;
	}
		
	public int getEnableMultiPrintTemplate() {
		return enableMultiPrintTemplate;
	}
	public void setEnableMultiPrintTemplate(int enableMultiPrintTemplate) {
		this.enableMultiPrintTemplate = enableMultiPrintTemplate;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public boolean isSendPublicFormForApproval() {
		return sendPublicFormForApproval;
	}
	public void setSendPublicFormForApproval(boolean sendPublicFormForApproval) {
		this.sendPublicFormForApproval = sendPublicFormForApproval;
	}
	public long getYesterdayFormsCount() {
		return yesterdayFormsCount;
	}
	public void setYesterdayFormsCount(long yesterdayFormsCount) {
		this.yesterdayFormsCount = yesterdayFormsCount;
	}
	public boolean isOcrForm() {
		return ocrForm;
	}
	public void setOcrForm(boolean ocrForm) {
		this.ocrForm = ocrForm;
	}
	
	public boolean isEnableCustomerActivity() {
		return enableCustomerActivity;
	}
	public void setEnableCustomerActivity(boolean enableCustomerActivity) {
		this.enableCustomerActivity = enableCustomerActivity;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public boolean isIgnoreWorkFieldsPrefixOnApiCreation() {
		return ignoreWorkFieldsPrefixOnApiCreation;
	}
	public void setIgnoreWorkFieldsPrefixOnApiCreation(boolean ignoreWorkFieldsPrefixOnApiCreation) {
		this.ignoreWorkFieldsPrefixOnApiCreation = ignoreWorkFieldsPrefixOnApiCreation;
	}
	
	public String getPublicLinkTinyUrlId() {
		return publicLinkTinyUrlId;
	}
	public void setPublicLinkTinyUrlId(String publicLinkTinyUrlId) {
		this.publicLinkTinyUrlId = publicLinkTinyUrlId;
	}
	public boolean isShowOnlySaveOnRequisionAutoApprovalEnabled() {
		return showOnlySaveOnRequisionAutoApprovalEnabled;
	}
	public void setShowOnlySaveOnRequisionAutoApprovalEnabled(boolean showOnlySaveOnRequisionAutoApprovalEnabled) {
		this.showOnlySaveOnRequisionAutoApprovalEnabled = showOnlySaveOnRequisionAutoApprovalEnabled;
	}
	
	public boolean isSendPublicFormForApprovalForCustomerReq() {
		return sendPublicFormForApprovalForCustomerReq;
	}
	public void setSendPublicFormForApprovalForCustomerReq(boolean sendPublicFormForApprovalForCustomerReq) {
		this.sendPublicFormForApprovalForCustomerReq = sendPublicFormForApprovalForCustomerReq;
	}
	public boolean isSendPublicFormForApprovalForEntityReq() {
		return sendPublicFormForApprovalForEntityReq;
	}
	public void setSendPublicFormForApprovalForEntityReq(boolean sendPublicFormForApprovalForEntityReq) {
		this.sendPublicFormForApprovalForEntityReq = sendPublicFormForApprovalForEntityReq;
	}
	public Integer getResetAutoGenerateSequence() {
		return resetAutoGenerateSequence;
	}
	public void setResetAutoGenerateSequence(Integer resetAutoGenerateSequence) {
		this.resetAutoGenerateSequence = resetAutoGenerateSequence;
	}
	public boolean isTicketingSpec() {
		return ticketingSpec;
	}
	public void setTicketingSpec(boolean ticketingSpec) {
		this.ticketingSpec = ticketingSpec;
	}
	public Integer getFormType() {
		return formType;
	}
	public void setFormType(Integer formType) {
		this.formType = formType;
	}
	public Long getTodaySubmissionCount() {
		return todaySubmissionCount;
	}
	public void setTodaySubmissionCount(Long todaySubmissionCount) {
		this.todaySubmissionCount = todaySubmissionCount;
	}
	public Long getYesterDaysSubmissionCount() {
		return yesterDaysSubmissionCount;
	}
	public void setYesterDaysSubmissionCount(Long yesterDaysSubmissionCount) {
		this.yesterDaysSubmissionCount = yesterDaysSubmissionCount;
	}
	public Long getOverallPendingFormsCount() {
		return overallPendingFormsCount;
	}
	public void setOverallPendingFormsCount(Long overallPendingFormsCount) {
		this.overallPendingFormsCount = overallPendingFormsCount;
	}
	public Long getWorkFormSpecMapId() {
		return workFormSpecMapId;
	}
	public void setWorkFormSpecMapId(Long workFormSpecMapId) {
		this.workFormSpecMapId = workFormSpecMapId;
	}
	public Long getLast30DaysSubmissionCount() {
		return last30DaysSubmissionCount;
	}
	public void setLast30DaysSubmissionCount(Long last30DaysSubmissionCount) {
		this.last30DaysSubmissionCount = last30DaysSubmissionCount;
	}
	public boolean isEnableAutoCopyConfiguration() {
		return enableAutoCopyConfiguration;
	}
	public void setEnableAutoCopyConfiguration(boolean enableAutoCopyConfiguration) {
		this.enableAutoCopyConfiguration = enableAutoCopyConfiguration;
	}
	public boolean isEnableSectionToSectionAutoCopyConfiguration() {
		return enableSectionToSectionAutoCopyConfiguration;
	}
	public void setEnableSectionToSectionAutoCopyConfiguration(boolean enableSectionToSectionAutoCopyConfiguration) {
		this.enableSectionToSectionAutoCopyConfiguration = enableSectionToSectionAutoCopyConfiguration;
	}
	public boolean isShowApprovalOrRequision() {
		return showApprovalOrRequision;
	}
	public void setShowApprovalOrRequision(boolean showApprovalOrRequision) {
		this.showApprovalOrRequision = showApprovalOrRequision;
	}
	public boolean isSendPublicFormForApprovalForEmployeeReq() {
		return sendPublicFormForApprovalForEmployeeReq;
	}
	public void setSendPublicFormForApprovalForEmployeeReq(boolean sendPublicFormForApprovalForEmployeeReq) {
		this.sendPublicFormForApprovalForEmployeeReq = sendPublicFormForApprovalForEmployeeReq;
	}
	public boolean isEnableDefineLogic() {
		return enableDefineLogic;
	}
	public void setEnableDefineLogic(boolean enableDefineLogic) {
		this.enableDefineLogic = enableDefineLogic;
	}

	public int getFormApprovalByMe() {
		return formApprovalByMe;
	}
	public void setFormApprovalByMe(int formApprovalByMe) {
		this.formApprovalByMe = formApprovalByMe;
	}
	public int getFormApprovalByTeam() {
		return formApprovalByTeam;
	}
	public void setFormApprovalByTeam(int formApprovalByTeam) {
		this.formApprovalByTeam = formApprovalByTeam;
	}

	public int getLaunchFormForSectionInstance() {
		return launchFormForSectionInstance;
	}
	public void setLaunchFormForSectionInstance(int launchFormForSectionInstance) {
		this.launchFormForSectionInstance = launchFormForSectionInstance;
	}
	public int getApprovedCount() {
		return approvedCount;
	}
	public void setApprovedCount(int approvedCount) {
		this.approvedCount = approvedCount;
	}
	public int getRejectedCount() {
		return rejectedCount;
	}
	public void setRejectedCount(int rejectedCount) {
		this.rejectedCount = rejectedCount;
	}
	public int getPendingCount() {
		return pendingCount;
	}
	public void setPendingCount(int pendingCount) {
		this.pendingCount = pendingCount;
	}
	public boolean isEnableFormSubmissionFrequency() {
		return enableFormSubmissionFrequency;
	}
	public void setEnableFormSubmissionFrequency(boolean enableFormSubmissionFrequency) {
		this.enableFormSubmissionFrequency = enableFormSubmissionFrequency;
	}
	public Integer getFormSubmissionFrequencyInDays() {
		return formSubmissionFrequencyInDays;
	}
	public void setFormSubmissionFrequencyInDays(Integer formSubmissionFrequencyInDays) {
		this.formSubmissionFrequencyInDays = formSubmissionFrequencyInDays;
	}
	public String getFormSubmissionFrequencyModifiedTime() {
		return formSubmissionFrequencyModifiedTime;
	}
	public void setFormSubmissionFrequencyModifiedTime(String formSubmissionFrequencyModifiedTime) {
		this.formSubmissionFrequencyModifiedTime = formSubmissionFrequencyModifiedTime;
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
	public Boolean getEnableDraftFormForMobile() {
		return enableDraftFormForMobile;
	}
	public void setEnableDraftFormForMobile(Boolean enableDraftFormForMobile) {
		this.enableDraftFormForMobile = enableDraftFormForMobile;
	}

	public String getDynamicSaveButton() {
		return dynamicSaveButton;
	}
	public void setDynamicSaveButton(String dynamicSaveButton) {
		this.dynamicSaveButton = dynamicSaveButton;
	}
	public String getDynamicSuccessMessage() {
		return dynamicSuccessMessage;
	}
	public void setDynamicSuccessMessage(String dynamicSuccessMessage) {
		this.dynamicSuccessMessage = dynamicSuccessMessage;
	}
	public boolean isOpenFormsUniqueCheck() {
		return openFormsUniqueCheck;
	}
	public void setOpenFormsUniqueCheck(boolean openFormsUniqueCheck) {
		this.openFormsUniqueCheck = openFormsUniqueCheck;
	}
	public Integer getFormSubmissionFrequencyInMinutes() {
		return formSubmissionFrequencyInMinutes;
	}
	public void setFormSubmissionFrequencyInMinutes(Integer formSubmissionFrequencyInMinutes) {
		this.formSubmissionFrequencyInMinutes = formSubmissionFrequencyInMinutes;
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
	public long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedByName() {
		return modifiedByName;
	}
	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}
	public String getActualCreatedTime() {
		return actualCreatedTime;
	}
	public void setActualCreatedTime(String actualCreatedTime) {
		this.actualCreatedTime = actualCreatedTime;
	}
	

}

