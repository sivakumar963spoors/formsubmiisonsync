package com.effort.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class FormAndField implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	
	public static int DRAFT_FORM = 1;
	public static int DRAFT_AND_CLOSE_FORM = 2;
	
	private long formId;
	private long formSpecId;
	private String assignTo;
	private List<FormField> fields;
	private List<FormSectionField> sectionFields;
	private Customer customer;
	private List<CustomerField> customerFieldsConfigList;
	
	private String error;
	
	private int minInstanceId;
	private int maxInstanceId;
	private String workId;
	private String workActionSpecId;
	private String customEntityId;
	
	private String externalWorkId;
	private String workSpecId;
	
	private Integer savedOnline;
	private Integer modifiedOnline;
	
	private Integer status;
	private boolean isWorkInviation;
	private boolean workSharing;
	private String workSharingEmpIds;
	private Work work;
	
	private JResponse jresponse;
	private boolean jresError; 
	
	private String stage;
	
	private boolean workCompleted;
	
	private String ipAddress;
	
	private String ltfWorkType;
	private String ltfVendorId;
	private String ltfCityId;
	
	private boolean isworkDataSync;
	
	private String externalWorkSpecId;
	private String externalCustomEntitySpecId;
	private String externalCustomEntityId;
	private Long customEntitySpecId;
	
	private String externalFormSpecId;
	private String externalFormId;
	
	private WorkSpec workSpec;
	private Long subTaskParentWorkId;
	private Long workProcessSubTaskSpecId;
	
	private String workFieldsUniqueKey;
	private String formFieldsUniqueKey;
	private boolean customEntityModify;
	
	private Employee employee;
	private String apiEntityIdFieldLabel;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String modifyStatus;//modified or No form change detected
	private boolean deleted;
	private boolean workSharingSetUpDone = false;
	
	private boolean gmetCompanyBasedWorkCreation = false;
	private String empGroupIdsToSendWorkInvitationForGmet;
	private boolean includeWorkInvitations;
	private boolean ignoreComputedFieldsComputation;
	
	private List<String> mappedEmployees;
	private boolean isAssignToChanged;
	private String oldAssignTo;
	
	private String sendForApproval;
	private int draftForm;
	private long bulkUploadId;
	
	private long tatTime;
	private Integer assignmentType;
	private String workInvitationEmpIds;
	private boolean mosSpec;
	private String existingWorkId;
	
	private boolean ticketingForm;
	private Integer sourceOfAction;
	private boolean skipValidation;
	private String soruceOfActionString;
	private boolean disableDataPost;
	private String restrictWorkDataPostConfigId;
	private boolean bulkAction;
	private String externalIdPrefix;
	private Integer sourceOfModification;
	private String createdTime;
	private String createdBy;
	private boolean fromMigrationFields;
	private String modifiedTime;
	private String completedTime;
	private String modifiedBy;
	private Integer empSelectionType;
	private String allInvitaionEmpIds;
	private boolean feedbackForm;
	private boolean skipSendJmsMessage;
	
	private String customEntityFieldsUniqueKey;
	
	private String hiddenFieldsMapJson;
	private String hiddenSectionsJson;
	private String workName;
	private boolean skipDateTimeFields;
	
	private Long triggerFormId;
	
	
	public List<String> getMappedEmployees() {
        return mappedEmployees;
    }
    public void setMappedEmployees(List<String> mappedEmployees) {
        this.mappedEmployees = mappedEmployees;
    }
    public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
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
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	public List<FormField> getFields() {
		return fields;
	}
	public void setFields(List<FormField> fields) {
		this.fields = fields;
	}
	public List<FormSectionField> getSectionFields() {
		return sectionFields;
	}
	public void setSectionFields(List<FormSectionField> sectionFields) {
		this.sectionFields = sectionFields;
	}
