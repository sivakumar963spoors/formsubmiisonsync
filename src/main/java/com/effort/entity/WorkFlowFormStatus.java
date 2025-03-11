package com.effort.entity;



import java.text.ParseException;

import com.effort.util.Api;
import com.effort.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class WorkFlowFormStatus implements Cloneable {
	public static short STATUS_TYPE_REJECTED=-1;
	public static short STATUS_TYPE_APPROVED=1;
	public static short STATUS_TYPE_WAITING=0;
	public static short STATUS_TYPE_RESUBMITTED=2;
	public static short STATUS_TYPE_CANCELED=3;
	public static short NEXT_MANAGER_ROLE=4;
	public static short PREVIOUS_MANAGER_ROLE=5;
	public static short FIANL_APPROVER_ROLE=6;
	
	@JsonProperty(value="workflowStatusId")
	private Long id;
	private Long formSpecId;
	private String formSpec;
	private Long formId;
	private String clientFormId;
	private String formIdentifier;
	private Long submittedBy;
	private String empName;
	private Long empId;
	private String submittedTime;
	private Long approvedBy;
	private String approvedTime;
	private Long workFlowId;
	@JsonProperty("clientWorkflowStatusId")
	private String clientSideId;
	
	private Long workFlowStageId;
	private String statusMessage;
	private Short status;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String createdTime;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String modifiedTime;
	private Integer currentRank;
	private Integer nextRank;
	private Integer previousRank;
	private Integer previousStage;
	private String empgroupId;
	private String remarks;
	
	private String submitedEmp;
	private String stageName;
	private String workFlowName;
	private String filledByTzo;
	private String tzo;
	
	private String submittedName;
	
	private Integer finalApproverRank;
	
	private String submittedByName;
	private String approvedByName;
	private String clientCode;
	private Long auditId;
	private Long managerRank;
	private String managerCsvRanks;
   // private String formSpecUniqueId;
    private Long stageType;
    private Long historyId;
    private String formTitle;
    private boolean editable;
    private int submiterRank;
    @JsonProperty(access = Access.WRITE_ONLY)
    private int approvalMode;
    @JsonProperty(access = Access.WRITE_ONLY)
    private int finalApprovalRank;
    private Integer canApprove = 0;
    
    private int onlyWithHierarchy;
    
    private String previousApprovedByIds;
    private boolean canRejectAfterApproval;
    private boolean canEditAfterSubmission;
    private boolean reintiateApprovedFormAfterModify;
    private boolean triggerJms = true;
    
    private String clientModifiedTime;
    private String noOfDaysPendingFrom;
    private String statusMessageColor;
    private String uniqueId;
    private boolean removeWorkStatus;
    private int typeOfApproval;
    private String approvalType;
    private int count;
    private String actionableEmpIds;
    private int considerEveryoneInGroup;
    private boolean alreadyApproved = false;
    private String parallelApprovalRoleIds;
    private boolean isNextStageEvaluated;
    private boolean teamApproval;
    private int teamCount;
    private Long teamFormId;
    private String filledByName;

    public final static String REJECTED_BY_SYSTEM_STATUS_MESSAGE = "Rejected by the system : Employee Hirarchy modified";
    
	public int getApprovalMode() {
		return approvalMode;
	}
	public void setApprovalMode(int approvalMode) {
		this.approvalMode = approvalMode;
	}
	
	public int getFinalApprovalRank() {
		return finalApprovalRank;
	}
	public void setFinalApprovalRank(int finalApprovalRank) {
		this.finalApprovalRank = finalApprovalRank;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public String getFormTitle() {
		return formTitle;
	}
	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}
	public Long getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}
	public Long getStageType() {
		return stageType;
	}
	public void setStageType(Long stageType) {
		this.stageType = stageType;
	}
	public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	
	public Integer getIsInGroup() {
		return isInGroup;
	}
	public void setIsInGroup(Integer isInGroup) {
		this.isInGroup = isInGroup;
	}
	private Integer isInGroup;
		
	public Integer getFinalApproverRank() {
		return finalApproverRank;
	}
	public void setFinalApproverRank(Integer finalApproverRank) {
		this.finalApproverRank = finalApproverRank;
	}
	public String getSubmittedName() {
		return submittedName;
	}
	public void setSubmittedName(String submittedName) {
		this.submittedName = submittedName;
	}
	public String getWorkFlowName() {
		return workFlowName;
	}
	public void setWorkFlowName(String workFlowName) {
		this.workFlowName = workFlowName;
	}
	public String getSubmitedEmp() {
		return submitedEmp;
	}
	public void setSubmitedEmp(String submitedEmp) {
		this.submitedEmp = submitedEmp;
	}
	
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
	
	public String getTzo() {
	    return tzo;
	}
	public void setTzo(String tzo) {
	    this.tzo = tzo;
	}
    public String getClientFormId() {
		return clientFormId;
	}
	public void setClientFormId(String clientFormId) {
		this.clientFormId = clientFormId;
	}
	//	public String getClientSideFormId() {
