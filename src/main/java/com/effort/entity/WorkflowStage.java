package com.effort.entity;


import com.effort.util.Api;
import com.effort.util.Log;

public class WorkflowStage {
      public static final int STAGE_TYPE_ROLE_BASED =1;
      public static final int STAGE_TYPE_EMPLOYEE_GROUP_BASED =2;
      public static final int STAGE_TYPE_HIERARCHY_BASED =3;
      
      public static final int APPROVAL_FLOW_MODE_STRICT_HIERARCHY=1;
      public static final int APPROVAL_FLOW_MODE_DIRECT_APPROVAL=2;
      public static final int APPROVAL_FLOW_MODE_DIRECT_WITH_IMEDIATE=3;
      public static final int ONLY_IMMEDIATE_MANAGER=4;
      public static final int EVERYONE_IN_HIERARCHY_UP_TO_TOP_LEVEL=5;
      public static final int IMMIEDATE_MANAGER_CUSTOM_LEVEL=6;
      public static final int APPROVAL_FLOW_MODE_PARALLEL_APROVAL=7;

      
      public static final int NEXT_STAGE=1;
      public static final int PREVIOUS_STAGE=2;
      
      private Long stageId;
      private long workflowId;
      private int stageType;
      private String stageName;
      private int finalApproverRank;
      private int approvalMode;
      private String empGroupIds;
      private boolean deleted;
      private boolean canExecuteCustomCode;
      private String createdTime;
      private String modifiedTime;
      private String stageNameEmptyErrorMsg;
      private String empIdGroupIdsEmptyErrorMsg;
      private String noRolesFoundErrorMessage;
      private Long skeletonWorkFlowStageId;
      private int onlyWithHierarchy;
      private int autoApprovalDuration;
      private String autoApprovalDurationErrorMessage;
      private int autoApprovalDayOfMonth;
      private String autoApprovalDayOfMonthErrorMessage;
      private boolean endRuleType;
      private boolean initiatedFromRejectedConfiguration;
      private boolean goToFormSubmisson;
      private long mappedStageId;
      private Integer approvalModeLevel;
      private String approvalModeLevelErrorMessage;
      private int considerEveryoneInGroup;
      private int approvalType;
      private String parallelApprovalRoleIds;
      
	
	public int getAutoApprovalDayOfMonth() {
		return autoApprovalDayOfMonth;
	}
	public void setAutoApprovalDayOfMonth(int autoApprovalDayOfMonth) {
		this.autoApprovalDayOfMonth = autoApprovalDayOfMonth;
	}
	public String getAutoApprovalDayOfMonthErrorMessage() {
		return autoApprovalDayOfMonthErrorMessage;
	}
	public void setAutoApprovalDayOfMonthErrorMessage(String autoApprovalDayOfMonthErrorMessage) {
		this.autoApprovalDayOfMonthErrorMessage = autoApprovalDayOfMonthErrorMessage;
	}
	public String getAutoApprovalDurationErrorMessage() {
		return autoApprovalDurationErrorMessage;
	}
	public void setAutoApprovalDurationErrorMessage(String autoApprovalDurationErrorMessage) {
		this.autoApprovalDurationErrorMessage = autoApprovalDurationErrorMessage;
	}
	public boolean isCanExecuteCustomCode() {
		return canExecuteCustomCode;
	}
	public void setCanExecuteCustomCode(boolean canExecuteCustomCode) {
		this.canExecuteCustomCode = canExecuteCustomCode;
	}
	
