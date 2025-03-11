package com.effort.beans.http.response.extra;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkflowContainer {

	@JsonProperty("data")
	private WorkflowDataContainer workflowDataContainer;
	
	
	@JsonIgnore
	public WorkflowDataContainer getWorkflowDataContainer() {
		return workflowDataContainer;
	}
	
	
	

}
