package com.effort.entity;


import java.util.List;

public class WorkOnDemandMapping {

	private long id;
	private String formSpecUniqueId;
	private Integer companyId;
	private Integer triggerEvent; 
	private long createdBy;
	private long modifiedBy;
	private String createdTime;
	private String modifiedTime;
	private String mapToOpp;
	private boolean updateWorkOnModify;
	private Integer updateTriggerEvent;
	private String updateColumnUniqueId;
	private int changeType;
	private String workRequisitionVisibilityConditionJson;
	private List<WorkRequisitionDependencyCriterias> workRequisitionVisibilityCriterias;
	private String workSpecId;
	public static final int CREATE = 1;
	public static final int UPDATE = 2;
	private boolean sendPublicFormForApproval;
	private boolean sectionAutoFill;
	private List<WorkOnDemandMappingSectionConfiguration> workOnDemandMappingSectionConfiguration;
	private boolean showEmpActivity;
	private boolean enableBulkApproval;
	private int entityType;

	public static final int CUSTOMER_FIELD_TYPE = 7;
	public static final int ENTITY_TYPE_FORM = 1;
	public static final int ENTITY_TYPE_CUSTOMER = 2;
	
	public int getChangeType() {
		return changeType;
	}
	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}
	public String getUpdateColumnUniqueId() {
		return updateColumnUniqueId;
	}
	public void setUpdateColumnUniqueId(String updateColumnUniqueId) {
		this.updateColumnUniqueId = updateColumnUniqueId;
	}
	private List<WorkAndFieldSpecsOnDemandConfig> workAndFieldSpecsOnDemandConfigList;
	private List<CustomerEmpMappingOnDemand> customerEmpMappingOnDemandList;
	
	public static final int TRIGGER_EVENT_TYPE_APPROVED_FORM = 1;
	public static final int TRIGGER_EVENT_TYPE_SUBMITTED_FORM = 2;
	public static final int TRIGGER_EVENT_TYPE_MODIFIED_FORM = 3;
	public static final int UNPROCESSED_STATUS = 0;
	public static final int UNPROCESSED_STATUS_TEMPORARY = 99;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public List<WorkAndFieldSpecsOnDemandConfig> getWorkAndFieldSpecsOnDemandConfigList() {
		return workAndFieldSpecsOnDemandConfigList;
	}
	public void setWorkAndFieldSpecsOnDemandConfigList(
			List<WorkAndFieldSpecsOnDemandConfig> workAndFieldSpecsOnDemandConfigList) {
		this.workAndFieldSpecsOnDemandConfigList = workAndFieldSpecsOnDemandConfigList;
	}
	public List<CustomerEmpMappingOnDemand> getCustomerEmpMappingOnDemandList() {
		return customerEmpMappingOnDemandList;
	}
	public void setCustomerEmpMappingOnDemandList(List<CustomerEmpMappingOnDemand> customerEmpMappingOnDemandList) {
		this.customerEmpMappingOnDemandList = customerEmpMappingOnDemandList;
	}
	public String getMapToOpp() {
		return mapToOpp;
	}
	public void setMapToOpp(String mapToOpp) {
		this.mapToOpp = mapToOpp;
	}
	public Integer getTriggerEvent() {
		return triggerEvent;
	}
	public void setTriggerEvent(Integer triggerEvent) {
		this.triggerEvent = triggerEvent;
	}
	public Integer getUpdateTriggerEvent() {
		return updateTriggerEvent;
	}
	public void setUpdateTriggerEvent(Integer updateTriggerEvent) {
		this.updateTriggerEvent = updateTriggerEvent;
	}
	public boolean isUpdateWorkOnModify() {
		return updateWorkOnModify;
	}
	public void setUpdateWorkOnModify(boolean updateWorkOnModify) {
		this.updateWorkOnModify = updateWorkOnModify;
	}
	public String getWorkRequisitionVisibilityConditionJson() {
		return workRequisitionVisibilityConditionJson;
	}
	public void setWorkRequisitionVisibilityConditionJson(String workRequisitionVisibilityConditionJson) {
		this.workRequisitionVisibilityConditionJson = workRequisitionVisibilityConditionJson;
	}
	public List<WorkRequisitionDependencyCriterias> getWorkRequisitionVisibilityCriterias() {
		return workRequisitionVisibilityCriterias;
	}
	public void setWorkRequisitionVisibilityCriterias(
			List<WorkRequisitionDependencyCriterias> workRequisitionVisibilityCriterias) {
		this.workRequisitionVisibilityCriterias = workRequisitionVisibilityCriterias;
	}
	public String getWorkSpecId() {
		return workSpecId;
	}
	public void setWorkSpecId(String workSpecId) {
		this.workSpecId = workSpecId;
	}
	public boolean isSendPublicFormForApproval() {
		return sendPublicFormForApproval;
	}
	public void setSendPublicFormForApproval(boolean sendPublicFormForApproval) {
		this.sendPublicFormForApproval = sendPublicFormForApproval;
	}
	public List<WorkOnDemandMappingSectionConfiguration> getWorkOnDemandMappingSectionConfiguration() {
		return workOnDemandMappingSectionConfiguration;
	}
	public void setWorkOnDemandMappingSectionConfiguration(
			List<WorkOnDemandMappingSectionConfiguration> workOnDemandMappingSectionConfiguration) {
		this.workOnDemandMappingSectionConfiguration = workOnDemandMappingSectionConfiguration;
	}
	public boolean isSectionAutoFill() {
		return sectionAutoFill;
	}
	public void setSectionAutoFill(boolean sectionAutoFill) {
		this.sectionAutoFill = sectionAutoFill;
	}
	public boolean isShowEmpActivity() {
		return showEmpActivity;
	}
	public void setShowEmpActivity(boolean showEmpActivity) {
		this.showEmpActivity = showEmpActivity;
	}
	public boolean isEnableBulkApproval() {
		return enableBulkApproval;
	}
	public void setEnableBulkApproval(boolean enableBulkApproval) {
		this.enableBulkApproval = enableBulkApproval;
	}
	public int getEntityType() {
		return entityType;
	}
	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}
	

}