//		return clientSideFormId;
//	}
//	public void setClientSideFormId(String clientSideFormId) {
//		this.clientSideFormId = clientSideFormId;
//	}
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
	
	public Long getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
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
	
	public String getApprovedTime() {
		return approvedTime;
	}
	public void setApprovedTime(String approvedTime) {
		this.approvedTime = approvedTime;
	}
	
	public String getSubmittedTime() {
		return submittedTime;
	}
	public void setSubmittedTime(String submittedTime) {
		
		this.submittedTime = submittedTime;
	}
	
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
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
	
	
	
	public String getSubmittedTimeLTZ(){
	    return Api.getTimeZoneDatesInLTZ(submittedTime, tzo, filledByTzo+"");
	}
	
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	//@JsonProperty(access = Access.WRITE_ONLY)
	public String getApprovedTimeLTZ(){
	    return Api.getTimeZoneDatesInLTZ(approvedTime, tzo, filledByTzo+"");
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
	public String getFilledByTzo() {
		return filledByTzo;
	}
	public void setFilledByTzo(String filledByTzo) {
		this.filledByTzo = filledByTzo;
	}

	
	public int getSubmiterRank() {
		return submiterRank;
	}
	public void setSubmiterRank(int submiterRank) {
		this.submiterRank = submiterRank;
	}
	@JsonIgnore
	public String getClientSideId() {
		return clientSideId;
	}
	@JsonIgnore
	public void setClientSideId(String clientSideId) {
		this.clientSideId = clientSideId;
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
	public Integer getCanApprove() {
		return canApprove;
	}
	public void setCanApprove(Integer canApprove) {
		this.canApprove = canApprove;
	}
	public String getFormSpec() {
		return formSpec;
	}
	public void setFormSpec(String formSpec) {
		this.formSpec = formSpec;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public int getOnlyWithHierarchy() {
		return onlyWithHierarchy;
	}
	public void setOnlyWithHierarchy(int onlyWithHierarchy) {
		this.onlyWithHierarchy = onlyWithHierarchy;
	}
	public String getPreviousApprovedByIds() {
		return previousApprovedByIds;
	}
	public void setPreviousApprovedByIds(String previousApprovedByIds) {
		this.previousApprovedByIds = previousApprovedByIds;
	}
	
	public int getPendingFrom() throws ParseException{
	    try{
	    	int days = Api.getDaysBetweenTwoDates(getSubmittedTimeLTZ(),Api.getCurrentTimeTz24New());
		    if(days > 0)
		    {
		    	return days;
		    }
	    }catch(Exception e){
	    	
	    }
	    return 0;
	}
	
	@Override
	public String toString() {
		return "WorkFlowFormStatus [id=" + id + ", formSpecId=" + formSpecId + ", formSpec=" + formSpec + ", formId="
				+ formId + ", clientFormId=" + clientFormId + ", formIdentifier=" + formIdentifier + ", submittedBy="
				+ submittedBy + ", empName=" + empName + ", empId=" + empId + ", submittedTime=" + submittedTime
				+ ", approvedBy=" + approvedBy + ", approvedTime=" + approvedTime + ", workFlowId=" + workFlowId
				+ ", clientSideId=" + clientSideId + ", workFlowStageId=" + workFlowStageId + ", statusMessage="
				+ statusMessage + ", status=" + status + ", createdTime=" + createdTime + ", modifiedTime="
				+ modifiedTime + ", currentRank=" + currentRank + ", nextRank=" + nextRank + ", previousRank="
				+ previousRank + ", previousStage=" + previousStage + ", empgroupId=" + empgroupId + ", remarks="
				+ remarks + ", submitedEmp=" + submitedEmp + ", stageName=" + stageName + ", workFlowName="
				+ workFlowName + ", filledByTzo=" + filledByTzo + ", tzo=" + tzo + ", submittedName=" + submittedName
				+ ", finalApproverRank=" + finalApproverRank + ", submittedByName=" + submittedByName
				+ ", approvedByName=" + approvedByName + ", clientCode=" + clientCode + ", auditId=" + auditId
				+ ", managerRank=" + managerRank + ", managerCsvRanks=" + managerCsvRanks + ", stageType=" + stageType
				+ ", historyId=" + historyId + ", formTitle=" + formTitle + ", editable=" + editable + ", submiterRank="
				+ submiterRank + ", approvalMode=" + approvalMode + ", finalApprovalRank=" + finalApprovalRank
				+ ", canApprove=" + canApprove + ", onlyWithHierarchy=" + onlyWithHierarchy + ", previousApprovedByIds="
				+ previousApprovedByIds + ", isInGroup=" + isInGroup + "]";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	public boolean isCanRejectAfterApproval() {
		return canRejectAfterApproval;
	}
	public void setCanRejectAfterApproval(boolean canRejectAfterApproval) {
		this.canRejectAfterApproval = canRejectAfterApproval;
	}
	@JsonIgnore
	public boolean isCanEditAfterSubmission() {
		return canEditAfterSubmission;
	}
	@JsonIgnore
	public void setCanEditAfterSubmission(boolean canEditAfterSubmission) {
		this.canEditAfterSubmission = canEditAfterSubmission;
	}
	@JsonIgnore
	public boolean isReintiateApprovedFormAfterModify() {
		return reintiateApprovedFormAfterModify;
	}
	@JsonIgnore
	public void setReintiateApprovedFormAfterModify(boolean reintiateApprovedFormAfterModify) {
		this.reintiateApprovedFormAfterModify = reintiateApprovedFormAfterModify;
	}
	public String getClientModifiedTime() {
		return clientModifiedTime;
	}
	public void setClientModifiedTime(String clientModifiedTime) {
		this.clientModifiedTime = clientModifiedTime;
	}
	@JsonIgnore
	public boolean isTriggerJms() {
		return triggerJms;
	}
	@JsonIgnore
	public void setTriggerJms(boolean triggerJms) {
		this.triggerJms = triggerJms;
	}
	public String getNoOfDaysPendingFrom() {
		return noOfDaysPendingFrom;
	}
	public void setNoOfDaysPendingFrom(String noOfDaysPendingFrom) {
		this.noOfDaysPendingFrom = noOfDaysPendingFrom;
	}
	public String getStatusMessageColor() {
		return statusMessageColor;
	}
	public void setStatusMessageColor(String statusMessageColor) {
		this.statusMessageColor = statusMessageColor;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public boolean isRemoveWorkStatus() {
		return removeWorkStatus;
	}
	public void setRemoveWorkStatus(boolean removeWorkStatus) {
		this.removeWorkStatus = removeWorkStatus;
	}
	public int getTypeOfApproval() {
		return typeOfApproval;
	}
	public void setTypeOfApproval(int typeOfApproval) {
		this.typeOfApproval = typeOfApproval;
	}
	public String getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getActionableEmpIds() {
		return actionableEmpIds;
	}
	public void setActionableEmpIds(String actionableEmpIds) {
		this.actionableEmpIds = actionableEmpIds;
	}
	public int getConsiderEveryoneInGroup() {
		return considerEveryoneInGroup;
	}
	public void setConsiderEveryoneInGroup(int considerEveryoneInGroup) {
		this.considerEveryoneInGroup = considerEveryoneInGroup;
	}
	public boolean isAlreadyApproved() {
		return alreadyApproved;
	}
	public void setAlreadyApproved(boolean alreadyApproved) {
		this.alreadyApproved = alreadyApproved;
	}
	public String getParallelApprovalRoleIds() {
		return parallelApprovalRoleIds;
	}
	public void setParallelApprovalRoleIds(String parallelApprovalRoleIds) {
		this.parallelApprovalRoleIds = parallelApprovalRoleIds;
	}
	public boolean isNextStageEvaluated() {
		return isNextStageEvaluated;
	}
	public void setNextStageEvaluated(boolean isNextStageEvaluated) {
		this.isNextStageEvaluated = isNextStageEvaluated;
	}
	public boolean isTeamApproval() {
		return teamApproval;
	}
	public void setTeamApproval(boolean teamApproval) {
		this.teamApproval = teamApproval;
	}
	public int getTeamCount() {
		return teamCount;
	}
	public void setTeamCount(int teamCount) {
		this.teamCount = teamCount;
	}
	public String getFilledByName() {
		return filledByName;
	}
	public void setFilledByName(String filledByName) {
		this.filledByName = filledByName;
	}
	public Long getTeamFormId() {
		return teamFormId;
	}
	public void setTeamFormId(Long teamFormId) {
		this.teamFormId = teamFormId;
	}
	
	
	

}
