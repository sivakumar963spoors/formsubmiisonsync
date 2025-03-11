package com.effort.entity;



public class WorkFlowRealData {

	private Integer approvalMode;
	private String empGroupIds;
	private Integer stageType;
	private Integer finalApproverRank;
	private boolean endRuleType;
	
	public Integer getApprovalMode() {
		return approvalMode;
	}

	public void setApprovalMode(Integer approvalMode) {
		this.approvalMode = approvalMode;
	}

	public String getEmpGroupIds() {
		return empGroupIds;
	}

	public void setEmpGroupIds(String empGroupIds) {
		this.empGroupIds = empGroupIds;
	}

	public Integer getStageType() {
		return stageType;
	}

	public void setStageType(Integer stageType) {
		this.stageType = stageType;
	}

	public Integer getFinalApproverRank() {
		return finalApproverRank;
	}

	public void setFinalApproverRank(Integer finalApproverRank) {
		this.finalApproverRank = finalApproverRank;
	}
	public boolean isEndRuleType() {
		return endRuleType;
	}

	public void setEndRuleType(boolean endRuleType) {
		this.endRuleType = endRuleType;
	}

	@Override
	public String toString() {
		return "WorkFlowRealData [approvalMode=" + approvalMode + ", empGroupIds=" + empGroupIds + ", stageType="
				+ stageType + ", finalApproverRank=" + finalApproverRank + ", endRuleType=" + endRuleType + "]";
	}

}
