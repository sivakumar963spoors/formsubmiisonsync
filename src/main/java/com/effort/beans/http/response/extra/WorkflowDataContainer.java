package com.effort.beans.http.response.extra;
import java.util.ArrayList;
import java.util.List;

import com.effort.entity.WorkFlowFormStatus;
import com.effort.entity.WorkFlowFormStatusHistory;



public class WorkflowDataContainer {
	private List<WorkFlowFormStatusHistory>workflowFormStatusHistory;
	private List<WorkFlowFormStatus>   workflowFormStatus;
	
	public WorkflowDataContainer() {
		workflowFormStatusHistory=new ArrayList<WorkFlowFormStatusHistory>();
		workflowFormStatus=new ArrayList<WorkFlowFormStatus>();
	}
	
	public List<WorkFlowFormStatusHistory> getWorkflowFormStatusHistory() {
		return workflowFormStatusHistory;
	}

	public void setWorkflowFormStatusHistory(List<WorkFlowFormStatusHistory> workflowFormStatusHistory) {
		this.workflowFormStatusHistory = workflowFormStatusHistory;
	}

	public List<WorkFlowFormStatus> getWorkflowFormStatus() {
		return workflowFormStatus;
	}
	public void setWorkflowFormStatus(List<WorkFlowFormStatus> workflowFormStatus) {
		this.workflowFormStatus = workflowFormStatus;
	}
	
	

}
