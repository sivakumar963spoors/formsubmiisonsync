package com.effort.entity;

import com.effort.util.Api;
import com.effort.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class WorkFlowFormStatusHistory {
	@JsonProperty(value="workflowStatusHistoryId",access = Access.WRITE_ONLY)
	private Long id;
	private Long formSpecId;
	private Long formId;
	private String formIdentifier;
	private Long submittedBy;
	private String submittedTime;
	private Long approvedBy;
	private String approvedTime;
	private Long workFlowId;
	
	private Long workFlowStageId;
	private String statusMessage;
	private Short status;
	private String createdTime;
	private String modifiedTime;
	private Integer currentRank;
	private Integer nextRank;
	private Integer previousRank;
	private Integer previousStage;
	private String empgroupId;
	private String remarks;
	private Long formAuditId;
	private String stageName;
	
	private String approvedEmp;
	private String workFlowStage;
	private String filledByTzo;
	private String submittedByName;
	private String approvedByName;
	private String tzo;
	private Long auditId;
	private Long managerRank;
	private String managerCsvRanks;
	
	private int onlyWithHierarchy;
	private String timeLineTitle;
	private int considerEveryoneInGroup;
	
	private boolean invalidateHistory;
	
	public Long getManagerRank() {
		return managerRank;
	}
	public void setManagerRank(Long managerRank) {
		this.managerRank = managerRank;
	}
	public String getManagerCsvRanks() {
		return managerCsvRanks;
	}
	public void setManagerCsvRanks(String managerCsvRanks) {
		this.managerCsvRanks = managerCsvRanks;
	}
	public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	public String getWorkFlowStage() {
		return workFlowStage;
	}
	public void setWorkFlowStage(String workFlowStage) {
		this.workFlowStage = workFlowStage;
	}
	public String getApprovedEmp() {
		return approvedEmp;
	}
	public void setApprovedEmp(String approvedEmp) {
		this.approvedEmp = approvedEmp;
	}
	@JsonIgnore
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFormSpecId() {
		return formSpecId;
	}
	public void setFormSpecId(Long formSpecId) {
		this.formSpecId = formSpecId;
	}
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public String getFormIdentifier() {
		return formIdentifier;
	}
	public void setFormIdentifier(String formIdentifier) {
		this.formIdentifier = formIdentifier;
	}
	public Long getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(Long submittedBy) {
		this.submittedBy = submittedBy;
	}
	public String getSubmittedTime() {
		return submittedTime;
	}
	public void setSubmittedTime(String submittedTime) {
		this.submittedTime = submittedTime;
	}
	public Long getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getApprovedTime() {
		return approvedTime;
	}
	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}
	public Long getWorkFlowId() {
		return workFlowId;
	}
	public void setWorkFlowId(Long workFlowId) {
		this.workFlowId = workFlowId;
	}
	public Long getWorkFlowStageId() {
		return workFlowStageId;
	}
	public void setWorkFlowStageId(Long workFlowStageId) {
		this.workFlowStageId = workFlowStageId;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
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
	public Integer getCurrentRank() {
		return currentRank;
	}
	public void setCurrentRank(Integer currentRank) {
		this.currentRank = currentRank;
	}
	public Integer getNextRank() {
		return nextRank;
	}
	public void setNextRank(Integer nextRank) {
		this.nextRank = nextRank;
	}
	public Integer getPreviousRank() {
		return previousRank;
	}
	public void setPreviousRank(Integer previousRank) {
		this.previousRank = previousRank;
	}
	public Integer getPreviousStage() {
		return previousStage;
	}
	public void setPreviousStage(Integer previousStage) {
		this.previousStage = previousStage;
	}
	public String getEmpgroupId() {
		return empgroupId;
	}
	public void setEmpgroupId(String empgroupId) {
		this.empgroupId = empgroupId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getTzo() {
	    return tzo;
	}
	public void setTzo(String tzo) {
	    this.tzo = tzo;
	}
	
	public String getFilledByTzo() {
		return filledByTzo;
	}
	public void setFilledByTzo(String filledByTzo) {
		this.filledByTzo = filledByTzo;
	}
	public Long getFormAuditId() {
		return formAuditId;
	}
	public void setFormAuditId(Long formAuditId) {
		this.formAuditId = formAuditId;
	}
	
	public String getSubmittedByName() {
		return submittedByName;
	}
	public void setSubmittedByName(String submittedByName) {
		this.submittedByName = submittedByName;
	}
	public String getApprovedByName() {
		return approvedByName;
	}
	public void setApprovedByName(String approvedByName) {
		this.approvedByName = approvedByName;
	}
	
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
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
	
	public void toSubmittedTimeXSD(){
		if(!Api.isEmptyString(submittedTime) && !submittedTime.endsWith("M")){
			try {
				submittedTime = Api.toUtcXsd(submittedTime);
			} catch (Exception e) {
				Log.info(this.getClass(), e.toString());
			}
		}
	}
	
	public void toApprovedTimeXSD(){
		if(!Api.isEmptyString(approvedTime) && !approvedTime.endsWith("M")){
			try {
				approvedTime = Api.toUtcXsd(approvedTime);
			} catch (Exception e) {
				Log.info(this.getClass(), e.toString());
			}
		}
	}
	
	public int getOnlyWithHierarchy() {
		return onlyWithHierarchy;
	}
	public void setOnlyWithHierarchy(int onlyWithHierarchy) {
		this.onlyWithHierarchy = onlyWithHierarchy;
	}
	public String getTimeLineTitle() {
		return timeLineTitle;
	}
	public void setTimeLineTitle(String timeLineTitle) {
		this.timeLineTitle = timeLineTitle;
	}
	public int getConsiderEveryoneInGroup() {
		return considerEveryoneInGroup;
	}
	public void setConsiderEveryoneInGroup(int considerEveryoneInGroup) {
		this.considerEveryoneInGroup = considerEveryoneInGroup;
	}
	public boolean isInvalidateHistory() {
		return invalidateHistory;
	}
	public void setInvalidateHistory(boolean invalidateHistory) {
		this.invalidateHistory = invalidateHistory;
	}
	
	
	
	/*@JsonIgnore
	public void setST(String tzo){
		if(submittedTime != null && submittedTime.length() > 0){
			submittedTime = Api.getTimeZoneDatesInDBFromDB(submittedTime, tzo, filledByTzo+"");
		}
	}
	
	@JsonIgnore
	public void setAT(String tzo){
		if(approvedTime != null && approvedTime.length() > 0){
			approvedTime = Api.getTimeZoneDatesInDBFromDB(approvedTime, tzo, filledByTzo+"");
		}
		
	}*/
	

}
