package com.effort.beans.http.response.extra;



import com.effort.entity.FormWorkflowContainer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormMainContainer {

	@JsonProperty("specs")
	private FormSpecContainer formSpecContainer = new FormSpecContainer();
	@JsonProperty("data")
	private FormDataContainer formDataContainer = new FormDataContainer();
	@JsonProperty("formWorkflow")
	private FormWorkflowContainer formWorkflowContainer=new FormWorkflowContainer();
	
	@JsonIgnore
	public FormSpecContainer getFormSpecContainer() {
		return formSpecContainer;
	}

	@JsonIgnore
	public FormDataContainer getFormDataContainer() {
		return formDataContainer;
	}
	@JsonIgnore
	public void setFormDataContainer(FormDataContainer formDataContainer) {
		this.formDataContainer = formDataContainer;
	}
	@JsonIgnore
	public FormWorkflowContainer getFormWorkflowContainer() {
		return formWorkflowContainer;
	}
}