	public Long getStageId() {
		return stageId;
	}
	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}
	public long getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(long workflowId) {
		this.workflowId = workflowId;
	}
	public int getStageType() {
		return stageType;
	}
	public void setStageType(int stageType) {
		this.stageType = stageType;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public int getFinalApproverRank() {
		return finalApproverRank;
	}
	public void setFinalApproverRank(int finalApproverRank) {
		this.finalApproverRank = finalApproverRank;
	}
	public int getApprovalMode() {
		return approvalMode;
	}
	public void setApprovalMode(int approvalMode) {
		this.approvalMode = approvalMode;
	}
	public String getEmpGroupIds() {
		return empGroupIds;
	}
	public void setEmpGroupIds(String empGroupIds) {
		this.empGroupIds = empGroupIds;
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
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getStageNameEmptyErrorMsg() {
		return stageNameEmptyErrorMsg;
	}
	public void setStageNameEmptyErrorMsg(String stageNameEmptyErrorMsg) {
		this.stageNameEmptyErrorMsg = stageNameEmptyErrorMsg;
	}
	public String getEmpIdGroupIdsEmptyErrorMsg() {
		return empIdGroupIdsEmptyErrorMsg;
	}
	public void setEmpIdGroupIdsEmptyErrorMsg(String empIdGroupIdsEmptyErrorMsg) {
		this.empIdGroupIdsEmptyErrorMsg = empIdGroupIdsEmptyErrorMsg;
	}
	
	public String getNoRolesFoundErrorMessage() {
		return noRolesFoundErrorMessage;
	}
	public void setNoRolesFoundErrorMessage(String noRolesFoundErrorMessage) {
		this.noRolesFoundErrorMessage = noRolesFoundErrorMessage;
	}
	public boolean equals(Object obj) {
		if(obj instanceof WorkflowStage){
		   WorkflowStage workflowStage = (WorkflowStage) obj;
		   if(stageId!=null&&workflowStage.getStageId()!=null){
		        boolean result= workflowStage.getStageId().longValue() == this.stageId.longValue();
		        return result;
		   }
		   return false;
		}  else {
			return super.equals(obj);
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
	
	public void toModifiedTimeXSD(){
		if(!Api.isEmptyString(modifiedTime) && !modifiedTime.endsWith("M")){
			try {
			    modifiedTime = Api.toUtcXsd(modifiedTime);
			} catch (Exception e) {
				Log.info(this.getClass(), e.toString());
			}
		}
	}
	public Long getSkeletonWorkFlowStageId() {
		return skeletonWorkFlowStageId;
	}
	public void setSkeletonWorkFlowStageId(Long skeletonWorkFlowStageId) {
		this.skeletonWorkFlowStageId = skeletonWorkFlowStageId;
	}
	public int getOnlyWithHierarchy() {
		return onlyWithHierarchy;
	}
	public void setOnlyWithHierarchy(int onlyWithHierarchy) {
		this.onlyWithHierarchy = onlyWithHierarchy;
	}
	public int getAutoApprovalDuration() {
		return autoApprovalDuration;
	}
	public void setAutoApprovalDuration(int autoApprovalDuration) {
		this.autoApprovalDuration = autoApprovalDuration;
	}
	public boolean isEndRuleType() {
		return endRuleType;
	}
	public void setEndRuleType(boolean endRuleType) {
		this.endRuleType = endRuleType;
	}
	public boolean isInitiatedFromRejectedConfiguration() {
		return initiatedFromRejectedConfiguration;
	}
	public void setInitiatedFromRejectedConfiguration(boolean initiatedFromRejectedConfiguration) {
		this.initiatedFromRejectedConfiguration = initiatedFromRejectedConfiguration;
	}
	public boolean isGoToFormSubmisson() {
		return goToFormSubmisson;
	}
	public void setGoToFormSubmisson(boolean goToFormSubmisson) {
		this.goToFormSubmisson = goToFormSubmisson;
	}
	public long getMappedStageId() {
		return mappedStageId;
	}
	public void setMappedStageId(long mappedStageId) {
		this.mappedStageId = mappedStageId;
	}
	public Integer getApprovalModeLevel() {
		return approvalModeLevel;
	}
	public void setApprovalModeLevel(Integer approvalModeLevel) {
		this.approvalModeLevel = approvalModeLevel;
	}
	public String getApprovalModeLevelErrorMessage() {
		return approvalModeLevelErrorMessage;
	}
	public void setApprovalModeLevelErrorMessage(String approvalModeLevelErrorMessage) {
		this.approvalModeLevelErrorMessage = approvalModeLevelErrorMessage;
	}
	public int getConsiderEveryoneInGroup() {
		return considerEveryoneInGroup;
	}
	public void setConsiderEveryoneInGroup(int considerEveryoneInGroup) {
		this.considerEveryoneInGroup = considerEveryoneInGroup;
	}
	public int getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(int approvalType) {
		this.approvalType = approvalType;
	}
	public String getParallelApprovalRoleIds() {
		return parallelApprovalRoleIds;
	}
	public void setParallelApprovalRoleIds(String parallelApprovalRoleIds) {
		this.parallelApprovalRoleIds = parallelApprovalRoleIds;
	}
	
	
	

	
}
