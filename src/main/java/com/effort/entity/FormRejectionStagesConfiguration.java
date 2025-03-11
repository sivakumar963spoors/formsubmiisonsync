package com.effort.entity;



import java.io.Serializable;

public class FormRejectionStagesConfiguration implements Serializable{

	private static final long serialVersionUID = 1L;

    private long formRejectionStageId; 
    private long formRejectionId; 
    private long stageId;
    private long mappedStageId;
    private String createdTime;
    private long createdBy;
    private String stageName;
    
    public static final int GO_TO_FORM_SUBMISSION_STAGEID = -200;
	 
	public long getFormRejectionStageId() {
		return formRejectionStageId;
	}
	public void setFormRejectionStageId(long formRejectionStageId) {
		this.formRejectionStageId = formRejectionStageId;
	}
	public long getFormRejectionId() {
		return formRejectionId;
	}
	public void setFormRejectionId(long formRejectionId) {
		this.formRejectionId = formRejectionId;
	}
	public long getStageId() {
		return stageId;
	}
	public void setStageId(long stageId) {
		this.stageId = stageId;
	}
	public long getMappedStageId() {
		return mappedStageId;
	}
	public void setMappedStageId(long mappedStageId) {
		this.mappedStageId = mappedStageId;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	
	@Override
	public String toString() {
		return "FormRejectionStagesConfiguration [formRejectionStageId=" + formRejectionStageId + ", formRejectionId="
				+ formRejectionId + ", stageId=" + stageId + ", mappedStageId=" + mappedStageId + ", createdTime="
				+ createdTime + ", createdBy=" + createdBy + ", stageName=" + stageName + "]";
	}
	
}
