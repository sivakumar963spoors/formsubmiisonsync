package com.effort.entity;


public class SystemRejectedFormsLog {

	private long id;
	private int companyId;
	private long formId;
	private String remarks;
	private boolean reinitateFromBeginingOfTheStage;
	private Long workflowId;
	
	public SystemRejectedFormsLog() {}
	public SystemRejectedFormsLog(int companyId,long formId,String remarks) {
		this.companyId=companyId;
		this.formId=formId;
		this.remarks=remarks;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public long getFormId() {
		return formId;
	}
	public void setFormId(long formId) {
		this.formId = formId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public boolean isReinitateFromBeginingOfTheStage() {
		return reinitateFromBeginingOfTheStage;
	}
	public void setReinitateFromBeginingOfTheStage(boolean reinitateFromBeginingOfTheStage) {
		this.reinitateFromBeginingOfTheStage = reinitateFromBeginingOfTheStage;
	}
	public Long getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}
	
	
}
