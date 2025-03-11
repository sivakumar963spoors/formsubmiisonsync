package com.effort.entity;



import java.util.List;

public class CustomEntityOnDemandMapping {
	
	private long id;
	private String formSpecUniqueId;
	private Long customEntitySpecId;
	private Integer companyId;
	private Integer triggerEvent; 
	private Integer updateTriggerEvent; 
	private long createdBy;
	private long modifiedBy;
	private String createdTime;
	private String modifiedTime;
	private String mapToOpp;
	private boolean updateCustomEntityOnModify;
	private int changeType;
	private String updateColumnUniqueId;
	private boolean sendPublicFormForApprovalForEntityReq;

	private List<CustomEntityAndFieldSpecOnDemandConfig> customEntityAndFieldSpecOnDemandConfig;
	private List<CustomEntityEmpMappingOnDemand> customEntityEmpMappingOnDemandList;
	
	public static final int TRIGGER_EVENT_TYPE_APPROVED_FORM = 1;
	public static final int TRIGGER_EVENT_TYPE_SUBMITTED_FORM = 2;
	public static final int UNPROCESSED_STATUS = 0;

	public static final int CREATE = 1;
	public static final int UPDATE = 2;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCustomEntitySpecId() {
		return customEntitySpecId;
	}

	public void setCustomEntitySpecId(Long customEntitySpecId) {
		this.customEntitySpecId = customEntitySpecId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getTriggerEvent() {
		return triggerEvent;
	}

	public void setTriggerEvent(Integer triggerEvent) {
		this.triggerEvent = triggerEvent;
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

	public String getMapToOpp() {
		return mapToOpp;
	}

	public void setMapToOpp(String mapToOpp) {
		this.mapToOpp = mapToOpp;
	}

	public List<CustomEntityAndFieldSpecOnDemandConfig> getCustomEntityAndFieldSpecOnDemandConfig() {
		return customEntityAndFieldSpecOnDemandConfig;
	}

	public void setCustomEntityAndFieldSpecOnDemandConfig(
			List<CustomEntityAndFieldSpecOnDemandConfig> customEntityAndFieldSpecOnDemandConfig) {
		this.customEntityAndFieldSpecOnDemandConfig = customEntityAndFieldSpecOnDemandConfig;
	}

	public List<CustomEntityEmpMappingOnDemand> getCustomEntityEmpMappingOnDemandList() {
		return customEntityEmpMappingOnDemandList;
	}

	public void setCustomEntityEmpMappingOnDemandList(
			List<CustomEntityEmpMappingOnDemand> customEntityEmpMappingOnDemandList) {
		this.customEntityEmpMappingOnDemandList = customEntityEmpMappingOnDemandList;
	}

	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}

	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	
	public boolean isUpdateCustomEntityOnModify() {
		return updateCustomEntityOnModify;
	}

	public void setUpdateCustomEntityOnModify(boolean updateCustomEntityOnModify) {
		this.updateCustomEntityOnModify = updateCustomEntityOnModify;
	}

	public Integer getUpdateTriggerEvent() {
		return updateTriggerEvent;
	}

	public void setUpdateTriggerEvent(Integer updateTriggerEvent) {
		this.updateTriggerEvent = updateTriggerEvent;
	}
	
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
	

	public boolean isSendPublicFormForApprovalForEntityReq() {
		return sendPublicFormForApprovalForEntityReq;
	}

	public void setSendPublicFormForApprovalForEntityReq(boolean sendPublicFormForApprovalForEntityReq) {
		this.sendPublicFormForApprovalForEntityReq = sendPublicFormForApprovalForEntityReq;
	}

	@Override
	public String toString() {
		return "CustomEntityOnDemandMapping [id=" + id + ", formSpecUniqueId=" + formSpecUniqueId
				+ ", customEntitySpecId=" + customEntitySpecId + ", companyId=" + companyId + ", triggerEvent="
				+ triggerEvent + ", updateTriggerEvent=" + updateTriggerEvent + ", createdBy=" + createdBy
				+ ", modifiedBy=" + modifiedBy + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime
				+ ", mapToOpp=" + mapToOpp + ", updateCustomEntityOnModify=" + updateCustomEntityOnModify
				+ ", changeType=" + changeType + ", updateColumnUniqueId=" + updateColumnUniqueId
				+ ", customEntityAndFieldSpecOnDemandConfig=" + customEntityAndFieldSpecOnDemandConfig
				+ ", sendPublicFormForApprovalForEntityReq=" + sendPublicFormForApprovalForEntityReq
				+ ", customEntityEmpMappingOnDemandList=" + customEntityEmpMappingOnDemandList + "]";
	}

}
