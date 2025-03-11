package com.effort.entity;



import com.effort.util.Api;
import com.effort.util.Log;

public class Workflow {
	private Long workflowId;
	private String workflowName;
	private long formSpecId;
	private String formSpecUniqueId;
	private boolean editable;
	private int companyId;
	private boolean deleted;
	private long createdBy;
	private long modifiedBy;
	private String createdTime;
	private String modifiedTime;
	private boolean hasRoleBasedStages;
	private String createdByName;
	private int isSystemDefined;
	private Long skeletonWorkFlowId;
	private boolean canRejectAfterApproval;
	private boolean canEditAfterSubmission;
	private boolean reintiateApprovedFormAfterModify;
	
	public Long getWorkflowId() {
	    return workflowId;
	}
	public void setWorkflowId(Long workflowId) {
	    this.workflowId = workflowId;
	}
	public String getWorkflowName() {
	    return workflowName;
	}
	public void setWorkflowName(String workflowName) {
	    this.workflowName = workflowName;
	}
	public long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
	
	
	public boolean isHasRoleBasedStages() {
		return hasRoleBasedStages;
	}
	public void setHasRoleBasedStages(boolean hasRoleBasedStages) {
		this.hasRoleBasedStages = hasRoleBasedStages;
	}
	public String getFormSpecUniqueId() {
		return formSpecUniqueId;
	}
	public void setFormSpecUniqueId(String formSpecUniqueId) {
		this.formSpecUniqueId = formSpecUniqueId;
	}
	public void toModifiedTimeXSD(){
		if(!Api.isEmptyString(modifiedTime) && !modifiedTime.endsWith("M")){
			try {
			    	modifiedTime = Api.toUtcXsd(modifiedTime);
			} catch (Exception e) {
				Log.info(this.getClass(), e.toString());
			}
		}
	}
	
	public void toCreatedTimeXSD(){
		if(!Api.isEmptyString(createdTime) && !createdTime.endsWith("M")){
			try {
			    	createdTime = Api.toUtcXsd(createdTime);
			} catch (Exception e) {
				Log.info(this.getClass(), e.toString());
			}
		}
	}
	public String getCreatedByName() {
	    return createdByName;
	}
	public void setCreatedByName(String createdByName) {
	    this.createdByName = createdByName;
	}
	public int getIsSystemDefined() {
		return isSystemDefined;
	}
	public void setIsSystemDefined(int isSystemDefined) {
		this.isSystemDefined = isSystemDefined;
	}
	public Long getSkeletonWorkFlowId() {
		return skeletonWorkFlowId;
	}
	public void setSkeletonWorkFlowId(Long skeletonWorkFlowId) {
		this.skeletonWorkFlowId = skeletonWorkFlowId;
	}
	public boolean isCanRejectAfterApproval() {
		return canRejectAfterApproval;
	}
	public void setCanRejectAfterApproval(boolean canRejectAfterApproval) {
		this.canRejectAfterApproval = canRejectAfterApproval;
	}
	public boolean isCanEditAfterSubmission() {
		return canEditAfterSubmission;
	}
	public void setCanEditAfterSubmission(boolean canEditAfterSubmission) {
		this.canEditAfterSubmission = canEditAfterSubmission;
	}
	public boolean isReintiateApprovedFormAfterModify() {
		return reintiateApprovedFormAfterModify;
	}
	public void setReintiateApprovedFormAfterModify(boolean reintiateApprovedFormAfterModify) {
		this.reintiateApprovedFormAfterModify = reintiateApprovedFormAfterModify;
	}
	
}