//	public List<FormAndSectionField> getFormAndSectionFields() {
//		return formAndSectionFields;
//	}
//	public void setFormAndSectionFields(
//			List<FormAndSectionField> formAndSectionFields) {
//		this.formAndSectionFields = formAndSectionFields;
//	}
	
	
	
	
	
	
	
	
	public int getMinInstanceId() {
		return minInstanceId;
	}
	public void setMinInstanceId(int minInstanceId) {
		this.minInstanceId = minInstanceId;
	}
	public int getMaxInstanceId() {
		return maxInstanceId;
	}
	public void setMaxInstanceId(int maxInstanceId) {
		this.maxInstanceId = maxInstanceId;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getExternalWorkId() {
		return externalWorkId;
	}
	public void setExternalWorkId(String externalWorkId) {
		this.externalWorkId = externalWorkId;
	}
	public String getWorkSpecId() {
		return workSpecId;
	}
	public void setWorkSpecId(String workSpecId) {
		this.workSpecId = workSpecId;
	}
	public Integer getSavedOnline() {
		return savedOnline;
	}
	public void setSavedOnline(Integer savedOnline) {
		this.savedOnline = savedOnline;
	}
	public Integer getModifiedOnline() {
		return modifiedOnline;
	}
	public void setModifiedOnline(Integer modifiedOnline) {
		this.modifiedOnline = modifiedOnline;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public boolean getIsWorkInviation() {
		return isWorkInviation;
	}
	public void setIsWorkInviation(boolean isWorkInviation) {
		this.isWorkInviation = isWorkInviation;
	}
	public Work getWork() {
		return work;
	}
	public void setWork(Work work) {
		this.work = work;
	}
	public JResponse getJresponse() {
		return jresponse;
	}
	public boolean isJresError() {
		return jresError;
	}
	public void setJresponse(JResponse jresponse) {
		this.jresponse = jresponse;
	}
	public void setJresError(boolean jresError) {
		this.jresError = jresError;
	}
	
	public String getStage() {
		return stage;
	}
	
	public void setStage(String stage) {
		this.stage = stage;
	}
	@Override
	public String toString() {
		return "FormAndField [formId=" + formId + ", formSpecId=" + formSpecId
				+ ", assignTo=" + assignTo + ", fields=" + fields
				+ ", sectionFields=" + sectionFields + ", error=" + error
				+ ", minInstanceId=" + minInstanceId + ", maxInstanceId="
				+ maxInstanceId + ", workId=" + workId + ", externalWorkId="
				+ externalWorkId + ", workSpecId=" + workSpecId
				+ ", savedOnline=" + savedOnline + ", modifiedOnline="
				+ modifiedOnline + ", status=" + status + ", isWorkInviation="
				+ isWorkInviation + ", work=" + work + ", jresponse="
				+ jresponse + ", jresError=" + jresError + ", stage=" + stage
				+ "]";
	}
	public boolean isWorkCompleted() {
		return workCompleted;
	}
	public void setWorkCompleted(boolean workCompleted) {
		this.workCompleted = workCompleted;
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
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getLtfWorkType() {
		return ltfWorkType;
	}
	public void setLtfWorkType(String ltfWorkType) {
		this.ltfWorkType = ltfWorkType;
	}
	public String getLtfVendorId() {
		return ltfVendorId;
	}
	public void setLtfVendorId(String ltfVendorId) {
		this.ltfVendorId = ltfVendorId;
	}
	public String getLtfCityId() {
		return ltfCityId;
	}
	public void setLtfCityId(String ltfCityId) {
		this.ltfCityId = ltfCityId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<CustomerField> getCustomerFieldsConfigList() {
		return customerFieldsConfigList;
	}
	public void setCustomerFieldsConfigList(
			List<CustomerField> customerFieldsConfigList) {
		this.customerFieldsConfigList = customerFieldsConfigList;
	}
	public String getExternalWorkSpecId() {
		return externalWorkSpecId;
	}
	public void setExternalWorkSpecId(String externalWorkSpecId) {
		this.externalWorkSpecId = externalWorkSpecId;
	}
	public WorkSpec getWorkSpec() {
		return workSpec;
	}
	public void setWorkSpec(WorkSpec workSpec) {
		this.workSpec = workSpec;
	}
	public String getCustomEntityId() {
		return customEntityId;
	}
	public void setCustomEntityId(String customEntityId) {
		this.customEntityId = customEntityId;
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
	public boolean isCustomEntityModify() {
		return customEntityModify;
	}
	public void setCustomEntityModify(boolean customEntityModify) {
		this.customEntityModify = customEntityModify;
	}
	public String getExternalCustomEntitySpecId() {
		return externalCustomEntitySpecId;
	}
	public void setExternalCustomEntitySpecId(String externalCustomEntitySpecId) {
		this.externalCustomEntitySpecId = externalCustomEntitySpecId;
	}
	public String getExternalCustomEntityId() {
		return externalCustomEntityId;
	}
	public void setExternalCustomEntityId(String externalCustomEntityId) {
		this.externalCustomEntityId = externalCustomEntityId;
	}
	public Long getCustomEntitySpecId() {
		return customEntitySpecId;
	}
	public void setCustomEntitySpecId(Long customEntitySpecId) {
		this.customEntitySpecId = customEntitySpecId;
	}
	public String getApiEntityIdFieldLabel() {
		return apiEntityIdFieldLabel;
	}
	public void setApiEntityIdFieldLabel(String apiEntityIdFieldLabel) {
		this.apiEntityIdFieldLabel = apiEntityIdFieldLabel;
	}
	public String getModifyStatus() {
		return modifyStatus;
	}
	public void setModifyStatus(String modifyStatus) {
		this.modifyStatus = modifyStatus;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public boolean isWorkSharingSetUpDone() {
		return workSharingSetUpDone;
	}
	public void setWorkSharingSetUpDone(boolean workSharingSetUpDone) {
		this.workSharingSetUpDone = workSharingSetUpDone;
	}
	public boolean isGmetCompanyBasedWorkCreation() {
		return gmetCompanyBasedWorkCreation;
	}
	public void setGmetCompanyBasedWorkCreation(boolean gmetCompanyBasedWorkCreation) {
		this.gmetCompanyBasedWorkCreation = gmetCompanyBasedWorkCreation;
	}
	public String getEmpGroupIdsToSendWorkInvitationForGmet() {
		return empGroupIdsToSendWorkInvitationForGmet;
	}
	public void setEmpGroupIdsToSendWorkInvitationForGmet(
			String empGroupIdsToSendWorkInvitationForGmet) {
		this.empGroupIdsToSendWorkInvitationForGmet = empGroupIdsToSendWorkInvitationForGmet;
	}
	public boolean isIncludeWorkInvitations() {
		return includeWorkInvitations;
	}
	public void setIncludeWorkInvitations(boolean includeWorkInvitations) {
		this.includeWorkInvitations = includeWorkInvitations;
	}
	public boolean isIgnoreComputedFieldsComputation() {
		return ignoreComputedFieldsComputation;
	}
	public void setIgnoreComputedFieldsComputation(boolean ignoreComputedFieldsComputation) {
		this.ignoreComputedFieldsComputation = ignoreComputedFieldsComputation;
	}
	public boolean isAssignToChanged() {
		return isAssignToChanged;
	}
	public void setAssignToChanged(boolean isAssignToChanged) {
		this.isAssignToChanged = isAssignToChanged;
	}
	public String getOldAssignTo() {
		return oldAssignTo;
	}
	public void setOldAssignTo(String oldAssignTo) {
		this.oldAssignTo = oldAssignTo;
	}
	public String getExternalFormSpecId() {
		return externalFormSpecId;
	}
	public void setExternalFormSpecId(String externalFormSpecId) {
		this.externalFormSpecId = externalFormSpecId;
	}
	public String getExternalFormId() {
		return externalFormId;
	}
	public void setExternalFormId(String externalFormId) {
		this.externalFormId = externalFormId;
	}
	public String getSendForApproval() {
		return sendForApproval;
	}
	public void setSendForApproval(String sendForApproval) {
		this.sendForApproval = sendForApproval;
	}
	public int getDraftForm() {
		return draftForm;
	}
	public void setDraftForm(int draftForm) {
		this.draftForm = draftForm;
	}
	public long getBulkUploadId() {
		return bulkUploadId;
	}
	public void setBulkUploadId(long bulkUploadId) {
		this.bulkUploadId = bulkUploadId;
	}

	public long getTatTime() {
		return tatTime;
	}
	public void setTatTime(long tatTime) {
		this.tatTime = tatTime;
	}
	public boolean isTicketingForm() {
		return ticketingForm;
	}
	public void setTicketingForm(boolean ticketingForm) {
		this.ticketingForm = ticketingForm;
	}
	public Integer getAssignmentType() {
		return assignmentType;
	}
	public void setAssignmentType(Integer assignmentType) {
		this.assignmentType = assignmentType;
	}
	public String getWorkInvitationEmpIds() {
		return workInvitationEmpIds;
	}
	public void setWorkInvitationEmpIds(String workInvitationEmpIds) {
		this.workInvitationEmpIds = workInvitationEmpIds;
	}
	public boolean isMosSpec() {
		return mosSpec;
	}
	public void setMosSpec(boolean mosSpec) {
		this.mosSpec = mosSpec;
	}
	public String getExistingWorkId() {
		return existingWorkId;
	}
	public void setExistingWorkId(String existingWorkId) {
		this.existingWorkId = existingWorkId;
	}
	public Integer getSourceOfAction() {
		return sourceOfAction;
	}
	public void setSourceOfAction(Integer sourceOfAction) {
		this.sourceOfAction = sourceOfAction;
	}
	public boolean isSkipValidation() {
		return skipValidation;
	}
	public void setSkipValidation(boolean skipValidation) {
		this.skipValidation = skipValidation;
	}
	public String getSoruceOfActionString() {
		return soruceOfActionString;
	}
	public void setSoruceOfActionString(String soruceOfActionString) {
		this.soruceOfActionString = soruceOfActionString;
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
	
	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
		}
	public String getWorkActionSpecId() {
		return workActionSpecId;
	}
	public void setWorkActionSpecId(String workActionSpecId) {
		this.workActionSpecId = workActionSpecId;
	}
	public boolean isBulkAction() {
		return bulkAction;
	}
	public void setBulkAction(boolean bulkAction) {
		this.bulkAction = bulkAction;
	}
	public String getExternalIdPrefix() {
		return externalIdPrefix;
	}
	public void setExternalIdPrefix(String externalIdPrefix) {
		this.externalIdPrefix = externalIdPrefix;
	}
	public Integer getSourceOfModification() {
		return sourceOfModification;
	}
	public void setSourceOfModification(Integer sourceOfModification) {
		this.sourceOfModification = sourceOfModification;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	} 
	public boolean isFromMigrationFields() {
		return fromMigrationFields;
	}
	public void setFromMigrationFields(boolean fromMigrationFields) {
		this.fromMigrationFields = fromMigrationFields;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getEmpSelectionType() {
		return empSelectionType;
	}
	public void setEmpSelectionType(Integer empSelectionType) {
		this.empSelectionType = empSelectionType;
	}
	public String getAllInvitaionEmpIds() {
		return allInvitaionEmpIds;
	}
	public void setAllInvitaionEmpIds(String allInvitaionEmpIds) {
		this.allInvitaionEmpIds = allInvitaionEmpIds;
	}
	public boolean isFeedbackForm() {
		return feedbackForm;
	}
	public void setFeedbackForm(boolean feedbackForm) {
		this.feedbackForm = feedbackForm;
	}
	public boolean isSkipSendJmsMessage() {
		return skipSendJmsMessage;
	}
	public void setSkipSendJmsMessage(boolean skipSendJmsMessage) {
		this.skipSendJmsMessage = skipSendJmsMessage;
	}
	public String getCustomEntityFieldsUniqueKey() {
		return customEntityFieldsUniqueKey;
	}
	public void setCustomEntityFieldsUniqueKey(String customEntityFieldsUniqueKey) {
		this.customEntityFieldsUniqueKey = customEntityFieldsUniqueKey;
	}
	public String getHiddenFieldsMapJson() {
		return hiddenFieldsMapJson;
	}
	public void setHiddenFieldsMapJson(String hiddenFieldsMapJson) {
		this.hiddenFieldsMapJson = hiddenFieldsMapJson;
	}
	public String getHiddenSectionsJson() {
		return hiddenSectionsJson;
	}
	public void setHiddenSectionsJson(String hiddenSectionsJson) {
		this.hiddenSectionsJson = hiddenSectionsJson;
	}
	public String getFormFieldsUniqueKey() {
		return formFieldsUniqueKey;
	}
	public void setFormFieldsUniqueKey(String formFieldsUniqueKey) {
		this.formFieldsUniqueKey = formFieldsUniqueKey;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public boolean isSkipDateTimeFields() {
		return skipDateTimeFields;
	}
	public void setSkipDateTimeFields(boolean skipDateTimeFields) {
		this.skipDateTimeFields = skipDateTimeFields;
	}
	public Long getTriggerFormId() {
		return triggerFormId;
	}
	public void setTriggerFormId(Long triggerFormId) {
		this.triggerFormId = triggerFormId;
	}
	
	
	
}
