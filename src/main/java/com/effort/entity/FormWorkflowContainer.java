package com.effort.entity;



import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormWorkflowContainer {
	
	private List<Workflow> workflows;
	private List<WorkflowStage>workflowStages;
	private List<WorkFlowFormStatusHistory>workFlowFormStatusHistory;
	private List<WorkFlowFormStatus>   workflowFormStatus;
	private List<WorkflowEntityMap> WorkflowEntityMap;
	
	public FormWorkflowContainer() {
		workflows=new ArrayList<Workflow>();
		workFlowFormStatusHistory=new ArrayList<WorkFlowFormStatusHistory>();
		workflowFormStatus=new ArrayList<WorkFlowFormStatus>();
	}

	@JsonProperty("workflowFormStatusHistory")
	public List<WorkFlowFormStatusHistory> getWorkFlowFormStatusHistory() {
		return workFlowFormStatusHistory;
	}

	public void setWorkFlowFormStatusHistory(List<WorkFlowFormStatusHistory> workFlowFormStatusHistory) {
		this.workFlowFormStatusHistory = workFlowFormStatusHistory;
	}

	
	
	public List<WorkflowEntityMap> getWorkflowEntityMap() {
		return WorkflowEntityMap;
	}

	public void setWorkflowEntityMap(List<WorkflowEntityMap> workflowEntityMap) {
		WorkflowEntityMap = workflowEntityMap;
	}

	@JsonProperty("workflows")
	public List<Workflow> getWorkflows() {
		return workflows;
	}
	
	@JsonProperty("workflowFormStatus")
	public List<WorkFlowFormStatus> getWorkflowFormStatus() {
		return workflowFormStatus;
	}

	public void setWorkflowFormStatus(List<WorkFlowFormStatus> workflowFormStatus) {
		this.workflowFormStatus = workflowFormStatus;
	}

	
	public void setWorkflows(List<Workflow> workflows) {
		this.workflows = workflows;
	}

	public List<WorkflowStage> getWorkflowStages() {
		return workflowStages;
	}

	public void setWorkflowStages(List<WorkflowStage> workflowStages) {
		this.workflowStages = workflowStages;
	}
   	
	

}
