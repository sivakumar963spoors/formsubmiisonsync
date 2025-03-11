package com.effort.entity;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class WorkflowEntityMap implements Serializable {
	public static int WORKFLOW_ENTITY_TYPE_FORM=1;
	public static int WORKFLOW_ENTITY_TYPE_JOB=2;
	public static int WORKFLOW_ENABLED=1;
	
	private Long id;
	private Long workflowId;
	private String entityId;
	private int entityType;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String modifiedTime;
	private String extraId;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String workflowName;
	private int isEnabled;
	
	private String visibilityConditionJson;
	private long oldId;
	private int defaultWorkFlow;
	private boolean showEmpActivity;
	private boolean enableBulkApproval;
    
	
	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public int getEntityType() {
		return entityType;
	}

	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	public String getExtraId() {
		return extraId;
	}

	public void setExtraId(String extraId) {
		this.extraId = extraId;
	}
	
	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public int getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(int isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getVisibilityConditionJson() {
		return visibilityConditionJson;
	}

	public void setVisibilityConditionJson(String visibilityConditionJson) {
		this.visibilityConditionJson = visibilityConditionJson;
	}

	public long getOldId() {
		return oldId;
	}

	public void setOldId(long oldId) {
		this.oldId = oldId;
	}
	

	public int getDefaultWorkFlow() {
		return defaultWorkFlow;
	}

	public void setDefaultWorkFlow(int defaultWorkFlow) {
		this.defaultWorkFlow = defaultWorkFlow;
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

	@Override
	public String toString() {
		return "WorkflowEntityMap [id=" + id + ", workflowId=" + workflowId + ", entityId=" + entityId + ", entityType="
				+ entityType + ", modifiedTime=" + modifiedTime + ", extraId=" + extraId + ", workflowName="
				+ workflowName + ", isEnabled=" + isEnabled + ", visibilityConditionJson=" + visibilityConditionJson
				+ ", oldId=" + oldId + ", defaultWorkFlow=" + defaultWorkFlow + "]";
	}
	
}
