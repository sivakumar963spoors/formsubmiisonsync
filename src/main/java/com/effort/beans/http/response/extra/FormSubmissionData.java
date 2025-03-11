package com.effort.beans.http.response.extra;

import java.util.List;

import com.effort.entity.Form;
import com.effort.entity.Workflow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FormSubmissionData {

	@JsonProperty("forms")
	private FormDataContainer formDataContainer = new FormDataContainer();
	
	
	@JsonProperty("workflowDataContainer")
	private WorkflowDataContainer workflowDataContainer;
	private List<Form> forms;
	private List<Workflow> workflow;
	
	@JsonIgnore
	public FormDataContainer getFormDataContainer() {
		return formDataContainer;
	}


	
	public void setFormDataContainer(FormDataContainer formDataContainer) {
		this.formDataContainer = formDataContainer;
	}

	public WorkflowDataContainer getWorkflowDataContainer() {
		return workflowDataContainer;
	}

	public void setWorkflowDataContainer(WorkflowDataContainer workflowDataContainer) {
		this.workflowDataContainer = workflowDataContainer;
	}


	@JsonIgnore
	public List<Form> getForms() {
		return forms;
	}



	public void setForms(List<Form> forms) {
		this.forms = forms;
	}



	public List<Workflow> getWorkflow() {
		return workflow;
	}



	public void setWorkflow(List<Workflow> workflow) {
		this.workflow = workflow;
	} 
	
	
	
}
